package com.hxts.unicorn.modules.resident.service;

import java.util.List;

import com.hxts.unicorn.modules.resident.dto.FamilyRelationDto;
import com.hxts.unicorn.platform.interfaces.biz.FamilyRelationInfo;

public interface ISrvFamilyRelation{
	/**
	 * 
	 * <根据本人id获取家庭关系列表>
	 * <功能详细描述>
	 * @param selfId 本人id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<FamilyRelationDto> getFamilyRelation(Integer selfId);
	
	/**
	 * <根据身份证号码查询人员关系列表>
	 * 
	 * @param  idNo 身份证号码
	 * @return 人员关系列表
	 * @see [类、类#方法、类#成员]
	 */
	public List<FamilyRelationDto> getFamilyRelations(String idNo);
	/**
	 * 
	 * <保存新增或者编辑家庭关系>
	 * <功能详细描述>
	 * @param familyRelation 家庭关系信息
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public void setFamilyRelation(FamilyRelationInfo familyRelationInfo);
	/**
	 * 
	 * <根据家庭关系id删除家庭关系信息>
	 * <功能详细描述>
	 * @param familyRelationId 家庭关系id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public void deleteFamilyRelation(Integer familyRelationId);
}
