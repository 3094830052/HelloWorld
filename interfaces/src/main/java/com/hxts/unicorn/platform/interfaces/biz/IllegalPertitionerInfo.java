package com.hxts.unicorn.platform.interfaces.biz;

public class IllegalPertitionerInfo {
	/**
	 * 非法上访人员id
	 */
    private Integer illegalPertitionerId;
    /**
	 * 人口基本信息id
	 */
    private Integer residentBaseId;
    /**
	 * 上访类型
	 */
    private String pertitionType;
    /**
	 * 上访诉求
	 */
    private String pertitionAppeal;
    /**
	 * 首次上访时间
	 */
    private String firstPertitionTime;
    /**
	 * 最近上访时间
	 */
    private String lastPertitionTime;
    /**
	 * 是否涉案
	 */
    private String isCrime;
    /**
	 * 涉案状态
	 */
    private String crimeStatus;
    /**
	 * 创建者userId
	 */
    private Integer createUserId;
    
    public Integer getIllegalPertitionerId() {
        return illegalPertitionerId;
    }

    public void setIllegalPertitionerId(Integer illegalPertitionerId) {
        this.illegalPertitionerId = illegalPertitionerId;
    }

    public Integer getResidentBaseId() {
        return residentBaseId;
    }

    public void setResidentBaseId(Integer residentBaseId) {
        this.residentBaseId = residentBaseId;
    }

    public String getPertitionType() {
        return pertitionType;
    }

    public void setPertitionType(String pertitionType) {
        this.pertitionType = pertitionType == null ? null : pertitionType.trim();
    }

    public String getPertitionAppeal() {
        return pertitionAppeal;
    }

    public void setPertitionAppeal(String pertitionAppeal) {
        this.pertitionAppeal = pertitionAppeal == null ? null : pertitionAppeal.trim();
    }

    public String getFirstPertitionTime() {
        return firstPertitionTime;
    }

    public void setFirstPertitionTime(String firstPertitionTime) {
        this.firstPertitionTime = firstPertitionTime;
    }

    public String getLastPertitionTime() {
        return lastPertitionTime;
    }

    public void setLastPertitionTime(String lastPertitionTime) {
        this.lastPertitionTime = lastPertitionTime;
    }

    public String getIsCrime() {
        return isCrime;
    }

    public void setIsCrime(String isCrime) {
        this.isCrime = isCrime == null ? null : isCrime.trim();
    }

    public String getCrimeStatus() {
        return crimeStatus;
    }

    public void setCrimeStatus(String crimeStatus) {
        this.crimeStatus = crimeStatus == null ? null : crimeStatus.trim();
    }

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
}