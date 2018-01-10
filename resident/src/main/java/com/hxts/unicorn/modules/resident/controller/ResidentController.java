package com.hxts.unicorn.modules.resident.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.hxts.unicorn.modules.resident.dto.FamilyRelationDto;
import com.hxts.unicorn.modules.resident.dto.ResidentBaseInfoDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvDisabledPeople;
import com.hxts.unicorn.modules.resident.service.ISrvDrug;
import com.hxts.unicorn.modules.resident.service.ISrvEmancipist;
import com.hxts.unicorn.modules.resident.service.ISrvFamilyRelation;
import com.hxts.unicorn.modules.resident.service.ISrvFloatingResident;
import com.hxts.unicorn.modules.resident.service.ISrvForeignerInfo;
import com.hxts.unicorn.modules.resident.service.ISrvHouseholdRegisteredResident;
import com.hxts.unicorn.modules.resident.service.ISrvIllegalPertitioner;
import com.hxts.unicorn.modules.resident.service.ISrvKeyTeenager;
import com.hxts.unicorn.modules.resident.service.ISrvLeftBehindResident;
import com.hxts.unicorn.modules.resident.service.ISrvLivingAloneAged;
import com.hxts.unicorn.modules.resident.service.ISrvRenterInfo;
import com.hxts.unicorn.modules.resident.service.ISrvResidentBaseInfo;
import com.hxts.unicorn.modules.resident.service.ISrvSubsistenceAllowance;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.CurrentUser;
import com.hxts.unicorn.platform.interfaces.DataDictionaryItem;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.DisabledPeopleInfo;
import com.hxts.unicorn.platform.interfaces.biz.DrugInfo;
import com.hxts.unicorn.platform.interfaces.biz.EmancipistInfo;
import com.hxts.unicorn.platform.interfaces.biz.FamilyRelationInfo;
import com.hxts.unicorn.platform.interfaces.biz.FloatingResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.ForeignerResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseholdResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.IllegalPertitionerInfo;
import com.hxts.unicorn.platform.interfaces.biz.KeyTeenagerInfo;
import com.hxts.unicorn.platform.interfaces.biz.LeftBehindResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.LivingAloneAgedInfo;
import com.hxts.unicorn.platform.interfaces.biz.RenterInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentBaseInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.SubsistenceAllowanceInfo;

@Controller
@RequestMapping("/m/resident")
public class ResidentController {
	@Autowired
	private IPersonManageService personManageServiceImpl;
	@Autowired
	private ISrvResidentBaseInfo srvResidentBaseInfo;
	@Autowired
	private ISrvHouseholdRegisteredResident srvHouseholdRegisteredResident;
	@Autowired
	private ISrvFloatingResident srvFloatingResident;
	@Autowired
	private ISrvForeignerInfo srvForeignerInfo;
	@Autowired
	private ISrvLeftBehindResident srvLeftBehindResident;
	@Autowired
	private ISrvRenterInfo srvRenterInfo;
	@Autowired
	private ISrvKeyTeenager srvKeyTeenager;
	@Autowired
	private ISrvSubsistenceAllowance srvSubsistenceAllowance;
	@Autowired
	private ISrvEmancipist srvEmancipist;
	@Autowired
	private ISrvIllegalPertitioner srvIllegalPertitioner;
	@Autowired
	private ISrvDrug srvDrug;
	@Autowired
	private ISrvLivingAloneAged srvLivingAloneAged;
	@Autowired
	private ISrvDisabledPeople srvDisabledPeople;
	@Autowired
	private ISrvFamilyRelation SrvFamilyRelation;
	@Autowired
	private IHouseManageService houseManageService;

