package com.yzh.fv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzh.fv.entity.Category;

/**
 * @author 杨振华
 * @since 2023/1/12
 */
public interface CategoryService extends IService<Category> {
        public void remove(Long id);
}
