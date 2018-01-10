package com.hxts.unicorn.modules.resident.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dto.RenterInfoDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvRenterInfo;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.RenterInfo;

@Controller
@RequestMapping("/m/resident/renter")
public class RenterInfoController {
	@Autowired
	private ISrvRenterInfo srvRenterInfo;
	@Autowired
	private IHouseManageService houseManageService;
	/**
	 * 
	 * <根据关键词查询出租屋承租人列表>
	 * <功能详细描述>
	 * @param gridId 网格id
	 * @param name 姓名
	 * @param idNo 身份证号码
	 * @param integrity 信息完整度（0-100）
	 * @param ethnicity 民族
	 * @param pageNum 当前页 可选 ，默认 1
	 * @param pageSize 每页显示信息条数， 可选， 默认10
	 * @return 出租屋承租人分页信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public PageInfo<RenterInfoDto> getRenterList(@RequestParam(required = false) Integer gridId,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String idNo,
			@RequestParam(required = false) BigDecimal integrity,
			@RequestParam(required = false) String ethnicity,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		if(ethnicity!=null &&ethnicity.isEmpty()){
			ethnicity=null;
		}
		List<Integer> residentBaseIds = getResidentByGridId(gridId);
		PageHelper.startPage(pageNum, pageSize);
		List<RenterInfoDto> residentInfoList = srvRenterInfo.getRenterInfoList(residentBaseIds, name, idNo, integrity, ethnicity);
		PageInfo<RenterInfoDto> pageInfo = new PageInfo<RenterInfoDto>(residentInfoList);
		return pageInfo;
	}
	
	/**
	 * 
	 * <保存新增或者编辑的出租屋承租人的信息>
	 * <功能详细描述>
	 * @param residentBaseInfo 人口基本信息r
	 * @param residentOfHouseInfo 出租屋承租人独有的信息
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody RenterInfo renterInfo){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "出租房承租人";
		if (renterInfo != null && renterInfo.getResidentBaseId() != null) {
			operKey = "修改";
		}else {
			operKey = "新建";
		}
		int saveResult = srvRenterInfo.save(renterInfo);
		Long houseId = renterInfo.getHouseId();
		JSONObject json = new JSONObject();
		json.put("renterInfo", renterInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, houseId, json);
		return saveResult;
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
		List<StatsResult> ageStats = srvRenterInfo.getAgeStats();
		statsMap.put("ageStats", ageStats);
	    List<StatsResult> rentHouseNumsStats = srvRenterInfo.getRentHouseNumsStats();
	    statsMap.put("rentHouseNumsStats", rentHouseNumsStats);
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
		residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, "02");
		return residentBaseIds;
	}
}
