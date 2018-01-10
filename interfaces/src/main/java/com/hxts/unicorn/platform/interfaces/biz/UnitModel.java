package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * <房屋建模辅助类：单元模型>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年12月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UnitModel implements Serializable{
	 
	private static final long serialVersionUID = 3953895223957906632L;

	/**
	 * 单元号
	 */
	private int unitNumber;
	
	/**
	 * 楼层建模数据
	 */
	private List<HouseModelInfo> hms;

	public int getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(int unitNumber) {
		this.unitNumber = unitNumber;
	}

	public List<HouseModelInfo> getHms() {
		return hms;
	}

	public void setHms(List<HouseModelInfo> hms) {
		this.hms = hms;
	}
	
}
