package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.modules.resident.dto.AssistCrowdDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.model.SubsistenceAllowance;
import com.hxts.unicorn.platform.interfaces.biz.ResidentBaseInfo;
import com.hxts.unicorn.platform.interfaces.biz.SubsistenceAllowanceInfo;

public interface SubsistenceAllowanceDao {
    int insert(SubsistenceAllowance record);

    int insertSelective(SubsistenceAllowance record);
    
    int deleteByPrimaryKey(Integer subsistAllowanceId);

    SubsistenceAllowanceInfo selectByPrimaryKey(Integer subsistAllowanceId);

    int updateByPrimaryKey(SubsistenceAllowance record);
    
    int updateByPrimaryKeySelective(SubsistenceAllowance record);
    
    /**
     * 
     * <帮扶人群页面，按照帮扶人群类型分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getAssistTypeStats(@Param("residentBaseIds")List<Integer>residentBaseIds);
    
    List<Integer> getAssistTypeStatsCount(@Param("residentBaseIds")List<Integer>residentBaseIds);
    
    List<AssistCrowdDto> queryAssistCrowdListByKeywords(@Param("healthCondition")String healthCondition,@Param("assistType")String assistType,@Param("name")String name,@Param("idNo")String idNo,@Param("residentBaseIds")List<Integer> residentBaseIds);
    
    List<ResidentBaseInfo> queryBaseInfoByKeywords(@Param("healthCondition")String healthCondition,@Param("assistType")String assistType,@Param("name")String name,@Param("idNo")String idNo,@Param("residentBaseIds")List<Integer> residentBaseIds);
}