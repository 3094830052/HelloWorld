package com.hxts.unicorn.modules.resident.dto;

import java.util.List;

public class KeyTeenagerDto {
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
	 * 人员类型
	 */
    private String teenagerType;
    /**
	 * 监护人姓名
	 */
    private String guarderName;
    /**
	 * 监护人联系方式
	 */
    private String guarderContact;
    /**
  	 * 监护人身份号码
  	 */
      private String guarderIdNo;
    /**
	 * 重点青少年id
	 */
    private Integer keyTeenagerId;
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

	public String getTeenagerType() {
		return teenagerType;
	}

	public void setTeenagerType(String teenagerType) {
		this.teenagerType = teenagerType;
	}

	public String getGuarderName() {
		return guarderName;
	}

	public void setGuarderName(String guarderName) {
		this.guarderName = guarderName;
	}

	public String getGuarderContact() {
		return guarderContact;
	}

	public void setGuarderContact(String guarderContact) {
		this.guarderContact = guarderContact;
	}

	public String getGuarderIdNo() {
		return guarderIdNo;
	}

	public void setGuarderIdNo(String guarderIdNo) {
		this.guarderIdNo = guarderIdNo;
	}

	public Integer getKeyTeenagerId() {
		return keyTeenagerId;
	}

	public void setKeyTeenagerId(Integer keyTeenagerId) {
		this.keyTeenagerId = keyTeenagerId;
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