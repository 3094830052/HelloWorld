package com.hxts.unicorn.modules.resident.controller;

import java.math.BigDecimal;
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
import com.hxts.unicorn.modules.resident.dto.ForeignerInfoDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvForeignerInfo;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.ForeignerResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;

@Controller
@RequestMapping("/m/resident/foreigner")
public class ForeignerInfoController {
	@Autowired
	private ISrvForeignerInfo srvForeignerInfo;
	@Autowired
	private IPersonManageService personManageServiceImpl;
	@Autowired
	private IHouseManageService houseManageService;
	/**
	 * <根据境外人口id查询境外人口标签信息>
	 * 
	 * @param  foreignerId 境外人口id
	 * @return 境外人口标签信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/view/{foreignerId}",method=RequestMethod.GET) 
	@ResponseBody
	public ForeignerResidentInfo view(@PathVariable Integer foreignerId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		ForeignerResidentInfo foreignerResidentInfo = srvForeignerInfo.selectByForeignerId(foreignerId);
		String operModule = "人口管理";
		String operKey ="查看";
		String operTarget = "流动人口";
		JSONObject json = new JSONObject();
		json.put("id", foreignerId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, foreignerId.longValue(), json);
		return foreignerResidentInfo;
	}
	/**
	 * <保存新增或编辑境外人口标签信息>
	 * 
	 * @param  foreignerResidentInfo 境外人口标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ForeignerResidentInfo foreignerResidentInfo){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "流动人口";
		if (foreignerResidentInfo != null && foreignerResidentInfo.getForeignerId() != null) {
			operKey = "修改";
		}else {
			operKey = "新建";
		}
		int saveResult = srvForeignerInfo.save(foreignerResidentInfo);
		Integer foreignerId = foreignerResidentInfo.getForeignerId();
		JSONObject json = new JSONObject();
		json.put("foreignerResidentInfo", foreignerResidentInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, foreignerId.longValue(), json);
		return saveResult;
	}
	
	/**
	 * 
	 * <根据境外人口id删除境外人口信息>
	 * @param foreignerId 境外人口id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/delete/{foreignerId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteByForeignerId(@PathVariable Integer foreignerId){
		return srvForeignerInfo.deleteByForeignerId(foreignerId);
	}
	
	/**
	 * 
	 * <境外人口页面的统计图表，包括年龄分段统计、来华目的统计>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="stats",method=RequestMethod.GET) 
	@ResponseBody
	public Map<String, Object> stats(){
		Map<String, Object> statsMap = new HashMap<String, Object>();
		List<StatsResult> ageStats = srvForeignerInfo.getAgeStats();
		List<StatsResult> purposeStats = srvForeignerInfo.getPurposeStats();
		statsMap.put("ageStats", ageStats);
		statsMap.put("purposeStats", purposeStats);
		return statsMap;   
	}
	
	/**
	 * <根据关键词查询境外人员信息列表>
	 * 
	 * @param  nationality 国籍(地区)
	 * @param integrity 信息完整度（0-100）
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param pageNum 当前页 可选 ，默认 1
	 * @param pageSize 每页显示信息条数， 可选， 默认10
	 * @return 户籍人口信息及分页信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody PageInfo<ForeignerInfoDto> getForeignerInfoList(@RequestParam(required = false) String nationality,
			@RequestParam(required = false) BigDecimal integrity,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String idNo,
			@RequestParam(required = false) Integer gridId,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		if(nationality!=null &&nationality.isEmpty()){
			nationality=null;
		}
		List<Integer> residentBaseIds = getResidentByGridId(gridId);
		PageHelper.startPage(pageNum, pageSize);
		List<ForeignerInfoDto> foreignerInfoList = srvForeignerInfo.getForeignerInfoListByKeywords(nationality,integrity,name,idNo,residentBaseIds);
		PageInfo<ForeignerInfoDto> pageInfo = new PageInfo<ForeignerInfoDto>(foreignerInfoList);
		return pageInfo;
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
