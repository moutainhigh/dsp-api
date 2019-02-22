package com.songheng.dsp.common.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author: luoshaobing
 * @date: 2019/2/22 11:23
 * @description: HostIpUtils
 */
public class HostIpUtils {

    /**
     * 获取服务器地址
     * @return serverIp
     */
    public static String getServerIp() {
        try {
            Enumeration<?> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<?> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    String intranetIP;
                    if (ip != null && ip instanceof Inet4Address &&
                            (intranetIP = ip.getHostAddress()).indexOf("127.0.0.1") == -1) {
                        return intranetIP;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }
}
