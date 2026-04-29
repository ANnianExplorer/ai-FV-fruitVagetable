package com.yzh.fv.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户和优惠券关联
 *
 * @author : xiao
 * @since ： 2023/5/13 14:58
 */
@Data
public class VoucherUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    //优惠券id
    private Long voucherId;

    //用户id
    private Long userId;

    //该券是否使用（0没用，1用了）
    private Integer status;

    //是否是新用户（0不是，1是）
    private Integer checkv;
    
    //使用时间
    private LocalDateTime useTime;

    // 领取次数
    private Integer receivedCount;

    // 使用次数
    private Integer usedCount;
    
    //关联订单ID
    private Long orderId;
    
    //创建时间
    private LocalDateTime createTime;
    
    //更新时间
    private LocalDateTime updateTime;
    
    //版本号（用于乐观锁）
    private Integer version;
}
