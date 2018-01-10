package com.hxts.unicorn.modules.resident.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class ResidentInfoTemp {
	/**
	 * 网格员确认状态
	 */
	private Integer status;
	/**
	 * 保存类型绑定or保存 0新增 1 认证
	 */
	private Integer type;
	/**
	 *  微信ID
	 */
	private String openId;
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
     * 国籍
     */
    private String nationality;
	/**
     * 与户主关系
     */
    private String householderRelationship;
    /**
     * 户主姓名
     */
    private String householderName;
    /**
     * 户主身份证号
     */
    private String householderIdcardNo;
    /**
     * 户主联系方式
     */
    private String householderContact;
    /**
     * 房屋编号
     */
    private Long houseId;
	/**
	 * 单元号
	 */
	private int unitNumber;

	/**
	 * 楼层
	 */
	private int floorNumber;

	/**
	 * 房间号
	 */
	private int houseNumber;
	/**
	 * 楼栋编号
	 */
    private Long buildingId;
    /**
     * 房屋证件代码
     */
    private String idCode;
    /**
     * 房屋证件号码
     */
    private String idNumber;
    /**
     * 房屋用途
     */
    private String houseType;
    /**
     * 流入原因
     */
    private String inflowReason;
    /**
     * 办证类型
     */
    private String certificateHandlingType;
    /**
     * 证件号码
     */
    private String certificateNo;
    /**
     * 登记日期
     */
    private String registerDate;
    /**
     * 证件到期日期
     */
    private String certificateExpireDate;
    /**
     * 住所类型
     */
    private String residenceType;
    /**
     * 外文姓
     */
    private String foreignSurname;
    /**
     * 外文名
     */
    private String foreignGivenName;
    /**
     * 来华目的
     */
    private String purpose;
    /**
     * 抵达日期
     */
    private Date arriveDate;
    /**
     * 预计离开日期
     */
    private Date expectDepartureDate;
    /**
     * 照片路径
     */
    private String picture;
    /**
	 * 创建者userId
	 */
    private Integer createUserId;
    /**
     * 创建时间
     */
    private Date date;
    
    public Integer getResidentBaseId() {
        return residentBaseId;
    }

    public void setResidentBaseId(Integer residentBaseId) {
        this.residentBaseId = residentBaseId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFormerName() {
        return formerName;
    }

    public void setFormerName(String formerName) {
        this.formerName = formerName == null ? null : formerName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate == null ? null : birthDate.trim();
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity == null ? null : ethnicity.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus == null ? null : maritalStatus.trim();
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus == null ? null : politicalStatus.trim();
    }

    public String getEducationalDegree() {
        return educationalDegree;
    }

    public void setEducationalDegree(String educationalDegree) {
        this.educationalDegree = educationalDegree == null ? null : educationalDegree.trim();
    }

    public String getReligiousBelief() {
        return religiousBelief;
    }

    public void setReligiousBelief(String religiousBelief) {
        this.religiousBelief = religiousBelief == null ? null : religiousBelief.trim();
    }

    public String getOccupationCategory() {
        return occupationCategory;
    }

    public void setOccupationCategory(String occupationCategory) {
        this.occupationCategory = occupationCategory == null ? null : occupationCategory.trim();
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation == null ? null : occupation.trim();
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer == null ? null : employer.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getRegisteredResidence() {
        return registeredResidence;
    }

    public void setRegisteredResidence(String registeredResidence) {
        this.registeredResidence = registeredResidence == null ? null : registeredResidence.trim();
    }

    public String getRegisteredResidenceAddress() {
        return registeredResidenceAddress;
    }

    public void setRegisteredResidenceAddress(String registeredResidenceAddress) {
        this.registeredResidenceAddress = registeredResidenceAddress == null ? null : registeredResidenceAddress.trim();
    }

    public String getCurrentResidence() {
        return currentResidence;
    }

    public void setCurrentResidence(String currentResidence) {
        this.currentResidence = currentResidence == null ? null : currentResidence.trim();
    }

    public String getCurrentResidenceAddress() {
        return currentResidenceAddress;
    }

    public void setCurrentResidenceAddress(String currentResidenceAddress) {
        this.currentResidenceAddress = currentResidenceAddress == null ? null : currentResidenceAddress.trim();
    }

    public String getHouseholderRelationship() {
        return householderRelationship;
    }

    public void setHouseholderRelationship(String householderRelationship) {
        this.householderRelationship = householderRelationship == null ? null : householderRelationship.trim();
    }

    public String getHouseholderName() {
        return householderName;
    }

    public void setHouseholderName(String householderName) {
        this.householderName = householderName == null ? null : householderName.trim();
    }

    public String getHouseholderIdcardNo() {
        return householderIdcardNo;
    }

    public void setHouseholderIdcardNo(String householderIdcardNo) {
        this.householderIdcardNo = householderIdcardNo == null ? null : householderIdcardNo.trim();
    }

    public String getHouseholderContact() {
        return householderContact;
    }

    public void setHouseholderContact(String householderContact) {
        this.householderContact = householderContact == null ? null : householderContact.trim();
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode == null ? null : idCode.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
    public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setUnitNumber(int unitNumber) {
		this.unitNumber = unitNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getInflowReason() {
		return inflowReason;
	}

	public void setInflowReason(String inflowReason) {
		this.inflowReason = inflowReason;
	}

	public String getCertificateHandlingType() {
		return certificateHandlingType;
	}

	public void setCertificateHandlingType(String certificateHandlingType) {
		this.certificateHandlingType = certificateHandlingType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getCertificateExpireDate() {
		return certificateExpireDate;
	}

	public void setCertificateExpireDate(String certificateExpireDate) {
		this.certificateExpireDate = certificateExpireDate;
	}

	public String getResidenceType() {
		return residenceType;
	}

	public void setResidenceType(String residenceType) {
		this.residenceType = residenceType;
	}

	public String getForeignSurname() {
		return foreignSurname;
	}

	public void setForeignSurname(String foreignSurname) {
		this.foreignSurname = foreignSurname;
	}

	public String getForeignGivenName() {
		return foreignGivenName;
	}

	public void setForeignGivenName(String foreignGivenName) {
		this.foreignGivenName = foreignGivenName;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Date getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	public Date getExpectDepartureDate() {
		return expectDepartureDate;
	}

	public void setExpectDepartureDate(Date expectDepartureDate) {
		this.expectDepartureDate = expectDepartureDate;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}