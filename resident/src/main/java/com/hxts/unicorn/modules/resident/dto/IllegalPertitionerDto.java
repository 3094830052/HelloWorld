package com.hxts.unicorn.modules.resident.dto;

import java.util.Date;
import java.util.List;

public class IllegalPertitionerDto {
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
	 * 最近上访时间
	 */
    private Date lastPertitionTime;
    /**
	 * 是否涉案
	 */
    private String isCrime;
    /**
	 * 上访诉求
	 */
    private String pertitionAppeal;
    /**
	 * 非访人口id
	 */
    private Integer IllegalPertitionerId;
    /**
	 * 人口基础信息id
	 */
    private Integer residentBaseId;
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

	public Integer getResidentBaseId() {
		return residentBaseId;
	}

	public void setResidentBaseId(Integer residentBaseId) {
		this.residentBaseId = residentBaseId;
	}

	public List<HouseDto> getHouseInfos() {
		return houseInfos;
	}

	public void setHouseInfos(List<HouseDto> houseInfos) {
		this.houseInfos = houseInfos;
	}

	public Date getLastPertitionTime() {
		return lastPertitionTime;
	}

	public void setLastPertitionTime(Date lastPertitionTime) {
		this.lastPertitionTime = lastPertitionTime;
	}

	public String getIsCrime() {
		return isCrime;
	}

	public void setIsCrime(String isCrime) {
		this.isCrime = isCrime;
	}

	public String getPertitionAppeal() {
		return pertitionAppeal;
	}

	public void setPertitionAppeal(String pertitionAppeal) {
		this.pertitionAppeal = pertitionAppeal;
	}

	public Integer getIllegalPertitionerId() {
		return IllegalPertitionerId;
	}

	public void setIllegalPertitionerId(Integer illegalPertitionerId) {
		IllegalPertitionerId = illegalPertitionerId;
	}
}