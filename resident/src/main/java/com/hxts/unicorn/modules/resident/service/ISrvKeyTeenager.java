package com.hxts.unicorn.modules.resident.service;

import java.util.List;

import com.hxts.unicorn.modules.resident.dto.KeyTeenagerDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.KeyTeenagerInfo;

public interface ISrvKeyTeenager{
	/**
	 * 
	 * <保存新增或编辑重点青少年信息>
	 * @param KeyTeenagerInfo 重点青少年标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(KeyTeenagerInfo keyTeenagerInfo);
	/**
	 * 
	 * <根据重点青少年id删除重点青少年信息>
	 * @param KeyTeenagerId 重点青少年id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByKeyTeenagerId(Integer keyTeenagerId);
	/**
	 * 
	 * <根据重点青少年id查询重点青少年信息>
	 * @param keyTeenagerId 重点青少年id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	KeyTeenagerInfo selectByKeyTeenagerId(Integer keyTeenagerId);
	/**
	 * 
	 * <根据关键字查询重点青少年信息列表>
	 * @param  teenagerType 人员类型
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param gridId 所属网格
	 * @return 流动人口信息列表
	 * @see [类、类#方法、类#成员]
	 */
	public List<KeyTeenagerDto> getKeyTeenagerListByKeywords(String teenagerType,String name, String idNo,List<Integer> residentBaseIds);
	
    /**
     * 
     * <重点青少年页面，按照人员类型分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getTeenagerTypeStats();
}
