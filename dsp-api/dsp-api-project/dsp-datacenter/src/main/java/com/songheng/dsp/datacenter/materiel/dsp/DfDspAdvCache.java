package com.songheng.dsp.datacenter.materiel.dsp;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.enums.ClusterEnum;
import com.songheng.dsp.common.enums.HbaseFamilyQualifierEnum;
import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.hbase.HbaseUtil;
import com.songheng.dsp.common.utils.DateUtils;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.DbConfigLoader;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.datacenter.config.props.PropertiesLoader;
import com.songheng.dsp.model.materiel.MaterielDirect;
import com.songheng.dsp.model.materiel.MaterielImgInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/3/7 16:08
 * @description: DfDsp广告池缓存
 */
@Slf4j
@Component
public class DfDspAdvCache {

    /**
     * 按 terminal+pgtype 维度拆分MaterielDirect
     * key: terminal+pgtype
     * value: Set<MaterielDirect>
     */
    private volatile Map<String, Set<MaterielDirect>> tmlPgTypeMap = new ConcurrentHashMap<>(16);

    /**
     * 获取以 terminal+pgtype 为key的 MaterielDirect Map
     * @return
     */
    public Map<String, Set<MaterielDirect>> getTmlPgTypeMaterielDirectMap(){
        return tmlPgTypeMap;
    }

    /**
     * 根据指定 terminal,pgtype 获取 Set<MaterielDirect>
     * @param terminal
     * @param pgtype
     * @return
     */
    public Set<MaterielDirect> getMaterielDirectSet(String terminal, String pgtype){
        String tml_pg_key = String.format("%s%s%s", terminal, "_", pgtype);
        Set<MaterielDirect> result = tmlPgTypeMap.get(tml_pg_key);
        return null != result ? result : new HashSet<MaterielDirect>();
    }


