/*
 * 文 件 名:  IActionLogService.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  用户操作日志管理
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月10日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.platform.interfaces.IBasicModule;

/**
 * 用户操作日志管理接口 <功能详细描述>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IActionLogService extends IBasicModule {

	// Service AppID
	public static final String APP_ID = "SYSLOG";
	/**
	 * 按模块分组
	 */
	public static final int GROUP_BY_MDL = 1;
	/**
	 * 按操作类型分组
	 */
	public static final int GROUP_BY_OPER = 2;
	/**
	 * 按操作目标分组
	 */
	public static final int GROUP_BY_TARGET = 4;
	/**
	 * 按模块+操作类型分组
	 */
	public static final int GROUP_BY_MDL_OPER = GROUP_BY_MDL | GROUP_BY_OPER;
	/**
	 * 按操作类型+目标分组
	 */
	public static final int GROUP_BY_OPER_TARGET = GROUP_BY_OPER | GROUP_BY_TARGET;
	/**
	 * 按模块+操作类型+目标分组
	 */
	public static final int GROUP_BY_MDL_OPER_TARGET = GROUP_BY_MDL | GROUP_BY_OPER | GROUP_BY_TARGET;

	/**
	 * 写系统用户操作日志,操作类型就业务模块自定中文名
	 * 
	 * @param uid
	 *            用户
	 * @param operKey
	 *            操作类型
	 * @param operModule
	 *            操作模块
	 * @param operTarget
	 *            操作目标
	 * @param description
	 *            记录内容
	 */
	void writeSysUserLog(String operModule, String operKey, String operTarget, Long targetKey, JSONObject description);

	/**
	 * 写普通用户操作日志,操作类型就业务模块自定中文名
	 * 
	 * @param uid
	 * @param operKey
	 * @param description
	 */
	void writeGuestUserLog(String operKey, JSONObject description);

	/**
	 * 日志查询 支持多种条件组合，如用户、时间段、操作类型。并指定数据的起始索引和个数。
	 * 
	 * @param uid
	 * @param operKey
	 * @param start
	 * @param end
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            每一页的大小
	 * @return
	 */
	List<LogItem> querySysUserLog(int uid, String operKey, String operModule, String operTarget, String start,
			String end, int pageNum, int pageSize);

	/**
	 * 日志查询 支持多种条件组合，如用户、时间段、操作类型。并指定数据的起始索引和个数。
	 * 
	 * @param uid
	 * @param operKey
	 * @param start
	 * @param end
	 * @param index
	 * @param size
	 * @return
	 */
	PageInfo<LogItem> queryGuestUserLog(int uid, String operKey, String start, String end, int pageNum, int pageSize);

	/**
	 * 统计接口-------------------------------------------------
	 */

	/**
	 * 在指定时间段内，统计某系统用户的操作，按操作类型分组汇总。 操作分组方式由groupBy指定
	 */
	Map<String, Long> countSysUserLogByAction(int uid, String start, String end, int groupBy);

	/**
	 * 在指定时间段内，统计某访客用户的操作，按操作类型分组汇总。
	 */
	Map<String, Long> countGuestUserLogByAction(int uid, String start, String end);

	/**
	 * 访客日志统计一段时间内某个操作的个数
	 * 
	 * @param operKey
	 * @param start
	 * @param end
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int countActionGuestLog(String operKey, String start, String end);

	/**
	 * 系统日志统计一段时间内某个操作的个数
	 * 
	 * @param operKey
	 * @param operModule
	 * @param operTarget
	 * @param start
	 * @param end
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int countActionSysLog(String operModule, String operKey, String operTarget, String start, String end,
			List<Integer> userIds);

	/**
	 * 访客日志统计一段时间内所有操作的个数，按操作类型分组
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	Map<String, Long> countGuestLogByAction(String start, String end);

	/**
	 * 系统日志，在指定时间段内，统计多个用户的操作个数。按用户分组汇总。
	 * 
	 * @param start
	 * @param end
	 * @return Map<用户id，操作个数>
	 * @see [类、类#方法、类#成员]
	 */
	Map<Integer, Long> countAllSysLogByUser(String start, String end);

	/**
	 * 系统日志，在指定时间段内，统计多个用户的某一操作对比，按用户分组汇总。
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @see [类、类#方法、类#成员]
	 */

	Map<Integer, Long> countActionSysLogByUser(String operModule, String operKey, String operTarget, Long targetKey,
			String start, String end);

	Map<Integer, Long> countByTargetKey(String operModule, List<String> operKeys, List<Integer> userIds, Date begin,
			Date end);

	// 指定某个用户，统计在不同时间段内，任意操作个数，按时间段分组汇总。

	// 指定某个用户，统计在不同时间段内，某个操作的个数，按时间段分组汇总。

}
