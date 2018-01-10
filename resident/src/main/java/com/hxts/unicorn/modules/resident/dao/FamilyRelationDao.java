package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import com.hxts.unicorn.modules.resident.dto.FamilyRelationDto;
import com.hxts.unicorn.modules.resident.model.FamilyRelation;

public interface FamilyRelationDao {
    int deleteByPrimaryKey(Integer familyRelationId);

    int insert(FamilyRelation record);

    int insertSelective(FamilyRelation record);

    FamilyRelation selectByPrimaryKey(Integer familyRelationId);

    int updateByPrimaryKeySelective(FamilyRelation record);

    int updateByPrimaryKey(FamilyRelation record);
    
    List<FamilyRelationDto> getFamilyRelationBySelfId(Integer selfId);
    
    List<FamilyRelationDto> getFamilyRelationByIdNo(String idNo);
}