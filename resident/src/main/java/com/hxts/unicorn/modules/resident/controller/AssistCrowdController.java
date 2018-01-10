package com.hxts.unicorn.modules.resident.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.hxts.unicorn.modules.resident.dto.AssistCrowdDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvDisabledPeople;
import com.hxts.unicorn.modules.resident.service.ISrvLivingAloneAged;
import com.hxts.unicorn.modules.resident.service.ISrvResidentBaseInfo;
import com.hxts.unicorn.modules.resident.service.ISrvSubsistenceAllowance;
import com.hxts.unicorn.platform.interfaces.IUnicornFrame;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.DisabledPeopleInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.LivingAloneAgedInfo;
import com.hxts.unicorn.platform.interfaces.biz.SubsistenceAllowanceInfo;
import com.hxts.unicorn.platform.interfaces.biz.routineJob.IRoutineJobService;

@Controller
@RequestMapping("/m/resident/assist")
public class AssistCrowdController {
	@Autowired
	private ISrvResidentBaseInfo srvResidentBaseInfo;
	@Autowired
	private ISrvSubsistenceAllowance srvSubsistenceAllowance;
	@Autowired
	private ISrvLivingAloneAged srvLivingAloneAged;
	@Autowired
	private ISrvDisabledPeople srvDisabledPeople;
	@Autowired
	private IPersonManageService personManageServiceImpl;
	@Autowired
	private IRoutineJobService routineJobServiceImpl;
	@Autowired
	private IHouseManageService houseManageService;
	
