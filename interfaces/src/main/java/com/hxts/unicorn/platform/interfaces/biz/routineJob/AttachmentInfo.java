package com.hxts.unicorn.platform.interfaces.biz.routineJob;

import java.util.Date;

public class AttachmentInfo {
	/**
	 * 附件id
	 */
    private Integer attachId;
    /**
	 * 业务类型
	 */
    private Byte businessType;
	/**
	 * 附件名
	 */
    private String attachName;
	/**
	 * 附件文件类型
	 */
    private Byte fileType;
	/**
	 * 附件实际存放位置
	 */
    private String filePath;
	/**
	 * 上传用户id
	 */
    private Integer uploadUserId;
	/**
	 * 上传时间
	 */
    private Date uploadTime;
    /**
	 * 工单日志id
	 */
    private Integer orderLogId;
	public Integer getAttachId() {
		return attachId;
	}
	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}
	public Byte getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Byte businessType) {
		this.businessType = businessType;
	}
	public String getAttachName() {
		return attachName;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	public Byte getFileType() {
		return fileType;
	}
	public void setFileType(Byte fileType) {
		this.fileType = fileType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getUploadUserId() {
		return uploadUserId;
	}
	public void setUploadUserId(Integer uploadUserId) {
		this.uploadUserId = uploadUserId;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Integer getOrderLogId() {
		return orderLogId;
	}
	public void setOrderLogId(Integer orderLogId) {
		this.orderLogId = orderLogId;
	}
  
}
