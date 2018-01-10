/*
 * 文 件 名:  HouseManagerController.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月25日
 * 修改内容:  
 */
package com.hxts.unicorn.modules.resident.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.model.BuildingModel;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.IUnicornFrame;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.BuildingInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HisResidentOfHouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.HouseModelInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;

import net.coobird.thumbnailator.Thumbnails;

/**
 * <楼栋，房屋 控制层 接口>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping(value = "/m/resident")
public class HouseManagerController {

	@Autowired
	private IHouseManageService houseManageServiceImpl;

	/**
	 * 
	 * <房屋管理首页查询获取楼栋信息>
	 * 
	 * @param street
	 *            街道
	 * @param community
	 *            社区
	 * @param gridId
	 *            网格id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/buildings", method = RequestMethod.GET)
	public @ResponseBody PageInfo<BuildingInfo> getBuildings(
			@RequestParam(name = "street", required = false) String street,
			@RequestParam(name = "community", required = false) String community,
			@RequestParam(name = "gridId", required = false) Integer gridId,
			@RequestParam(name = "buildingId", required = false) Long buildingId,
			@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
		List<BuildingInfo> buildings = houseManageServiceImpl.getBaseBuildings(street, community, gridId, buildingId,
				pageNum, pageSize);
		PageInfo<BuildingInfo> pageInfo = new PageInfo<BuildingInfo>(buildings);

		// 日志
		JSONObject json = new JSONObject();
		json.put("street", street);
		json.put("community", community);
		json.put("gridId", gridId);
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "查询", IHouseManageService.OPER_TARGET_BUILDING, 0l,
				json);
		return pageInfo;
	}

	/**
	 * 
	 * <B/S端房屋管理首页以及楼栋详情页楼栋信息统计图表>
	 * 
	 * @param buildings
	 *            楼栋ID，按","区隔
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/buildingstat", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getBuildingStats(@RequestParam("buildingIds") String buildingIds) {
		Map<String, Object> buildingsInfo = new HashMap<String, Object>();
		Map<String, Object> statBuilding = houseManageServiceImpl.subBuildingStat(buildingIds);
		buildingsInfo.put("statBuilding", statBuilding);// 统计数据信息
		return statBuilding;
	}

	/**
	 * 
	 * <获取单个楼栋基础信息>
	 * 
	 * @param buildingId
	 *            楼栋Id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/building", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getBuilding(@RequestParam("buildingId") long buildingId) {
		Map<String, Object> result = new HashMap<String, Object>();
		BuildingInfo building = houseManageServiceImpl.getBaseBuildingById(buildingId);
		// 楼栋基础数据信息
		result.put("building", building);
		// 楼栋图片 -> 转换为Base64编码
		List<String> pics = new ArrayList<String>();
		if (building.getPictures() != null && !building.getPictures().equals("")) {
			String[] pathArr = building.getPictures().split(",");
			for (String path : pathArr) {
				InputStream in = null;
				byte[] data = null;
				try {
					path = path + ".jpg";
					File file = new File(path);
					in = new FileInputStream(file);
					data = new byte[in.available()];
					in.read(data);
					in.close();
					String basePic = new String(Base64.encodeBase64(data));
					pics.add(basePic);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			result.put("pics", pics);
		}
		// 楼栋文件 ->提供文件名
		List<String> fileNames = new ArrayList<String>();
		if (building.getConstructionDrawings() != null && !building.getConstructionDrawings().equals("")) {
			String[] filePaths = building.getConstructionDrawings().split(",");
			for (String path : filePaths) {
				fileNames.add(path.substring(path.lastIndexOf("/") + 1));
			}
			result.put("fileName", fileNames);
		}
		// 日志
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "查看", IHouseManageService.OPER_TARGET_BUILDING,
				buildingId, null);
		return result;
	}

	/**
	 * 
	 * <获取单个楼栋房屋平铺图信息>
	 * 
	 * @param buildingId
	 *            楼栋Id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/buildingTile", method = RequestMethod.GET)
	public @ResponseBody Map<Integer, Map<Integer, Map<Integer, Object>>> getBuildingTile(
			@RequestParam("buildingId") long buildingId) {
		// 楼栋房屋平铺图
		Map<Integer, Map<Integer, Map<Integer, Object>>> buildingFalt = houseManageServiceImpl
				.getBuildingTile(buildingId);
		// 日志
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "查看", IHouseManageService.OPER_TARGET_BUILDING,
				buildingId, null);
		return buildingFalt;
	}

	/**
	 * 
	 * <获取楼栋中的房屋列表>
	 * 
	 * @param buildingId：楼栋ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/buildinghouses", method = RequestMethod.GET)
	public @ResponseBody PageInfo<HouseInfo> getBuilding(@RequestParam("buildingId") Long buildingId,
			@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<HouseInfo> houses = houseManageServiceImpl.getHousesByBuildingId(buildingId);
		PageInfo<HouseInfo> pageInfo = new PageInfo<HouseInfo>(houses);
		PageHelper.clearPage();
		return pageInfo;
	}

	/**
	 * 
	 * <条件查询楼栋信息>
	 * 
	 * @param street
	 *            街道
	 * @param community
	 *            社区
	 * @param buildingName
	 *            楼栋名称
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/buildinglist", method = RequestMethod.GET)
	public @ResponseBody PageInfo<BuildingInfo> getBuildingList(
			@RequestParam(name = "street", required = false) String street,
			@RequestParam(name = "community", required = false) String community,
			@RequestParam(name = "buildingName", required = false) String buildingName,
			@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<BuildingInfo> buildings = houseManageServiceImpl.getBaseBuildings(street, community, buildingName);
		PageInfo<BuildingInfo> pageInfo = new PageInfo<BuildingInfo>(buildings);
		// 日志
		JSONObject json = new JSONObject();
		json.put("street", street);
		json.put("community", community);
		json.put("buildingName", buildingName);
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "查询", IHouseManageService.OPER_TARGET_BUILDING, 0l,
				json);
		return pageInfo;
	}

	/**
	 * 
	 * <添加楼栋和房屋信息>
	 * 
	 * @param pics
	 *            外观图片
	 * @param files
	 *            建筑图纸
	 * @param buildingModel
	 *            楼栋信息，房屋建模数据
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "m/addbuilding", method = RequestMethod.POST)
	public @ResponseBody boolean addBuilding(@RequestParam("pics") MultipartFile[] pics,
			@RequestParam("files") MultipartFile[] files, BuildingModel buildingModel) {
		boolean flag = false;
		String picsPath = "";
		String filesPath = "";
		if (pics != null && files != null) {
			picsPath = uploadFile(pics, "pic");// 外观 图片
			filesPath = uploadFile(files, "file");// 建筑图纸
		}
		if (buildingModel == null) {
			throw new AppModuleErrorException("请输入楼栋信息");
		}
		// 添加楼栋
		BuildingInfo building = buildingModel.getBuilding();
		building.setPictures(picsPath);
		building.setConstructionDrawings(filesPath);
		houseManageServiceImpl.addBuilding(building);
		// 根据建模信息添加房屋信息
		flag = houseManageServiceImpl.addHousesByModel(buildingModel.getBuilding().getBuildingId(),
				buildingModel.getUms());
		// 日志
		JSONObject json = new JSONObject();
		json.put("building", building);
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "新建", IHouseManageService.OPER_TARGET_BUILDING,
				buildingModel.getBuilding().getBuildingId(), json);
		return flag;
	}

	/**
	 * 
	 * <修改楼栋和房屋信息>
	 * 
	 * @param building
	 *            楼栋信息基础信息
	 * @param houseModels
	 *            房屋建模信息
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "m/updatebuilding", method = RequestMethod.POST)
	public @ResponseBody boolean updateBuilding(@RequestBody BuildingInfo building) {
		// TODO 文件信息:外观 图片和建筑图纸， 可根据建模数据修改房屋基础信息
		boolean flag = false;
		flag = houseManageServiceImpl.modifyBuilding(building);
		// 日志
		JSONObject json = new JSONObject();
		json.put("building", building);
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "编辑", IHouseManageService.OPER_TARGET_BUILDING,
				building.getBuildingId(), json);
		return flag;
	}

	/**
	 * 
	 * <获取此楼栋的基本信息以及房屋建模信息>
	 * 
	 * @param buildingId
	 *            楼栋ID
	 * @param unitNumber
	 *            单元号
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/building/housemodel/{buildingId}/{unitNumber}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getBuildingAndHouseModel(@PathVariable long buildingId,
			@PathVariable int unitNumber) {
		Map<String, Object> result = new HashMap<String, Object>();
		BuildingInfo building = houseManageServiceImpl.getBaseBuildingById(buildingId);
		List<HouseModelInfo> houseModels = houseManageServiceImpl.getHouseModels(buildingId, unitNumber);
		result.put("building", building);
		result.put("houseModels", houseModels);
		// 日志
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "查看", IHouseManageService.OPER_TARGET_BUILDING,
				buildingId, null);
		return result;
	}

	/**
	 * 
	 * <获取房屋基本信息及关联人口信息>
	 * 
	 * @param houseId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/house/{houseId}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getHouse(@PathVariable long houseId) {
		Map<String, Object> result = new HashMap<String, Object>();
		HouseInfo house = houseManageServiceImpl.getHouseById(houseId);// 房屋基本信息+业主信息
		List<ResidentOfHouseInfo> residentOfHouses = houseManageServiceImpl.getResidentOfHouse(houseId);// 现住
		List<HisResidentOfHouseInfo> hisResidentOfHouses = houseManageServiceImpl.getHisResidentOfHouse(houseId);// 历史入住
		result.put("house", house);
		result.put("residentOfHouse", residentOfHouses);
		result.put("hisResidentOfHouse", hisResidentOfHouses);
		// 日志
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "查看", IHouseManageService.OPER_TARGET_HOUSE,
				houseId, null);
		return result;
	}

	/**
	 * 
	 * <获取房屋基本信息>
	 * 
	 * @param houseId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/house", method = RequestMethod.GET)
	public @ResponseBody HouseInfo getHouseInfo(@RequestParam("houseId") Long houseId) {
		HouseInfo house = houseManageServiceImpl.getHouseById(houseId);// 房屋基本信息+业主信息
		// 日志
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "查看", IHouseManageService.OPER_TARGET_HOUSE,
				houseId, null);
		return house;
	}

	/**
	 * <获取现住人口>
	 * 
	 * @param houseId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/houseroh", method = RequestMethod.GET)
	public @ResponseBody List<ResidentOfHouseInfo> getHouseRoh(@RequestParam("houseId") Long houseId) {
		List<ResidentOfHouseInfo> residentOfHouses = houseManageServiceImpl.getResidentOfHouse(houseId);// 现住
		return residentOfHouses;
	}

	/**
	 * <获取历史现住人口>
	 * 
	 * @param houseId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/househroh", method = RequestMethod.GET)
	public @ResponseBody List<HisResidentOfHouseInfo> getHouseHroh(@RequestParam("houseId") Long houseId) {
		List<HisResidentOfHouseInfo> hisResidentOfHouses = houseManageServiceImpl.getHisResidentOfHouse(houseId);// 历史入住
		return hisResidentOfHouses;
	}

	/**
	 * 
	 * <条件查询房屋列表>
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
	 *            房间号
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/houses", method = RequestMethod.GET)
	public @ResponseBody PageInfo<HouseInfo> getHouseInfos(
			@RequestParam(name = "street", required = false) String street,
			@RequestParam(name = "community", required = false) String community,
			@RequestParam(name = "gridId", required = false) Integer gridId,
			@RequestParam(name = "buildingId", required = false) Long buildingId,
			@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
		// TODO
		List<BuildingInfo> buildings = houseManageServiceImpl.getBaseBuildings(street, community, gridId, buildingId,
				null, null);
		if (buildings == null || buildings.isEmpty()) {
			List<Integer> gridIds = new ArrayList<Integer>();
			IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager()
					.getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
			buildings = houseManageServiceImpl.queryBaseBuildingByGridIds(gridIds);
		}
		PageHelper.startPage(pageNum, pageSize);
		List<HouseInfo> houseInfos = houseManageServiceImpl.getHousesByBuildings(buildings);
		PageInfo<HouseInfo> pageInfo = new PageInfo<HouseInfo>(houseInfos);
		PageHelper.clearPage();
		// 日志
		JSONObject json = new JSONObject();
		if (street != null && !street.isEmpty()) {
			json.put("street", street);
		}
		if (community != null && !community.isEmpty()) {
			json.put("community", community);
		}
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "查询", IHouseManageService.OPER_TARGET_HOUSE, 0l,
				json);
		return pageInfo;
	}

	/**
	 * 
	 * <修改房屋信息> 房屋用途，业主信息， 删除业主信息：设置房屋业主属性为null
	 * 
	 * @param house
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "m/modifyhouse", method = RequestMethod.POST)
	public @ResponseBody boolean modifyHouseInfo(@RequestBody HouseInfo house) {
		boolean flag = false;
		flag = houseManageServiceImpl.modifyHouse(house);
		// 日志
		JSONObject json = new JSONObject();
		json.put("house", house);
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "编辑", IHouseManageService.OPER_TARGET_HOUSE,
				house.getHouseId(), json);
		return flag;
	}

	/**
	 * 
	 * <关联人口>
	 * 
	 * @param houseId
	 *            房屋ID
	 * @param redidentId
	 *            人口ID
	 * @param residentType
	 *            与房关系：自住，租住，借住，寄住
	 * @param rentType
	 *            租住用途
	 * @param hiddenDanger
	 *            安全隐患
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "m/associate", method = RequestMethod.GET)
	public @ResponseBody int associateResident(@RequestParam("houseId") long houseId,
			@RequestParam("residentId") long residentId, @RequestParam("residentType") String residentType,
			@RequestParam(name = "rentType", required = false) String rentType,
			@RequestParam(name = "hiddenDanger", required = false) String hiddenDanger) {
		int flag = 0;
		flag = houseManageServiceImpl.associate(houseId, residentId, residentType, rentType, hiddenDanger);
		// 日志
		JSONObject json = new JSONObject();
		json.put("houseId", houseId);
		json.put("residentId", residentId);
		json.put("residentType", residentType);
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "新建", IHouseManageService.OPER_TARGET_ROH, 0l,
				json);
		return flag;
	}

	/**
	 * 
	 * <取消关联： 删除>
	 * 
	 * @param residentOfHouseId
	 *            关联信息ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "m/disassociate", method = RequestMethod.GET)
	public @ResponseBody boolean disAssociateResident(@RequestParam("residentOfHouseId") long residentOfHouseId) {
		boolean flag = false;
		flag = houseManageServiceImpl.disassociate(residentOfHouseId);
		return flag;
	}

	/**
	 * <根据人的身份证号查询人关联的房屋>
	 * 
	 * @param residentIdNo
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/housebyresidentIdNo", method = RequestMethod.GET)
	public @ResponseBody List<HouseInfo> getHouseInfosByResidentNo(@RequestParam("residentIdNo") String residentIdNo) {
		List<HouseInfo> houseInfos = houseManageServiceImpl.getHouseInfosByResidentIdNo(residentIdNo);
		// 日志
		JSONObject json = new JSONObject();
		json.put("residentIdNo", residentIdNo);
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "查询", IHouseManageService.OPER_TARGET_HOUSE, 0l,
				json);
		return houseInfos;
	}

	/**
	 * <查询关联人数大于一定数量的房屋信息>
	 * 
	 * @param residentNum
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/housebyresidentNum", method = RequestMethod.GET)
	public @ResponseBody List<HouseInfo> getHouseInfosByResidentNum(@RequestParam("residentNum") Integer residentNum) {
		List<HouseInfo> houseInfos = houseManageServiceImpl.getHouseInfosByResidentNum(residentNum);
		// 日志
		JSONObject json = new JSONObject();
		json.put("residentNum", residentNum);
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "查询", IHouseManageService.OPER_TARGET_HOUSE, 0l,
				json);
		return houseInfos;
	}

	/**
	 * <下载建筑图纸>
	 * 
	 * @param fileName
	 *            文件名称
	 * @param buildingId
	 *            楼栋ID
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/download/{buildingId}", method = RequestMethod.GET)
	public void downloadFile(@RequestParam("fileName") String fileName, @PathVariable Long buildingId,
			HttpServletResponse response) {
		if (fileName != null) {
			BuildingInfo building = houseManageServiceImpl.getBuildingById(buildingId);
			String[] filePaths = building.getConstructionDrawings().split(",");
			String filePath = "";
			for (String path : filePaths) {
				if (path.endsWith(fileName)) {
					filePath = path;
				}
			}
			File file = new File(filePath);
			if (file.exists()) {
				String encoderFileName = null;
				try {
					encoderFileName = URLEncoder.encode(fileName, "UTF-8");// 中文编码
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
					throw new AppModuleErrorException("系统错误，" + e1.getMessage());
				}
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition", "attachment;fileName=" + encoderFileName);// 设置文件名
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new AppModuleErrorException("系统错误，" + e.getMessage());
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
							throw new AppModuleErrorException("系统错误，" + e.getMessage());
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
							throw new AppModuleErrorException("系统错误，" + e.getMessage());
						}
					}
				}
			}
		}
		// 日志
		JSONObject json = new JSONObject();
		json.put("fileName", fileName);
		IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		logService.writeSysUserLog(IHouseManageService.OPER_MODULE, "下载", IHouseManageService.OPER_TARGET_BUILDING,
				buildingId, json);
	}

	/**
	 * 
	 * <文件上传>
	 * 
	 * @param files
	 *            需要上传的文件
	 * @param fileType
	 *            上传文件的类型：pic,file
	 * @see [类、类#方法、类#成员]
	 */
	private String uploadFile(MultipartFile[] files, String fileType) {
		String filePaths = "";
		StringBuffer sb = new StringBuffer(filePaths);
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					try {
						String fileName = file.getOriginalFilename();
						int hashcode = Math.abs(fileName.hashCode());
						// 系统存储文件路径
						String fileUrl = ServiceModuleFactory.getFramework()
								.getSetting(IUnicornFrame.RES_UPLOAD_PRIVATE);
						String fileDir = "";
						if (fileType.equals("pic")) {// 图片
							fileDir = fileUrl + "building/pics" + hashcode % 256 + "/";
							fileName = fileName.substring(0, fileName.lastIndexOf("."));
						}
						if (fileType.equals("file")) {// 建筑图纸
							fileDir = fileUrl + "building/files" + hashcode % 256 + "/";
						}
						File newFile = new File(fileDir);
						if (!newFile.exists()) {
							newFile.mkdirs();
						}
						String filePath = fileDir + fileName;
						if (fileType.equals("pic")) {
							if (file.getSize() > 200 * 1024) {
								InputStream is = file.getInputStream();
								BufferedImage bufferedImage = Thumbnails.of(is).scale(1f).outputQuality(1.0f)
										.outputFormat("jpg").asBufferedImage();
								Thumbnails.of(bufferedImage).scale(1f).outputQuality((200 * 1024f) / file.getSize())
										.outputFormat("jpg").toFile(filePath);
								is.close();
								bufferedImage.flush();
							} else {
								InputStream is = file.getInputStream();
								Thumbnails.of(is).scale(1f).outputQuality(file.getSize() / (200 * 1024f))
										.outputFormat("jpg").toFile(filePath);
								is.close();
							}
						} else if (fileType.equals("file")) {
							FileOutputStream out = new FileOutputStream(filePath);
							InputStream in = file.getInputStream();
							byte buffer[] = new byte[1024];
							int len = 0;
							while ((len = in.read(buffer)) > 0) {
								out.write(buffer, 0, len);
							}
							in.close();
							out.close();
						}
						if (sb.toString().length() == 0 || sb.toString().equals("")) {
							sb.append(filePath);
						} else {
							sb.append(",").append(filePath);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * <获取楼栋信息>
	 * 
	 * @param street
	 *            街道
	 * @param community
	 *            社区
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/selectbuilding", method = RequestMethod.GET)
	public @ResponseBody List<BuildingInfo> getSelectBuilding(
			@RequestParam(name = "street", required = false) String street,
			@RequestParam(name = "community", required = false) String community,
			@RequestParam(name = "gridId", required = false) Integer gridId) {
		return houseManageServiceImpl.getBaseSelectBuildings(street, community, gridId);
	}

	/**
	 * 
	 * <获取房屋信息>
	 * 
	 * @param buildingId
	 *            楼栋ID
	 * @param unitNumber
	 *            单元号
	 * @param floorNumber
	 *            楼层
	 * @param houseNumber
	 *            房号
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/selecthouse", method = RequestMethod.GET)
	public @ResponseBody Map<String, Map<String, Map<String, HouseInfo>>> getSelectHouse(
			@RequestParam("buildingId") Long buildingId) {
		return houseManageServiceImpl.getHouses(buildingId);
	}

	/**
	 * 
	 * <查询楼栋房屋>
	 * 
	 * @param buildingId
	 *            楼栋ID
	 * @param houseTypes
	 *            需要的房屋类别
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/buildinghouse", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> getBuildingHouseByType(@RequestParam("buildingId") Long buildingId,
			@RequestParam("houseTypes") List<String> houseTypes) {
		return houseManageServiceImpl.getHousesByTypes(buildingId, houseTypes);
	}

	/**
	 * 
	 * <获取房屋总数以及不同类型的房屋数量>
	 * 
	 * @param buildingId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "s/houseNum", method = RequestMethod.GET)
	public @ResponseBody Map<String, Integer> getHouseNumByType() {
		return houseManageServiceImpl.getHouseNumByType();
	}

}
