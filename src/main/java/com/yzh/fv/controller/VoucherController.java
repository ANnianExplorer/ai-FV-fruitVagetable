package com.yzh.fv.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzh.fv.common.BaseContext;
import com.yzh.fv.common.CustomException;
import com.yzh.fv.common.R;
import com.yzh.fv.dto.VoucherDto;
import com.yzh.fv.dto.VoucherUseRequest;
import com.yzh.fv.entity.*;
import com.yzh.fv.mapper.VoucherUserMapper;
import com.yzh.fv.service.UserService;
import com.yzh.fv.service.VoucherServer;
import com.yzh.fv.service.VoucherUserServer;
import com.yzh.fv.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 券控制器
 * 优惠券类
 *
 * @author 杨振华
 * @date 2023/05/31
 * @since ： 2023/5/13 15:12
 */
@RestController
@Slf4j
@RequestMapping("/voucher")
public class VoucherController {
    @Resource
    private VoucherServer voucherServer;

    @Resource
    private VoucherUserServer voucherUserServer;

    // 移除错误的注入

    @Resource
    private VoucherUserMapper voucherUserMapper;

    @Resource
    private UserService userService;
    
    @Resource
    private ShoppingCartService shoppingCartService;
    
    /**
     * 计算购物车总金额
     * @param userId 用户ID
     * @return 购物车总金额
     */
    private BigDecimal calculateCartTotal(Long userId) {
        List<ShoppingCart> cartItems = shoppingCartService.getUserCartList(userId);
        BigDecimal total = BigDecimal.ZERO;
        if (cartItems != null && !cartItems.isEmpty()) {
            for (ShoppingCart item : cartItems) {
                total = total.add(item.getAmount().multiply(new BigDecimal(item.getNumber())));
            }
        }
        return total;
    }
    
    /**
     * 获取所有用户ID列表
     * 注意：实际项目中应该调用用户服务获取所有用户
     * 这里提供一个临时实现，实际使用时需要替换为真实的用户获取逻辑
     */
    private List<Long> getAllUserIds() {
        try {
            // 方法1：尝试从用户表获取所有用户（推荐，需要注入用户服务）
            // 以下是示例代码，实际项目中应该注入用户服务
             return userService.list().stream()
                     .map(User::getId)
                     .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("获取用户列表失败：", e);
            // 出错时返回一个默认列表，避免功能完全不可用
            return java.util.Arrays.asList(1L); // 默认只有管理员用户
        }
    }


    @PostMapping()
    public R<String> addVoucher(@RequestBody Voucher voucher){
        log.info(voucher.toString());
        voucherServer.save(voucher);
        return R.success("新增优惠券成功！");
    }


    /**
     * 后台分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //分页构造器
        Page<Voucher> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Voucher> voucherQuery = new LambdaQueryWrapper<>();
        voucherQuery
                .like(StringUtils.isNotEmpty(name), Voucher::getName, name)
                .orderByDesc(Voucher::getCreateTime);
        //执行查询
        voucherServer.page(pageInfo, voucherQuery);
        return R.success(pageInfo);
    }

    /**
     * 根据id删除优惠券
     * @param ids
     * @return
     */
    @DeleteMapping()
    public R<String> deleteVoucher(@RequestParam List<Long> ids) {
        //先查询是否存在起售
        LambdaQueryWrapper<Voucher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(Voucher::getId, ids)
                .eq(Voucher::getStatus, 1);
        if (voucherServer.count(queryWrapper) > 0) {
            throw new CustomException("该优惠券正在启用中，不可删除！");
        }
        //删除
        voucherServer.removeByIds(ids);
        return R.success("优惠券删除成功！");
    }

    /**
     * 优惠券停用
     * 前台应提前告知客户停用时间
     * @param ids
     * @return
     */
    @PostMapping("/status/0")
    public R<String> statusStop(@RequestParam List<Long> ids) {
        //根据输入的ids，进行停用
        LambdaQueryWrapper<Voucher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(Voucher::getId, ids)
                .eq(Voucher::getStatus, 1);
        int count = voucherServer.count(queryWrapper);
        if (count > 0) {
            for (Long id : ids) {
                Voucher voucher = voucherServer.getById(id);
                voucher.setStatus(0);
                voucherServer.updateById(voucher);
            }
        }
        UpdateWrapper<VoucherUser> userUpdateWrapper = new UpdateWrapper<VoucherUser>();
        userUpdateWrapper.set("voucher_id",0);
        userUpdateWrapper.set("status",0);
        voucherUserMapper.update(null, userUpdateWrapper);
        return R.success("优惠券已停用！");
    }

