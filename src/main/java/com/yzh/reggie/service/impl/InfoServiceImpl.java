package com.yzh.reggie.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.reggie.entity.Info;
import com.yzh.reggie.mapper.InfoMapper;
import com.yzh.reggie.service.InfoService;
import org.springframework.stereotype.Service;

/**
 * @author : xiao
 * @since ： 2023/5/14 19:35
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {
}
