package com.yzh.fv.dto;

import com.yzh.fv.entity.Voucher;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 杨振华
 * @since 2023/5/22
 */
@Data
public class VoucherDto extends Voucher {

    private Long id;

    //优惠券名称
    private String name;

    //优惠券价格
    private BigDecimal price;

    //优惠券图片
    private String image;

    //优惠券描述
    private String description;

}

