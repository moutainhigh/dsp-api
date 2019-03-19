package com.songheng.dsp.datacenter.ssp;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvSspQidService;
import com.songheng.dsp.model.ssp.AdvSspQid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/3/11 21:40
 * @description: 渠道白名单缓存接口实现类
 */
@Slf4j
@Service(interfaceClass = AdvSspQidService.class)
@Component
public class AdvSspQidImpl implements AdvSspQidService {

    /**
     * terminal, appName, qid 映射 渠道白名单信息
     */
    private volatile Map<String, AdvSspQid> advSspQidMap = new ConcurrentHashMap<>(16);

    /**
     * 获取所有 渠道白名单信息
     * @return
     */
    @Override
    public Map<String, AdvSspQid> getAdvSspQidMap(){
        return advSspQidMap;
    }

    /**
     * 获取指定 terminal, appName, qid 渠道白名单信息
     * @param terminal
     * @param appName
     * @param qid
     * @return
     */
    @Override
    public AdvSspQid getAdvSspQid(String terminal, String appName, String qid){
        String tml_site_qid = String.format("%s%s%s%s%s", terminal, ".", appName, ".", qid);
        AdvSspQid advSspQid = advSspQidMap.get(tml_site_qid);
        return null != advSspQid ? advSspQid : new AdvSspQid();
    }

    /**
     * 更新 渠道白名单信息（要求进行渠道白名单验证）
     */
    public void updateAdvSspQid(){
        String sql = SqlMapperLoader.getSql("AdvSsp", "queryAdvSspQid");
        if (StringUtils.isBlank(sql)){
            log.error("updateAdvSspQid error sql is null, namespace: AdvSsp, id: queryAdvSspQid");
            return;
        }
        List<AdvSspQid> advSspQidList = DbUtils.queryList(sql, AdvSspQid.class);
        Map<String, AdvSspQid> advSspQidTmp = new ConcurrentHashMap<>(32);
        String tml_site_qid;
        for (AdvSspQid advSspQid : advSspQidList){
            tml_site_qid = String.format("%s%s%s%s%s", advSspQid.getTerminal(), ".", advSspQid.getAppName(),
                    ".", advSspQid.getQid());
            advSspQidTmp.put(tml_site_qid, advSspQid);
        }
        if (advSspQidTmp.size() > 0){
            advSspQidMap = advSspQidTmp;
        }
        log.debug("AdvSspQid Size: {}\tTerminal Site Qid Of Map Size: {}", advSspQidList.size(), advSspQidTmp.size());
    }

}
