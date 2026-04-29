package com.yzh.fv.service;

import com.yzh.fv.dto.DataScreenDto;

/**
 * 数据大屏服务接口
 */
public interface DataScreenService {
    
    /**
     * 获取数据大屏数据
     * @return 数据大屏数据DTO
     */
    DataScreenDto getDataScreenInfo();
}