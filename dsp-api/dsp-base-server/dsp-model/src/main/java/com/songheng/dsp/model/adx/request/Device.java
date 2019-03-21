package com.songheng.dsp.model.adx.request;

import com.songheng.dsp.model.flow.BaseFlow;
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

	public Device(BaseFlow baseFlow) {
		this.ua = baseFlow.getUa();
		this.ip = baseFlow.getRemoteIp();
		this.serverip = "";
		this.model = baseFlow.getModel();
		this.os = baseFlow.getOs();
		this.osver = baseFlow.getOsAndVersion();
		this.net = baseFlow.getNetwork();
		this.imsi = baseFlow.getImsi();
		this.isp = baseFlow.getIsp();
	}
}
