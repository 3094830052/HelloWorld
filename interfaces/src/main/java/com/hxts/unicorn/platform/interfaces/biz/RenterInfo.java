package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;

/**
 * 
 * <出租屋承租人的标签信息>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RenterInfo implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5462606776525748546L;
	/**
	 * 关联的房屋ID
	 */
	private long houseId;
	/**
	 * 关联的人口ID
	 */
	private Integer residentBaseId;
	/**
	 * 关联的人口类型：自住，租住
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
	
	public long getHouseId() {
		return houseId;
	}
	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}
	public Integer getResidentBaseId() {
		return residentBaseId;
	}
	public void setResidentBaseId(Integer residentBaseId) {
		this.residentBaseId = residentBaseId;
	}
	public String getResidentType() {
		return residentType;
	}
	public void setResidentType(String residentType) {
		this.residentType = residentType;
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
	
}
