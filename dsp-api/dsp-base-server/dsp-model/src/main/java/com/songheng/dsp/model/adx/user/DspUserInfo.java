package com.songheng.dsp.model.adx.user;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DspUserInfo {
	private String dspid;
	@JSONField(serialize=false)
	private String token;
	@JSONField(serialize=false)
	private double banlance;
	@JSONField(serialize=false)
	private double point;
	@JSONField(serialize=false)
	private String mappingurl;
	@JSONField(serialize=false)
	private String bidurl	;
	@JSONField(serialize=false)
	private String winnoticeurl	;
	@JSONField(serialize=false)
	private String qps;
	@JSONField(serialize=false)
	private String nocmresponse	;
	@JSONField(serialize=false)
	private String usedfuserinfo;
	@JSONField(serialize=false)
	private String rtbmsgformat;
	@JSONField(serialize=false)
	private String priority;
	@JSONField(serialize=false)
	private String imei_sendreq;
	@JSONField(serialize=false)
	private String noimei_sendreq;

	public DspUserInfo(){}
	public DspUserInfo(String dspid, String token, String bidurl) {
		super();
		this.dspid = dspid;
		this.token = token;
		this.bidurl = bidurl;
	}
	@Override
	public String toString() {
		return this.dspid + "_" + this.banlance + "_" + this.bidurl;
	}
	
}
