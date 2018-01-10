package com.hxts.unicorn.modules.resident.service;

import java.util.List;

import com.hxts.unicorn.modules.resident.dto.AssistCrowdDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.SubsistenceAllowanceInfo;

public interface ISrvSubsistenceAllowance{
	/**
	 * 
	 * <保存新增或编辑低保人员信息>
	 * @param subsistenceAllowanceInfo 低保人员标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(SubsistenceAllowanceInfo subsistenceAllowanceInfo);
	/**
	 * 
	 * <根据低保人员id删除低保人员信息>
	 * @param subsistAllowanceId 低保人员id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteBySubsistAllowanceId(Integer subsistAllowanceId);
	/**
	 * 
	 * <根据低保人员id查询低保人员信息>
	 * @param subsistAllowanceId 低保人员id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	SubsistenceAllowanceInfo selectBySubsistAllowanceId(Integer subsistAllowanceId);
	
	  /**
     * 
     * <帮扶人群页面，按照帮扶人群类型分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getAssistTypeStats();
    
    List<Integer> getAssistTypeStatsCount();
    
    /**
	 * <根据关键词查询帮扶人群信息列表>
	 * 
	 * @param  healthCondition 健康状况
	 * @param assistType 帮扶类型
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param pageNum 当前页 可选 ，默认 1
	 * @param pageSize 每页显示信息条数， 可选， 默认10
	 * @return 户籍人口信息及分页信息
	 * @see [类、类#方法、类#成员]
	 */
    List<AssistCrowdDto> getAssistCrowdListByKeywords(String healthCondition,String assistType,String name,String idNo,List<Integer> residentBaseIds);
}
