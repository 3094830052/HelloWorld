package com.hxts.unicorn.modules.resident.dto;

import java.util.List;

public class FloatingResidentDto {
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
	 * 流动人口id
	 */
    private Integer FloatingId;
    /**
	 * 人口基础信息id
	 */
    private Integer residentBaseId;
    /**
	 * 办证类型
	 */
    private String certificateHandlingType;
    /**
	 * 证件到期日期
	 */
    private String certificateExpireDate;
    /**
     * 关联的房屋信息，包括房屋地址、网格等等
     */
    private List<HouseDto> houseInfos;
    /**
     * 家庭关系
     */
    private List<FamilyRelationDto> familyRelationInfos;
    
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
	public Integer getFloatingId() {
		return FloatingId;
	}
	public void setFloatingId(Integer floatingId) {
		FloatingId = floatingId;
	}
	public Integer getResidentBaseId() {
		return residentBaseId;
	}
	public void setResidentBaseId(Integer residentBaseId) {
		this.residentBaseId = residentBaseId;
	}
	public String getCertificateHandlingType() {
		return certificateHandlingType;
	}
	public void setCertificateHandlingType(String certificateHandlingType) {
		this.certificateHandlingType = certificateHandlingType;
	}
	public String getCertificateExpireDate() {
		return certificateExpireDate;
	}
	public void setCertificateExpireDate(String certificateExpireDate) {
		this.certificateExpireDate = certificateExpireDate;
	}
	public List<HouseDto> getHouseInfos() {
		return houseInfos;
	}
	public void setHouseInfos(List<HouseDto> houseInfos) {
		this.houseInfos = houseInfos;
	}
	public List<FamilyRelationDto> getFamilyRelationInfos() {
		return familyRelationInfos;
	}
	public void setFamilyRelationInfos(List<FamilyRelationDto> familyRelationInfos) {
		this.familyRelationInfos = familyRelationInfos;
	}
    
}
