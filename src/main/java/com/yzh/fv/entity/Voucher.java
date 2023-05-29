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

    //优惠券状态（0启用，1停用）
    private Integer status;

    //优惠券创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //优惠券创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    //使用填充下面这两个如果不写不知道为什么报错
    //TODO 肯定错啊，MyMetaObjecthandler里面设置了四个都得有，你用就得都有
    @TableField(fill = FieldFill.INSERT)
    public LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private Long updateUser;

}
