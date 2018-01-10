package com.hxts.unicorn.platform.interfaces.biz;

import java.util.ArrayList;
import java.util.List;

public class GridItem {
	// 网格id
	Integer gridId;
	
	// 网格名
	String gridName;
	
	// 区域坐标
	String region;
	
	// 网格员信息
	List<GridMan> gridMans = new ArrayList<GridMan>();

	public List<GridMan> getGridMans() {
		return gridMans;
	}

	public void setGridMans(List<GridMan> gridMans) {
		this.gridMans = gridMans;
	}

	public Integer getGridId() {
		return gridId;
	}

	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
