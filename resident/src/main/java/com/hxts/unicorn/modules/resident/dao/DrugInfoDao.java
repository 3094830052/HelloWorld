package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.modules.resident.dto.DrugDto;
import com.hxts.unicorn.modules.resident.dto.SpecialCrowdDto;
import com.hxts.unicorn.modules.resident.model.Drug;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.DrugInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentBaseInfo;
public interface DrugInfoDao {
    int deleteByPrimaryKey(Integer drugAddictId);

    int insert(Drug record);

    int insertSelective(Drug record);

    DrugInfo selectByPrimaryKey(Integer drugAddictId);

    int updateByPrimaryKeySelective(Drug record);

    int updateByPrimaryKey(Drug record);
    
    List<DrugDto> querydrugInfoListByKeywords(@Param("name")String name,@Param("idNo") String idNo,@Param("residentBaseIds") List<Integer> residentBaseIds,@Param("iscrime")Integer iscrime,@Param("controlsituation")String controlsituation,@Param("datefirstdiscover")Integer datefirstdiscover);
    
    /**
     * 
     * <特殊人群页面，按照特殊人群类型分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getSpecialTypeStats(@Param("residentBaseIds")List<Integer>residentBaseIds);
    
    List<Integer> getSpecialTypeStatsCount(@Param("residentBaseIds")List<Integer>residentBaseIds);
    
    List<SpecialCrowdDto> querySpecialCrowdListByKeywords(@Param("specialType")String specialType,@Param("name")String name,@Param("idNo")String idNo,@Param("residentBaseIds")List<Integer> residentBaseIds);
    
    List<ResidentBaseInfo> queryBaseInfoByKeywords(@Param("specialType")String specialType,@Param("name")String name,@Param("idNo")String idNo,@Param("residentBaseIds")List<Integer> residentBaseIds);
}
