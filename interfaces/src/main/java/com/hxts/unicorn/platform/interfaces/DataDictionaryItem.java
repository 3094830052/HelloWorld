/*
 * 文 件 名:  DataDictionaryItem.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月24日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces;

/**
 * 统一的数据字典数据结构 <功能详细描述>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class DataDictionaryItem {
	public String dataType; // 字典分类,可用中文
	public String dataCode; // 字典编码
	public String dataName; // 字典项中文名

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

}
