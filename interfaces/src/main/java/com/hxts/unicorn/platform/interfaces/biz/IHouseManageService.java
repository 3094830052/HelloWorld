/*
 * 文 件 名:  IHouseManager.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月26日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.biz;

import java.util.List;
import java.util.Map;

import com.hxts.unicorn.platform.interfaces.IBizModule;
import com.hxts.unicorn.platform.interfaces.GIS.BuildingStat;

/**
 * 房屋管理 <功能详细描述>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年9月26日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IHouseManageService extends IBizModule {

	public static final String APP_ID = "RESIDENT_BUILDING";

	public static final String OPER_MODULE = "房屋管理";

	public static final String OPER_TARGET_BUILDING = "楼栋";

	public static final String OPER_TARGET_HOUSE = "用户管理";

	public static final String OPER_TARGET_ROH = "人房关联";

	public static final String OPER_TARGET_HROH = "历史人房关联";

	/**
	 * <添加楼栋信息>
	 * 
	 * @param building
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public int addBuilding(BuildingInfo building);

	/**
	 * 
	 * <删除楼栋信息>
	 * 
	 * @param buildingId
	 *            楼栋ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean delBuilding(long buildingId);

	/**
	 * <修改楼栋信息>
	 * 
	 * @param building
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean modifyBuilding(BuildingInfo building);

	/**
	 * <根据楼栋ID查询楼栋信息><包含房屋的信息>
	 * 
	 * @param buildingId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public BuildingInfo getBuildingById(long buildingId);

	/**
	 * 
	 * <根据ID查询楼栋信息>
	 * 
	 * @param buildingId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public BuildingInfo getBaseBuildingById(long buildingId);

	/**
	 * 
	 * <根据网格ID获取网格内楼栋信息>
	 * 
	 * @param street
	 * @param community
	 * @param gridId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> getBaseBuildings(String street, String community, Integer gridId, Long buildingId,
			Integer pageNum, Integer pageSize);

	/**
	 * <根据街道，社区(地址信息)，楼栋名称查询楼栋信息>
	 * 
	 * @param street
	 * @param community
	 * @param buildingName
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> getBaseBuildings(String street, String community, String buildingName);

	/**
	 * 
	 * <查询多个网格的楼栋><包含楼栋信息>
	 * 
	 * @param gridIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBuildingByGridIds(List<Integer> gridIds);

	/**
	 * 
	 * <查询多个网格的楼栋基本信息>
	 * 
	 * @param gridIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBaseBuildingByGridIds(List<Integer> gridIds);

	/**
	 * <统计楼栋基础信息> <按房屋用途统计楼栋房屋信息> <统计楼栋人口标签>
	 * 
	 * @param buildingIds，以","区隔
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, Object> subBuildingStat(String buildingIds);

	/**
	 * <统计范围：当前用户> <按房屋类别统计数量百分比><按楼栋完整度百分比区间统计数量>
	 * 
	 * @param buildingIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, Object> homePageBuildingStat();

	/**
	 * 
	 * <批量向某栋楼添加房屋>
	 * 
	 * @param buildingId
	 * @param houses
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean addHouses(long buildingId, List<HouseInfo> houses);

	/**
	 * <根据建模信息添加房屋>
	 * 
	 * @param buildingId
	 * @param ums
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean addHousesByModel(long buildingId, List<UnitModel> ums);

	/**
	 * <更新房屋信息>
	 * 
	 * @param house
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean modifyHouse(HouseInfo house);

	/**
	 * <根据建模数据修改房屋信息>
	 * 
	 * @param houseModelInfo：建模数据
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean modifyHouseByModel(List<HouseModelInfo> houseModelInfo);

	/**
	 * <获取房屋信息>
	 * 
	 * @param houseId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public HouseInfo getHouseById(long houseId);

	/**
	 * <获取某个楼栋的所有房屋信息>
	 * 
	 * @param buildingId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<HouseInfo> getHousesByBuildingId(Long buildingId);

	/**
	 * 根据楼栋查询所有房屋 <一句话功能简述> <功能详细描述>
	 * 
	 * @param buildings
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<HouseInfo> getHousesByBuildings(List<BuildingInfo> buildings);

	/**
	 * <获取房屋建模数据>
	 * 
	 * @param buildingId
	 * @param unitNumber
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<HouseModelInfo> getHouseModels(long buildingId, int unitNumber);

	/**
	 * 
	 * <房屋关联人口>
	 * 
	 * @param houseId
	 *            房屋ID
	 * @param residentId
	 *            人口ID
	 * @param residentType
	 *            与房关系
	 * @param rentType
	 *            租住类型：只有在与房关系是“租住”时才有
	 * @param hiddenDanger
	 *            隐患登记：只有在与房关系是“租住”时才有
	 * @return 返回关联人口数据ID
	 * @see [类、类#方法、类#成员]
	 */
	public int associate(long houseId, long residentId, String residentType, String rentType, String hiddenDanger);

	/**
	 * <取消关联>
	 * 
	 * @param residentOfHouseId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean disassociate(long residentOfHouseId);

	/**
	 * <获取某个房屋现住人口>
	 * 
	 * @param houseId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<ResidentOfHouseInfo> getResidentOfHouse(long houseId);

	/**
	 * <获取某个房屋历史入住人口>
	 * 
	 * @param houseId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<HisResidentOfHouseInfo> getHisResidentOfHouse(long houseId);

	/**
	 * 
	 * <获取人关联的所有房屋的信息>
	 * 
	 * @param residentIds
	 *            人的ID集合，可以是1个或n个...
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Map<Integer, List<ResidentOfHouseInfo>> getResidentOfHouseInfos(List<Integer> residentIds);

	/**
	 * 
	 * <根据网格和与房关系获取人口ID>
	 * 
	 * @param gridIds
	 *            多个 网格ID
	 * @param redidentType
	 *            与房关系
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<Integer> getResidentIdsOfGrid(List<Integer> gridIds, String redidentType);

	/**
	 * <统计网格内的出租房数量>
	 * 
	 * @param gridIds
	 *            网格ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Map<Integer, Integer> statRentHouseNum(List<Integer> gridIds);

	/**
	 * 
	 * <根据条件获取楼栋统计数据集合>
	 * 
	 * @param buildingName:模糊匹配
	 *            street community buildingName
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingStat> getBuildingStats(String buildingName);

	/**
	 * 
	 * <根据楼栋ID获取楼栋统计数据>
	 * 
	 * @param buildingId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public BuildingStat getBuildingStat(Long buildingId);

	/**
	 * 
	 * <获取楼栋某个单元的所有住户ID>
	 * 
	 * @param buildingId
	 * @param unitNumber：单元号
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<Integer> getResidentIdsOfBuildingUnit(Long buildingId, Integer unitNumber);

	/**
	 * 
	 * <多条件查询楼栋的房屋信息>
	 * 
	 * @param street
	 *            街道
	 * @param community
	 *            社区
	 * @param buildingName
	 *            楼栋名称
	 * @param unitNumber
	 *            单元号
	 * @param floorNumber
	 *            楼层
	 * @param houseNumber
	 *            房号
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<HouseInfo> getHouseInfos(List<BuildingInfo> buildingInfos, Integer unitNumber, Integer floorNumber,
			Integer houseNumber);

	/**
	 * 
	 * <统计一栋楼的每个房屋：是否关联人口，房屋用途，房屋中有特殊标签的人>
	 * 
	 * @param buildingId
	 * @return <unitNumber,<floorNumber,<houseNumber,data>>
	 * @see [类、类#方法、类#成员]
	 */
	public Map<Integer, Map<Integer, Map<Integer, Object>>> getBuildingTile(Long buildingId);

	/**
	 * 
	 * <楼栋名称模糊匹配查询楼栋信息>
	 * 
	 * @param buildingName
	 *            street（楼栋所属街道）+community（楼栋所属社区）+buildingName（楼栋名称）
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> getBuildingByFullName(String buildingName);

	/**
	 * 
	 * <根据人的身份证号查询人关联的房屋信息>
	 * 
	 * @param idNo
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<HouseInfo> getHouseInfosByResidentIdNo(String idNo);

	/**
	 * 
	 * <根据房屋人数查询房屋信息>
	 * 
	 * @param residentNum：人数大于等于输入的参数
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<HouseInfo> getHouseInfosByResidentNum(Integer residentNum);

	/**
	 * 
	 * <查询某房屋内特定标签的人>
	 * 
	 * @param houseId
	 *            房屋ID
	 * @param lable:帮扶，重点
	 * @return 房号以及此房间对应标签的人的集合<房号，此标签人的集合>
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, List<ResidentBaseInfo>> getResidentInfoOfHouseByLable(Long houseId, String lable);

	/**
	 * 
	 * <查询楼栋中各个房屋有特定标签的人的信息>
	 * 
	 * @param buildingId
	 *            楼栋ID
	 * @param lable:帮扶，重点
	 * @return <房号，此标签人的集合>
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, List<ResidentBaseInfo>> getResidentInfoOfHouseOfBuildingByLable(Long buildingId, String lable);

	/**
	 * 
	 * <查询网格中特定标签的人的数量>
	 * 
	 * @param gridId
	 * @param lable:帮扶，重点
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Integer getResidentInfoOfGridByLable(Integer gridId, String lable);

	/**
	 * 
	 * <根据楼栋Id和房屋类别查询房屋信息>
	 * 
	 * @param buildingAddress：街道，社区，楼栋名
	 * @param houseType
	 * @return type List<HouseAddress>
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> getHousesByTypes(Long buildingId, List<String> houseTypes);

	/**
	 * 
	 * <统计网格出租房数量>
	 * 
	 * @param gridIds：网格ID集合
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, Integer> getRentHouseNumOfGrids(List<Integer> gridIds);

	/**
	 * 
	 * <统计楼栋出租房数量>
	 * 
	 * @param buildingInfos：楼栋信息集合：楼栋信息集合，楼栋信息包含房屋信息
	 * @return 返回<楼栋ID, 此楼栋出租房数量>
	 * @see [类、类#方法、类#成员]
	 */
	public Map<Integer, Integer> getRentHouseNumOfBuildings(List<BuildingInfo> buildingInfos);

	/**
	 * 
	 * <统计当前用户（网格员）各个楼栋标签的人的数量>
	 * 
	 * @param label
	 *            需要查询的标签
	 * @return <楼栋ID，此楼栋下改标签的人的数量>
	 * @see [类、类#方法、类#成员]
	 */
	public Map<Integer, Integer> getLabelResidentNum(String label);

	/**
	 * 
	 * <获取楼栋基础信息，分级显示>
	 * 
	 * @param street
	 *            街道
	 * @param community
	 *            社区
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> getBaseSelectBuildings(String street, String community, Integer gridId);

	/**
	 * 
	 * <条件获取房屋信息>
	 * 
	 * @param buildingId
	 *            楼栋ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, Map<String, Map<String, HouseInfo>>> getHouses(Long buildingId);

	/**
	 * 
	 * <获取网格内房屋总数及已关联人口房屋数量>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Map<Integer, Map<String, Integer>> getHouseNumByGrids(List<Integer> gridIds);

	/**
	 * 
	 * <按房屋分类统计房屋数量，包括总数>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, Integer> getHouseNumByType();

}
