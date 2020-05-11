package com.swjd.util;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import org.json.JSONException;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

import java.util.HashMap;

/**
 * @Description:百度AI工具类
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 16:27
 * @Version：1.0
 */
public class BaiduAiUtil {
    //文字转语音
    public static String textToSpeech(String text, HashMap<String, Object> options){
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(Constants.BAIDDU_Voice_APP_ID, Constants.BAIDDU_Voice_API_KEY, Constants.BAIDDU_Voice_SECRET_KEY);
        // 设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        TtsResponse res = client.synthesis(text, "zh", 1, options);
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        String result = null;
        if (data != null) {
            try {
                result =  BaiduAiUtil.synthesisToBase64(data);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return "语音提示失败";
        }
        if (res1 != null) {
            System.out.println(res1.toString(2));
        }
        return result;
    }

    public static String synthesisToBase64(byte[] context) throws JSONException {
        return new BASE64Encoder().encode(context);
    }
}
