package com.songheng.dsp.common.utils;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.enums.ProjectEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/3/13 18:11
 * @description:
 */
public class FileUtilsTest {

    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init(ProjectEnum.H5);
    }

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void getFilesByPath(){
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(path);
        List<File> fileList = new ArrayList<>();
        FileUtils.getFilesByPath(path, fileList);
        for (File file : fileList){
            System.out.println("File Path: "+file.getAbsolutePath());
        }
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
