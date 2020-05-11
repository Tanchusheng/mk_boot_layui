package com.swjd.exception;

import com.alibaba.fastjson.JSONObject;
import com.swjd.util.RestResponse;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import freemarker.core.TemplateConfiguration;
import freemarker.template.TemplateModelException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * @Description:统一异常处理类
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 15:00
 * @Version：1.0
 */
@ControllerAdvice
public class GlobalExceptionHandle {

    private static final Log log = LogFactory.get();

    /*
    * 500 request
    */
    @ExceptionHandler({HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            TemplateModelException.class,
            SQLException.class
    })
    public void handleHttpMessageNotReadableException(HttpServletRequest request, HttpServletResponse response,Exception e){

        RestResponse restResponse=RestResponse.failure(e.getMessage());

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer= null;
        try {
            writer = response.getWriter();
            writer.write(JSONObject.toJSONString(restResponse));
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
