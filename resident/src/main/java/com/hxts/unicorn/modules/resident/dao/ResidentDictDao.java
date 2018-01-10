package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.modules.resident.model.ResidentDict;
import com.hxts.unicorn.platform.interfaces.DataDictionaryItem;

public interface ResidentDictDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ResidentDict record);

    int insertSelective(ResidentDict record);

    ResidentDict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResidentDict record);

    int updateByPrimaryKey(ResidentDict record);
    
    List<DataDictionaryItem> getAllDict();
    
    List<DataDictionaryItem> getDictByDataType(String dataType);
    
    int deleteByKeyWords(@Param("dataType")String dataType,@Param("dataCode")String dataCode);
    
    List<ResidentDict> selectByKeyWords(@Param("dataType")String dataType,@Param("dataCode")String dataCode);
}