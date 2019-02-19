package com.songheng.dsp.model.adx.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private String id;
	private String imei;
	private String deviceid;
	private String buyerid;
	private String gender;
	private String age;
	public User(String id, String gender, String age,String deviceId) {
		super();
		this.id = id;
		this.imei = id;
		this.buyerid = "";
		this.gender = gender;
		this.age = age;
		this.deviceid = deviceId;
	}
}
