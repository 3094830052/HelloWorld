package com.hxts.unicorn.platform.interfaces.basic.message;

import java.util.Date;

public class AlarmMessage {
	private String alarmSource;

	private String alarmType;
	
	private String alarmDesc;

	private String alarmLocation;

	private Date alarmTime;

	private String relationPerson;

	private String identityCard;
	
	private String deviceId;

	private String captureImage;

	private String fullImage;

	private String videoUrl;

	private String supplement;

	public String getAlarmSource() {
		return alarmSource;
	}

	public void setAlarmSource(String alarmSource) {
		this.alarmSource = alarmSource == null ? null : alarmSource.trim();
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType == null ? null : alarmType.trim();
	}

	public String getAlarmLocation() {
		return alarmLocation;
	}

	public void setAlarmLocation(String alarmLocation) {
		this.alarmLocation = alarmLocation == null ? null : alarmLocation.trim();
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getRelationPerson() {
		return relationPerson;
	}

	public void setRelationPerson(String relationPerson) {
		this.relationPerson = relationPerson == null ? null : relationPerson.trim();
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard == null ? null : identityCard.trim();
	}

	public String getAlarmDesc() {
		return alarmDesc;
	}

	public void setAlarmDesc(String alarmDesc) {
		this.alarmDesc = alarmDesc == null ? null : alarmDesc.trim();
	}

	public String getCaptureImage() {
		return captureImage;
	}

	public void setCaptureImage(String captureImage) {
		this.captureImage = captureImage == null ? null : captureImage.trim();
	}

	public String getFullImage() {
		return fullImage;
	}

	public void setFullImage(String fullImage) {
		this.fullImage = fullImage == null ? null : fullImage.trim();
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl == null ? null : videoUrl.trim();
	}

	public String getSupplement() {
		return supplement;
	}

	public void setSupplement(String supplement) {
		this.supplement = supplement == null ? null : supplement.trim();
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}