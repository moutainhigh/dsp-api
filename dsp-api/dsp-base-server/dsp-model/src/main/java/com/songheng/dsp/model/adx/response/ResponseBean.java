package com.songheng.dsp.model.adx.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
public class ResponseBean {
	/**
	 * 请求id
	 */
	private String id;
	/**
	 * response id
	 */
	private String bidid;
	/**
	 * dsp出价
	 */
	private List<BidBean> seatbid;
	/**
	 * dsp_id
	 */
	private String dsp_id;
	/**
	 * 从请求到响应的时间,ms
	 */
	private long processing_time_ms;
	
	/**
	 * 是否包位置广告
	 */
	private boolean ismonopolyad;
	
	/**
	 * 包位置的下标 
	 */
	private int monopolyidx;

	/**
     * 包位置pid
     * */
	private String pid;

	public ResponseBean(){
		this.ismonopolyad = false;
		this.monopolyidx = 0;
	}
	public ResponseBean(String id, String bidid, List<BidBean> seatbid) {
		super();
		this.ismonopolyad = false;
		this.monopolyidx = 0;
		this.id = id;
		this.bidid = bidid;
		this.seatbid = seatbid;
		this.dsp_id = "";
	}

	@Override
	public String toString() {
		return this.seatbid.get(0).getAdm();
	}
}
