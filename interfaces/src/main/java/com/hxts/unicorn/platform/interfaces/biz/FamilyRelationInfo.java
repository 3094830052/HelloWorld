package com.hxts.unicorn.platform.interfaces.biz;
/**
 * 
 * <家庭关系>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FamilyRelationInfo {
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
}