package com.hxts.unicorn.platform.interfaces.basic;

import java.util.LinkedHashMap;

/**
 * ajax调用时返回的json数据封装 返回值使用toMapper方法获取
 * 
 * @author zhoujf
 * 
 */
public class JsonResult<T> {
	
	public static JsonResult createResult(){
		JsonResult result = new JsonResult();
		result.setResultCode(ERROR);
		result.setData(null);
		result.setDataReserve1(null);
		result.setDataReserve2(null);
		result.setError("");
		result.setMessage("");
		return result;
	}
	
	public JsonResult(){
		this.resultCode=ERROR;
	}
	
	public JsonResult(boolean flag){
		if(flag)
			this.resultCode=SUCCESS;
		else
			this.resultCode=ERROR;
	}
	
	public static final int SUCCESS = 1000;
	public static final int ERROR = 1001;
	public static final int UNLOGIN = 1002;
	public static final int PAYPASS_NOTEXIST = 1003;
	public static final int UPDATE_APP_VERSION = 1004;
	public static final int LOTTERY_BIZ_EXCEPTION = 1005;
	public static final int PROD_OFF_SHELF=9999;
	public static final int WARN=2001;

	/**
	 * 返回码：
	 */
	private int resultCode;
	private T data;
	private String message;
	private String error;

	// 预留，一般不用
	private Object dataReserve1;
	// 预留，一般不用
	private Object dataReserve2;

	public Object getDataReserve1() {
		return dataReserve1;
	}

	public void setDataReserve1(Object dataReserve) {
		this.dataReserve1 = dataReserve;
	}

	public Object getDataReserve2() {
		return dataReserve2;
	}

	public void setDataReserve2(Object dataReserve) {
		this.dataReserve2 = dataReserve;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.resultCode=ERROR;
		this.error = error;
	}

	/**
	 * 
	 * @return
	 */
	public LinkedHashMap<String, Object> toMapper() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("resultCode", this.resultCode);
		map.put("message", this.message);
		map.put("error", this.error);
		map.put("dataList", this.data);
		map.put("dataReserve1", this.dataReserve1);
		map.put("dataReserve2", this.dataReserve2);
		return map;
	}

	public LinkedHashMap<String, Object> errorMapper(String error) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		if(this.resultCode==0||resultCode==SUCCESS)
			resultCode=ERROR;
		map.put("resultCode", resultCode);
		map.put("error", error);
		return map;
	}
	
	public LinkedHashMap<String, Object> errorMapper(String error,int errorcode) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("resultCode", errorcode);
		map.put("error", error);
		return map;
	}
	

	public LinkedHashMap<String, Object> rejectMapper() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("resultCode", UNLOGIN);
		map.put("error", "尚未登陆，请先登录!");
		return map;
	}
	
	
	public JsonResult error(String error){
		this.message=error;
		this.error=error;
		this.resultCode=ERROR;
		return this;
	}

	public JsonResult error(String error2, int errorcode) {
		this.message=error2;
		this.error=error2;
		this.resultCode=errorcode;
		return this;
	}
}
