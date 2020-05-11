package com.swjd.base;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:自定义shiro表单拦截器
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/9 16:31
 * @Version：1.0
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);

}
