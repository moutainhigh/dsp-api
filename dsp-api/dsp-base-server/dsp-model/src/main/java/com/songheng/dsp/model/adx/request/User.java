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
	public User(String id, String buyerid, String gender, String age,String imei,String deviceid) {
		super();
		this.id = id;
		this.buyerid = buyerid;
		this.gender = gender;
		this.age = age;
		this.imei = imei;
		this.deviceid = deviceid;
	}
	public User() {
		super();
	}
}
