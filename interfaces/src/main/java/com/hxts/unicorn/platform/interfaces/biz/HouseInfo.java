/*
 * 文 件 名:  House.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月18日
 * 修改内容:  
 */
package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;
import java.util.Date;

/**
 * <房屋>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HouseInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7542479560767793427L;

	/**
	 * 房屋ID，唯一标识
	 */
	private long houseId;

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
	 * 门牌
	 */
	private String doorplate;

	/**
	 * 建筑面积
	 */
	private double structureArea;

	/**
	 * 使用面积
	 */
	private double usableArea;

	/**
	 * 公摊面积
	 */
	private double poolArea;

	/**
	 * 房屋证件代码：房屋产权证，土地使用证
	 */
	private String idCode;

	/**
	 * 房屋证件号码
	 */
	private String idNumber;

	/**
	 * 房屋类型
	 */
	private String houseType;

	/**
	 * 录入时间(DateTime)
	 */
	private Date inputTime;

	/**
	 * 楼栋ID
	 */
	private long buildingId;

	/**
	 * 所属楼栋信息
	 */
	private BuildingInfo buildingInfo;
	
	/**
	 * 业主1的信息
	 */
	/**
	 * 业主姓名
	 */
	private String ower1Name;
	
	/**
	 * 业主联系方式
	 */
	private String ower1Contact;
	
	/**
	 * 业主证件类别
	 */
	private String ower1NoType;
	
	/**
	 * 业主证件号
	 */
	private String ower1No;
	
	/**
	 * 业主现住地址
	 */
	private String ower1Address;
	
	/**
	 * 业主2的信息
	 */
	private String ower2Name;
	
	private String ower2Contact;
	
	private String ower2NoType;
	
	private String ower2No;
	
	private String ower2Address;
	
	//是否已关联人口
	private boolean associate;
	
	//已关联人员姓名，","分割
	private String assResidents;
	
	public long getHouseId() {
		return houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}

	public int getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(int unitNumber) {
		this.unitNumber = unitNumber;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getDoorplate() {
		return unitNumber + "-" + floorNumber + "0" + houseNumber;
	}

	public void setDoorplate(String doorplate) {
		this.doorplate = doorplate;
	}

	public double getStructureArea() {
		return structureArea;
	}

	public void setStructureArea(double structureArea) {
		this.structureArea = structureArea;
	}

	public double getUsableArea() {
		return usableArea;
	}

	public void setUsableArea(double usableArea) {
		this.usableArea = usableArea;
	}

	public double getPoolArea() {
		return poolArea;
	}

	public void setPoolArea(double poolArea) {
		this.poolArea = poolArea;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType == null ? null : houseType.trim();
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public BuildingInfo getBuildingInfo() {
		return buildingInfo;
	}

	public void setBuildingInfo(BuildingInfo buildingInfo) {
		this.buildingInfo = buildingInfo;
	}

	public long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(long buildingId) {
		this.buildingId = buildingId;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getOwer1Name() {
		return ower1Name;
	}

	public void setOwer1Name(String ower1Name) {
		this.ower1Name = ower1Name;
	}

	public String getOwer1Contact() {
		return ower1Contact;
	}

	public void setOwer1Contact(String ower1Contact) {
		this.ower1Contact = ower1Contact;
	}

	public String getOwer1NoType() {
		return ower1NoType;
	}

	public void setOwer1NoType(String ower1NoType) {
		this.ower1NoType = ower1NoType;
	}

	public String getOwer1No() {
		return ower1No;
	}

	public void setOwer1No(String ower1No) {
		this.ower1No = ower1No;
	}

	public String getOwer1Address() {
		return ower1Address;
	}

	public void setOwer1Address(String ower1Address) {
		this.ower1Address = ower1Address;
	}

	public String getOwer2Name() {
		return ower2Name;
	}

	public void setOwer2Name(String ower2Name) {
		this.ower2Name = ower2Name;
	}

	public String getOwer2Contact() {
		return ower2Contact;
	}

	public void setOwer2Contact(String ower2Contact) {
		this.ower2Contact = ower2Contact;
	}

	public String getOwer2NoType() {
		return ower2NoType;
	}

	public void setOwer2NoType(String ower2NoType) {
		this.ower2NoType = ower2NoType;
	}

	public String getOwer2No() {
		return ower2No;
	}

	public void setOwer2No(String ower2No) {
		this.ower2No = ower2No;
	}

	public String getOwer2Address() {
		return ower2Address;
	}

	public void setOwer2Address(String ower2Address) {
		this.ower2Address = ower2Address;
	}

	public boolean isAssociate() {
		return associate;
	}

	public void setAssociate(boolean associate) {
		this.associate = associate;
	}

	public String getAssResidents() {
		return assResidents;
	}

	public void setAssResidents(String assResidents) {
		this.assResidents = assResidents;
	}

}
