/*
 * 文 件 名:  HouseModel.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月19日
 * 修改内容:  
 */
package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;
import java.util.List;

/**
 * <房屋建模辅助类：楼层模型>
 * 
 * @author peidehao 0380008672
 * @version [版本号, 2017年10月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HouseModelInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5932407450063519191L;

	/**
	 * 单元号（按单元建模生成房屋初始信息）
	 */
	private int unitNumber;

	/**
	 * 包含楼层 1-3{"1","2","3"} , 4-8{"4","5","6","7","8"}
	 */
	private String floorScope;

	/**
	 * 开始楼层 (floorScope.sort)[0]
	 */
	private int startFloorNumber;// 表单输入

	/**
	 * 结束楼层 (floorScope.sort)[floorScope.length-1]
	 */
	private int endFloorNumber;// 表单输入

	/**
	 * 楼层包含门派编号 {"1","2","3","4","5"} , {"1","2","3","4"}
	 */
	private String householdScope;// 表单输入

	/**
	 * 户数 householdScope.toArray.length
	 */
	private int householdNum;// 每层户数

	/**
	 * 房屋面积:多个不同户型
	 */
	private List<HouseArea> areas;

	public List<HouseArea> getAreas() {
		return areas;
	}

	public void setAreas(List<HouseArea> areas) {
		this.areas = areas;
	}

	public int getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(int unitNumber) {
		this.unitNumber = unitNumber;
	}

	public int getStartFloorNumber() {
		return startFloorNumber;
	}

	public void setStartFloorNumber(int startFloorNumber) {
		this.startFloorNumber = startFloorNumber;
	}

	public int getEndFloorNumber() {
		return endFloorNumber;
	}

	public void setEndFloorNumber(int endFloorNumber) {
		this.endFloorNumber = endFloorNumber;
	}

	public String getFloorScope() {
		return floorScope;
	}

	public void setFloorScope(String floorScope) {
		this.floorScope = floorScope;
	}

	public String getHouseholdScope() {
		return householdScope;
	}

	public void setHouseholdScope(String householdScope) {
		this.householdScope = householdScope;
	}

	public int getHouseholdNum() {
		return householdNum;
	}

	public void setHouseholdNum(int householdNum) {
		this.householdNum = householdNum;
	}

}
