package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.fv.entity.VoucherUser;
import com.yzh.fv.mapper.VoucherUserMapper;
import com.yzh.fv.service.VoucherUserServer;
import org.springframework.stereotype.Service;

/**
 * @author : xiao
 * @since ： 2023/5/13 15:10
 */
@Service
public class VoucherUserServiceImpl extends ServiceImpl<VoucherUserMapper, VoucherUser> implements VoucherUserServer {
}
