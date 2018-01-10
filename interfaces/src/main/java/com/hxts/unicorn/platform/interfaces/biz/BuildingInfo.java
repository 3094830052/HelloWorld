/*
 * 文 件 名:  Building.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月18日
 * 修改内容:  
 */
package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <楼栋>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BuildingInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8030940748647497884L;

	/**
	 * 基本信息
	 */
	/**
	 * 楼栋ID，唯一标识
	 */
	private long buildingId;

	/**
	 * 楼栋名称
	 */
	private String buildingName;

	/**
	 * 楼栋全称：street+"/"+community+"/"+buildingName
	 */
	private String fullBuildingName;

	/**
	 * 楼栋面积
	 */
	private double buildingArea;

	/**
	 * 地上总层数
	 */
	private int abovegroundNum;

	/**
	 * 地下总层数
	 */
	private int undergroundNum;

	/**
	 * 总户数
	 */
	private int householdNum;

	/**
	 * 建成年代 yyyy
	 */
	private int completionyYear;

	/**
	 * 建筑用途
	 */
	private String buildingPurpose;

	/**
	 * 设计单位
	 */
	private String designOrg;

	/**
	 * 建筑单位
	 */
	private String constructionUnit;

	/**
	 * 单元数
	 */
	private int unitNum;

	/**
	 * 地址信息
	 */
	/**
	 * 省
	 */
	private String province;

	/**
	 * 市
	 */
	private String city;

	/**
	 * 区
	 */
	private String district;

	/**
	 * 街道
	 */
	private String street;

	/**
	 * 社区
	 */
	private String community;

	/**
	 * 详细地址
	 */
	private String address;

	/**
	 * 经度
	 */
	private String longitude;

	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 管理信息
	 */
	/**
	 * 所属网格ID
	 */
	private int gridId;

	/**
	 * 所属网格名称
	 */
	private String gridName;

	/**
	 * 楼栋长ID：关联人口
	 */
	private int buildingManagerId;

	/**
	 * 楼栋长姓名
	 */
	private String buildingManagerName;

	/**
	 * 楼栋长联系方式：手机号
	 */
	private String buildingManagerContact;

	/**
	 * 文件信息
	 */
	/**
	 * 建筑图纸：多文件，存储建筑图纸名称或路径
	 */
	private String constructionDrawings;

	/**
	 * 外观图片：多文件，存储图片名称或路径
	 */
	private String pictures;

	/**
	 * 信息完整度(0-100)
	 */
	private BigDecimal integrity;
	
	/**
	 * 数据完整的房屋数量：已关联人的房屋
	 * 计算：房屋若关联了人口（不论关联了几个），则此房屋信息完善了，  此类房屋数/此楼栋房屋总数 = 楼栋房屋信息完整度
	 */
	private int integrityNum;
	
	/**
	 * 关联了此楼栋房屋的人口的数量
	 */
	private int personNum;

	/**
	 * 楼栋的房屋
	 */
	private List<HouseInfo> houseInfos;

	public long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(long buildingId) {
		this.buildingId = buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName == null ? null : buildingName.trim();
	}

	public String getFullBuildingName() {
		return street + "/" + community + "/" + buildingName;
	}

	public void setFullBuildingName(String fullBuildingName) {
		this.fullBuildingName = fullBuildingName;
	}

	public double getBuildingArea() {
		return buildingArea;
	}

	public void setBuildingArea(double buildingArea) {
		this.buildingArea = buildingArea;
	}

	public int getAbovegroundNum() {
		return abovegroundNum;
	}

	public void setAbovegroundNum(int abovegroundNum) {
		this.abovegroundNum = abovegroundNum;
	}

	public int getUndergroundNum() {
		return undergroundNum;
	}

	public void setUndergroundNum(int undergroundNum) {
		this.undergroundNum = undergroundNum;
	}

	public int getHouseholdNum() {
		return householdNum;
	}

	public void setHouseholdNum(int householdNum) {
		this.householdNum = householdNum;
	}

	public int getCompletionyYear() {
		return completionyYear;
	}

	public void setCompletionyYear(int completionyYear) {
		this.completionyYear = completionyYear;
	}

	public String getDesignOrg() {
		return designOrg;
	}

	public void setDesignOrg(String designOrg) {
		this.designOrg = designOrg == null ? null : designOrg.trim();
	}

	public String getConstructionUnit() {
		return constructionUnit;
	}

	public void setConstructionUnit(String constructionUnit) {
		this.constructionUnit = constructionUnit == null ? null : constructionUnit.trim();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street == null ? null : street.trim();
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community == null ? null : community.trim();
	}

	public int getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(int unitNum) {
		this.unitNum = unitNum;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district == null ? null : district.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public int getGridId() {
		return gridId;
	}

	public void setGridId(int gridId) {
		this.gridId = gridId;
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName == null ? null : gridName.trim();
	}

	public int getBuildingManagerId() {
		return buildingManagerId;
	}

	public void setBuildingManagerId(int buildingManagerId) {
		this.buildingManagerId = buildingManagerId;
	}

	public String getBuildingManagerName() {
		return buildingManagerName;
	}

	public void setBuildingManagerName(String buildingManagerName) {
		this.buildingManagerName = buildingManagerName == null ? null : buildingManagerName.trim();
	}

	public String getBuildingManagerContact() {
		return buildingManagerContact;
	}

	public void setBuildingManagerContact(String buildingManagerContact) {
		this.buildingManagerContact = buildingManagerContact == null ? null : buildingManagerContact.trim();
	}

	public String getConstructionDrawings() {
		return constructionDrawings;
	}

	public void setConstructionDrawings(String constructionDrawings) {
		this.constructionDrawings = constructionDrawings == null ? null : constructionDrawings.trim();
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures == null ? null : pictures.trim();
	}

	public BigDecimal getIntegrity() {
		return integrity;
	}

	public void setIntegrity(BigDecimal integrity) {
		this.integrity = integrity;
	}

	public List<HouseInfo> getHouseInfos() {
		return houseInfos;
	}

	public void setHouseInfos(List<HouseInfo> houseInfos) {
		this.houseInfos = houseInfos;
	}

	public String getBuildingPurpose() {
		return buildingPurpose;
	}

	public void setBuildingPurpose(String buildingPurpose) {
		this.buildingPurpose = buildingPurpose;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public int getIntegrityNum() {
		return integrityNum;
	}

	public void setIntegrityNum(int integrityNum) {
		this.integrityNum = integrityNum;
	}

	public int getPersonNum() {
		return personNum;
	}

	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}

}