	/**
	 * 
	 * <帮扶人群页面的统计图表，包括帮扶人员类型统计>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="stats",method=RequestMethod.GET) 
	@ResponseBody
	public Map<String, Object> stats(){
		Map<String, Object> statsMap = new HashMap<String, Object>();
		List<StatsResult> assistTypeStats = srvSubsistenceAllowance.getAssistTypeStats();
		int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
		Date date=new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat fy = new SimpleDateFormat("yyyy");
		String startDate = fy.format(date)+"01";
		String endDate = f.format(date);
		Map<String,Integer> assistTimesStats = routineJobServiceImpl.getAssistStats(startDate, endDate, userId);
		statsMap.put("assistTypeStats", assistTypeStats);
		statsMap.put("assistTimesStats", assistTimesStats);
		return statsMap; 
	}
	
	/**
	 * <根据关键词查询帮扶人群信息列表>
	 * 
	 * @param  healthCondition 健康状况
	 * @param assistType 帮扶类型
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param pageNum 当前页 可选 ，默认 1
	 * @param pageSize 每页显示信息条数， 可选， 默认10
	 * @return 户籍人口信息及分页信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody PageInfo<AssistCrowdDto> getAssistCrowdList(@RequestParam(required = false) String healthCondition,
			@RequestParam(required = false) String assistType,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String idNo,
			@RequestParam(required = false) Integer gridId,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		if(healthCondition!=null &&healthCondition.isEmpty()){
			healthCondition=null;
		}
		if(assistType!=null &&assistType.isEmpty()){
			assistType=null;
		}
		List<Integer> residentBaseIds = getResidentByGridId(gridId);
		PageHelper.startPage(pageNum, pageSize);
		List<AssistCrowdDto> householdRegisterList = srvSubsistenceAllowance.getAssistCrowdListByKeywords(healthCondition,assistType,name,idNo,residentBaseIds);
		PageInfo<AssistCrowdDto> pageInfo = new PageInfo<AssistCrowdDto>(householdRegisterList);
		return pageInfo;
	}
	
	/*低保人员**********************************************************************/
	/**
	 * <根据低保人员信息id查询低保人员信息>
	 * 
	 * @param  subsistAllowanceId 低保人员信息id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/subAllow/view/{subsistAllowanceId}",method=RequestMethod.GET) 
	@ResponseBody
	public SubsistenceAllowanceInfo viewSubsistAllowance(@PathVariable Integer subsistAllowanceId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		SubsistenceAllowanceInfo subsistenceAllowanceInfo = srvSubsistenceAllowance.selectBySubsistAllowanceId(subsistAllowanceId);
		String operModule = "人口管理";
		String operKey = IUnicornFrame.ACT_VIEW[1];
		String operTarget = "低保人员";
		JSONObject json = new JSONObject();
		json.put("id", subsistAllowanceId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, subsistAllowanceId.longValue(), json);
		return subsistenceAllowanceInfo;
	}
	
	/**
	 * <保存新增或编辑低保人员信息>
	 * 
	 * @param  低保人员信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/subAllow/save",method=RequestMethod.POST)
	@ResponseBody
	public int saveSubsistenceAllowance(@RequestBody SubsistenceAllowanceInfo subsistenceAllowanceInfo){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "低保人员";
		if (subsistenceAllowanceInfo != null && subsistenceAllowanceInfo.getSubsistAllowanceId() != null) {
			operKey = IUnicornFrame.ACT_EDIT[1];
		}else {
			operKey = IUnicornFrame.ACT_ADD[1];
		}
		int saveResult = srvSubsistenceAllowance.save(subsistenceAllowanceInfo);
		Integer subsistAllowanceId = subsistenceAllowanceInfo.getSubsistAllowanceId();
		JSONObject json = new JSONObject();
		//较长字段截取最大长度200记录日志
		subsistenceAllowanceInfo.setFamilyEmployStatus(subsistenceAllowanceInfo.getFamilyEmployStatus().substring(0,200));
		subsistenceAllowanceInfo.setFamilyHealthCondition(subsistenceAllowanceInfo.getFamilyHealthCondition().substring(0,200));
		json.put("subsistenceAllowanceInfo", subsistenceAllowanceInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget,subsistAllowanceId.longValue(), json);
		return saveResult;
	}
	
	/**
	 * 
	 * <根据低保人员id删除低保人员信息>
	 * @param subsistAllowanceId 低保人员id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/subAllow/delete/{subsistAllowanceId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteBySubsistAllowanceId(@PathVariable Integer subsistAllowanceId){
		return srvSubsistenceAllowance.deleteBySubsistAllowanceId(subsistAllowanceId);
	}
	
	/*独居老人**********************************************************************/
	/**
	 * <根据独居老人信息id查询独居老人信息>
	 * 
	 * @param  livingAloneAgedId 独居老人信息id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/liveAlone/view/{livingAloneAgedId}",method=RequestMethod.GET) 
	@ResponseBody
	public LivingAloneAgedInfo viewLivingAloneAged(@PathVariable Integer livingAloneAgedId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		LivingAloneAgedInfo livingAloneAgedInfo = srvLivingAloneAged.selectByLivingAloneAgedId(livingAloneAgedId);
		String operModule = "人口管理";
		String operKey = IUnicornFrame.ACT_VIEW[1];
		String operTarget = "独居老人";
		JSONObject json = new JSONObject();
		json.put("id", livingAloneAgedId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, livingAloneAgedId.longValue(), json);
		return livingAloneAgedInfo;
	}
	/**
	 * <保存新增或编辑独居老人信息>
	 * 
	 * @param  独居老人信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/liveAlone/save",method=RequestMethod.POST)
	@ResponseBody
	public int saveLivingAloneAged(@RequestBody LivingAloneAgedInfo livingAloneAgedInfo){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "独居老人";
		if (livingAloneAgedInfo != null && livingAloneAgedInfo.getLivingAloneId() != null) {
			operKey = IUnicornFrame.ACT_EDIT[1];
		}else {
			operKey = IUnicornFrame.ACT_ADD[1];
		}
		int saveResult = srvLivingAloneAged.save(livingAloneAgedInfo);
		Integer livingAloneAgedId = livingAloneAgedInfo.getLivingAloneId();
		JSONObject json = new JSONObject();
        json.put("livingAloneAgedInfo",livingAloneAgedInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, livingAloneAgedId.longValue(), json);
		return saveResult;
	}
	
	/**
	 * 
	 * <根据独居老人id删除独居老人信息>
	 * @param livingAloneAgedId 独居老人id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/liveAlone/delete/{livingAloneAgedId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteByLivingAloneAgedId(@PathVariable Integer livingAloneAgedId){
		return srvLivingAloneAged.deleteByLivingAloneAgedId(livingAloneAgedId);
	}
	
	/*残障人士**********************************************************************/
	/**
	 * <根据残障人士信息id查询残障人士信息>
	 * 
	 * @param  Id 残障人士信息id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/disabled/view/{disabledPeopleId}",method=RequestMethod.GET) 
	@ResponseBody
	public DisabledPeopleInfo viewDisabledPeople(@PathVariable Integer disabledPeopleId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		DisabledPeopleInfo disabledPeopleInfo = srvDisabledPeople.selectByDisabledPeopleId(disabledPeopleId);
		String operModule = "人口管理";
		String operKey =IUnicornFrame.ACT_VIEW[1];
		String operTarget = "残障人士";
		JSONObject json = new JSONObject();
		json.put("id", disabledPeopleId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, disabledPeopleId.longValue(), json);
		return disabledPeopleInfo;
	}
	/**
	 * <保存新增或编辑残障人士信息>
	 * 
	 * @param  残障人士信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/disabled/save",method=RequestMethod.POST)
	@ResponseBody
	public int savedisabledPeople(@RequestBody DisabledPeopleInfo disabledPeopleInfo){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "残障人士";
		if (disabledPeopleInfo != null && disabledPeopleInfo.getDisabledPeopleId() != null) {
			operKey = IUnicornFrame.ACT_EDIT[1];
		}else {
			operKey = IUnicornFrame.ACT_ADD[1];
		}
		int saveResult = srvDisabledPeople.save(disabledPeopleInfo);
		Integer disabledPeopleId = disabledPeopleInfo.getDisabledPeopleId();
		JSONObject json = new JSONObject();
		json.put("disabledPeopleInfo", disabledPeopleInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, disabledPeopleId.longValue(), json);
		return saveResult;
	}
	
	/**
	 * 
	 * <根据残障人士id删除残障人士信息>
	 * @param disabledPeopleId 残障人士id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/disabled/delete/{disabledPeopleId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteByDisabledPeopleId(@PathVariable Integer disabledPeopleId){
		return srvDisabledPeople.deleteByDisabledPeopleId(disabledPeopleId);
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
