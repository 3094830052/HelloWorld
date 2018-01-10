/*
 * 文 件 名:  UserLocation.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年11月28日
 * 修改内容:  
 */
package com.hxts.unicorn.platform.interfaces.basic;

import java.io.Serializable;
import java.util.Date;

/**
 * <用户地理位置>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UserLocation implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6530860649350571078L;
	
	/**
	 * 主键，唯一
	 */
	private int id;

	/**
	 * 用户ID
	 */
	private int userId;
	
	/**
	 * 经度
	 */
	private String longitude;

	/**
	 * 纬度
	 */
	private String latitude;
	
	/**
	 * 记录时间
	 */
	private Date recordTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
}
