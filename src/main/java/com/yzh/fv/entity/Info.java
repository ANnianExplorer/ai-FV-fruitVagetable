package com.yzh.fv.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : xiao
 * @since ： 2023/5/14 19:30
 */
@Data
public class Info {
    private static final long serialVersionUID = 1L;

    private Long id;

    //通知标题
    private String name;

    //通知内容
    private String text;

    //优惠券创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //优惠券创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    //使用填充下面这两个如果不写不知道为什么报错
    @TableField(exist = false,fill = FieldFill.INSERT)
    private LocalDateTime updateTime;
    @TableField(exist = false,fill = FieldFill.INSERT)
    private Long updateUser;
}