	/**
	 * <根据人口基础信息id查询人口基础信息>
	 * 
	 * @param residentBaseId
	 *            人口基础信息id
	 * @return 人口基础信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "base/view/{residentBaseId}", method = RequestMethod.GET)
	@ResponseBody
	public ResidentBaseInfo baseView(@PathVariable Integer residentBaseId) {
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		ResidentBaseInfo residentBaseInfo = srvResidentBaseInfo.selectByResidentBaseId(residentBaseId);
		String operModule = "人口管理";
		String operKey = "查看";
		String operTarget = "人口基础信息";
		JSONObject json = new JSONObject();
		json.put("id", residentBaseId);
		if (residentBaseInfo != null) {
			json.put("姓名", residentBaseInfo.getName());
			json.put("身份证号码", residentBaseInfo.getIdNo());
		}
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, residentBaseId.longValue(), json);
		return residentBaseInfo;
	}

	/**
	 * <保存新增或编辑人口基础信息>
	 * 
	 * @param 人口基础信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "base/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ResidentBaseInfo residentBaseInfo) {
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		CurrentUser currentUser = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser();
		String operModule = "人口管理";
		String operKey = "";
		String operTarget = "人口基础信息";
		if (residentBaseInfo != null && residentBaseInfo.getResidentBaseId() != null) {
			operKey = "修改";
		} else {
			residentBaseInfo.setCreateUserId(currentUser.getUserId());
			operKey = "新建";
		}
		JSONObject json = new JSONObject();
		json.put("residentBaseInfo", residentBaseInfo);
		int saveResult = srvResidentBaseInfo.save(residentBaseInfo);
		Integer residentBaseId = residentBaseInfo.getResidentBaseId();
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, residentBaseId.longValue(), json);
		return saveResult;
	}

	/**
	 * 
	 * <根据人口基础信息id删除人口基础信息>
	 * 
	 * @param residentBaseId
	 *            人口基础信息id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "base/delete/{residentBaseId}", method = RequestMethod.GET)
	@ResponseBody
	public int deleteByResidentId(@PathVariable Integer residentBaseId) {
		return srvResidentBaseInfo.deleteByResidentId(residentBaseId);
	}

	/**
	 * <根据关键词查询实有人口基本信息列表>
	 * 
	 * 
	 * @param name
	 *            姓名
	 * @param idNo
	 *            身份号码
	 * @param contact
	 *            联系方式
	 * @return 人口基本信息列表
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "baselist", method = RequestMethod.GET)
	public @ResponseBody List<ResidentBaseInfo> getResidentListByKeywords(@RequestParam(required = false) String idNo,
			@RequestParam(required = false) String name, @RequestParam(required = false) String contact) {
		List<ResidentBaseInfo> list = personManageServiceImpl.getResidentListByKeywords(idNo, name, contact);
		return list;
	}

	/**
	 * <根据关键词查询实有人口信息列表>
	 * 
	 * @param gridId
	 *            网格id
	 * @param integrity
	 *            信息完整度（0-100）
	 * @param name
	 *            姓名
	 * @param idNo
	 *            身份号码
	 * @param pageNum
	 *            当前页 可选 ，默认 1
	 * @param pageSize
	 *            每页显示信息条数， 可选， 默认10
	 * @return 实有人口信息列表及分页信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "base/list", method = RequestMethod.GET)
	public @ResponseBody PageInfo<ResidentBaseInfoDto> getAllResidentList(
			@RequestParam(required = false) Integer gridId, @RequestParam(required = false) String name,
			@RequestParam(required = false) String idNo, @RequestParam(required = false) BigDecimal integrity,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		List<Integer> residentBaseIds = getResidentByGridId(gridId);
		PageHelper.startPage(pageNum, pageSize);
		List<ResidentBaseInfoDto> residentInfoList = srvResidentBaseInfo.getResidentInfoByKeywords(residentBaseIds,
				name, idNo, integrity);
		PageInfo<ResidentBaseInfoDto> pageInfo = new PageInfo<ResidentBaseInfoDto>(residentInfoList);
		return pageInfo;
	}

	/**
	 * <根据身份号码查询人口基础信息>
	 * 
	 * @param idNo
	 *            身份号码
	 * @return 人口基础信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "base/baseInfo", method = RequestMethod.GET)
	@ResponseBody
	public ResidentBaseInfo getBaseInfoByIdNo(String idNo) {
		return srvResidentBaseInfo.selectByIdNo(idNo);
	}

	/**
	 * <根据人口ID，姓名身份证号查询人口基础信息>
	 * 
	 * @param residentBaseId,idNo,name
	 *            身份号码
	 * @return 人口基础信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "base/baseInfos", method = RequestMethod.GET)
	@ResponseBody
	public List<ResidentBaseInfo> getResidentBaseInfo(@RequestParam(required = false) List<Integer> residentBaseIds,
			@RequestParam(required = false) String idNo, @RequestParam(required = false) String name) {
		return srvResidentBaseInfo.getResidentBaseInfo(residentBaseIds, idNo, name);
	}

	/**
	 * 
	 * <实有人口页面的统计图表，包括居民属性统计、年龄分段统计、学历统计> <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "base/stats", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> baseStats() {
		Map<String, Object> statsMap = new HashMap<String, Object>();
		List<StatsResult> tagStats = srvResidentBaseInfo.getTagStats();
		List<StatsResult> ageStats = srvResidentBaseInfo.getAgeStats();
		List<StatsResult> degreeStats = srvResidentBaseInfo.getDegreeStats();
		statsMap.put("tagStats", tagStats);
		statsMap.put("ageStats", ageStats);
		statsMap.put("degreeStats", degreeStats);
		return statsMap;
	}

	/**
	 * 
	 * <人口管理页面的统计图表，包括居民属性统计、重点青少年类型统计、帮扶人群统计、特殊人群统计、人口数据今日新增修改统计> <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "stats", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> stats() {
		Map<String, Object> statsMap = new HashMap<String, Object>();
		List<StatsResult> tagStats = srvResidentBaseInfo.getTagStats();
		List<StatsResult> assistTypeStats = srvSubsistenceAllowance.getAssistTypeStats();
		List<StatsResult> teenagerTypeStats = srvKeyTeenager.getTeenagerTypeStats();
		List<StatsResult> specialTypeStats = srvDrug.getSpecialTypeStats();
		CurrentUser currentUser = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser();
		Map<String, Integer> dataManagementStats = personManageServiceImpl.getDataUpdateStats(currentUser.getUserId());
		statsMap.put("tagStats", tagStats);
		statsMap.put("assistTypeStats", assistTypeStats);
		statsMap.put("teenagerTypeStats", teenagerTypeStats);
		statsMap.put("specialTypeStats", specialTypeStats);
		statsMap.put("dataManagementStats", dataManagementStats);
		return statsMap;
	}
	
	/**
	 * 
	 * <一张图，人口管理页面的统计图表，包括居民属性统计、重点青少年类型统计、帮扶人群统计、特殊人群统计> <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "statis/focal", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> mapStats() {
		Map<String, Object> statsMap = new HashMap<String, Object>();
		Map<String, Object> statsMap1 = new HashMap<String, Object>();
		Map<String, Object> assistTypeStatsMap = new LinkedHashMap<String, Object>();		
		Map<String, Object> specialTypeStatsMap = new LinkedHashMap<String, Object>();
		Map<String, Object> teenagerTypeStatsMap = new LinkedHashMap<String, Object>();
		List<StatsResult> tagStats = srvResidentBaseInfo.getTagStats();
		int tagTotal = 0;
		for(StatsResult result:tagStats){
			tagTotal = tagTotal + result.getTotal();
		}
		List<StatsResult> assistTypeStats = srvSubsistenceAllowance.getAssistTypeStats();
		List<Integer> assistTotal = srvSubsistenceAllowance.getAssistTypeStatsCount();
		assistTypeStatsMap.put("total", assistTotal.size());
		for(StatsResult result:assistTypeStats){
			assistTypeStatsMap.put(result.getName(), result.getTotal());
		}
		List<StatsResult> specialTypeStats = srvDrug.getSpecialTypeStats();
		List<Integer> specialTotal = srvDrug.getSpecialTypeStatsCount();
		specialTypeStatsMap.put("total", specialTotal.size());
		for(StatsResult result:specialTypeStats){
			specialTypeStatsMap.put(result.getName(), result.getTotal());
		}
		List<StatsResult> teenagerTypeStats = srvKeyTeenager.getTeenagerTypeStats();
		int teenagerTotal = 0;
		for(StatsResult result:teenagerTypeStats){
			teenagerTotal = teenagerTotal+result.getTotal();
		}
		teenagerTypeStatsMap.put("total", teenagerTotal);
		for(StatsResult result:teenagerTypeStats){
			teenagerTypeStatsMap.put(result.getName(), result.getTotal());
		}
		statsMap1.put("帮扶人群", assistTypeStatsMap);
		statsMap1.put("特殊人群", specialTypeStatsMap);
		statsMap1.put("重点青少年", teenagerTypeStatsMap);
		statsMap.put("total", tagTotal);
		statsMap.put("focal", assistTotal.size()+specialTotal.size()+teenagerTotal);
		statsMap.put("detail",statsMap1);
		return statsMap;
	}
	
	
	/**
	 * 
	 * <一张图，根据标签类型和人员类型获取人口信息列表> <功能详细描述>
	 * 
	 * @param type  人员类型（统计接口输出的人员类型）
	 * @return 人口信息列表
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "peopleInfoList", method = RequestMethod.GET)
	@ResponseBody
	public List<ResidentBaseInfoDto> getPeopleInfoList(@RequestParam(value = "type", required = false) String type) {
		List<ResidentBaseInfoDto> infos = new ArrayList<ResidentBaseInfoDto>();
		infos = srvResidentBaseInfo.getPeopleInfoList(type);
		return infos;
	}

	/**
	 * 
	 * <访问人口图像> <功能详细描述>
	 * 
	 * @param residentBaseId
	 * @param response
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "base/picture/{residentBaseId}", method = RequestMethod.GET)
	public void picture(@PathVariable Integer residentBaseId, HttpServletResponse response) {
		ResidentInfo residentInfo = personManageServiceImpl.getResidentInfo(IPersonManageService.LABEL_BASE,
				residentBaseId, null, false);
		if (residentInfo != null) {
			ResidentBaseInfo residentBaseInfo = residentInfo.getB();
			if (residentBaseInfo != null) {
				String imgPath = residentBaseInfo.getPicture();
				try {
					File file = new File(imgPath);
					if (file.exists()) {
						FileInputStream fis = new FileInputStream(file);
						response.setContentType("image/*");
						OutputStream out = response.getOutputStream();
						// 读取文件流
						int len = 0;
						byte[] buffer = new byte[1024 * 10];
						while ((len = fis.read(buffer)) != -1) {
							out.write(buffer, 0, len);
						}
						fis.close();
						out.flush();
						out.close();
					} else {
						throw new AppModuleErrorException("图片不存在！");
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					throw new AppModuleErrorException(e.getMessage());
				}
			}
		}
	}

	/**
	 * <根据人口基本信息id查询家庭关系列表>
	 * 
	 * @param residentBaseId
	 *            人口基础信息id
	 * @return 家庭关系列表
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "relationlist/{residentBaseId}", method = RequestMethod.GET)
	@ResponseBody
	public List<FamilyRelationDto> getFamilyRelationList(@PathVariable Integer residentBaseId) {
		List<FamilyRelationDto> relatinInfo = SrvFamilyRelation.getFamilyRelation(residentBaseId);
		return relatinInfo;
	}

	/**
	 * <根据人口基本信息id查询房屋信息列表列表>
	 * 
	 * @param residentBaseId
	 *            人口基础信息id
	 * @return 房屋信息列表
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "houseinfolist/{residentBaseId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ResidentOfHouseInfo> getHouseInfoList(@PathVariable Integer residentBaseId) {
		List<Integer> residentIds = new ArrayList<Integer>();
		residentIds.add(residentBaseId);
		Map<Integer, List<ResidentOfHouseInfo>> map = houseManageService.getResidentOfHouseInfos(residentIds);
		List<ResidentOfHouseInfo> residentOfHouseInfo = map.get(residentBaseId);
		return residentOfHouseInfo;
	}

	/**
	 * <根据身份证号码查询人员关系列表>
	 * 
	 * @param idNo
	 *            身份证号码
	 * @return 人员关系列表
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "relationlists/{idNo}", method = RequestMethod.GET)
	@ResponseBody
	public List<FamilyRelationDto> getFamilyRelationLists(@PathVariable String idNo) {
		List<FamilyRelationDto> relatinInfo = SrvFamilyRelation.getFamilyRelations(idNo);
		return relatinInfo;
	}

	/**
	 * <查询人口信息>
	 * 
	 * @param residentBaseId
	 *            人口基础信息id
	 * @param isReturnLabel
	 *            是否返回标签属性信息
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	@ResponseBody
	public ResidentInfo view(@RequestParam(value = "idNo", required = false) String idNo,
			@RequestParam(value = "residentBaseId", required = false) Integer residentBaseId,
			@RequestParam(value = "isReturnLabel", required = false) Boolean isReturnLabel) {
		if (isReturnLabel == null) {
			isReturnLabel = false;
		}
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		ResidentInfo residentInfo = personManageServiceImpl.getResidentInfo(IPersonManageService.LABEL_BASE,
				residentBaseId, idNo, isReturnLabel);
		String operModule = "人口管理";
		String operKey = "查看";
		String operTarget = "人口信息";
		JSONObject json = new JSONObject();
		json.put("id", residentBaseId);
		json.put("idNo", idNo);
		long targetKey = 0;
		if (residentBaseId != null) {
			targetKey = residentBaseId.longValue();
		}
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, targetKey, json);
		return residentInfo;
	}

	/**
	 * <保存新增或编辑人口信息>
	 * 
	 * @param file
	 *            上传图片（原图）
	 * @param x
	 *            裁剪区域起始横坐标(原图)
	 * @param y
	 *            裁剪区域起始纵坐标（原图）
	 * @param width
	 *            裁剪区域宽度（原图）
	 * @param height
	 *            裁剪区域高度（原图）
	 * @param residentInfo
	 *            人口信息
	 * @return residentBaseId
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public int saveResidentInfo(@RequestParam(value = "picBaseStr", required = false) String picBaseStr,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "x", required = false) Integer x,
			@RequestParam(value = "y", required = false) Integer y,
			@RequestParam(value = "width", required = false) Integer width,
			@RequestParam(value = "height", required = false) Integer height, @Valid ResidentInfo residentInfo,
			BindingResult result) {
		String errorStr = "";
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			for (ObjectError error : errors) {
				errorStr = errorStr + "," + error.getDefaultMessage();
			}
			throw new AppModuleErrorException(errorStr);
		}
		ResidentBaseInfo residentBaseInfo = residentInfo.getB();
		HouseholdResidentInfo householdResidentInfo = residentInfo.getH();
		FloatingResidentInfo floatingResidentInfo = residentInfo.getF();
		ForeignerResidentInfo foreignerResidentInfo = residentInfo.getFo();
		LeftBehindResidentInfo leftBehindResidentInfo = residentInfo.getLb();
		RenterInfo renterInfo = residentInfo.getR();
		KeyTeenagerInfo keyTeenagerInfo = residentInfo.getK();
		SubsistenceAllowanceInfo subsistenceAllowanceInfo = residentInfo.getS();
		LivingAloneAgedInfo livingAloneAgedInfo = residentInfo.getL();
		DisabledPeopleInfo disabledPeopleInfo = residentInfo.getD();
		DrugInfo drugInfo = residentInfo.getDr();
		EmancipistInfo emancipistInfo = residentInfo.getE();
		IllegalPertitionerInfo illegalPertitionerInfo = residentInfo.getI();
		List<FamilyRelationInfo> relations = residentInfo.getRelations();
		List<ResidentOfHouseInfo> houses = residentInfo.getHouses();
		Integer residentBaseId = null;

		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		// CurrentUser currentUser =
		// ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser();
		String operModule = "人口管理";
		String operKey = "";
		String operTarget = "人口基础信息";

		JSONObject json = new JSONObject();
//		json.put("residentBaseInfo", residentBaseInfo);
		if (residentBaseInfo != null) {
			if(residentBaseInfo.getResidentBaseId() == null && residentBaseInfo.getIdNo() != null){
				ResidentBaseInfo baseInfo = srvResidentBaseInfo.selectByIdNo(residentBaseInfo.getIdNo());
				if(baseInfo != null){
					throw new AppModuleErrorException("该身份证号码的人口信息已存在，请勿重复提交！");
				}
			}
			// 图片剪切、压缩、上传
			if (picBaseStr != null && !picBaseStr.isEmpty()) {
				picBaseStr = picBaseStr.substring(22);// 去除开头data:image/jpg;base64,声明数据协议及类型名称及编码形式
				byte[] bytes = Base64.decodeBase64(picBaseStr);
				InputStream sbs = new ByteArrayInputStream(bytes);
				// 新的图片文件名 = 获取时间戳+"."图片扩展名
				String newFileName = String.valueOf(System.currentTimeMillis());
				int hashcode = Math.abs(newFileName.hashCode());
				String residentPictureUrl = ServiceModuleFactory.getFramework().getSetting("upload.private");
				String imgDir = residentPictureUrl + "resident/idno/" + hashcode % 256 + "/";
				File imgFile = new File(imgDir);
				if (!imgFile.exists()) {// 如果不存在此目录就新建目录
					imgFile.mkdirs();
				}
				String imgPath = imgDir + newFileName;
				try {
					// if(file.getSize() > 200*1024){
					// //剪切
					// //BufferedImage bufferedImage =
					// Thumbnails.of(file.getInputStream()).sourceRegion(x,
					// y,width,height).size(width,height).keepAspectRatio(false).outputQuality(1.0f).outputFormat("jpg").asBufferedImage();
					// //压缩
					// Thumbnails.of(sbs).scale(1f).outputQuality((200*1024f)
					// /file.getSize()).outputFormat("jpg").toFile(imgPath);
					// }else{
					// 剪切
					// Thumbnails.of(file.getInputStream()).sourceRegion(x,
					// y,width,height).size(width,height).keepAspectRatio(false).outputQuality(1.0f).outputFormat("jpg").toFile(imgPath);
					Thumbnails.of(sbs).scale(1f).outputQuality(1.0).outputFormat("jpg").toFile(imgPath);
					// }
				} catch (IOException ioex) {
					ioex.printStackTrace();
				}
				residentBaseInfo.setPicture(imgPath + ".jpg");
			}
			if (file != null && file.getSize() > 0) {
				// 获取图片的文件名
				// String fileName = file.getOriginalFilename();
				// 获取图片的扩展名
				// String extensionName =
				// fileName.substring(fileName.lastIndexOf(".") + 1);
				// 新的图片文件名 = 获取时间戳+"."图片扩展名
				// String newFileName =
				// String.valueOf(System.currentTimeMillis()) + "." +
				// extensionName;
				String newFileName = String.valueOf(System.currentTimeMillis());
				int hashcode = Math.abs(newFileName.hashCode());
				String residentPictureUrl = ServiceModuleFactory.getFramework().getSetting("upload.private");
				String imgDir = residentPictureUrl + "resident/idno/" + hashcode % 256 + "/";
				File imgFile = new File(imgDir);
				if (!imgFile.exists()) {// 如果不存在此目录就新建目录
					imgFile.mkdirs();
				}
				String imgPath = imgDir + newFileName;
				try {
					if (file.getSize() > 200 * 1024) {
						// 剪切
						// BufferedImage bufferedImage =
						// Thumbnails.of(file.getInputStream()).sourceRegion(x,
						// y,width,height).size(width,height).keepAspectRatio(false).outputQuality(1.0f).outputFormat("jpg").asBufferedImage();
						// 压缩
						Thumbnails.of(file.getInputStream()).scale(1f).outputQuality((200 * 1024f) / file.getSize())
								.outputFormat("jpg").toFile(imgPath);
					} else {
						// 剪切
						// Thumbnails.of(file.getInputStream()).sourceRegion(x,
						// y,width,height).size(width,height).keepAspectRatio(false).outputQuality(1.0f).outputFormat("jpg").toFile(imgPath);
						Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(1.0).outputFormat("jpg")
								.toFile(imgPath);
					}
				} catch (IOException ioex) {
					ioex.printStackTrace();
				}
				residentBaseInfo.setPicture(imgPath + ".jpg");
			}
//			srvResidentBaseInfo.selectByIdNo(residentBaseInfo.getIdNo())!=null
			if (residentBaseInfo != null && residentBaseInfo.getResidentBaseId() != null) {
				operKey = "编辑";
			} else {
				// residentBaseInfo.setCreateUserId(currentUser.getUserId());
				operKey = "新建";
			}
			if (foreignerResidentInfo != null) {
				ResidentInfo labelInfo = null;
				if (foreignerResidentInfo.getResidentBaseId() == null) {
					labelInfo = personManageServiceImpl.getResidentInfo(IPersonManageService.LABEL_BASE,
							residentBaseInfo.getResidentBaseId(), residentBaseInfo.getIdNo(), false);
				} else {
					labelInfo = personManageServiceImpl.getResidentInfo(IPersonManageService.LABEL_BASE,
							foreignerResidentInfo.getResidentBaseId(), residentBaseInfo.getIdNo(), false);
				}
				if (labelInfo != null) {
					Map<String, Integer> list = labelInfo.getLabels();
					if (list != null && list.get("household") != null) {
						throw new AppModuleErrorException("此人口已添加户籍人口标签信息！");
					}
					if (list != null && list.get("floating") != null) {
						throw new AppModuleErrorException("此人口已添加流动人口标签信息！");
					}
				}

				srvResidentBaseInfo.save(residentBaseInfo, foreignerResidentInfo);				
			} else {
				srvResidentBaseInfo.save(residentBaseInfo);				
			}
			residentBaseId = residentBaseInfo.getResidentBaseId();
		}
		if (householdResidentInfo != null) {
			if (residentBaseInfo != null) {
				householdResidentInfo.setResidentBaseId(residentBaseInfo.getResidentBaseId());
				residentBaseId = residentBaseInfo.getResidentBaseId();
			} else {
				if (householdResidentInfo.getResidentBaseId() == null) {
					throw new AppModuleErrorException("户籍人口标签信息添加异常！");
				}
			}
			ResidentInfo labelInfo = personManageServiceImpl.getResidentInfo(IPersonManageService.LABEL_BASE,
					householdResidentInfo.getResidentBaseId(), residentBaseInfo.getIdNo(), false);
			if (labelInfo != null) {
				Map<String, Integer> list = labelInfo.getLabels();
				if (list != null && list.get("floating") != null) {
					throw new AppModuleErrorException("此人口已添加流动人口标签信息！");
				}
				if (list != null && list.get("foreigner") != null) {
					throw new AppModuleErrorException("此人口已添加境外人口标签信息！");
				}
			}
			srvHouseholdRegisteredResident.save(householdResidentInfo);
			
		}
		if (floatingResidentInfo != null) {
			if (residentBaseInfo != null) {
				floatingResidentInfo.setResidentBaseId(residentBaseInfo.getResidentBaseId());
				residentBaseId = residentBaseInfo.getResidentBaseId();
			} else {
				if (floatingResidentInfo.getResidentBaseId() == null) {
					throw new AppModuleErrorException("流动人口标签信息添加异常！");
				}
			}
			ResidentInfo labelInfo = personManageServiceImpl.getResidentInfo(IPersonManageService.LABEL_BASE,
					floatingResidentInfo.getResidentBaseId(), residentBaseInfo.getIdNo(), false);
			if (labelInfo != null) {
				Map<String, Integer> list = labelInfo.getLabels();
				if (list != null && list.get("household") != null) {
					throw new AppModuleErrorException("此人口已添加户籍人口标签信息！");
				}
				if (list != null && list.get("foreigner") != null) {
					throw new AppModuleErrorException("此人口已添加境外人口标签信息！");
				}
			}
			srvFloatingResident.save(floatingResidentInfo);
			
		}
		if (leftBehindResidentInfo != null) {
			if (residentBaseInfo != null) {
				leftBehindResidentInfo.setResidentBaseId(residentBaseInfo.getResidentBaseId());
				residentBaseId = residentBaseInfo.getResidentBaseId();
			} else {
				if (leftBehindResidentInfo.getResidentBaseId() == null) {
					throw new AppModuleErrorException("留守人口标签信息添加异常！");
				}
			}
			srvLeftBehindResident.save(leftBehindResidentInfo);
			
		}
		if (renterInfo != null) {
			if (residentBaseInfo != null) {
				renterInfo.setResidentBaseId(residentBaseInfo.getResidentBaseId());
				residentBaseId = residentBaseInfo.getResidentBaseId();
			} else {
				if (renterInfo.getResidentBaseId() == null) {
					throw new AppModuleErrorException("出租房承租人标签信息添加异常！");
				}
			}
			srvRenterInfo.save(renterInfo);
			
		}
		if (keyTeenagerInfo != null) {
			if (residentBaseInfo != null) {
				keyTeenagerInfo.setResidentBaseId(residentBaseInfo.getResidentBaseId());
				residentBaseId = residentBaseInfo.getResidentBaseId();
			} else {
				if (keyTeenagerInfo.getResidentBaseId() == null) {
					throw new AppModuleErrorException("重点青少年标签信息添加异常！");
				}
			}
			srvKeyTeenager.save(keyTeenagerInfo);
			
		}
		if (subsistenceAllowanceInfo != null) {
			if (residentBaseInfo != null) {
				subsistenceAllowanceInfo.setResidentBaseId(residentBaseInfo.getResidentBaseId());
				residentBaseId = residentBaseInfo.getResidentBaseId();
			} else {
				if (subsistenceAllowanceInfo.getResidentBaseId() == null) {
					throw new AppModuleErrorException("低保人群标签信息添加异常！");
				}
			}
			srvSubsistenceAllowance.save(subsistenceAllowanceInfo);
			
		}
		if (livingAloneAgedInfo != null) {
			if (residentBaseInfo != null) {
				livingAloneAgedInfo.setResidentBaseId(residentBaseInfo.getResidentBaseId());
				residentBaseId = residentBaseInfo.getResidentBaseId();
			} else {
				if (livingAloneAgedInfo.getResidentBaseId() == null) {
					throw new AppModuleErrorException("独居老人标签信息添加异常！");
				}
			}
			srvLivingAloneAged.save(livingAloneAgedInfo);
			
		}
		if (disabledPeopleInfo != null) {
			if (residentBaseInfo != null) {
				disabledPeopleInfo.setResidentBaseId(residentBaseInfo.getResidentBaseId());
				residentBaseId = residentBaseInfo.getResidentBaseId();
			} else {
				if (disabledPeopleInfo.getResidentBaseId() == null) {
					throw new AppModuleErrorException("残障人士标签信息添加异常！");
				}
			}
			srvDisabledPeople.save(disabledPeopleInfo);
			
		}
		if (drugInfo != null) {
			if (residentBaseInfo != null) {
				drugInfo.setResidentBaseId(residentBaseInfo.getResidentBaseId());
				residentBaseId = residentBaseInfo.getResidentBaseId();
			} else {
				if (drugInfo.getResidentBaseId() == null) {
					throw new AppModuleErrorException("吸毒人员标签信息添加异常！");
				}
			}
			srvDrug.save(drugInfo);
			
		}
		if (emancipistInfo != null) {
			if (residentBaseInfo != null) {
				emancipistInfo.setResidentBaseId(residentBaseInfo.getResidentBaseId());
				residentBaseId = residentBaseInfo.getResidentBaseId();
			} else {
				if (emancipistInfo.getResidentBaseId() == null) {
					throw new AppModuleErrorException("刑满释放人员标签信息添加异常！");
				}
			}
			srvEmancipist.save(emancipistInfo);
			
		}
		if (illegalPertitionerInfo != null) {
			if (residentBaseInfo != null) {
				illegalPertitionerInfo.setResidentBaseId(residentBaseInfo.getResidentBaseId());
				residentBaseId = residentBaseInfo.getResidentBaseId();
			} else {
				if (illegalPertitionerInfo.getResidentBaseId() == null) {
					throw new AppModuleErrorException("非法上访标签信息添加异常！");
				}
			}
			srvIllegalPertitioner.save(illegalPertitionerInfo);
			logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, residentBaseId.longValue(), json);
		}
		if (relations != null) {
			for (FamilyRelationInfo familyRelation : relations) {
				familyRelation.setSelfId(residentBaseId);
				SrvFamilyRelation.setFamilyRelation(familyRelation);
			}
		}
		if (houses != null) {
			for (ResidentOfHouseInfo residentOfHouseInfo : houses) {
				houseManageService.associate(residentOfHouseInfo.getHouseId(), residentBaseId,
						residentOfHouseInfo.getResidentType(), null, null);
			}
		}
		if (residentBaseId == null) {
			throw new AppModuleErrorException("人口基础信息Id不能为空！");
		}
		json.put("id", residentBaseId);
		json.put("idNo",residentBaseInfo.getIdNo());
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, residentBaseId.longValue(), json);
		return residentBaseId;
	}

	/**
	 * 
	 * <根据姓名模糊查询> <功能详细描述>
	 * 
	 * @param name
	 *            姓名
	 * @return 姓名 ,人口基本信息id列表
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "nameView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<Integer>> getResidentInfoByName(
			@RequestParam(value = "name", required = false) String name) {
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		Map<String, List<Integer>> infos = personManageServiceImpl.getResidentInfoByName(name);
		String operModule = "人口管理";
		String operKey = "查询";
		String operTarget = "人口信息";
		JSONObject json = new JSONObject();
		Integer targetKey = 0;
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, targetKey.longValue(), json);
		return infos;
	}

	/**
	 * 
	 * <获取重点人员或帮扶人员所在房屋名称> <功能详细描述>
	 * 
	 * @param type
	 *            人群类型 帮扶人群、重点人员,已定义常量
	 * @return 房屋名称列表
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "houseNameView", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getSpecialsHouseNames(@RequestParam(value = "type", required = false) String type) {
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		List<String> infos = personManageServiceImpl.getSpecialsHouseNames(type);
		String operModule = "人口管理";
		String operKey = "查询";
		String operTarget = "房屋名称";
		JSONObject json = new JSONObject();
		Integer targetKey = 0;
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, targetKey.longValue(), json);
		return infos;
	}

	/**
	 * 返回行政区域结构，分层结构
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "region", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getRegion() {
		class LayeredDict {
			public String name;
			public Map<String, Object> child;

			public LayeredDict(String name) {
				this.name = name;
				child = new TreeMap<String, Object>();
			}
		}
		List<DataDictionaryItem> dicts = personManageServiceImpl.getDataDictionary("行政区划");

		Map<String, Object> result = new TreeMap<String, Object>();
		java.util.Stack<Map<String, Object>> stack = new java.util.Stack<Map<String, Object>>();
		stack.push(result);

		int maxLayer = 3;
		int currLayer = 0;
		String currKey = "";
		if (!dicts.isEmpty()) {
			maxLayer = dicts.get(0).dataCode.length() / 2;
		}
		for (DataDictionaryItem item : dicts) {
			String key = item.dataCode;
			if (currKey.isEmpty()) { // 第一层
				currKey = key.substring(0, 2);
				currLayer = 1;
				LayeredDict newDict = new LayeredDict(item.dataName);
				result.put(key, newDict);
				stack.push(newDict.child);
				continue;
			}

			while (currLayer > 0 && !key.startsWith(currKey)) { // 层次后退
				currKey = currKey.substring(0, currKey.length() - 2);
				currLayer--;
				stack.pop();
			}
			if (key.startsWith(currKey)) {
				Map<String, Object> currMap = stack.peek();
				LayeredDict newDict = new LayeredDict(item.dataName);
				currMap.put(key, newDict);
				if (currLayer < maxLayer - 1) {
					stack.push(newDict.child);
					currKey = key.substring(0, currKey.length() + 2);
					currLayer++;
				}
			}

		}
		return result;
	}

	private List<Integer> getResidentByGridId(Integer gridId) {
		List<Integer> gridIds = new ArrayList<Integer>();
		List<Integer> residentBaseIds = new ArrayList<Integer>();
		if (gridId == null) {
			IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager()
					.getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
		} else {
			gridIds.add(gridId);
		}
		residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, null);
		return residentBaseIds;
	}

}
