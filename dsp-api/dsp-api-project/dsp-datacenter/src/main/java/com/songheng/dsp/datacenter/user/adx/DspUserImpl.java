package com.songheng.dsp.datacenter.user.adx;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.dubbo.baseinterface.user.adx.DspUserService;
import com.songheng.dsp.model.adx.user.DspUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/2/25 16:58
 * @description: DSP用户池缓存接口实现类
 */
@Slf4j
@Component
public class DspUserImpl implements DspUserService {

    /**
     * dsp users cache
     * key: app,h5,pc
     * value: List<DspUserInfo>
     */
    private volatile static Map<String, List<DspUserInfo>> dspUserInfoMap = new ConcurrentHashMap<>(6);
    /**
     * dsp users cache
     * key: app,h5,pc+dspId
     * value: List<DspUserInfo>
     */
    private volatile static Map<String, List<DspUserInfo>> dspUserInfoDspIdMap = new ConcurrentHashMap<>(32);
    /**
     * dsp users cache
     * key: app,h5,pc+priority
     * value: List<DspUserInfo>
     */
    private volatile static Map<String, List<DspUserInfo>> dspUserInfoPriorityMap = new ConcurrentHashMap<>(32);

    /**
     * 更新dsp用户
     */
    public void updateDspUsers(){
        List<DspUserInfo> users = DbUtils.queryList("SELECT" +
                "\tdspid," +
                "\ttoken," +
                "\tbanlance," +
                "\tpoint," +
                "\tmappingurl," +
                "\tbidurl," +
                "\twinnoticeurl," +
                "\tqps AS h5Qps," +
                "\tapp_qps AS appQps," +
                "\tnocmresponse," +
                "\tusedfuserinfo," +
                "\trtbmsgformat," +
                "\tpriority," +
                "\tterminal," +
                "\timei_sendreq," +
                "\tnoimei_sendreq" +
                "\tFROM adx_dspuser" +
                "\tWHERE flag = 1" +
                "\tAND banlance > 0", DspUserInfo.class);
        List<DspUserInfo> appUserList = new ArrayList<>();
        List<DspUserInfo> h5UserList = new ArrayList<>();
        List<DspUserInfo> pcUserList = new ArrayList<>();
        String terminal = "", k_dspId = "", k_priority = "";
        Map<String, List<DspUserInfo>> dspIdMapTmp = new ConcurrentHashMap<>(32);
        Map<String, List<DspUserInfo>> priorityMapTmp = new ConcurrentHashMap<>(32);
        for (DspUserInfo userInfo : users){
            terminal = userInfo.getTerminal();
            if (terminal.toLowerCase().indexOf("app") != -1){
                //设置h5,pc qps初始值
                userInfo.setH5Qps(100);
                userInfo.setPcQps(100);
                appUserList.add(userInfo);
            }
            if (terminal.toLowerCase().indexOf("h5") != -1){
                //设置app,pc qps初始值
                userInfo.setAppQps(100);
                userInfo.setPcQps(100);
                h5UserList.add(userInfo);
            }
            if (terminal.toLowerCase().indexOf("pc") != -1){
                //设置app,h5 qps初始值
                userInfo.setAppQps(100);
                userInfo.setH5Qps(100);
                pcUserList.add(userInfo);
            }
            for (String tml : terminal.split(",|，")){
                k_dspId = String.format("%s%s", tml, userInfo.getDspid());
                k_priority = String.format("%s%s", tml, userInfo.getPriority());
                if (dspIdMapTmp.containsKey(k_dspId)){
                    dspIdMapTmp.get(k_dspId).add(userInfo);
                } else {
                    List<DspUserInfo> tmp_dspId = new ArrayList<>();
                    tmp_dspId.add(userInfo);
                    dspIdMapTmp.put(k_dspId, tmp_dspId);
                }
                if (!priorityMapTmp.containsKey(k_priority)){
                    List<DspUserInfo> tmp_priority = new ArrayList<>();
                    tmp_priority.add(userInfo);
                    priorityMapTmp.put(k_priority, tmp_priority);
                } else {
                    priorityMapTmp.get(k_priority).add(userInfo);
                }
            }
        }
        if (appUserList.size() > 0){
            dspUserInfoMap.put("app", appUserList);
        }
        if (h5UserList.size() > 0){
            dspUserInfoMap.put("h5", h5UserList);
        }
        if (pcUserList.size() > 0){
            dspUserInfoMap.put("pc", pcUserList);
        }
        if (dspIdMapTmp.size() > 0){
            dspUserInfoDspIdMap = dspIdMapTmp;
        }
        if (priorityMapTmp.size() > 0){
            dspUserInfoPriorityMap = priorityMapTmp;
        }
        log.debug("dspUserSize: {}\tappUserSize: {}\th5UserSize: {}\tpcUserSize: {}",
                users.size(), appUserList.size(), h5UserList.size(), pcUserList.size());
    }

    /**
     * 根据terminal 获取 List<DspUserInfo>
     * @param terminal
     * @return
     */
    @Override
    public List<DspUserInfo> getDspUsers(String terminal){
        if (StringUtils.isBlank(terminal)){
            return new ArrayList<>();
        }
        return dspUserInfoMap.get(terminal.toLowerCase());
    }

    /**
     * 根据terminal,dspId 获取 List<DspUserInfo>
     * @param terminal
     * @param dspId
     * @return
     */
    @Override
    public List<DspUserInfo> getDspUsersByDspId(String terminal, String dspId){
        String tml_dspId = String.format("%s%s", terminal, dspId);
        if (StringUtils.isBlank(tml_dspId)){
            return new ArrayList<>();
        }
        return dspUserInfoDspIdMap.get(tml_dspId);
    }

    /**
     * 根据terminal,priority 获取 List<DspUserInfo>
     * @param terminal
     * @param priority
     * @return
     */
    @Override
    public List<DspUserInfo> getDspUsersByPriority(String terminal, String priority){
        String tml_priority = String.format("%s%s", terminal, priority);
        if (StringUtils.isBlank(tml_priority)){
            return new ArrayList<>();
        }
        return dspUserInfoPriorityMap.get(tml_priority);
    }
}
