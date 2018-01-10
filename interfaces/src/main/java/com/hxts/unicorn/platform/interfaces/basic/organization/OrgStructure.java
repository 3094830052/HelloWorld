/*
 * 文 件 名:  OrgStruct.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月28日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.basic.organization;

import java.util.List;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年9月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OrgStructure {
	/**
	 * 组织id，主键
	 */
	public int orgId;
	/**
	 * 组织名称，默认引用数据字典---（综治中心、网格站、网格等），
	 * 但可修改，如改为某某街道综治中心，某某网格站等
	 */
	public String orgName;
	
	public int defType;
	
	public String orgLevel;
	
	/**
	 * 类型名，引用数据字典，展示用字段无需录入
	 */
	public String typeName;
	
	/**
	 * 下级组织
	 */
	public List<OrgStructure> subOrg;
	
	/**
	 * 机构所有岗位
	 */
	public List<OrgPosition> position;
}
