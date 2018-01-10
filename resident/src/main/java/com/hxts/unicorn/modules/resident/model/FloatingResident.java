package com.hxts.unicorn.modules.resident.model;

/**
 * 流动人口标签信息实体类
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class FloatingResident {
	/**
	 * 流动人口标签信息id
	 */
    private Integer floatingId;

    /**
     * 人口基本信息id
     */
    private Integer residentBaseId;
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
	 * 是否重点关注人员
	 */
    private String isFocusPerson;
    /**
	 * 创建者userId
	 */
    private Integer createUserId;

    public Integer getFloatingId() {
        return floatingId;
    }

    public void setFloatingId(Integer floatingId) {
        this.floatingId = floatingId;
    }

    public Integer getResidentBaseId() {
        return residentBaseId;
    }

    public void setResidentBaseId(Integer residentBaseId) {
        this.residentBaseId = residentBaseId;
    }

    public String getInflowReason() {
        return inflowReason;
    }

    public void setInflowReason(String inflowReason) {
        this.inflowReason = inflowReason == null ? null : inflowReason.trim();
    }

    public String getCertificateHandlingType() {
        return certificateHandlingType;
    }

    public void setCertificateHandlingType(String certificateHandlingType) {
        this.certificateHandlingType = certificateHandlingType == null ? null : certificateHandlingType.trim();
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo == null ? null : certificateNo.trim();
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate == null ? null : registerDate.trim();
    }

    public String getCertificateExpireDate() {
        return certificateExpireDate;
    }

    public void setCertificateExpireDate(String certificateExpireDate) {
        this.certificateExpireDate = certificateExpireDate == null ? null : certificateExpireDate.trim();
    }

    public String getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType == null ? null : residenceType.trim();
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