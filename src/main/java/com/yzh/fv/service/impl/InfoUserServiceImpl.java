package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.fv.entity.InfoUser;
import com.yzh.fv.mapper.InfoUserMapper;
import com.yzh.fv.service.InfoUserService;
import org.springframework.stereotype.Service;

/**
 * @author 杨振华
 * @since 2023/5/31
 */
@Service
public class InfoUserServiceImpl extends ServiceImpl<InfoUserMapper, InfoUser> implements InfoUserService {
}