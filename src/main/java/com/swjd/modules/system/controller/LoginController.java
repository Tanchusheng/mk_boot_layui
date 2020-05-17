package com.swjd.modules.system.controller;


import com.google.common.collect.Maps;
import com.swjd.util.BaiduAiUtil;
import com.swjd.util.Constants;
import com.swjd.util.RestResponse;
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
@Controller
@RequestMapping("/api")
public class LoginController  {
    public static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Value("${server.port}")
    private String port;

    @GetMapping("/genCaptcha")
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
        //写给浏览器
        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
    }

    /**
     * 功能描述：验证码语音提示
     * @param request
     * @return
     */
    @GetMapping("/getCaptchaVoice")
    @ResponseBody
    public RestResponse getCaptchaVoice(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(null==session){
            return RestResponse.failure("session超时");
        }

        String trueCode = (String)session.getAttribute(Constants.VALIDATE_CODE);
        LOGGER.info("从HttpSession中获取验证码[" + trueCode + "]");
        if(null==trueCode){
            return RestResponse.failure("获取语音提示失败");
        }else{
            //根据验证码生成语音，百度ai技术（文字转语音）
            HashMap<String,Object> options = new HashMap<>();
            options.put("spd","1");//语速取值0-9，默认为5语速
            options.put("pit","5");//音调，取值0-9，默认为5中语调
            options.put("per","0");//发音人选择, 0为女声，1为男声，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女
            String vocie = BaiduAiUtil.textToSpeech(trueCode,options);
            return RestResponse.success().setData(vocie);
        }
    }

    /**
     * 功能描述:通过账号和密码登录系统
     * @Date: 21:40 2020/5/11
     * @Param: [request]
     * @return: com.swjd.util.RestResponse
     **/
    @RequestMapping(value = "/login/main",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    //@SysLog("用户登录")
    public RestResponse loginMain(HttpServletRequest request){
        LOGGER.info("用户登录------login/main:0");
        //用户名称
        String username = request.getParameter("username");
        //用户登录密码
        String password = request.getParameter("password");
        //记住我
        String rememberMe = request.getParameter("rememberMe");
        //验证码
        String code = request.getParameter("captcha");

        Boolean isVoice = "true".equals(request.getParameter("isVoice"));

        Map<String,Object> map = new HashMap<>();
        String error = null;
        String textMsg = "登录成功正在跳转";

        //判断用户名或密码是否为空
        if(StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            return RestResponse.failure("用户名或密码不能为空");
        }

        if(StringUtils.isBlank(rememberMe)){
            return RestResponse.failure("记住我不能为空");
        }
        if(StringUtils.isBlank(code)){
            return RestResponse.failure("验证码不能为空");
        }

        HttpSession session = request.getSession();
        if(null==session){
            return RestResponse.failure("session超时");
        }
        String trueCode = (String)session.getAttribute(Constants.VALIDATE_CODE);
        if(StringUtils.isBlank(trueCode)){
            return RestResponse.failure("验证码超时");
        }

        //判断用户输入的验证码跟保存到session里的验证码是否相等
        if(!trueCode.toLowerCase().equals(code.toLowerCase())){
            error = "验证码错误";
        }else {
            Subject user = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username,password,Boolean.valueOf(rememberMe));
            try {
                user.login(token);
                if(user.isAuthenticated()){
                    map.put("user",user.getPrincipal());
                }
            }catch (IncorrectCredentialsException e){
                error = "登录密码错误.";
            }catch (ExcessiveAttemptsException e){
                error = "登录失败次数过多";
            }catch (LockedAccountException e){
                error = "帐号已被锁定.";
            }catch (DisabledAccountException e){
                error = "帐号已被禁用.";
            }catch (ExpiredCredentialsException e){
                error = "帐号已过期.";
            }catch (UnknownAccountException e){
                error = "帐号不存在";
            }catch (UnauthorizedException e){
                error = "您没有得到相应的授权！";
            }
        }
        //根据验提示信息生成语音
        HashMap<String,Object> options = new HashMap<>();
        options.put("spd", "5");
        options.put("pit", "5");
        options.put("per", "0");
        request.getSession().setAttribute(Constants.ISVOICE, isVoice);
        String result=null;
        if(StringUtils.isBlank(error)){
            if(isVoice){//开启语音提示
                result = BaiduAiUtil.textToSpeech(textMsg,options);
            }
            map.put("voice",result);
            return RestResponse.success("登录成功").setData(map);
        }else {
            if(isVoice){//开启语音提示
                result = BaiduAiUtil.textToSpeech(error,options);
            }
            map.put("voice",result);
            return RestResponse.failure(error).setData(map);
        }
    }

    /**
     * 功能描述:退出系统
     * @Date: 22:49 2020/5/16
     * @Param: []
     * @return: com.swjd.util.RestResponse
     **/
    @RequestMapping("/systemLogout")
    @ResponseBody
    public RestResponse systemLogout(){
        LOGGER.info("用户退出系统");
        SecurityUtils.getSubject().logout();
        return RestResponse.success("退出成功").setCode(0);
    }
}
