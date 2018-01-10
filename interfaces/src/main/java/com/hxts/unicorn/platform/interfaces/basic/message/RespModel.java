package com.hxts.unicorn.platform.interfaces.basic.message;

public class RespModel {

	/**
	 * 应答码（200成功，500失败）
	 */
	private String respCode;

	/**
	 * 应答描述
	 */
	private String respDesc;

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

}
