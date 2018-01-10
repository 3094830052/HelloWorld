package com.hxts.unicorn.modules.resident.service;

import com.hxts.unicorn.platform.interfaces.biz.DisabledPeopleInfo;

public interface ISrvDisabledPeople{
	/**
	 * 
	 * <保存新增或编辑残障人士信息>
	 * @param disabledPeopleInfo 残障人士标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(DisabledPeopleInfo disabledPeopleInfo);
	/**
	 * 
	 * <根据残障人士id删除残障人士信息>
	 * @param disabledPeopleId 残障人士id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByDisabledPeopleId(Integer disabledPeopleId);
	/**
	 * 
	 * <根据残障人士id查询残障人士信息>
	 * @param disabledPeopleId 残障人士id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	DisabledPeopleInfo selectByDisabledPeopleId(Integer disabledPeopleId);
}
