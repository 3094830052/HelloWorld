package com.hxts.unicorn.modules.resident.dto;

public class FamilyRelationDto {
	/**
	 * 家庭关系id
	 */
    private Integer familyRelationId;
    /**
	 * 本人id
	 */
    private Integer selfId;
    /**
	 * 关系人id
	 */
    private Integer otherId;
    /**
	 * 家庭关系
	 */
    private String relation;
    /**
   	 * 关系人公民身份号码
   	 */
    private String idNo;
    /**
   	 * 关系人姓名
   	 */
    private String name;
    /**
   	 * 关系人性别
   	 */
    private String sex;
    /**
   	 * 关系人出生日期
   	 */
    private String birthDate;
     /**
   	 * 关系人民族
   	 */
    private String ethnicity;
    /**
 	 * 关系人联系方式
 	 */
     private String contact;

    public Integer getFamilyRelationId() {
        return familyRelationId;
    }

    public void setFamilyRelationId(Integer familyRelationId) {
        this.familyRelationId = familyRelationId;
    }

    public Integer getSelfId() {
        return selfId;
    }

    public void setSelfId(Integer selfId) {
        this.selfId = selfId;
    }

    public Integer getOtherId() {
        return otherId;
    }

    public void setOtherId(Integer otherId) {
        this.otherId = otherId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation == null ? null : relation.trim();
    }

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
}