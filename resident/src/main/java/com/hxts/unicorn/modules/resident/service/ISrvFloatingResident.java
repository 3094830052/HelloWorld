package com.hxts.unicorn.modules.resident.service;

import java.util.List;

import com.hxts.unicorn.modules.resident.dto.FloatingResidentDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.FloatingResidentInfo;

public interface ISrvFloatingResident{
	
	/**
	 * 
	 * <保存新增或编辑流动人口信息>
	 * @param floatingResidentInfo 流动人口标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(FloatingResidentInfo floatingResidentInfo);
	/**
	 * 
	 * <根据流动人口id删除流动人口信息>
	 * @param floatingId
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByFloatingId(Integer floatingId);
	/**
	 * 
	 * <根据流动人口id查询流动人口信息>
	 * @param floatingId
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	FloatingResidentInfo selectByFloatingId(Integer floatingId);
	/**
	 * 
	 * <根据关键字查询流动人口信息列表>
	 * @param inflowReason 流入原因
	 * @param isFocusPerson 是否重点关注人员
	 * @param certificateHandlingType 办证类型
	 * @param certificateExpireDate 证件到期日期
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param gridId 所属网格
	 * @return 流动人口信息列表
	 * @see [类、类#方法、类#成员]
	 */
    List<FloatingResidentDto> getFloatingResidentListByKeywords(String inflowReason,String isFocusPerson,String certificateHandlingType,String certificateExpireDate,String name, String idNo,List<Integer> residentBaseIds);
    /**
     * 
     * <流动人口页面，按照流入原因分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getInflowReasonStats();
    /**
     * 
     * <流动人口页面，按照证件到期时间分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getCertificateExpireStats();
}
