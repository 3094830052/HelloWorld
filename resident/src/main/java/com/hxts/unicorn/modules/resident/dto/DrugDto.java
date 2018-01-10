package com.hxts.unicorn.modules.resident.dto;

import java.util.Date;
import java.util.List;

public class DrugDto {
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
     * 初次发现时间
     */
    private Date datefirstdiscover;
    /**
     * 有无犯罪史
     */
    private Integer iscrime;
    /**
     * 管控情况
     */
    private String controlsituation;
    /**
     * 吸毒者id
     */
    private Integer drugid;
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
	public Date getDatefirstdiscover() {
		return datefirstdiscover;
	}
	public void setDatefirstdiscover(Date datefirstdiscover) {
		this.datefirstdiscover = datefirstdiscover;
	}
	public Integer getIscrime() {
		return iscrime;
	}
	public void setIscrime(Integer iscrime) {
		this.iscrime = iscrime;
	}
	public String getControlsituation() {
		return controlsituation;
	}
	public void setControlsituation(String controlsituation) {
		this.controlsituation = controlsituation;
	}
	public Integer getDrugid() {
		return drugid;
	}
	public void setDrugid(Integer drugid) {
		this.drugid = drugid;
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

