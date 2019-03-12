package com.songheng.dsp.datacenter.ssp;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
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
        if (StringUtils.isBlank(tml_site_qid)){
            return new AdvSspQid();
        }
        AdvSspQid advSspQid = advSspQidMap.get(tml_site_qid);
        return null != advSspQid ? advSspQid : new AdvSspQid();
    }

    /**
     * 更新 渠道白名单信息（要求进行渠道白名单验证）
     */
    public void updateAdvSspQid(){

        List<AdvSspQid> advSspQidList = DbUtils.queryList("SELECT\n" +
                "\ta.id AS sspAppId,\n" +
                "\ta.appName,\n" +
                "\ta.terminal,\n" +
                "\ta.appType,\n" +
                "\ta.appId,\n" +
                "\ta.bosshead,\n" +
                "\ta.validateQid,\n" +
                "\ta.remark,\n" +
                "\tq.qid,\n" +
                "\tq.qidCode \n" +
                "\tFROM\n" +
                "\tadv_ssp_qid q\n" +
                "\tINNER JOIN adv_ssp_application a ON q.appId = a.id \n" +
                "\tWHERE\n" +
                "\tq.STATUS = 1 \n" +
                "\tAND a.STATUS = 1 \n" +
                "\tAND a.validateQid = 1", AdvSspQid.class);
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
