package com.hxts.unicorn.platform.interfaces.biz.routineJob;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class WorkOrderInfo {
	/**
	 * 工单id
	 */
    private Integer workOrderId;
    /**
	 * 信息来源
	 */
    private Byte infoSources;
    /**
	 * 发起人
	 */
    private Integer initiator;
    /**
	 * 工单类型
	 */
    private Byte type;
    /**
	 * 工单优先级别
	 */
    private Byte priorityLevel;
    /**
	 * 工单标题
	 */
    private String title;
    /**
	 * 工单详情
	 */
    private String details;
    /**
	 * 截止时限
	 */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date deadline;
    /**
     * 街道
     */
    private String street;
    
    /**
	 * 社区
	 */
    private String community;
    /**
     * 楼栋
     */
    private String buildingName;
    
    /**
	 * 单元号
	 */
	private Integer unitNumber;

	/**
	 * 楼层
	 */
	private Integer floorNumber;

	/**
	 * 房间号
	 */
	private Integer houseNumber;
    /**
	 * 楼栋id
	 */
    private Integer buildingId;
    /**
	 * 房屋id
	 */
    private Integer houseId;
    /**
	 * 详细地址
	 */
    private String address;
    /**
	 * 工单状态
	 */
    private Byte status;
    /**
	 * 关联的专项工作id
	 */
    private Integer specialId;
    /**
	 * 创建时间
	 */
    private Date createTime;
    /**
	 * 最新更新状态
	 */
    private String latestUpdates;
    /**
	 * 受理人
	 */
    private List<OrderPersonInfo> receiver;
    /**
	 * 当事人
	 */
    private List<OrderPersonInfo> party;
    /**
	 * 转发人
	 */
    private List<OrderPersonInfo> forwarder;
    /**
	 * 抄送人
	 */
    private List<OrderPersonInfo> copy;
    /**
	 * 附件
	 */
    private List<AttachmentInfo> attachments;
    /**
	 * 文档模版
	 */
    private List<AttachmentInfo> documents;
    /**
     * 工单处理日志
     */
    private List<OrderLogInfo> logInfos;
    /**
     * 发起人人名
     */
    private String initiatorName;
    
	public Integer getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Integer workOrderId) {
		this.workOrderId = workOrderId;
	}
	public Byte getInfoSources() {
		return infoSources;
	}
	public void setInfoSources(Byte infoSources) {
		this.infoSources = infoSources;
	}
	public Integer getInitiator() {
		return initiator;
	}
	public void setInitiator(Integer initiator) {
		this.initiator = initiator;
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
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public Integer getUnitNumber() {
		return unitNumber;
	}
	public void setUnitNumber(Integer unitNumber) {
		this.unitNumber = unitNumber;
	}
	public Integer getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}
	public Integer getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}
	public Integer getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Integer getSpecialId() {
		return specialId;
	}
	public void setSpecialId(Integer specialId) {
		this.specialId = specialId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLatestUpdates() {
		return latestUpdates;
	}
	public void setLatestUpdates(String latestUpdates) {
		this.latestUpdates = latestUpdates;
	}
	public List<OrderPersonInfo> getReceiver() {
		return receiver;
	}
	public void setReceiver(List<OrderPersonInfo> receiver) {
		this.receiver = receiver;
	}
	public List<OrderPersonInfo> getParty() {
		return party;
	}
	public void setParty(List<OrderPersonInfo> party) {
		this.party = party;
	}
	public List<OrderPersonInfo> getForwarder() {
		return forwarder;
	}
	public void setForwarder(List<OrderPersonInfo> forwarder) {
		this.forwarder = forwarder;
	}
	public List<OrderPersonInfo> getCopy() {
		return copy;
	}
	public void setCopy(List<OrderPersonInfo> copy) {
		this.copy = copy;
	}
	public List<AttachmentInfo> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<AttachmentInfo> attachments) {
		this.attachments = attachments;
	}
	public List<AttachmentInfo> getDocuments() {
		return documents;
	}
	public void setDocuments(List<AttachmentInfo> documents) {
		this.documents = documents;
	}
	public List<OrderLogInfo> getLogInfos() {
		return logInfos;
	}
	public void setLogInfos(List<OrderLogInfo> logInfos) {
		this.logInfos = logInfos;
	}
	public String getInitiatorName() {
		return initiatorName;
	}
	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}
}
