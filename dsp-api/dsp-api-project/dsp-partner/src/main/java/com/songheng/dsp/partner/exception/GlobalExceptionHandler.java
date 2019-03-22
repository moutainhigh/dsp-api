package com.songheng.dsp.partner.exception;

import com.songheng.dsp.common.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 全局异常处理
 * @author: zhangshuai@021.com
 * @date: 2019-03-22 13:14
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Map<String,Object> defaultErrorHandler(Exception e) {
        e.printStackTrace();
        String errId = RandomUtils.generateUUID(true);
        log.error("{},{}",errId,e);
        Map<String,Object> info = new HashMap<>(3);
        //异常code信息 用于定位异常问题
        info.put("errCode",errId);
        //业务code,用于定位业务问题
        info.put("bizCode","00000");
        info.put("data",new ArrayList<>());
        return info;
    }

}
