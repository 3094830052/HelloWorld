package com.hxts.unicorn.modules.resident.dto;
import java.util.Date;
import java.util.List;

public class EmancipistDto {
	/**
	 * 刑满释放人员ID
	 */
    private Integer emancipistId;
	/**
	 * 危险性评估类型
	 */
    private String riskAssessmentType;
	/**
	 * 衔接日期
	 */
    private Date cohesionDate;
	/**
	 * 安置情况
	 */
    private String placeSituation;
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
	 * 人口基础信息id
	 */
    private Integer residentBaseId;
    /**
     * 关联的房屋信息，包括房屋地址、网格等等
     */
    private List<HouseDto> houseInfos;
    
    
	public Integer getEmancipistId() {
		return emancipistId;
	}
	public void setEmancipistId(Integer emancipistId) {
		this.emancipistId = emancipistId;
	}
	public String getRiskAssessmentType() {
		return riskAssessmentType;
	}
	public void setRiskAssessmentType(String riskAssessmentType) {
		this.riskAssessmentType = riskAssessmentType;
	}
	public Date getCohesionDate() {
		return cohesionDate;
	}
	public void setCohesionDate(Date cohesionDate) {
		this.cohesionDate = cohesionDate;
	}
	public String getPlaceSituation() {
		return placeSituation;
	}
	public void setPlaceSituation(String placeSituation) {
		this.placeSituation = placeSituation;
	}
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

}