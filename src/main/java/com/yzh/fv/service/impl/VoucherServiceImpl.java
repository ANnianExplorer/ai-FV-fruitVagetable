package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.fv.entity.Voucher;
import com.yzh.fv.dto.VoucherDto;
import com.yzh.fv.entity.VoucherUser;
import com.yzh.fv.mapper.VoucherMapper;
import com.yzh.fv.service.VoucherServer;
import com.yzh.fv.service.VoucherUserServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : xiao
 * @date : 2022/11/28 10:59
 */
@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher> implements VoucherServer {
    
    @Autowired
    private VoucherUserServer voucherUserServer;
    
    @Override
    public List<VoucherDto> getUserAvailableVouchers(Long userId) {
        // 查询用户所有未使用的优惠券记录
        LambdaQueryWrapper<VoucherUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VoucherUser::getUserId, userId)
               .eq(VoucherUser::getStatus, 0); // 0表示未使用
        
        List<VoucherUser> voucherUsers = voucherUserServer.list(wrapper);
        if (CollectionUtils.isEmpty(voucherUsers)) {
            return new ArrayList<>();
        }
        
        // 提取优惠券ID列表
        List<Long> voucherIds = voucherUsers.stream()
                .map(VoucherUser::getVoucherId)
                .filter(id -> id != null && id > 0)
                .collect(Collectors.toList());
        
        if (CollectionUtils.isEmpty(voucherIds)) {
            return new ArrayList<>();
        }
        
        // 查询优惠券详情
        List<Voucher> vouchers = this.listByIds(voucherIds);
        LocalDateTime now = LocalDateTime.now();
        
        // 转换为DTO并过滤有效优惠券
        return vouchers.stream()
                .filter(voucher -> isVoucherValid(voucher, now))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Voucher> getActiveNewUserVouchers(LocalDateTime now) {
        LambdaQueryWrapper<Voucher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Voucher::getType, 0) // 新人优惠券
               .eq(Voucher::getStatus, 1) // 启用状态
               .le(Voucher::getStartTime, now) // 开始时间不晚于当前时间
               .ge(Voucher::getEndTime, now); // 结束时间不早于当前时间
        
        return this.list(wrapper);
    }
    
    @Override
    public String validateVoucher(Long voucherId, Long userId) {
        // 验证优惠券是否存在
        Voucher voucher = this.getById(voucherId);
        if (voucher == null) {
            return "优惠券不存在";
        }
        
        // 验证优惠券状态
        if (voucher.getStatus() != 1) {
            return "优惠券未激活";
        }
        
        // 验证优惠券是否过期
        LocalDateTime now = LocalDateTime.now();
        if (!isVoucherValid(voucher, now)) {
            return "优惠券不在有效期内";
        }
        
        // 验证用户是否拥有该优惠券
        VoucherUser voucherUser = voucherUserServer.checkUserHasVoucher(userId, voucherId);
        if (voucherUser == null) {
            return "您未拥有该优惠券";
        }
        
        // 验证优惠券是否已使用
        if (voucherUser.getStatus() != 0) {
            return "该优惠券已被使用";
        }
        
        return null; // 验证通过
    }
    
    /**
     * 检查优惠券是否在有效期内
     */
    private boolean isVoucherValid(Voucher voucher, LocalDateTime now) {
        return voucher.getStartTime() != null && voucher.getEndTime() != null
                && !now.isBefore(voucher.getStartTime()) && !now.isAfter(voucher.getEndTime());
    }
    
    /**
     * 将Voucher转换为VoucherDto
     */
    private VoucherDto convertToDto(Voucher voucher) {
        VoucherDto dto = new VoucherDto();
        BeanUtils.copyProperties(voucher, dto);
        dto.setId(voucher.getId());
        dto.setName(voucher.getName());
        dto.setPrice(voucher.getPrice());
        dto.setImage(voucher.getImage());
        dto.setDescription(voucher.getDescription());
        return dto;
    }
}
