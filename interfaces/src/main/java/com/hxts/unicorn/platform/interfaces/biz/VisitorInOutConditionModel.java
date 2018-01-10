package com.hxts.unicorn.platform.interfaces.biz;

import java.util.Date;



public class VisitorInOutConditionModel {
	/**
	 * 访客身份证号码
	 */
	private String visitorId;
	/**
	 * 进出类型;true表示出，对应表中的1；false表示进，对应表中的0,null表示对进出类型不做要求
	 */
	private Boolean inOut;
	/**
	 * 门禁具体位置
	 */
	private String position;
	/**
	 * 开门方式
	 */
	private String openType;
	/**
	 * 目前设定的时间条件有‘today’、‘yesterday’、‘before yesterday’、‘2d’、‘3d’‘this week’、‘last week’、‘15d’、‘1m’、‘2m’、‘3m’
	 * 对应网页显示选择条件依次为 ‘今天’、‘昨天’、‘前天’、‘2天内’、‘3天内’、‘本周’、‘上周’、‘15天内’、‘1个月内’、‘2个月内’、‘3个月内’
	 */
	private String timeCondition;
	/**
	 * 要查询出入记录的开始时间限制，格式为yyyy-MM-dd HH:mm:ss
	 */
	private Date startTime;
	/**
	 * 要查询出入记录的截止时间限制，格式为yyyy-MM-dd HH:mm:ss
	 */
	private Date endTime;
	public String getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}
	public Boolean getInOut() {
		return inOut;
	}
	public void setInOut(Boolean inOut) {
		this.inOut = inOut;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getOpenType() {
		return openType;
	}
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	public String getTimeCondition() {
		return timeCondition;
	}
	public void setTimeCondition(String timeCondition) {
		this.timeCondition = timeCondition;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
}
