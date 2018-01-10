package com.hxts.unicorn.modules.resident.dao;

import com.hxts.unicorn.modules.resident.model.LivingAloneAged;
import com.hxts.unicorn.platform.interfaces.biz.LivingAloneAgedInfo;


public interface LivingAloneAgedDao {
    int insert(LivingAloneAged record);

    int insertSelective(LivingAloneAged record);
    
    int deleteByPrimaryKey(Integer livingAloneAgedId);

    LivingAloneAgedInfo selectByPrimaryKey(Integer livingAloneAgedId);
    
    int updateByPrimaryKey(LivingAloneAged record);

    int updateByPrimaryKeySelective(LivingAloneAged record);
}