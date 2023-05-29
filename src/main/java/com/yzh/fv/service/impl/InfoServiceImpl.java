package com.yzh.fv.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.fv.entity.Info;
import com.yzh.fv.mapper.InfoMapper;
import com.yzh.fv.service.InfoService;
import org.springframework.stereotype.Service;

/**
 * @author : xiao
 * @since ： 2023/5/14 19:35
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {
}
