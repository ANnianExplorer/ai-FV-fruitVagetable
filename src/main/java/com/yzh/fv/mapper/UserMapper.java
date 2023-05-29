package com.yzh.fv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzh.fv.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 杨振华
 * @since 2023/1/15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
