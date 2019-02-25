package com.songheng.dsp.datacenter.common.adx.impl;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.dubbo.baseinterface.common.adx.service.DspUserService;
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
 * @description: DSP用户池
 */
@Slf4j
@Component
public class DspUserImpl implements DspUserService{

    /**
     * dsp users cache
     * key: app,h5,pc
     * value: List<DspUserInfo>
     */
    private volatile static Map<String, List<DspUserInfo>> dspUserInfoMap = new ConcurrentHashMap<>();
    /**
     * dspUserInfoList
     */
    private static List<DspUserInfo> dspUserInfoList = new ArrayList<>();

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
        if (null == users){
            dspUserInfoList = new ArrayList<>();
        } else {
            dspUserInfoList = users;
        }
        List<DspUserInfo> appUserList = new ArrayList<>();
        List<DspUserInfo> h5UserList = new ArrayList<>();
        List<DspUserInfo> pcUserList = new ArrayList<>();
        for (DspUserInfo userInfo : dspUserInfoList){
            if (userInfo.getTerminal().toLowerCase().indexOf("app") != -1){
                //设置h5,pc qps初始值
                userInfo.setH5Qps(100);
                userInfo.setPcQps(100);
                appUserList.add(userInfo);
            }
            if (userInfo.getTerminal().toLowerCase().indexOf("h5") != -1){
                //设置app,pc qps初始值
                userInfo.setAppQps(100);
                userInfo.setPcQps(100);
                h5UserList.add(userInfo);
            }
            if (userInfo.getTerminal().toLowerCase().indexOf("pc") != -1){
                //设置app,h5 qps初始值
                userInfo.setAppQps(100);
                userInfo.setH5Qps(100);
                pcUserList.add(userInfo);
            }
        }
        dspUserInfoMap.put("app", appUserList);
        dspUserInfoMap.put("h5", h5UserList);
        dspUserInfoMap.put("pc", pcUserList);
        log.debug("user: {}\tsize: {}", users, users.size());
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
        return dspUserInfoMap.get(terminal);
    }

    /**
     * 根据terminal,dspId 获取 List<DspUserInfo>
     * @param terminal
     * @param dspId
     * @return
     */
    @Override
    public List<DspUserInfo> getDspUsersByDspId(String terminal, String dspId){
        List<DspUserInfo> dspUserInfoList = new ArrayList<>();
        if (StringUtils.isBlank(terminal) || StringUtils.isBlank(dspId)){
            return dspUserInfoList;
        }
        List<DspUserInfo> tmp = dspUserInfoMap.get(terminal);
        if (null != tmp && tmp.size() > 0){
            for (DspUserInfo userInfo : tmp){
                if (dspId.equals(userInfo.getDspid())){
                    dspUserInfoList.add(userInfo);
                }
            }
        }
        return dspUserInfoList;
    }

    /**
     * 根据terminal,priority 获取 List<DspUserInfo>
     * @param terminal
     * @param priority
     * @return
     */
    @Override
    public List<DspUserInfo> getDspUsersByPriority(String terminal, String priority){
        List<DspUserInfo> dspUserInfoList = new ArrayList<>();
        if (StringUtils.isBlank(terminal) || StringUtils.isBlank(priority)){
            return dspUserInfoList;
        }
        List<DspUserInfo> tmp = dspUserInfoMap.get(terminal);
        if (null != tmp && tmp.size() > 0){
            for (DspUserInfo userInfo : tmp){
                if (priority.equals(userInfo.getPriority())){
                    dspUserInfoList.add(userInfo);
                }
            }
        }
        return dspUserInfoList;
    }
}
