package com.hxts.unicorn.modules.resident.dao;

import com.hxts.unicorn.modules.resident.model.DisabledPeople;
import com.hxts.unicorn.platform.interfaces.biz.DisabledPeopleInfo;

public interface DisabledPeopleDao {
    int insert(DisabledPeople record);

    int insertSelective(DisabledPeople record);
    
    int deleteByPrimaryKey(Integer disabledPeopleId);

    DisabledPeopleInfo selectByPrimaryKey(Integer disabledPeopleId);

    int updateByPrimaryKey(DisabledPeople record);
    
    int updateByPrimaryKeySelective(DisabledPeople record);
}