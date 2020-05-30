package com.swjd.config;


import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.swjd.base.SysUser;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @Description: mybatisplus自定义填充公共字段 ,即没有传的字段自动填充
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/11 9:47
 * @Version：1.0
 */
public class SysMetaObjectHandler extends MetaObjectHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void insertFill(MetaObject metaObject) {
        logger.info("正在调用该insert填充字段方法");
        Object createDate = getFieldValByName("createDate",metaObject);
        Object createId = getFieldValByName("createId",metaObject);
        Object updateDate = getFieldValByName("updateDate",metaObject);
        Object updateId = getFieldValByName("updateId",metaObject);


        if (null == createDate) {
            setFieldValByName("createDate", new Date(),metaObject);
        }
        if (null == createId) {
            if(SysUser.ShiroUser() != null) {
                setFieldValByName("createId", SysUser.id(), metaObject);
            }
        }
        if (null == updateDate) {
            setFieldValByName("updateDate", new Date(),metaObject);
        }
        if (null == updateId) {
            if(SysUser.ShiroUser() != null) {
                setFieldValByName("updateId", SysUser.id(), metaObject);
            }
        }
    }

    //更新填充
    @Override
    public void updateFill(MetaObject metaObject) {
        logger.info("正在调用该update填充字段方法");
        setFieldValByName("updateDate",new Date(), metaObject);
        Object updateId = getFieldValByName("updateId",metaObject);
        if (null == updateId) {
            setFieldValByName("updateId", SysUser.id(), metaObject);
        }
    }
}
