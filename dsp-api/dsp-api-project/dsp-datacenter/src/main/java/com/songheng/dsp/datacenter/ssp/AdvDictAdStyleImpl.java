package com.songheng.dsp.datacenter.ssp;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvDictAdStyleService;
import com.songheng.dsp.model.ssp.AdvDictAdStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/3/12 09:51
 * @description: 广告样式缓存接口实现类
 */
@Slf4j
@Service(interfaceClass = AdvDictAdStyleService.class,
        timeout = 1000)
@Component
public class AdvDictAdStyleImpl implements AdvDictAdStyleService {

    /**
     * styleId 映射 广告样式
     */
    private volatile Map<String, AdvDictAdStyle> advDictAdStyleMap = new ConcurrentHashMap<>(16);

    /**
     * 获取所有 styleId 映射 广告样式信息
     * @return
     */
    @Override
    public Map<String, AdvDictAdStyle> getAdvDictAdStyleMap(){
        return advDictAdStyleMap;
    }

    /**
     * 获取指定 styleId 映射 广告样式信息
     * @param styleId
     * @return
     */
    @Override
    public AdvDictAdStyle getAdvDictAdStyle(String styleId){
        if (StringUtils.isBlank(styleId)){
            return new AdvDictAdStyle();
        }
        AdvDictAdStyle advDictAdStyle = advDictAdStyleMap.get(styleId);
        return null != advDictAdStyle ? advDictAdStyle : new AdvDictAdStyle();
    }

    /**
     * 更新 广告样式
     */
    public void updateAdvDictAdStyle(){
        String sql = SqlMapperLoader.getSql("AdvSsp", "queryAdvDictAdStyle");
        if (StringUtils.isBlank(sql)){
            log.error("updateAdvDictAdStyle error sql is null, namespace: AdvSsp, id: queryAdvDictAdStyle");
            return;
        }
        List<AdvDictAdStyle> advDictAdStyleList = DbUtils.queryList(sql, AdvDictAdStyle.class);
        Map<String, AdvDictAdStyle> advDictAdStyleTmp = new ConcurrentHashMap<>(64);
        for (AdvDictAdStyle advDictAdStyle : advDictAdStyleList){
            if (StringUtils.isNotBlank(advDictAdStyle.getStyleId())){
                advDictAdStyleTmp.put(advDictAdStyle.getStyleId(), advDictAdStyle);
            }
        }
        if (advDictAdStyleTmp.size() > 0){
            advDictAdStyleMap = advDictAdStyleTmp;
        }
        log.debug("AdvDictAdStyle Size: {}", advDictAdStyleList.size());
    }
}
