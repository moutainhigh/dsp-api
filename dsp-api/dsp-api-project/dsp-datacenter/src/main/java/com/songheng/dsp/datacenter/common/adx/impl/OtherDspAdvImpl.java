package com.songheng.dsp.datacenter.common.adx.impl;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.common.config.PropertiesLoader;
import com.songheng.dsp.dubbo.baseinterface.common.adx.service.OtherDspAdvService;
import com.songheng.dsp.model.materiel.DspAdvExtend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/2/26 16:57
 * @description: 第三方DSP广告池
 */
@Slf4j
@Component
public class OtherDspAdvImpl implements OtherDspAdvService{

    /**
     * key: app,h5,pc
     * value: List<DspAdvExtend>
     */
    private volatile static Map<String, List<DspAdvExtend>> terminalAds = new ConcurrentHashMap<>(6);
    /**
     * key: app,h5,pc+advid+dspid
     * value DspAdvExtend
     */
    private volatile static Map<String, DspAdvExtend> advidAds = new ConcurrentHashMap<>(32);
    /**
     * key: app,h5,pc+deliveryid+dspid
     * value DspAdvExtend
     */
    private volatile static Map<String, DspAdvExtend> deliveryIdAds = new ConcurrentHashMap<>(32);

    /**
     * 更新第三方DSP广告池
     */
    public void updateDspAdvs(){
        List<DspAdvExtend> dspAdvs = DbUtils.queryList("SELECT\n" +
                        "\tadStyle,\n" +
                        "\tadId AS deliveryid,\n" +
                        "\tadv_adId AS adv_id,\n" +
                        "\tshowrep AS showbackurl,\n" +
                        "\tclickrep AS clickbackurl,\n" +
                        "\tdsp_id AS dspId,\n" +
                        "\tadvertiser AS source,\n" +
                        "\tadTitle AS topic,\n" +
                        "\tadurl AS url,\n" +
                        "\tisgrayav,\n" +
                        "\tisdownload,\n" +
                        "\tdownloadlink AS downloadurl,\n" +
                        "\tdeepLinkUrl AS deeplink,\n" +
                        "\tappStoreId AS appstoreid,\n" +
                        "\tpackageName,\n" +
                        "\timg1Path,\n" +
                        "\timg2Path,\n" +
                        "\timg3Path,\n" +
                        "\tterminal \n" +
                        "\tFROM\n" +
                        "\tadx_adinfo_sync\n" +
                        "\tWHERE\n" +
                        "\tSTATUS = 1 \n" +
                        "\tAND reviewstatus = 1 \n" +
                        "\tAND startTime <= NOW( ) AND endTime >= NOW( )",
                DspAdvExtend.class);
        List<DspAdvExtend> appAdvList = new ArrayList<>();
        List<DspAdvExtend> h5AdvList = new ArrayList<>();
        List<DspAdvExtend> pcAdvList = new ArrayList<>();
        String terminal = "", adType = "", k_advId = "", k_deliveryId = "";
        Map<String, DspAdvExtend> advIdMapTmp = new ConcurrentHashMap<>(32);
        Map<String, DspAdvExtend> deliveryIdMapTmp = new ConcurrentHashMap<>(32);
        for (DspAdvExtend dspAdvExtend : dspAdvs){
            terminal = dspAdvExtend.getTerminal();
            adType = PropertiesLoader.getProperty(dspAdvExtend.getAdStyle());
            List<DspAdvExtend.Img> imglist = new ArrayList<>();
            DspAdvExtend.Img img = dspAdvExtend.new Img(dspAdvExtend.getImg1Path(),320, 240);
            imglist.add(img);
            //group
            if ("3".equals(adType)) {
                img = dspAdvExtend.new Img(dspAdvExtend.getImg2Path(),320, 240);
                imglist.add(img);
                img = dspAdvExtend.new Img(dspAdvExtend.getImg3Path(),320, 240);
                imglist.add(img);
            } else if(!"2".equals(adType)) {
                img.setImgwidth(500);
                img.setImgheight(250);
                imglist.add(img);
            }
            dspAdvExtend.setLbimg(imglist);
            dspAdvExtend.setMiniimg(imglist);
            if ("1".equals(adType)){
                dspAdvExtend.setIspicnews("1");
            } else {
                dspAdvExtend.setIspicnews("0");
            }
            dspAdvExtend.setIsadv("1");
            dspAdvExtend.setIsmonopolyad(false);
            dspAdvExtend.setPlatform("dongfang");
            dspAdvExtend.setChargeway("CPM");
            if (terminal.toLowerCase().indexOf("app") != -1){
                dspAdvExtend.setAllowStations("北京,安徽,福建,甘肃,广东,广西,贵州,海南,河北,河南,黑龙江,湖北,湖南,吉林,江苏,江西,辽宁,内蒙古,宁夏,青海,山东,山西,陕西,上海,四川,天津,西藏,新疆,云南,浙江,重庆,香港,澳门,台湾");
                dspAdvExtend.setIscustomtime(0);
                dspAdvExtend.setSex(-1);
                dspAdvExtend.setDeliveryOs("Android,iOS");
                dspAdvExtend.setShowtime(3);
                dspAdvExtend.setShowrep(Arrays.asList(dspAdvExtend.getShowbackurl().split("@_@")));
                dspAdvExtend.setClickrep(Arrays.asList(dspAdvExtend.getClickbackurl().split("@_@")));
                appAdvList.add(dspAdvExtend);
            }
            if (terminal.toLowerCase().indexOf("h5") != -1){
                dspAdvExtend.setAllowStations("all");
                dspAdvExtend.setDeliveryOs("all");
                dspAdvExtend.setInviewbackurl(dspAdvExtend.getShowbackurl());
                h5AdvList.add(dspAdvExtend);
            }
            if (terminal.toLowerCase().indexOf("pc") != -1){
                pcAdvList.add(dspAdvExtend);
            }
            for (String tml : terminal.split(",|，")){
                k_advId = String.format("%s%s%s", tml, dspAdvExtend.getAdv_id(), dspAdvExtend.getDspId());
                k_deliveryId = String.format("%s%s%s", tml, dspAdvExtend.getDeliveryid(), dspAdvExtend.getDspId());
                //dsp方唯一标识 advid+dspid
                advIdMapTmp.put(k_advId, dspAdvExtend);
                //adx方唯一标识  deliveryId+dspid
                deliveryIdMapTmp.put(k_deliveryId, dspAdvExtend);
            }

        }
        terminalAds.put("app", appAdvList);
        terminalAds.put("h5", h5AdvList);
        terminalAds.put("pc", pcAdvList);
        advidAds = advIdMapTmp;
        deliveryIdAds = deliveryIdMapTmp;
        log.debug("dspAdvs: {}\tsize: {}", dspAdvs, dspAdvs.size());
    }


    @Override
    public List<DspAdvExtend> getDspAdvInfos(String terminal) {
        if (StringUtils.isBlank(terminal)){
            return new ArrayList<>();
        }
        return terminalAds.get(terminal);
    }

    @Override
    public DspAdvExtend getDspAdvByHisIdDspId(String terminal, String hisId, String dspId) {
        String tml_hisId_dspId = String.format("%s%s%s", terminal, hisId, dspId);
        if (StringUtils.isBlank(tml_hisId_dspId)){
            return new DspAdvExtend();
        }
        return deliveryIdAds.get(tml_hisId_dspId);
    }

    @Override
    public DspAdvExtend getDspAdvByAdvIdDspId(String terminal, String advId, String dspId) {
        String tml_advId_dspId = String.format("%s%s%s", terminal, advId, dspId);
        if (StringUtils.isBlank(tml_advId_dspId)){
            return new DspAdvExtend();
        }
        return advidAds.get(tml_advId_dspId);
    }

}
