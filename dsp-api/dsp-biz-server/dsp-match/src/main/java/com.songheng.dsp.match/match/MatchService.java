package com.songheng.dsp.match.match;

import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.client.MatchClientRequest;
import com.songheng.dsp.model.materiel.MaterielDirect;

import java.util.Iterator;
import java.util.List;

/**
 * @description: 广告匹配服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 11:30
 **/
public abstract class MatchService {

    public boolean matchAdvInfo(MatchClientRequest request){
        MaterielDirect next = request.getAdv();
        //基础匹配
        if(!baseMatch(request,next)){
           return false;
        }
        //高级匹配
        if(!highMatch(request,next)){
            return false;
        }
        return true;

    }
    /**
     * 匹配基础信息
     * @param request
     * @param next
     * @return
     */
    private boolean baseMatch(MatchClientRequest request,MaterielDirect next){
        //展现频次匹配 consumeInfo
        if(!matchFrequency(request,next)){ return false;}
        //投放应用匹配
        if(!matchAppTypeId(request,next)){return false;}
        //投放渠道匹配 qids
        if(!matchQid(request,next)){return false;}
        //操作系统匹配 os
        if(!matchOs(request,next)){ return false;}
        //客户端需要的样式匹配
        if(!matchStyle(request,next)){return false;}
        //投放区域匹配 province
        if(!matchArea(request,next)){return false;}
        //投放栏位匹配  newsClassify
        if(!matchNewsClassify(request,next)){return false;}
        //广告位匹配
        if(!matchTagId(request,next)){return false;}
        return true;
    }

    /**
     * 高级匹配
     * @param request
     * @param next
     * @return
     */
    private boolean highMatch(MatchClientRequest request,MaterielDirect next){
        //年龄组匹配 ageGroup
        if(!matchAgeGroup(request,next)){return false;}
        //性别匹配  sex
        if(!matchSex(request,next)){return false; }
        //已安装应用匹配  installs
        if(!matchInstalls(request,next)){return false;}
        //未安装应用匹配 notinstalls
        if(!matchNotInstalls(request,next)){return false;}

        //网络环境匹配  network
        if(!matchNetWork(request,next)){return false;}
        //手机型号匹配 vendor
        if(!matchVendor(request,next)){return false;}
        //运营商匹配 operator
        if(!matchOperator(request,next)){return false;}

        //兴趣趋向匹配 interesttendency

        //点击标签匹配 clickHisLabel

        //重定向匹配 redirect

        //首刷匹配

        //隐退广告匹配

        //投放兴趣匹配

        return true;
    }

    /**
     * 匹配频次控制
     * @param request
     * @param next
     * @return
     */
    protected abstract boolean matchFrequency(MatchClientRequest request, MaterielDirect next);


    /**
     * 匹配运营商
     * @param request
     * @param next
     * @return
     */
    protected abstract boolean matchOperator(MatchClientRequest request, MaterielDirect next);

    /**
     * 匹配手机型号
     * @param request
     * @param next
     * @return
     */
    protected abstract boolean matchVendor(MatchClientRequest request, MaterielDirect next);

    /**
     * 匹配网络环境
     * @param request
     * @param next
     * @return
     */
    protected abstract boolean matchNetWork(MatchClientRequest request, MaterielDirect next);

    /**
     * 匹配未安装的应用
     * @param request
     * @param next
     * @return
     */
    protected abstract boolean matchNotInstalls(MatchClientRequest request, MaterielDirect next);


    /**
     * 匹配安装的应用
     * @param request
     * @param next
     * @return
     */
    protected abstract boolean matchInstalls(MatchClientRequest request, MaterielDirect next);

    /**
     * 匹配性别
     * @param request
     * @param next
     * @return
     */
    protected abstract boolean matchSex(MatchClientRequest request, MaterielDirect next);

    /**
     * 匹配年龄组
     * @param request
     * @param next
     * @return
     */
    protected abstract boolean matchAgeGroup(MatchClientRequest request, MaterielDirect next);

    private boolean matchTagId(MatchClientRequest request,MaterielDirect adv) {
        //非自由竞价类型的广告
        if(request.getBidModel()!=0){
            List<String> limitPosition = StringUtils.strToList(adv.getLimitPosition());
            if(!limitPosition.contains(request.getTagId())){
                return false;
            }
        }
        return true;

    }





    private boolean matchNewsClassify(MatchClientRequest request,MaterielDirect adv) {
        return true;
    }

    private boolean matchArea(MatchClientRequest request,MaterielDirect adv) {
        return true;
    }

    private boolean matchStyle(MatchClientRequest request,MaterielDirect adv) {
        return true;
    }

    private boolean matchOs(MatchClientRequest request,MaterielDirect adv) {
        return true;
    }

    private boolean matchQid(MatchClientRequest request,MaterielDirect adv) {
        return true;
    }

    private boolean matchAppTypeId(MatchClientRequest request,MaterielDirect adv) {
        return true;
    }



}
