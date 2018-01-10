/*
 * 文 件 名:  HisResidentOfHouseDao.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月21日
 * 修改内容:  
 */
package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.platform.interfaces.biz.HisResidentOfHouseInfo;

/**
 * <历史入住人口关 数据持久层接口>
 * 
 * @author peidehao 0380008672
 * @version [版本号, 2017年10月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface HisResidentOfHouseDao {

	public void deleteHrohById(@Param("hisResidentOfHouseId") Long hisResidentOfHouseId);

	public void addHisResidentOfHouse(HisResidentOfHouseInfo isResidentOfHouse);
	
	public void updateHisResidentOfHouse(HisResidentOfHouseInfo isResidentOfHouse);

	public HisResidentOfHouseInfo queryBaseHrohById(Long hisResidentOfHouseId);

	public List<HisResidentOfHouseInfo> queryHrohByHouseId(Long houseId);

	public List<HisResidentOfHouseInfo> queryBaseHrohByHouseId(Long houseId);
}
