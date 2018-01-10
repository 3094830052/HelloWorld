package com.hxts.unicorn.modules.resident.dao;

import java.util.Date;
import java.util.List;

import com.hxts.unicorn.modules.resident.model.CarInOutRecord;
import com.hxts.unicorn.platform.interfaces.biz.CarInOutConditionModel;
import com.hxts.unicorn.platform.interfaces.biz.CarInOutRecordInfo;

public interface CarInOutRecordMapper {

	int insertSelective(CarInOutRecord record);

	CarInOutRecord selectByPrimaryKey(Integer recordId);

	List<CarInOutRecordInfo> selectByVariableConditions(CarInOutConditionModel model);

	List<CarInOutRecord> selectOverdueRecord(Date nowTime);

	int transferToHistoryBase(List<CarInOutRecord> records);

	int deleteByPrimaryKeys(List<Integer> recordIds);

	int deleteByPrimaryKey(List<Integer> recordIds);

	int updateByPrimaryKeySelective(CarInOutRecord record);

	int updateByPrimaryKey(CarInOutRecord record);

	List<CarInOutRecord> selectByPassTime(String carNumber);

	// 通过车牌号判断
	List<CarInOutRecord> selectByCarNumber(String carNumber);

	List<String> selectSimilarPosition(String reference);

}