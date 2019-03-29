package com.songheng.dsp.datacenter.shield;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.dubbo.baseinterface.shield.AdvDictShieldService;
import com.songheng.dsp.model.shield.AdvDictShield;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/3/29 17:35
 * @description: 屏蔽信息缓存接口实现类
 */
@Slf4j
@Service(interfaceClass = AdvDictShieldService.class,
        timeout = 1000)
@Component
public class AdvDictShieldImpl implements AdvDictShieldService {

    /**
     * 按 qid 维度拆分AdvDictShield
     * key: qid
     * value: AdvDictShield
     */
    private volatile Map<String, AdvDictShield> advDictShieldMap = new ConcurrentHashMap<>(16);

    /**
     * 获取 以qid 维度所有的AdvDictShield
     * @return
     */
    @Override
    public Map<String, AdvDictShield> getAdvDictShieldMap(){
        return advDictShieldMap;
    }

    /**
     * 获取 指定qid 的AdvDictShield
     * @param qid
     * @return
     */
    @Override
    public AdvDictShield getAdvDictShieldByQid(String qid){
        if (StringUtils.isBlank(qid)){
            return new AdvDictShield();
        }
        AdvDictShield result = advDictShieldMap.get(qid);
        if (null == result){
            result = advDictShieldMap.get("all");
        }
        return null != result ? result : new AdvDictShield();
    }

    /**
     * 更新屏蔽信息缓存
     *
     */
    public void updateAdvDictShield(){
        String sql = SqlMapperLoader.getSql("AdvDictShield", "queryAdvDictShield");
        if (StringUtils.isBlank(sql)){
            log.error("updateAdvDictShield error sql is null, namespace: AdvDictShield, id: queryAdvDictShield");
            return;
        }
        List<AdvDictShield> advDictShieldList = DbUtils.queryList(sql, AdvDictShield.class);
        Map<String, AdvDictShield> advDictShieldTmp = new ConcurrentHashMap<>(32);
        for (AdvDictShield advDictShield : advDictShieldList){
            if (StringUtils.isNotBlank(advDictShield.getQid())){
                advDictShieldTmp.put(advDictShield.getQid(), advDictShield);
            }
        }
        if (advDictShieldTmp.size() > 0){
            advDictShieldMap = advDictShieldTmp;
        }
        log.debug("AdvDictShield List Size: {}", advDictShieldList.size());
    }

}
