/*
 * 文 件 名:  BuildingDao.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月19日
 * 修改内容:  
 */
package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.platform.interfaces.biz.BuildingInfo;

/**
 * <楼栋数据持久层接口>
 * 
 * @author peidehao 0380008672
 * @version [版本号, 2017年10月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface BuildingDao {

	/**
	 * <新增楼栋信息>
	 * 
	 * @param building
	 * @see [类、类#方法、类#成员]
	 */
	public int addBuilding(BuildingInfo building);

	/**
	 * <更新楼栋信息>
	 * 
	 * @param building
	 * @see [类、类#方法、类#成员]
	 */
	public void updateBuilding(BuildingInfo building);

	/**
	 * <根据ID删除楼栋信息>
	 * 
	 * @param id
	 * @see [类、类#方法、类#成员]
	 */
	public void deleteBuildingById(@Param("buildingId") Long buildingId);

	/**
	 * <批量删除楼栋信息>
	 * 
	 * @param ids
	 * @see [类、类#方法、类#成员]
	 */
	public void batchDleteByBuidlingIds(@Param("buildingIds") List<Long> buildingIds);

	/**
	 * <查询多个网格的楼栋> <包含关联楼栋的房屋信息>
	 * 
	 * @param buildingId
	 * @param gridIds
	 *            多网格ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public BuildingInfo queryBuildingById(@Param("buildingId") Long buildingId,
			@Param("gridIds") List<Integer> gridIds);

	/**
	 * <根据ID查询楼栋信息>
	 * 
	 * @param buildingId
	 * @param gridIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public BuildingInfo queryBaseBuildingById(@Param("buildingId") Long buildingId,
			@Param("gridIds") List<Integer> gridIds);

	/**
	 * <查询多个网格的楼栋> <包含关联楼栋的房屋信息>
	 * 
	 * @param buildingIds
	 *            多栋楼
	 * @param gridIds
	 *            多网格ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBuildingByIds(@Param("buildingIds") List<Long> buildingIds,
			@Param("gridIds") List<Integer> gridIds);

	/**
	 * <根据ID查询楼栋信息>
	 * 
	 * @param buildingIds
	 *            多栋楼
	 * @param gridIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBaseBuildingByIds(@Param("buildingIds") List<Long> buildingIds,
			@Param("gridIds") List<Integer> gridIds);

	/**
	 * <查询多个网格的楼栋> <包含关联楼栋的房屋信息>
	 * 
	 * @param buildingName:street+"/"+community+"/"+buildingName
	 * @param gridIds
	 *            多网格ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBuildingByBuildingFullName(@Param("buildingName") String buildingName,
			@Param("gridIds") List<Integer> gridIds);

	/**
	 * <根据楼栋名称查询楼栋信息>
	 * 
	 * @param buildingName:street+"/"+community+"/"+buildingName
	 * @param gridIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBaseBuildingByBuildingFullName(@Param("buildingName") String buildingName,
			@Param("gridIds") List<Integer> gridIds);

	/**
	 * <查询多个网格的楼栋> <包含关联楼栋的房屋信息>
	 * 
	 * @param street
	 *            街道
	 * @param community
	 *            社区
	 * @param gridIds
	 *            多网格ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBuildingsByAddress(@Param("street") String street,
			@Param("community") String community, @Param("gridIds") List<Integer> gridIds);

	/**
	 * <条件查询获取楼栋信息>
	 * 
	 * @param street
	 * @param community
	 * @param gridIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBaseBuildingsByAddress(@Param("street") String street,
			@Param("community") String community, @Param("gridIds") List<Integer> gridIds,
			@Param("buildingIds") List<Long> buildingIds);

	/**
	 * <查询多个网格的楼栋> <包含关联楼栋的房屋信息>
	 * 
	 * @param street
	 *            街道
	 * @param community
	 *            社区
	 * @param buildingName
	 *            楼栋名称
	 * @param gridIds
	 *            多网格ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBuildingsByNameAndAddress(@Param("street") String street,
			@Param("community") String community, @Param("buildingName") String buildingName,
			@Param("gridIds") List<Integer> gridIds, @Param("buildingIds") List<Long> buildingIds);

	/**
	 * <条件查询获取楼栋信息>
	 * 
	 * @param street
	 *            街道
	 * @param community
	 *            社区
	 * @param buildingName
	 *            楼栋名称
	 * @param gridIds
	 *            多网格ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBaseBuildingsByNameAndAddress(@Param("street") String street,
			@Param("community") String community, @Param("buildingName") String buildingName,
			@Param("gridIds") List<Integer> gridIds);

	/**
	 * <查询多个网格的楼栋> <包含关联楼栋的房屋信息>
	 * 
	 * @param gridId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBuildingsByGridId(@Param("gridId") Integer gridId);

	/**
	 * <根据网格ID查询所有楼栋信息>
	 * 
	 * @param gridId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBaseBuildingsByGridId(@Param("gridId") Integer gridId);

	/**
	 * 
	 * <查询多个网格的楼栋> <包含关联楼栋的房屋信息>
	 * 
	 * @param gridIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBuildingsByGridIds(@Param("gridIds") List<Integer> gridIds);

	/**
	 * 
	 * <查询多个网格的楼栋>
	 * 
	 * @param gridIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBaseBuildingsByGridIds(@Param("gridIds") List<Integer> gridIds);

}
