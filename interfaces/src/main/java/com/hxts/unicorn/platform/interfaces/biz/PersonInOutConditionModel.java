package com.hxts.unicorn.platform.interfaces.biz;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <用于封装客户端发出请求按条件查询出入记录时所传递的参数> <功能详细描述>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年11月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PersonInOutConditionModel {
	/**
	 * 与房屋绑定的门禁卡号
	 */
	private String cardId;
	/**
	 * 进出类型;true表示出，对应表中的1；false表示进，对应表中的0,null表示对进出类型不做要求
	 */
	private Boolean inOut;
	/**
	 * 门禁具体位置
	 */
	private String position;
	/**
	 * 门禁类型，目前只有“小区出入口”和“单元出入口”两种，null表示对门禁类型不做要求
	 */
	private String guardType;
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

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
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

	public String getGuardType() {
		return guardType;
	}

	public void setGuardType(String guardType) {
		this.guardType = guardType;
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
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
