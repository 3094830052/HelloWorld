/*
 * 文 件 名:  HouseManageServiceImpl.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月30日
 * 修改内容:  
 */
package com.hxts.unicorn.modules.resident.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.hxts.unicorn.modules.resident.dao.BuildingDao;
import com.hxts.unicorn.modules.resident.dao.BuildingDataDictionaryItemDao;
import com.hxts.unicorn.modules.resident.dao.HisResidentOfHouseDao;
import com.hxts.unicorn.modules.resident.dao.HouseDao;
import com.hxts.unicorn.modules.resident.dao.ResidentOfHouseDao;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.AuthorityItem;
import com.hxts.unicorn.platform.interfaces.DataDictionaryItem;
import com.hxts.unicorn.platform.interfaces.IModuleBroker;
import com.hxts.unicorn.platform.interfaces.IUnicornFrame;
import com.hxts.unicorn.platform.interfaces.ModuleProperty;
import com.hxts.unicorn.platform.interfaces.RequestAuthority;
import com.hxts.unicorn.platform.interfaces.GIS.BuildingStat;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.basic.IAuthorityService;
import com.hxts.unicorn.platform.interfaces.biz.BuildingInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HisResidentOfHouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.HouseArea;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.HouseModelInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.ResidentBaseInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.UnitModel;

