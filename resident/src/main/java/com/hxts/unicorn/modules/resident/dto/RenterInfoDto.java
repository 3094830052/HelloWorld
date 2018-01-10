package com.hxts.unicorn.modules.resident.dto;

import java.math.BigDecimal;
import java.util.List;

public class RenterInfoDto {
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
   	 * 信息完整度(0-100)
   	 */
    private BigDecimal integrity; 
    /**
	 * 民族
	 */
    private String ethnicity;
    /**
     * 关联的房屋信息，包括房屋地址、网格等等
     */
    private List<HouseDto> houseInfos;
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
	public BigDecimal getIntegrity() {
		return integrity;
	}
	public void setIntegrity(BigDecimal integrity) {
		this.integrity = integrity;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public List<HouseDto> getHouseInfos() {
		return houseInfos;
	}
	public void setHouseInfos(List<HouseDto> houseInfos) {
		this.houseInfos = houseInfos;
	}
}
