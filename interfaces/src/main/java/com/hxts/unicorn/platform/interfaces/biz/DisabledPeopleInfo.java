package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;

public class DisabledPeopleInfo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3834150011352754646L;
	/**
	 * 残障人士id
	 */
    private Integer disabledPeopleId;
    /**
     * 人口基础信息id
     */
    private Integer residentBaseId;
    /**
     * 健康状况
     */
    private String healthCondition;
    /**
     * 残疾类别
     */
    private String disabledCategory;
    /**
     * 残疾等级
     */
    private String disabledDegree;
    /**
     * 残疾证号
     */
    private String disabledNo;
    /**
     * 致残原因
     */
    private String disabledReason;
    /**
     * 致残部位
     */
    private String disabledParts;
    /**
     * 法定监护人姓名
     */
    private String guarderName;
    /**
     * 法定监护人身份号码
     */
    private String guarderIdNo;
    /**
     * 法定监护人联系方式
     */
    private String guarderContact;
    /**
     * 家庭残疾人数
     */
    private Integer familyDisabled;
    /**
     * 家庭月收入
     */
    private String familyMonthIncome;
    /**
     * 是否低保户
     */
    private String isSubsistAllowance;
    /**
     * 就业情况
     */
    private String employmentStatus;
    /**
     * 社保情况
     */
    private String socialSecurityStatus;
    /**
     * 创建者userId
     */
    private Integer createUserId;

    public Integer getDisabledPeopleId() {
        return disabledPeopleId;
    }

    public void setDisabledPeopleId(Integer disabledPeopleId) {
        this.disabledPeopleId = disabledPeopleId;
    }

    public Integer getResidentBaseId() {
        return residentBaseId;
    }

    public void setResidentBaseId(Integer residentBaseId) {
        this.residentBaseId = residentBaseId;
    }

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition == null ? null : healthCondition.trim();
    }

    public String getDisabledCategory() {
        return disabledCategory;
    }

    public void setDisabledCategory(String disabledCategory) {
        this.disabledCategory = disabledCategory == null ? null : disabledCategory.trim();
    }

    public String getDisabledDegree() {
        return disabledDegree;
    }

    public void setDisabledDegree(String disabledDegree) {
        this.disabledDegree = disabledDegree == null ? null : disabledDegree.trim();
    }

    public String getDisabledNo() {
        return disabledNo;
    }

    public void setDisabledNo(String disabledNo) {
        this.disabledNo = disabledNo == null ? null : disabledNo.trim();
    }

    public String getDisabledReason() {
        return disabledReason;
    }

    public void setDisabledReason(String disabledReason) {
        this.disabledReason = disabledReason == null ? null : disabledReason.trim();
    }

    public String getDisabledParts() {
        return disabledParts;
    }

    public void setDisabledParts(String disabledParts) {
        this.disabledParts = disabledParts == null ? null : disabledParts.trim();
    }

    public String getGuarderName() {
        return guarderName;
    }

    public void setGuarderName(String guarderName) {
        this.guarderName = guarderName == null ? null : guarderName.trim();
    }

    public String getGuarderIdNo() {
        return guarderIdNo;
    }

    public void setGuarderIdNo(String guarderIdNo) {
        this.guarderIdNo = guarderIdNo == null ? null : guarderIdNo.trim();
    }

    public String getGuarderContact() {
        return guarderContact;
    }

    public void setGuarderContact(String guarderContact) {
        this.guarderContact = guarderContact == null ? null : guarderContact.trim();
    }

    public Integer getFamilyDisabled() {
        return familyDisabled;
    }

    public void setFamilyDisabled(Integer familyDisabled) {
        this.familyDisabled = familyDisabled;
    }

    public String getFamilyMonthIncome() {
        return familyMonthIncome;
    }

    public void setFamilyMonthIncome(String familyMonthIncome) {
        this.familyMonthIncome = familyMonthIncome == null ? null : familyMonthIncome.trim();
    }

    public String getIsSubsistAllowance() {
        return isSubsistAllowance;
    }

    public void setIsSubsistAllowance(String isSubsistAllowance) {
        this.isSubsistAllowance = isSubsistAllowance == null ? null : isSubsistAllowance.trim();
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus == null ? null : employmentStatus.trim();
    }

    public String getSocialSecurityStatus() {
        return socialSecurityStatus;
    }

    public void setSocialSecurityStatus(String socialSecurityStatus) {
        this.socialSecurityStatus = socialSecurityStatus == null ? null : socialSecurityStatus.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}