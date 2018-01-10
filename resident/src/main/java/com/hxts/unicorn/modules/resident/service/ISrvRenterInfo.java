package com.hxts.unicorn.modules.resident.service;

import java.math.BigDecimal;
import java.util.List;

import com.hxts.unicorn.modules.resident.dto.RenterInfoDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.RenterInfo;

public interface ISrvRenterInfo {
	/**
	 * 
	 * <根据关键词查询出租屋承租人列表>
	 * <功能详细描述>
	 * @param gridId 网格id
	 * @param name 姓名
	 * @param idNo 身份证号码
	 * @param integrity 信息完整度（0-100）
	 * @param ethnicity 民族
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<RenterInfoDto> getRenterInfoList(List<Integer> residentBaseIds, String name, String idNo,BigDecimal integrity,String ethnicity);
	/**
	 * 
	 * <保存新增或者编辑的出租屋承租人的信息>
	 * <功能详细描述>
	 * @param residentBaseInfo 人口基本信息
	 * @param residentOfHouseInfo 出租屋承租人独有的信息
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	/**
	 * 
	 * <保存新增或者编辑的出租屋承租人的信息>
	 * <功能详细描述>
	 * @param houseId 出租屋房屋id
	 * @param residentBaseId 人口基础信息id
	 * @param rentType 出租用途
	 * @param hiddenDanger 隐患类型
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int save(RenterInfo RenterInfo);
	/**
	 * 
	 * <从房屋模块获取所有的租住人的id，再统计其年龄分布>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
    List<StatsResult> getAgeStats();
    
    /**
	 * 
	 * <统计出租房数量分布>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
    List<StatsResult> getRentHouseNumsStats();
}
