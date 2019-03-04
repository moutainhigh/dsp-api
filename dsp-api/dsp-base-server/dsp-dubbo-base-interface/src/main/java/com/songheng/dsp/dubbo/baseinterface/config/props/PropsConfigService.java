package com.songheng.dsp.dubbo.baseinterface.config.props;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/4 14:29
 * @description: PropsConfig
 */
public interface PropsConfigService {

    /**
     * 获取配置文件属性值
     * @param name
     * @return
     */
    String getProperty(String name);

    /**
     * 获取所有配置文件key value
     * @return
     */
    Map<String, String> getAllProperty();

}
