package com.hxts.unicorn.modules.resident.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dto.LeftBehindResidentDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvLeftBehindResident;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.LeftBehindResidentInfo;

@Controller
@RequestMapping("/m/resident/leftBehind")
public class LeftBehindResidentController {
	@Autowired
	private ISrvLeftBehindResident srvLeftBehindResident;
	@Autowired
	private IPersonManageService personManageServiceImpl;
	@Autowired
	private IHouseManageService houseManageService;
	/**
	 * <根据留守人口id查询人口信息>
	 * 
	 * @param  leftBehindId 留守人口id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/view/{leftBehindId}",method=RequestMethod.GET) 
	@ResponseBody
	public LeftBehindResidentInfo view(@PathVariable Integer leftBehindId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		LeftBehindResidentInfo leftBehindResidentInfo = srvLeftBehindResident.selectByLeftBehindId(leftBehindId);
		String operModule = "人口管理";
		String operKey ="查看";
		String operTarget = "留守人口";
		JSONObject json = new JSONObject();
		json.put("id", leftBehindId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, leftBehindId.longValue(), json);
		return leftBehindResidentInfo;
	}
	/**
	 * <保存新增或编辑人口信息>
	 * 
	 * @param  residentBaseInfo 人口基础信息
	 * @param  leftBehindResidentInfo 留守人口标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody LeftBehindResidentInfo leftBehindResidentInfo){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "留守人口";
		if (leftBehindResidentInfo != null && leftBehindResidentInfo.getLeftBehindId() != null) {
			operKey = "修改";
		}else {
			operKey = "新建";
		}
		int saveResult = srvLeftBehindResident.save(leftBehindResidentInfo);
		Integer leftBehindId = leftBehindResidentInfo.getLeftBehindId();
		JSONObject json = new JSONObject();
		//较长字段截取最大长度200记录日志
		leftBehindResidentInfo.setDifficultAppeal(leftBehindResidentInfo.getDifficultAppeal().substring(0,200));
		leftBehindResidentInfo.setAssistState(leftBehindResidentInfo.getAssistState().substring(0,200));
		json.put("leftBehindResidentInfo", leftBehindResidentInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, leftBehindId.longValue(), json);
		return saveResult;
	}
	
	/**
	 * 
	 * <根据留守人口id删除留守人口信息>
	 * @param leftBehindId 留守人口id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/delete/{leftBehindId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteByLeftBehindId(@PathVariable Integer leftBehindId){
		return srvLeftBehindResident.deleteByLeftBehindId(leftBehindId);
	}
	/**
	 * <根据关键词查询留守人口信息列表>
	 * 
	 * @param healthCondition 健康状况
	 * @param leftBehindResidentType 留守类型
	 * @param name 姓名
	 * @param idNo  身份号码
	 * @param gridId  所属网格
	 * @param pageNum 当前页 可选 ，默认 1
	 * @param pageSize 每页显示信息条数， 可选， 默认10
	 * @return 留守人口信息及分页信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody PageInfo<LeftBehindResidentDto> getHouseholdRegisterList(@RequestParam(required = false) String healthCondition,
			@RequestParam(required = false) String leftBehindResidentType,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String idNo,
			@RequestParam(required = false) Integer gridId,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		if(healthCondition!=null &&healthCondition.isEmpty()){
			healthCondition=null;
		}
		if(leftBehindResidentType!=null &&leftBehindResidentType.isEmpty()){
			leftBehindResidentType=null;
		}
		List<Integer> residentBaseIds = getResidentByGridId(gridId);
		PageHelper.startPage(pageNum, pageSize);
		List<LeftBehindResidentDto> leftBehindList = srvLeftBehindResident.getLeftBehindListByKeywords(healthCondition,leftBehindResidentType,name,idNo,residentBaseIds);
		PageInfo<LeftBehindResidentDto> pageInfo = new PageInfo<LeftBehindResidentDto>(leftBehindList);
		return pageInfo;
	}
	
	/**
	 * 
	 * <留守人口页面的统计图表，包括留守人员类型分类统计、留守人员分布统计>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="stats",method=RequestMethod.GET) 
	@ResponseBody
	public Map<String, Object> stats(){
		Map<String, Object> statsMap = new HashMap<String, Object>();
		List<StatsResult> leftBehindTypeStats = srvLeftBehindResident.getLeftBehindTypeStats();
		List<StatsResult> gridStats = srvLeftBehindResident.getGridStats();
		statsMap.put("leftBehindTypeStats", leftBehindTypeStats);
		statsMap.put("gridStats", gridStats);
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
