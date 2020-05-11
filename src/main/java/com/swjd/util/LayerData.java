package com.swjd.util;

import java.util.List;

/**
 * @Description:layui工具类
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 16:19
 * @Version：1.0
 */
public class LayerData<T> {
    private Integer code = 0;

    private Integer count;

    private List<T> data;

    private String msg = "";

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
