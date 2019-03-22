package com.songheng.dsp.partner.utils;

import com.songheng.dsp.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: TODO
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 17:05
 **/
public class RemoteIpUtil {

    private final static String UNKNOWN = "unknown";

    private final  static String SPLIT_SYMBOL = ",";

    private final static String LOCATION_IPS = "0:0:0:0:0:0:0:1,127.0.0.1,localhost";
    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     * @return ip
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = "";
        String[] headKeys = {"x-forwarded-for","Proxy-Client-IP","WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP","HTTP_X_FORWARDED_FOR","X-Real-IP"} ;
        for(String headKey : headKeys){
            ip = request.getHeader(headKey);
            if (StringUtils.isNotBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                // 多次反向代理后会有多个ip值，第一个ip才是真实ip
                if(ip.indexOf(SPLIT_SYMBOL)!=-1 ){
                    ip = ip.split(",")[0];
                }
                break;
            }
        }
        if(StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        ip = LOCATION_IPS.indexOf(ip) == -1 ? ip : "58.246.80.58";
        return ip;
    }
}
