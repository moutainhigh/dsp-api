package com.songheng.dsp.common.utils;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @description: 文件操作工具类
 * @author: zhangshuai@021.com
 * @date: 2019-01-23 20:44
 **/
@Slf4j
public final class FileUtils {

    private FileUtils(){}

    /**
     * @description: 写文件
     * @param writeFilePath 写入的文件路径
     * @param content   写入的文件内容
     * @param append   是否追加
     * @return 写入成功返回true 写入失败返回false
     **/
    public static boolean writeFile(String writeFilePath,String content,boolean append){
        try {
            Files.createParentDirs(new File(writeFilePath));
            if(append){
                Files.append(content, new File(writeFilePath), Charsets.UTF_8);
            }else {
                Files.write(content, new File(writeFilePath), Charsets.UTF_8);
            }
            log.debug("[write file]:path={}&append={}&content:{}",writeFilePath,append,content);
            return true;
        }catch (Exception e){
            log.error("[write file]:path={}&append={}&content={}\t{}",writeFilePath,append,content,e);
            return false;
        }
    }

    /**
     * @description: 复制文件
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
            log.error("[copy file]:origin={}&copy={}\t{}",originFilePath,copyFilePath,e);
            return false;
        }
    }

    /**
     * @description: 移动、重命名文件
     * @param originFilePath 原始文件路径
     * @param moveFilePath   需要复制的文件路径
     * @return 移动成功返回true 移动失败返回false
     **/
    public static boolean moveFile(String originFilePath,String moveFilePath){
        try {
            Files.move(new File(originFilePath), new File(moveFilePath));
            log.debug("[move file]:origin={}&move:{}",originFilePath,moveFilePath);
            return true;
        }catch (Exception e){
            log.error("[move file]:origin={}move=&{}\t{}",originFilePath,moveFilePath,e);
            return false;
        }
    }
    /**
     * @description: 按行读取文件内容,并转化为List集合
     * @param filePath 读取的文件路径
     * @return 读取的集合
     **/
    public static String readFileIntoString(String filePath){
        try {
            String string = Files.toString(new File(filePath), Charsets.UTF_8);
            return string;
        }catch (Exception e){
            log.error("[read file]:path={}\t{}",filePath,e);
            return "";
        }
    }
    /**
     * @description: 按行读取文件内容,并转化为List集合
     * @param filePath 读取的文件路径
     * @param removeDuplicate 是否去掉重复数据
     * @return 读取的集合
     **/
    public static List<String> readFileIntoList(String filePath,boolean removeDuplicate){
        try {
            List<String> strings = Files.readLines(new File(filePath), Charsets.UTF_8);
            if(removeDuplicate){
                CollectionUtils.removeDuplicate(strings);
            }
            log.debug("[read file]:path={}&removeDuplicate={}&size:{}",filePath,removeDuplicate,strings.size());
            return strings;
        }catch (Exception e){
            log.error("[read file]:path={}&removeDuplicate={}\t{}",filePath,removeDuplicate,e);
            return new ArrayList<>(0);
        }
    }
    /**
     * @description: 按行读取文件内容并按指定字符分割,取下标列内容读取后转化为List集合
     * @param filePath 读取的文件路径
     * @param splitSymbol 分割符
     * @param index 取得第几列 从0开始
     * @param removeDuplicate 是否去掉重复数据
     * @return 读取的集合
     **/

    public static List<String> readFileLineSplitIntoList(String filePath,String splitSymbol,int index,boolean removeDuplicate){
        try {
            List<String> strings = Files.readLines(new File(filePath), Charsets.UTF_8,
                new FileLineProcessor(splitSymbol,index));
            if(removeDuplicate){
                CollectionUtils.removeDuplicate(strings);
            }
            log.debug("[read file & split]:path={}&removeDuplicate={}&size:{}",filePath,removeDuplicate,strings.size());
            return strings;
        }catch (Exception e){
            log.error("[read file & split]:path={}&removeDuplicate={}\t{}",filePath,removeDuplicate,e);
            return new ArrayList<>(0);
        }
    }
    /**
     * @description: 按行读取文件内容并按指定字符分割,取下标列内容读取后转化为List集合
     * @param filePath 读取的文件路径
     * @param splitSymbol 分割符
     * @param index 取得第几列 从0开始
     * @param removeDuplicate 是否去掉重复数据
     * @param replaceSymbol 需要将原始分隔符替换成的分隔符
     * @param matchStr 需要查找的字符串
     * @return 读取的集合
     **/

    public static List<String> readFileLineSplitFindIntoList(String filePath,String splitSymbol,int index,
                                                             boolean removeDuplicate,String replaceSymbol,String matchStr){
        try {
            List<String> strings = Files.readLines(new File(filePath), Charsets.UTF_8,
                    new FileLineProcessor(splitSymbol,index,replaceSymbol,matchStr));
            if(removeDuplicate){
                CollectionUtils.removeDuplicate(strings);
            }
            log.debug("[read file & split]:path={}&removeDuplicate={}&size:{}",filePath,removeDuplicate,strings.size());
            return strings;
        }catch (Exception e){
            log.error("[read file & split]:path={}&removeDuplicate={}\t{}",filePath,removeDuplicate,e);
            return new ArrayList<>(0);
        }
    }

    /***
     * 文件行处理器
     * */
    public static class FileLineProcessor implements LineProcessor<List<String>> {
        private List<String> result = Lists.newArrayList();
        private static Splitter splitter = null;
        private static int INDEX = 0;
        private static String REPLACESYMBOL = "";
        private static String SPLITSYMBOL = "";
        private static String MATCHSTR = "";
        private static boolean isFindMatchStr = false;
        /**
         * 获取index列的值
         * **/
        private FileLineProcessor(String splitSymbol,int index){
            splitter =  Splitter.on(splitSymbol);
            INDEX = index;
        }
        /**
         * 查找matchIndex列包含matchStr的值 并将分隔符splitSymbol替换成replaceSymbol
         * **/
        private FileLineProcessor(String splitSymbol,int matchIndex,String replaceSymbol,String matchStr){
            this(splitSymbol,matchIndex);
            isFindMatchStr = true;
            REPLACESYMBOL = replaceSymbol;
            SPLITSYMBOL = splitSymbol;
            MATCHSTR = StringUtils.replaceNullOrEmpty(matchStr,"");
        }
        @Override
        public boolean processLine(String line) {
            try {
                if(isFindMatchStr){
                    if(MATCHSTR.equalsIgnoreCase(Iterables.get(splitter.split(line), INDEX))) {
                        result.add(line.replace(SPLITSYMBOL, REPLACESYMBOL));
                    }
                }else {
                    result.add(Iterables.get(splitter.split(line), INDEX));
                }
            }catch (Exception e){
                log.debug("[read file & split & process]:line={}\t{}",line,e);
            }
            return true;
        }
        @Override
        public List<String> getResult() {
            return result;
        }
    }


    public static void main(String[] args) throws Exception{
        writeFile("/data/1.txt","10009\tzhangsan\n10010\tlisi\n",true);
        System.out.println(readFileLineSplitFindIntoList("/data/1.txt",
                "\t",0,false,"】","10010"));

    }
}
