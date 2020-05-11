package com.swjd.util;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.swjd.modules.system.entity.User;
import com.xiaoleilu.hutool.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 16:20
 * @Version：1.0
 */
public class ToolUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(ToolUtil.class);

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    public static void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(Constants.SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));
        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, Constants.HASH_INTERATIONS);
            user.setPassword(Encodes.encodeHex(hashPassword));
    }

    /**
     *
     * @param paramStr 输入需要加密的字符串
     * @return
     */
    public static String entryptPassword(String paramStr,String salt) {
        if(StringUtils.isNotEmpty(paramStr)){
            byte[] saltStr = Encodes.decodeHex(salt);
            byte[] hashPassword = Digests.sha1(paramStr.getBytes(), saltStr, Constants.HASH_INTERATIONS);
            String password = Encodes.encodeHex(hashPassword);
            return password;
        }else{
            return null;
        }

    }

    /**
     * 获取客户端的ip信息
     *
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        LOGGER.info("ipadd : " + ip);
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        LOGGER.info(" ip --> " + ip);
        return ip;
    }

    /**
     * 判断请求是否是ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request){
        String accept = request.getHeader("accept");
        return accept != null && accept.contains("application/json") || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest"));
    }

    /**
     * 获取操作系统,浏览器及浏览器版本信息
     * @param request
     * @return
     */
    public static Map<String,String> getOsAndBrowserInfo(HttpServletRequest request){
        Map<String,String> map = Maps.newHashMap();
        String  browserDetails  =   request.getHeader("User-Agent");
        String  userAgent       =   browserDetails;
        String  user            =   userAgent.toLowerCase();

        String os = "";
        String browser = "";

        //=================OS Info=======================
        if (userAgent.toLowerCase().contains("windows"))
        {
            os = "Windows";
        } else if(userAgent.toLowerCase().contains("mac"))
        {
            os = "Mac";
        } else if(userAgent.toLowerCase().contains("x11"))
        {
            os = "Unix";
        } else if(userAgent.toLowerCase().contains("android"))
        {
            os = "Android";
        } else if(userAgent.toLowerCase().contains("iphone"))
        {
            os = "IPhone";
        }else{
            os = "UnKnown, More-Info: "+userAgent;
        }
        //===============Browser===========================
        if (user.contains("edge"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("msie"))
        {
            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
                    + "-" +(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if ( user.contains("opr") || user.contains("opera"))
        {
            if(user.contains("opera")){
                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
                        +"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            }else if(user.contains("opr")){
                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
                        .replace("OPR", "Opera");
            }

        } else if (user.contains("chrome"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6"))  ||
                (user.contains("mozilla/4.7")) || (user.contains("mozilla/4.78")) ||
                (user.contains("mozilla/4.08")) || (user.contains("mozilla/3")) )
        {
            browser = "Netscape-?";

        } else if (user.contains("firefox"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if(user.contains("rv"))
        {
            String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
            browser="IE" + IEVersion.substring(0,IEVersion.length() - 1);
        } else
        {
            browser = "UnKnown, More-Info: "+userAgent;
        }
        map.put("os",os);
        map.put("browser",browser);
        return map;
    }

    public static Map<String,Object> getAddressByIP(String ip) {
        LOGGER.info("根据ip获取地址----getAddressByIP----0");
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "0.0.0.0";
        }
        Map<String,Object> map = Maps.newHashMap();
        //StringBuilder sb = new StringBuilder("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=");
        StringBuilder sb = new StringBuilder("http://ip.taobao.com/service/getIpInfo.php?ip=");
        sb.append(ip);
        LOGGER.info("根据ip获取地址----getAddressByIP----1");
        String result= HttpUtil.get(sb.toString(), "UTF-8");
        LOGGER.info("根据ip获取地址----getAddressByIP----2");
        LOGGER.info(result);
        Map resultMap = JSON.parseObject(result,Map.class);
        return resultMap;
    }

    public static Map<String,Object> getAddressFromAliyunByIP(String ip) {
        LOGGER.info("阿里云接口根据ip获取地址----getAddressByIP----0");
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "0.0.0.0";
        }
        Map<String,Object> map = Maps.newHashMap();

        String host = "https://ipquery.market.alicloudapi.com";
        String path = "/query";
        String method = "GET";
        String appcode = "ab58659aca0b48abb233551479f839a9";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ip", ip);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            Map resultMap = JSON.parseObject(EntityUtils.toString(response.getEntity()),Map.class);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }
}
