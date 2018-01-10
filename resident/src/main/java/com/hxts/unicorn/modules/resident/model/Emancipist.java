package com.hxts.unicorn.modules.resident.model;

import java.util.Date;
/**
 * 刑满释放人员标签信息实体类
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Emancipist {
	/**
	 * 刑满释放人员ID
	 */
    private Integer emancipistId;
	/**
	 * 人口基础信息ID
	 */
    
    private Integer residentBaseId;
	/**
	 * 是否累犯
	 */
    private Integer isRecidivismId;
	/**
	 * 原罪名
	 */
    private String originalCondemned;
	/**
	 * 原判刑期
	 */
    private String originalSentence;
	/**
	 * 服刑场所
	 */
    private String punishmentPlace;
	/**
	 * 释放日期
	 */
    private Date releaseDate;
	/**
	 * 危险性评估类型
	 */
    private String riskAssessmentType;
	/**
	 * 衔接日期
	 */
    private Date cohesionDate;
	/**
	 * 衔接情况
	 */
    private String cohesionSituation;
	/**
	 * 安置日期
	 */
    private Date placeDate;
	/**
	 * 安置情况
	 */
    private String placeSituation;
	/**
	 * 未安置原因
	 */
    private String unplaceReason;
	/**
	 * 帮教情况
	 */
    private String helpTeachSituation;
	/**
	 * 是否重新犯罪
	 */
    private Integer isCrimeAgain;
	/**
	 * 重新犯罪名
	 */
    private String reCondemned;
	/**
	 * 创建者userIduserId
	 */
    private Integer createUserId;


    public Integer getEmancipistId() {
        return emancipistId;
    }

    public void setEmancipistId(Integer emancipistId) {
        this.emancipistId = emancipistId;
    }

    public Integer getIsRecidivismId() {
        return isRecidivismId;
    }

    public void setIsRecidivismId(Integer isRecidivismId) {
        this.isRecidivismId = isRecidivismId;
    }

    public String getOriginalCondemned() {
        return originalCondemned;
    }

    public void setOriginalCondemned(String originalCondemned) {
        this.originalCondemned = originalCondemned == null ? null : originalCondemned.trim();
    }

    public String getOriginalSentence() {
        return originalSentence;
    }

    public void setOriginalSentence(String originalSentence) {
        this.originalSentence = originalSentence == null ? null : originalSentence.trim();
    }

    public String getPunishmentPlace() {
        return punishmentPlace;
    }

    public void setPunishmentPlace(String punishmentPlace) {
        this.punishmentPlace = punishmentPlace == null ? null : punishmentPlace.trim();
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRiskAssessmentType() {
        return riskAssessmentType;
    }

    public void setRiskAssessmentType(String riskAssessmentType) {
        this.riskAssessmentType = riskAssessmentType == null ? null : riskAssessmentType.trim();
    }

    public Date getCohesionDate() {
        return cohesionDate;
    }

    public void setCohesionDate(Date cohesionDate) {
        this.cohesionDate = cohesionDate;
    }

    public String getCohesionSituation() {
        return cohesionSituation;
    }

    public void setCohesionSituation(String cohesionSituation) {
        this.cohesionSituation = cohesionSituation == null ? null : cohesionSituation.trim();
    }

    public Date getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(Date placeDate) {
        this.placeDate = placeDate;
    }

    public String getPlaceSituation() {
        return placeSituation;
    }

    public void setPlaceSituation(String placeSituation) {
        this.placeSituation = placeSituation == null ? null : placeSituation.trim();
    }

    public String getUnplaceReason() {
        return unplaceReason;
    }

    public void setUnplaceReason(String unplaceReason) {
        this.unplaceReason = unplaceReason == null ? null : unplaceReason.trim();
    }

    public String getHelpTeachSituation() {
        return helpTeachSituation;
    }

    public void setHelpTeachSituation(String helpTeachSituation) {
        this.helpTeachSituation = helpTeachSituation == null ? null : helpTeachSituation.trim();
    }

    public Integer getIsCrimeAgain() {
        return isCrimeAgain;
    }

    public void setIsCrimeAgain(Integer isCrimeAgain) {
        this.isCrimeAgain = isCrimeAgain;
    }

    public String getReCondemned() {
        return reCondemned;
    }

    public void setReCondemned(String reCondemned) {
        this.reCondemned = reCondemned == null ? null : reCondemned.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

	public Integer getResidentBaseId() {
		return residentBaseId;
	}

	public void setResidentBaseId(Integer residentBaseId) {
		this.residentBaseId = residentBaseId;
	}

    @Override
	public String toString() {
		return "Emancipist_info [emancipistId=" + emancipistId + ", residentBaseId=" + residentBaseId
				+ ", isRecidivismId=" + isRecidivismId + ", originalCondemned=" + originalCondemned
				+ ", originalSentence=" + originalSentence + ", punishmentPlace=" + punishmentPlace + ", releaseDate="
				+ releaseDate + ", riskAssessmentType=" + riskAssessmentType + ", cohesionDate=" + cohesionDate
				+ ", cohesionSituation=" + cohesionSituation + ", placeDate=" + placeDate + ", placeSituation="
				+ placeSituation + ", unplaceReason=" + unplaceReason + ", helpTeachSituation=" + helpTeachSituation
				+ ", isCrimeAgain=" + isCrimeAgain + ", reCondemned=" + reCondemned + ", createUserId=" + createUserId
				+ "]";
	}


}