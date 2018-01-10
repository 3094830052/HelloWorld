package com.hxts.unicorn.platform.interfaces.biz.routineJob;

/**
 * 
 * <工单相关人员>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OrderPersonInfo {
	/**
	 * 人员的用户id
	 */
    private Integer person;
    /**
	 * 人员类型
	 */
    private Byte type;
	/**
	 * 该人员对应的工单状态
	 */
    private Byte orderStatus;
    /**
     * 网格id
     */
    private Integer gridId;
    /**
	 * 姓名
	 */
	private String name;
	/**
	 * 身份证号码
	 */
	private String idNo;
	/**
	 * 联系方式
	 */
	private String phone;
	
	
	public Integer getPerson() {
		return person;
	}
	public void setPerson(Integer person) {
		this.person = person;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Byte getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Byte orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getGridId() {
		return gridId;
	}
	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
