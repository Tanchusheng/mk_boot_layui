package com.swjd.util;

/**
 * @Description:常量类
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 16:14
 * @Version：1.0
 */
public class Constants {
    /**
     * shiro采用加密算法
     */
    public static final String HASH_ALGORITHM = "SHA-1";

    /**
     * 生成Hash值的迭代次数
     */
    public static final int HASH_INTERATIONS = 1024;

    /**
     * 生成盐的长度
     */
    public static final int SALT_SIZE = 8;

    /**
     * 验证码
     */
    public static final String VALIDATE_CODE = "validateCode";

    /**
     * 是否开启语音提示验证码
     */
    public static final String ISVOICE = "ISVOICE";

    /**
     *系统用户默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 允许跨域请求路径
     */
    public static final String ALLOWEDORIGINS = "http://localhost:63343";


    /**
     * 百度ai设置APPID/AK/SK
     */
    public static final String BAIDDU_Voice_APP_ID = "15048426";
    /**
     * 百度ai设置APPID/AK/SK
     */
    public static final String BAIDDU_Voice_API_KEY = "HFDYHTFB6MnCtue60t5GDatk";
    /**
     * 百度ai设置APPID/AK/SK
     */
    public static final String BAIDDU_Voice_SECRET_KEY = "WugjoumTzIoZ6v0SvBfSaTGSvlxlwrVw";
}
