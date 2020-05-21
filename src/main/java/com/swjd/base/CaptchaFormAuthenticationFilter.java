package com.swjd.base;

import com.alibaba.fastjson.JSONObject;
import com.swjd.util.Constants;
import com.swjd.util.RestResponse;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Description:自定义shiro表单拦截器
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/9 16:31
 * @Version：1.0
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);

    /**
     * 功能描述:解决跨域问题 放行OPTIONS请求
     * @Date: 21:41 2020/5/21
     * @Param: [request, response, mappedValue]
     * @return: boolean
     **/
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", Constants.ALLOWEDORIGINS);
        res.setHeader("Access-Control-Allow-Credentials","true");
        if(request instanceof HttpServletRequest){
            if(((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")){
                res.addHeader("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH");
                res.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");
                res.setStatus(HttpServletResponse.SC_OK);
                res.setCharacterEncoding("UTF-8");
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 功能描述:用户未登录返回信息
     * @Date: 21:47 2020/5/21
     * @Param: [request, response]
     * @return: boolean
     **/
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse)response;
        res.setHeader("Access-Control-Allow-Origin", Constants.ALLOWEDORIGINS);
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        RestResponse restResponse = RestResponse.failure("未登录").setCode(-1);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = res.getWriter();
        writer.write(JSONObject.toJSONString(restResponse));
        writer.close();
        return false;
    }
}
