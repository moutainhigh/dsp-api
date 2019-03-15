package com.songheng.dsp.datacenter.config.db;

import com.songheng.dsp.common.utils.FileUtils;
import com.songheng.dsp.common.utils.XmlParseUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/15 11:01
 * @description: 初始化加载sql mapper
 */
@Component
public class SqlMapperLoader implements InitializingBean {

    /**
     * sql 映射
     * key: namespace+id
     * value: sql
     */
    private static Map<String, String> sqlMap = new HashMap<>(16);

    /**
     * 获取sql语句
     * @param namespace
     * @param id
     * @return
     */
    public static String getSql(String namespace, String id){
        String namespace_id = String.format("%s%s%s", namespace, ".", id);
        return sqlMap.get(namespace_id);
    }

    /**
     * 初始化加载sql mapper
     */
    private void initLoadSqlMapper(){
        String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String filePath = String.format("%s%s", classPath, "mapper");
        System.out.println("Sql Mapper Path: "+filePath);
        List<File> fileList = new ArrayList<>();
        FileUtils.getFilesByPath(filePath, fileList);
        for (File file : fileList){
            sqlMap.putAll(XmlParseUtils.parseXmlSql(file));
        }
    }

    @Override
    public void afterPropertiesSet() {
        initLoadSqlMapper();
    }
}
