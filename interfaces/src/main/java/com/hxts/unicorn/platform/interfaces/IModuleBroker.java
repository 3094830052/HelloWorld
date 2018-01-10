/**
 * 
 */
package com.hxts.unicorn.platform.interfaces;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LENOVO
 * 模块的对外联络Broker接口，负责处理restful请求相关功能
 */
public interface IModuleBroker {
	
	/**
	 *  模块具备的权限列表
	 * @return
	 */
	List<AuthorityItem> getAuthorityList();
	/**
	 * 分析一个URL所需的权限和作用域，如果URL属于该模块处理则返回true，
	 * 否则false。根据request的url以及提交的数据，确定权限类型和作用域。
	 * @param request [in]请求
	 * @param output  [out]存放计算的结果（输出参数）。
	 * @return request是否属于该模块处理
	 */
	boolean checkRequestAuthorityAndScope(HttpServletRequest request, RequestAuthority output);
	
	/**
	 *  返回本模块所有的Controller对象，以便动态注入到SpringMVC框架
	 * @return Controller对象数组
	 */
	//ArrayList<Object> getControllers();
	
//	/**
//	 * 返回该模块在前端的主菜单定义，key=菜单名(中文)，value=菜单URL(当无子菜单时),或空(有子菜单)
//	 * 该方法是否返回菜单，要结合前端的设计策略而定。
//	 * 还要根据当前用户的权限，如无操作该模块的任何权限，返回null。
//	 * 返回null，表示无主菜单或无权限。无主菜单的模块其功能通常会融入其他模块中
//	 * @return 
//	 * @see [类、类#方法、类#成员]
//	 */
//	Map<String, String> getMainMenuDefinition();
//	
//	/**
//	 * 返回该模块在前端的二级子菜单定义。key=菜单名（中文），value=菜单URL
//	 * 要根据当前用户的权限，只返回具备权限的子菜单项。
//	 * 
//	 * @return 菜单项
//	 * @see [类、类#方法、类#成员]
//	 */
//	List<Map.Entry<String, String>> getSubMenuDefinition();
	
	/**
	 * 返回当前用户允许的操作，以便页面以此来启用/禁用操作按钮
	 * 模块要根据当前用户的权限，决定哪些操作可用，返回可用的操作列表
	 * 操作是模块自定义的字符串，做关键字
	 * 常用的操作定义：add,del,edit,view,query,enable,disable,play,download,upload,import,export,relay
	 * 等分别代表新建、删除、编辑、查看、查询、启用、禁用、播放、下载、上传、导入、导出、转发
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<String> getAllowableAction();

	/**
	 * 判断当前用户是否可访问该模块的功能，可访问任一功能即返回true
	 * 该方法是getAllowableAction()的简化，只判断是否有任一功能即返回
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean isAnyAccessible();
}
