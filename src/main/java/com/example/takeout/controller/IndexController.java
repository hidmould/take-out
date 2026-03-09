package com.example.takeout.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * 处理根路径映射的控制器
 */
@Controller
@Slf4j
public class IndexController {

    /**
     * 默认访问根路径时重定向到登录页面或首页
     * - 如果已登录，跳转到管理端首页
     * - 如果未登录，跳转到登录页面
     */
    @RequestMapping("/")
    public void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 检查用户是否已登录
        Long employeeId = (Long) request.getSession().getAttribute("employee");
        
        if (employeeId != null) {
            log.info("用户已登录，id={}, 重定向到管理端首页", employeeId);
            response.sendRedirect("/backend/index.html");
        } else {
            log.info("用户未登录，重定向到登录页面");
            response.sendRedirect("/backend/page/login/login.html");
        }
    }
}
