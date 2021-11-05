package com.yyy.blog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
* 拦截器
* */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //判断是否登录
        if (request.getSession().getAttribute("user")==null){
            // 如果未登录，重定向到登陆页面
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
