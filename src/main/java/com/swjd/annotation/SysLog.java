package com.swjd.annotation;

import java.lang.annotation.*;

/**
 * @Description: 系统日志注解
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/28 16:02
 * @Version：1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
