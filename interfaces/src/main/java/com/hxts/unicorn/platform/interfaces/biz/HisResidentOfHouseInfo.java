/*
 * 文 件 名:  ResidentHisOfHouse.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月18日
 * 修改内容:  
 */
package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;
import java.util.Date;

/**
 * <历史房屋关联人口>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HisResidentOfHouseInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4158425951401137328L;

	/**
	 * 历史房屋关联人口ID，唯一标识
	 */
	private long hisResidentOfHouseId;

	/**
	 * 关联的房屋ID
	 */
	private long houseId;

	/**
	 * 房屋信息
	 */
	private HouseInfo houseInfo;

	/**
	 * 关联的人口ID
	 */
	private long residentBaseId;

	/**
	 * 人口信息
	 */
	private ResidentBaseInfo residentBaseInfo;

	/**
	 * 关联类型： 与房关系（自住，租住）
	 */
	private String residentType;

	/**
	 * 租住用途：此属性只在与房关系是“租住”时才有
	 */
	private String rentType;

	/**
	 * 安全隐患
	 */
	private String hiddenDanger;

	/**
	 * 历史记录录入时间(搬出时间)
	 */
	private Date outOfTime;

	/**
	 * 曾入住的住所入住的时间
	 */
	private Date moveInTime;
	
	public long getHisResidentOfHouseId() {
		return hisResidentOfHouseId;
	}

	public void setHisResidentOfHouseId(long hisResidentOfHouseId) {
		this.hisResidentOfHouseId = hisResidentOfHouseId;
	}

	public long getHouseId() {
		return houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}

	public long getResidentBaseId() {
		return residentBaseId;
	}

	public void setResidentBaseId(long residentBaseId) {
		this.residentBaseId = residentBaseId;
	}

	public ResidentBaseInfo getResidentBaseInfo() {
		return residentBaseInfo;
	}

	public void setResidentBaseInfo(ResidentBaseInfo residentBaseInfo) {
		this.residentBaseInfo = residentBaseInfo;
	}

	public String getResidentType() {
		return residentType;
	}

	public void setResidentType(String residentType) {
		this.residentType = residentType;
	}

	public HouseInfo getHouseInfo() {
		return houseInfo;
	}

	public void setHouseInfo(HouseInfo houseInfo) {
		this.houseInfo = houseInfo;
	}

	public String getRentType() {
		return rentType;
	}

	public void setRentType(String rentType) {
		this.rentType = rentType;
	}

	public String getHiddenDanger() {
		return hiddenDanger;
	}

	public void setHiddenDanger(String hiddenDanger) {
		this.hiddenDanger = hiddenDanger;
	}

	public Date getOutOfTime() {
		return outOfTime;
	}

	public void setOutOfTime(Date outOfTime) {
		this.outOfTime = outOfTime;
	}

	public Date getMoveInTime() {
		return moveInTime;
	}

	public void setMoveInTime(Date moveInTime) {
		this.moveInTime = moveInTime;
	}

}
