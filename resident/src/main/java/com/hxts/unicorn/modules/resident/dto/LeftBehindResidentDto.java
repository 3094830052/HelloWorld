package com.hxts.unicorn.modules.resident.dto;

import java.util.List;

public class LeftBehindResidentDto {
	/**
	 * 身份号码
	 */
    private String idNo;
    /**
	 * 姓名
	 */
    private String name;  
    /**
	 * 性别
	 */
    private String sex;
    /**
	 * 联系方式
	 */
    private String contact;
    /**
	 * 留守人口信息id
	 */
    private Integer leftBehindId;
    /**
	 * 人口基本信息id
	 */
    private Integer residentBaseId;
    /**
	 * 健康状况
	 */
    private String healthCondition;
    /**
	 * 留守类型
	 */
    private String leftBehindResidentType;
    /**
     * 关联的房屋信息，包括房屋地址、网格等等
     */
    private List<HouseDto> houseInfos;
    
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Integer getLeftBehindId() {
		return leftBehindId;
	}
	public void setLeftBehindId(Integer leftBehindId) {
		this.leftBehindId = leftBehindId;
	}
	public Integer getResidentBaseId() {
		return residentBaseId;
	}
	public void setResidentBaseId(Integer residentBaseId) {
		this.residentBaseId = residentBaseId;
	}
	public String getHealthCondition() {
		return healthCondition;
	}
	public void setHealthCondition(String healthCondition) {
		this.healthCondition = healthCondition;
	}
	public String getLeftBehindResidentType() {
		return leftBehindResidentType;
	}
	public void setLeftBehindResidentType(String leftBehindResidentType) {
		this.leftBehindResidentType = leftBehindResidentType;
	}
	public List<HouseDto> getHouseInfos() {
		return houseInfos;
	}
	public void setHouseInfos(List<HouseDto> houseInfos) {
		this.houseInfos = houseInfos;
	}
    
}
