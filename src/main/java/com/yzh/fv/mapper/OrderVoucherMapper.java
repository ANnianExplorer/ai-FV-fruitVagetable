package com.yzh.fv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzh.fv.entity.OrderVoucher;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单优惠券关联表Mapper接口
 *
 * @author : xiao
 */
@Mapper
public interface OrderVoucherMapper extends BaseMapper<OrderVoucher> {
}