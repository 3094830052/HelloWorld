package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;

public class SubsistenceAllowanceInfo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9000705121335074187L;
	/**
	 * 低保人员id
	 */
    private Integer subsistAllowanceId;
    /**
	 * 人口基础信息id
	 */
    private Integer residentBaseId;
    /**
	 * 健康状况
	 */
    private String healthCondition;
    /**
	 * 家庭人口
	 */
    private Integer familyMember;
    /**
	 * 家庭成员就业情况
	 */
    private String familyEmployStatus;
    /**
	 * 家庭成员身体状况
	 */
    private String familyHealthCondition;
    /**
	 * 家庭经济月收入
	 */
    private String familyMonthIncome;
    /**
	 * 家庭成员是否有残疾
	 */
    private String hasDisabled;
    /**
	 * 月救助金额
	 */
    private Integer monthReliefFund;
    /**
	 * 主要致贫原因
	 */
    private String povertyReason;
    /**
	 * 低保享受类别
	 */
    private String subsistAllowanceCategory;
    /**
	 * 创建者userId
	 */
    private Integer createUserId;

    public Integer getSubsistAllowanceId() {
        return subsistAllowanceId;
    }

    public void setSubsistAllowanceId(Integer subsistAllowanceId) {
        this.subsistAllowanceId = subsistAllowanceId;
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

    public Integer getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(Integer familyMember) {
        this.familyMember = familyMember;
    }

    public String getFamilyEmployStatus() {
        return familyEmployStatus;
    }

    public void setFamilyEmployStatus(String familyEmployStatus) {
        this.familyEmployStatus = familyEmployStatus == null ? null : familyEmployStatus.trim();
    }

    public String getFamilyHealthCondition() {
        return familyHealthCondition;
    }

    public void setFamilyHealthCondition(String familyHealthCondition) {
        this.familyHealthCondition = familyHealthCondition == null ? null : familyHealthCondition.trim();
    }

    public String getFamilyMonthIncome() {
        return familyMonthIncome;
    }

    public void setFamilyMonthIncome(String familyMonthIncome) {
        this.familyMonthIncome = familyMonthIncome == null ? null : familyMonthIncome.trim();
    }

    public String getHasDisabled() {
        return hasDisabled;
    }

    public void setHasDisabled(String hasDisabled) {
        this.hasDisabled = hasDisabled == null ? null : hasDisabled.trim();
    }

    public Integer getMonthReliefFund() {
        return monthReliefFund;
    }

    public void setMonthReliefFund(Integer monthReliefFund) {
        this.monthReliefFund = monthReliefFund;
    }

    public String getPovertyReason() {
        return povertyReason;
    }

    public void setPovertyReason(String povertyReason) {
        this.povertyReason = povertyReason == null ? null : povertyReason.trim();
    }

    public String getSubsistAllowanceCategory() {
        return subsistAllowanceCategory;
    }

    public void setSubsistAllowanceCategory(String subsistAllowanceCategory) {
        this.subsistAllowanceCategory = subsistAllowanceCategory == null ? null : subsistAllowanceCategory.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}