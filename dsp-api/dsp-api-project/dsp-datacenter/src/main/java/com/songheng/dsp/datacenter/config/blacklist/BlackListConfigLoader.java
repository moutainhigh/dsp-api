package com.songheng.dsp.datacenter.config.blacklist;

import com.songheng.dsp.common.utils.FileUtils;
import com.songheng.dsp.common.utils.XmlParseUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 黑名单配置数据
 * @author: zhangshuai@021.com
 * @date: 2019-04-01 20:00
 **/
public class BlackListConfigLoader {

    /**
     * 黑名单列表
     * key:文件名
     * value:文件内容列表
     */
    private volatile static Map<String, List<String>> blackListConfigMap = new ConcurrentHashMap<>(5);

    /**
     * 加载所有黑名单列表数据
     */
    public static void loadAllBlackListConfig(){
        try {
            String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String filePath = String.format("%s%s", classPath, "blacklist");
            System.out.println("BlackList Path: "+filePath);
            List<File> fileList = new ArrayList<>();
            FileUtils.getFilesByPath(filePath, fileList);
            for (File file : fileList){
                List<String> data = FileUtils.readLines(file.getPath(),true);
                blackListConfigMap.put(file.getName().replace(".data",""),data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Map<String, List<String>> getBlackListConfigMap(){
        return blackListConfigMap;
    }

}
