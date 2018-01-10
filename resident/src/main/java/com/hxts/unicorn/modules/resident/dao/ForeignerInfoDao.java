package com.hxts.unicorn.modules.resident.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.modules.resident.dto.ForeignerInfoDto;
import com.hxts.unicorn.modules.resident.model.ForeignerInfo;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.ForeignerResidentInfo;

public interface ForeignerInfoDao {
    int deleteByPrimaryKey(Integer foreignerId);

    int insert(ForeignerInfo record);

    int insertSelective(ForeignerInfo record);

    ForeignerResidentInfo selectByPrimaryKey(Integer foreignerId);

    int updateByPrimaryKeySelective(ForeignerInfo record);

    int updateByPrimaryKey(ForeignerInfo record);
    /**
     * 
     * <境外人口页面统计，按来华目的统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getPurposeStats(@Param("residentBaseIds")List<Integer>residentBaseIds);
    /**
     * 
     * <境外人口页面统计，按年龄分布统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getAgeStats(@Param("residentBaseIds")List<Integer>residentBaseIds);
    
    List<ForeignerInfoDto> queryForeignerInfoListByKeywords(@Param("nationality")String nationality,
    		@Param("integrity")BigDecimal integrity, @Param("name")String name,@Param("idNo")String idNo,@Param("residentBaseIds") List<Integer> residentBaseIds);
}