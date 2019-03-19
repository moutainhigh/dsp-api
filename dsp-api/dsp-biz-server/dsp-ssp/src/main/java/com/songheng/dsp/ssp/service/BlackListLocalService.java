package com.songheng.dsp.ssp.service;

import com.songheng.dsp.common.utils.FileUtils;
import com.songheng.dsp.model.flow.BaseFlow;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 黑名单本地服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-19 20:31
 **/
@Service
public class BlackListLocalService {

    private static Map<String,List<String>> blackListMap = new HashMap<>();

    private final static String[] blackListKey = {"ip","advuid","appuid"};

    public Map<String,String> getBlackListKey(BaseFlow baseFlow){
        Map<String,String> result = new HashMap<>(3);
        result.put(blackListKey[0],baseFlow.getRemoteIp());
        result.put(blackListKey[1],baseFlow.getUserId());
        result.put(blackListKey[2],baseFlow.getAppUserId());
        return result;
    }

    static{
        try {

            String filePath = Class.class.getClass().getResource("/").getPath();
            for(int i=0;i<blackListKey.length;i++){
                String fileFullPath = filePath + blackListKey[i] + ".data";
                blackListMap.put(blackListKey[i],FileUtils.readLines(fileFullPath, true)) ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 验证 data 是否在黑名单列表中
     */
    public boolean isInBlackList(String type,String data){
        return blackListMap.get(type).contains(data);
    }
}
