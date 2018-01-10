/*
 * 文 件 名:  BuildingModel.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年11月6日
 * 修改内容:  
 */
package com.hxts.unicorn.modules.resident.model;

import java.io.Serializable;
import java.util.List;

import com.hxts.unicorn.platform.interfaces.biz.BuildingInfo;
import com.hxts.unicorn.platform.interfaces.biz.UnitModel;

/**
 * <新增楼栋信息包装类>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BuildingModel implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4831903884097441056L;

	private BuildingInfo building;
	
	private List<UnitModel> ums;

	public BuildingInfo getBuilding() {
		return building;
	}

	public void setBuilding(BuildingInfo building) {
		this.building = building;
	}

	public List<UnitModel> getUms() {
		return ums;
	}

	public void setUms(List<UnitModel> ums) {
		this.ums = ums;
	}

}
