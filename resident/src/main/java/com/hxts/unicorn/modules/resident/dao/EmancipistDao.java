package com.hxts.unicorn.modules.resident.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.modules.resident.dto.EmancipistDto;
import com.hxts.unicorn.modules.resident.model.Emancipist;
import com.hxts.unicorn.platform.interfaces.biz.EmancipistInfo;

public interface EmancipistDao {
    int deleteByPrimaryKey(Integer emancipistId);

    int insert(Emancipist record);

    int insertSelective(Emancipist record);

    EmancipistInfo selectByPrimaryKey(Integer emancipistId);

    int updateByPrimaryKeySelective(Emancipist record);

    int updateByPrimaryKey(Emancipist record);
    
    List<EmancipistDto> queryEmancipistListByKeywords(@Param("cohesionDate")Date cohesionDate,@Param("riskAssessmentType")String riskAssessmentType,@Param("placeSituation")String placeSituation,@Param("name")String name,@Param("idNo")String idNo,@Param("residentBaseIds") List<Integer> residentBaseIds);
    
    
}