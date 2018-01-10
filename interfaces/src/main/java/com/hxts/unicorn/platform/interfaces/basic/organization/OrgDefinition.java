/*
 * 文 件 名:  OrgDefinition.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月30日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.basic.organization;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年9月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OrgDefinition {
	/**
	 * 字典项id
	 */
	public int defId;
	/**
	 * 名称（组织类型名或岗位类型名）
	 */
	public String name;
	/**
	 * 是否组织，true为组织，false为岗位
	 */
	public boolean isOrg;
	
}
