package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.fv.entity.Employee;
import com.yzh.fv.mapper.EmployeeMapper;
import com.yzh.fv.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * Employee接口
 *
 * @author 杨振华
 * @since 2023/1/9
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
