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
     * @description: 写文件内容，无效字符禁止写入
     * @param writeFilePath 写入的文件路径
     * @param content   写入的文件内容
     * @param append   是否追加
     * @return 写入成功返回true 写入失败返回false
     **/
    public static boolean writeFile(String writeFilePath,String content,boolean append){
        try {
            //无效字符串禁止写入
            if(StringUtils.isInvalidString(content)) {
                return false;
            }
            //创建父级目录
            Files.createParentDirs(new File(writeFilePath));
            if(append){
                Files.append(content, new File(writeFilePath), Charsets.UTF_8);
            }else {
                Files.write(content, new File(writeFilePath), Charsets.UTF_8);
            }
            log.debug("[writeFile]:path={}&append={}&content:{}",writeFilePath,append,
                    content.replace("\n","\\n"));
            return true;
        }catch (Exception e){
            log.error("[writeFile]:path={}&append={}&content={}\t{}",writeFilePath,append,
                    content.replace("\n","\\n"),e);
            return false;
        }
    }
    /**
     * @description: 清空文件内容
     * @param writeFilePath 写入的文件路径
     * @return 写入成功返回true 写入失败返回false
     **/
    public static boolean clearFileContent(String writeFilePath){
        try {
            //创建父级目录
            Files.createParentDirs(new File(writeFilePath));
            Files.write("", new File(writeFilePath), Charsets.UTF_8);
            log.debug("[clearFileContent]:path={}",writeFilePath);
            return true;
        }catch (Exception e){
            log.error("[clearFileContent]:path={}\t{}",writeFilePath,e);
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
            log.debug("[copyFile]:origin={}&copy={}",originFilePath,copyFilePath);
            return true;
        }catch (Exception e){
            log.error("[copyFile]:origin={}&copy={}\t{}",originFilePath,copyFilePath,e);
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
            log.debug("[moveFile]:origin={}&move:{}",originFilePath,moveFilePath);
            return true;
        }catch (Exception e){
            log.error("[moveFile]:origin={}&move=&{}\t{}",originFilePath,moveFilePath,e);
            return false;
        }
    }
    /**
     * @description: 读取文件内容
     * @param filePath 读取的文件路径
     * @return 读取的内容
     **/
    public static String readString(String filePath){
        try {
            String string = Files.toString(new File(filePath), Charsets.UTF_8);
            log.debug("[readString]:filePath={}&length={}",filePath,string.length());
            return string;
        }catch (Exception e){
            log.error("[readString]:path={}\t{}",filePath,e);
            return "";
        }
    }
    /**
     * @description: 按行读取内容,并转化为List集合
     * @param filePath 读取的文件路径
     * @param removeDuplicate 是否去掉重复数据
     * @return 读取的集合
     **/
    public static List<String> readLines(String filePath,boolean removeDuplicate){
        try {
            List<String> strings = Files.readLines(new File(filePath), Charsets.UTF_8);
            if(removeDuplicate){
                CollectionUtils.removeDuplicate(strings);
            }
            log.debug("[readLines]:path={}&removeDuplicate={}&size:{}",filePath,removeDuplicate,strings.size());
            return strings;
        }catch (Exception e){
            log.error("[readLines]:path={}&removeDuplicate={}\t{}",filePath,removeDuplicate,e);
            return new ArrayList<>(0);
        }
    }
    /**
     * @description: 获取文件的某列的值 (文件每行按照 <code>splitSymbol</code>分割成多列)
     * @param filePath 读取的文件路径
     * @param splitSymbol 分割符
     * @param index 取得第几列 从0开始
     * @param removeDuplicate 是否去掉重复数据
     * @return 读取的集合
     **/

    public static List<String> readColInLines(String filePath,String splitSymbol,int index,boolean removeDuplicate){
        try {
            List<String> strings = Files.readLines(new File(filePath), Charsets.UTF_8,
                new FileLineProcessor(splitSymbol,index));
            if(removeDuplicate){
                CollectionUtils.removeDuplicate(strings);
            }
            log.debug("[readColInLines]:path={}&removeDuplicate={}&size:{}",filePath,removeDuplicate,strings.size());
            return strings;
        }catch (Exception e){
            log.error("[readColInLines]:path={}&removeDuplicate={}\t{}",filePath,removeDuplicate,e);
            return new ArrayList<>(0);
        }
    }
    /**
     * @description: 读取匹配列的行并格式化行
     * @param filePath 读取的文件路径
     * @param splitSymbol 分割符
     * @param index 取得第几列 从0开始
     * @param removeDuplicate 是否去掉重复数据
     * @param replaceSymbol 需要将原始分隔符替换成的分隔符
     * @param matchStr 需要查找的字符串
     * @return 读取的集合
     **/

    public static List<String> readFormatLinesByMatchCol(String filePath,String splitSymbol,int index,
                                                      boolean removeDuplicate,String replaceSymbol,String matchStr){
        try {
            List<String> strings = Files.readLines(new File(filePath), Charsets.UTF_8,
                    new FileLineProcessor(splitSymbol,index,replaceSymbol,matchStr));
            if(removeDuplicate){
                CollectionUtils.removeDuplicate(strings);
            }
            log.debug("[readFormatLinesByMatchCol]:path={}&removeDuplicate={}&size:{}",filePath,removeDuplicate,strings.size());
            return strings;
        }catch (Exception e){
            log.error("[readFormatLinesByMatchCol]:path={}&removeDuplicate={}\t{}",filePath,removeDuplicate,e);
            return new ArrayList<>(0);
        }
    }

    /***
     * 文件行处理器
     * */
    public static class FileLineProcessor implements LineProcessor<List<String>> {

        private List<String> result = Lists.newArrayList();

        private static Splitter splitter = null;
        /**列下标**/
        private static int INDEX = 0;
        /**需要替换的字符串**/
        private static String REPLACE_SYMBOL = "";
        /**分隔符**/
        private static String SPLIT_SYMBOL = "";
        /**是否按照匹配字符串查找***/
        private static boolean isFindMatchStr = false;
        /**匹配的字符串***/
        private static String MATCH_STR = "";

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
            REPLACE_SYMBOL = replaceSymbol;
            SPLIT_SYMBOL = splitSymbol;
            MATCH_STR = StringUtils.replaceInvalidString(matchStr,"");
        }
        @Override
        public boolean processLine(String line) {
            try {
                /***获取<code>INDEX</code>列的value***/
                String colVal = Iterables.get(splitter.split(line), INDEX);
                if(isFindMatchStr){
                    //查找指定匹配的字符串
                    if(MATCH_STR.equalsIgnoreCase(colVal)) {
                        result.add(line.replace(SPLIT_SYMBOL, REPLACE_SYMBOL));
                    }
                }else {
                    //查找某列的字符串
                    result.add(colVal);
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
}
