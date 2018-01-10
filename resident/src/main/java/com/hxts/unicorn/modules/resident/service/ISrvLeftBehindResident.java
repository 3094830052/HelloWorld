package com.hxts.unicorn.modules.resident.service;

import java.util.List;

import com.hxts.unicorn.modules.resident.dto.LeftBehindResidentDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.LeftBehindResidentInfo;

public interface ISrvLeftBehindResident{
	/**
	 * 
	 * <保存新增或编辑留守人口信息>
	 * @param leftBehindResidentInfo 留守人口标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(LeftBehindResidentInfo leftBehindResidentInfo);
	/**
	 * 
	 * <根据留守人口id删除留守人口信息>
	 * @param leftBehindId 留守人口id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByLeftBehindId(Integer leftBehindId);
	/**
	 * 
	 * <根据留守人口id查询留守人口信息>
	 * @param leftBehindId 留守人口id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	LeftBehindResidentInfo selectByLeftBehindId(Integer leftBehindId);
	/**
	 * 
	 * <根据关键字查询留守人口信息列表>
	 * @param healthCondition 健康状况
	 * @param leftBehindResidentType 留守类型
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param gridId 所属网格
	 * @return 留守人口信息列表
	 * @see [类、类#方法、类#成员]
	 */
    List<LeftBehindResidentDto> getLeftBehindListByKeywords(String healthCondition,String leftBehindResidentType, String name, String idNo, List<Integer> residentBaseIds);
    /**
     * 
     * <留守人口页面统计，按留守人员类型分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getLeftBehindTypeStats();
    /**
     * 
     * <留守人口页面统计，按网格分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getGridStats();
}
