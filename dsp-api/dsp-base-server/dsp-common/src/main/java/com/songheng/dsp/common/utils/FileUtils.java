package com.songheng.dsp.common.utils;

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
            log.error("{}&{}\t{}",originFilePath,copyFilePath,e);
            return false;
        }
    }

    public static void main(String[] args) {
        long base = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            log.error("{}&{}\t{}","aaaaaadfdfadsfadfadfadfadaaaaaa","bbbbbbadfdsafadfadsfadfasfabbbb","ccccccsdfadsfadsfadadfadfadfadadfadsfadfadfa");
        }
        long end = System.currentTimeMillis();
        System.out.println(base+"\t"+end+"\t"+(end-base));
    }
}
