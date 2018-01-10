package com.hxts.unicorn.modules.resident.model;

import java.util.Date;

public class VisitorInOutRecord {
	private Integer recordId;

	private Integer sysId;
	/**
	 * 经过门禁时间,表中格式为yyyy-MM-dd HH:mm:ss
	 */
	private Date passTime;

	private String visitorName;

	private String visitorId;

	private String deviceId;

	private String openType;

	private String ownerName;

	private String ownerRoomId;

	private String pictureUrl;

	private Boolean inOut;

	private String position;

	/**
	 * 记录过期时间，格式为yyyy-MM-dd
	 */
	private Date dueTime;

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName == null ? null : visitorName.trim();
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId == null ? null : visitorId.trim();
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId == null ? null : deviceId.trim();
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType == null ? null : openType.trim();
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName == null ? null : ownerName.trim();
	}

	public String getOwnerRoomId() {
		return ownerRoomId;
	}

	public void setOwnerRoomId(String ownerRoomId) {
		this.ownerRoomId = ownerRoomId == null ? null : ownerRoomId.trim();
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position == null ? null : position.trim();
	}

	public Boolean getInOut() {
		return inOut;
	}

	public void setInOut(Boolean inOut) {
		this.inOut = inOut;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	public Date getDueTime() {
		return dueTime;
	}

	public void setDueTime(Date dueTime) {
		this.dueTime = dueTime;
	}

	public Integer getSysId() {
		return sysId;
	}

	public void setSysId(Integer sysId) {
		this.sysId = sysId;
	}

}