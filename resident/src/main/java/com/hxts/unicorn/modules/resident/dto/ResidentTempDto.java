package com.hxts.unicorn.modules.resident.dto;

import java.util.Date;

public class ResidentTempDto {

	/**
	 * 人员基础ID
	 */
	private Integer residentBaseId;
	
	/**
	 * 人口姓名
	 */
	private String name;
	
	/**
	 * 审核状态
	 */
	private Integer status;
	
	/**
	 * 提交此人信息获得分数
	 */
	private Integer score;
	/**
	 *  创建时间
	 */
	private Date date;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getResidentBaseId() {
		return residentBaseId;
	}

	public void setResidentBaseId(Integer residentBaseId) {
		this.residentBaseId = residentBaseId;
	}
	
}
