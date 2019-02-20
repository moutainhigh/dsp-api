package com.songheng.dsp.common.sentinel;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.FileRefreshableDataSource;
import com.alibaba.csp.sentinel.datasource.FileWritableDataSource;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.songheng.dsp.common.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/2/19 20:35
 * @description: 加载资源规则json文件
 */
public class FileDataSourceInit {

    /**
     * 初始化加载sentinel资源文件
     * @param filePath 文件路径
     * @throws Exception
     */
    public static void initLoadSource(String filePath) throws Exception {
        if (StringUtils.isBlank(filePath)){
            return;
        }
        long startTs = System.currentTimeMillis();
        System.out.println(DateUtils.dateFormat(new Date(), DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS) + "\t【"+System.getProperty("dsp.project.name")+"】"
                + "\t【Sentinel 初始化加载资源规则文件...】\t filePath："+filePath);

        String flowRulePath = URLDecoder.decode(filePath, "UTF-8");

        ReadableDataSource<String, List<FlowRule>> ds = new FileRefreshableDataSource<>(
                flowRulePath, new Converter<String,List<FlowRule>>(){

            @Override
            public List<FlowRule> convert(String source) {

                return JSON.parseObject(source, new TypeReference<List<FlowRule>>() {});
            }

        });
        // Register to flow rule manager.
        FlowRuleManager.register2Property(ds.getProperty());

        WritableDataSource<List<FlowRule>> wds = new FileWritableDataSource<>(flowRulePath, new Converter<List<FlowRule>, String>() {
            @Override
            public String convert(List<FlowRule> t) {

                return JSON.toJSONString(t);
            }
        });

        // Register to writable data source registry so that rules can be updated to file
        // when there are rules pushed from the Sentinel Dashboard.
        WritableDataSourceRegistry.registerFlowDataSource(wds);

        System.out.println(DateUtils.dateFormat(new Date(), DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS) + "\t【"+System.getProperty("dsp.project.name")+"】"
                + "\t【Sentinel 加载资源规则文件完毕 】\t 耗时："+(System.currentTimeMillis()-startTs));
    }
}
