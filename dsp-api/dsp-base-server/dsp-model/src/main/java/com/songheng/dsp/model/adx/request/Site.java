package com.songheng.dsp.model.adx.request;
import com.songheng.dsp.common.utils.Md5Utils;
import com.songheng.dsp.model.flow.BaseFlow;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Site {
	/**
	 * 站点名称 MD5加密
	 * */
	private String id;
	/**
	 * 站点名称
	 * */
	private String name;
	/**
	 * 站点包名 或者 网站首页url
	 * */
	private String bundle;
	/**
	 * 当前访问的新闻url
	 * */
	private String page;
	/**
	 * 渠道号 MD5加密
	 * */
	private String qid;
	/**
	 * 请求页面类型 list/detail
	 * */
	private String reqtype;
	/**
	 * 用户最新访问的新闻url后缀
	 * */
	private String readhistory;
	/**
	 * 用户访问的新闻类别
	 * */
	private String newstype;
	/**
	 * 终端类型 h5,app,pc
	 * */
	private String flowtype;
	public Site(BaseFlow baseFlow) {
		this.id = Md5Utils.MD5(baseFlow.getAppName());
		this.name = baseFlow.getAppName();
		this.bundle = baseFlow.getAppId();
		this.page = baseFlow.getNewsurl();
		this.qid = Md5Utils.MD5(baseFlow.getQid());
		this.readhistory = baseFlow.getVisitHistory();
		this.reqtype = baseFlow.getPgType();
		this.newstype = baseFlow.getNewsClassify();

		this.flowtype = baseFlow.getTerminal();
	}
}
