package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;
import java.util.Date;

public class VisitorInOutRecordInfo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6888502447431183996L;
	/**
	 * 访客出入recordId，唯一标识符
	 */
	private Integer recordId;
	/**
	 * 刷卡时间，表中格式为yyyy-MM-dd HH:mm:ss
	 */
	private Integer sysId;
	
	private Date passTime;
	/**
	 * 访客姓名
	 */
	private String visitorName;
	/**
	 * 持卡人身份证号
	 */
	private String visitorId;
	/**
	 * 刷卡设备ID
	 */
	private String deviceId;

	/**
	 * 开门方式
	 */
	private String openType;
	/**
	 * 持卡人姓名
	 */
	private String ownerName;
	/**
	 * 拜访人 房间号
	 */
	private String visitorRoomId;
	/**
	 * 留影：访客照片
	 */
	private String pictureUrl;
	/**
	 * 访客进出类型
	 */
	private Boolean inOut;

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
	
	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getVisitorRoomId() {
		return visitorRoomId;
	}

	public void setVisitorRoomId(String visitorRoomId) {
		this.visitorRoomId = visitorRoomId;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 门禁具体位置
	 */
	private String position;

}
