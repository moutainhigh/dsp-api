package com.songheng.dsp.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: luoshaobing
 * @date: 2019/2/22 11:27
 * @description: 钉钉消息发送工具类
 */
public class MsgNotifyUtils {

    /**
     * 消息发送url
     */
    private static String sendMsgUrl = String.format(PropertyPlaceholder.getProperty("msg.url"),
            PropertyPlaceholder.getProperty("msg.token"));

    /**
     * 消息通知开关
     */
    private static boolean enableSendMsg  = "on".equalsIgnoreCase(PropertyPlaceholder.getProperty("msg.mswitch"))
            || "true".equalsIgnoreCase(PropertyPlaceholder.getProperty("msg.mswitch"));


    /**
     * 消息发送
     * @param content
     */
    public static void sendMsg(String content){
        if (StringUtils.isBlank(content)){
            return;
        }
        if(enableSendMsg) {
            try {
                JSONObject json = new JSONObject();
                JSONObject jsonContent = new JSONObject();
                JSONObject jsonAt = new JSONObject();
                json.put("msgtype", "text");
                json.put("text", jsonContent);
                json.put("at", jsonAt);
                jsonAt.put("isAtAll", false);
                jsonContent.put("content", content);
                HttpClientUtils.httpPost(sendMsgUrl, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
