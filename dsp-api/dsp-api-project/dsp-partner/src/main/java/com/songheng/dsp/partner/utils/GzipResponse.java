package com.songheng.dsp.partner.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

/**
 * @description: 对返回的数据进行zip压缩
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 19:22
 **/
public class GzipResponse {

    /**
     * 压缩返回接口请求数据
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data String
     */
    public static void gzipPrint(HttpServletRequest request, HttpServletResponse response, String data){
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            if (isSupported(request)){
                //协议： 告知客户端使用gzip压缩方式
                response.setHeader("Content-Encoding", "gzip");
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        new GZIPOutputStream(response.getOutputStream(), 1024), "UTF-8")));
            } else {
                out = response.getWriter();
            }
            out.print(data);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != out){
                out.close();
            }
        }
    }
    /**
     * 检测客户端浏览器是否支持gzip的编码方式
     * @param request HttpServletRequest
     * @return boolean
     */
    private static boolean isSupported(HttpServletRequest request) {
        String encodingString = request.getHeader("Accept-Encoding");
        return (null != encodingString) && (encodingString.indexOf("gzip") != -1);
    }
}
