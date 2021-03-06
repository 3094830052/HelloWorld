package com.hxts.unicorn.modules.resident.model;

import java.io.Serializable;

/**
 * <微信用户积分信息>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ResidentScore implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -58542458675508199L;

	/**
	 * 人口绑定积分ID，唯一标识
	 */
	private Integer residentScoreId;

	/**
	 * 人口基础信息ID
	 */
    private Integer residentBaseId;

    /**
     * 微信用户ID
     */
    private String openId;

    /**
     * 身份证号
     */
    private String idNo;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 联系方式
     */
    private String contact;
    
    /**
     * 审核状态
     */
    private Integer status;
    
    /**
     * 用户积分
     */
    private Integer score;


    public Integer getResidentScoreId() {
        return residentScoreId;
    }

    public void setResidentScoreId(Integer residentScoreId) {
        this.residentScoreId = residentScoreId;
    }

    public Integer getResidentBaseId() {
        return residentBaseId;
    }

    public void setResidentBaseId(Integer residentBaseId) {
        this.residentBaseId = residentBaseId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}




}