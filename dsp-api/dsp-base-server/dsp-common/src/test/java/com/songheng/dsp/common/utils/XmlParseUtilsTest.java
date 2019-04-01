package com.songheng.dsp.common.utils;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.enums.ProjectEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/13 22:24
 * @description:
 */
public class XmlParseUtilsTest {

    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init(ProjectEnum.H5);
    }

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void parseXmlSql(){
        Map<String, String> sqlMap = new HashMap<>(16);
        String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String filePath = String.format("%s%s", classPath, "mapper");
        System.out.println(filePath);
        List<File> fileList = new ArrayList<>();
        FileUtils.getFilesByPath(filePath, fileList);
        for (File file : fileList){
            sqlMap.putAll(XmlParseUtils.parseXmlSql(file));
        }
        for (String key : sqlMap.keySet()){
            System.out.println("id: "+key+", sql: "+sqlMap.get(key));
        }
        List<String> list = DbUtils.queryList(ProjectEnum.H5.getDs()[0], sqlMap.get("AdStateChangeMapper.getMonopolyFloorPrice"), String.class, "2019-02-12", "2019-03-13");
        List<String> list2 = DbUtils.queryList(ProjectEnum.H5.getDs()[0], sqlMap.get("AdStateChangeMapper.getMonopolyFloorPrice2"), String.class);
        for (String str : list){
            System.out.println(str);
        }
        System.out.println("------------------------------");
        for (String str : list2){
            System.out.println(str);
        }
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
