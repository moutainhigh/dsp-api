package com.songheng.dsp.model.adx.response;

import com.alibaba.fastjson.JSONArray;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidBean {
	/**
	 * bidder response id
	 */
	private String id;
	/**
	 * 对应的曝光 ID
	 */
	private String impid;
	/**
	 * DSP 出价，单位是分/千次曝光，即 CPM
	 */
	private float price;
	/**
	 * 第二高价
	 */
	private float secondPrice;
	/**
	 * 广告投放id
	 */
	private String secondPriceAdm;
	/**
	 * win notice url
	 */
	private String nurl;
	/**
	 * 广告投放id
	 */
	private String adm;
	/**
	 * dsp自定义字段
	 */
	private JSONArray ext;
	/**
	 * 广告位ID
	 */
	private String tagid;
	/**
	 * chargeway:CPC|CPM
	 */
	private String chargeway;


	public BidBean() {
		this.id = "";
		this.impid = "";
		this.price = 0;
		this.nurl = "";
		this.adm = "";
		this.ext = new JSONArray();
	}

	public BidBean(String id, String impid, float price, String nurl, String adm, JSONArray ext) {
		super();
		this.id = id;
		this.impid = impid;
		this.price = price;
		this.nurl = nurl;
		this.adm = adm;
		this.ext = ext;
	}

	@Override
	public String toString() {
		return "BidBean [id=" + id + ", impid=" + impid + ", price=" + price + ", nurl=" + nurl + ", adm=" + adm
				+ ", ext=" + ext + "]";
	}

}
