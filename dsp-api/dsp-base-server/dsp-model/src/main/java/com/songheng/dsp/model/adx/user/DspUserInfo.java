package com.songheng.dsp.model.adx.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: luoshaobing
 * @date: 2019/3/8 17:22
 * @description:
 */
@Getter
@Setter
@ToString
public class DspUserInfo implements Serializable {

	private static final long serialVersionUID = -5690285271015173819L;

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
	/**
	 * 终端 h5/app
	 */
	private String terminal;


	public DspUserInfo(){
		this.isOneselfDsp = false;
		//TODO qps 默认值
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
