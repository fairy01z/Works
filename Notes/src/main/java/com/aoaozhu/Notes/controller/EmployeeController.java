package com.aoaozhu.Notes.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.aoaozhu.Notes.common.R;
import com.aoaozhu.Notes.entity.Employee;
import com.aoaozhu.Notes.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //1.将页面提交的密码Password进行加密处理
        String password=employee.getPassword();
       // password= DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的username查询库
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp=employeeService.getOne(queryWrapper);//做了唯一约束，所以是one

        //3.如果为空返回失败
        if(emp==null){
            return R.error("该用户不存在");
        }
        //4.密码比对失败

        if(!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }

        //查看是否禁用
        if(emp.getStatus()==0){
            return R.error("已禁用");
        }

        //登录成功，将员工id存入Session并返回登录结果
        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
         request.getSession().removeAttribute("employee");
         return R.success("退出成功");
    }
    //根据id修改员工信息
    @PutMapping("/second/{id}")
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee,@PathVariable Long id){
        log.info(employee.toString());

        employeeService.updateById(employee);
        return R.success("信息修改成功");
    }

    @GetMapping("/{id}")//@PathVariable Long id
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息。。。");
        //Long empId=(Long)request.getSession().getAttribute("employee");
        Employee employee=employeeService.getById(id);
        System.out.println(id);
        if(employee!=null){
        return R.success(employee);}
        return R.error("no found");
    }

    //注册
    @PostMapping("/logon")
    public R<String> Logon(HttpServletRequest request, @RequestBody Employee employee){
        String username=employee.getUsername();
        //String password=employee.getPassword();

        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        int count=employeeService.count();
        int ID=count++;
        Long id=(long)ID;
        employee.setId(id);
        Employee emp=employeeService.getOne(queryWrapper);

        if(emp==null){
            employeeService.save(employee);
            System.out.println(employee.getId());
            request.getSession().setAttribute("employee",employee.getId());
            return  R.success("注册成功");
        }else return R.error("用户名已存在");
    }
}
