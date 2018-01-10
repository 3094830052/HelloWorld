package com.hxts.unicorn.modules.resident.model;

/**
 * 户籍人口标签信息临时
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HouseholdRegisteredResidentTemp {
	/**
	 * 网格员确认状态
	 */
	private Integer status;
	/**
	 * 户籍人口标签信息id
	 */
    private Integer householdId;
    /**
	 * 人口基础信息id
	 */
    private Integer residentBaseId;
    /**
	 * 人户一致标识
	 */
    private String uniformityFlag;
    /**
	 * 户号
	 */
    private String householdNo;
    /**
	 * 户主公民身份号码
	 */
    private String householderIdcardNo;
    /**
	 * 户主姓名
	 */
    private String householderName;
    /**
	 * 与户主关系
	 */
    private String householderRelationship;
    /**
	 * 户主联系方式
	 */
    private String householderContact;
    /**
  	 * 创建者userId
  	 */
    private Integer createUserId;
    
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getHouseholdId() {
		return householdId;
	}

	public void setHouseholdId(Integer householdId) {
		this.householdId = householdId;
	}

	public Integer getResidentBaseId() {
		return residentBaseId;
	}

	public void setResidentBaseId(Integer residentBaseId) {
		this.residentBaseId = residentBaseId;
	}

	public String getUniformityFlag() {
        return uniformityFlag;
    }

    public void setUniformityFlag(String uniformityFlag) {
        this.uniformityFlag = uniformityFlag == null ? null : uniformityFlag.trim();
    }

    public String getHouseholdNo() {
        return householdNo;
    }

    public void setHouseholdNo(String householdNo) {
        this.householdNo = householdNo == null ? null : householdNo.trim();
    }

    public String getHouseholderIdcardNo() {
        return householderIdcardNo;
    }

    public void setHouseholderIdcardNo(String householderIdcardNo) {
        this.householderIdcardNo = householderIdcardNo == null ? null : householderIdcardNo.trim();
    }

    public String getHouseholderName() {
        return householderName;
    }

    public void setHouseholderName(String householderName) {
        this.householderName = householderName == null ? null : householderName.trim();
    }

    public String getHouseholderRelationship() {
        return householderRelationship;
    }

    public void setHouseholderRelationship(String householderRelationship) {
        this.householderRelationship = householderRelationship == null ? null : householderRelationship.trim();
    }

    public String getHouseholderContact() {
        return householderContact;
    }

    public void setHouseholderContact(String householderContact) {
        this.householderContact = householderContact == null ? null : householderContact.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}