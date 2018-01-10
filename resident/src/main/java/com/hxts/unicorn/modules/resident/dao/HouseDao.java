/*
 * 文 件 名:  HouseDao.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月21日
 * 修改内容:  
 */
package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.platform.interfaces.biz.BuildingInfo;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.HouseModelInfo;

/**
 * <房屋 数据持久层接口>
 * 
 * @author peidehao 0380008672
 * @version [版本号, 2017年10月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface HouseDao {

	public void deleteHouseById(Long houseId);

	public void addHouse(HouseInfo house);

	public void batchAddHouse(List<HouseInfo> houses);

	public void modifyHouse(HouseInfo house);

	public void bathModifyHouse(List<HouseInfo> houses);

	public HouseInfo queryHouseById(@Param("houseId") Long houseId);

	public HouseInfo queryBaseHouseById(@Param("houseId") Long houseId);

	public List<HouseInfo> queryHousesByBuildingId(@Param("buildingId") Long buildingId);

	public List<HouseInfo> queryBaseHousesByBuildingId(@Param("buildingId") Long buildingId);

	public List<HouseInfo> queryHousesByBuildingIds(@Param("buildingIds") List<Long> buildingIds);

	public List<HouseInfo> queryBaseHousesByBuildingIds(@Param("buildingIds") List<Long> buildingIds);

	public List<HouseModelInfo> queryHouseModels(@Param("buildingId") Long buildingId,
			@Param("unitNumber") Integer unitNumber);

	public List<HouseInfo> queryHousesByBuidlingIdUnitNumber(@Param("buildingId") Long buildingId,
			@Param("unitNumber") Integer unitNumber);

	public List<HouseInfo> queryBaseHousesByBuidlingIdUnitNumber(@Param("buildingId") Long buildingId,
			@Param("unitNumber") Integer unitNumber);

	public List<HouseInfo> queryHouseInfos(@Param("buildings") List<BuildingInfo> buildings,
			@Param("unitNumber") Integer unitNumber, @Param("floorNumber") Integer floorNumber,
			@Param("houseNumber") Integer houseNumber);

	public List<HouseInfo> queryBaseHouseInfos(@Param("buildings") List<BuildingInfo> buildings,
			@Param("unitNumber") Integer unitNumber, @Param("floorNumber") Integer floorNumber,
			@Param("houseNumber") Integer houseNumber);

	public List<HouseInfo> queryHouseInfoByResidentNum(@Param("residentNum") Integer residentNum);

	public List<HouseInfo> queryBaseHouseByBuildingIdAndType(@Param("buildingId") Long buildingId,
			@Param("houseType") String houseType);

	public List<HouseInfo> queryBaseHouses(@Param("buildingId") Long buildingId,
			@Param("unitNumber") Integer unitNumber, @Param("floorNumber") Integer floorNumber,
			@Param("houseNumber") Integer houseNumber);

}
