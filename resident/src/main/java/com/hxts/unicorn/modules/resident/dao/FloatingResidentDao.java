package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.modules.resident.dto.FloatingResidentDto;
import com.hxts.unicorn.modules.resident.model.FloatingResident;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.FloatingResidentInfo;

public interface FloatingResidentDao {
    int deleteByPrimaryKey(Integer floatingId);

    int insert(FloatingResident record);

    int insertSelective(FloatingResident record);

    FloatingResidentInfo selectByPrimaryKey(Integer floatingId);

    int updateByPrimaryKeySelective(FloatingResident record);

    int updateByPrimaryKey(FloatingResident record);
    
    List<FloatingResidentDto> queryFloatingResidentListByKeywords(@Param("inflowReason")String inflowReason,@Param("isFocusPerson")String isFocusPerson,@Param("certificateHandlingType")String certificateHandlingType,@Param("certificateExpireDate")String certificateExpireDate,@Param("name")String name,@Param("idNo")String idNo,@Param("residentBaseIds") List<Integer> residentBaseIds);
    
    /**
     * 
     * <流动人口页面，按照流入原因分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getInflowReasonStats(@Param("residentBaseIds")List<Integer>residentBaseIds);
    /**
     * 
     * <流动人口页面，按照证件到期时间分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getCertificateExpireStats(@Param("residentBaseIds")List<Integer>residentBaseIds);
}