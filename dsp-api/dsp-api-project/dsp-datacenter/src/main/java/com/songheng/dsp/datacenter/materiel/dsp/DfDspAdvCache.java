package com.songheng.dsp.datacenter.materiel.dsp;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.enums.ClusterEnum;
import com.songheng.dsp.common.enums.HbaseFamilyQualifierEnum;
import com.songheng.dsp.common.hbase.HbaseUtil;
import com.songheng.dsp.common.utils.DateUtils;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.DbConfigLoader;
import com.songheng.dsp.datacenter.config.props.PropertiesLoader;
import com.songheng.dsp.model.materiel.ExtendNews;
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
     * 按 terminal+pgtype 维度拆分ExtendNews
     * key: terminal+pgtype
     * value: Set<ExtendNews>
     */
    private volatile Map<String, Set<ExtendNews>> tmlPgTypeMap = new ConcurrentHashMap<>(16);

    /**
     * 获取以 terminal+pgtype 为key的 ExtendNews Map
     * @return
     */
    public Map<String, Set<ExtendNews>> getTmlPgTypeExtendNewsMap(){
        return tmlPgTypeMap;
    }

    /**
     * 根据指定 terminal,pgtype 获取 Set<ExtendNews>
     * @param terminal
     * @param pgtype
     * @return
     */
    public Set<ExtendNews> getExtendNewsSet(String terminal, String pgtype){
        String tml_pg_key = String.format("%s%s%s", terminal, ".", pgtype);
        if (StringUtils.isBlank(tml_pg_key)){
            return new HashSet<>();
        }
        return tmlPgTypeMap.get(tml_pg_key);
    }


    /**
     * 更新DfDspAdv缓存数据
     *
     */
    public void updateDfDspAdv(){

        List<ExtendNews> extendNewsList = DbUtils.queryList("SELECT\n" +
                "\ta.hisId AS deliveryId,\n" +
                "\tCASE\n" +
                "\tWHEN a.positionType = 'pc' THEN\n" +
                "\tb.positionType ELSE a.positionType\n" +
                "\tEND AS pgtype,\n" +
                "\ta.unitprice AS money,\n" +
                "\ta.showTime AS maxShowTime,\n" +
                "\ta.chargeway,\n" +
                "\ta.position,\n" +
                "\ta.money AS totalmoney,\n" +
                "\ta.realendTime,\n" +
                "\ta.intervalTime,\n" +
                "\ta.limitshowtime,\n" +
                "\ta.limitclicktime,\n" +
                "\ta.groupid,\n" +
                "\ta.ocpcunitprice,\n" +
                "\ta.subhisidnum,\n" +
                "\ta.yusuanType,\n" +
                "\tb.adId,\n" +
                "\tb.channel,\n" +
                "\tb.adurl AS url,\n" +
                "\tb.advertiser AS source,\n" +
                "\tb.isdown AS isdownload,\n" +
                "\tb.isaccurate,\n" +
                "\tb.isadv AS isAdv,\n" +
                "\tb.islimittime,\n" +
                "\tb.switch AS switchTag,\n" +
                "\tb.apptypeid,\n" +
                "\tb.isfake,\n" +
                "\tb.isgrayav,\n" +
                "\tb.iscustomtime,\n" +
                "\tb.appstoreid,\n" +
                "\tb.channelnames,\n" +
                "\tb.videourl AS video_link,\n" +
                "\tb.videoalltime,\n" +
                "\tb.isfullscreen,\n" +
                "\tb.positionType AS pcadposition,\n" +
                "\tb.download AS downloadurl,\n" +
                "\tb.summary,\n" +
                "\tb.isretreatad,\n" +
                "\tb.packagename,\n" +
                "\tb.userId,\n" +
                "\tb.interest,\n" +
                "\tb.cpcfactor,\n" +
                "\tb.isfirstbrush,\n" +
                "\tb.extra1,\n" +
                "\tb.extra2,\n" +
                "\tb.extra3,\n" +
                "\tb.extra4,\n" +
                "\tb.extra5,\n" +
                "\tb.shieldarea,\n" +
                "\tb.adIntroduction AS title,\n" +
                "\tb.sex,\n" +
                "\tb.province AS allowStations,\n" +
                "\tb.ageGroup,\n" +
                "\tb.positionId AS isBigImgNews,\n" +
                "\td.typeName AS adType,\n" +
                "\tGROUP_CONCAT( DISTINCT e.imgPath ORDER BY e.imgId ASC ) AS imgJson,\n" +
                "\tGROUP_CONCAT( DISTINCT f.formName ) AS os,\n" +
                "\tc.imgWidth,\n" +
                "\tCASE\n" +
                "\tWHEN b.isfullscreen = 1 THEN\n" +
                "\t1136 ELSE c.imgHeight \n" +
                "\tEND AS imgHeight,\n" +
                "\tg.stdate,\n" +
                "\tg.eddate,\n" +
                "\tg.realunitprice,\n" +
                "\ti.installIds,\n" +
                "\ti.notInstallIds,\n" +
                "\ti.remark,\n" +
                "\tj.budget,\n" +
                "\tr.issupplement,\n" +
                "\tr.supplementrate,\n" +
                "\tr.vendor,\n" +
                "\tr.operator,\n" +
                "\tr.interesttendency AS interestTendency,\n" +
                "\tr.clickHisLabel,\n" +
                "\tr.totalnum,\n" +
                "\tr.refreshnum,\n" +
                "\tr.monopolyposition,\n" +
                "\tr.sectors,\n" +
                "\tr.subadstyle,\n" +
                "\tr.gdLabel,\n" +
                "\tr.putinway,\n" +
                "\tr.appredirect AS redirect,\n" +
                "\tmax( o.childrenId ) AS orderId,\n" +
                "\tmax( o.createTime ) AS orderCreateTime \n" +
                "\tFROM\n" +
                "\t(\n" +
                "\tSELECT\n" +
                "\t\thisId,\n" +
                "\t\tadId,\n" +
                "\t\tmoney,\n" +
                "\t\tshowTime,\n" +
                "\t\tchargeway,\n" +
                "\t\tstartTime,\n" +
                "\t\tPOSITION,\n" +
                "\t\tunitprice,\n" +
                "\t\trealendTime,\n" +
                "\t\tintervalTime,\n" +
                "\t\tlimitshowtime,\n" +
                "\t\tlimitclicktime,\n" +
                "\t\tgroupid,\n" +
                "\t\tocpcunitprice,\n" +
                "\t\tsubhisidnum,\n" +
                "\t\tyusuanType,\n" +
                "\t\tpositionType \n" +
                "\tFROM\n" +
                "\t\tadvertise.adplatform_adShowHistory \n" +
                "\tWHERE\n" +
                "\t\tstartTime <= NOW( ) AND endTime >= NOW( ) \n" +
                "\t\tAND adstate = 1\n" +
                "\tUNION ALL\n" +
                "\tSELECT\n" +
                "\t\thisId,\n" +
                "\t\tadId,\n" +
                "\t\tmoney,\n" +
                "\t\tshowTime,\n" +
                "\t\tchargeway,\n" +
                "\t\tstartTime,\n" +
                "\t\tPOSITION,\n" +
                "\t\tunitprice,\n" +
                "\t\trealendTime,\n" +
                "\t\tintervalTime,\n" +
                "\t\tlimitshowtime,\n" +
                "\t\tlimitclicktime,\n" +
                "\t\tgroupid,\n" +
                "\t\tocpcunitprice,\n" +
                "\t\tsubhisidnum,\n" +
                "\t\tyusuanType,\n" +
                "\t\tpositionType \n" +
                "\tFROM\n" +
                "\t\tadvertise.adplatform_adShowHistory \n" +
                "\tWHERE\n" +
                "\t\tstartTime <= NOW( ) AND endTime >= NOW( ) \n" +
                "\t\tAND ( adstate =- 1 AND statusflag IN ( 1, 2 ) )\n" +
                "\t) AS a\n" +
                "\tLEFT JOIN advertise.adplatform_adInfo b ON a.adId = b.adId\n" +
                "\tLEFT JOIN advertise.adplatform_adPosition c ON b.positionId = c.positionId\n" +
                "\tLEFT JOIN advertise.adplatform_adType d ON b.adType = d.adType\n" +
                "\tLEFT JOIN advertise.adplatform_adImgInfo e ON e.adId = b.adId\n" +
                "\tLEFT JOIN advertise.adplatform_formType f ON FIND_IN_SET( f.formId, b.formId )\n" +
                "\tLEFT JOIN (\n" +
                "\tSELECT\n" +
                "\t\tadId,\n" +
                "\t\tstdate,\n" +
                "\t\teddate,\n" +
                "\t\trealunitprice \n" +
                "\tFROM\n" +
                "\t\tadvertise.adplatform_adInfo_extend \n" +
                "\tWHERE\n" +
                "\t\tflag = 1 \n" +
                "\t\tAND stdate <= NOW( ) AND eddate >= NOW( ) \n" +
                "\t) g ON b.adId = g.adId\n" +
                "\tLEFT JOIN advertise.adplatform_banlance h ON b.userId = h.userId\n" +
                "\tLEFT JOIN advertise.adplatform_installappinfo i ON a.adId = i.adId\n" +
                "\tLEFT JOIN advertise.adplatform_adInfo_reviewextend r ON r.adId = a.adId\n" +
                "\tLEFT JOIN advertise.adplatform_adInfo_group j ON a.groupid = j.id\n" +
                "\tLEFT JOIN advertise.adplatform_delivery_order o ON a.hisId = o.hisId \n" +
                "\tAND o.statusFlag = 1 \n" +
                "\tWHERE\n" +
                "\t( b.switch = 1 OR ( b.switch = - 1 AND b.deleteflag IN ( 1, 5 ) ) ) \n" +
                "\tAND j.flag = 1 \n" +
                "\tAND b.checked = 1 \n" +
                "\tAND b.platform = 'dongfang' \n" +
                "\tAND ( h.banlance > 0 OR h.virtualBalance > 0 ) \n" +
                "\tGROUP BY\n" +
                "\ta.adId \n" +
                "\tORDER BY\n" +
                "\tb.iscustomtime DESC,\n" +
                "\ta.unitprice DESC,\n" +
                "\tb.isaccurate DESC,\n" +
                "\ta.startTime ASC", ExtendNews.class);
        Map<String, ExtendNews> extendNewsMap = new HashMap<>(256);
        //deliveryid --> groupRowKey映射
        Map<String, String> groupRowKeyMap = new HashMap<>(256);
        //deliveryid --> dailyRowKey映射
        Map<String, String> dailyRowKeyMap = new HashMap<>(256);
        List<Get> getlist_B = new ArrayList<>();
        List<Get> getlist_E = new ArrayList<>();
        String date = DateUtils.dateFormat(new Date(), DateUtils.DATE_FORMAT_YYYYMMDD);
        for (ExtendNews extendNews : extendNewsList){
            if (StringUtils.isNotBlank(extendNews.getOrderId())){
                try {
                    long orderCreateTs = DateUtils.dateParse(extendNews.getOrderCreateTime(), DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS).getTime();
                    //修改预算订单延迟时间，默认2分钟
                    long delayTime = Long.parseLong(StringUtils.replaceInvalidString(DbConfigLoader.getDbConfigValue("orderDelayTime"), "120"));
                    if ((System.currentTimeMillis()-orderCreateTs)/1000 < delayTime){
                        log.debug("【orderDelay】【修改预算订单延迟过滤】\tdeliveryId: {}\torderId: {}", extendNews.getDeliveryId(), extendNews.getOrderId());
                        continue;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }
            }
            extendNews.setTotalmoney(extendNews.getTotalmoney()*100000);
            extendNews.setBudget(extendNews.getBudget()>0 ? extendNews.getBudget()*100000 : extendNews.getBudget());
            extendNews.setIntervalTime(extendNews.getIntervalTime()*60*1000);
            String deliveryId = extendNews.getDeliveryId();
            deliveryId = new StringBuilder(deliveryId).reverse().toString();
            //总消耗
            Get totalCostGet = new Get(deliveryId.getBytes());
            totalCostGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName());

            //广告组日消耗
            String groupId = null != extendNews.getGroupid() ? extendNews.getGroupid() : "";
            String groupRowKey = new StringBuilder(date).reverse().append(new StringBuilder(groupId).reverse()).toString();
            groupRowKeyMap.put(deliveryId, groupRowKey);
            Get groupGet = new Get(groupRowKey.getBytes());
            groupGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName());

            //日消耗
            String dailyRowKey = new StringBuilder(extendNews.getDeliveryId()).reverse().append(new StringBuilder(date).reverse()).toString();
            dailyRowKeyMap.put(deliveryId, dailyRowKey);
            Get dailyGet = new Get(dailyRowKey.getBytes());
            dailyGet.addColumn(HbaseFamilyQualifierEnum.FAMILY_D.getFmlyQlfirName(), HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName());

            if ("pc".equalsIgnoreCase(extendNews.getChannel())){
                getlist_E.add(totalCostGet);
                getlist_E.add(groupGet);
                getlist_E.add(dailyGet);
            } else {
                getlist_B.add(totalCostGet);
                getlist_B.add(groupGet);
                getlist_B.add(dailyGet);
            }
            extendNewsMap.put(deliveryId, extendNews);
        }
        Map<String, byte[]> getRlt = new HashMap<>(256);
        //分别查询对应Hbase集群的消耗
        getRlt.putAll(HbaseUtil.get(ClusterEnum.CLUSTER_B, PropertyPlaceholder.getProperty("adplatform_adstatus_B"), getlist_B));
        getRlt.putAll(HbaseUtil.get(ClusterEnum.CLUSTER_E, PropertyPlaceholder.getProperty("adplatform_adstatus_E"), getlist_E));
        //消耗过滤
        costFilter(extendNewsMap, groupRowKeyMap, dailyRowKeyMap, getRlt);
        Collection<ExtendNews> collection = extendNewsMap.values();
        Map<String, Set<ExtendNews>> extendNewsTmp = new ConcurrentHashMap<>(64);
        String tml_pg_key;
        String[] channels;
        for (ExtendNews news : collection){
            if (StringUtils.isNotBlank(news.getChannel())){
                channels = news.getChannel().split(",|，");
                for (String channel : channels){
                    tml_pg_key = String.format("%s%s%s", channel, ".", news.getPgtype());
                    if (extendNewsTmp.containsKey(tml_pg_key)){
                        extendNewsTmp.get(tml_pg_key).add(news);
                    } else {
                        Set<ExtendNews> extendNews = new HashSet<>();
                        extendNews.add(news);
                        extendNewsTmp.put(tml_pg_key, extendNews);
                    }
                }
            }
        }
        if (extendNewsTmp.size() > 0){
            tmlPgTypeMap = extendNewsTmp;
        }
        log.debug("DfDspAdvCache Size: {}\tAfter Filter Size: {}", extendNewsList.size(), collection.size());
    }

    /**
     * 消耗过滤 总消耗/广告组日消耗/日消耗
     * @param extendNewsMap
     * @param groupRowKeyMap
     * @param dailyRowKeyMap
     * @param getRlt
     */
    private void costFilter(Map<String, ExtendNews> extendNewsMap, Map<String, String> groupRowKeyMap,
                            Map<String, String> dailyRowKeyMap, Map<String, byte[]> getRlt){
        if (null == extendNewsMap || extendNewsMap.size() == 0){
            return;
        }
        try {
            Iterator<String> iterator = extendNewsMap.keySet().iterator();
            while (iterator.hasNext()) {
                String hisIdRev = iterator.next();
                ExtendNews extendNews = extendNewsMap.get(hisIdRev);
                //总消耗key
                String get_key = String.format("%s%s%s", HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName(), ".", hisIdRev);
                //组日消耗key
                String get_key_grp = String.format("%s%s%s", HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName(), ".", groupRowKeyMap.get(hisIdRev));
                //日消耗key
                String get_key_day = String.format("%s%s%s", HbaseFamilyQualifierEnum.QUALIFIER_COST_TOTAL.getFmlyQlfirName(), ".", dailyRowKeyMap.get(hisIdRev));
                //总消耗过滤
                if (null != getRlt.get(get_key)) {
                    long totalCost = Bytes.toLong(getRlt.get(get_key));
                    if (0 == extendNews.getYusuanType() && totalCost >= extendNews.getTotalmoney()) {
                        //总消耗超标
                        iterator.remove();
                        log.debug("【costFilter】【总消耗过滤】\thisId: {}\ttotalCost: {}\ttotalMoney: {}", extendNews.getDeliveryId(), totalCost, extendNews.getTotalmoney());
                        continue;
                    }
                }

                //广告组日消耗过滤
                if (null != getRlt.get(get_key_grp)){
                    long grpCost = Bytes.toLong(getRlt.get(get_key_grp));
                    if (0 == extendNews.getBudget()){
                        //防止为第一次投放，且预算为0时，广告会正常吐出去的情况。
                        iterator.remove();
                        log.debug("【costFilter】【广告组日消耗过滤】【组预算为0】\tgroupId: {}\tbudget: {}", extendNews.getGroupid(), extendNews.getBudget());
                        continue;
                    }
                    //广告组日消耗折算的百分比，默认100%
                    double percent_grp = Double.parseDouble(StringUtils.replaceInvalidString(PropertiesLoader.getProperty("group_cost_percent"), "100"))/100;
                    if (extendNews.getBudget() > 0 && grpCost >= extendNews.getBudget()*percent_grp){
                        //广告组日预算超标
                        iterator.remove();
                        log.debug("【costFilter】【广告组日消耗过滤】\tgroupId: {}\tgroupCost: {}\tbudget: {}", extendNews.getGroupid(), grpCost, extendNews.getBudget());
                        continue;
                    }
                }

                //日消耗过滤
                if (null != getRlt.get(get_key_day)){
                    long dayCost = Bytes.toLong(getRlt.get(get_key_day));
                    //日消耗折算的百分比，默认100%
                    double percent_day = Double.parseDouble(StringUtils.replaceInvalidString(PropertiesLoader.getProperty("daily_cost_percent"), "100"))/100;
                    if (1 == extendNews.getYusuanType() && dayCost >= extendNews.getTotalmoney()*percent_day){
                        //日消耗超标
                        iterator.remove();
                        log.debug("【costFilter】【日消耗过滤】\thisId: {}\tdayCost: {}\tdayMoney: {}", extendNews.getDeliveryId(), dayCost, extendNews.getTotalmoney());
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
