package com.yzh.fv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzh.fv.common.R;
import com.yzh.fv.entity.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {

    public R<String> clean();
}
