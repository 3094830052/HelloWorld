package com.hxts.unicorn.modules.resident.service;

import java.math.BigDecimal;
import java.util.List;

import com.hxts.unicorn.modules.resident.dto.ForeignerInfoDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.ForeignerResidentInfo;

public interface ISrvForeignerInfo {
	/**
	 * 
	 * <保存新增或者编辑的境外人口信息>
	 * <功能详细描述>
	 * @param foreignerInfo 境外人口标签信息
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int save(ForeignerResidentInfo foreignerInfo);
	/**
	 * 
	 * <根据境外人口id删除境外人口信息>
	 * @param foreignerId 境外人口id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByForeignerId(Integer foreignerId);
	/**
	 * 
	 * <根据境外人口id查询境外人口标签信息>
	 * <功能详细描述>
	 * @param foreignerId 境外人口id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	ForeignerResidentInfo selectByForeignerId(Integer foreignerId);
	/**
	 * 
	 * <根据关键字查询境外人员信息列表>
	 * @param nationality 国籍(地区)
	 * @param integrity 信息完整度（0-100）
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param gridId 网格id
	 * @return 户籍人口信息列表
	 * @see [类、类#方法、类#成员]
	 */
    List<ForeignerInfoDto> getForeignerInfoListByKeywords(String nationality,BigDecimal integrity, String name, String idNo, List<Integer> residentBaseIds);
	/**
     * 
     * <境外人口页面统计，按来华目的统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getPurposeStats();
    /**
     * 
     * <境外人口页面统计，按年龄分布统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getAgeStats();
}
