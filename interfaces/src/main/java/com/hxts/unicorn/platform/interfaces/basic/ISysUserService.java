/*
 * 文 件 名:  ISysUserService.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月25日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.basic;

import java.util.List;
import java.util.Map;

import com.hxts.unicorn.platform.interfaces.IBasicModule;

/**
 * 
 * <系统用户业务逻辑层接口>
 * 
 * @author peidehao 0380008672
 * @version [版本号, 2017年9月30日]
 * @param <SysUser>
 * @param <SysUser>
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ISysUserService extends IBasicModule {

	public static final String APP_ID = "SYSUSER";

	public static final String OPER_MODULE = "用户管理";

	public static final String OPER_TARGET = "用户";

	/**
	 * <新增一条系统用户数据>
	 * 
	 * @param sysUser
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean addSysUser(SysUserInfo sysUser);

	/**
	 * <更新系统一条系统用户数据> <功能详细描述>
	 * 
	 * @param sysUser
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean modifySysUser(SysUserInfo sysUserInfo);

	/**
	 * <根据ID删除一条系统用户数据>
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean delSysUser(int id);

	/**
	 * <批量删除系统用户数据>
	 * 
	 * @param sysUserIds
	 *            多个系统用户ID，以","区隔
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean batchDelSysUser(String sysUserIds);

	/**
	 * <通过用户名获取用户信息>
	 * 
	 * @param userName
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public SysUserInfo getSysUserByUserName(String userName);

	/**
	 * <通过手机号获取用户信息>
	 * 
	 * @param mobilephone
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public SysUserInfo getSysUserByMobilephone(String mobilephone);

	/**
	 * <通过邮箱获取用户信息>
	 * 
	 * @param email
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public SysUserInfo getSysUserByEmail(String email);

	/**
	 * 
	 * <条件获取用户信息>
	 * 
	 * @param inputStr：用户名，邮箱，手机号
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public SysUserInfo getSysUser(String inputStr);

	/**
	 * <系统用户登录验证：验证用户名及密码>
	 * 
	 * @param inputStr:username,mobilephone,email
	 * @param password
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public SysUserInfo sysUserAuth(String inputStr, String password);

	/**
	 * <用户名,真实姓名,手机号 三个条件 匹配查询用户>
	 * 
	 * @param userName
	 * @param realName
	 * @param mobilephone
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<SysUserInfo> getSysUsers(String userName, String realName, String mobilephone);

	/**
	 * <关键字查询用户信息>
	 * 
	 * @param keywords:userName,
	 *            realName, mobilephone, email
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<SysUserInfo> getSysUsersByKeywords(String keywords);

	/**
	 * <修改用户密码>
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            新密码
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean modifyPassword(String username, String password);

	/**
	 * 
	 * <通过用户ID获取用户信息>
	 * 
	 * @param userId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public SysUserInfo getSysUserById(int userId);

	/**
	 * 
	 * <通过用户ID集合获取用户信息集合>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<SysUserInfo> getSysUserByIds(List<Integer> ids);

	/**
	 * <用户ID与手机号或真实姓名 模糊匹配查询用户信息>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<SysUserInfo> getSysUserByRealNameOrMobilePhone(List<Integer> ids, String keywords);

	/**
	 * 
	 * <记录/更新用户的在线状态记录>
	 * 
	 * @param currentUserId：用户ID
	 * @see [类、类#方法、类#成员]
	 */
	public void recordOnLine(Integer currentUserId);

	/**
	 * 
	 * <获取多个网格的在线用户列表>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Map<Integer, List<SysUserInfo>> getOnLineUsers(List<String> gridIds);

	/**
	 * <新增用户地理位置>
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public int addUserLocation(String longitude, String latitude);

	/**
	 * 
	 * <获取用户的地理位置信息>
	 * 
	 * @param userIds:多个用户ID的字符床，ID之间使用","区隔
	 * @param startTime
	 * @param endTime
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Map<Integer, List<UserLocation>> getUsersLocations(List<String> userIds, String startTime, String endTime);
	
	/**
	 * 
	 * <获取用户当前位置>
	 * @param userId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public UserLocation getUserCurrentLocation(Integer userId);

}