    /**
     * 更新DfDspAdv缓存数据
     *
     */
    public void updateDfDspAdv(){
        String sql = SqlMapperLoader.getSql("DfDspAdv", "queryDfDspAdvs");
        if (StringUtils.isBlank(sql)){
            log.error("updateDfDspAdv error sql is null, namespace: DfDspAdv, id: queryDfDspAdvs");
            return;
        }
        List<MaterielDirect> materielDirectList = DbUtils.queryList(ProjectEnum.DATACENTER.getDs()[0], sql, MaterielDirect.class);
        Map<String, MaterielDirect> materielDirectMap = new HashMap<>(256);
        //deliveryid --> groupRowKey映射
        Map<String, String> groupRowKeyMap = new HashMap<>(256);
        //deliveryid --> dailyRowKey映射
        Map<String, String> dailyRowKeyMap = new HashMap<>(256);
        List<Get> getlist_B = new ArrayList<>();
        List<Get> getlist_E = new ArrayList<>();
        String date = DateUtils.dateFormat(new Date(), DateUtils.DATE_FORMAT_YYYYMMDD);
        for (MaterielDirect materielDirect : materielDirectList){
            if (StringUtils.isNotBlank(materielDirect.getOrderId())){
                try {
                    long orderCreateTs = DateUtils.dateParse(materielDirect.getOrderCreateTime(), DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS).getTime();
                    //修改预算订单延迟时间，默认2分钟
                    long delayTime = Long.parseLong(StringUtils.replaceInvalidString(DbConfigLoader.getDbConfigValue("orderDelayTime"), "120"));
                    if ((System.currentTimeMillis()-orderCreateTs)/1000 < delayTime){
                        log.debug("【orderDelay】【修改预算订单延迟过滤】\tdeliveryId: {}\torderId: {}", materielDirect.getDeliveryId(), materielDirect.getOrderId());
                        continue;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            //时间段过滤
            if(StringUtils.isNotBlank(materielDirect.getTimeQuantum())){
                try {
                    if(materielDirect.getTimeQuantum().indexOf(","+DateUtils.getHour(new Date())+",")==-1){
                        log.debug("【TimeQuantum】\tadvId: {}\tdeliveryId: {}\ttimeQuantum: {}", materielDirect.getAdvId(), materielDirect.getDeliveryId(), materielDirect.getTimeQuantum());
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            materielDirect.setPlanBudget(materielDirect.getPlanBudget()*100000);
            materielDirect.setGroupBudget(materielDirect.getGroupBudget()>0 ? materielDirect.getGroupBudget()*100000 : materielDirect.getGroupBudget());
            materielDirect.setIntervalTime(materielDirect.getIntervalTime()*60*1000);
            List<MaterielImgInfo> imglist = new ArrayList<>();
            if (StringUtils.isNotBlank(materielDirect.getImgJson())){
                String[] imgPaths = materielDirect.getImgJson().split(",");
                for (String imgPath : imgPaths){
                    MaterielImgInfo materielImgInfo = new MaterielImgInfo(imgPath, materielDirect.getImgWidth(),
                            materielDirect.getImgHeight(), 0);
                    imglist.add(materielImgInfo);
                }
            }
            materielDirect.setImgs(imglist);
            String deliveryId = materielDirect.getDeliveryId();
            deliveryId = new StringBuilder(deliveryId).reverse().toString();
            //总消耗
            Get totalCostGet = new Get(deliveryId.getBytes());
            totalCostGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName());

            //广告组日消耗
            String groupId = String.valueOf(materielDirect.getGroupId());
            String groupRowKey = new StringBuilder(date).reverse().append(new StringBuilder(groupId).reverse()).toString();
            groupRowKeyMap.put(deliveryId, groupRowKey);
            Get groupGet = new Get(groupRowKey.getBytes());
            groupGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName());

            //日消耗
            String dailyRowKey = new StringBuilder(materielDirect.getDeliveryId()).reverse().append(new StringBuilder(date).reverse()).toString();
            dailyRowKeyMap.put(deliveryId, dailyRowKey);
            Get dailyGet = new Get(dailyRowKey.getBytes());
            dailyGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName());

            if ("pc".equalsIgnoreCase(materielDirect.getChannel())){
                getlist_E.add(totalCostGet);
                getlist_E.add(groupGet);
                getlist_E.add(dailyGet);
            } else {
                getlist_B.add(totalCostGet);
                getlist_B.add(groupGet);
                getlist_B.add(dailyGet);
            }
            materielDirectMap.put(deliveryId, materielDirect);
        }
        Map<String, byte[]> getRlt = new HashMap<>(256);
        //分别查询对应Hbase集群的消耗
        getRlt.putAll(HbaseUtil.get(ClusterEnum.CLUSTER_B, PropertyPlaceholder.getProperty("adplatform_adstatus_B"), getlist_B));
        getRlt.putAll(HbaseUtil.get(ClusterEnum.CLUSTER_E, PropertyPlaceholder.getProperty("adplatform_adstatus_E"), getlist_E));
        //消耗过滤
        costFilter(materielDirectMap, groupRowKeyMap, dailyRowKeyMap, getRlt);
        Collection<MaterielDirect> collection = materielDirectMap.values();
        Map<String, Set<MaterielDirect>> materielDirectTmp = new ConcurrentHashMap<>(64);
        String tml_pg_key;
        String[] channels;
        for (MaterielDirect news : collection){
                if (StringUtils.isNotBlank(news.getChannel())){
                    channels = news.getChannel().split(",|，");
                    for (String channel : channels){
                        tml_pg_key = String.format("%s%s%s", channel, "_", news.getPgtype());
                        if (materielDirectTmp.containsKey(tml_pg_key)){
                            materielDirectTmp.get(tml_pg_key).add(news);
                        } else {
                            Set<MaterielDirect> md = new HashSet<>();
                            md.add(news);
                            materielDirectTmp.put(tml_pg_key, md);
                        }
                    }
                }
        }
        if (materielDirectTmp.size() > 0){
            tmlPgTypeMap = materielDirectTmp;
        }
        log.debug("DfDspAdvCache Size: {}\tAfter Filter Size: {}", materielDirectList.size(), collection.size());
    }

    /**
     * 消耗过滤 总消耗/广告组日消耗/日消耗
     * @param materielDirectMap
     * @param groupRowKeyMap
     * @param dailyRowKeyMap
     * @param getRlt
     */
    private void costFilter(Map<String, MaterielDirect> materielDirectMap, Map<String, String> groupRowKeyMap,
                            Map<String, String> dailyRowKeyMap, Map<String, byte[]> getRlt){
        if (null == materielDirectMap || materielDirectMap.size() == 0){
            return;
        }
        try {
            Iterator<String> iterator = materielDirectMap.keySet().iterator();
            while (iterator.hasNext()) {
                String hisIdRev = iterator.next();
                MaterielDirect materielDirect = materielDirectMap.get(hisIdRev);
                //总消耗key
                String get_key = String.format("%s%s%s", HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName(), ".", hisIdRev);
                //组日消耗key
                String get_key_grp = String.format("%s%s%s", HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName(), ".", groupRowKeyMap.get(hisIdRev));
                //日消耗key
                String get_key_day = String.format("%s%s%s", HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName(), ".", dailyRowKeyMap.get(hisIdRev));
                //总消耗过滤
                if (null != getRlt.get(get_key)) {
                    long totalCost = Bytes.toLong(getRlt.get(get_key));
                    if (0 == materielDirect.getYusuanType() && totalCost >= new Double(materielDirect.getPlanBudget()).longValue()) {
                        //总消耗超标
                        iterator.remove();
                        log.debug("【costFilter】【总消耗过滤】\thisId: {}\ttotalCost: {}\ttotalMoney: {}", materielDirect.getDeliveryId(), totalCost, materielDirect.getPlanBudget());
                        continue;
                    }
                }

                //广告组日消耗过滤
                if (null != getRlt.get(get_key_grp)){
                    long grpCost = Bytes.toLong(getRlt.get(get_key_grp));
                    if (0 == materielDirect.getGroupBudget()){
                        //防止为第一次投放，且预算为0时，广告会正常吐出去的情况。
                        iterator.remove();
                        log.debug("【costFilter】【广告组日消耗过滤】【组预算为0】\tgroupId: {}\tbudget: {}", materielDirect.getGroupId(), materielDirect.getGroupBudget());
                        continue;
                    }
                    //广告组日消耗折算的百分比，默认100%
                    double percent_grp = Double.parseDouble(StringUtils.replaceInvalidString(PropertiesLoader.getProperty("group_cost_percent"), "100"))/100;
                    if (materielDirect.getGroupBudget() > 0 && grpCost >= materielDirect.getGroupBudget()*percent_grp){
                        //广告组日预算超标
                        iterator.remove();
                        log.debug("【costFilter】【广告组日消耗过滤】\tgroupId: {}\tgroupCost: {}\tbudget: {}", materielDirect.getGroupId(), grpCost, materielDirect.getGroupBudget());
                        continue;
                    }
                }

                //日消耗过滤
                if (null != getRlt.get(get_key_day)){
                    long dayCost = Bytes.toLong(getRlt.get(get_key_day));
                    //日消耗折算的百分比，默认100%
                    double percent_day = Double.parseDouble(StringUtils.replaceInvalidString(PropertiesLoader.getProperty("daily_cost_percent"), "100"))/100;
                    if (1 == materielDirect.getYusuanType() && dayCost >= new Double(materielDirect.getPlanBudget()*percent_day).longValue()){
                        //日消耗超标
                        iterator.remove();
                        log.debug("【costFilter】【日消耗过滤】\thisId: {}\tdayCost: {}\tdayMoney: {}", materielDirect.getDeliveryId(), dayCost, materielDirect.getPlanBudget());
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
