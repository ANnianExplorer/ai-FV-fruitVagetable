package com.yzh.fv.entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券类
 * 优惠券一旦创建除状态外不可修改（目前能力有限）
 * @author : xiao
 * @since ： 2023/5/13 14:42
 */
@Data
public class Voucher implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    //优惠券名称
    private String name;

    //优惠券价格
    private BigDecimal price;

    //优惠券图片
    private String image;

    //优惠券描述
    private String description;

    //优惠券类型（可以为空）
    private Integer type;
    
    //最低消费金额
    private BigDecimal minMoney;

    //优惠券状态（0启用，1停用）
    private Integer status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    //优惠券创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //优惠券创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    //更新时间在创建和更新时都需要填充
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    //更新人在创建和更新时都需要填充
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
