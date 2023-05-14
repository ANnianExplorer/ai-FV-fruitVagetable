package com.yzh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.reggie.entity.Voucher;
import com.yzh.reggie.mapper.VoucherMapper;
import com.yzh.reggie.service.VoucherServer;
import org.springframework.stereotype.Service;

/**
 * @author : xiao
 * @since ： 2023/5/13 15:09
 */
@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher> implements VoucherServer {
}
