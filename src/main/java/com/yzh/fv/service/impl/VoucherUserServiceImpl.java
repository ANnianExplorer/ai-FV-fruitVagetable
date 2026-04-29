package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.fv.entity.OrderVoucher;
import com.yzh.fv.entity.User;
import com.yzh.fv.entity.Voucher;
import com.yzh.fv.entity.VoucherUser;
import com.yzh.fv.mapper.VoucherUserMapper;
import com.yzh.fv.service.OrderVoucherService;
import com.yzh.fv.service.UserService;
import com.yzh.fv.service.VoucherServer;
import com.yzh.fv.service.VoucherUserServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : xiao
 * @date : 2022/11/28 10:59
 */
@Service
@Slf4j
public class VoucherUserServiceImpl extends ServiceImpl<VoucherUserMapper, VoucherUser> implements VoucherUserServer {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private VoucherServer voucherServer;
    
    @Autowired
    private OrderVoucherService orderVoucherService;
    
    @Override
    public int batchIssueVouchersToUsers(List<Long> userIds, Long voucherId, int checkv, LocalDateTime now) {
        if (CollectionUtils.isEmpty(userIds) || voucherId == null) {
            return 0;
        }
        
        List<VoucherUser> newVoucherUsers = new ArrayList<>();
        List<VoucherUser> updateVoucherUsers = new ArrayList<>();
        
        // 批量查询用户是否已拥有该优惠券
        for (Long userId : userIds) {
            LambdaQueryWrapper<VoucherUser> checkWrapper = new LambdaQueryWrapper<>();
            checkWrapper.eq(VoucherUser::getUserId, userId)
                       .eq(VoucherUser::getVoucherId, voucherId);
            
            VoucherUser existing = this.getOne(checkWrapper);
            
            if (existing == null) {
                // 创建新的优惠券记录
                VoucherUser voucherUser = new VoucherUser();
                voucherUser.setVoucherId(voucherId);
                voucherUser.setUserId(userId);
                voucherUser.setStatus(0); // 0表示未使用
                voucherUser.setCheckv(checkv);
                voucherUser.setCreateTime(now);
                voucherUser.setUpdateTime(now);
                voucherUser.setReceivedCount(1);
                voucherUser.setUsedCount(0);
                newVoucherUsers.add(voucherUser);
            } else {
                // 更新现有记录，增加领取次数
                existing.setUpdateTime(now);
                Integer currentCount = existing.getReceivedCount() != null ? existing.getReceivedCount() : 0;
                existing.setReceivedCount(currentCount + 1);
                updateVoucherUsers.add(existing);
            }
        }
        
        // 批量保存和更新，减少数据库交互次数
        int successCount = 0;
        if (!newVoucherUsers.isEmpty()) {
            this.saveBatch(newVoucherUsers, 100);
            successCount += newVoucherUsers.size();
        }
        
        if (!updateVoucherUsers.isEmpty()) {
            this.updateBatchById(updateVoucherUsers, 100);
            successCount += updateVoucherUsers.size();
        }
        
        return successCount;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int issueNewUserCoupons(Long userId) {
        // 验证用户是否存在
        User user = userService.getById(userId);
        if (user == null) {
            log.warn("用户 {} 不存在，无法发放新人优惠券", userId);
            return 0;
        }
        
        // 计算用户创建时间与当前时间的差值，确保是新用户（创建时间在7天内）
        LocalDateTime now = LocalDateTime.now();
        long daysDifference = ChronoUnit.DAYS.between(user.getCreateTime(), now);
        int checkv = daysDifference <= 7 ? 1 : 0;
        
        if (checkv != 1) {
            log.info("用户 {} 创建于 {}, 与当前时间相差 {} 天，不是新用户，不发放新人优惠券", 
                    userId, user.getCreateTime(), daysDifference);
            return 0;
        }
        
        // 获取所有启用状态的新人优惠券
        List<Voucher> newUserVouchers = voucherServer.getActiveNewUserVouchers(now);
        
        if (CollectionUtils.isEmpty(newUserVouchers)) {
            log.info("当前没有可用的新人优惠券，用户 {} 未获得优惠券", userId);
            return 0;
        }
        
        // 准备批量保存新的优惠券记录
        List<VoucherUser> newVoucherUsers = new ArrayList<>();
        
        for (Voucher voucher : newUserVouchers) {
            // 检查用户是否已经拥有该优惠券
            VoucherUser existing = checkUserHasVoucher(userId, voucher.getId());
            
            if (existing == null) {
                // 创建新的优惠券记录
                VoucherUser voucherUser = new VoucherUser();
                voucherUser.setVoucherId(voucher.getId());
                voucherUser.setUserId(userId);
                voucherUser.setStatus(0); // 0表示未使用
                voucherUser.setCheckv(1); // 标记为新用户
                voucherUser.setCreateTime(now);
                voucherUser.setUpdateTime(now);
                voucherUser.setReceivedCount(1);
                voucherUser.setUsedCount(0);
                newVoucherUsers.add(voucherUser);
                
                log.info("为新用户 {} 发放新人优惠券 {} - {} 成功", 
                        userId, voucher.getId(), voucher.getName());
            } else {
                log.info("新用户 {} 已经拥有新人优惠券 {} - {}，跳过发放", 
                        userId, voucher.getId(), voucher.getName());
            }
        }
        
        // 批量保存优惠券记录
        if (!newVoucherUsers.isEmpty()) {
            this.saveBatch(newVoucherUsers, 100);
            log.info("成功为新用户 {} 批量发放 {} 张新人优惠券", userId, newVoucherUsers.size());
        }
        
        return newVoucherUsers.size();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean useCouponWithLock(Long voucherId, Long userId, Long orderId) {
        // 验证优惠券是否有效
        String validateResult = voucherServer.validateVoucher(voucherId, userId);
        if (validateResult != null) {
            log.warn("优惠券验证失败: {}，用户ID: {}，优惠券ID: {}", validateResult, userId, voucherId);
            return false;
        }
        
        // 使用乐观锁更新优惠券状态
        UpdateWrapper<VoucherUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId)
                    .eq("voucher_id", voucherId)
                    .eq("status", 0); // 确保状态还是未使用
        
        // 设置状态和更新时间
        LocalDateTime now = LocalDateTime.now();
        updateWrapper.set("status", 1); // 1表示已使用
        updateWrapper.set("update_time", now);
        updateWrapper.set("use_time", now);
        updateWrapper.setSql("used_count = used_count + 1");
        updateWrapper.set("order_id", orderId); // 关联订单ID
        
        // 直接使用updateWrapper更新
        boolean updateResult = this.update(updateWrapper);
        
        if (updateResult && orderId != null) {
            // 保存订单优惠券关联记录
            saveOrderVoucher(orderId, voucherId);
        }
        
        return updateResult;
    }
    
    @Override
    public VoucherUser checkUserHasVoucher(Long userId, Long voucherId) {
        LambdaQueryWrapper<VoucherUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VoucherUser::getUserId, userId)
               .eq(VoucherUser::getVoucherId, voucherId);
        return this.getOne(wrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelCoupon(Long voucherId, Long userId) {
        try {
            // 使用乐观锁更新优惠券状态为未使用
            UpdateWrapper<VoucherUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", userId)
                        .eq("voucher_id", voucherId)
                        .eq("status", 1); // 确保状态是已使用
            
            LocalDateTime now = LocalDateTime.now();
            updateWrapper.set("status", 0); // 恢复为未使用状态
            updateWrapper.set("update_time", now);
            updateWrapper.set("use_time", null); // 清空使用时间
            updateWrapper.setSql("used_count = used_count - 1"); // 减少使用次数
            updateWrapper.set("order_id", null); // 清空订单关联
            
            // 执行更新
            boolean updateResult = this.update(updateWrapper);
            
            if (updateResult) {
                log.info("优惠券取消使用成功，用户ID：{}，优惠券ID：{}", userId, voucherId);
                // 删除订单优惠券关联记录
                deleteOrderVoucher(userId, voucherId);
            } else {
                log.warn("优惠券取消使用失败，用户ID：{}，优惠券ID：{}", userId, voucherId);
            }
            
            return updateResult;
        } catch (Exception e) {
            log.error("取消优惠券使用时发生异常: userId={}, voucherId={}, error={}", 
                    userId, voucherId, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public List<VoucherUser> getUserAvailableVouchers(Long userId) {
        LambdaQueryWrapper<VoucherUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VoucherUser::getUserId, userId)
               .eq(VoucherUser::getStatus, 0) // 未使用状态
               .orderByDesc(VoucherUser::getCreateTime);
        
        return this.list(wrapper);
    }
    
    /**
     * 保存订单优惠券关联记录
     */
    private void saveOrderVoucher(Long orderId, Long voucherId) {
        try {
            // 获取优惠券金额
            Voucher voucher = voucherServer.getById(voucherId);
            if (voucher == null) {
                log.warn("优惠券不存在: voucherId={}", voucherId);
                return;
            }
            
            // 创建订单优惠券关联记录
            OrderVoucher orderVoucher = new OrderVoucher();
            orderVoucher.setOrderId(orderId);
            orderVoucher.setVoucherId(voucherId);
            orderVoucher.setDiscountAmount(voucher.getPrice());
            orderVoucher.setCreateTime(LocalDateTime.now());
            orderVoucher.setUpdateTime(LocalDateTime.now());
            
            // 保存订单优惠券关联记录
            boolean saveResult = orderVoucherService.save(orderVoucher);
            if (!saveResult) {
                log.error("保存订单优惠券关联记录失败，订单ID：{}，优惠券ID：{}", orderId, voucherId);
            } else {
                log.info("保存订单优惠券关联记录成功，订单ID：{}，优惠券ID：{}", orderId, voucherId);
            }
        } catch (Exception e) {
            log.error("保存订单优惠券关联记录时发生异常: orderId={}, voucherId={}, error={}", 
                    orderId, voucherId, e.getMessage(), e);
        }
    }
    
    /**
     * 删除订单优惠券关联记录
     */
    private void deleteOrderVoucher(Long userId, Long voucherId) {
        try {
            // 先查询相关的订单优惠券关联记录
            LambdaQueryWrapper<VoucherUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(VoucherUser::getUserId, userId)
                   .eq(VoucherUser::getVoucherId, voucherId)
                   .select(VoucherUser::getOrderId);
            
            VoucherUser voucherUser = this.getOne(wrapper);
            if (voucherUser != null && voucherUser.getOrderId() != null) {
                // 删除订单优惠券关联记录
                LambdaQueryWrapper<OrderVoucher> orderVoucherWrapper = new LambdaQueryWrapper<>();
                orderVoucherWrapper.eq(OrderVoucher::getOrderId, voucherUser.getOrderId())
                                  .eq(OrderVoucher::getVoucherId, voucherId);
                
                boolean deleteResult = orderVoucherService.remove(orderVoucherWrapper);
                if (deleteResult) {
                    log.info("删除订单优惠券关联记录成功，订单ID：{}，优惠券ID：{}", voucherUser.getOrderId(), voucherId);
                }
            }
        } catch (Exception e) {
            log.error("删除订单优惠券关联记录时发生异常: userId={}, voucherId={}, error={}", 
                    userId, voucherId, e.getMessage(), e);
        }
    }
}
