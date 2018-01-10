package com.hxts.unicorn.modules.resident.service;

import com.hxts.unicorn.platform.interfaces.biz.LivingAloneAgedInfo;

public interface ISrvLivingAloneAged{
	/**
	 * 
	 * <保存新增或编辑独居老人信息>
	 * @param livingAloneAgedInfo 独居老人标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(LivingAloneAgedInfo livingAloneAgedInfo);
	/**
	 * 
	 * <根据独居老人id删除独居老人信息>
	 * @param livingAloneAgedId 独居老人id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByLivingAloneAgedId(Integer livingAloneAgedId);
	/**
	 * 
	 * <根据独居老人id查询独居老人信息>
	 * @param livingAloneAgedId 独居老人id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	LivingAloneAgedInfo selectByLivingAloneAgedId(Integer livingAloneAgedId);
}
