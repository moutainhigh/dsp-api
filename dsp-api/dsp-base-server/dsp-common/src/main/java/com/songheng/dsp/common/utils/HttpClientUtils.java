package com.songheng.dsp.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * @author: luoshaobing
 * @date: 2019/1/25 16:15
 * @description: HttpClient工具类
 */
@Slf4j
public class HttpClientUtils {

    /**
     * 请求参数配置
     */
    private static RequestConfig requestConfig = null;

    /**
     * CHARSET
     */
    private static final String CHARSET_ENCODING = "UTF-8";


    static {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.
                custom()
                //socket time out
                .setSocketTimeout(Integer.parseInt(com.songheng.dsp.common.utils.StringUtils.replaceInvalidString(
                        PropertyPlaceholder.getProperty("httpclient.socket.timeout"),"5000")))
                // connect time out
                .setConnectTimeout(Integer.parseInt(com.songheng.dsp.common.utils.StringUtils.replaceInvalidString(
                        PropertyPlaceholder.getProperty("httpclient.connect.timeout"),"5000")))
                .build();
    }

    /**
     * 获取httpClient
     * @return
     */
    public static CloseableHttpClient getHttpClient(){
        return HttpClients.createDefault();
    }


    /**
     * post请求传输json参数
     * @param url  url地址
     * @param jsonParam 参数
     * @return
     */
    public static String httpPost(String url, JSONObject jsonParam) {

        CloseableHttpClient httpClient = getHttpClient();
        // post请求返回结果
        String strResult = "";
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        try {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), CHARSET_ENCODING);
                entity.setContentEncoding(CHARSET_ENCODING);
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (HttpStatus.SC_OK == result.getStatusLine().getStatusCode()) {
                try {
                    // 读取服务器返回过来的json字符串数据
                    strResult = EntityUtils.toString(result.getEntity(), CHARSET_ENCODING);
                    log.info("POST请求结果：code: {}, result: {}",result.getStatusLine().getStatusCode(),strResult);
                } catch (Exception e) {
                    log.error("post请求提交失败: url={}&params={}\t{}" , url, jsonParam, e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("post请求提交失败: url={}&params={}\t{}" , url, jsonParam, e);
        } finally {
            if(null != httpPost){
                httpPost.releaseConnection();
            }
            if (null != httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strResult;
    }

    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     * @param url            url地址
     * @param strParam       参数
     * @return
     */
    public static String httpPost(String url, String strParam) {

        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        // post请求返回结果
        String httpRlt = "";
        try {
            if (StringUtils.isNotBlank(strParam)) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, CHARSET_ENCODING);
                entity.setContentEncoding(CHARSET_ENCODING);
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (HttpStatus.SC_OK == result.getStatusLine().getStatusCode()) {
                try {
                    // 读取服务器返回数据
                    httpRlt = EntityUtils.toString(result.getEntity(), CHARSET_ENCODING);
                    log.info("POST请求结果：code: {}, result: {}",result.getStatusLine().getStatusCode(),httpRlt);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("post请求提交失败: url={}&params={}\t{}" , url, strParam, e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("post请求提交失败: url={}&params={}\t{}" , url, strParam, e);
        } finally {
            if(null != httpPost){
                httpPost.releaseConnection();
            }
            if (null != httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpRlt;
    }

    /**
     * httpPost
     * @param url
     * @param heads
     * @param params
     * @return
     */
    public static String httpPost(String url, Map<String,String> heads, Map<String,String> params){
        // post请求返回结果
        String strResult = "";
        //响应
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = getHttpClient();
        //请求参数转换
        List<NameValuePair> list = convertParams(params);
        try{
            response = executeRequest(httpClient, url,"POST",heads,list,CHARSET_ENCODING);
            // 请求发送成功，并得到响应
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                strResult = EntityUtils.toString(response.getEntity(), CHARSET_ENCODING);
                log.info("POST请求结果：code: {}, result: {}",response.getStatusLine().getStatusCode(),strResult);
            } else {
                log.info("post请求提交失败: url={}&params={}", url, params);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("post请求提交失败: url={}&params={}\t{}", url, params, e);
        } finally {
            if(null != response){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strResult;
    }
    /**
     * httpGet
     * @param url
     * @param params 请求参数
     * @return
     */
    public static String httpGet(String url,Map<String,String> params){
        // get请求返回结果
        String strResult = "";
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        List<NameValuePair> list = convertParams(params);
        try{
            response = executeRequest(httpClient, url,"GET",null,list,CHARSET_ENCODING);
            // 请求发送成功，并得到响应
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode())
            {
                strResult = EntityUtils.toString(response.getEntity(), CHARSET_ENCODING);
                log.info("GET请求结果：code: {}, result: {}",response.getStatusLine().getStatusCode(),strResult);
            } else {
                log.info("get请求提交失败: url={}&params={}", url, params);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("get请求提交失败: url={}&params={}\t{}", url, params, e);
        } finally {
            if(null != response){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strResult;
    }
    /**
     * 发送get请求
     * @param url 路径
     * @return
     */
    public static String httpGet(String url) {
        return httpGet(url,null);
    }

    /**
     * 执行请求，得到resonse对象
     * @param httpClient
     * @param url
     * @param method
     * @param header
     * @param nameValuePairs
     * @param charset
     * @return
     */
    public static CloseableHttpResponse executeRequest(CloseableHttpClient httpClient, String url, String method,
                                                       Map<String, String> header, List<NameValuePair> nameValuePairs, String charset) {
        CloseableHttpResponse response = null;
        try {
            if (HttpPost.METHOD_NAME.equalsIgnoreCase(method)) {
                // post方法
                // 创建httpPost
                HttpPost httpPost = new HttpPost(url);
                httpPost.setConfig(requestConfig);
                // 设置请求头
                if (header != null && !header.isEmpty()) {
                    Header[] requestHeaders = getHeader(header);
                    httpPost.setHeaders(requestHeaders);
                }
                // 设置参数
                if (nameValuePairs != null && !nameValuePairs.isEmpty()) {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,charset));
                }
                // 执行并得到response
                response = httpClient.execute(httpPost);
            } else if (HttpGet.METHOD_NAME.equalsIgnoreCase(method)) {
                // get方法
                // 创建httpGet
                HttpGet httpGet = new HttpGet(url);
                httpGet.setConfig(requestConfig);
                // 设置请求头
                if (header != null && !header.isEmpty()) {
                    Header[] requestHeaders = getHeader(header);
                    httpGet.setHeaders(requestHeaders);
                }
                // 设置参数
                if (nameValuePairs != null && !nameValuePairs.isEmpty()) {
                    httpGet.setURI(URI.create(url
                            + "?"
                            + EntityUtils.toString(new UrlEncodedFormEntity(
                            nameValuePairs,charset))));
                }
                // 执行并得到resposne
                response = httpClient.execute(httpGet);
            } else {
                log.info("method must be POST or GET");
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("executeRequest failure: url={}&method={}&header={}&nameValuePairs={}\t{}", url, method, header, nameValuePairs, e);
        }

        return response;
    }


    /**
     * 获取消息头
     * @param header
     * @return
     */
    public static Header[] getHeader(Map<String, String> header) {
        List<Header> headers;
        if (header != null && !header.isEmpty()) {
            headers = new ArrayList<>();
            Set<String> keys = header.keySet();
            for (Iterator<String> keyIterator = keys.iterator(); keyIterator
                    .hasNext();) {
                String key = keyIterator.next();
                headers.add(new BasicHeader(key, header.get(key)));
            }
            Header[] requestHeaders = headers.toArray(new Header[0]);

            return requestHeaders;
        }
        return null;
    }

    /**
     * 转换请求参数
     * @param params
     * @return
     */
    public static List<NameValuePair> convertParams(Map<String, String> params) {
        List<NameValuePair> nameValueParams = null;
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            nameValueParams = new ArrayList<>();
            for (Iterator<String> keyIterator = keys.iterator(); keyIterator
                    .hasNext();) {
                String key = keyIterator.next();
                String value = params.get(key);
                log.info("key:{};value:{}", key, value);
                nameValueParams.add(new BasicNameValuePair(key, value));
            }
        } else {
            log.info("the params is null");
        }
        return nameValueParams;
    }
}
