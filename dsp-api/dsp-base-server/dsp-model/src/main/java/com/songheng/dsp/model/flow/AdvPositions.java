
package com.songheng.dsp.model.flow;


import com.alibaba.fastjson.JSONArray;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.enums.DeviceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @description 请求的广告位
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
	 * 广告样式 one/group/big
	 */
	private String style;

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
		this.style = "";
	}

	public static List<AdvPositions> jsonArrayToBeans(String jsonStr,String deviceType){
		List<AdvPositions> positionInfo = new ArrayList<>();
		try {
			//测试json
			jsonStr = getTestJson(deviceType);
			if(jsonStr!=null && !"".contentEquals(jsonStr.trim())){
				positionInfo =  JSONArray.parseArray(jsonStr,AdvPositions.class);
				parseData(positionInfo,deviceType);
			}
			return positionInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return positionInfo;
		}
	}

	private static String getTestJson(String deviceType){
		if(DeviceType.isMobile(deviceType)) {
			return "[{\"pid\":\"detail_-2_1\",\"style\":\"one,group,full\"},"
					+ "{\"pid\":\"detail_-1_1\",\"style\":\"one,big,group\"},"
					+ "{\"pid\":\"detail_-1_2\",\"style\":\"one,big,group\"},"
					+ "{\"pid\":\"detail_-6_1\",\"style\":\"big\"},"
					+ "{\"pid\":\"detail_1_1\",\"style\":\"one,big,group\"},"
					+ "{\"pid\":\"detail_1_2\",\"style\":\"one,big,group\"},"
					+ "{\"pid\":\"detail_1_3\",\"style\":\"one,big,group\"}]";
		}else if(DeviceType.isComputer(deviceType)){
			return "[{\"pid\":\"sy_nyxf\"},"
					+ "{\"pid\":\"sy_y5\"}]";
		}else{
			return null;
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
			if(pids.length==3 && "mobile".equalsIgnoreCase(deviceType)){
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

	public static void main(String[] args) {
		System.out.println(jsonArrayToBeans("","computer"));
	}

}
