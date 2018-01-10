/*
 * 文 件 名:  ResidentOfDao.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月21日
 * 修改内容:  
 */
package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;

/**
 * <现住人口数据持久层接口>
 * 
 * @author peidehao 0380008672
 * @version [版本号, 2017年10月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ResidentOfHouseDao {

	public void deleteRohById(@Param("residentOfHouseId") Long residentOfHouseId);

	public int addResidentOfHouse(ResidentOfHouseInfo residentOfHouse);
	
	public void updateResidentOfHouse(ResidentOfHouseInfo residentOfHouse);

	public ResidentOfHouseInfo queryBaseRohById(@Param("residentOfHouseId") Long residentOfHouseId);

	public List<ResidentOfHouseInfo> queryRohByHouseId(@Param("houseId") Long houseId);

	public List<ResidentOfHouseInfo> queryBaseRohByHouseId(@Param("houseId") Long houseId);

	public List<ResidentOfHouseInfo> queryRohByHouseIds(@Param("houses") List<HouseInfo> houses);

	public List<ResidentOfHouseInfo> queryBaseRohByHouseIds(@Param("houses") List<HouseInfo> houses);

	public List<ResidentOfHouseInfo> queryBaseRohByResidentId(@Param("residentId") Long residentId);

	public List<ResidentOfHouseInfo> queryRohAndHousesByResidentId(@Param("residentId") Long residentId);
	
	public List<ResidentOfHouseInfo> queryRohByResidentId(@Param("residentId") Long residentId);

	public List<ResidentOfHouseInfo> queryBaseRohByResidentIds(@Param("residentIds") List<Long> residentIds);

	public List<ResidentOfHouseInfo> queryRohByResidentIds(@Param("residentIds") List<Long> residentIds);

	public ResidentOfHouseInfo queryRohByHouseIdAndResidentId(@Param("houseId") Long houseId,
			@Param("residentId") Long residentId);

}
