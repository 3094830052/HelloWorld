package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;
import org.hibernate.validator.constraints.Length;
/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HouseholdResidentInfo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 978165466670269804L;
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
    @Length(max=50,message="户主姓名最大长度不超过25个汉字")
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
		this.uniformityFlag = uniformityFlag;
	}

	public String getHouseholdNo() {
		return householdNo;
	}

	public void setHouseholdNo(String householdNo) {
		this.householdNo = householdNo;
	}

	public String getHouseholderIdcardNo() {
		return householderIdcardNo;
	}

	public void setHouseholderIdcardNo(String householderIdcardNo) {
		this.householderIdcardNo = householderIdcardNo;
	}

	public String getHouseholderName() {
		return householderName;
	}

	public void setHouseholderName(String householderName) {
		this.householderName = householderName;
	}

	public String getHouseholderRelationship() {
		return householderRelationship;
	}

	public void setHouseholderRelationship(String householderRelationship) {
		this.householderRelationship = householderRelationship;
	}

	public String getHouseholderContact() {
		return householderContact;
	}

	public void setHouseholderContact(String householderContact) {
		this.householderContact = householderContact;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
}