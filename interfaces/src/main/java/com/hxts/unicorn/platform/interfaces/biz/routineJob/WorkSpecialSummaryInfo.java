package com.hxts.unicorn.platform.interfaces.biz.routineJob;

import java.util.Map;

/**
 * 
 * <专项工作概要信息>
 * <包括参与人数、工单回复记录数，附件数量等等>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WorkSpecialSummaryInfo {
	/**
	 * 专项工作参与人数
	 */
	private int totalParticipantCount;
	/**
	 * 专项工作回复记录数
	 */
	private int totalResponseCount;
	/**
	 * 专项工作回复记录，根据不同的分类（街道页面根据社区分类，社区根据工单分类）显示
	 */
	private Map<String, Integer> responseCount;
	/**
	 * 根据附件类型（图片、音频、视频）分类显示附件数量
	 */
	private Map<String, Integer> attachCount;
	public int getTotalParticipantCount() {
		return totalParticipantCount;
	}
	public void setTotalParticipantCount(int totalParticipantCount) {
		this.totalParticipantCount = totalParticipantCount;
	}
	public int getTotalResponseCount() {
		return totalResponseCount;
	}
	public void setTotalResponseCount(int totalResponseCount) {
		this.totalResponseCount = totalResponseCount;
	}
	public Map<String, Integer> getResponseCount() {
		return responseCount;
	}
	public void setResponseCount(Map<String, Integer> responseCount) {
		this.responseCount = responseCount;
	}
	public Map<String, Integer> getAttachCount() {
		return attachCount;
	}
	public void setAttachCount(Map<String, Integer> attachCount) {
		this.attachCount = attachCount;
	}
	
	
}
