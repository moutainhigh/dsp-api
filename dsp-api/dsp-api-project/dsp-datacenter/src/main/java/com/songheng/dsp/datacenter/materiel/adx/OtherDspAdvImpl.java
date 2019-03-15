package com.songheng.dsp.datacenter.materiel.adx;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.datacenter.config.props.PropertiesLoader;
import com.songheng.dsp.dubbo.baseinterface.materiel.adx.OtherDspAdvService;
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
 * @description: 第三方DSP广告池缓存接口实现类
 */
@Slf4j
@Component
public class OtherDspAdvImpl implements OtherDspAdvService {

    /**
     * key: app,h5,pc
     * value: List<DspAdvExtend>
     */
    private volatile Map<String, List<DspAdvExtend>> terminalAds = new ConcurrentHashMap<>(6);
    /**
     * key: app,h5,pc+advid+dspid
     * value DspAdvExtend
     */
    private volatile Map<String, DspAdvExtend> advidAds = new ConcurrentHashMap<>(16);
    /**
     * key: app,h5,pc+deliveryid+dspid
     * value DspAdvExtend
     */
    private volatile Map<String, DspAdvExtend> deliveryIdAds = new ConcurrentHashMap<>(16);

    /**
     * 更新第三方DSP广告池
     */
    public void updateDspAdvs(){
        String sql = SqlMapperLoader.getSql("OtherDspAdv", "queryOtherDspAdvs");
        if (StringUtils.isBlank(sql)){
            log.error("updateDspAdvs error sql is null, namespace: OtherDspAdv, id: queryOtherDspAdvs");
            return;
        }
        List<DspAdvExtend> dspAdvs = DbUtils.queryList(sql, DspAdvExtend.class);
        List<DspAdvExtend> appAdvList = new ArrayList<>();
        List<DspAdvExtend> h5AdvList = new ArrayList<>();
        List<DspAdvExtend> pcAdvList = new ArrayList<>();
        String terminal = "", adStyle = "", k_advId = "", k_deliveryId = "";
        Map<String, DspAdvExtend> advIdMapTmp = new ConcurrentHashMap<>(1024);
        Map<String, DspAdvExtend> deliveryIdMapTmp = new ConcurrentHashMap<>(1024);
        for (DspAdvExtend dspAdvExtend : dspAdvs){
            terminal = dspAdvExtend.getTerminal();
            adStyle = PropertiesLoader.getProperty(dspAdvExtend.getAdStyle());
            List<DspAdvExtend.Img> imglist = new ArrayList<>();
            DspAdvExtend.Img img = dspAdvExtend.new Img(dspAdvExtend.getImg1Path(),320, 240);
            imglist.add(img);
            //group
            if ("3".equals(adStyle)) {
                img = dspAdvExtend.new Img(dspAdvExtend.getImg2Path(),320, 240);
                imglist.add(img);
                img = dspAdvExtend.new Img(dspAdvExtend.getImg3Path(),320, 240);
                imglist.add(img);
            } else if(!"2".equals(adStyle)) {
                img.setImgwidth(500);
                img.setImgheight(250);
                imglist.add(img);
            }
            dspAdvExtend.setIsadv("1");
            dspAdvExtend.setIsmonopolyad(false);
            dspAdvExtend.setPlatform("dongfang");
            dspAdvExtend.setChargeway("CPM");
            if (terminal.toLowerCase().indexOf("app") != -1){
                if ("1".equals(adStyle)){
                    dspAdvExtend.setBigpic("1");
                    dspAdvExtend.setLbimg(imglist);
                } else {
                    dspAdvExtend.setBigpic("0");
                    dspAdvExtend.setMiniimg(imglist);
                }
                dspAdvExtend.setType(adStyle);
                dspAdvExtend.setAllowStations("北京,安徽,福建,甘肃,广东,广西,贵州,海南,河北,河南,黑龙江,湖北,湖南,吉林,江苏,江西,辽宁,内蒙古,宁夏,青海,山东,山西,陕西,上海,四川,天津,西藏,新疆,云南,浙江,重庆,香港,澳门,台湾");
                dspAdvExtend.setIscustomtime(0);
                dspAdvExtend.setSex(-1);
                dspAdvExtend.setDeliveryOs("Android,iOS");
                dspAdvExtend.setAdtype(0);
                dspAdvExtend.setShowtime(3);
                dspAdvExtend.setShowrep(Arrays.asList(dspAdvExtend.getShowbackurl().split("@_@")));
                dspAdvExtend.setClickrep(Arrays.asList(dspAdvExtend.getClickbackurl().split("@_@")));
                appAdvList.add(dspAdvExtend);
            }
            if (terminal.toLowerCase().indexOf("h5") != -1){
                dspAdvExtend.setIspicnews("1".equals(adStyle)?"1":"0");
                switch (adStyle) {
                    case "1": adStyle = "big";break;
                    case "2": adStyle = "one";break;
                    case "3": adStyle = "group";break;
                    case "6": adStyle = "full";break;
                    default: adStyle = "big";break;
                }
                dspAdvExtend.setAdStyle(adStyle);
                dspAdvExtend.setLbimg(imglist);
                dspAdvExtend.setMiniimg(imglist);
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
        if (appAdvList.size() > 0){
            terminalAds.put("app", appAdvList);
        }
        if (h5AdvList.size() > 0){
            terminalAds.put("h5", h5AdvList);
        }
        if (pcAdvList.size() > 0){
            terminalAds.put("pc", pcAdvList);
        }
        if (advIdMapTmp.size() > 0){
            advidAds = advIdMapTmp;
        }
        if (deliveryIdMapTmp.size() > 0){
            deliveryIdAds = deliveryIdMapTmp;
        }
        log.debug("otherDspAdvSize: {}\tappAdvSize: {}\th5AdvSize: {}\tpcAdvSize: {}",
                dspAdvs.size(), appAdvList.size(), h5AdvList.size(), pcAdvList.size());
    }


    @Override
    public List<DspAdvExtend> getDspAdvInfos(String terminal) {
        if (StringUtils.isBlank(terminal)){
            return new ArrayList<>();
        }
        List<DspAdvExtend> result = terminalAds.get(terminal);
        return null != result ? result : new ArrayList<DspAdvExtend>();
    }

    @Override
    public DspAdvExtend getDspAdvByHisIdDspId(String terminal, String hisId, String dspId) {
        String tml_hisId_dspId = String.format("%s%s%s", terminal, hisId, dspId);
        if (StringUtils.isBlank(tml_hisId_dspId)){
            return new DspAdvExtend();
        }
        DspAdvExtend dspAdvExtend = deliveryIdAds.get(tml_hisId_dspId);
        return null != dspAdvExtend ? dspAdvExtend : new DspAdvExtend();
    }

    @Override
    public DspAdvExtend getDspAdvByAdvIdDspId(String terminal, String advId, String dspId) {
        String tml_advId_dspId = String.format("%s%s%s", terminal, advId, dspId);
        if (StringUtils.isBlank(tml_advId_dspId)){
            return new DspAdvExtend();
        }
        DspAdvExtend dspAdvExtend = advidAds.get(tml_advId_dspId);
        return null != dspAdvExtend ? dspAdvExtend : new DspAdvExtend();
    }

}
