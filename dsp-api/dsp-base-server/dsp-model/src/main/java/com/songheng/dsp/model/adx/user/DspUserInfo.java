package com.songheng.dsp.model.adx.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DspUserInfo {
	private String dspid;
	private String token;
	private double banlance;
	private double point;
	private String mappingurl;
	private String bidurl	;
	private String winnoticeurl	;
	private Integer h5Qps;
	private Integer appQps;
	private Integer pcQps;
	private String nocmresponse	;
	private String usedfuserinfo;
	private String rtbmsgformat;
	private String priority;
	private String imei_sendreq;
	private String noimei_sendreq;
	/**
	 * 是否自家dsp
	 **/
	private boolean isOneselfDsp;


	public DspUserInfo(){
		this.isOneselfDsp = false;
		//默认值
		this.pcQps = 100;
		this.h5Qps = 100;
		this.appQps = 100;

	}
	public DspUserInfo(String dspid, String token, String bidurl) {
		this();
		this.dspid = dspid;
		this.token = token;
		this.bidurl = bidurl;
	}

	
}
