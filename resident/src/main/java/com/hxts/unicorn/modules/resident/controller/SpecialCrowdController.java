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
import com.hxts.unicorn.modules.resident.dto.SpecialCrowdDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvDrug;
import com.hxts.unicorn.modules.resident.service.ISrvEmancipist;
import com.hxts.unicorn.modules.resident.service.ISrvIllegalPertitioner;
import com.hxts.unicorn.modules.resident.service.ISrvResidentBaseInfo;
import com.hxts.unicorn.platform.interfaces.IUnicornFrame;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.DrugInfo;
import com.hxts.unicorn.platform.interfaces.biz.EmancipistInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.IllegalPertitionerInfo;

@Controller
@RequestMapping("/m/resident/special")
public class SpecialCrowdController {
	@Autowired
	private ISrvResidentBaseInfo srvResidentBaseInfo;
	@Autowired
	private ISrvDrug srvDrug;
	@Autowired
	private ISrvEmancipist srvEmancipist;
	@Autowired
	private ISrvIllegalPertitioner srvIllegalPertitioner;
	@Autowired
	private IPersonManageService personManageServiceImpl;
	@Autowired
	private IHouseManageService houseManageService;
	
	/**
	 * 
	 * <特殊人群页面的统计图表，包括特殊人员类型统计>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="stats",method=RequestMethod.GET) 
	@ResponseBody
	public Map<String, Object> stats(){
		Map<String, Object> statsMap = new HashMap<String, Object>();
		List<StatsResult> specialTypeStats = srvDrug.getSpecialTypeStats();
		statsMap.put("specialTypeStats", specialTypeStats);
		List<StatsResult> gridStats = srvDrug.getGridStats();
		statsMap.put("gridStats", gridStats);
		return statsMap; 
	}
	
	/**
	 * <根据关键词查询特殊人群信息列表>
	 * 
	 * @param  gridId 所属网格
	 * @param assistType 特殊人员类型
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param pageNum 当前页 可选 ，默认 1
	 * @param pageSize 每页显示信息条数， 可选， 默认10
	 * @return 户籍人口信息及分页信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody PageInfo<SpecialCrowdDto> getAssistCrowdList(
			@RequestParam(required = false) String specialType,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String idNo,
			@RequestParam(required = false) Integer gridId,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		if(specialType!=null &&specialType.isEmpty()){
			specialType=null;
		}
		List<Integer> residentBaseIds = getResidentByGridId(gridId);
		PageHelper.startPage(pageNum, pageSize);
		List<SpecialCrowdDto> specialCrowdList = srvDrug.getSpecialCrowdListByKeywords(specialType,name,idNo,residentBaseIds);
		PageInfo<SpecialCrowdDto> pageInfo = new PageInfo<SpecialCrowdDto>(specialCrowdList);
		return pageInfo;
	}
	
	/*吸毒人员**********************************************************************/
	/**
	 * <根据吸毒人员信息id查询吸毒人员信息>
	 * 
	 * @param  drugId 吸毒人员信息id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/drug/view/{drugId}",method=RequestMethod.GET) 
	@ResponseBody
	public DrugInfo viewDrug(@PathVariable Integer drugId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		DrugInfo drugInfo = srvDrug.selectByDrugInfoId(drugId);
		String operModule = "人口管理";
		String operKey = IUnicornFrame.ACT_VIEW[1];
		String operTarget = "吸毒人员";
		JSONObject json = new JSONObject();
		json.put("id", drugId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, drugId.longValue(), json);
		return drugInfo;
	}
	
	/**
	 * <保存新增或编辑吸毒人员信息>
	 * 
	 * @param  吸毒人员信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/drug/save",method=RequestMethod.POST)
	@ResponseBody
	public int saveDrug(@RequestBody DrugInfo drugInfo){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "吸毒人员";
		if (drugInfo != null && drugInfo.getDrugAddictId() != null) {
			operKey = IUnicornFrame.ACT_EDIT[1];
		}else {
			operKey = IUnicornFrame.ACT_ADD[1];
		}
		int saveResult = srvDrug.save(drugInfo);
		Integer drugId = drugInfo.getDrugAddictId();
		JSONObject json = new JSONObject();
		//较长字段截取最大长度200记录日志
		drugInfo.setSupportSituation(drugInfo.getSupportSituation().substring(0,200));
		drugInfo.setCrimeCondition(drugInfo.getCrimeCondition().substring(0,200));
		json.put("drugInfo", drugInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget,drugId.longValue(), json);
		return saveResult;
	}
	
	/**
	 * 
	 * <根据吸毒人员id删除吸毒人员信息>
	 * @param drugId 吸毒人员id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/drug/delete/{drugId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteByDrugId(@PathVariable Integer drugId){
		return srvDrug.deleteByDrugInfoId(drugId);
	}
	
	/*刑满释放人员**********************************************************************/
	/**
	 * <根据刑满释放人员信息id查询刑满释放人员信息>
	 * 
	 * @param  emancipistId 刑满释放人员信息id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/emancipist/view/{emancipistId}",method=RequestMethod.GET) 
	@ResponseBody
	public EmancipistInfo viewEmancipist(@PathVariable Integer emancipistId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		EmancipistInfo emancipistInfo = srvEmancipist.selectByEmancipistId(emancipistId);
		String operModule = "人口管理";
		String operKey = IUnicornFrame.ACT_VIEW[1];
		String operTarget = "刑满释放人员";
		JSONObject json = new JSONObject();
		json.put("id", emancipistId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, emancipistId.longValue(), json);
		return emancipistInfo;
	}
	/**
	 * <保存新增或编辑刑满释放人员信息>
	 * 
	 * @param  刑满释放人员信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/emancipist/save",method=RequestMethod.POST)
	@ResponseBody
	public int saveEmancipist(@RequestBody EmancipistInfo emancipistInfo){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "刑满释放人员";
		if (emancipistInfo != null && emancipistInfo.getEmancipistId() != null) {
			operKey = IUnicornFrame.ACT_EDIT[1];
		}else {
			operKey = IUnicornFrame.ACT_ADD[1];
		}
		int saveResult = srvEmancipist.save(emancipistInfo);
		Integer emancipistId = emancipistInfo.getEmancipistId();
		JSONObject json = new JSONObject();
        json.put("emancipistInfo",emancipistInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, emancipistId.longValue(), json);
		return saveResult;
	}
	
	/**
	 * 
	 * <根据刑满释放人员id删除刑满释放人员信息>
	 * @param emancipistId 刑满释放人员id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/emancipist/delete/{emancipistId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteByEmancipistId(@PathVariable Integer emancipistId){
		return srvEmancipist.deleteByEmancipistId(emancipistId);
	}
	
	/*非法上访人员**********************************************************************/
	/**
	 * <根据非访上访人员信息id查询非法上访人员信息>
	 * 
	 * @param  Id 非访上访人员信息id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/pertition/view/{illegalPertitionerId}",method=RequestMethod.GET) 
	@ResponseBody
	public IllegalPertitionerInfo viewIllegalPertitioner(@PathVariable Integer illegalPertitionerId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		IllegalPertitionerInfo illegalPertitionerInfo = srvIllegalPertitioner.selectByIllegalPertitionerId(illegalPertitionerId);
		String operModule = "人口管理";
		String operKey =IUnicornFrame.ACT_VIEW[1];
		String operTarget = "非法上访人员";
		JSONObject json = new JSONObject();
		json.put("id", illegalPertitionerId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, illegalPertitionerId.longValue(), json);
		return illegalPertitionerInfo;
	}
	/**
	 * <保存新增或编辑非法上访人员信息>
	 * 
	 * @param  非法上访人员信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/pertition/save",method=RequestMethod.POST)
	@ResponseBody
	public int saveIllegalPertitioner(@RequestBody IllegalPertitionerInfo illegalPertitionerInfo){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "非法上访人员";
		if (illegalPertitionerInfo != null && illegalPertitionerInfo.getIllegalPertitionerId() != null) {
			operKey = IUnicornFrame.ACT_EDIT[1];
		}else {
			operKey = IUnicornFrame.ACT_ADD[1];
		}
		int saveResult = srvIllegalPertitioner.save(illegalPertitionerInfo);
		Integer illegalPertitionerId = illegalPertitionerInfo.getIllegalPertitionerId();
		JSONObject json = new JSONObject();
		json.put("illegalPertitionerInfo", illegalPertitionerInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, illegalPertitionerId.longValue(), json);
		return saveResult;
	}
	
	/**
	 * 
	 * <根据非法上访人员id删除非法上访人员信息>
	 * @param illegalPertitionerId 非法上访人员id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/pertition/delete/{illegalPertitionerId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteByIllegalPertitionerId(@PathVariable Integer illegalPertitionerId){
		return srvIllegalPertitioner.deleteByIllegalPertitionerId(illegalPertitionerId);
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
