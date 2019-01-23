package com.songheng.dsp.common.utils;

import com.google.common.io.Files;
import java.io.File;

/**
 *
 * @Title: FileUtils
 * @Package com.songheng.dsp.utils
 * @Description: 文件操作工具类
 * @author: zhangshuai@021.com
 * @date: 2019-01-23 20:44
 * @version V1.0
 **/
public class FileUtils {
    /**
     * @Description: 复制文件
     * @param originFilePath 原始文件路径
     * @param copyFilePath   需要复制的文件路径
     * @return 复制成功返回true 复制失败返回false
     **/
    public static boolean copyFile(String originFilePath,String copyFilePath){
        try {
            Files.copy(new File(originFilePath), new File(copyFilePath));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        FileUtils.copyFile("/Users/mac/Documents/workspace/1.txt","/Users/mac/Documents/workspace/2.txt");
    }
}
