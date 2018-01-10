/**
 * 
 */
package com.hxts.unicorn.platform.interfaces;

/**
 * @author LENOVO
 *
 */
public interface IUnicornFrame {
	//文件上传的路径,分公开、私有
	//公开的文件可以直接映射到url，私有的文件只能系统本地访问
	public static final String RES_UPLOAD_PUBLIC = "upload.public";
	public static final String RES_UPLOAD_PRIVATE = "upload.private";
	public static final String WEI_APPID = "weixin.appid";
	public static final String WEI_SECRET = "weixin.secret";
	/**
	 * 根据权限向前端提供的操作列表，分别代表新建、删除、编辑、查看、查询、启用、
	 * 禁用、播放、下载、上传、导入、导出、转发
	 * 也可以用于操作日志中作为操作类型
	 */
	public static final String[] ACT_ADD     = {"add", "新建"};
	public static final String[] ACT_DEL     = {"del", "删除"};
	public static final String[] ACT_EDIT    = {"edit", "编辑"};
	public static final String[] ACT_VIEW    = {"view", "查看"};
	public static final String[] ACT_QUERY   = {"query", "查询"};
	public static final String[] ACT_ENABLE  = {"enable", "启用"};
	public static final String[] ACT_DISABLE = {"disable", "禁用"};
	public static final String[] ACT_PLAY    = {"play", "播放"};
	public static final String[] ACT_DOWNLD  = {"download", "下载"};
	public static final String[] ACT_UPLD    = {"upload", "上传"};
	public static final String[] ACT_IMP     = {"import", "导入"};
	public static final String[] ACT_EXP     = {"export", "导出"};
	public static final String[] ACT_RELAY   = {"relay", "转发"};
	public static final String[] ACT_REPLY   = {"reply", "回复"};
	public static final String[] ACT_LOGIN   = {"login", "登录"};
	public static final String[] ACT_LOGOUT  = {"logout", "退出"};
	

	IAppPluginManager getPluginManager();
	IModuleManager getModuleManager();	
	IExtenalComponentManager getExtenalComponentManager();
	
	/**
	 * 处理全局的一些请求所需接口
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	IModuleBroker getGlobalBroker();
	/**
	 * 当前线程的会话
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	UserSession getCurrentSession();
	
	/**
	 * 系统全局设置，参数见常量定义
	 * @param key
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	String getSetting(String key);
	
	boolean isDebugMode();
}
