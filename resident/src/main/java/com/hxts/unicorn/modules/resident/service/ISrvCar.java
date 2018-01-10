package com.hxts.unicorn.modules.resident.service;

import java.util.List;

import com.hxts.unicorn.platform.interfaces.biz.CarInfo;

public interface ISrvCar {
	/**
	 * 
	 * <保存新增或编辑人车信息>
	 * @param CarInfo 人车标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(CarInfo carInfo);
	/**
	 * 
	 * <删除人车信息>
	 * @param CarInfoId 人车标签ID
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */	
	int deleteByCarInfo(Integer CarInfoId);
	/**
	 * 
	 * <根据CarInfoId查询 人车信息>
	 * @param CarInfoId 人车标签ID
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */		
	CarInfo selectByCarInfoId(Integer CarInfoId);
	/**
	 * 
	 * <根据residentBaseId查询 人车信息>
	 * @param residentBaseId 人员信息ID
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */		
	List<CarInfo> getcarInfo(Integer residentBaseId);
}
