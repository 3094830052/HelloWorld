package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.modules.resident.dto.IllegalPertitionerDto;
import com.hxts.unicorn.modules.resident.model.IllegalPertitioner;
import com.hxts.unicorn.platform.interfaces.biz.IllegalPertitionerInfo;

public interface IllegalPertitionerDao {
    int deleteByPrimaryKey(Integer illegalPertitionerId);

    int insert(IllegalPertitioner record);

    int insertSelective(IllegalPertitioner record);

    IllegalPertitionerInfo selectByPrimaryKey(Integer illegalPertitionerId);

    int updateByPrimaryKeySelective(IllegalPertitioner record);

    int updateByPrimaryKey(IllegalPertitioner record);
    
    List<IllegalPertitionerDto> getIllegalPertitionerListByKeywords(@Param("pertitionType")String pertitionType,@Param("lastPertitionTime")String lastPertitionTime,@Param("isCrime")String isCrime,@Param("name")String name,@Param("idNo")String idNo,@Param("residentBaseIds")List<Integer> residentBaseIds);
}