package com.hxts.unicorn.platform.interfaces.biz.routineJob;

public class ConflictDisputePersonInfo {
	private Integer id;

	// 当事人证件代码
	private String certificatesCode;

	// 当事人证件号码
	private String idCard;

	// 当事人姓名
	private String name;

	// 当事人性别
	private String sex;

	// 当事人民族
	private String ethnicity;

	// 当事人学历
	private String educationalDegree;

	// 当事人类别
	private String type;

	// 当事人联系方式
	private String tel;

	// 当事人所属街道
	private String street;

	// 当事人所属社区
	private String community;

	// 当事人所属楼栋
	private String buildingName;

	// 当事人所属单元
	private String unitNumber;

	// 当事人所属楼层
	private String floorNumber;

	// 当事人所属房号
	private String houseNumber;

	// 关联的事件
	private Integer eventId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCertificatesCode() {
		return certificatesCode;
	}

	public void setCertificatesCode(String certificatesCode) {
		this.certificatesCode = certificatesCode == null ? null : certificatesCode.trim();
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard == null ? null : idCard.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity == null ? null : ethnicity.trim();
	}

	public String getEducationalDegree() {
		return educationalDegree;
	}

	public void setEducationalDegree(String educationalDegree) {
		this.educationalDegree = educationalDegree == null ? null : educationalDegree.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel == null ? null : tel.trim();
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

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName == null ? null : buildingName.trim();
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber == null ? null : unitNumber.trim();
	}

	public String getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber == null ? null : floorNumber.trim();
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber == null ? null : houseNumber.trim();
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

}