    /**
     * 优惠券启用
     *
     * @param ids
     * @return
     */
    @PostMapping("/status/1")
    public R<String> statusStart(@RequestParam List<Long> ids) {
        //根据输入的ids，进行启用
        // 查询数据库找到存在且停售的优惠券
        LambdaQueryWrapper<Voucher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(Voucher::getId, ids)
                .eq(Voucher::getStatus, 0);
        int count = voucherServer.count(queryWrapper);
        // 如果启用优惠券，为用户创建优惠券记录
        if (count > 0) {
            for (Long id : ids) {
                Voucher voucher = voucherServer.getById(id);
                voucher.setStatus(1);
                voucher.setUpdateTime(LocalDateTime.now());
                voucherServer.updateById(voucher);
                
                // 获取所有用户列表
                List<Long> allUserIds = getAllUserIds();
                
                // 根据优惠券类型区分发放逻辑
                Integer voucherType = voucher.getType() != null ? voucher.getType() : 1; // 默认类型为1（老用户优惠券）
                
                // 优化1: 批量获取用户信息，减少数据库查询次数
                List<User> users = userService.listByIds(allUserIds);
                Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
                
                // 优化2: 批量获取当前优惠券的所有用户记录
                LambdaQueryWrapper<VoucherUser> existingWrapper = new LambdaQueryWrapper<>();
                existingWrapper.eq(VoucherUser::getVoucherId, id);
                List<VoucherUser> existingVoucherUsers = voucherUserServer.list(existingWrapper);
                Map<Long, VoucherUser> existingVoucherUserMap = existingVoucherUsers.stream()
                        .collect(Collectors.toMap(VoucherUser::getUserId, v -> v));
                
                // 准备批量操作的数据
                List<VoucherUser> newVoucherUsers = new ArrayList<>();
                List<VoucherUser> updateVoucherUsers = new ArrayList<>();
                
                LocalDateTime now = LocalDateTime.now();
                
                for (Long userId : allUserIds) {
                    // 从缓存的用户Map中获取用户信息
                    User user = userMap.get(userId);
                    if (user == null) {
                        log.warn("用户 {} 不存在，跳过优惠券发放", userId);
                        continue;
                    }
                    
                    // 计算用户创建时间与当前时间的差值，判断是否为新用户
                    int checkv = 0; // 默认是老用户
                    if (user.getCreateTime() != null) {
                        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(user.getCreateTime(), now);
                        // 如果用户创建时间与当前时间差值不超过7天，则为新用户
                        checkv = daysDifference <= 7 ? 1 : 0;
                        
                        if (log.isDebugEnabled()) {
                            log.debug("用户 {} 创建于 {}, 与当前时间相差 {} 天, 判定为{}用户", 
                                    userId, user.getCreateTime(), daysDifference, checkv == 1 ? "新" : "老");
                        }
                    }
                    
                    // 根据优惠券类型和用户类型决定是否发放优惠券
                    boolean shouldIssue = false;
                    
                    // 从缓存的优惠券用户Map中获取现有记录
                    VoucherUser existing = existingVoucherUserMap.get(userId);
                    
                    if (voucherType == 0) { // 新人优惠券
                        shouldIssue = checkv == 1; // 只发放给新用户
                        if (log.isInfoEnabled()) {
                            log.info("优惠券 {} 为新人优惠券，用户 {} 是{}新用户，{}发放", 
                                    id, userId, checkv == 1 ? "" : "非", shouldIssue ? "进行" : "不进行");
                        }
                    } else if (voucherType == 1) { // 老用户/通用优惠券
                        // 对于非新人优惠券，如果用户已有记录且领取次数和使用次数都超过2次，则不再发放
                        if (existing != null) {
                            Integer receivedCount = existing.getReceivedCount() != null ? existing.getReceivedCount() : 0;
                            Integer usedCount = existing.getUsedCount() != null ? existing.getUsedCount() : 0;
                            
                            if (receivedCount >= 2 && usedCount >= 2) {
                                shouldIssue = false;
                                if (log.isInfoEnabled()) {
                                    log.info("用户 {} 对优惠券 {} 的领取次数({})和使用次数({})均已超过2次，不再发放", 
                                            userId, id, receivedCount, usedCount);
                                }
                            } else {
                                shouldIssue = true;
                                if (log.isDebugEnabled()) {
                                    log.debug("优惠券 {} 为通用优惠券，用户 {} 的领取/使用次数未达上限，进行发放", id, userId);
                                }
                            }
                        } else {
                            shouldIssue = true; // 新用户第一次领取优惠券
                            if (log.isDebugEnabled()) {
                                log.debug("优惠券 {} 为通用优惠券，用户 {} 首次领取，进行发放", id, userId);
                            }
                        }
                    }
                    
                    if (shouldIssue) {
                        if (existing == null) {
                            // 创建新的优惠券记录，添加到批量插入列表
                            VoucherUser voucherUser = new VoucherUser();
                            voucherUser.setVoucherId(id);
                            voucherUser.setUserId(userId);
                            voucherUser.setStatus(0); // 0表示未使用
                            voucherUser.setCheckv(checkv); // 设置是否为新用户
                            voucherUser.setCreateTime(now);
                            voucherUser.setUpdateTime(now);
                            voucherUser.setReceivedCount(1); // 初始领取次数为1
                            voucherUser.setUsedCount(0); // 初始使用次数为0
                            newVoucherUsers.add(voucherUser);
                            if (log.isInfoEnabled()) {
                                log.info("为用户 {} 发放优惠券 {} 成功", userId, id);
                            }
                        } else {
                            // 更新现有记录，增加领取次数，添加到批量更新列表
                            existing.setUpdateTime(now);
                            Integer currentCount = existing.getReceivedCount() != null ? existing.getReceivedCount() : 0;
                            existing.setReceivedCount(currentCount + 1);
                            updateVoucherUsers.add(existing);
                            if (log.isDebugEnabled()) {
                                log.debug("用户 {} 已有优惠券 {}，更新领取次数至 {}，更新时间戳", 
                                        userId, id, existing.getReceivedCount());
                            }
                        }
                    }
                }
                
                // 优化3: 批量保存和更新，减少数据库交互次数
                if (!newVoucherUsers.isEmpty()) {
                    voucherUserServer.saveBatch(newVoucherUsers, 100); // 批量保存，每批100条
                    log.info("批量保存新优惠券记录 {} 条", newVoucherUsers.size());
                }
                
                if (!updateVoucherUsers.isEmpty()) {
                    voucherUserServer.updateBatchById(updateVoucherUsers, 100); // 批量更新，每批100条
                    log.info("批量更新优惠券记录 {} 条", updateVoucherUsers.size());
                }
            }
        }

        return R.success("优惠券已启用并发放给所有用户！");

    }

