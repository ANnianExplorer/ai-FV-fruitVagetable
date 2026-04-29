package com.yzh.fv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzh.fv.entity.VoucherUser;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author : xiao
 * @since ： 2023/5/13 15:08
 */
public interface VoucherUserServer extends IService<VoucherUser> {
    
    /**
     * 批量发放优惠券给用户
     * @param userIds 用户ID列表
     * @param voucherId 优惠券ID
     * @param checkv 是否为新用户标识
     * @param now 当前时间
     * @return 成功发放的数量
     */
    int batchIssueVouchersToUsers(List<Long> userIds, Long voucherId, int checkv, LocalDateTime now);
    
    /**
     * 为新用户发放新人优惠券
     * @param userId 用户ID
     * @return 成功发放的优惠券数量
     */
    int issueNewUserCoupons(Long userId);
    
    /**
     * 使用优惠券（乐观锁）
     * @param voucherId 优惠券ID
     * @param userId 用户ID
     * @param orderId 订单ID（可选）
     * @return 是否使用成功
     */
    boolean useCouponWithLock(Long voucherId, Long userId, Long orderId);
    
    /**
     * 检查用户是否拥有指定优惠券
     * @param userId 用户ID
     * @param voucherId 优惠券ID
     * @return 优惠券用户关联记录
     */
    VoucherUser checkUserHasVoucher(Long userId, Long voucherId);

    @Transactional(rollbackFor = Exception.class)
    boolean cancelCoupon(Long voucherId, Long userId);

    List<VoucherUser> getUserAvailableVouchers(Long userId);
}
