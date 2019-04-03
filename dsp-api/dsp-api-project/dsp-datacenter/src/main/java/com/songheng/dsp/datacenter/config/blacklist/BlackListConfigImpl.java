package com.songheng.dsp.datacenter.config.blacklist;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.dubbo.baseinterface.config.blacklist.BlackListService;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * @description: 黑名单缓存接口实现类
 * @author: zhangshuai@021.com
 * @date: 2019-04-01 20:58
 *
 **/
@Service(interfaceClass = BlackListService.class,
        timeout = 1000)
@Component
public class BlackListConfigImpl implements BlackListService {
    @Override
    public Map<String, List<String>> getAllBlackList() {
        return BlackListConfigLoader.getBlackListConfigMap();
    }
}