    /**
     *  以下是客户端的优惠券功能
     */
    @GetMapping("/getVoucher")
    public R<List<VoucherDto>> getVou(){
        log.info("查看用户优惠券");
        Long userId = BaseContext.getCurrentId();
        
        if (userId == null) {
            log.warn("用户未登录");
            return R.error("用户未登录");
        }
        
        try {
            // 从服务层获取用户可用优惠券列表
            List<VoucherDto> voucherDtoList = voucherServer.getUserAvailableVouchers(userId);
            
            if (voucherDtoList != null && !voucherDtoList.isEmpty()) {
                log.info("成功获取用户 {} 的优惠券列表，共 {} 张", userId, voucherDtoList.size());
                return R.success(voucherDtoList);
            } else {
                log.info("用户 {} 暂无可用优惠券", userId);
                return R.error("暂无优惠券");
            }
        } catch (Exception e) {
            log.error("获取用户优惠券时发生异常: userId={}, error={}", userId, e.getMessage(), e);
            return R.error("获取优惠券失败: " + e.getMessage());
        }
    }

    /**
     * 使用优惠券
     * 在用户点击使用优惠券时进行完整验证和状态更新
     */
    @PostMapping("/coupon/use")
    @Transactional
    public R<String> useCoupon(@RequestBody @Valid VoucherUseRequest request) {
        Long voucherID = request.getVoucherID();
        Long userId = BaseContext.getCurrentId();
        Long orderId = request.getOrderId();
        
        log.info("用户 {} 尝试使用优惠券 {}，订单ID: {}", userId, voucherID, orderId);
        
        try {
            // 参数验证
            if (userId == null || voucherID == null) {
                log.warn("参数错误: userId={}, voucherID={}", userId, voucherID);
                return R.error("用户未登录或参数错误");
            }
            
            // 1. 检查优惠券基本信息
            Voucher voucher = voucherServer.getById(voucherID);
            if (voucher == null) {
                log.warn("优惠券 {} 不存在", voucherID);
                return R.error("优惠券不存在");
            }
            
            // 2. 验证优惠券状态
            if (voucher.getStatus() != 1) {
                log.warn("优惠券 {} 未激活，状态码：{}", voucherID, voucher.getStatus());
                return R.error("优惠券未激活或已失效");
            }
            
            // 3. 验证优惠券有效期
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(voucher.getStartTime()) || now.isAfter(voucher.getEndTime())) {
                log.warn("优惠券 {} 不在有效期内，当前时间：{}，有效期：{}-{}", 
                        voucherID, now, voucher.getStartTime(), voucher.getEndTime());
                return R.error("优惠券不在有效期内");
            }
            
            // 4. 检查用户是否拥有该优惠券且未使用
            VoucherUser voucherUser = voucherUserServer.checkUserHasVoucher(userId, voucherID);
            if (voucherUser == null || voucherUser.getStatus() != 0) {
                log.warn("用户 {} 未拥有优惠券 {} 或优惠券已使用", userId, voucherID);
                return R.error("您未拥有该优惠券或优惠券已使用");
            }
            
            // 5. 验证购物车金额是否达到最低消费标准
            BigDecimal cartTotal = calculateCartTotal(userId);
            if (voucher.getMinMoney() != null && cartTotal.compareTo(voucher.getMinMoney()) < 0) {
                log.warn("购物车金额 {} 未达到优惠券 {} 最低使用标准 {}", 
                        cartTotal, voucherID, voucher.getMinMoney());
                return R.error("购物车金额未达到优惠券最低使用标准");
            }
            
            // 6. 调用服务层方法使用优惠券（包含乐观锁机制）
            boolean result = voucherUserServer.useCouponWithLock(voucherID, userId, orderId);
            
            if (result) {
                // 7. 使用优惠券成功后，更新购物车价格
                // 计算折扣率
                if (cartTotal.compareTo(BigDecimal.ZERO) > 0) {
                    // 计算折扣率 = (原价 - 优惠金额) / 原价
                    BigDecimal discountRate = cartTotal.subtract(voucher.getPrice())
                            .divide(cartTotal, 2, BigDecimal.ROUND_HALF_UP);
                    // 确保折扣率在有效范围内
                    if (discountRate.compareTo(BigDecimal.ZERO) > 0 && discountRate.compareTo(BigDecimal.ONE) <= 0) {
                        shoppingCartService.updateCartPricesWithDiscount(userId, discountRate);
                        log.info("已更新用户 {} 的购物车价格，应用折扣率：{}", userId, discountRate);
                    }
                }
                
                String discountAmount = String.valueOf(voucher.getPrice());
                log.info("用户 {} 使用优惠券 {} 成功，优惠金额：{}", userId, voucherID, discountAmount);
                return R.success(discountAmount);
            } else {
                log.warn("用户 {} 使用优惠券 {} 失败，可能已被其他请求使用", userId, voucherID);
                return R.error("优惠券已被使用，请刷新后重试");
            }
        } catch (IllegalArgumentException e) {
            // 处理参数错误和业务规则异常
            log.warn("优惠券使用参数或业务规则错误: userId={}, voucherID={}, error={}", 
                    userId, voucherID, e.getMessage());
            return R.error(e.getMessage());
        } catch (Exception e) {
            // 捕获所有其他异常
            log.error("优惠券使用过程中发生异常: userId={}, voucherID={}, error={}", 
                    userId, voucherID, e.getMessage(), e);
            return R.error("系统处理优惠券时出现错误: " + e.getMessage());
        }
    }

    
    /**
     * 验证优惠券是否可用（用于订单提交前的校验）
     */
    @PostMapping("/coupon/verify")
    public R<String> verifyCoupon(@RequestBody Map<String, Long> request) {
        Long voucherID = request.get("voucherID");
        Long userId = BaseContext.getCurrentId();
        
        log.info("验证用户 {} 的优惠券 {} 是否可用", userId, voucherID);
        
        try {
            // 参数验证
            if (voucherID == null || userId == null) {
                log.warn("参数错误: userId={}, voucherID={}", userId, voucherID);
                return R.error("参数错误");
            }
            
            // 先检查优惠券基本信息
            Voucher voucher = voucherServer.getById(voucherID);
            if (voucher == null) {
                return R.error("优惠券不存在");
            }
            
            if (voucher.getStatus() != 1) {
                return R.error("优惠券未激活");
            }
            
            // 检查是否在有效期内
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(voucher.getStartTime()) || now.isAfter(voucher.getEndTime())) {
                return R.error("优惠券不在有效期内");
            }
            
            // 检查用户是否拥有该优惠券
            VoucherUser voucherUser = voucherUserServer.checkUserHasVoucher(userId, voucherID);
            if (voucherUser == null) {
                return R.error("您未拥有该优惠券");
            }
            
            // 注意：这里不检查优惠券是否已使用(status != 0)
            // 因为用户已经在前端点击了使用优惠券，优惠券状态可能已经被设置为已使用
            // 真正的使用和状态更新应该在订单提交时由OrdersServiceImpl处理
            
            log.info("优惠券 {} 验证通过，用户 {} 可使用", voucherID, userId);
            return R.success("优惠券验证通过");
        } catch (Exception e) {
            log.error("验证优惠券过程中发生异常: userId={}, voucherID={}, error={}", 
                    userId, voucherID, e.getMessage(), e);
            return R.error("系统验证优惠券时出现错误: " + e.getMessage());
        }
    }


    /**
     * 为新注册用户自动发放新人优惠券
     * 当新用户注册成功时调用此方法，自动发放所有启用状态的新人优惠券
     * 
     * @param userId 新注册用户的ID
     * @return 发放结果
     */
    @PostMapping("/issueNewUserCoupons/{userId}")
    @Transactional
    public R<String> issueNewUserCoupons(@PathVariable Long userId) {
        log.info("为新注册用户 {} 自动发放新人优惠券", userId);
        
        try {
            // 调用服务层方法发放新人优惠券
            int issuedCount = voucherUserServer.issueNewUserCoupons(userId);
            
            if (issuedCount > 0) {
                log.info("成功为新用户 {} 发放 {} 张新人优惠券", userId, issuedCount);
                return R.success("新人优惠券发放成功，共发放 " + issuedCount + " 张优惠券");
            } else {
                return R.success("用户不是新用户或当前没有可用的新人优惠券");
            }
        } catch (IllegalArgumentException e) {
            log.warn("发放新人优惠券参数错误: userId={}, error={}", userId, e.getMessage());
            return R.error(e.getMessage());
        } catch (Exception e) {
            log.error("为新用户 {} 发放新人优惠券时发生异常: {}", userId, e.getMessage(), e);
            return R.error("优惠券发放失败: " + e.getMessage());
        }
    }
    
    /**
     * 内部方法：为新注册用户自动发放新人优惠券
     * 供其他服务直接调用，无需通过HTTP请求
     * 
     * @param userId 新注册用户的ID
     */
    public void issueNewUserCouponsInternal(Long userId) {
        try {
            // 直接调用服务层内部方法
            voucherUserServer.issueNewUserCoupons(userId);
            log.info("内部调用发放新人优惠券成功: userId={}", userId);
        } catch (Exception e) {
            log.error("内部调用发放新人优惠券失败: userId={}, error={}", userId, e.getMessage(), e);
            // 内部调用时不抛异常，仅记录日志
        }
    }
    
    /**
     * 取消使用优惠券并恢复购物车原始价格
     */
    @PostMapping("/coupon/cancel")
    public R<String> cancelCoupon(@RequestBody Map<String, Long> request) {
        Long voucherID = request.get("voucherID");
        Long userId = BaseContext.getCurrentId();
        
        log.info("用户 {} 取消使用优惠券 {}", userId, voucherID);
        
        try {
            // 参数验证
            if (voucherID == null || userId == null) {
                log.warn("取消优惠券参数错误: userId={}, voucherID={}", userId, voucherID);
                return R.error("参数错误");
            }
            
            // 1. 恢复优惠券状态为未使用
            boolean couponResult = voucherUserServer.cancelCoupon(voucherID, userId);
            
            // 2. 恢复购物车原始价格
            shoppingCartService.restoreCartOriginalPrices(userId);
            log.info("已恢复用户 {} 的购物车原始价格", userId);
            
            if (couponResult) {
                log.info("用户 {} 优惠券 {} 取消使用成功，优惠券状态和购物车价格已恢复", userId, voucherID);
                return R.success("已取消优惠券使用，恢复购物车原始价格和优惠券状态");
            } else {
                log.warn("用户 {} 优惠券 {} 状态恢复失败，但购物车价格已恢复", userId, voucherID);
                return R.success("已取消优惠券使用，恢复购物车原始价格");
            }
        } catch (Exception e) {
            log.error("取消优惠券使用过程中发生异常: userId={}, voucherID={}, error={}", 
                    userId, voucherID, e.getMessage(), e);
            return R.error("系统取消优惠券时出现错误: " + e.getMessage());
        }
    }
}
