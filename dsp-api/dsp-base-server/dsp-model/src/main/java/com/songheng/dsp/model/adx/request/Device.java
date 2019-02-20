package com.songheng.dsp.model.adx.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 设备信息
 * @author zhangshuai@021.com
 * */
@Getter
@Setter
@ToString
public class Device {
	private String ua;
	private String ip;
	private String serverip;
	private String model;
	private String os;
	private String osver;
	private String net;
	private String imsi;
	private String isp;

	public Device(String ua, String ip, String os, String net, String model, String osver) {
		super();
		this.ua = ua;
		this.ip = ip;
		this.serverip = "";
		this.model = model;
		this.os = os;
		this.osver = osver;
		switch (net) {
			case "wifi":this.net="100";break;
			case "5g":this.net="5";break;
			case "4g":this.net="4";break;
			case "3g":this.net="3";break;
			case "2g":this.net="2";break;
			default:this.net="0";break;
		}
		this.imsi = "";
		this.isp = "0";
	}
}
