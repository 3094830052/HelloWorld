package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.modules.resident.dto.KeyTeenagerDto;
import com.hxts.unicorn.modules.resident.model.KeyTeenager;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.KeyTeenagerInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentBaseInfo;

public interface KeyTeenagerDao {
    int deleteByPrimaryKey(Integer keyTeenagerId);

    int insert(KeyTeenager record);

    int insertSelective(KeyTeenager record);

    KeyTeenagerInfo selectByPrimaryKey(Integer keyTeenagerId);
    
    List<KeyTeenagerDto> queryKeyTeenagerListByKeywords(@Param("teenagerType")String teenagerType,@Param("name")String name,@Param("idNo")String idNo,@Param("residentBaseIds") List<Integer> residentBaseIds);
    
    List<ResidentBaseInfo> queryBaseInfoByKeywords(@Param("teenagerType")String teenagerType,@Param("name")String name,@Param("idNo")String idNo,@Param("residentBaseIds") List<Integer> residentBaseIds);

    int updateByPrimaryKeySelective(KeyTeenager record);

    int updateByPrimaryKey(KeyTeenager record);
    
    /**
     * 
     * <重点青少年页面，按照人员类型分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getTeenagerTypeStats(@Param("residentBaseIds")List<Integer>residentBaseIds);
}