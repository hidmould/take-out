package com.example.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.takeout.common.R;
import com.example.takeout.entity.Employee;
import com.example.takeout.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 1.将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2.根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // 3.如果没有查询到则返回登录失败结果
        if (emp== null){
            return R.error("登录失败");
        }

        // 4.密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }

        // 5.查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus()==0){
            return R.error("账号已禁用");
        }

        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

}
