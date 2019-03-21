package com.songheng.dsp.model.adx.request;
import com.songheng.dsp.common.utils.Md5Utils;
import com.songheng.dsp.model.flow.BaseFlow;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	/**
	 * app登录的用户id 或者新闻的userId
	 * */
	private String id;
	/**
	 * 设备唯一Id uid
	 * */
	private String imei;
	/**
	 * 设备id
	 * */
	private String deviceid;
	/**
	 * 购买方id：暂时无用
	 * */
	private String buyerid;
	/**
	 * 性别
	 * */
	private String gender;
	/**
	 * 年龄
	 * */
	private String age;

	public User(BaseFlow baseFlow) {
		this.id = Md5Utils.MD5(baseFlow.getAppUserId());
		this.imei = baseFlow.getUserId();
		this.buyerid = "";
		this.gender = baseFlow.getGender();
		this.age = baseFlow.getAge();
		this.deviceid = Md5Utils.MD5(baseFlow.getAppUserId());
	}
}
