package com.hxts.unicorn.modules.resident.dao;

import com.hxts.unicorn.modules.resident.model.ResidentScore;
import com.hxts.unicorn.platform.interfaces.biz.ResidentScoreInfo;

public interface ResidentScoreDao {
    int deleteByPrimaryKey(Integer residentScoreId);

    int insert(ResidentScore record);

    int insertSelective(ResidentScore record);

    ResidentScoreInfo selectByPrimaryKey(Integer residentScoreId);
    
    ResidentScoreInfo selectByResidentBaseId(Integer ResidentBaseId);
    
    ResidentScoreInfo selectByOpenId(String openId);

    int updateByPrimaryKeySelective(ResidentScore record);

    int updateByPrimaryKey(ResidentScore record);
}