package com.songheng.dsp.datacenter.ssp;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
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
        List<AdvDictAdStyle> advDictAdStyleList = DbUtils.queryList(
                "SELECT " +
                "\tda.id AS styleId,\n" +
                "\tda.txtlen,\n" +
                "\tda.styleType,\n" +
                "\tda.styleName,\n" +
                "\tda.styleValue,\n" +
                "\tda.width,\n" +
                "\tda.height,\n" +
                "\tda.number,\n" +
                "\tda.iconwidth,\n" +
                "\tda.iconheight,\n" +
                "\tda.perType,\n" +
                "\tda.perCode,\n" +
                "\tda.txtlen,\n" +
                "\tda.subtxtlen,\n" +
                "\tda.imgFormatimgFormat AS imgFormat,\n" +
                "\tda.supportadx\n" +
                "\tFROM adv_dict_adstyle da\n" +
                "\tWHERE da.status = 1", AdvDictAdStyle.class);
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
