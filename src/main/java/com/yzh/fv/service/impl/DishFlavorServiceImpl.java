package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.fv.entity.DishFlavor;
import com.yzh.fv.mapper.DishFlavorMapper;
import com.yzh.fv.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author 杨振华
 * @since 2023/1/13
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
