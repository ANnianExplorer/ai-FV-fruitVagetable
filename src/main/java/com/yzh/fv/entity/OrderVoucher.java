package com.yzh.fv.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单优惠券关联表实体类
 *
 * @author : xiao
 * @since ： 2023/5/13 14:58
 */
@Data
public class OrderVoucher implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    //订单ID
    private Long orderId;

    //优惠券ID
    private Long voucherId;

    //优惠金额
    private BigDecimal discountAmount;

    // 用户ID
    private Long userId;

    //创建时间
    private LocalDateTime createTime;

    //更新时间
    private LocalDateTime updateTime;
}