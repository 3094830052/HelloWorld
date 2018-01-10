/*
 * 文 件 名:  HouseArea.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年12月4日
 * 修改内容:  
 */
package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;

/**
 * <房屋建模面积>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年12月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HouseArea implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2661317511207525351L;

	/**
	 * 房号
	 */
	private int houseNumber;
	
	/**
	 * 建筑面积
	 */
	private double structureArea;

	/**
	 * 使用面积
	 */
	private double usableArea;

	/**
	 * 公摊面积
	 */
	private double poolArea;

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public double getStructureArea() {
		return structureArea;
	}

	public void setStructureArea(double structureArea) {
		this.structureArea = structureArea;
	}

	public double getUsableArea() {
		return usableArea;
	}

	public void setUsableArea(double usableArea) {
		this.usableArea = usableArea;
	}

	public double getPoolArea() {
		return poolArea;
	}

	public void setPoolArea(double poolArea) {
		this.poolArea = poolArea;
	}
	
}
