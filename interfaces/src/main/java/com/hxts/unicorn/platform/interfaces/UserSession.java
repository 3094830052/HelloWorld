/*
 * 文 件 名:  UserSession.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月30日
 * 修改内容:  
 */
package com.hxts.unicorn.platform.interfaces;

import javax.servlet.http.HttpServletResponse;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年9月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface UserSession {

	/**
	 * <获取当前用户信息>
	 * 
	 * @return 用户ID及用户类型
	 * @see [类、类#方法、类#成员]
	 */
	CurrentUser getCurrentSysUser();

	/**
	 * <设置当前用户信息，并设置cookie>
	 * 
	 * @param userId
	 *            当前用户ID
	 * @param userType
	 *            用户类别
	 * @param minutes
	 *            token有效期(分钟)
	 * @see [类、类#方法、类#成员]
	 */
	void setCurrentSysUser(String userId, int userType, int minutes, HttpServletResponse response);

	/**
	 * 
	 * <验证token有效性>
	 * 
	 * @param token
	 *            token身份编码
	 * @param userType
	 *            用户类别：0 系统用户，1普通用户
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean auth(String token, int userType);

	/**
	 * 当前调用请求是否是外部调用（从客户端）
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean isExtenalCall();
	
	/**
	 * 
	 * <记录/更新用户在线状态>
	 * @param userId
	 * @see [类、类#方法、类#成员]
	 */
	public void setUserOnLineState(String userId);

	/**
	 * 
	 * <获取用户在线状态>
	 * 
	 * @param userId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String getUserOnLineState(String userId);

	/**
	 * 
	 * <删除用户在线状态缓存>
	 * 
	 * @param userId
	 * @see [类、类#方法、类#成员]
	 */
	public void removeUserOnLineState(String userId);

}
