package com.hxts.unicorn.modules.resident.dto;

import java.math.BigDecimal;
import java.util.List;

public class ResidentBaseInfoDto {
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
     * 居民属性
     */
    private String residentType;
    /**
     * 标签id，例如居民属性为户籍人口时，就对应为houlsehold_id
     */
    private String labelId;
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
	public String getResidentType() {
		return residentType;
	}
	public void setResidentType(String residentType) {
		this.residentType = residentType;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public List<HouseDto> getHouseInfos() {
		return houseInfos;
	}
	public void setHouseInfos(List<HouseDto> houseInfos) {
		this.houseInfos = houseInfos;
	}
	
}