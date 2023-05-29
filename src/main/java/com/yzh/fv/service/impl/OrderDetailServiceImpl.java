package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yzh.fv.entity.OrderDetail;
import com.yzh.fv.mapper.OrderDetailMapper;
import com.yzh.fv.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}