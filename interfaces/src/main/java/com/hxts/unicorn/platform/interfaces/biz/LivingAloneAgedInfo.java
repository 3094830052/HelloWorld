package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;

public class LivingAloneAgedInfo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7124787421544640924L;
	/**
	 * 独居老人id
	 */
    private Integer livingAloneId;
    /**
	 * 人口基本信息id
	 */
    private Integer residentBaseId;
    /**
	 * 健康状况
	 */
    private String healthCondition;
    /**
	 * 精神心理状态
	 */
    private String mentalState;
    /**
	 * 是否子女赡养
	 */
    private String hasChildSupport;
    /**
	 * 居住情况
	 */
    private String livingCondition;
    /**
	 * 经济来源
	 */
    private String financialResource;
    /**
	 * 独居原因
	 */
    private String livingAloneReason;
    /**
	 * 创建者userId
	 */
    private Integer createUserId;

    public Integer getLivingAloneId() {
        return livingAloneId;
    }

    public void setLivingAloneId(Integer livingAloneId) {
        this.livingAloneId = livingAloneId;
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

    public String getMentalState() {
        return mentalState;
    }

    public void setMentalState(String mentalState) {
        this.mentalState = mentalState == null ? null : mentalState.trim();
    }

    public String getHasChildSupport() {
        return hasChildSupport;
    }

    public void setHasChildSupport(String hasChildSupport) {
        this.hasChildSupport = hasChildSupport == null ? null : hasChildSupport.trim();
    }

    public String getLivingCondition() {
        return livingCondition;
    }

    public void setLivingCondition(String livingCondition) {
        this.livingCondition = livingCondition == null ? null : livingCondition.trim();
    }

    public String getFinancialResource() {
        return financialResource;
    }

    public void setFinancialResource(String financialResource) {
        this.financialResource = financialResource == null ? null : financialResource.trim();
    }

    public String getLivingAloneReason() {
        return livingAloneReason;
    }

    public void setLivingAloneReason(String livingAloneReason) {
        this.livingAloneReason = livingAloneReason == null ? null : livingAloneReason.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}