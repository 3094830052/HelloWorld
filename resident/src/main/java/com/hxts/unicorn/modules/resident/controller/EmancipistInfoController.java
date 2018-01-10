package com.hxts.unicorn.modules.resident.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dto.EmancipistDto;
import com.hxts.unicorn.modules.resident.service.ISrvEmancipist;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.EmancipistInfo;

@RestController
@RequestMapping("/m/resident/emancipist")
public class EmancipistInfoController {
	@Autowired
	private ISrvEmancipist srvEmancipist;

	/**
	 * <根据刑满释放人员id查询刑满释放人员标签信息>
	 * 
	 * @param  emancipistId 刑满释放人员id
	 * @return 刑满释放人员标签信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/view/{emancipistId}",method=RequestMethod.GET) 
	@ResponseBody
	public EmancipistInfo view(@PathVariable Integer emancipistId){
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		EmancipistInfo emancipist = srvEmancipist.selectByEmancipistId(emancipistId);
		String operModule = "人口管理";
		String operKey ="查看";
		String operTarget = "重点青少年";
		JSONObject json = new JSONObject();
		json.put("id", emancipistId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, emancipistId.longValue(), json);
		return emancipist;
	}
	
	/**
	 * 
	 * <根据刑满释放人员id删除刑满释放人员信息>
	 * @param emancipistId 刑满释放人员id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/delete/{emancipistId}",method=RequestMethod.GET) 
	@ResponseBody
	public int deleteByKeyTeenagerId(@PathVariable Integer emancipistId){
		return srvEmancipist.deleteByEmancipistId(emancipistId);
	}
	
	/**
	 * <保存新增或编辑刑满释放人员标签信息>
	 * 
	 * @param  emancipist 刑满释放人员标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public int save( EmancipistInfo emancipist) {
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey ="";
		String operTarget = "刑满释放人员";
		if (emancipist != null && emancipist.getEmancipistId() != null) {
			operKey = "修改";
		}else {
			operKey = "新建";
		}
	    
		int saveResult = srvEmancipist.save(emancipist);
		
	Integer emancipistId = emancipist.getEmancipistId();
		JSONObject json = new JSONObject();
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, emancipistId.longValue(), json);
		return saveResult;
	}
	
	/**
	 * <根据关键词查询刑满释放人员信息列表>
	 * 
	 * @param  intDate 最近衔接日期月数范围
	 * @param  riskAssessmentType 危险性评估
	 * @param  placeSituation 安置情况
	 * @param  name 姓名
	 * @param  idNo 身份号码
	 * @param gridId 所属网格id
	 * @param pageNum 当前页 可选 ，默认 1
	 * @param pageSize 每页显示信息条数， 可选， 默认10
	 * @return 重点青少年信息及分页信息
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody PageInfo<EmancipistDto> getKeyTeenagerList(@RequestParam(required = false) String intDate,
			@RequestParam(required = false) String riskAssessmentType,
			@RequestParam(required = false) String placeSituation,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String idNo,
			@RequestParam(required = false) Integer gridId,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Date dNow = new Date();
		Date LastcohesionDate = new Date();
		if (intDate != null ) {
			int i=Integer.valueOf(intDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dNow);
			calendar.add(Calendar.MONTH, -i);
			LastcohesionDate = calendar.getTime();
		}
		else {
			LastcohesionDate=null;
		}
		List<EmancipistDto> FloatingResidentList = srvEmancipist.getEmancipistListByKeywords(LastcohesionDate, riskAssessmentType, placeSituation, name, idNo, gridId);
		PageInfo<EmancipistDto> pageInfo = new PageInfo<EmancipistDto>(FloatingResidentList);
		return pageInfo;
	}
	
	
}
