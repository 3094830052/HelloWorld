package com.hxts.unicorn.platform.interfaces.basic.message;

import java.io.Serializable;

public class AlarmMsgInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String alarmType;

	private String alarmDesc;

	private String alarmTime;

	private String alarmLocation;
	
	private String identityCard;

	private String deviceId;

	private String captureImageUri;

	private String fullImageUri;

	private String content;

	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getAlarmLocation() {
		return alarmLocation;
	}

	public void setAlarmLocation(String alarmLocation) {
		this.alarmLocation = alarmLocation;
	}
	
	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getCaptureImageUri() {
		return captureImageUri;
	}

	public void setCaptureImageUri(String captureImageUri) {
		this.captureImageUri = captureImageUri;
	}

	public String getFullImageUri() {
		return fullImageUri;
	}

	public void setFullImageUri(String getFullImageUri) {
		this.fullImageUri = getFullImageUri;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAlarmDesc() {
		return alarmDesc;
	}

	public void setAlarmDesc(String alarmDesc) {
		this.alarmDesc = alarmDesc;
	}

}
