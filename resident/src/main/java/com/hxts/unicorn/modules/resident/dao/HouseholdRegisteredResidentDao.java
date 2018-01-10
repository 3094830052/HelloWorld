package com.hxts.unicorn.modules.resident.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hxts.unicorn.modules.resident.dto.HouseholdRegisteredResidentDto;
import com.hxts.unicorn.modules.resident.model.HouseholdRegisteredResident;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.HouseholdResidentInfo;
@Repository
public interface HouseholdRegisteredResidentDao {
    int deleteByPrimaryKey(Integer householdId);

    int insert(HouseholdRegisteredResident record);

    int insertSelective(HouseholdRegisteredResident record);

    HouseholdResidentInfo selectByPrimaryKey(Integer householdId);
    
    List<HouseholdResidentInfo> selectByKeyWord(@Param("householdNo")String householdNo,@Param("idNo")String idNo);

    int updateByPrimaryKeySelective(HouseholdRegisteredResident record);

    int updateByPrimaryKey(HouseholdRegisteredResident record);
    
    List<HouseholdRegisteredResidentDto> queryHouseholdRegisterListByKeywords(@Param("uniformityFlag")String uniformityFlag,
    		@Param("integrity")BigDecimal integrity, @Param("name")String name,@Param("idNo")String idNo,@Param("residentBaseIds") List<Integer> residentBaseIds);
    /**
     * 
     * <户籍人口页面统计，按学历统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getDegreeStats(@Param("residentBaseIds")List<Integer> residentBaseIds);
    /**
     * 
     * <户籍人口页面统计，按年龄分布统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getAgeStats(@Param("residentBaseIds")List<Integer> residentBaseIds);
}