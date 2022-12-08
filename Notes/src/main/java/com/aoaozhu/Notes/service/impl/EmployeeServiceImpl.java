package com.aoaozhu.Notes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aoaozhu.Notes.entity.Employee;
import com.aoaozhu.Notes.mapper.EmployeeMapper;
import com.aoaozhu.Notes.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
