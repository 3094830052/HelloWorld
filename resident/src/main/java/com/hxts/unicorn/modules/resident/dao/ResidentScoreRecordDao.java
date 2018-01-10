package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import com.hxts.unicorn.modules.resident.model.ResidentScoreRecord;

public interface ResidentScoreRecordDao {
    int deleteByPrimaryKey(Integer residentScoreRecordId);

    int insert(ResidentScoreRecord record);

    int insertSelective(ResidentScoreRecord record);

    ResidentScoreRecord selectByPrimaryKey(Integer residentScoreRecordId);
    
    List<ResidentScoreRecord> selectByResidentBaseId(Integer residentBaseId);

    int updateByPrimaryKeySelective(ResidentScoreRecord record);

    int updateByPrimaryKey(ResidentScoreRecord record);
}