package com.songheng.dsp.partner.dc;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author: luoshaobing
 * @date: 2019/3/27 20:34
 * @description:
 */
@Component
public class CacheableTest {

    @Cacheable(value = "adx_user", key = "#root.methodName")
    public String getTerminal(){
        return "h5";
    }

}
