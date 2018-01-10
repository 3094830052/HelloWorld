package com.hxts.unicorn.modules.resident.controller;

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
import com.hxts.unicorn.modules.resident.dto.IllegalPertitionerDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvIllegalPertitioner;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.IllegalPertitionerInfo;

@Controller
@RequestMapping("/m/resident/pertition")
public class IllegalPertitionerController {
	@Autowired
	private ISrvIllegalPertitioner srvIllegalPertitioner;
	@Autowired
	private IPersonManageService personManageServiceImpl;
	/**
	 * <根据非法上访人口id查询人口信息>
	 * 
	 * @param  householdId 非法上访人口id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/view/{illegalPertitionerId}",method=RequestMethod.GET) 
	@ResponseBody
	public IllegalPertitionerInfo view(@PathVariable Integer illegalPertitionerId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		IllegalPertitionerInfo IllegalPertitionerInfo = srvIllegalPertitioner.selectByIllegalPertitionerId(illegalPertitionerId);
		String operModule = "人口管理";
		String operKey ="查看";
		String operTarget = "非法上访人口";
		JSONObject json = new JSONObject();
		json.put("id", illegalPertitionerId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, illegalPertitionerId.longValue(), json);
		return IllegalPertitionerInfo;
	}
	/**
	 * <保存新增或编辑非法上访人口标签信息>
	 * @param  householdResidentInfo 非法上访人口标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody @Valid IllegalPertitionerInfo illegalPertitionerInfo,BindingResult result){
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
		String operTarget = "非法上访人口";
		if (illegalPertitionerInfo != null && illegalPertitionerInfo.getIllegalPertitionerId() != null) {
			operKey = "修改";
		}else {
			operKey = "新建";
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
	 * <根据非法上访人口id删除非法上访人口信息>
	 * @param householdId 非法上访人口id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/delete/{illegalPertitionerId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteByIllegalPertitionerId(@PathVariable Integer illegalPertitionerId){
		return srvIllegalPertitioner.deleteByIllegalPertitionerId(illegalPertitionerId);
	}
	
	/**
	 * <根据关键词查询非法上访人口信息列表>
	 * 
	 * @param  pertitionType 上访类型 
	 * @param lastPertitionTime 最近上访时间
	 * @param  isCrime 是否涉案
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param pageNum 当前页 可选 ，默认 1
	 * @param pageSize 每页显示信息条数， 可选， 默认10
	 * @return 非法上访人口信息及分页信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody PageInfo<IllegalPertitionerDto> getPertitionerList(@RequestParam(required = false) String pertitionType,
			@RequestParam(required = false) String lastPertitionTime,
			@RequestParam(required = false) String isCrime,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String idNo,
			@RequestParam(required = false) Integer gridId,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		if(pertitionType!=null &&pertitionType.isEmpty()){
			pertitionType=null;
		}
		if(lastPertitionTime!=null &&lastPertitionTime.isEmpty()){
			lastPertitionTime=null;
		}
		if(isCrime!=null &&isCrime.isEmpty()){
			isCrime=null;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<IllegalPertitionerDto> pertitionerList = srvIllegalPertitioner.getIllegalPertitionerListByKeywords(pertitionType, lastPertitionTime, isCrime, name, idNo, gridId);
		PageInfo<IllegalPertitionerDto> pageInfo = new PageInfo<IllegalPertitionerDto>(pertitionerList);
		return pageInfo;
	}
	
	/**
	 * 
	 * <非法上访人口页面的统计图表，包括年龄分段统计、学历统计>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="stats",method=RequestMethod.GET) 
	@ResponseBody
	public Map<String, Object> stats(){
		Map<String, Object> statsMap = new HashMap<String, Object>();
		List<StatsResult> ageStats = srvIllegalPertitioner.getAgeStats();
		List<StatsResult> degreeStats = srvIllegalPertitioner.getDegreeStats();
		statsMap.put("ageStats", ageStats);
		statsMap.put("degreeStats", degreeStats);
		return statsMap; 
	}
}
