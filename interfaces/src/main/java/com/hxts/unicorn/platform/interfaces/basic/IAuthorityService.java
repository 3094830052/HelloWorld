/*
 * 文 件 名:  IAuthorityService.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月9日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.basic;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hxts.unicorn.platform.interfaces.IBasicModule;
import com.hxts.unicorn.platform.interfaces.AuthorityItem;

/**
 * 权限管理模块接口
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年10月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface IAuthorityService extends IBasicModule {
	public static final String APP_ID = "SYS_AUTH";
	/**
	 * 返回系统中所有权限列表
	 * 按业务模块分类，Map的key为业务模块名称（中文），value为权限列表
	 * @return 分类权限列表
	 * @see AuthorityItem
	 */
	Map<String, List<AuthorityItem>> getAllAuthorityList();
	
	/**
	 * 返回一个用户所具有的所有权限列表
	 * 权限是用户多种岗位类型的合集
	 * @param userid
	 * @param includeTempAuth 是否包含临时授权
	 * @return 权限列表
	 * @see AuthorityItem
	 */
	List<AuthorityItem> getUserAuthority(int userId, boolean includeTempAuth);
	
	/**
	 * 返回一个岗位类型的权限
	 * @param orgDefId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<AuthorityItem> getOrgPositionAuthority(int posDefId);
	
	/**
	 * 返回一个用户在某个组织上单独分配的权限
	 * 专指临时性授权，权限中的expireTime有效
	 * @param userId
	 * @param orgId 组织id
	 * @return
	 * @see AuthorityItem
	 */
	List<AuthorityItem> getUserAuthorityByOrg(int userId, int orgId);
	
	/**
	 * 返回一个用户所有的在组织上单独分配的权限
	 * 专指临时性授权，权限中的expireTime有效
	 * @param userId
	 * @return
	 * @see AuthorityItem
	 */
	Map<Integer, List<AuthorityItem>> getAllUserAuthorityByOrg(int userId);
	
	/**
	 * 验证一个用户是否具有某项权限
	 * 这个接口适用于全局的和组织架构无关的权限判断，否则意义不大
	 * @param userid 用户
	 * @param authKey 权限关键字，对应AuthorityItem.authKey
	 * @return 是否具有权限
	 * @see AuthorityItem
	 */
	boolean checkUserAuthority(int userId, String authKey);
	
	/**
	 * 验证一个用户是否具有在一个组织区域内的某个访问权限
	 * 代码实现参考：
	 * 	IOrganizationService orgService = (IOrganizationService)
	 *		frame.getModuleManager().getModule(IOrganizationService.APP_ID);
	 *  Map<Integer, List<AuthorityItem>> map = getAllUserAuthorityByOrg(userId);
	 *  for (Integer authOrg: map.keySet()) {
	 *  	List<AuthorityItem> auths = map.get(authOrg);
	 *  	if (auths.contains(authKey)){
	 *  		int rel = orgService.checkOrgRelation(authOrg, orgId);
	 *  		if (rel == IOrganizationService.SAME || rel == IOrganizationService.SUPERIOR) {
	 *  			return true;
	 *  		}
	 *  	}
	 *  }
	 *  
	 *	List<OrgPosition> posList = orgService.getUserPosition(user.getUserId());
	 *	for (OrgPosition orgPos: posList) { //遍历用户所在的组织域，确定是否和请求所需域匹配
	 *		if (checkOrgPositionAuthority(orgPos.posTypeId, authKey)) {
	 *			int rel = orgService.checkOrgRelation(orgPos.orgId, orgId);
	 *			if (rel == IOrganizationService.SAME || rel == IOrganizationService.SUPERIOR) {
	 *				return true;
	 *			}
	 *		}
	 *	}
	 *
	 * @param userId
	 * @param orgId 待验证的组织的id
	 * @param authKey 权限关键字，对应AuthorityItem.authKey
	 * @return 是否具有权限
	 * @see AuthorityItem
	 */
	boolean checkUserAuthorityForOrg(int userId, int orgId, String authKey);
	
	/**
	 * 验证一个岗位类型是否具有某项权限
	 * @param orgDefId
	 * @param authKey
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean checkOrgPositionAuthority(int orgDefId, String authKey);
	
	/**
	 * 向岗位授权（更改岗位权限）
	 * 岗位类型id对应于OrgDefinition.defId，指一种岗位类型
	 * 要求必须是岗位，即OrgDefinition.isOrg为false
	 * 授权是全量的，覆盖原有的权限列表
	 * 
	 * @param orgDefId
	 * @param authList 权限key列表
	 * @see OrgDefinition
	 */
	void authorizeOrgPosition(int orgDefId, Collection<String> authList);
	
	/**
	 * 直接给一个用户授权
	 * 通常是临时性授权，指定一个具体的组织范围，权限类型，及过期时间
	 * 权限列表为全量设置，覆盖同一用户在同一组织上原有的授权设置
	 * @param userId
	 * @param orgId
	 * @param authList
	 * @param expire
	 */
	void authorizeUser(int userId, int orgId, Collection<String> authList, Date startTime, Date endTime);
	
	/**
	 * 取消对用户在某个组织上的所有授权
	 * 只可以取消针对用户个人进行的授权，而不包括因岗位获得的权限
	 * @param userId
	 * @param orgId
	 */
	void cancelAuthorizeUser(int userId, int orgId);
	
	/**
	 * 取消对用户在某个组织上的一种权限
	 * 只可以取消针对用户个人进行的授权，而不包括因岗位获得的权限
	 * @param userId
	 * @param orgId
	 * @param authKey
	 */
	void cancelAuthorizeUser(int userId, int orgId, String authKey);
}
