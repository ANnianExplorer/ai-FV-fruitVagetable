package com.yzh.fv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzh.fv.entity.Voucher;
import com.yzh.fv.dto.VoucherDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : xiao
 * @since ： 2023/5/13 15:07
 */
public interface VoucherServer extends IService<Voucher> {
    
    /**
     * 获取用户可用的优惠券列表
     * @param userId 用户ID
     * @return 优惠券DTO列表
     */
    List<VoucherDto> getUserAvailableVouchers(Long userId);
    
    /**
     * 获取所有启用状态的新人优惠券
     * @param now 当前时间
     * @return 新人优惠券列表
     */
    List<Voucher> getActiveNewUserVouchers(LocalDateTime now);
    
    /**
     * 验证优惠券是否有效
     * @param voucherId 优惠券ID
     * @param userId 用户ID
     * @return 验证结果信息
     */
    String validateVoucher(Long voucherId, Long userId);
}
