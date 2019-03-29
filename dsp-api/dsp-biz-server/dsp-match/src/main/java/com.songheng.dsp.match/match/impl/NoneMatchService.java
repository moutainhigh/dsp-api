package com.songheng.dsp.match.match.impl;

import com.songheng.dsp.match.match.MatchService;
import com.songheng.dsp.model.client.MatchClientRequest;
import com.songheng.dsp.model.materiel.MaterielDirect;

/**
 * @description: 不执行匹配策略
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 14:55
 **/
public class NoneMatchService extends MatchService {
    @Override
    protected boolean matchFrequency(MatchClientRequest request, MaterielDirect next) {
        return true;
    }

    @Override
    protected boolean matchOperator(MatchClientRequest request, MaterielDirect next) {
        return true;
    }

    @Override
    protected boolean matchVendor(MatchClientRequest request, MaterielDirect next) {
        return true;
    }

    @Override
    protected boolean matchNetWork(MatchClientRequest request, MaterielDirect next) {
        return true;
    }

    @Override
    protected boolean matchNotInstalls(MatchClientRequest request, MaterielDirect next) {
        return true;
    }

    @Override
    protected boolean matchInstalls(MatchClientRequest request, MaterielDirect next) {
        return true;
    }

    @Override
    protected boolean matchSex(MatchClientRequest request, MaterielDirect next) {
        return true;
    }

    @Override
    protected boolean matchAgeGroup(MatchClientRequest request, MaterielDirect next) {
        return true;
    }
}