/**
 * <房屋相关业务逻辑层接口>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class HouseManageServiceImpl implements IHouseManageService {

	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private HouseDao houseDao;
	@Autowired
	private ResidentOfHouseDao residentOfHouseDao;
	@Autowired
	private HisResidentOfHouseDao hisResidentOfHouseDao;
	@Autowired
	private BuildingDataDictionaryItemDao buildingDataDictionaryItemDao;

	private IModuleBroker broker;

	private IUnicornFrame frame;

	private static final ModuleProperty property;

	static {
		property = new ModuleProperty();
		property.name = "房屋管理";
		property.author = "";
		property.copyright = "";
		property.description = "";
		property.version = "";
		property.review = "";
	}

	/**
	 * 该模块依赖的其他模块，String参数是AppId，依赖主要为了处理初始化的顺序
	 * 
	 * @return
	 */
	@Override
	public List<String> getDepends() {
		List<String> depends = new ArrayList<String>();
		depends.add(IGridService.APP_ID);// 网格模块
		depends.add(IPersonManageService.APP_ID);// 人口模块
		return depends;
	}

	/**
	 * 可选的依赖关系即如果存在则依赖，否则忽略
	 * 
	 * @return
	 */
	@Override
	public List<String> getOptionalDepends() {
		return null;
	}

	@Override
	public String appId() {
		return IHouseManageService.APP_ID;
	}

	@Override
	public ModuleProperty getProperty() {
		return property;
	}

	@Override
	public void initialize(IUnicornFrame frame) {
		this.frame = frame;
	}

	@Override
	public IModuleBroker getModuleBroker() {
		if (broker == null) {
			broker = new IHouseManageServiceBroker();
		}
		return this.broker;
	}

	@Override
	public List<DataDictionaryItem> getDataDictionary(String dataType) {
		return buildingDataDictionaryItemDao.selectByDataType(dataType);
	}

	@Override
	public List<DataDictionaryItem> getAllDataDictionary() {
		return buildingDataDictionaryItemDao.selectAll();
	}

	@Override
	public void setDataDictionaryItem(DataDictionaryItem item) {
		buildingDataDictionaryItemDao.insert(item);
	}

	@Override
	public void deleteDataDictionary(String dataType, String dataCode) {
		buildingDataDictionaryItemDao.delete(dataType, dataCode);
	}

	/**
	 * <添加楼栋信息>
	 * 
	 * @param building
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public int addBuilding(BuildingInfo buildingInfo) {
		int result = 0;
		try {
			// 获取当前用户下辖的网格ID
			List<Integer> gridIds = new ArrayList<Integer>();
			IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
			List<BuildingInfo> exist = buildingDao.queryBaseBuildingByBuildingFullName(buildingInfo.getBuildingName(),
					gridIds);
			if (exist.size() > 0) {// 楼栋名称不可重复(街道，社区，输入的名称（1栋、2栋...）)
				throw new AppModuleErrorException("楼栋名称重复");
			}
			result = buildingDao.addBuilding(buildingInfo);
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * <删除楼栋信息>
	 * 
	 * @param buildingId
	 *            楼栋ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public boolean delBuilding(long buildingId) {
		boolean result = false;
		try {
			// 获取当前用户下辖的网格ID
			List<Integer> gridIds = new ArrayList<Integer>();
			IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
			BuildingInfo building = buildingDao.queryBaseBuildingById(buildingId, gridIds);
			if (building == null) {
				throw new AppModuleErrorException("系统不存在此楼栋信息");
			}
			buildingDao.deleteBuildingById(buildingId);
			result = true;
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return result;
	}

	/**
	 * <修改楼栋信息>
	 * 
	 * @param building
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public boolean modifyBuilding(BuildingInfo buildingInfo) {
		boolean result = false;
		try {
			if (buildingInfo == null) {
				throw new AppModuleErrorException("请输入需修改的楼栋信息");
			}
			// 获取当前用户下辖的网格ID
			List<Integer> gridIds = new ArrayList<Integer>();
			IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
			BuildingInfo exist = buildingDao.queryBaseBuildingById(buildingInfo.getBuildingId(), gridIds);
			if (exist == null) {
				throw new AppModuleErrorException("系统不存在该楼栋信息");
			}
			buildingDao.updateBuilding(buildingInfo);
			result = true;
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return result;
	}

	/**
	 * <根据楼栋ID查询楼栋信息> <包含房屋信息>
	 * 
	 * @param buildingId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public BuildingInfo getBuildingById(long buildingId) {
		BuildingInfo buildingInfo = null;
		try {
			// 获取当前用户下辖的网格ID
			List<Integer> gridIds = new ArrayList<Integer>();
			IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
			buildingInfo = buildingDao.queryBuildingById(buildingId, gridIds);
			if (buildingInfo == null) {
				throw new AppModuleErrorException("系统不存在该楼栋信息");
			}
			Map<String, Object> calculateData = calculateIntegrity(buildingInfo);
			buildingInfo.setIntegrity(new BigDecimal(calculateData.get("integrity").toString()));// 完整度
			buildingInfo.setIntegrityNum(Integer.valueOf(calculateData.get("integrityNum").toString()));// 已关联人口房屋数
			buildingInfo.setPersonNum(Integer.valueOf(calculateData.get("personNum").toString()));// 关联的此楼栋房屋的人口的数量
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return buildingInfo;
	}

	/**
	 * 
	 * <根据ID查询楼栋信息>
	 * 
	 * @param buildingId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public BuildingInfo getBaseBuildingById(long buildingId) {
		BuildingInfo buildingInfo = null;
		try {
			// 获取当前用户下辖的网格ID
			List<Integer> gridIds = new ArrayList<Integer>();
			IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
			buildingInfo = buildingDao.queryBaseBuildingById(buildingId, gridIds);
			if (buildingInfo == null) {
				throw new AppModuleErrorException("系统不存在该楼栋信息");
			}
			Map<String, Object> calculateData = calculateIntegrity(buildingInfo);
			buildingInfo.setIntegrity(new BigDecimal(calculateData.get("integrity").toString()));// 完整度
			buildingInfo.setIntegrityNum(Integer.valueOf(calculateData.get("integrityNum").toString()));// 已关联人口房屋数
			buildingInfo.setPersonNum(Integer.valueOf(calculateData.get("personNum").toString()));// 关联的此楼栋房屋的人口的数量
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return buildingInfo;
	}

	/**
	 * 
	 * <根据网格ID获取网格内楼栋信息>
	 * 
	 * @param street
	 *            默认是全部,""
	 * @param community
	 *            默认全部,""
	 * @param gridId
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<BuildingInfo> getBaseBuildings(String street, String community, Integer gridId, Long buildingId,
			Integer pageNum, Integer pageSize) {
		List<BuildingInfo> buildingInfos = new ArrayList<BuildingInfo>();
		try {
			// 若无gridIds参数，根据当前用户获取用户下辖的网格
			List<GridItem> gridItems = new ArrayList<GridItem>();
			List<Integer> gridIds = new ArrayList<Integer>();
			if (gridId == null) {
				IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
				gridItems = gridService.getCurrentUserGrid();
				for (GridItem gridItem : gridItems) {
					gridIds.add(gridItem.getGridId());
				}
			} else {
				gridIds.add(gridId);
			}
			List<Long> buildingIds = new ArrayList<Long>();
			if (buildingId != null) {
				buildingIds.add(buildingId);
			}
			if (pageSize != null && pageNum != null) {
				PageHelper.startPage(pageNum, pageSize);
			}
			buildingInfos = buildingDao.queryBaseBuildingsByAddress(street, community, gridIds, buildingIds);
			for (BuildingInfo buildingInfo : buildingInfos) {
				Map<String, Object> calculateData = calculateIntegrity(buildingInfo);
				buildingInfo.setIntegrity(new BigDecimal(calculateData.get("integrity").toString()));// 完整度
				buildingInfo.setIntegrityNum(Integer.valueOf(calculateData.get("integrityNum").toString()));// 已关联人口房屋数
				buildingInfo.setPersonNum(Integer.valueOf(calculateData.get("personNum").toString()));// 关联的此楼栋房屋的人口的数量
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return buildingInfos;
	}

	/**
	 * <根据街道，社区，楼栋名称查询楼栋信息>
	 * 
	 * @param street
	 *            街道
	 * @param community
	 *            社区
	 * @param buildingName
	 *            楼栋名
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<BuildingInfo> getBaseBuildings(String street, String community, String buildingName) {
		List<BuildingInfo> buildingInfos = new ArrayList<BuildingInfo>();
		try {
			// 获取当前用户下辖的网格ID
			List<Integer> gridIds = new ArrayList<Integer>();
			IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
			buildingInfos = buildingDao.queryBaseBuildingsByNameAndAddress(street, community, buildingName, gridIds);
			for (BuildingInfo buildingInfo : buildingInfos) {
				Map<String, Object> calculateData = calculateIntegrity(buildingInfo);
				buildingInfo.setIntegrity(new BigDecimal(calculateData.get("integrity").toString()));// 完整度
				buildingInfo.setIntegrityNum(Integer.valueOf(calculateData.get("integrityNum").toString()));// 已关联人口房屋数
				buildingInfo.setPersonNum(Integer.valueOf(calculateData.get("personNum").toString()));// 关联的此楼栋房屋的人口的数量
			}
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return buildingInfos;
	}

	/**
	 * 
	 * <查询多个网格的楼栋基本信息>
	 * 
	 * @param gridIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBaseBuildingByGridIds(List<Integer> gridIds) {
		List<BuildingInfo> buildingInfos = new ArrayList<BuildingInfo>();
		try {
			buildingInfos = buildingDao.queryBaseBuildingsByGridIds(gridIds);
			for (BuildingInfo buildingInfo : buildingInfos) {
				Map<String, Object> calculateData = calculateIntegrity(buildingInfo);
				buildingInfo.setIntegrity(new BigDecimal(calculateData.get("integrity").toString()));// 完整度
				buildingInfo.setIntegrityNum(Integer.valueOf(calculateData.get("integrityNum").toString()));// 已关联人口房屋数
				buildingInfo.setPersonNum(Integer.valueOf(calculateData.get("personNum").toString()));// 关联的此楼栋房屋的人口的数量
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return buildingInfos;
	}

	/**
	 * 
	 * <查询多个网格的楼栋><包含楼栋的房屋信息>
	 * 
	 * @param gridIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<BuildingInfo> queryBuildingByGridIds(List<Integer> gridIds) {
		List<BuildingInfo> buildingInfos = new ArrayList<BuildingInfo>();
		try {
			buildingInfos = buildingDao.queryBuildingsByGridIds(gridIds);
			for (BuildingInfo buildingInfo : buildingInfos) {
				Map<String, Object> calculateData = calculateIntegrity(buildingInfo);
				buildingInfo.setIntegrity(new BigDecimal(calculateData.get("integrity").toString()));// 完整度
				buildingInfo.setIntegrityNum(Integer.valueOf(calculateData.get("integrityNum").toString()));// 已关联人口房屋数
				buildingInfo.setPersonNum(Integer.valueOf(calculateData.get("personNum").toString()));// 关联的此楼栋房屋的人口的数量
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return buildingInfos;
	}

	/**
	 * <统计楼栋基础信息> <按房屋用途统计楼栋房屋信息> <统计楼栋人口标签>
	 * 
	 * @param buildingIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public Map<String, Object> subBuildingStat(String buildingIds) {
		Map<String, Object> statsMap = new HashMap<String, Object>();
		String[] buildingIdsArr = buildingIds.split(",");
		List<Long> buildings = new ArrayList<Long>();
		for (String buildingid : buildingIdsArr) {
			buildings.add(Long.valueOf(buildingid));
		}
		// 楼栋基本信息统计
		Map<String, Object> baseInfo = new LinkedHashMap<String, Object>();
		int unitNums = 0;// 单元数
		int houseNums = 0;// 户数
		double buildingAreas = 0.00;// 占地面积
		// 根据楼栋ids获取所有的楼栋信息（包含房屋信息）
		List<BuildingInfo> buildingInfoList = buildingDao.queryBuildingByIds(buildings, null);
		// 统计基础信息，以及所有房屋信息
		List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
		for (BuildingInfo buildingInfo : buildingInfoList) {
			unitNums += buildingInfo.getUnitNum();
			houseNums += buildingInfo.getHouseholdNum();
			buildingAreas += buildingInfo.getBuildingArea();
			houseInfoList.addAll(buildingInfo.getHouseInfos());
		}
		baseInfo.put("unitNums", unitNums);
		baseInfo.put("houseNums", houseNums);
		baseInfo.put("buildingAreas", buildingAreas);
		statsMap.put("baseInfo", baseInfo);
		// 按房屋用途 统计房屋信息集合，key:房屋用途，value:此用途房屋数量
		Map<String, Integer> statHouseInfo = new LinkedHashMap<String, Integer>();
		List<DataDictionaryItem> houseTypes = buildingDataDictionaryItemDao.selectByDataType("房屋用途");
		for (DataDictionaryItem houseType : houseTypes) {
			List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
			for (HouseInfo houseInfo : houseInfoList) {
				if ((houseInfo.getHouseType() != null || !houseInfo.getHouseType().isEmpty())// 房屋如果只是初始化，type为空
						&& houseInfo.getHouseType().equals(houseType.getDataCode())) {
					houseInfos.add(houseInfo);
				}
			}
			statHouseInfo.put(houseType.getDataName(), houseInfos.size());
		}
		statsMap.put("statHouseInfo", statHouseInfo);
		// 按特殊标签统计人口，key:标签，value:此标签人口数量
		List<ResidentOfHouseInfo> allRohs = new ArrayList<ResidentOfHouseInfo>();
		// 通过房屋集合查询现住人口集合
		allRohs = residentOfHouseDao.queryBaseRohByHouseIds(houseInfoList);
		List<Integer> residentBaseIds = new ArrayList<Integer>();// 楼栋中的所有人口ID
		for (ResidentOfHouseInfo roh : allRohs) {
			residentBaseIds.add(Integer.valueOf(String.valueOf(roh.getResidentBaseId())));
		}
		// 人按特殊标签统计
		Map<String, Integer> statSpecialPerson = new HashMap<String, Integer>();
		if (!residentBaseIds.isEmpty()) {
			IPersonManageService personManageService = (IPersonManageService) frame.getModuleManager()
					.getModule(IPersonManageService.APP_ID);
			List<String> labels = new ArrayList<String>();
			labels.add(IPersonManageService.LABEL_TEENAGER);// 重点青少年
			labels.add(IPersonManageService.LABEL_DRUG);// 吸毒
			labels.add(IPersonManageService.LABEL_EMANCIPIST);// 刑满释放
			labels.add(IPersonManageService.LABEL_PERTITION);// 非法上访
			labels.add(IPersonManageService.LABEL_SUB_ALLOW);// 低保
			labels.add(IPersonManageService.LABEL_LIVE_ALONE);// 独居老人
			labels.add(IPersonManageService.LABEL_DISABLED);// 残障
			labels.add(IPersonManageService.LABEL_RECTIFY);// 社会矫正
			labels.add(IPersonManageService.LABEL_AIDS);// 艾滋病
			labels.add(IPersonManageService.LABEL_ALLOEOSIS);// 精神障碍

			statSpecialPerson = personManageService.getResidentLabelsByIds(residentBaseIds, labels);
		}
		statsMap.put("statSpecialPerson", statSpecialPerson);
		return statsMap;
	}

	/**
	 * <统计范围当前用户> <按房屋类别统计数量百分比><按楼栋完整度百分比区间统计数量>
	 * 
	 * @param buildingIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public Map<String, Object> homePageBuildingStat() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Integer> gridIds = new ArrayList<Integer>();
		IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
		List<GridItem> gridItems = gridService.getCurrentUserGrid();
		for (GridItem gridItem : gridItems) {
			gridIds.add(gridItem.getGridId());
		}
		List<BuildingInfo> buildings = buildingDao.queryBuildingsByGridIds(gridIds);
		// 定义楼栋数据完整度百分比统计区间
		double region1 = 0.00;
		double region2 = 10.00;
		double region3 = 25.00;
		double region4 = 50.00;
		double region5 = 75.00;
		double region6 = 100.00;
		// 楼栋完整度百分比区间数量百分比
		Map<String, Integer> statIntegrity = new LinkedHashMap<String, Integer>();
		List<BuildingInfo> zeroToTen = new ArrayList<BuildingInfo>();// 0~10
		List<BuildingInfo> tenToTwentyFive = new ArrayList<BuildingInfo>();// 10~25
		List<BuildingInfo> twentyFiveToFifty = new ArrayList<BuildingInfo>();// 25~50
		List<BuildingInfo> fiftyToSeventyFive = new ArrayList<BuildingInfo>();// 50~75
		List<BuildingInfo> seventyFiveToOneHundred = new ArrayList<BuildingInfo>();// 75~100
		// 统计基础信息，以及所有房屋信息
		List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
		for (BuildingInfo buildingInfo : buildings) {
			houseInfoList.addAll(buildingInfo.getHouseInfos());
			Map<String, Object> calculateData = calculateIntegrity(buildingInfo);
			buildingInfo.setIntegrity(new BigDecimal(calculateData.get("integrity").toString()));// 完整度
			buildingInfo.setIntegrityNum(Integer.valueOf(calculateData.get("integrityNum").toString()));// 已关联人口房屋数
			buildingInfo.setPersonNum(Integer.valueOf(calculateData.get("personNum").toString()));// 关联的此楼栋房屋的人口的数量
			// 楼栋完整度分区间
			double integrity = Double.valueOf(buildingInfo.getIntegrity().toString());
			if (integrity >= region1 && integrity < region2) {
				zeroToTen.add(buildingInfo);
			} else if (integrity >= region2 && integrity < region3) {
				tenToTwentyFive.add(buildingInfo);
			} else if (integrity >= region3 && integrity < region4) {
				twentyFiveToFifty.add(buildingInfo);
			} else if (integrity >= region4 && integrity < region5) {
				fiftyToSeventyFive.add(buildingInfo);
			} else if (integrity >= region5 && integrity <= region6) {
				seventyFiveToOneHundred.add(buildingInfo);
			}
		}
		statIntegrity.put("0-10%", zeroToTen.size());
		statIntegrity.put("10-25%", tenToTwentyFive.size());
		statIntegrity.put("25-50%", twentyFiveToFifty.size());
		statIntegrity.put("50-75%", fiftyToSeventyFive.size());
		statIntegrity.put("75-100%", seventyFiveToOneHundred.size());
		result.put("statIntegrity", statIntegrity);
		// 按房屋类型统计数量百分比
		Map<String, Integer> statHouseNumByType = new LinkedHashMap<String, Integer>();
		List<DataDictionaryItem> houseTypes = buildingDataDictionaryItemDao.selectByDataType("房屋用途");
		for (DataDictionaryItem houseType : houseTypes) {
			List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
			for (HouseInfo houseInfo : houseInfoList) {
				if (houseInfo.getHouseType().equals(houseType.getDataCode())) {
					houseInfos.add(houseInfo);
				}
			}
			statHouseNumByType.put(houseType.getDataName(), houseInfos.size());
		}
		result.put("statHouseNumByType", statHouseNumByType);
		return result;
	}

	/**
	 * 
	 * <批量向某栋楼添加房屋>
	 * 
	 * @param buildingId
	 * @param houses
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public boolean addHouses(long buildingId, List<HouseInfo> houseInfos) {
		boolean result = false;
		try {
			if (houseInfos.size() == 0) {
				throw new AppModuleErrorException("请输入需要房屋信息");
			}
			// 获取当前用户下辖的网格ID
			List<Integer> gridIds = new ArrayList<Integer>();
			IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
			BuildingInfo buildingInfo = buildingDao.queryBuildingById(buildingId, gridIds);
			if (buildingInfo == null) {
				throw new AppModuleErrorException("请输入楼栋ID");
			}
			houseDao.batchAddHouse(houseInfos);
			result = true;
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return result;
	}

	/**
	 * <根据建模信息添加房屋>
	 * 
	 * @param buildingId
	 *            楼栋ID
	 * @param ums
	 *            楼栋每个单元的房屋建模数据
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public boolean addHousesByModel(long buildingId, List<UnitModel> ums) {
		boolean result = false;
		List<HouseInfo> houses = new ArrayList<HouseInfo>();
		try {
			if (ums == null || ums.size() == 0) {
				throw new AppModuleErrorException("请输入此楼栋相关的房屋建模信息");
			}
			for (int u = 0; u < ums.size(); u++) {
				UnitModel um = ums.get(u);
				for (HouseModelInfo hm : um.getHms()) {
					// 获取所有的楼层
					List<Integer> floorNumbers = new ArrayList<Integer>();
					for (int j = hm.getStartFloorNumber(); j <= hm.getEndFloorNumber(); j++) {
						floorNumbers.add(j);
					}
					for (Integer floorNumber : floorNumbers) {
						// 获取所有的房号
						List<Integer> houseNumbers = new ArrayList<Integer>();
						for (int m = 1; m <= hm.getHouseholdNum(); m++) {
							houseNumbers.add(m);
						}
						for (Integer houseNumber : houseNumbers) {
							// 根据建模信息生成的房屋信息
							HouseInfo house = new HouseInfo();
							house.setUnitNumber(hm.getUnitNumber());
							house.setFloorNumber(floorNumber);
							house.setHouseNumber(houseNumber);
							house.setInputTime(new Date());
							house.setBuildingId(buildingId);
							house.setHouseType("01");// 默认房屋用途 01：住房
							// 此房号的房间的面积
							List<HouseArea> areas = hm.getAreas();
							for (HouseArea ha : areas) {
								if (ha.getHouseNumber() == houseNumber) {
									house.setStructureArea(ha.getStructureArea());
									house.setUsableArea(ha.getUsableArea());
									house.setPoolArea(ha.getPoolArea());
									break;
								}
							}
							houses.add(house);
						}
					}
				}
			}
			for (HouseInfo house : houses) {
				houseDao.addHouse(house);
			}
			result = true;
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return result;
	}

	/**
	 * <更新房屋信息>
	 * 
	 * @param house
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public boolean modifyHouse(HouseInfo houseInfo) {
		boolean result = false;
		try {
			if (houseInfo == null) {
				throw new AppModuleErrorException("请输入需修改的房屋信息");
			}
			HouseInfo exist = houseDao.queryBaseHouseById(houseInfo.getHouseId());
			if (exist == null) {
				throw new AppModuleErrorException("系统不存在该房屋信息");
			}
			houseDao.modifyHouse(houseInfo);
			result = true;
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return result;
	}

	/**
	 * <根据建模数据修改房屋信息>
	 * 
	 * @param houseModelInfo：建模数据
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public boolean modifyHouseByModel(List<HouseModelInfo> houseModelInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * <获取房屋信息>
	 * 
	 * @param houseId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public HouseInfo getHouseById(long houseId) {
		HouseInfo houseInfo = null;
		try {
			houseInfo = houseDao.queryHouseById(houseId);
			if (houseInfo == null) {
				throw new AppModuleErrorException("系统不存在该房屋信息");
			}
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return houseInfo;
	}

	/**
	 * <获取某个楼栋的所有房屋信息>
	 * 
	 * @param buildingId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<HouseInfo> getHousesByBuildingId(Long buildingId) {
		List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
		try {
			houseInfos = houseDao.queryHousesByBuildingId(buildingId);
			for (HouseInfo house : houseInfos) {
				List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryRohByHouseId(house.getHouseId());
				boolean isAssociate = false;// 是否关联人口
				if (rohs.size() > 0) {
					isAssociate = true;
				}
				house.setAssociate(isAssociate);
			}
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return houseInfos;
	}

	/**
	 * 根据楼栋查询所有房屋 <一句话功能简述> <功能详细描述>
	 * 
	 * @param buildings
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<HouseInfo> getHousesByBuildings(List<BuildingInfo> buildings) {
		// TODO
		List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
		List<Long> buildingIds = new ArrayList<Long>();
		for (BuildingInfo b : buildings) {
			buildingIds.add(b.getBuildingId());
		}
		houseInfos = houseDao.queryHousesByBuildingIds(buildingIds);
		for (HouseInfo house : houseInfos) {
			List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryRohByHouseId(house.getHouseId());
			boolean isAssociate = false;// 是否关联人口
			String residents = "";
			if (rohs.size() > 0) {
				isAssociate = true;
				for (ResidentOfHouseInfo roh : rohs) {
					residents = residents + roh.getResidentBaseInfo().getName() + ",";
				}
			}
			house.setAssociate(isAssociate);
			house.setAssResidents(residents);
		}

		return houseInfos;
	}

	/**
	 * <获取房屋建模数据>
	 * 
	 * @param buildingId
	 * @param unitNumber
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<HouseModelInfo> getHouseModels(long buildingId, int unitNumber) {
		// 数据库查询，默认获取一个单元的建模信息，同一楼栋各单元建模信息相同
		List<HouseModelInfo> hms = houseDao.queryHouseModels(buildingId, unitNumber);// 建模数据，没有房屋面积数据
		// 一个单元所有房屋，默认第一个单元
		List<HouseInfo> houses = houseDao.queryBaseHousesByBuidlingIdUnitNumber(buildingId, unitNumber);
		// 补充查询得到的建模信息，主要是面积信息
		for (HouseModelInfo hm : hms) {
			List<String> floorNumberStrs = Arrays.asList(hm.getFloorScope().split(","));
			List<Integer> floorNumbers = new ArrayList<Integer>();
			for (String str : floorNumberStrs) {
				floorNumbers.add(Integer.valueOf(str));
			}
			List<String> houseNumberStrs = Arrays.asList(hm.getHouseholdScope().split(","));
			List<Integer> houseNumbers = new ArrayList<Integer>();
			for (String str : houseNumberStrs) {
				houseNumbers.add(Integer.valueOf(str));
			}
			Collections.sort(floorNumbers);
			Collections.sort(houseNumbers);
			hm.setStartFloorNumber(Integer.valueOf(floorNumbers.get(0)));
			hm.setEndFloorNumber(Integer.valueOf(floorNumbers.get(floorNumbers.size() - 1)));
			hm.setUnitNumber(unitNumber);
			// 设置建模数据中的房屋面积
			Map<Integer, Map<String, Double>> areaMap = new HashMap<Integer, Map<String, Double>>();
			for (HouseInfo house : houses) {
				if (floorNumbers.contains(house.getFloorNumber()) && houseNumbers.contains(house.getHouseNumber())) {
					int houseNumber = house.getHouseNumber();
					Map<String, Double> areas = new HashMap<String, Double>();
					areas.put("structureArea", house.getStructureArea());
					areas.put("usableArea", house.getUsableArea());
					areas.put("poolArea", house.getPoolArea());
					areaMap.put(houseNumber, areas);
				}
			}
			List<HouseArea> has = new ArrayList<HouseArea>();
			for (Map.Entry<Integer, Map<String, Double>> entry : areaMap.entrySet()) {
				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
				HouseArea ha = new HouseArea();
				ha.setHouseNumber(entry.getKey());
				ha.setStructureArea(entry.getValue().get("structureArea"));
				ha.setStructureArea(entry.getValue().get("usableArea"));
				ha.setPoolArea(entry.getValue().get("poolArea"));
				has.add(ha);
			}
			hm.setAreas(has);
		}
		return hms;
	}

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
	 * @return 新增人房关联数据ID
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public int associate(long houseId, long residentId, String residentType, String rentType, String hiddenDanger) {
		int result = 0;
		try {
			ResidentOfHouseInfo exist = residentOfHouseDao.queryRohByHouseIdAndResidentId(houseId, residentId);
			if (exist != null) {
				throw new AppModuleErrorException("该人已经与此房屋关联，请勿重复操作");
			}
			ResidentOfHouseInfo roh = new ResidentOfHouseInfo();
			roh.setHouseId(houseId);
			roh.setResidentBaseId(residentId);
			roh.setResidentType(residentType);
			roh.setRentType(rentType);
			roh.setHiddenDanger(hiddenDanger);
			roh.setAssociateTime(new Date());
			residentOfHouseDao.addResidentOfHouse(roh);
			result = (int) roh.getResidentOfHouseId();
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return result;
	}

	/**
	 * <取消关联>
	 * 
	 * @param residentOfHouseId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public boolean disassociate(long residentOfHouseId) {
		boolean result = false;
		try {
			ResidentOfHouseInfo roh = residentOfHouseDao.queryBaseRohById(residentOfHouseId);
			if (roh == null) {
				throw new AppModuleErrorException("系统不存在该现住人口记录");
			}
			HisResidentOfHouseInfo hroh = new HisResidentOfHouseInfo();
			hroh.setHouseId(roh.getHouseId());
			hroh.setRentType(roh.getRentType());
			hroh.setResidentBaseId(roh.getResidentBaseId());
			hroh.setResidentType(roh.getResidentType());
			hroh.setHiddenDanger(roh.getHiddenDanger());
			hroh.setMoveInTime(roh.getAssociateTime());
			hroh.setOutOfTime(new Date());
			hisResidentOfHouseDao.addHisResidentOfHouse(hroh);
			residentOfHouseDao.deleteRohById(residentOfHouseId);
			result = true;
			JSONObject json = new JSONObject();
			json.put("houseId", hroh.getHouseId());
			json.put("residentId", hroh.getHisResidentOfHouseId());
			json.put("residentType", hroh.getResidentType());
			IActionLogService logService = (IActionLogService) frame.getModuleManager()
					.getModule(IActionLogService.APP_ID);
			logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "删除", IHouseManageService.OPER_TARGET_ROH, 0l,
					json);
			logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "新建", IHouseManageService.OPER_TARGET_HROH, 0l,
					json);
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return result;
	}

	/**
	 * <获取某个房屋现住人口>
	 * 
	 * @param houseId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<ResidentOfHouseInfo> getResidentOfHouse(long houseId) {
		List<ResidentOfHouseInfo> result = new ArrayList<ResidentOfHouseInfo>();
		try {
			if (houseId == 0) {
				throw new AppModuleErrorException("请输入房屋ID");
			}
			result = residentOfHouseDao.queryRohByHouseId(houseId);// 包含人口基础信息
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return result;
	}

	/**
	 * <获取某个房屋历史入住人口>
	 * 
	 * @param houseId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<HisResidentOfHouseInfo> getHisResidentOfHouse(long houseId) {
		List<HisResidentOfHouseInfo> result = new ArrayList<HisResidentOfHouseInfo>();
		try {
			if (houseId == 0) {
				throw new AppModuleErrorException("请输入房屋ID");
			}
			result = hisResidentOfHouseDao.queryHrohByHouseId(houseId);// 包含人口基本信息
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * <人关联的房屋信息>
	 * 
	 * @param residentIds
	 *            人的ID集合，可以是1个或n个...
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public Map<Integer, List<ResidentOfHouseInfo>> getResidentOfHouseInfos(List<Integer> residentIds) {
		Map<Integer, List<ResidentOfHouseInfo>> result = new HashMap<Integer, List<ResidentOfHouseInfo>>();
		try {
			if (residentIds == null || residentIds.size() == 0) {
				throw new AppModuleErrorException("请输入人口");
			}
			for (Integer residentId : residentIds) {
				List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryRohByResidentId(Long.valueOf(residentId));
				result.put(residentId, rohs);
			}
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}

		return result;
	}

	/**
	 * 
	 * <根据网格和与房关系获取人口ID>
	 * 
	 * @param gridId
	 *            网格ID
	 * @param redidentType:不传此参数，返回所有的人员Id；传的话只返回此类"与房关系"的人员ID
	 *            与房关系
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<Integer> getResidentIdsOfGrid(List<Integer> gridIds, String redidentType) {
		List<Integer> residentIds = new ArrayList<Integer>();
		try {
			List<BuildingInfo> buildingInfos = buildingDao.queryBuildingsByGridIds(gridIds);
			if (buildingInfos == null || buildingInfos.isEmpty()) {
				throw new AppModuleErrorException("该网格无楼栋信息");
			}
			List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
			for (BuildingInfo buildingInfo : buildingInfos) {
				houseInfos.addAll(buildingInfo.getHouseInfos());
			}
			List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryBaseRohByHouseIds(houseInfos);
			for (ResidentOfHouseInfo roh : rohs) {
				if (redidentType == null || redidentType.isEmpty()) {
					residentIds.add(Integer.valueOf(String.valueOf(roh.getResidentBaseId())));
				} else {
					if (roh.getResidentType().equals(redidentType)) {
						residentIds.add(Integer.valueOf(String.valueOf(roh.getResidentBaseId())));
					}
				}
			}
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误" + e.getMessage());
		}
		return residentIds;
	}

	/**
	 * <统计网格内的出租房数量>
	 * 
	 * @param gridIds
	 *            网格ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public Map<Integer, Integer> statRentHouseNum(List<Integer> gridIds) {
		Map<Integer, Integer> result = new LinkedHashMap<Integer, Integer>();
		for (Integer gridId : gridIds) {
			List<BuildingInfo> buildingInfos = buildingDao.queryBuildingsByGridId(gridId);
			List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
			for (BuildingInfo buildingInfo : buildingInfos) {
				houseInfos.addAll(buildingInfo.getHouseInfos());
			}
			int houseNum = 0;// 网格出租房数量
			for (HouseInfo houseInfo : houseInfos) {
				List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryBaseRohByHouseId(houseInfo.getHouseId());
				List<String> residentTypes = new ArrayList<String>();
				for (ResidentOfHouseInfo roh : rohs) {
					residentTypes.add(roh.getResidentType());
				}
				if (residentTypes.contains("02")) {// 租户
					houseNum = houseNum + 1;
				}
			}
			result.put(gridId, houseNum);
		}
		return result;
	}

	/**
	 * 
	 * <根据条件获取楼栋统计数据集合>
	 * 
	 * @param buildingName:模糊匹配
	 *            street community buildingName
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<BuildingStat> getBuildingStats(String buildingName) {
		List<BuildingStat> buildingStats = new ArrayList<BuildingStat>();
		try {
			// 获取当前用户下辖的网格ID
			List<Integer> gridIds = new ArrayList<Integer>();
			IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
			List<BuildingInfo> buildingInfos = buildingDao.queryBuildingByBuildingFullName(buildingName, gridIds);
			for (BuildingInfo buildingInfo : buildingInfos) {
				BuildingStat buildingStat = new BuildingStat();
				buildingStat.setBuildingId(buildingInfo.getBuildingId());
				buildingStat.setBuildingPurpose(buildingInfo.getBuildingPurpose());
				buildingStat.setStreet(buildingInfo.getStreet());
				buildingStat.setCommunity(buildingInfo.getCommunity());
				buildingStat.setGridId(buildingInfo.getGridId());
				buildingStat.setBuildingName(buildingInfo.getFullBuildingName());
				buildingStat.setLongitude(buildingInfo.getLongitude());
				buildingStat.setLatitude(buildingInfo.getLatitude());
				String basePic = "";
				if (buildingInfo.getPictures() != null && !buildingInfo.getPictures().equals("")) {
					String[] pathArr = buildingInfo.getPictures().split(",");
					InputStream in = null;
					byte[] data = null;
					try {
						String path = pathArr[0] + ".jpg";
						File file = new File(path);
						in = new FileInputStream(file);
						data = new byte[in.available()];
						in.read(data);
						in.close();
						basePic = new String(Base64.encodeBase64(data));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				buildingStat.setPicUrl(basePic);
				List<HouseInfo> houseInfos = buildingInfo.getHouseInfos();
				int shopNum = 0;// 用于商铺的房屋户数
				int selfNum = 0;// 用于自住的房屋户数
				int rentNum = 0;// 用于租住的房屋户数
				BigDecimal rent = new BigDecimal(0);// 租住人口占比（相对于整栋楼的）
				int rentPeople = 0;// 租住的人口数量
				int allPeople = 0;// 所有人口数量
				List<String> companys = new ArrayList<String>();// 房屋为企业的房号
				for (HouseInfo houseInfo : houseInfos) {
					if (houseInfo.getHouseType().equals("03")) {// 商铺
						shopNum += 1;
					}
					if (houseInfo.getHouseType().equals("04")) {// 办公
						companys.add(houseInfo.getDoorplate());
					}
					List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryBaseRohByHouseId(houseInfo.getHouseId());
					if (rohs.size() > 0) {
						boolean rentFlag = false;
						boolean selfFlag = false;
						for (ResidentOfHouseInfo roh : rohs) {
							if (roh.getResidentType().equals("02")) {// 租户
								rentPeople += 1;
								rentFlag = true;
							} else if (roh.getResidentType().equals("01")) {// 自住
								selfFlag = true;
							}
						}
						if (rentFlag) {
							rentNum += 1;
						}
						if (selfFlag) {
							selfNum += 1;
						}
						allPeople += rohs.size();
					}
				}
				buildingStat.setShops(shopNum);
				buildingStat.setOwners(selfNum);
				buildingStat.setTenants(rentNum);
				buildingStat.setFirms(companys);
				if (allPeople > 0) {
					rent = new BigDecimal(rentPeople).multiply(new BigDecimal(100)).divide(new BigDecimal(allPeople), 2,
							BigDecimal.ROUND_HALF_EVEN);
				}
				buildingStat.setUsage(rent.floatValue());
				buildingStats.add(buildingStat);
			}
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return buildingStats;
	}

	/**
	 * 
	 * <根据楼栋ID获取楼栋统计数据>
	 * 
	 * @param buildingId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public BuildingStat getBuildingStat(Long buildingId) {
		BuildingStat buildingStat = new BuildingStat();
		try {
			// 获取当前用户下辖的网格ID
			List<Integer> gridIds = new ArrayList<Integer>();
			IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
			BuildingInfo buildingInfo = buildingDao.queryBuildingById(buildingId, gridIds);
			buildingStat.setBuildingId(buildingInfo.getBuildingId());
			buildingStat.setBuildingPurpose(buildingInfo.getBuildingPurpose());
			buildingStat.setStreet(buildingInfo.getStreet());
			buildingStat.setCommunity(buildingInfo.getCommunity());
			buildingStat.setGridId(buildingInfo.getGridId());
			buildingStat.setBuildingName(buildingInfo.getFullBuildingName());
			buildingStat.setLongitude(buildingInfo.getLongitude());
			buildingStat.setLatitude(buildingInfo.getLatitude());
			String basePic = "";
			if (buildingInfo.getPictures() != null && !buildingInfo.getPictures().equals("")) {
				String[] pathArr = buildingInfo.getPictures().split(",");
				InputStream in = null;
				byte[] data = null;
				try {
					String path = pathArr[0] + ".jpg";
					File file = new File(path);
					in = new FileInputStream(file);
					data = new byte[in.available()];
					in.read(data);
					in.close();
					basePic = new String(Base64.encodeBase64(data));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			buildingStat.setPicUrl(basePic);
			List<HouseInfo> houseInfos = buildingInfo.getHouseInfos();
			int shopNum = 0;// 用于商铺的房屋户数
			int selfNum = 0;// 用于自住的房屋户数
			int rentNum = 0;// 用于租住的房屋户数
			BigDecimal rent = new BigDecimal(0);// 租住人口占比（相对于整栋楼的）
			int rentPeople = 0;// 租住的人口数量
			int allPeople = 0;// 所有人口数量
			List<String> companys = new ArrayList<String>();// 房屋为企业的房号
			for (HouseInfo houseInfo : houseInfos) {
				if (houseInfo.getHouseType().equals("03")) {// 商铺
					shopNum += 1;
				}
				if (houseInfo.getHouseType().equals("04")) {// 办公
					companys.add(houseInfo.getDoorplate());
				}
				List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryBaseRohByHouseId(houseInfo.getHouseId()); 
				if (rohs.size() > 0) {
					boolean rentFlag = false;
					boolean selfFlag = false;
					for (ResidentOfHouseInfo roh : rohs) {
						if (roh.getResidentType().equals("02")) {// 租户
							rentPeople += 1;
							rentFlag = true;
						} else if (roh.getResidentType().equals("01")) {// 自住
							selfFlag = true;
						}
					}
					if (rentFlag) {
						rentNum += 1;
					}
					if (selfFlag) {
						selfNum += 1;
					}
					allPeople += rohs.size();
				}
			}
			buildingStat.setShops(shopNum);
			buildingStat.setOwners(selfNum);
			buildingStat.setTenants(rentNum);
			buildingStat.setFirms(companys);
			if (allPeople > 0) {
				rent = new BigDecimal(rentPeople).multiply(new BigDecimal(100)).divide(new BigDecimal(allPeople), 2,
						BigDecimal.ROUND_HALF_EVEN);
			}
			buildingStat.setUsage(rent.floatValue());
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return buildingStat;
	}

	/**
	 * 
	 * <获取某个楼栋某个单元的所有住户ID>
	 * 
	 * @param buildingId
	 * @param unitNumber：单元号
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<Integer> getResidentIdsOfBuildingUnit(Long buildingId, Integer unitNumber) {
		List<Integer> residentIds = new ArrayList<Integer>();
		try {
			if (buildingId == null) {
				throw new AppModuleErrorException("请输入需要查询的楼栋");
			}
			List<HouseInfo> houseInfos = houseDao.queryBaseHousesByBuidlingIdUnitNumber(buildingId, unitNumber);
			List<ResidentOfHouseInfo> allRohs = residentOfHouseDao.queryBaseRohByHouseIds(houseInfos);
			for (ResidentOfHouseInfo rohs : allRohs) {
				residentIds.add(Integer.valueOf(String.valueOf(rohs.getResidentBaseId())));
			}
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return residentIds;
	}

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
	@Override
	public List<HouseInfo> getHouseInfos(List<BuildingInfo> buildingInfos, Integer unitNumber, Integer floorNumber,
			Integer houseNumber) {
		List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
		try {
			List<Integer> gridIds = new ArrayList<Integer>();
			if (buildingInfos == null || buildingInfos.isEmpty()) {
				IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
				List<GridItem> gridItems = gridService.getCurrentUserGrid();
				for (GridItem gridItem : gridItems) {
					gridIds.add(gridItem.getGridId());
				}
			}
			buildingInfos = buildingDao.queryBaseBuildingsByAddress(null, null, gridIds, null);
			houseInfos = houseDao.queryBaseHouseInfos(buildingInfos, unitNumber, floorNumber, houseNumber);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return houseInfos;
	}

	/**
	 * 
	 * <b/s端使用，统计一栋楼的每个房屋：是否关联人口，房屋用途，房屋中有特殊标签的人>
	 * 
	 * @param buildingId
	 * 
	 * @return <unitNumber,<floorNumber,<houseNumber,data>>
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public Map<Integer, Map<Integer, Map<Integer, Object>>> getBuildingTile(Long buildingId) {
		Map<Integer, Map<Integer, Map<Integer, Object>>> buildingFlat = new LinkedHashMap<Integer, Map<Integer, Map<Integer, Object>>>();
		try {
			if (buildingId == null) {
				throw new AppModuleErrorException("请输入楼栋信息");
			}
			BuildingInfo building = buildingDao.queryBaseBuildingById(buildingId, null);
			List<BuildingInfo> buildings = new ArrayList<BuildingInfo>();
			buildings.add(building);
			for (int i = 1; i <= building.getUnitNum(); i++) {// 分单元
				Map<Integer, Map<Integer, Object>> unitFlat = new LinkedHashMap<Integer, Map<Integer, Object>>();// 单元
				if (building.getUndergroundNum() != 0) {// 地下
					for (int j = building.getUndergroundNum(); j < 0; j++) {
						List<HouseInfo> houses = houseDao.queryBaseHouseInfos(buildings, i, j, null);// 某单元某楼层房屋
						Map<Integer, Object> floorFlat = new LinkedHashMap<Integer, Object>();// 每层
						for (HouseInfo house : houses) {
							Map<String, Object> houseFlat = new HashMap<String, Object>();// 每个房子
							List<ResidentOfHouseInfo> rohs = residentOfHouseDao
									.queryBaseRohByHouseId(house.getHouseId());
							boolean isAssociate = false;// 是否关联人口
							if (rohs.size() > 0) {
								isAssociate = true;
							}
							IPersonManageService personManageService = (IPersonManageService) frame.getModuleManager()
									.getModule(IPersonManageService.APP_ID);

							Map<Long, Object> residentLabels = new HashMap<Long, Object>();
							for (ResidentOfHouseInfo roh : rohs) {
								ResidentInfo residentInfo = personManageService.getResidentInfo(
										IPersonManageService.LABEL_BASE,
										Integer.valueOf(String.valueOf(roh.getResidentBaseId())), null, false);
								if (residentInfo != null) {
									residentLabels.put(roh.getResidentBaseId(), residentInfo.getLabels());
								}
							}
							// houseFlat.put("house", house);
							houseFlat.put("isAssociate", isAssociate);
							houseFlat.put("lables", residentLabels);
							houseFlat.put("id", house.getHouseId());
							houseFlat.put("doorplate", house.getDoorplate());
							houseFlat.put("type", house.getHouseType());
							floorFlat.put(house.getHouseNumber(), houseFlat);
						}
						unitFlat.put(j, floorFlat);
					}
				}
				if (building.getAbovegroundNum() != 0) {// 地上
					for (int j = 1; j <= building.getAbovegroundNum(); j++) {
						List<HouseInfo> houses = houseDao.queryBaseHouseInfos(buildings, i, j, null);// 某单元某楼层房屋
						Map<Integer, Object> floorFlat = new LinkedHashMap<Integer, Object>();// 每层
						for (HouseInfo house : houses) {
							Map<String, Object> houseFlat = new HashMap<String, Object>();// 每个房子
							List<ResidentOfHouseInfo> rohs = residentOfHouseDao
									.queryBaseRohByHouseId(house.getHouseId());
							boolean isAssociate = false;// 是否关联人口
							if (rohs.size() > 0) {
								isAssociate = true;
							}
							IPersonManageService personManageService = (IPersonManageService) frame.getModuleManager()
									.getModule(IPersonManageService.APP_ID);

							Map<Long, Object> residentLabels = new HashMap<Long, Object>();
							for (ResidentOfHouseInfo roh : rohs) {
								ResidentInfo residentInfo = personManageService.getResidentInfo(
										IPersonManageService.LABEL_BASE,
										Integer.valueOf(String.valueOf(roh.getResidentBaseId())), null, false);
								if (residentInfo != null) {
									residentLabels.put(roh.getResidentBaseId(), residentInfo.getLabels());
								}
							}
							// houseFlat.put("house", house);
							houseFlat.put("isAssociate", isAssociate);
							houseFlat.put("lables", residentLabels);
							houseFlat.put("id", house.getHouseId());
							houseFlat.put("doorplate", house.getDoorplate());
							houseFlat.put("type", house.getHouseType());
							floorFlat.put(house.getHouseNumber(), houseFlat);
						}
						unitFlat.put(j, floorFlat);
					}
				}
				buildingFlat.put(i, unitFlat);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return buildingFlat;
	}

	/**
	 * 
	 * <楼栋名称模糊匹配查询楼栋信息>
	 * 
	 * @param buildingName
	 *            street（楼栋所属街道）+community（漏送所属社区）+buildingName（楼栋名称）：如，
	 *            佛祖岭街道佛祖岭B区36栋
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<BuildingInfo> getBuildingByFullName(String buildingName) {
		List<BuildingInfo> result = new ArrayList<BuildingInfo>();
		try {
			if (buildingName == null) {
				throw new AppModuleErrorException("请输入需要查询的楼栋名称");
			}
			// 获取当前用户下辖的网格ID
			List<Integer> gridIds = new ArrayList<Integer>();
			IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
			result = buildingDao.queryBaseBuildingByBuildingFullName(buildingName, gridIds);
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * <根据人的身份证号查询人关联的房屋信息>
	 * 
	 * @param idNo
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<HouseInfo> getHouseInfosByResidentIdNo(String idNo) {
		List<HouseInfo> result = new ArrayList<HouseInfo>();
		try {
			if (idNo == null || idNo.isEmpty()) {
				throw new AppModuleErrorException("请输入需要查询的楼栋名称");
			}
			IPersonManageService personService = (IPersonManageService) frame.getModuleManager()
					.getModule(IPersonManageService.APP_ID);
			ResidentInfo resident = personService.getResidentInfo(IPersonManageService.LABEL_BASE, null, idNo, false);
			if (resident != null) {
				List<ResidentOfHouseInfo> rohs = residentOfHouseDao
						.queryRohByResidentId(Long.valueOf(resident.getB().getResidentBaseId()));
				for (ResidentOfHouseInfo roh : rohs) {
					result.add(roh.getHouseInfo());
				}
			}
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * <根据房屋人数查询房屋信息>
	 * 
	 * @param residentNum：人数大于等于输入的参数
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<HouseInfo> getHouseInfosByResidentNum(Integer residentNum) {
		List<HouseInfo> result = new ArrayList<HouseInfo>();
		try {
			if (residentNum == null) {
				throw new AppModuleErrorException("请输入需人数范围");
			}
			result = houseDao.queryHouseInfoByResidentNum(residentNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return result;
	}

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
	@Override
	public Map<String, List<ResidentBaseInfo>> getResidentInfoOfHouseByLable(Long houseId, String lable) {
		Map<String, List<ResidentBaseInfo>> result = new HashMap<String, List<ResidentBaseInfo>>();
		try {
			if (houseId == null || lable == null) {
				throw new AppModuleErrorException("请输入需要查询的房屋和人口标签");
			}
			List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryBaseRohByHouseId(houseId);
			List<Integer> residentBaseIds = new ArrayList<Integer>();
			for (ResidentOfHouseInfo roh : rohs) {
				residentBaseIds.add(Integer.valueOf(String.valueOf(roh.getResidentBaseId())));
			}
			IPersonManageService personManageService = (IPersonManageService) frame.getModuleManager()
					.getModule(IPersonManageService.APP_ID);
			List<ResidentBaseInfo> residents = personManageService.getSpecialsInfoByIds(residentBaseIds, lable);
			HouseInfo houseInfo = houseDao.queryBaseHouseById(houseId);
			result.put(houseInfo.getDoorplate(), residents);
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return result;
	}

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
	@Override
	public Map<String, List<ResidentBaseInfo>> getResidentInfoOfHouseOfBuildingByLable(Long buildingId, String lable) {
		Map<String, List<ResidentBaseInfo>> result = new HashMap<String, List<ResidentBaseInfo>>();
		try {
			if (buildingId == null || lable == null) {
				throw new AppModuleErrorException("请输入需要查询的楼栋和人口标签");
			}
			List<Integer> gridIds = new ArrayList<Integer>();
			IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
			List<HouseInfo> houseInfos = houseDao.queryBaseHousesByBuildingId(buildingId);
			for (HouseInfo house : houseInfos) {
				List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryBaseRohByHouseId(house.getHouseId());
				List<Integer> residentBaseIds = new ArrayList<Integer>();
				for (ResidentOfHouseInfo roh : rohs) {
					residentBaseIds.add(Integer.valueOf(String.valueOf(roh.getResidentBaseId())));
				}
				IPersonManageService personManageService = (IPersonManageService) frame.getModuleManager()
						.getModule(IPersonManageService.APP_ID);
				List<ResidentBaseInfo> residents = personManageService.getSpecialsInfoByIds(residentBaseIds, lable);

				result.put(house.getDoorplate(), residents);
			}

		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return result;

	}

	/**
	 * 
	 * <查询网格中特定标签的人的数量>
	 * 
	 * @param gridId
	 * @param lable:帮扶，重点
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Integer getResidentInfoOfGridByLable(Integer gridId, String lable) {
		int result = 0;
		List<BuildingInfo> buildings = buildingDao.queryBuildingsByGridId(gridId);
		List<HouseInfo> houses = new ArrayList<HouseInfo>();
		for (BuildingInfo building : buildings) {
			for (HouseInfo house : building.getHouseInfos()) {
				houses.add(house);
			}
		}
		List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryBaseRohByHouseIds(houses);
		List<Integer> residentBaseIds = new ArrayList<Integer>();
		for (ResidentOfHouseInfo roh : rohs) {
			residentBaseIds.add(Integer.valueOf(String.valueOf(roh.getResidentBaseId())));
		}
		IPersonManageService personManageService = (IPersonManageService) frame.getModuleManager()
				.getModule(IPersonManageService.APP_ID);
		List<ResidentBaseInfo> residents = personManageService.getSpecialsInfoByIds(residentBaseIds, lable);
		result += residents.size();
		
		return result;
	}

	/**
	 * 
	 * <根据楼栋ID和房屋类别查询房屋信息>
	 * 
	 * @param buildingAddress：街道，社区，楼栋名
	 * @param houseType
	 * @return type List<HouseAddress>
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public Map<String, String> getHousesByTypes(Long buildingId, List<String> houseTypes) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		try {
			if (buildingId == null || houseTypes.isEmpty()) {
				throw new AppModuleErrorException("请输入需要查询的楼栋和房屋类别");
			}
			BuildingInfo building = buildingDao.queryBaseBuildingById(buildingId, null);
			for (String houseType : houseTypes) {
				List<HouseInfo> houseInfos = houseDao.queryBaseHouseByBuildingIdAndType(buildingId, houseType);
				for (HouseInfo house : houseInfos) {
					result.put(building.getStreet() + "/" + building.getCommunity() + "/" + building.getBuildingName()
							+ "/" + house.getUnitNumber() + "单元/" + house.getFloorNumber() + "0"
							+ house.getHouseNumber(), houseType);
				}
			}
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * <统计网格出租房数量>
	 * 
	 * @param gridIds：网格ID集合
	 * @return <网格名，出租房数量>
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public Map<String, Integer> getRentHouseNumOfGrids(List<Integer> gridIds) {
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		try {
			List<GridItem> gridItems = new ArrayList<GridItem>();
			IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
			if (gridIds == null || gridIds.isEmpty()) {
				gridIds = new ArrayList<Integer>();
				gridItems = gridService.getCurrentUserGrid();
				for (GridItem gridItem : gridItems) {
					gridIds.add(gridItem.getGridId());
				}
			} else {
				for (Integer gridId : gridIds) {
					gridItems.add(gridService.getGridById(gridId));
				}
			}
			for (GridItem gridItem : gridItems) {
				List<BuildingInfo> buildings = buildingDao.queryBuildingsByGridId(gridItem.getGridId());
				int rentHouseNumOfGrid = 0;
				for (BuildingInfo building : buildings) {
					int rentHouseNumOfBuilding = 0;
					for (HouseInfo house : building.getHouseInfos()) {
						boolean isRentHouse = false;
						List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryBaseRohByHouseId(house.getHouseId());
						for (ResidentOfHouseInfo roh : rohs) {
							if (roh.getResidentType().equals("02")) {
								isRentHouse = true;
							}
						}
						if (isRentHouse) {
							rentHouseNumOfBuilding += 1;
						}
					}
					rentHouseNumOfGrid += rentHouseNumOfBuilding;
				}
				result.put(gridItem.getGridName(), rentHouseNumOfGrid);
			}
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * <统计楼栋出租房数量>
	 * 
	 * @param buildingInfos：楼栋信息集合，楼栋信息包含房屋信息
	 * @return 返回<楼栋ID, 此楼栋出租房数量>
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public Map<Integer, Integer> getRentHouseNumOfBuildings(List<BuildingInfo> buildingInfos) {
		Map<Integer, Integer> result = new LinkedHashMap<Integer, Integer>();
		try {
			for (BuildingInfo building : buildingInfos) {
				int rentHouseNumOfBuilding = 0;
				for (HouseInfo house : building.getHouseInfos()) {
					boolean isRentHouse = false;
					List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryBaseRohByHouseId(house.getHouseId());
					for (ResidentOfHouseInfo roh : rohs) {
						if (roh.getResidentType().equals("02")) {
							isRentHouse = true;
						}
					}
					if (isRentHouse) {
						rentHouseNumOfBuilding += 1;
					}
				}
				result.put(Integer.valueOf(String.valueOf(building.getBuildingId())), rentHouseNumOfBuilding);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * <统计当前用户（网格员）各个楼栋标签的人的数量>
	 * 
	 * @param label
	 *            需要查询的标签
	 * @return <楼栋ID，此楼栋下改标签的人的数量>
	 * @see [类、类#方法、类#成员]
	 */
	public Map<Integer, Integer> getLabelResidentNum(String label) {
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		List<Integer> gridIds = new ArrayList<Integer>();
		IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
		List<GridItem> gridItems = gridService.getCurrentUserGrid();
		for (GridItem gridItem : gridItems) {
			gridIds.add(gridItem.getGridId());
		}
		List<BuildingInfo> buildings = buildingDao.queryBuildingsByGridIds(gridIds);
		for (BuildingInfo building : buildings) {
			int rentNum = 0;
			for (HouseInfo house : building.getHouseInfos()) {
				List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryBaseRohByHouseId(house.getHouseId());
				List<Integer> residentBaseIds = new ArrayList<Integer>();
				for (ResidentOfHouseInfo roh : rohs) {
					residentBaseIds.add(Integer.valueOf(String.valueOf(roh.getResidentBaseId())));
				}
				IPersonManageService personManageService = (IPersonManageService) frame.getModuleManager()
						.getModule(IPersonManageService.APP_ID);
				List<ResidentBaseInfo> residents = personManageService.getSpecialsInfoByIds(residentBaseIds, label);
				rentNum += residents.size();
			}
			result.put(Integer.valueOf(String.valueOf(building.getBuildingId())), rentNum);
		}
		return result;
	}

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
	@Override
	public List<BuildingInfo> getBaseSelectBuildings(String street, String community, Integer gridId) {
		List<BuildingInfo> result = new ArrayList<BuildingInfo>();
		try {
			List<Integer> gridIds = new ArrayList<Integer>();
			if (gridId != null) {
				gridIds.add(gridId);
			} else {
				IGridService gridService = (IGridService) frame.getModuleManager().getModule(IGridService.APP_ID);
				List<GridItem> gridItems = gridService.getCurrentUserGrid();
				for (GridItem gridItem : gridItems) {
					gridIds.add(gridItem.getGridId());
				}
			}
			result = buildingDao.queryBaseBuildingsByAddress(street, community, gridIds, null);
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * <条件获取房屋信息>
	 * 
	 * @param buildingId
	 *            楼栋ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public Map<String, Map<String, Map<String, HouseInfo>>> getHouses(Long buildingId) {
		Map<String, Map<String, Map<String, HouseInfo>>> result = new HashMap<String, Map<String, Map<String, HouseInfo>>>();
		try {
			if (buildingId == null) {
				throw new AppModuleErrorException("请输入需要查询的楼栋");
			}

			BuildingInfo building = buildingDao.queryBaseBuildingById(buildingId, null);
			List<HouseInfo> houseInfos = houseDao.queryBaseHousesByBuildingId(buildingId);
			for (int i = 1; i <= building.getUnitNum(); i++) {
				List<HouseModelInfo> houseModels = houseDao.queryHouseModels(buildingId, i);
				Map<String, Map<String, HouseInfo>> floorHouses = new LinkedHashMap<String, Map<String, HouseInfo>>();
				for (HouseModelInfo hm : houseModels) {
					List<String> floorNumberStrs = Arrays.asList(hm.getFloorScope().split(","));
					List<Integer> floorNumbers = new ArrayList<Integer>();
					for (String str : floorNumberStrs) {
						floorNumbers.add(Integer.valueOf(str));
					}
					List<String> houseNumberStrs = Arrays.asList(hm.getHouseholdScope().split(","));
					List<Integer> houseNumbers = new ArrayList<Integer>();
					for (String str : houseNumberStrs) {
						houseNumbers.add(Integer.valueOf(str));
					}
					Collections.sort(floorNumbers);
					Collections.sort(houseNumbers);
					hm.setStartFloorNumber(Integer.valueOf(floorNumbers.get(0)));
					hm.setEndFloorNumber(Integer.valueOf(floorNumbers.get(floorNumbers.size() - 1)));
					for (int f = hm.getStartFloorNumber(); f <= hm.getEndFloorNumber(); f++) {
						Map<String, HouseInfo> housesOfFloor = new LinkedHashMap<String, HouseInfo>();
						for (int h = 1; h <= hm.getHouseholdNum(); h++) {
							for (HouseInfo house : houseInfos) {
								if (house.getUnitNumber() == i && house.getFloorNumber() == f
										&& house.getHouseNumber() == h) {
									housesOfFloor.put("0" + h + "号", house);
								}
							}

						}
						floorHouses.put(f + "层", housesOfFloor);
					}
				}
				result.put(i + "单元", floorHouses);
			}
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * <获取网格内房屋总数及已关联人口房屋数量>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public Map<Integer, Map<String, Integer>> getHouseNumByGrids(List<Integer> gridIds) {
		// TODO Auto-generated method stub
		Map<Integer, Map<String, Integer>> result = new LinkedHashMap<Integer, Map<String, Integer>>();
		try {
			for (Integer gridId : gridIds) {
				Map<String, Integer> gridHouseNum = new HashMap<String, Integer>();
				List<BuildingInfo> buildings = buildingDao.queryBuildingsByGridId(gridId);
				int houseNum = 0;
				int takenHouseNum = 0;
				for (BuildingInfo building : buildings) {
					List<HouseInfo> houses = building.getHouseInfos();
					houseNum += houses.size();
					int buildTakenHouseNum = 0;
					for (HouseInfo house : houses) {
						List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryBaseRohByHouseId(house.getHouseId());
						if (rohs.size() > 0) {
							buildTakenHouseNum += 1;
						}
					}
					takenHouseNum += buildTakenHouseNum;
				}
				gridHouseNum.put("houseNum", houseNum);
				gridHouseNum.put("takenHouseNum", takenHouseNum);
				result.put(gridId, gridHouseNum);
			}
		} catch (AppModuleErrorException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppModuleErrorException("系统错误，" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * <按房屋分类统计房屋数量，包括总数>
	 * 
	 * @param buildingId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public Map<String, Integer> getHouseNumByType() {
		// TODO Auto-generated method stub
		List<BuildingInfo> builds = buildingDao.queryBuildingsByGridIds(null);
		// 统计基础信息，以及所有房屋信息
		List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
		for (BuildingInfo buildingInfo : builds) {
			houseInfoList.addAll(buildingInfo.getHouseInfos());
		}
		// 按房屋用途 统计房屋信息集合，key:房屋用途，value:此用途房屋数量
		Map<String, Integer> statHouseInfo = new LinkedHashMap<String, Integer>();
		List<DataDictionaryItem> houseTypes = buildingDataDictionaryItemDao.selectByDataType("房屋用途");
		for (DataDictionaryItem houseType : houseTypes) {
			List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
			for (HouseInfo houseInfo : houseInfoList) {
				if ((houseInfo.getHouseType() != null || !houseInfo.getHouseType().isEmpty())// 房屋如果只是初始化，type为空
						&& houseInfo.getHouseType().equals(houseType.getDataCode())) {
					houseInfos.add(houseInfo);
				}
			}
			statHouseInfo.put(houseType.getDataName(), houseInfos.size());
		}
		statHouseInfo.put("total", houseInfoList.size());
		return statHouseInfo;
	}

	/**
	 * 
	 * <计算楼栋信息完整度及已关联房屋的数量，人的数量>
	 * 
	 * @param buildingInfo
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private Map<String, Object> calculateIntegrity(BuildingInfo buildingInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		BigDecimal integrity = new BigDecimal(0);
		// 楼栋户数
		int householdNum = buildingInfo.getHouseholdNum();
		// 房屋信息完整的数量：房屋若关联了人口，则此房屋信息算作已完善
		int houseIntegrityNum = 0;
		List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
		if (buildingInfo.getHouseInfos() == null || buildingInfo.getHouseInfos().isEmpty()) {
			houseInfos = houseDao.queryBaseHousesByBuildingId(buildingInfo.getBuildingId());
		} else {
			houseInfos = buildingInfo.getHouseInfos();
		}
		// 房屋已关联的人口的熟练
		int personNum = 0;
		for (HouseInfo houseInfo : houseInfos) {
			List<ResidentOfHouseInfo> rohs = residentOfHouseDao.queryBaseRohByHouseId(houseInfo.getHouseId());
			if (rohs != null & rohs.size() > 0) {
				houseIntegrityNum += 1;
			}
			personNum += rohs.size();
		}
		if (houseIntegrityNum > 0) {
			integrity = new BigDecimal(houseIntegrityNum).multiply(new BigDecimal(100))
					.divide(new BigDecimal(householdNum), 2, BigDecimal.ROUND_HALF_EVEN);
		}
		result.put("integrity", integrity);
		result.put("integrityNum", houseIntegrityNum);
		result.put("personNum", personNum);
		return result;
	}

	private static final AuthorityItem AUTH_ACCESS = new AuthorityItem("House_Data_Accessibility", "查看房屋数据",
			"主要涉及：获取楼栋、房屋、关联人员");
	private static final AuthorityItem AUTH_MODIFY = new AuthorityItem("House_Data_Modification", "修改房屋数据",
			"主要涉及：楼栋、房屋、关联人员增、删、改操作");

	class IHouseManageServiceBroker implements IModuleBroker {

		@Override
		public List<AuthorityItem> getAuthorityList() {
			return Arrays.asList(AUTH_ACCESS, AUTH_MODIFY);
		}

		@Override
		public boolean checkRequestAuthorityAndScope(HttpServletRequest request, RequestAuthority output) {
			if (output == null)
				return false;
			String[] paths = request.getRequestURI().split("/");
			if (paths.length > 4) {
				if (paths[1].equals("m") && paths[2].equals("resident")) {
					if (paths[3].equals("s")) {
						output.authKey = AUTH_ACCESS.authKey;
						return true;
					} else if (paths[3].equals("m")) {
						output.authKey = AUTH_MODIFY.authKey;
						return true;
					}
				}
			}
			return false;
		}

		/**
		 * 返回当前用户允许的操作，以便页面以此来启用/禁用操作按钮 模块要根据当前用户的权限，决定哪些操作可用，返回可用的操作列表
		 * 操作是模块自定义的字符串，做关键字
		 * 常用的操作定义：add,del,edit,view,query,enable,disable,play,download,upload,
		 * import,export,relay 等分别代表新建、删除、编辑、查看、查询、启用、禁用、播放、下载、上传、导入、导出、转发
		 * 
		 * @return
		 * @see [类、类#方法、类#成员]
		 */
		@Override
		public List<String> getAllowableAction() {
			List<String> functions = new ArrayList<String>();
			try {
				int userId = frame.getCurrentSession().getCurrentSysUser().getUserId();
				IAuthorityService authorityService = (IAuthorityService) frame.getModuleManager()
						.getModule(IAuthorityService.APP_ID);
				List<AuthorityItem> authorities = authorityService.getUserAuthority(userId, true);
				for (AuthorityItem item : authorities) {
					if (item.getAuthKey().equals(AUTH_ACCESS.authKey)) {
						functions.add(IUnicornFrame.ACT_VIEW[0]);
						functions.add(IUnicornFrame.ACT_QUERY[0]);
						functions.add(IUnicornFrame.ACT_DOWNLD[0]);
					}
					if (item.getAuthKey().equals(AUTH_MODIFY.authKey)) {
						functions.add(IUnicornFrame.ACT_ADD[0]);
						functions.add(IUnicornFrame.ACT_DEL[0]);
						functions.add(IUnicornFrame.ACT_EDIT[0]);
						functions.add(IUnicornFrame.ACT_UPLD[0]);
					}
				}
			} catch (Exception e) {
				throw new AppModuleErrorException("系统错误，" + e.getMessage());
			}
			return functions;
		}

		/**
		 * 重载方法
		 * 
		 * @return
		 */
		@Override
		public boolean isAnyAccessible() {
			int userId = frame.getCurrentSession().getCurrentSysUser().getUserId();
			IAuthorityService authorityService = (IAuthorityService) frame.getModuleManager()
					.getModule(IAuthorityService.APP_ID);
			List<AuthorityItem> authorities = authorityService.getUserAuthority(userId, true);
			return !authorities.isEmpty();
		}
	}

}
