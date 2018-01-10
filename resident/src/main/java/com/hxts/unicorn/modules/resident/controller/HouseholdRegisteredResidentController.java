package com.hxts.unicorn.modules.resident.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dto.HouseholdRegisteredResidentDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvHouseholdRegisteredResident;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseholdResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;

@Controller
@RequestMapping("/m/resident/household")
public class HouseholdRegisteredResidentController {
	@Autowired
	private ISrvHouseholdRegisteredResident srvHouseholdRegisteredResident;
	@Autowired
	private IPersonManageService personManageServiceImpl;
	@Autowired
	private IHouseManageService houseManageService;
	/**
	 * <根据户籍人口id查询人口信息>
	 * 
	 * @param  householdId 户籍人口id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/view/{householdId}",method=RequestMethod.GET) 
	@ResponseBody
	public HouseholdResidentInfo view(@PathVariable Integer householdId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		HouseholdResidentInfo householdResidentInfo = srvHouseholdRegisteredResident.selectByHouseholdId(householdId);
		String operModule = "人口管理";
		String operKey ="查看";
		String operTarget = "户籍人口";
		JSONObject json = new JSONObject();
		json.put("id", householdId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, householdId.longValue(), json);
		return householdResidentInfo;
	}
	
	/**
	 * <根据户号和身份证关键字查询户籍人口信息列表>
	 * 
	 * @param  householdNo 户号
	 * @param  idNo 身份号码
	 * @return 户籍人口信息列表
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/viewlist",method=RequestMethod.GET) 
	@ResponseBody
	public List<HouseholdResidentInfo> viewList(@RequestParam(required = false)String householdNo,@RequestParam(required = false)String idNo){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		List<HouseholdResidentInfo> householdResidentInfoList = srvHouseholdRegisteredResident.selectByKeyWord(householdNo,idNo);
		String operModule = "人口管理";
		String operKey ="查询";
		String operTarget = "户籍人口";
		JSONObject json = new JSONObject(); 
		json.put("householdNo", householdNo);
		json.put("idNo", idNo);
		Integer householdId = 0;
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, householdId.longValue(), json);
		return householdResidentInfoList;
	}
	/**
	 * <保存新增或编辑户籍人口标签信息>
	 * @param  householdResidentInfo 户籍人口标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody @Valid HouseholdResidentInfo householdResidentInfo,BindingResult result){
		if(result.hasErrors()){
			String message="";
			for(ObjectError objectError : result.getAllErrors()){ 
		        message=message+","+objectError.getDefaultMessage();
		      } 
			throw new AppModuleErrorException(message);
		}
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "户籍人口";
		if (householdResidentInfo != null && householdResidentInfo.getHouseholdId() != null) {
			operKey = "修改";
		}else {
			operKey = "新建";
		}
		int saveResult = srvHouseholdRegisteredResident.save(householdResidentInfo);
		Integer householdId = householdResidentInfo.getHouseholdId();
		JSONObject json = new JSONObject();
		json.put("householdResidentInfo", householdResidentInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, householdId.longValue(), json);
		return saveResult;
	}
	
	/**
	 * 
	 * <根据户籍人口id删除户籍人口信息>
	 * @param householdId 户籍人口id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/delete/{householdId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteByHouseholdId(@PathVariable Integer householdId){
		return srvHouseholdRegisteredResident.deleteByHouseholdId(householdId);
	}
	
	/**
	 * <根据关键词查询户籍人口信息列表>
	 * 
	 * @param  uniformityFlag 人户一致标识 
	 * @param integrity 信息完整度（0-100）
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param pageNum 当前页 可选 ，默认 1
	 * @param pageSize 每页显示信息条数， 可选， 默认10
	 * @return 户籍人口信息及分页信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody PageInfo<HouseholdRegisteredResidentDto> getHouseholdRegisterList(@RequestParam(required = false) String uniformityFlag,
			@RequestParam(required = false) BigDecimal integrity,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String idNo,
			@RequestParam(required = false) Integer gridId,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		if(uniformityFlag!=null &&uniformityFlag.isEmpty()){
			uniformityFlag=null;
		}
		List<Integer> residentBaseIds = getResidentByGridId(gridId);
		PageHelper.startPage(pageNum, pageSize);
		List<HouseholdRegisteredResidentDto> householdRegisterList = srvHouseholdRegisteredResident.getHouseholdRegisterListByKeywords(uniformityFlag,integrity,name,idNo,residentBaseIds);
		PageInfo<HouseholdRegisteredResidentDto> pageInfo = new PageInfo<HouseholdRegisteredResidentDto>(householdRegisterList);
		return pageInfo;
	}
	
	/**
	 * 
	 * <户籍人口页面的统计图表，包括年龄分段统计、学历统计>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="stats",method=RequestMethod.GET) 
	@ResponseBody
	public Map<String, Object> stats(){
		Map<String, Object> statsMap = new HashMap<String, Object>();
		List<StatsResult> ageStats = srvHouseholdRegisteredResident.getAgeStats();
		List<StatsResult> degreeStats = srvHouseholdRegisteredResident.getDegreeStats();
		statsMap.put("ageStats", ageStats);
		statsMap.put("degreeStats", degreeStats);
		return statsMap; 
	}
	
	private List<Integer> getResidentByGridId(Integer gridId){
		List<Integer> gridIds = new ArrayList<Integer>();
		List<Integer> residentBaseIds = new ArrayList<Integer>();
		if(gridId==null){
			IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for(GridItem gridItem:gridItems){	
				gridIds.add(gridItem.getGridId());
			}
		}else{
			gridIds.add(gridId);
		}
		residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, null);
		return residentBaseIds;
	}
}
