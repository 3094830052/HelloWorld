package com.hxts.unicorn.platform.interfaces.biz;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <用于封装客户端发出请求按条件查询出入记录时所传递的参数> <功能详细描述>
 * 查询条件
 * @author 姓名 工号
 * @version [版本号, 2017年11月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CarInOutConditionModel {
	/**
	 * 车辆经过时间
	 */
	private String passTime;
	/**
	 * 车牌号码
	 */
	private String carNumber;
	/**
	 * 进出类型;true表示出，对应表中的1；false表示进，对应表中的0,null表示对进出类型不做要求
	 */
	private Boolean inOut;
	/**
	 * 车闸具体位置
	 */
	private String position;
	/**
	 * 目前设定的时间条件有‘today’、‘yesterday’、‘before yesterday’、‘2d’、‘3d’‘this week’、‘last
	 * week’、‘15d’、‘1m’、‘2m’、‘3m’ 对应网页显示选择条件依次为
	 * ‘今天’、‘昨天’、‘前天’、‘2天内’、‘3天内’、‘本周’、‘上周’、‘15天内’、‘1个月内’、‘2个月内’、‘3个月内’
	 */
	/**
	 * 车主姓名
	 */
	private String ownerName;
	/**
	 * 车主电话
	 */
	private String ownerTel;
	
	private String timeCondition;
	/**
	 * 要查询出入记录的开始时间限制，格式为yyyy-MM-dd HH:mm:ss
	 */
	private Date startTime;
	/**
	 * 要查询出入记录的截止时间限制，格式为yyyy-MM-dd HH:mm:ss
	 */
	private Date endTime;

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
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

	public String getOwnerTel() {
		return ownerTel;
	}

	public void setOwnerTel(String ownerTel) {
		this.ownerTel = ownerTel;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}


	public String getPassTime() {
		return passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

}
