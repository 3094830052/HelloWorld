package com.hxts.unicorn.modules.resident.dao;

import java.util.Date;
import java.util.List;

import com.hxts.unicorn.modules.resident.model.PersonInOutRecord;
import com.hxts.unicorn.platform.interfaces.biz.PersonInOutConditionModel;
import com.hxts.unicorn.platform.interfaces.biz.PersonInOutRecordInfo;

public interface PersonInOutRecordMapper {

	List<String> selectDistinctPositions();

	int insertSelective(PersonInOutRecord record);

	PersonInOutRecordInfo selectByPrimaryKey(Integer recordId);

	// int updateByPrimaryKeySelective(PersonInOutRecord record);

	// int updateByPrimaryKey(PersonInOutRecord record);

	List<PersonInOutRecordInfo> selectByVariableConditions(PersonInOutConditionModel model);
	
	List<PersonInOutRecord> selectOverdueRecord(Date nowTime);
	
	int transferToHistoryBase(List<PersonInOutRecord> records);
	
	int deleteByPrimaryKeys(List<Integer> recordIds);
	
}