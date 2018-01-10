package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.modules.resident.model.Car;
import com.hxts.unicorn.platform.interfaces.biz.CarInfo;

public interface CarInfoDao {
    int deleteByPrimaryKey(Integer carNumber);

    int insert(Car record);

    int insertSelective(Car record);

    CarInfo selectByPrimaryKey(Integer carNumber);
    
    CarInfo selectByResidentBaseId(Integer residentBaseId);

    int updateByPrimaryKeySelective(Car record);

    int updateByPrimaryKey(Car record);
    
    List<CarInfo> getall();
    
    List<CarInfo> getcarInfo(@Param(value = "residentBaseId") Integer residentBaseId);
}