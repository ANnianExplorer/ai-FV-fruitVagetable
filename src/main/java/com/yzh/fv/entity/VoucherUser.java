package com.yzh.fv.entity;

import lombok.Data;

/**
 * 用户和优惠券关联
 *
 * @author : xiao
 * @since ： 2023/5/13 14:58
 */
@Data
public class VoucherUser {
    private static final long serialVersionUID = 1L;

    private Long id;

    //优惠券id
    private Long voucherId;

    //用户id
    private Long userId;

    //该券是否使用（0没用，1用了）
    private Integer status;
}
