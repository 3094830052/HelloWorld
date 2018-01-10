package com.hxts.unicorn.modules.resident.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hxts.unicorn.modules.resident.model.VisitorInOutRecord;
import com.hxts.unicorn.platform.interfaces.biz.PersonInOutRecordInfo;
import com.hxts.unicorn.platform.interfaces.biz.VisitorInOutConditionModel;
import com.hxts.unicorn.platform.interfaces.biz.VisitorInOutRecordInfo;

public interface VisitorInOutRecordMapper {
	List<Map<String, Object>> selectDistinctPositions();

	int insertSelective(VisitorInOutRecord record);

	PersonInOutRecordInfo selectByPrimaryKey(Integer recordId);

	// int updateByPrimaryKeySelective(PersonInOutRecord record);

	// int updateByPrimaryKey(PersonInOutRecord record);

	List<VisitorInOutRecordInfo> selectByVariableConditions(VisitorInOutConditionModel model);
	
	List<VisitorInOutRecord> selectOverdueRecord(Date nowTime);
	
	int transferToHistoryBase(List<VisitorInOutRecord> records);
	
	int deleteByPrimaryKeys(List<Integer> recordIds);
    
}