package com.songheng.dsp.datacenter.config.props;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.dubbo.baseinterface.config.props.PropsConfigService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/4 14:37
 * @description: PropsConfig缓存接口实现类
 */
@Service(interfaceClass = PropsConfigService.class,
        timeout = 100)
@Component
public class PropsConfigImpl implements PropsConfigService {

    /**
     * 根据属性key获取value
     * @param name
     * @return
     */
    @Override
    public String getProperty(String name) {
        return PropertiesLoader.getProperty(name);
    }

    /**
     * 获取所有propertiesMap
     * @return
     */
    @Override
    public Map<String, String> getAllProperty() {
        return PropertiesLoader.getAllProperty();
    }

}
