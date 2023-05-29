package com.yzh.fv.dto;

import com.yzh.fv.entity.OrderDetail;
import com.yzh.fv.entity.Orders;
import lombok.Data;

import java.util.List;

/**
 * @author 杨振华
 * @since 2023/1/16
 */
@Data
public class OrdersDto extends Orders {
    private List<OrderDetail> orderDetails;

    private int sumNum;
}
