package com.songheng.dsp.dubbo.consumer;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author: luoshaobing
 * @date: 2019/1/22 13:56
 * @description: 构建部署web项目，需要WebApplicationInitializer
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //此处为带有@SpringBootApplication注解的启动类
        return builder.sources(ConsumerApplication.class);
    }
}
