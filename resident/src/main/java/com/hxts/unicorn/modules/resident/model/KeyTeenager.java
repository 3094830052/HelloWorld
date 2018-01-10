package com.hxts.unicorn.modules.resident.model;

public class KeyTeenager {
	/**
	 * 重点青少年id
	 */
    private Integer keyTeenagerId;
    /**
	 * 人口基础信息id
	 */
    private Integer residentBaseId;
    /**
	 * 人员类型
	 */
    private String teenagerType;
    /**
	 * 家庭情况
	 */
    private String familySituation;
    /**
	 * 监护人身份号码
	 */
    private String guarderIdNo;
    /**
	 * 监护人姓名
	 */
    private String guarderName;
    /**
	 *与监护人关系
	 */
    private String guarderRelationship;
    /**
	 * 监护人联系方式
	 */
    private String guarderContact;
    /**
	 * 监护人居住详址
	 */
    private String guarderAddr;
    /**
	 * 是否违法犯罪
	 */
    private Integer isCrime;
    /**
	 * 帮扶人姓名
	 */
    private String assisterName;
    /**
	 * 帮扶人联系方式
	 */
    private String assisterContact;
    /**
	 * 帮扶手段
	 */
    private String assistMeasure;
    /**
	 * 帮扶情况
	 */
    private String assistState;
    /**
	 * 创建者userId
	 */
    private Integer createUserId;

    public Integer getKeyTeenagerId() {
        return keyTeenagerId;
    }

    public void setKeyTeenagerId(Integer keyTeenagerId) {
        this.keyTeenagerId = keyTeenagerId;
    }

    public Integer getResidentBaseId() {
        return residentBaseId;
    }

    public void setResidentBaseId(Integer residentBaseId) {
        this.residentBaseId = residentBaseId;
    }

    public String getTeenagerType() {
        return teenagerType;
    }

    public void setTeenagerType(String teenagerType) {
        this.teenagerType = teenagerType == null ? null : teenagerType.trim();
    }

    public String getFamilySituation() {
        return familySituation;
    }

    public void setFamilySituation(String familySituation) {
        this.familySituation = familySituation == null ? null : familySituation.trim();
    }

    public String getGuarderIdNo() {
        return guarderIdNo;
    }

    public void setGuarderIdNo(String guarderIdNo) {
        this.guarderIdNo = guarderIdNo == null ? null : guarderIdNo.trim();
    }

    public String getGuarderName() {
        return guarderName;
    }

    public void setGuarderName(String guarderName) {
        this.guarderName = guarderName == null ? null : guarderName.trim();
    }

    public String getGuarderRelationship() {
        return guarderRelationship;
    }

    public void setGuarderRelationship(String guarderRelationship) {
        this.guarderRelationship = guarderRelationship == null ? null : guarderRelationship.trim();
    }

    public String getGuarderContact() {
        return guarderContact;
    }

    public void setGuarderContact(String guarderContact) {
        this.guarderContact = guarderContact == null ? null : guarderContact.trim();
    }

    public String getGuarderAddr() {
        return guarderAddr;
    }

    public void setGuarderAddr(String guarderAddr) {
        this.guarderAddr = guarderAddr == null ? null : guarderAddr.trim();
    }

    public Integer getIsCrime() {
        return isCrime;
    }

    public void setIsCrime(Integer isCrime) {
        this.isCrime = isCrime;
    }

    public String getAssisterName() {
        return assisterName;
    }

    public void setAssisterName(String assisterName) {
        this.assisterName = assisterName == null ? null : assisterName.trim();
    }

    public String getAssisterContact() {
        return assisterContact;
    }

    public void setAssisterContact(String assisterContact) {
        this.assisterContact = assisterContact == null ? null : assisterContact.trim();
    }

    public String getAssistMeasure() {
        return assistMeasure;
    }

    public void setAssistMeasure(String assistMeasure) {
        this.assistMeasure = assistMeasure == null ? null : assistMeasure.trim();
    }

    public String getAssistState() {
        return assistState;
    }

    public void setAssistState(String assistState) {
        this.assistState = assistState == null ? null : assistState.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}