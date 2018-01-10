package com.hxts.unicorn.platform.interfaces.biz.routineJob;

import java.util.Date;

/**
 * 
 * <工单处理日志实体类>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OrderLogInfo {
	/**
	 * 工单日志id
	 */
    private Integer orderLogId;
	/**
	 * 工单id
	 */
    private Integer workOrderId;
	/**
	 * 操作人
	 */
    private Integer operator;
	/**
	 * 操作类型
	 */
    private Byte type;
	/**
	 * 回复内容
	 */
    private String content;
	/**
	 * 创建时间
	 */
    private Date createTime;
    /**
	 * 操作人名
	 */
    private String operatorName;
    
    public Integer getOrderLogId() {
        return orderLogId;
    }

    public void setOrderLogId(Integer orderLogId) {
        this.orderLogId = orderLogId;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
}