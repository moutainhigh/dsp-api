package com.songheng.dsp.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/13 21:12
 * @description: XML 解析工具类
 */
@Slf4j
public class XmlParseUtils {

    /**
     * 解析Xml Sql 文件
     * 只解析两层
     * <mapper namespace=""><select id=""></select>...</mapper>
     * @param file
     * @return
     */
    public static Map<String, String> parseXmlSql(File file){
        Map<String, String> result = new HashMap<>(16);
        if (null == file || !file.exists()){
            return result;
        }
        SAXReader sr = new SAXReader();
        try {
            Document doc = sr.read(file);
            //获取根目录
            Element root = doc.getRootElement();
            //获取根目录属性值namespace
            String nameSpace = root.attribute("namespace").getValue();
            Iterator iterator = root.elementIterator();
            String attrValue, elementName, textContent;
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                elementName = element.getName();
                //xml元素约束
                if (SqlType.SELECT.name().equalsIgnoreCase(elementName) ||
                        SqlType.INSERT.name().equalsIgnoreCase(elementName) ||
                        SqlType.UPDATE.name().equalsIgnoreCase(elementName) ||
                        SqlType.DELETE.name().equalsIgnoreCase(elementName) ||
                        SqlType.SQL.name().equalsIgnoreCase(elementName)){
                    textContent = element.getTextTrim();
                    attrValue = element.attribute("id").getValue();
                    if (StringUtils.isNotBlank(attrValue)){
                        if (StringUtils.isBlank(nameSpace)){
                            result.put(attrValue, textContent);
                        } else {
                            result.put(String.format("%s%s%s", nameSpace, ".", attrValue), textContent);
                        }
                    }
                }
            }
        } catch (DocumentException e) {
            log.error("parseXmlSql error, File Path: {}\n{}", file.getAbsolutePath(), e.getMessage());
        }
        return result;
    }

    /**
     * sql解析类型
     */
    enum SqlType{
        SELECT,
        INSERT,
        UPDATE,
        DELETE,
        SQL
    }

}
