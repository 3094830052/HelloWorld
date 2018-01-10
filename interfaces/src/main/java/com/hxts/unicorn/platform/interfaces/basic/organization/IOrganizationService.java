/*
 * 文 件 名:  IOrganizationService.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月28日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.basic.organization;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.hxts.unicorn.platform.interfaces.IBasicModule;
import com.hxts.unicorn.platform.interfaces.GIS.GroupStat;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年9月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface IOrganizationService extends IBasicModule {
	public static final String APP_ID = "SYS_ORG";
	
	public final static int SAME = 0; //相同
	public final static int SUPERIOR = 1; //上级
	public final static int SUBORDINATE = 2; //下级
	public final static int DISCRETE = 3;  //无关
	
	/**
	 * 返回指定id的组织架构。失败抛出异常，如id无效。
	 * @param orgId  组织id，为0表示根
	 * @param includeSubOrg 是否包含下级单位
	 * @param includePosition 是否包含岗位
	 * @param includeUsers 是否包含人员
	 * @return 
	 * @see [类、类#方法、类#成员]
	 */
	OrgStructure getOrgStructure(int orgId, boolean includeSubOrg,
			boolean includePosition, boolean includeUsers);
	
	Map<Integer,String> getAllOrgMap();
	
	List<Integer> getGroupIds(List<Integer> positionIds);
	
	List<Integer> getGroupIds(List<Integer> positionIds, Integer orgId);
	
	List<OrgPosition> getOrgPositions(List<Integer> positionIds);
	
	List<Integer> getSubordinate(Integer userId);
	
	/**
	 * 在指定的组织上添加一个组织，如失败则抛出异常，
	 * 失败的原因可能是和模板约束冲突、组织重复、权限不足等。
	 * @param orgId
	 * @param subOrg
	 * @see [类、类#方法、类#成员]
	 */
	void addOrgStructure(int orgId, OrgStructure subOrg);
	
	/**
	 * 在指定的组织上添加一个岗位，如失败则抛出异常，
	 * 失败的原因可能是和模板约束冲突、岗位重复、权限不足等。
	 * @param orgId
	 * @param subOrg
	 * @see [类、类#方法、类#成员]
	 */
	void addOrgPosition(int orgId, OrgPosition subPos);
	
	/**
	 * 删除一个组织或岗位。会同时删除该组织下的所有下级组织和岗位，
	 * 或必须先删除下级组织才允许删除本级，否则报错。失败会抛出异常，
	 * 如id无效，存在下级组织不允许删除。
	 * @param orgId
	 * @param delSubOrg 是否删除下级单位，如为false且存在下级单位，失败
	 * @see [类、类#方法、类#成员]
	 */
	void delOrgStructure(int orgId, boolean delSubOrg);
	
	/**
	 * 修改一个组织结构的属性。只修改本组织本身属性，不涉及下属单位及岗位
	 * @param org
	 * @see [类、类#方法、类#成员]
	 */
	void setOrgStructure(OrgStructure org); 
	
	/**
	 * 返回某用户所属的岗位，可能有多个
	 * @param userId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<OrgPosition> getUserPosition (int userId);

	
	/**
	 * 返回本组织上一级组织，失败抛出异常，如id无效
	 * @param orgId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	OrgStructure getSuperiorOrg(int orgId);
	
	
	/**
	 * 返回一个岗位所属组织，失败抛出异常，如id无效
	 * @param posId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	OrgStructure getPositionOwnerOrg(int posId);
	
	/**
	 * 判断两个组织是否具有上下级关系。用于权限控制中确定权限范围
	 * 返回的是第一个组织相对于第二个组织的身份
	 * 如参数1是参数2的上级，则返回SUPERIOR（上级）
	 * @param srcOrg 上级组织
	 * @param dstOrg 下级组织
	 * @return 
	 * @see [类、类#方法、类#成员]
	 */
	int checkOrgRelation(int srcOrg, int dstOrg);
	
	/**
	 * 往岗位上添加人员
	 * @param users
	 * @see [类、类#方法、类#成员]
	 */
	void addUserToPosition(int posId, Collection<Integer> users);
	
	/**
	 * 从岗位上移除人员
	 * @param posId
	 * @param users
	 * @see [类、类#方法、类#成员]
	 */
	void removeUserFromPosition(int posId, Collection<Integer> users);
	
	/**
	 * 模糊搜索
	 * 返回满足条件的组织架构以及岗位、人员。不论是匹配人员、组织或岗位，都
	 * 同时返回所属组织和岗位及人员
	 * 
	 * @param str 搜索的关键字
	 * @return 组织架构
	 */
	OrgStructure search(String str);
	
	
	/**
	 * 组织架构/岗位类型（数据字典）
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<OrgDefinition> getOrgDefinition();
	
	/**
	 * 添加组织架构/岗位类型（数据字典）
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @param posDef
	 * @param isOrg 是否组织，否则为岗位
	 * @return 新字典项的id
	 * @see [类、类#方法、类#成员]
	 */
	int addOrgDefinition(String posDef, boolean isOrg);
	
	/**
	 * 删除组织架构/岗位类型（数据字典）的一项。
	 * 如果该项在组织架构中已使用，不允许删除，抛出异常
	 * @param id
	 */
	void delOrgDefinition(int id, boolean isOrg);
	
	/**
	 * 返回组织架构模板
	 * @return
	 * @see OrgDefinition
	 */
	OrgScheme getOrgScheme();
	
	/**
	 * 添加一个组织架构模板项
	 * maxNumber指明允许创建的个数，0不限，否则为个数。如站长1名，网格员1名，
	 * 当子节点为组织时，指子节点个数，当为岗位时，指该岗位允许的人数（岗位本身不能重复）
	 * @param defId 上级类型id，引用OrgDefinition的id
	 * @param maxNumber 允许创建的个数，0不限，否则为个数
     * @param isOrg 是否机构
     * @param defName 名称
	 * @see OrgDefinition
	 */
	void addOrgScheme(int defId, int maxNumber, boolean isOrg, String defName);
	
	/**
	 * 删除一个组织架构模板项
	 * 组织架构模板没有主关键字，用上下级的id唯一确定一条
	 * <功能详细描述>
	 * @param defId
	 * @param isOrg
	 * @return
	 * @see OrgScheme
	 */
	int delOrgSchemeTree(int defId, boolean isOrg);
	
	List<GroupStat> getManagePower(List<String> powerTypes);
}
