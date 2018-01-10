package com.hxts.unicorn.platform.interfaces.biz;

public class ForeignerResidentInfo {
	/**
	 * 境外人口id
	 */
    private Integer foreignerId;
    /**
	 * 人口基础信息id
	 */
    private Integer residentBaseId;
    /**
	 * 外文姓
	 */
    private String foreignSurname;
    /**
	 * 外文名
	 */
    private String foreignGivenName;
    /**
	 * 国籍（地区）
	 */
    private String nationality;
    /**
	 * 证件代码
	 */
    private String certificateCode;
    /**
	 * 证件有效期
	 */
    private String certificateExpireDate;
    /**
	 * 来华目的
	 */
    private String purpose;
    /**
	 * 抵达日期
	 */
    private String arriveDate;
    /**
	 * 预计离开日期
	 */
    private String expectDepartureDate;
    /**
	 * 是否重点关注人员
	 */
    private String isFocusPerson;
    /**
	 * 创建者
	 */
    private Integer createUserId;

    public Integer getForeignerId() {
        return foreignerId;
    }

    public void setForeignerId(Integer foreignerId) {
        this.foreignerId = foreignerId;
    }

    public Integer getResidentBaseId() {
        return residentBaseId;
    }

    public void setResidentBaseId(Integer residentBaseId) {
        this.residentBaseId = residentBaseId;
    }

    public String getForeignSurname() {
        return foreignSurname;
    }

    public void setForeignSurname(String foreignSurname) {
        this.foreignSurname = foreignSurname == null ? null : foreignSurname.trim();
    }

    public String getForeignGivenName() {
        return foreignGivenName;
    }

    public void setForeignGivenName(String foreignGivenName) {
        this.foreignGivenName = foreignGivenName == null ? null : foreignGivenName.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode == null ? null : certificateCode.trim();
    }

    public String getCertificateExpireDate() {
        return certificateExpireDate;
    }

    public void setCertificateExpireDate(String certificateExpireDate) {
        this.certificateExpireDate = certificateExpireDate == null ? null : certificateExpireDate.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate == null ? null : arriveDate.trim();
    }

    public String getExpectDepartureDate() {
        return expectDepartureDate;
    }

    public void setExpectDepartureDate(String expectDepartureDate) {
        this.expectDepartureDate = expectDepartureDate == null ? null : expectDepartureDate.trim();
    }

    public String getIsFocusPerson() {
        return isFocusPerson;
    }

    public void setIsFocusPerson(String isFocusPerson) {
        this.isFocusPerson = isFocusPerson == null ? null : isFocusPerson.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}