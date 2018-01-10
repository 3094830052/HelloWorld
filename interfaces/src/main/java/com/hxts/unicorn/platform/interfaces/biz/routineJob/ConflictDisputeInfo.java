package com.hxts.unicorn.platform.interfaces.biz.routineJob;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class ConflictDisputeInfo implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 268219551460718925L;

	// 事件id
	private Integer id;

	// 事件名称
	private String eventName;
	
	// 字符串版事件发生日期
	private String occurDateApp;

	// 事件发生日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date occurDate;

	// 事件发生街道
	private String street;

	// 事件发生社区
	private String community;

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

	// 事件发生楼栋
	private String buildingName;

	// 事件发生单元
	private String unitNumber;

	// 事件发生楼层
	private String floorNumber;

	// 事件发生房号
	private String houseNumber;

	// 事件规模
	private String eventScale;

	// 事件类别
	private String eventType;

	// 涉及人数
	private Integer involvedPersonNumber;

	// 事件描述
	private String eventDesc;

	// 涉及单位
	private String involvedUnit;
	
	// 字符串化解时限
	private String resolveDateLimitApp;
	

	public String getOccurDateApp() {
		return occurDateApp;
	}

	public void setOccurDateApp(String occurDateApp) {
		this.occurDateApp = occurDateApp;
	}

	public String getResolveDateLimitApp() {
		return resolveDateLimitApp;
	}

	public void setResolveDateLimitApp(String resolveDateLimitApp) {
		this.resolveDateLimitApp = resolveDateLimitApp;
	}

	// 化解时限
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date resolveDateLimit;

	// 化解方式
	private String resolveWay;

	// 化解组织
	private String resolveOrganization;

	// 化解责任人姓名
	private String resolvePersonName;

	// 化解责任人联系方式
	private String resolvePersonTel;

	// 化解是否成功(0:未成功;1:成功)
	private Integer resolveIsSuccess;

	// 化解情况
	private String resolveCondition;

	// 考评日期
	private Date evaluationDate;

	// 考评意见
	private String evaluationSuggestion;

	// 关联的工单id集合
	private String workOrderIds;

	// 矛盾纠纷提交用户id
	private Integer userId;

	// 用户所属网格
	private Integer gridId;

	// 关联的当事人集合
	private List<ConflictDisputePersonInfo> peoples = new ArrayList<ConflictDisputePersonInfo>();

	// 关联的工单集合
	private List<WorkOrderInfo> workOrders = new ArrayList<WorkOrderInfo>();

	// 所属网格名称
	private String gridName;

	public Integer getUserId() {
		return userId;
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGridId() {
		return gridId;
	}

	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}

	public List<ConflictDisputePersonInfo> getPeoples() {
		return peoples;
	}

	public void setPeoples(List<ConflictDisputePersonInfo> peoples) {
		this.peoples = peoples;
	}

	public List<WorkOrderInfo> getWorkOrders() {
		return workOrders;
	}

	public void setWorkOrders(List<WorkOrderInfo> workOrders) {
		this.workOrders = workOrders;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName == null ? null : eventName.trim();
	}

	public Date getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName == null ? null : buildingName.trim();
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber == null ? null : unitNumber.trim();
	}

	public String getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber == null ? null : floorNumber.trim();
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber == null ? null : houseNumber.trim();
	}

	public String getEventScale() {
		return eventScale;
	}

	public void setEventScale(String eventScale) {
		this.eventScale = eventScale == null ? null : eventScale.trim();
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType == null ? null : eventType.trim();
	}

	public Integer getInvolvedPersonNumber() {
		return involvedPersonNumber;
	}

	public void setInvolvedPersonNumber(Integer involvedPersonNumber) {
		this.involvedPersonNumber = involvedPersonNumber;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc == null ? null : eventDesc.trim();
	}

	public String getInvolvedUnit() {
		return involvedUnit;
	}

	public void setInvolvedUnit(String involvedUnit) {
		this.involvedUnit = involvedUnit == null ? null : involvedUnit.trim();
	}

	public Date getResolveDateLimit() {
		return resolveDateLimit;
	}

	public void setResolveDateLimit(Date resolveDateLimit) {
		this.resolveDateLimit = resolveDateLimit;
	}

	public String getResolveWay() {
		return resolveWay;
	}

	public void setResolveWay(String resolveWay) {
		this.resolveWay = resolveWay == null ? null : resolveWay.trim();
	}

	public String getResolveOrganization() {
		return resolveOrganization;
	}

	public void setResolveOrganization(String resolveOrganization) {
		this.resolveOrganization = resolveOrganization == null ? null : resolveOrganization.trim();
	}

	public String getResolvePersonName() {
		return resolvePersonName;
	}

	public void setResolvePersonName(String resolvePersonName) {
		this.resolvePersonName = resolvePersonName == null ? null : resolvePersonName.trim();
	}

	public String getResolvePersonTel() {
		return resolvePersonTel;
	}

	public void setResolvePersonTel(String resolvePersonTel) {
		this.resolvePersonTel = resolvePersonTel == null ? null : resolvePersonTel.trim();
	}

	public Integer getResolveIsSuccess() {
		return resolveIsSuccess;
	}

	public void setResolveIsSuccess(Integer resolveIsSuccess) {
		this.resolveIsSuccess = resolveIsSuccess;
	}

	public String getResolveCondition() {
		return resolveCondition;
	}

	public void setResolveCondition(String resolveCondition) {
		this.resolveCondition = resolveCondition == null ? null : resolveCondition.trim();
	}

	public Date getEvaluationDate() {
		return evaluationDate;
	}

	public void setEvaluationDate(Date evaluationDate) {
		this.evaluationDate = evaluationDate;
	}

	public String getEvaluationSuggestion() {
		return evaluationSuggestion;
	}

	public void setEvaluationSuggestion(String evaluationSuggestion) {
		this.evaluationSuggestion = evaluationSuggestion == null ? null : evaluationSuggestion.trim();
	}

	public String getWorkOrderIds() {
		return workOrderIds;
	}

	public void setWorkOrderIds(String workOrderIds) {
		this.workOrderIds = workOrderIds == null ? null : workOrderIds.trim();
	}

}
