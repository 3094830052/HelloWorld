package com.hxts.unicorn.modules.resident.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dto.DrugDto;
import com.hxts.unicorn.modules.resident.service.ISrvDrug;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.DrugInfo;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;

@RestController
@RequestMapping("/m/resident/drug")
public class DrugController {

	@Autowired
	private ISrvDrug srvDrug;

	@Autowired
	private IPersonManageService personManageServiceImpl;

	/**
	 * <根据吸毒者id查询吸毒者标签信息>
	 * 
	 * @param drugId
	 *            吸毒者id
	 * @return 吸毒者标签信息
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping("/{drugId}")
	public DrugInfo view(@PathVariable Integer drugId) {
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		DrugInfo drugInfo = srvDrug.selectByDrugInfoId(drugId);
		String operModule = "人口管理";
		String operKey = "查看";
		String operTarget = "吸毒者";
		JSONObject json = new JSONObject();
		json.put("id", drugId);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, drugId.longValue(), json);
		return drugInfo;
	}

	/**
	 * <保存新增或编辑吸毒者标签信息>
	 * 
	 * @param floatingResidentInfo
	 *            吸毒者标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@PostMapping("/save")
	public int save(DrugInfo drugInfo) {
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
				.getModule(IActionLogService.APP_ID);
		String operModule = "人口管理";
		String operKey = "";
		String operTarget = "吸毒者";
		if (drugInfo != null && drugInfo.getDrugAddictId() != null) {
			operKey = "修改";
		} else {
			operKey = "新建";
		}
		int saveResult = srvDrug.save(drugInfo);
		Integer drugid = drugInfo.getDrugAddictId();
		JSONObject json = new JSONObject();
		// 较长字段截取最大长度200记录日志
		// drugInfo.setAssistState(drugInfo.getAssistState().substring(0,200));
		json.put("drugInfo", drugInfo);
		logServiceImpl.writeSysUserLog(operModule, operKey, operTarget, drugid.longValue(), json);
		return saveResult;
	}

	/**
	 * 
	 * <根据吸毒者id删除吸毒者信息>
	 * 
	 * @param DrugInfoId
	 *            吸毒者Id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@DeleteMapping("/{DrugInfoId}")
	public int deleteByDrugInfoId(@PathVariable Integer DrugInfoId) {
		return srvDrug.deleteByDrugInfoId(DrugInfoId);
	}

	/**
	 * <根据关键词查询吸毒者信息列表>
	 * 
	 * @param controlsituation
	 *            管控情况
	 * @param iscrime
	 *            有无犯罪史
	 * @param datefirstdiscover
	 *            初次发现日期
	 * @param name
	 *            姓名
	 * @param idNo
	 *            身份号码
	 * @param gridId
	 *            所属网格id
	 * @param pageNum
	 *            当前页 可选 ，默认 1
	 * @param pageSize
	 *            每页显示信息条数， 可选， 默认10
	 * @return 吸毒者信息及分页信息
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping("list")
	public PageInfo<DrugDto> getDrugList(@RequestParam(required = false) String controlsituation,
			@RequestParam(required = false) Integer iscrime,
			@RequestParam(required = false) Integer datefirstdiscover,
			@RequestParam(required = false) String name, @RequestParam(required = false) String idNo,
			@RequestParam(required = false) Integer gridId,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<DrugDto> FloatingResidentList = srvDrug.getDrugInfoListByKeywords(name, idNo, gridId, iscrime,
				controlsituation,datefirstdiscover);
		PageInfo<DrugDto> pageInfo = new PageInfo<DrugDto>(FloatingResidentList);
		return pageInfo;
	}
}
