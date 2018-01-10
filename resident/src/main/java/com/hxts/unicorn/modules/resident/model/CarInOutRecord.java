package com.hxts.unicorn.modules.resident.model;

import java.util.Date;

/**
 * <车辆出入记录>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CarInOutRecord {
	/**
	 * 车辆出入记录recordId，唯一标识符
	 */
	private Integer recordId;
	/**
	 * 对应表tb_thirdsys‌_info中注册的第三方子系统id
	 */
	private Integer sysId;
	/**
	 * 车辆经过车闸时间yyyy-MM-dd HH:mm:ss
	 */
	private Date passTime;
	/**
	 * 车牌号码
	 */
	private String carNumber;
	/**
	 * 进出类型;true表示出，对应表中的1；false表示进，对应表中的0
	 */
	private Boolean inOut;
	/**
	 * 设备位置
	 */
	private String position;
	/**
	 * 设备ID
	 */
	private String deviceId;
	/**
	 * 出入场照片
	 */
	private String pictureUrl;
	/**
	 * 车主姓名
	 */
	private String ownerName;
	/**
	 * 车主电话
	 */
	private String ownerTel;
	 /**
	  * 
	  * 车辆出小区时间
	  */
	 private Date outTime;
	 /**
	  * 记录过期时间
	  */
	 private Date dueTime;

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerTel() {
		return ownerTel;
	}

	public void setOwnerTel(String ownerTel) {
		this.ownerTel = ownerTel;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getSysId() {
		return sysId;
	}

	public void setSysId(Integer sysId) {
		this.sysId = sysId;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber == null ? null : carNumber.trim();
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
		this.position = position == null ? null : position.trim();
	}

	public Date getDueTime() {
		return dueTime;
	}

	public void setDueTime(Date dueTime) {
		this.dueTime = dueTime;
	}


	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}
}