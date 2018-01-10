package com.hxts.unicorn.platform.interfaces.biz.routineJob;

import java.util.Date;
import java.util.List;

public class WorkSpecialInfo {
	/**
	 * 专项工作id
	 */
    private Integer specialId;
	/**
	 * 专项标签
	 */
    private String labels;
	/**
	 * 发起人
	 */
    private Integer initiator;
	/**
	 * 受理范围
	 */
    private Integer receiveOrganId;
	/**
	 * 专项分类
	 */
    private Byte type;
	/**
	 * 专项优先级别
	 */
    private Byte priorityLevel;
	/**
	 * 专项标题
	 */
    private String title;
	/**
	 * 专项详情
	 */
    private String details;
	/**
	 * 开始日期
	 */
    private Date startTime;
	/**
	 * 结束日期
	 */
    private Date endTime;
	/**
	 * 状态
	 */
    private Byte status;
    /**
	 * 创建时间
	 */
    private Date createTime;
    /**
	 * 附件
	 */
    private List<AttachmentInfo> attachments;
	public Integer getSpecialId() {
		return specialId;
	}
	public void setSpecialId(Integer specialId) {
		this.specialId = specialId;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public Integer getInitiator() {
		return initiator;
	}
	public void setInitiator(Integer initiator) {
		this.initiator = initiator;
	}
	public Integer getReceiveOrganId() {
		return receiveOrganId;
	}
	public void setReceiveOrganId(Integer receiveOrganId) {
		this.receiveOrganId = receiveOrganId;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Byte getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(Byte priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
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
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<AttachmentInfo> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<AttachmentInfo> attachments) {
		this.attachments = attachments;
	}
    
}
