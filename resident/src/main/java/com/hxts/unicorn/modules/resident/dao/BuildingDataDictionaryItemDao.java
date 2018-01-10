/*
 * 文 件 名:  SysDataDictionaryItemMapper.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月30日
 * 修改内容:  
 */
package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.platform.interfaces.DataDictionaryItem;

/**
 * <编码 数据持久层接口>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface BuildingDataDictionaryItemDao {

	int insert(DataDictionaryItem record);

	List<DataDictionaryItem> selectAll();

	List<DataDictionaryItem> selectByDataType(@Param("dataType") String dataType);

	void delete(@Param("dataType") String dataType, @Param("dataCode") String dataCode);
}
