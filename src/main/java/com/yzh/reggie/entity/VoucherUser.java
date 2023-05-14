package com.yzh.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

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

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
