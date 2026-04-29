package com.yzh.fv.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户评价实体类
 */
@Data
public class Evaluate implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 主键id
    private Long id;
    
    // 用户id
    private Long userId;
    
    // 订单id
    private Long orderId;
    
    // 评价类型：1-菜品评价 2-套餐评价
    private Integer type;
    
    // 评价对象id（dishId或setmealId）
    private Long targetId;
    
    // 评价对象名称（菜品或套餐名称）
    private String targetName;
    
    // 评分(1-5星)
    private Integer rating;
    
    // 评价内容
    private String content;
    
    // 评价图片
    private String images;
    
    // 评价时间
    private LocalDateTime evaluateTime;
    
    // 回复内容
    private String reply;
    
    // 回复时间
    private LocalDateTime replyTime;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    
    // 是否删除
    private Integer isDeleted;
}