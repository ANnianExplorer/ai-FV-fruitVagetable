package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.fv.entity.OrderVoucher;
import com.yzh.fv.mapper.OrderVoucherMapper;
import com.yzh.fv.service.OrderVoucherService;
import org.springframework.stereotype.Service;

/**
 * 订单优惠券关联表Service实现类
 *
 * @author : xiao
 */
@Service
public class OrderVoucherServiceImpl extends ServiceImpl<OrderVoucherMapper, OrderVoucher> implements OrderVoucherService {
}