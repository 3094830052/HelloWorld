/*
 * 文 件 名:  LogItem.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月10日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.basic;

import java.util.Date;


/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年10月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LogItem {
	/**
	 * 主键id
	 */
	int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 用户id
	 */
	int userId;
	/**
	 * 操作类型
	 */
	String operKey;
	/**
	 * 操作时间
	 */
	Date operTime;
	
	String content;
	
	String operModule;
	
	Long targetKey;
	
	public Long getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

	public String getOperModule() {
		return operModule;
	}

	public void setOperModule(String operModule) {
		this.operModule = operModule;
	}

	public String getOperTarget() {
		return operTarget;
	}

	public void setOperTarget(String operTarget) {
		this.operTarget = operTarget;
	}

	String operTarget;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getOperKey() {
		return operKey;
	}

	public void setOperKey(String operKey) {
		this.operKey = operKey;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
