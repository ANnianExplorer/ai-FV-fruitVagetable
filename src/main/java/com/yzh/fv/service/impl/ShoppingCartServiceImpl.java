package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.fv.common.BaseContext;
import com.yzh.fv.common.R;
import com.yzh.fv.entity.ShoppingCart;
import com.yzh.fv.mapper.ShoppingCartMapper;
import com.yzh.fv.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    
    // 用于存储用户购物车商品的原始价格，使用ConcurrentHashMap保证线程安全
    private final Map<String, BigDecimal> originalPriceCache = new ConcurrentHashMap<>();
    
    public R<String> clean(){
        // 进行用户比对
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        // 删除即可
        this.remove(queryWrapper);
        
        // 清空缓存中的原始价格
        String cacheKeyPrefix = "user_" + BaseContext.getCurrentId() + "_";
        originalPriceCache.keySet().removeIf(key -> key.startsWith(cacheKeyPrefix));

        return R.success("清空成功");
    }
    
    @Override
    public R<String> updateCartPricesWithDiscount(Long userId, BigDecimal discountRate) {
        try {
            // 参数验证
            if (userId == null || discountRate == null || discountRate.compareTo(BigDecimal.ZERO) <= 0 || discountRate.compareTo(BigDecimal.ONE) > 0) {
                log.error("无效的参数：userId={}, discountRate={}", userId, discountRate);
                return R.error("无效的参数");
            }
            
            // 获取用户的购物车列表
            List<ShoppingCart> cartList = getUserCartList(userId);
            if (cartList == null || cartList.isEmpty()) {
                return R.error("购物车为空");
            }
            
            // 更新购物车商品价格
            for (ShoppingCart cart : cartList) {
                String cacheKey = "user_" + userId + "_item_" + cart.getId();
                // 先缓存原始价格（如果还没有缓存）
                if (!originalPriceCache.containsKey(cacheKey)) {
                    originalPriceCache.put(cacheKey, cart.getAmount());
                    log.info("缓存商品{}的原始价格：{}", cart.getId(), cart.getAmount());
                }
                
                // 计算折扣后的价格
                BigDecimal discountedPrice = originalPriceCache.get(cacheKey).multiply(discountRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                cart.setAmount(discountedPrice);
                log.info("更新商品{}价格为折扣价：{}", cart.getId(), discountedPrice);
                
                // 保存更新后的价格
                this.updateById(cart);
            }
            
            return R.success("购物车价格已更新");
        } catch (Exception e) {
            log.error("更新购物车价格时出错", e);
            return R.error("更新购物车价格失败");
        }
    }
    
    @Override
    public R<String> restoreCartOriginalPrices(Long userId) {
        try {
            if (userId == null) {
                return R.error("无效的用户ID");
            }
            
            // 获取用户的购物车列表
            List<ShoppingCart> cartList = getUserCartList(userId);
            if (cartList == null || cartList.isEmpty()) {
                return R.error("购物车为空");
            }
            
            // 恢复购物车商品的原始价格
            for (ShoppingCart cart : cartList) {
                String cacheKey = "user_" + userId + "_item_" + cart.getId();
                if (originalPriceCache.containsKey(cacheKey)) {
                    BigDecimal originalPrice = originalPriceCache.get(cacheKey);
                    cart.setAmount(originalPrice);
                    log.info("恢复商品{}价格为原价：{}", cart.getId(), originalPrice);
                    
                    // 保存更新后的价格
                    this.updateById(cart);
                    
                    // 从缓存中移除已恢复的价格
                    originalPriceCache.remove(cacheKey);
                }
            }
            
            return R.success("购物车价格已恢复为原价");
        } catch (Exception e) {
            log.error("恢复购物车原始价格时出错", e);
            return R.error("恢复购物车原始价格失败");
        }
    }
    
    @Override
    public List<ShoppingCart> getUserCartList(Long userId) {
        if (userId == null) {
            return null;
        }
        
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        
        return this.list(queryWrapper);
    }
}
