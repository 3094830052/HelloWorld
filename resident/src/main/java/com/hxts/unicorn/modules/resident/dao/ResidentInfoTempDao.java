package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.platform.interfaces.biz.ResidentTemp;


public interface ResidentInfoTempDao {
    int deleteByPrimaryKey(Integer residentBaseId);

    int insert(ResidentTemp record);

    int insertSelective(ResidentTemp record);

    ResidentTemp selectByPrimaryKey(Integer residentBaseId);

    int updateByPrimaryKeySelective(ResidentTemp record);

    int updateByPrimaryKey(ResidentTemp record);
    
    ResidentTemp selectByIdNo(@Param("idNo")String idNo,@Param("type")Integer type);
    
    ResidentTemp selectByOpenId(String openId);
    
    List<ResidentTemp> getall(String openId);
    
    List<ResidentTemp> getResidentTemp(@Param("status")Integer status,@Param("openId")String openId);
}