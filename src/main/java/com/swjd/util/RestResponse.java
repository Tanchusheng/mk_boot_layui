package com.swjd.util;

import java.util.HashMap;

/**
 * @Description: ResponseBody 构造器 一般用于ajax 。rest 等类型的web服务
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 15:10
 * @Version：1.0
 */
public class RestResponse extends HashMap<String, Object> {

    public static RestResponse success(){
        return success("成功");
    }



    public static RestResponse success(String message){
        RestResponse restResponse=new RestResponse();
        restResponse.setSuccess(true);
        restResponse.setMessage(message);
        restResponse.setCode(0);
        return restResponse;
    }

    public static RestResponse failure(String message){
        RestResponse restResponse=new RestResponse();
        restResponse.setSuccess(false);
        restResponse.setMessage(message);
        restResponse.setCode(0);
        return restResponse;
    }

    private RestResponse setSuccess(Boolean success) {
        if (success != null) put("success",success);
        return this;
    }

    private RestResponse setMessage(String message) {
        if (message != null) put("message",message);
        return this;
    }

    public RestResponse setData(Object data){
        if (data != null) put("data",data);
        return this;
    }

    public RestResponse setCode(Object code){
        if (code != null) put("code",code);
        return this;
    }

    public RestResponse setPage(Object page){
        if (page != null) put("page",page);
        return this;
    }

    public RestResponse setCurrentPage(Integer currentPage){
        if (currentPage != null) put("currentPage",currentPage);
        return this;
    }

    public RestResponse setLimit(Integer limit){
        if (limit != null) put("limit",limit);
        return this;
    }

    public RestResponse setTotal(Long total){
        if (total != null) put("total",total);
        return this;
    }
}
