package com.songheng.dsp.datacenter.materiel.adx;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.datacenter.config.props.PropertiesLoader;
import com.songheng.dsp.dubbo.baseinterface.materiel.adx.OtherDspAdvService;
import com.songheng.dsp.model.materiel.MaterielDirect;
import com.songheng.dsp.model.materiel.MaterielImgInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/2/26 16:57
 * @description: 第三方DSP广告池缓存接口实现类
 */
@Slf4j
@Service(interfaceClass = OtherDspAdvService.class,
        timeout = 1000)
@Component
public class OtherDspAdvImpl implements OtherDspAdvService{

    /**
     * key: app,h5,pc
     * value: List<MaterielDirect>
     */
    private volatile Map<String, List<MaterielDirect>> terminalAds = new ConcurrentHashMap<>(6);
    /**
     * key: app,h5,pc+advid+dspid
     * value DspAdvExtend
     */
    private volatile Map<String, MaterielDirect> advidAds = new ConcurrentHashMap<>(16);
    /**
     * key: app,h5,pc+deliveryid+dspid
     * value DspAdvExtend
     */
    private volatile Map<String, MaterielDirect> deliveryIdAds = new ConcurrentHashMap<>(16);

    /**
     * 更新第三方DSP广告池
     */
    public void updateDspAdvs(){
        String sql = SqlMapperLoader.getSql("OtherDspAdv", "queryOtherDspAdvs");
        if (StringUtils.isBlank(sql)){
            log.error("updateDspAdvs error sql is null, namespace: OtherDspAdv, id: queryOtherDspAdvs");
            return;
        }
        List<MaterielDirect> materielDirectList = DbUtils.queryList(ProjectEnum.DATACENTER.getDs()[0], sql, MaterielDirect.class);
        List<MaterielDirect> appAdvList = new ArrayList<>();
        List<MaterielDirect> h5AdvList = new ArrayList<>();
        List<MaterielDirect> pcAdvList = new ArrayList<>();
        String terminal = "", adStyle = "", k_advId = "", k_deliveryId = "";
        Map<String, MaterielDirect> advIdMapTmp = new ConcurrentHashMap<>(1024);
        Map<String, MaterielDirect> deliveryIdMapTmp = new ConcurrentHashMap<>(1024);
        for (MaterielDirect materielDirect : materielDirectList){
            terminal = StringUtils.isNotBlank(materielDirect.getTerminal()) ? materielDirect.getTerminal() : "";
            if (StringUtils.isNotBlank(materielDirect.getAdStyle())){
                adStyle = PropertiesLoader.getProperty(materielDirect.getAdStyle());
            }
            List<MaterielImgInfo> imglist = new ArrayList<>();
            MaterielImgInfo imgInfo = new MaterielImgInfo(materielDirect.getImg1Path(), 320, 240, 0);
            imglist.add(imgInfo);
            //group
            if ("3".equals(adStyle)) {
                imgInfo = new MaterielImgInfo(materielDirect.getImg2Path(), 320, 240, 0);
                imglist.add(imgInfo);
                imgInfo = new MaterielImgInfo(materielDirect.getImg3Path(), 320, 240, 0);
                imglist.add(imgInfo);
            } else if(!"2".equals(adStyle)) {
                imgInfo.setWidth(500);
                imgInfo.setHeight(250);
                imglist.add(imgInfo);
            }
            materielDirect.setChargeway("CPM");
            if (terminal.toLowerCase().indexOf("app") != -1){
                materielDirect.setType(adStyle);
                materielDirect.setSex("-1");
                appAdvList.add(materielDirect);
            }
            if (terminal.toLowerCase().indexOf("h5") != -1){
                switch (adStyle) {
                    case "1": adStyle = "big";break;
                    case "2": adStyle = "one";break;
                    case "3": adStyle = "group";break;
                    case "6": adStyle = "full";break;
                    default: adStyle = "big";break;
                }
                materielDirect.setAdStyle(adStyle);
                h5AdvList.add(materielDirect);
            }
            if (terminal.toLowerCase().indexOf("pc") != -1){
                pcAdvList.add(materielDirect);
            }
            for (String tml : terminal.split(",|，")){
                k_advId = String.format("%s%s%s%s%s", tml, "_", materielDirect.getAdvId(), "_", materielDirect.getDspId());
                k_deliveryId = String.format("%s%s%s%s%s", tml, "_", materielDirect.getDeliveryId(), "_", materielDirect.getDspId());
                //dsp方唯一标识 advid+dspid
                advIdMapTmp.put(k_advId, materielDirect);
                //adx方唯一标识  deliveryId+dspid
                deliveryIdMapTmp.put(k_deliveryId, materielDirect);
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
                materielDirectList.size(), appAdvList.size(), h5AdvList.size(), pcAdvList.size());
    }

    /**
     * 根据terminal 获取 List<MaterielDirect>
     * @param terminal
     * @return
     */
    @Override
    public List<MaterielDirect> getDspAdvInfos(String terminal) {
        if (StringUtils.isBlank(terminal)){
            return new ArrayList<>();
        }
        List<MaterielDirect> result = terminalAds.get(terminal);
        return null != result ? result : new ArrayList<MaterielDirect>();
    }

    /**
     * 根据terminal,hisId,dspId 获取 MaterielDirect
     * @param terminal
     * @param hisId 投放id
     * @param dspId
     * @return
     */
    @Override
    public MaterielDirect getDspAdvByHisIdDspId(String terminal, String hisId, String dspId) {
        String tml_hisId_dspId = String.format("%s%s%s%s%s", terminal, "_", hisId, "_", dspId);
        MaterielDirect dspAdvExtend = deliveryIdAds.get(tml_hisId_dspId);
        return null != dspAdvExtend ? dspAdvExtend : new MaterielDirect();
    }

    /**
     * 根据terminal,advId,dspId 获取 MaterielDirect
     * @param terminal
     * @param advId 物料id
     * @param dspId
     * @return
     */
    @Override
    public MaterielDirect getDspAdvByAdvIdDspId(String terminal, String advId, String dspId) {
        String tml_advId_dspId = String.format("%s%s%s%s%s", terminal, "_", advId, "_", dspId);
        MaterielDirect dspAdvExtend = advidAds.get(tml_advId_dspId);
        return null != dspAdvExtend ? dspAdvExtend : new MaterielDirect();
    }

    /**
     * 获取所有 List<MaterielDirect>
     * @return
     */
    @Override
    public Map<String, List<MaterielDirect>> getDspAdvListMap() {
        return terminalAds;
    }

    /**
     * 获取所有 List<MaterielDirect>
     * @return
     */
    @Override
    public Map<String, MaterielDirect> getDspAdvByHisIdMap() {
        return deliveryIdAds;
    }

    /**
     * 获取所有 List<MaterielDirect>
     * @return
     */
    @Override
    public Map<String, MaterielDirect> getDspAdvByAdvIdMap() {
        return advidAds;
    }

}
