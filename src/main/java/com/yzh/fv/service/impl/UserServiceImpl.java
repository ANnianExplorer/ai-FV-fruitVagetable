package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.fv.entity.User;
import com.yzh.fv.mapper.UserMapper;
import com.yzh.fv.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 杨振华
 * @since 2023/1/15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
