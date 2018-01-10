/*
 * 文 件 名:  ApplicationErrorException.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月29日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform;

import javax.servlet.http.HttpServletResponse;

/**
 * 应用程序业务模块执行中出现错误
 * 比如调用方法失败。该异常信息会以400错误码返回给客户端/浏览器
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年9月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AppModuleErrorException extends RuntimeException {
	private int httpStatusCode = HttpServletResponse.SC_BAD_REQUEST;
	
	public AppModuleErrorException(String message) {
		super(message);
	}
	
	public AppModuleErrorException(int statusCode, String message) {
		super(message);
		httpStatusCode = statusCode;
	}

	/**
	 * 获取 httpStatusCode
	 * @return 返回 httpStatusCode
	 */
	public int getHttpStatusCode() {
		return httpStatusCode;
	}

}
