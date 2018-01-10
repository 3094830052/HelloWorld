package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hxts.unicorn.modules.resident.dto.LeftBehindResidentDto;
import com.hxts.unicorn.modules.resident.model.LeftBehindResident;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.LeftBehindResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentBaseInfo;
@Repository
public interface LeftBehindResidentDao {
    int deleteByPrimaryKey(Integer leftBehindId);

    int insert(LeftBehindResident record);

    int insertSelective(LeftBehindResident record);

    LeftBehindResidentInfo selectByPrimaryKey(Integer leftBehindId);

    int updateByPrimaryKeySelective(LeftBehindResident record);

    int updateByPrimaryKey(LeftBehindResident record);
    
    List<LeftBehindResidentDto> getLeftBehindListByKeywords(@Param("healthCondition")String healthCondition,@Param("leftBehindResidentType")String leftBehindResidentType, @Param("name")String name, @Param("idNo")String idNo,@Param("residentBaseIds") List<Integer> residentBaseIds);
    
    List<ResidentBaseInfo> queryBaseInfoByKeywords(@Param("healthCondition")String healthCondition,@Param("leftBehindResidentType")String leftBehindResidentType, @Param("name")String name, @Param("idNo")String idNo,@Param("residentBaseIds") List<Integer> residentBaseIds);
    /**
     * 
     * <留守人口页面统计，按留守人员类型分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getLeftBehindTypeStats(@Param("residentBaseIds")List<Integer>residentBaseIds);
}