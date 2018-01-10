package com.hxts.unicorn.modules.resident.service;

import java.math.BigDecimal;
import java.util.List;

import com.hxts.unicorn.modules.resident.dto.HouseholdRegisteredResidentDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.HouseholdResidentInfo;

public interface ISrvHouseholdRegisteredResident{
	/**
	 * 
	 * <保存新增或编辑户籍人口信息>
	 * @param householdResidentInfo 户籍人口标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(HouseholdResidentInfo householdResidentInfo);
	/**
	 * 
	 * <根据户籍人口id删除户籍人口信息>
	 * @param householdId 户籍人口id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByHouseholdId(Integer householdId);
	/**
	 * 
	 * <根据户籍人口id查询户籍人口信息>
	 * @param householdId 户籍人口id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	HouseholdResidentInfo selectByHouseholdId(Integer householdId);
	/**
	 * <根据户号和身份证关键字查询户籍人口信息列表>
	 * 
	 * @param  householdNo 户号
	 * @param  idNo 身份号码
	 * @return 户籍人口信息列表
	 * @see [类、类#方法、类#成员]
	 */
	List<HouseholdResidentInfo> selectByKeyWord(String householdNo,String idNo);
	/**
	 * 
	 * <根据关键字查询户籍人口信息列表>
	 * @param uniformityFlag 人户一致标识
	 * @param integrity 信息完整度（0-100）
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param gridId 网格id
	 * @return 户籍人口信息列表
	 * @see [类、类#方法、类#成员]
	 */
    List<HouseholdRegisteredResidentDto> getHouseholdRegisterListByKeywords(String uniformityFlag,BigDecimal integrity, String name, String idNo, List<Integer> residentBaseIds);
    /**
     * 
     * <户籍人口页面统计，按学历统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getDegreeStats();
    /**
     * 
     * <户籍人口页面统计，按年龄分布统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getAgeStats();
}
