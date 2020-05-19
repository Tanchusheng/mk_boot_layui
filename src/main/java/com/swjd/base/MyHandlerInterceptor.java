package com.swjd.base;


import com.swjd.modules.system.entity.User;
import com.swjd.modules.system.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:自定义拦截器
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/11 9:42
 * @Version：1.0
 */
public class MyHandlerInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MyHandlerInterceptor.class);
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        if ( userService == null) {//解决service为null无法注入问题
            System.out.println("siteService is null!!!");
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
            userService = (UserService) factory.getBean("userService");

        }
        User user = userService.queryUserById(SysUser.id());
        if(user != null){
            httpServletRequest.setAttribute("currentUser",user);
            return true;
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }
}
