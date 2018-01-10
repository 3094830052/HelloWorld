package com.hxts.unicorn.platform.interfaces.biz;

import java.util.Date;

public class EventItem {
    private Integer id;

    private Integer eventType;

    private String eventName;

    private String eventDesc;

    private String eventTime;

    private String eventPosition;

    private String eventLongitude;

    private String eventLatitude;

    private String pictures;

    private Date createTime;

    private Integer eventState;

    private Integer eventSource;

    private Integer userId;

    private Integer userType;
    
    // 上报人姓名
    private String name;
    
    // 上报人联系方式
    private String tel;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc == null ? null : eventDesc.trim();
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventPosition() {
        return eventPosition;
    }

    public void setEventPosition(String eventPosition) {
        this.eventPosition = eventPosition == null ? null : eventPosition.trim();
    }

    public String getEventLongitude() {
        return eventLongitude;
    }

    public void setEventLongitude(String eventLongitude) {
        this.eventLongitude = eventLongitude == null ? null : eventLongitude.trim();
    }

    public String getEventLatitude() {
        return eventLatitude;
    }

    public void setEventLatitude(String eventLatitude) {
        this.eventLatitude = eventLatitude == null ? null : eventLatitude.trim();
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures == null ? null : pictures.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getEventState() {
        return eventState;
    }

    public void setEventState(Integer eventState) {
        this.eventState = eventState;
    }

    public Integer getEventSource() {
        return eventSource;
    }

    public void setEventSource(Integer eventSource) {
        this.eventSource = eventSource;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}