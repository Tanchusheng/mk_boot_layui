package com.swjd.modules.system.controller;


import com.google.common.collect.Maps;
import com.swjd.util.Constants;
import com.swjd.util.VerifyCodeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:用户登录
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/11 10:11
 * @Version：1.0
 */
public class LoginController  {
    public static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Value("${server.port}")
    private String port;

    public void genCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //设置页面不缓存
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_ALL_MIXED,4 ,null);
        //将验证码放到httpSession里面
        request.getSession().setAttribute(Constants.VALIDATE_CODE,verifyCode);
        LOGGER.info("本次生成的验证码为["+verifyCode+"],已存放到HttpSession中");
        //设置输出的内容的类型为JPEG图像
        response.setContentType("image/jpeg");
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode,116,36, 5, true, new Color(249,205,173), null, null);
    }
}
