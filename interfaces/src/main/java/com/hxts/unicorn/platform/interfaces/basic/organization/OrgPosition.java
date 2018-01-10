/*
 * 文 件 名:  OrgPosition.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月30日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.basic.organization;

import java.util.List;

import com.hxts.unicorn.platform.interfaces.basic.SysUserInfo;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年9月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OrgPosition {
	/**
	 * 岗位id, 主键
	 */
	public int posId;
	
	/**
	 * 岗位名称，引用数据字典(综治主任、网格站长、网格员等）
	 */
	public String posName;
	
	/**
	 * 岗位类型id---引用数据字典(综治主任、网格站长、网格员等）
	 */
	public int posTypeId;
	
	/**
	 * 所属组织/部门
	 */
	public int orgId;
	
	public String typeName;
	
	/**
	 * 岗位人员,每个岗位可能有多个人员
	 */
	public List<SysUserInfo> users;
	
}
