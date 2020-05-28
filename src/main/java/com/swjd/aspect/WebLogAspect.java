package com.swjd.aspect;


import com.alibaba.fastjson.JSONObject;
import com.swjd.annotation.SysLog;
import com.swjd.base.SysUser;
import com.swjd.exception.GlobalExceptionHandle;
import com.swjd.modules.system.entity.Log;
import com.swjd.util.ToolUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description: 自定义日志切面
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/28 16:03
 * @Version：1.0
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect {

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Autowired
    private GlobalExceptionHandle exceptionHandle;


    private Log sysLog = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("@annotation(com.swjd.annotation.SysLog)")
    public void webLog(){}
    /**
     * 前置通知，在方法执行之前执行
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        startTime.set(System.currentTimeMillis());
        //接收到请求，记录请求的内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = (HttpSession) attributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        sysLog = new Log();

        sysLog.setClassMethod(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        sysLog.setHttpMethod(request.getMethod());
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for(int i = 0;i<args.length;i++){
            Object o = args[i];
            if(o instanceof ServletRequest ||(o instanceof ServletResponse)|| o instanceof MultipartFile){
                args[i] = o.toString();
            }
        }
        String str = JSONObject.toJSONString(args);
        sysLog.setParams(str.length()>5000?JSONObject.toJSONString("请求参数数据过长不与显示"):str);

        String ip = ToolUtil.getClientIp(request);
        if("0.0.0.0".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "localhost".equals(ip) || "127.0.0.1".equals(ip)){
            ip = "127.0.0.1";
        }

        sysLog.setRemoteAddr(ip);
        sysLog.setRequestUri(request.getRequestURL().toString());
        if(session != null){
            sysLog.setSessionId(session.getId());
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog mylog = method.getAnnotation(com.swjd.annotation.SysLog.class);
        if(mylog != null){
            //注解上的描述
            sysLog.setTitle(mylog.value());
        }

        Map<String,String> browserMap = ToolUtil.getOsAndBrowserInfo(request);
        sysLog.setBrowser(browserMap.get("os")+"-"+browserMap.get("browser"));

        //根据Ip地址获取归属地（测试这个功能，必须要放到外网，要调用第三方归属地的接口）
        if(!"127.0.0.1".equals(ip)){
            Map<String,Object> map = ToolUtil.getAddressFromAliyunByIP(ToolUtil.getClientIp(request));
            if ((Integer)(map.get("ret")) == 200){
                Map<String, Object>dataMap = (Map<String, Object>) map.get("data");
                sysLog.setArea(dataMap.get("country").toString());
                sysLog.setProvince(dataMap.get("prov").toString());
                sysLog.setCity(dataMap.get("city").toString());
                sysLog.setIsp(dataMap.get("isp").toString());
                LOGGER.info("国家："+dataMap.get("country"));
                LOGGER.info("省："+dataMap.get("prov"));
                LOGGER.info("市："+dataMap.get("city"));
                LOGGER.info("互联网服务提供商："+dataMap.get("isp"));


            }
        }

        sysLog.setType(ToolUtil.isAjax(request)?"Ajax请求":"普通请求");
        if(SysUser.ShiroUser() != null) {
            sysLog.setUsername(StringUtils.isNotBlank(SysUser.nickName()) ? SysUser.nickName() : SysUser.loginName());
        }

    }

    /**
     * 返回通知, 在方法返回结果之后执行
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {

        if(SysUser.ShiroUser() != null) {
            sysLog.setUsername(StringUtils.isNotBlank(SysUser.nickName()) ? SysUser.nickName() : SysUser.loginName());
        }
        String retString = JSONObject.toJSONString(ret);
        sysLog.setResponse(retString.length()>5000?JSONObject.toJSONString("请求参数数据过长不与显示"):retString);
        sysLog.setUseTime(System.currentTimeMillis() - startTime.get());
        sysLog.insert();
    }

    /**
     * 环绕通知, 围绕着方法执行
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        try {
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            sysLog.setException(e.getMessage());
            throw e;
        }
    }
}
