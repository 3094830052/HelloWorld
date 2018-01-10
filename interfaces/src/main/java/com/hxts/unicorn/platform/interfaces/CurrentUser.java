/*
 * 文 件 名:  CurrentUser.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月12日
 * 修改内容:  
 */
package com.hxts.unicorn.platform.interfaces;

import java.io.Serializable;

/**
 * <当前用户>
 * 
 * @author peidehao 0380008672
 * @version [版本号, 2017年10月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CurrentUser implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7017017743780098543L;
	
	public static final int USER_SYSTEM = 0;
	public static final int USER_GUEST = 1;

	/**
	 * 当前用户ID(对应系统用户)
	 */
	private int userId;

	/**
	 * 用户类型：系统管理用户 0 ，普通用户 1
	 */
	private int userType;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "CurrentUser [userId=" + userId + ", userType=" + userType + "]";
	}

}
