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
import com.hxts.unicorn.modules.resident.dto.KeyTeenagerDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvKeyTeenager;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.KeyTeenagerInfo;

@Controller
@RequestMapping("/m/resident/teenager")
public class KeyTeenagerController {
	@Autowired
	private ISrvKeyTeenager srvKeyTeenager;
	@Autowired
	private IPersonManageService personManageServiceImpl;
	@Autowired
	private IHouseManageService houseManageService;
	/**
	 * <根据重点青少年id查询重点青少年标签信息>
	 * 
	 * @param  keyTeenagerId 重点青少年id
	 * @return 重点青少年标签信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/view/{keyTeenagerId}",method=RequestMethod.GET) 
	@ResponseBody
	public KeyTeenagerInfo view(@PathVariable Integer keyTeenagerId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		KeyTeenagerInfo keyTeenagerInfo = srvKeyTeenager.selectByKeyTeenagerId(keyTeenagerId);
		String operModule = "人口管理";
		String operKey ="查看";
		String operTarget = "重点青少年";
		JSONObject json = new JSONObject();
		json.put("id", keyTeenagerId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, keyTeenagerId.longValue(), json);
		return keyTeenagerInfo;
	}
	/**
	 * <保存新增或编辑重点青少年标签信息>
	 * 
	 * @param  floatingResidentInfo 重点青少年标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody KeyTeenagerInfo keyTeenagerInfo) {
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "重点青少年";
		if (keyTeenagerInfo != null && keyTeenagerInfo.getKeyTeenagerId() != null) {
			operKey = "修改";
		}else {
			operKey = "新建";
		}
		int saveResult = srvKeyTeenager.save(keyTeenagerInfo);
		Integer keyTeenagerId = keyTeenagerInfo.getKeyTeenagerId();
		JSONObject json = new JSONObject();
		//较长字段截取最大长度200记录日志
		keyTeenagerInfo.setAssistState(keyTeenagerInfo.getAssistState().substring(0,200));
		json.put("keyTeenagerInfo", keyTeenagerInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, keyTeenagerId.longValue(), json);
		return saveResult;
	}
	
	/**
	 * 
	 * <根据重点青少年id删除重点青少年信息>
	 * @param KeyTeenagerId 重点青少年id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/delete/{keyTeenagerId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteByKeyTeenagerId(@PathVariable Integer keyTeenagerId){
		return srvKeyTeenager.deleteByKeyTeenagerId(keyTeenagerId);
	}
	
	/**
	 * <根据关键词查询重点青少年信息列表>
	 * 
	 * @param  teenagerType 人员类型
	 * @param  name 姓名
	 * @param  idNo 身份号码
	 * @param gridId 所属网格id
	 * @param pageNum 当前页 可选 ，默认 1
	 * @param pageSize 每页显示信息条数， 可选， 默认10
	 * @return 重点青少年信息及分页信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody PageInfo<KeyTeenagerDto> getKeyTeenagerList(@RequestParam(required = false) String teenagerType,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String idNo,
			@RequestParam(required = false) Integer gridId,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		if(teenagerType!=null &&teenagerType.isEmpty()){
			teenagerType=null;
		}
		List<Integer> residentBaseIds = getResidentByGridId(gridId);
		PageHelper.startPage(pageNum, pageSize);
		List<KeyTeenagerDto> FloatingResidentList = srvKeyTeenager.getKeyTeenagerListByKeywords(teenagerType,name,idNo,residentBaseIds);
		PageInfo<KeyTeenagerDto> pageInfo = new PageInfo<KeyTeenagerDto>(FloatingResidentList);
		return pageInfo;
	}
	
	/**
	 * 
	 * <重点青少年页面的统计图表，包括人员类型统计>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="stats",method=RequestMethod.GET) 
	@ResponseBody
	public Map<String, Object> stats(){
		Map<String, Object> statsMap = new HashMap<String, Object>();
		List<StatsResult> teenagerTypeStats = srvKeyTeenager.getTeenagerTypeStats();
		statsMap.put("teenagerTypeStats", teenagerTypeStats);
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
