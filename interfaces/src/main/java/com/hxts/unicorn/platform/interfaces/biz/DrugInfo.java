package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;
import java.util.Date;

public class DrugInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1365575472454313909L;
	/**
	 * 吸毒者ID
	 */
    private Integer drugAddictId;

    /**
     * 人员基本信息ID
     */
    private Integer residentBaseId;

    /**
     * 初次发现日期
     */
    private String dateFirstDiscovery;

    /**
     * 管控情况
     */
    private String controlSituation;

    /**
     * 管控人姓名
     */
    private String controlName;

    /**
     * 管控人手机号
     */
    private String controlPhone;

    /**
     * 帮扶情况
     */
    private String supportSituation;

    /**
     * 帮扶人姓名
     */
    private String supportName;

    /**
     * 帮扶人手机号
     */
    private String supportPhone;

    /**
     * 有无犯罪史
     */
    private Integer isCrime;

    /**
     * 犯罪情况
     */
    private String crimeCondition;

    /**
     * 吸毒原因
     */
    private String drugReasons;

    /**
     * 吸毒后果
     */
    private String drugConsequence;

    /**
     * 创建人ID
     */
    private Integer createUserId;


	public Integer getDrugAddictId() {
		return drugAddictId;
	}

	public void setDrugAddictId(Integer drugAddictId) {
		this.drugAddictId = drugAddictId;
	}

	public Integer getResidentBaseId() {
		return residentBaseId;
	}

	public void setResidentBaseId(Integer residentBaseId) {
		this.residentBaseId = residentBaseId;
	}

	public String getDateFirstDiscovery() {
		return dateFirstDiscovery;
	}

	public void setDateFirstDiscovery(String dateFirstDiscovery) {
		this.dateFirstDiscovery = dateFirstDiscovery;
	}

	public String getControlSituation() {
		return controlSituation;
	}

	public void setControlSituation(String controlSituation) {
		this.controlSituation = controlSituation == null ? null : controlSituation.trim();
	}

	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName == null ? null : controlName.trim();
	}

	public String getControlPhone() {
		return controlPhone;
	}

	public void setControlPhone(String controlPhone) {
		this.controlPhone = controlPhone == null ? null : controlPhone.trim();
	}

	public String getSupportSituation() {
		return supportSituation;
	}

	public void setSupportSituation(String supportSituation) {
		this.supportSituation = supportSituation == null ? null : supportSituation.trim();
	}

	public String getSupportName() {
		return supportName;
	}

	public void setSupportName(String supportName) {
		this.supportName = supportName == null ? null : supportName.trim();
	}

	public String getSupportPhone() {
		return supportPhone;
	}

	public void setSupportPhone(String supportPhone) {
		this.supportPhone = supportPhone == null ? null : supportPhone.trim();
	}

	public Integer getIsCrime() {
		return isCrime;
	}

	public void setIsCrime(Integer isCrime) {
		this.isCrime = isCrime;
	}

	public String getCrimeCondition() {
		return crimeCondition;
	}

	public void setCrimeCondition(String crimeCondition) {
		this.crimeCondition = crimeCondition == null ? null : crimeCondition.trim();
	}

	public String getDrugReasons() {
		return drugReasons;
	}

	public void setDrugReasons(String drugReasons) {
		this.drugReasons = drugReasons == null ? null : drugReasons.trim();
	}

	public String getDrugConsequence() {
		return drugConsequence;
	}

	public void setDrugConsequence(String drugConsequence) {
		this.drugConsequence = drugConsequence == null ? null : drugConsequence.trim();
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
}
