package com.hxts.unicorn.modules.resident.service;

import java.util.List;

import com.hxts.unicorn.modules.resident.dto.IllegalPertitionerDto;
import com.hxts.unicorn.modules.resident.model.IllegalPertitioner;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.IllegalPertitionerInfo;

public interface ISrvIllegalPertitioner{
	/**
	 * 
	 * <保存新增或编辑非法上访人口信息>
	 * @param illegalPertitionerInfo 非法上访人口标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(IllegalPertitionerInfo illegalPertitionerInfo);
	/**
	 * 
	 * <根据非法上访人口id删除非法上访人口信息>
	 * @param illegalPertitionerId 非访人口id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByIllegalPertitionerId(Integer illegalPertitionerId);
	/**
	 * 
	 * <根据户籍人口id查询非访人口信息>
	 * @param illegalPertitionerId 非访人口id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	IllegalPertitionerInfo selectByIllegalPertitionerId(Integer illegalPertitionerId);
	/**
	 * 
	 * <根据关键字查询非访人口信息列表>
	 * @param uniformityFlag 人户一致标识
	 * @param integrity 信息完整度（0-100）
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param gridId 网格id
	 * @return 非访人口信息列表
	 * @see [类、类#方法、类#成员]
	 */
    List<IllegalPertitionerDto> getIllegalPertitionerListByKeywords(String pertitionType,String lastPertitionTime,String isCrime, String name, String idNo, Integer gridId);
    /**
     * 
     * <非访人口页面统计，按学历统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getDegreeStats();
    /**
     * 
     * <非访人口页面统计，按年龄分布统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getAgeStats();
}
