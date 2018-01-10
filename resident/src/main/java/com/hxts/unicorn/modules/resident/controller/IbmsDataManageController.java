package com.hxts.unicorn.modules.resident.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.platform.interfaces.biz.CarInOutConditionModel;
import com.hxts.unicorn.platform.interfaces.biz.CarInOutRecordInfo;
import com.hxts.unicorn.platform.interfaces.biz.PersonInOutConditionModel;
import com.hxts.unicorn.platform.interfaces.biz.PersonInOutRecordInfo;
import com.hxts.unicorn.platform.interfaces.biz.integrationData.IbmsDataManageService;

@RestController
@RequestMapping("/m/ibms")
public class IbmsDataManageController {

	@Autowired
	private IbmsDataManageService ibmsDataManageService;

	@RequestMapping(value = "/person/list", method = RequestMethod.GET)
	public PageInfo<PersonInOutRecordInfo> selectByConditions(
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize, PersonInOutConditionModel model) {
		return ibmsDataManageService.selectByConditions(pageNum, pageSize, model);
	}

	@RequestMapping(value = "/person/position/{reference}", method = RequestMethod.GET)
	public List<String> selectSimilarPositions(@PathVariable String reference,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		return ibmsDataManageService.selectSimilarPositions(pageNum, pageSize, reference);
	}

	@RequestMapping(value = "/car/list", method = RequestMethod.GET)
	public PageInfo<CarInOutRecordInfo> selectByConditions(
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize, CarInOutConditionModel model) {
		return ibmsDataManageService.selectByConditions(pageNum, pageSize, model);
	}
	
//	@RequestMapping(value = "/visitor/list", method = RequestMethod.GET)
//	public PageInfo<VisitorInOutRecordInfo> selectByConditions(
//			@RequestParam(required = false, defaultValue = "1") int pageNum,
//			@RequestParam(required = false, defaultValue = "10") int pageSize, VisitorInOutConditionModel model) {
//		return ibmsDataManageService.selectByConditions(pageNum, pageSize, model);
//	}
	
}
