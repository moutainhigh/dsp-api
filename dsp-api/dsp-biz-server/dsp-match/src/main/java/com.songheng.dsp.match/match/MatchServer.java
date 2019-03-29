package com.songheng.dsp.match.match;

import com.songheng.dsp.model.client.MatchClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;

/**
 * @description: 广告匹配服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 11:30
 **/
public abstract class MatchServer {


    public void match(MatchClientRequest request){
        //投放应用匹配 apptypeId
        matchAppTypeId(request.getBaseFlow());
        //投放渠道匹配 qids

        //操作系统匹配 os

        //客户端需要的样式匹配

        //投放区域匹配 province

        //屏蔽区域匹配 shieldArea

        //年龄组匹配 ageGroup

        //性别匹配  sex

        //投放栏位匹配  newsClassifys

        //已安装应用匹配  installs

        //未安装应用匹配 notinstalls

        //网络环境匹配  network

        //手机型号匹配 vendor

        //运营商匹配 operator

        //兴趣趋向匹配 interesttendency

        //点击标签匹配 clickHisLabel

        //重定向匹配 redirect

        //展现频次匹配 consumeInfo

        //首刷匹配

        //隐退广告匹配

        //投放兴趣匹配

        //广告位匹配
    }

    private void matchAppTypeId(BaseFlow baseFlow) {
    }

}
