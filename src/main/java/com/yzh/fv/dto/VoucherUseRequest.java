package com.yzh.fv.dto;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 优惠券使用请求参数
 */
@Data
public class VoucherUseRequest {
    
    /**
     * 优惠券ID
     * 不能为null且必须大于0
     */
    @NotNull(message = "优惠券ID不能为空")
    @Min(value = 1, message = "优惠券ID无效")
    private Long voucherID;
    
    /**
     * 优惠券状态
     * null时默认为1（已使用状态）
     */
    private Long couponStatus;

    // 
    private Long orderId;
}