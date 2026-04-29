package com.yzh.fv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzh.fv.common.R;
import com.yzh.fv.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {

    public R<String> clean();

    R<String> updateCartPricesWithDiscount(Long userId, BigDecimal discountRate);

    R<String> restoreCartOriginalPrices(Long userId);

    List<ShoppingCart> getUserCartList(Long userId);
}
