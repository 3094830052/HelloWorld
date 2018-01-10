package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ResidentBaseInfo implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7612864790901767797L;
	/**
	 * 人口基本信息id
	 */
	private Integer residentBaseId;
	/**
	 * 公民身份号码
	 */
	@NotNull(message="身份证号码不能为空")
    private String idNo;
    /**
	 * 姓名
	 */
    private String name;
    /**
   	 * 曾用名
   	 */
    private String formerName;
    /**
	 * 性别
	 */
    private String sex;
    /**
   	 * 出生日期
   	 */
    private String birthDate;
    /**
	 * 民族
	 */
    private String ethnicity;
    /**
 	 * 籍贯
 	 */
    private String nativePlace;
    /**
  	 * 婚姻状况
  	 */
    private String maritalStatus;
    /**
  	 * 政治面貌
  	 */
    private String politicalStatus;
    /**
  	 * 学历
  	 */
    private String educationalDegree;
    /**
  	 * 宗教信仰
  	 */
    private String religiousBelief;
    /**
  	 * 职业类别
  	 */
    private String occupationCategory;
    /**
  	 * 职业
  	 */
    private String occupation;
    /**
  	 * 服务处所
  	 */
    private String employer;
    /**
  	 * 联系方式
  	 */
    private String contact;
    /**
  	 * 户籍地
  	 */
    private String registeredResidence;
    /**
  	 * 户籍门（楼）详址
  	 */
    private String registeredResidenceAddress;
    /**
  	 * 现住地
  	 */
    private String currentResidence;
    /**
  	 * 现住门（楼）详址
  	 */
    private String currentResidenceAddress;
    /**
	 * 创建者userId
	 */
    private Integer createUserId;
    /**
 	 * 信息完整度(0-100)
 	 */
    private BigDecimal integrity;
    /**
     * 照片路径
     */
    private String picture;

	public Integer getResidentBaseId() {
		return residentBaseId;
	}

	public void setResidentBaseId(Integer residentBaseId) {
		this.residentBaseId = residentBaseId;
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

	public String getFormerName() {
		return formerName;
	}

	public void setFormerName(String formerName) {
		this.formerName = formerName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public String getEducationalDegree() {
		return educationalDegree;
	}

	public void setEducationalDegree(String educationalDegree) {
		this.educationalDegree = educationalDegree;
	}

	public String getReligiousBelief() {
		return religiousBelief;
	}

	public void setReligiousBelief(String religiousBelief) {
		this.religiousBelief = religiousBelief;
	}

	public String getOccupationCategory() {
		return occupationCategory;
	}

	public void setOccupationCategory(String occupationCategory) {
		this.occupationCategory = occupationCategory;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getRegisteredResidence() {
		return registeredResidence;
	}

	public void setRegisteredResidence(String registeredResidence) {
		this.registeredResidence = registeredResidence;
	}

	public String getRegisteredResidenceAddress() {
		return registeredResidenceAddress;
	}

	public void setRegisteredResidenceAddress(String registeredResidenceAddress) {
		this.registeredResidenceAddress = registeredResidenceAddress;
	}

	public String getCurrentResidence() {
		return currentResidence;
	}

	public void setCurrentResidence(String currentResidence) {
		this.currentResidence = currentResidence;
	}

	public String getCurrentResidenceAddress() {
		return currentResidenceAddress;
	}

	public void setCurrentResidenceAddress(String currentResidenceAddress) {
		this.currentResidenceAddress = currentResidenceAddress;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public BigDecimal getIntegrity() {
		return integrity;
	}

	public void setIntegrity(BigDecimal integrity) {
		this.integrity = integrity;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
