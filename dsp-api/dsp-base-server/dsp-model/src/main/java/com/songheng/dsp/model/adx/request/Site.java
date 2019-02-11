package com.songheng.dsp.model.adx.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Site {
	private String id;
	private String bundle;
	private String name;
	private String page;
	private String qid;
	private String reqtype;
	private String readhistory;
	private String newstype;
	private String flowtype;
	public Site(String name, String page,String qid,String readhistory,String reqtype,String newstype) {
		super();
		this.name = name;
		this.page = page;
		this.qid = qid;
		this.readhistory = readhistory;
		this.reqtype = reqtype;
		this.newstype = newstype;
		this.id = "h5gvCdhbtNPMQpFvLmua";
		this.bundle = "com.songheng.eastnews";
		this.flowtype = "h5";
	}
	public Site() {
		super();
	}
}
