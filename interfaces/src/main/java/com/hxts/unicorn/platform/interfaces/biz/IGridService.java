package com.hxts.unicorn.platform.interfaces.biz;

import java.util.List;
import java.util.Map;

import com.hxts.unicorn.platform.interfaces.IBizModule;
import com.hxts.unicorn.platform.interfaces.GIS.GridStat;

public interface IGridService extends IBizModule {
	public static final String APP_ID = "GRIDMGR";

	/**
	 * 
	 * <一句话功能简述> <功能详细描述>给网格添加边界以及楼栋信息
	 * 
	 * @param gridInfo
	 * @see [类、类#方法、类#成员]
	 */
	void addPointOfGrid(Integer gridId, String region, String buildings, String deviceNames);

	// 获取当前用户所管辖的网格
	List<GridItem> getCurrentUserGrid();

	// 返回一个用户所属网格，所管辖的网格
	List<GridItem> getUserGrid(int uid);

	// 判断某个人是否网格员
	boolean isGridMan(int uid);

	// 判断某个人是否某个网格的网格员
	boolean isGridMan(int uid, int gridId);

	// 返回一个网格所有的楼栋
	List<String> getGridBuilding(int gridId);

	// 返回一个网格的网格员id
	List<Integer> getGridMan(int gridId);
	
	// 返回一个网格网格员姓名与ID
	// id:xxx  name:xxx
	List<Map<String, Object>> getGridManMap(int gridId);
	
	// 返回所有网格信息
	List<GridStat> getGridStat();
	
	// 根据网格id获取网格信息
	GridItem getGridById(Integer id);

}
