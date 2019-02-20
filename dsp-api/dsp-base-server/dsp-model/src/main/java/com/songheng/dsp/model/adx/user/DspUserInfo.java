package com.songheng.dsp.model.adx.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DspUserInfo {
	private String dspid;
	private String token;
	private double banlance;
	private double point;
	private String mappingurl;
	private String bidurl	;
	private String winnoticeurl	;
	private String qps;
	private String nocmresponse	;
	private String usedfuserinfo;
	private String rtbmsgformat;
	private String priority;
	private String imei_sendreq;
	private String noimei_sendreq;

	public DspUserInfo(){}
	public DspUserInfo(String dspid, String token, String bidurl) {
		super();
		this.dspid = dspid;
		this.token = token;
		this.bidurl = bidurl;
	}
	public String getPriority() {
		return priority;
	}
	@Override
	public String toString() {
		return this.dspid + "_" + this.banlance + "_" + this.bidurl;
	}
	
}
