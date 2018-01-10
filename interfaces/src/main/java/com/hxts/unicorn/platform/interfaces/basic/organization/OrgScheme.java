/*
 * 文 件 名:  OrgScheme.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月30日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.basic.organization;

import java.util.List;

/**
 * 组织架构的模板定义
 * 规定了组织架构的层级关系，即每个组织类型的下属类型和岗位，及其允许的数量
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年9月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OrgScheme {
	/**
	 * 当前的组织类型或岗位类型
	 */
	public OrgDefinition superOrgDef;
	
	/**
	 * 允许创建的个数，若superOrgDef为组织，则为组织个数，若下级为岗位，则为此岗位允许的人数
	 */
	public int maxNumber;
	
	/**
	 * 下级组织或岗位类型
	 */
	public List<OrgScheme> subSchemes;
}
