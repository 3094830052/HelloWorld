package com.hxts.unicorn.modules.resident.dto;

import java.util.List;

public class AssistCrowdDto {
	/**
	 * 人口基本信息id
	 */
    private Integer residentBaseId;
	/**
	 * 姓名
	 */
    private String name;
    /**
	 * 性别
	 */
    private String sex;
    /**
	 * 公民身份号码
	 */
    private String idNo;
    /**
	 * 联系方式
	 */
    private String contact;
	/**
	 * 标签id
	 */
	private Integer labelId;
	/**
	 * 帮扶类型
	 */
    private String assistType;
    /**
	 * 健康状况
	 */
    private String healthCondition;
    /**
     * 关联的房屋信息，包括房屋地址、网格等等
     */
    List<HouseDto> houseInfos;
    
	public Integer getResidentBaseId() {
		return residentBaseId;
	}
	public void setResidentBaseId(Integer residentBaseId) {
		this.residentBaseId = residentBaseId;
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
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public List<HouseDto> getHouseInfos() {
		return houseInfos;
	}
	public void setHouseInfos(List<HouseDto> houseInfos) {
		this.houseInfos = houseInfos;
	}
	public Integer getLabelId() {
		return labelId;
	}
	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	public String getAssistType() {
		return assistType;
	}
	public void setAssistType(String assistType) {
		this.assistType = assistType;
	}
	public String getHealthCondition() {
		return healthCondition;
	}
	public void setHealthCondition(String healthCondition) {
		this.healthCondition = healthCondition;
	}
}
