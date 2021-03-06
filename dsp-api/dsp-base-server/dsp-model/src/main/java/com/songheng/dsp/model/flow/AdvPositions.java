
package com.songheng.dsp.model.flow;


import com.alibaba.fastjson.JSONArray;
import com.songheng.dsp.common.utils.MathUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.enums.DeviceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 广告位
 * @author zhangshuai@021.com
 *
 */
@Setter
@Getter
@ToString
public class AdvPositions {
	/**
	 * 广告位标识(明文) detail_1_1 ......
	 */
	private String pid;
	/**
	 * 广告位id(密文) UUID
	 * */
	private String tagId;
	/**
	 * 对内的广告样式 one/group/big
	 */
	private String style;

	/**
	 * 对外的广告样式 DF_ADX_BIG:500*250*1
	 * */

	private String outerStyle;

	/**
	 * 广告位底价
	 * */
	private double floorPrice;

	/**
	 *页码：自行解析
	 * */
	private int pgNum;

	/**
	 * 下标：自行解析
	 * */
	private int idx;
	/**
	 * 是否验证样式：自行解析
	 * */
	private boolean isVerifyStyle;

	public AdvPositions(){
		this.pgNum = -1;
		this.idx = -1;
		this.isVerifyStyle = false;
		//默认底价1元
		this.floorPrice = MathUtils.round(1);
		this.style = "";
		this.outerStyle = "";
	}

	public static List<AdvPositions> getAdvPositionsByJsonStr(String jsonStr,BaseFlow baseFlow){
		List<AdvPositions> positionInfo = new ArrayList<>();
		try {
			//测试json
			//jsonStr = BaseFlow.getTestAdvPositionJson(baseFlow,jsonStr);
			if(jsonStr!=null && !"".contentEquals(jsonStr.trim())){
				positionInfo =  JSONArray.parseArray(jsonStr,AdvPositions.class);
				parseData(positionInfo,baseFlow.getDeviceType());
			}
			return positionInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return positionInfo;
		}
	}


	/**
	 * 解析页码 和 idx
	 * */
	private static void parseData(List<AdvPositions> positionInfo,String deviceType){
		Iterator<AdvPositions> iterator = positionInfo.iterator();
		while(iterator.hasNext()){
			AdvPositions advPositions = iterator.next();
			if(advPositions.getPid()==null || "".equals(advPositions.getPid().trim())){
				iterator.remove();
				continue;
			}
			//解析页码 和 下标
			String[] pids = advPositions.getPid().split("_");
			if(pids.length == 3 && DeviceType.isMobile(deviceType)){
				try {
					advPositions.setPgNum(Integer.parseInt(pids[1].trim()));
					advPositions.setIdx(Integer.parseInt(pids[2].trim()));
				}catch (NumberFormatException e){
					throw new NumberFormatException("解析请求数据异常,指定位非数值:【"+advPositions.getPid()+"】");
				}
			}
			//解析样式是否需要验证
			String style = advPositions.getStyle();
			if(style==null || "".equals(style) || "all".equalsIgnoreCase(style)){
				advPositions.setVerifyStyle(false);
			}else {
				advPositions.setVerifyStyle(true);
			}
			//TODO 获取广告位密文tagId

			//TODO 获取广告位底价

			//TODO 对外的样式



		}
	}

	@Override
	public int hashCode() {
		return this.getPid() == null ? 0 : this.getPid().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		if(!(obj instanceof AdvPositions)){
			return false;
		}
		if(StringUtils.isInvalidString(this.getPid())){
			return false;
		}
		AdvPositions advPositions = (AdvPositions) obj;
		if(StringUtils.isInvalidString(advPositions.getPid())){
			return false;
		}
		if(this.getPid().equalsIgnoreCase(advPositions.getPid())){
			return true;
		}
		return true;
	}
}
