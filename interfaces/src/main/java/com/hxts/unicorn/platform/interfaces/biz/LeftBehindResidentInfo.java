package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;
/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年10月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LeftBehindResidentInfo implements Serializable {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4500153161192719963L;
	/**
	 * 留守人口标签信息id
	 */
    private Integer leftBehindId;
    /**
	 * 人口基础信息id
	 */
    private Integer residentBaseId;
    /**
	 * 健康状况
	 */
    private String healthCondition;
    /**
	 * 个人年收入
	 */
    private String personAnnualIncome;
    /**
	 * 人户一致标识
	 */
    private String uniformityFlag;
    /**
	 * 留守人员类型
	 */
    private String leftBehindResidentType;
    /**
	 * 家庭主要成员身份号码
	 */
    private String mainMemberIdcardNo;
    /**
	 * 家庭主要成员姓名
	 */
    private String mainMemberName;
    /**
	 * 家庭主要成员健康状况
	 */
    private String mainMemberHealthCondition;
    /**
	 * 与留守人员关系
	 */
    private String leftBehindResidentRelationship;
    /**
	 * 家庭主要成员联系方式
	 */
    private String mainMemberContact;
    /**
	 * 家庭主要成员工作详细地址
	 */
    private String mainMemberAddress;
    /**
	 * 家庭年收入
	 */
    private String familyAnnualIncome;
    /**
	 * 困难及诉求
	 */
    private String difficultAppeal;
    /**
	 * 帮扶情况
	 */
    private String assistState;
    /**
  	 * 创建者userId
  	 */
    private Integer createUserId;

    public Integer getLeftBehindId() {
        return leftBehindId;
    }

    public void setLeftBehindId(Integer leftBehindId) {
        this.leftBehindId = leftBehindId;
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

    public String getPersonAnnualIncome() {
        return personAnnualIncome;
    }

    public void setPersonAnnualIncome(String personAnnualIncome) {
        this.personAnnualIncome = personAnnualIncome == null ? null : personAnnualIncome.trim();
    }

    public String getUniformityFlag() {
        return uniformityFlag;
    }

    public void setUniformityFlag(String uniformityFlag) {
        this.uniformityFlag = uniformityFlag == null ? null : uniformityFlag.trim();
    }

    public String getLeftBehindResidentType() {
        return leftBehindResidentType;
    }

    public void setLeftBehindResidentType(String leftBehindResidentType) {
        this.leftBehindResidentType = leftBehindResidentType == null ? null : leftBehindResidentType.trim();
    }

    public String getMainMemberIdcardNo() {
        return mainMemberIdcardNo;
    }

    public void setMainMemberIdcardNo(String mainMemberIdcardNo) {
        this.mainMemberIdcardNo = mainMemberIdcardNo == null ? null : mainMemberIdcardNo.trim();
    }

    public String getMainMemberName() {
        return mainMemberName;
    }

    public void setMainMemberName(String mainMemberName) {
        this.mainMemberName = mainMemberName == null ? null : mainMemberName.trim();
    }

    public String getMainMemberHealthCondition() {
        return mainMemberHealthCondition;
    }

    public void setMainMemberHealthCondition(String mainMemberHealthCondition) {
        this.mainMemberHealthCondition = mainMemberHealthCondition == null ? null : mainMemberHealthCondition.trim();
    }

    public String getLeftBehindResidentRelationship() {
        return leftBehindResidentRelationship;
    }

    public void setLeftBehindResidentRelationship(String leftBehindResidentRelationship) {
        this.leftBehindResidentRelationship = leftBehindResidentRelationship == null ? null : leftBehindResidentRelationship.trim();
    }

    public String getMainMemberContact() {
        return mainMemberContact;
    }

    public void setMainMemberContact(String mainMemberContact) {
        this.mainMemberContact = mainMemberContact == null ? null : mainMemberContact.trim();
    }

    public String getMainMemberAddress() {
        return mainMemberAddress;
    }

    public void setMainMemberAddress(String mainMemberAddress) {
        this.mainMemberAddress = mainMemberAddress == null ? null : mainMemberAddress.trim();
    }

    public String getFamilyAnnualIncome() {
        return familyAnnualIncome;
    }

    public void setFamilyAnnualIncome(String familyAnnualIncome) {
        this.familyAnnualIncome = familyAnnualIncome == null ? null : familyAnnualIncome.trim();
    }

    public String getDifficultAppeal() {
        return difficultAppeal;
    }

    public void setDifficultAppeal(String difficultAppeal) {
        this.difficultAppeal = difficultAppeal == null ? null : difficultAppeal.trim();
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