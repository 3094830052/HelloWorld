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
import com.hxts.unicorn.modules.resident.dto.FloatingResidentDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvFloatingResident;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.FloatingResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;

@Controller
@RequestMapping("/m/resident/floating")
public class FloatingResidentController {
	@Autowired
	private ISrvFloatingResident srvFloatingResident;
	@Autowired
	private IPersonManageService personManageServiceImpl;
	@Autowired
	private IHouseManageService houseManageService;
	/**
	 * <根据流动人口id查询人口信息>
	 * 
	 * @param  floatingId 流动人口id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/view/{floatingId}",method=RequestMethod.GET) 
	@ResponseBody
	public FloatingResidentInfo view(@PathVariable Integer floatingId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		FloatingResidentInfo floatingResidentInfo = srvFloatingResident.selectByFloatingId(floatingId);
		String operModule = "人口管理";
		String operKey ="查看";
		String operTarget = "流动人口";
		JSONObject json = new JSONObject();
		json.put("id", floatingId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, floatingId.longValue(), json);
		return floatingResidentInfo;
	}
	/**
	 * <保存新增或编辑流动人口标签信息>
	 * 
	 * @param  floatingResidentInfo 流动人口标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody FloatingResidentInfo floatingResidentInfo){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "流动人口";
		if (floatingResidentInfo != null && floatingResidentInfo.getFloatingId() != null) {
			operKey = "修改";
		}else {
			operKey = "新建";
		}
		int saveResult = srvFloatingResident.save(floatingResidentInfo);
		Integer floatingId = floatingResidentInfo.getFloatingId();
		JSONObject json = new JSONObject();
		json.put("floatingResidentInfo", floatingResidentInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, floatingId.longValue(), json);
		return saveResult;
	}
	
	/**
	 * 
	 * <根据流动人口id删除流动人口信息>
	 * @param floatingId
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/delete/{floatingId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteByFloatingId(@PathVariable Integer floatingId){
		return srvFloatingResident.deleteByFloatingId(floatingId);
	}
	
	/**
	 * <根据关键词查询流动人口信息列表>
	 * 
	 * @param  inflowReason 流入原因
	 * @param  isFocusPerson 是否重点关注人员
	 * @param  certificateHandlingType 办证类型
	 * @param  certificateExpireDate 证件到期日期
	 * @param  name 姓名
	 * @param  idNo 身份号码
	 * @param  gridId 所属网格
	 * @param pageNum 当前页 可选 ，默认 1
	 * @param pageSize 每页显示信息条数， 可选， 默认10
	 * @return 流动人口信息及分页信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody PageInfo<FloatingResidentDto> getFloatingResidentList(@RequestParam(required = false) String inflowReason,
			@RequestParam(required = false) String isFocusPerson,
			@RequestParam(required = false) String certificateHandlingType,
			@RequestParam(required = false) String certificateExpireDate,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String idNo,
			@RequestParam(required = false) Integer gridId,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		if(certificateHandlingType!=null &&certificateHandlingType.isEmpty()){
			certificateHandlingType=null;
		}
		if(certificateExpireDate!=null &&certificateExpireDate.isEmpty()){
			certificateExpireDate=null;
		}
		List<Integer> residentBaseIds = getResidentByGridId(gridId);
		PageHelper.startPage(pageNum, pageSize);
		List<FloatingResidentDto> FloatingResidentList = srvFloatingResident.getFloatingResidentListByKeywords(inflowReason,isFocusPerson,certificateHandlingType,certificateExpireDate,name,idNo,residentBaseIds);
		PageInfo<FloatingResidentDto> pageInfo = new PageInfo<FloatingResidentDto>(FloatingResidentList);
		return pageInfo;
	}
	
	/**
	 * 
	 * <流动人口页面的统计图表，包括流入原因统计、证件到期时间统计>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="stats",method=RequestMethod.GET) 
	@ResponseBody
	public Map<String, Object> stats(){
		Map<String, Object> statsMap = new HashMap<String, Object>();
		List<StatsResult> inflowReasonStats = srvFloatingResident.getInflowReasonStats();
		List<StatsResult> certificateExpireStats = srvFloatingResident.getCertificateExpireStats();
		statsMap.put("inflowReasonStats", inflowReasonStats);
		statsMap.put("certificateExpireStats", certificateExpireStats);
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
