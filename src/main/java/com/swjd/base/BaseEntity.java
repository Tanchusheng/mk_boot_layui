package com.swjd.base;

import com.baomidou.mybatisplus.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


import java.io.Serializable;

/**
 * @Description: 功能描述：entity基础类
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 13:50
 * @Version：1.0
 */
public abstract class BaseEntity <T extends Model> extends Model<T>{
    /*实体编号(唯一标志)*/
    protected Long id;

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this.id = id;
    }
    @JsonSerialize(using = ToStringSerializer.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj){
            return false;
        }
        if (this == obj){
            return true;
        }
        if (!getClass().equals(obj.getClass())){
            return false;
        }
        BaseEntity<?> that = (BaseEntity<?>) obj;
        return null != this.getId() && this.getId().equals(that.getId());
    }
}
