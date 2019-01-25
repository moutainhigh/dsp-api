package com.songheng.dsp.common.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FileUtils {

    /**
     * @Description: 写文件
     * @param writeFilePath 写入的文件路径
     * @param content   写入的文件内容
     * @param append   是否追加
     * @return 写入成功返回true 写入失败返回false
     **/
    public static boolean writeFile(String writeFilePath,String content,boolean append){
        try {
            if(append){
                Files.append(content, new File(writeFilePath), Charsets.UTF_8);
            }else {
                Files.write(content, new File(writeFilePath), Charsets.UTF_8);
            }
            log.debug("[write file]:path={}&append={}&content:{}",writeFilePath,append,content);
            return true;
        }catch (Exception e){
            log.error("path={}&append={}&content={}\t{}",writeFilePath,append,content,e);
            return false;
        }
    }

    /**
     * @Description: 复制文件
     * @param originFilePath 原始文件路径
     * @param copyFilePath   需要复制的文件路径
     * @return 复制成功返回true 复制失败返回false
     **/
    public static boolean copyFile(String originFilePath,String copyFilePath){
        try {
            Files.copy(new File(originFilePath), new File(copyFilePath));
            log.debug("[copy file]:origin={}&copy={}",originFilePath,copyFilePath);
            return true;
        }catch (Exception e){
            log.error("origin={}&copy={}\t{}",originFilePath,copyFilePath,e);
            return false;
        }
    }
    /**
     * @Description: 移动、重命名文件
     * @param originFilePath 原始文件路径
     * @param moveFilePath   需要复制的文件路径
     * @return 复制成功返回true 复制失败返回false
     **/
    public static boolean moveFile(String originFilePath,String moveFilePath){
        try {
            Files.move(new File(originFilePath), new File(moveFilePath));
            log.debug("[move file]:origin={}&move:{}",originFilePath,moveFilePath);
            return true;
        }catch (Exception e){
            log.error("origin={}move=&{}\t{}",originFilePath,moveFilePath,e);
            return false;
        }
    }
    public static void main(String[] args) {
        writeFile("/data/1.txt","hello,world8",true);
        copyFile("/data/1.txt","/data/2.txt");
        moveFile("/data/1.txt","/data/2.txt");

    }
}
