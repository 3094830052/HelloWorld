package com.hxts.unicorn.platform.interfaces.basic.integration;

import java.util.Map;

import com.hxts.unicorn.platform.interfaces.IBasicModule;

/**
 * <一句话功能简述> 维护和管理综治平台需要集成 的第三方子系统之间的 身份验证短链接和数据主动上报长链接
 * <功能详细描述>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年9月29日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ISysIntegrationService extends IBasicModule {
	
	public static final String APP_ID = "SYS_INTEGRATION";
	
	/**
	 * <一句话功能简述>在综治平台中注册第三方子系统信息
	 * <功能详细描述>注册第三方子系统信息，在数据库中保存相应的访问地址信息及相应的验证用户名和密码
	 * @param systemInfo
	 * @see [类、类#方法、类#成员]
	 */
	public void addThirdSystem(SystemInfo systemInfo);
	
	/**
	 * <根据输入的第三方系统类型查询所有该类型的注册在案的三方系统信息.>
	 * <功能详细描述>
	 * @param sysType
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public SystemInfo getSystemInfoByType(String sysType);
	
	/**
	 * <向数据库添加一条关于第三方系统上报数据的进度位置描述的记录.>
	 * <功能详细描述>
	 * @param record
	 * @see [类、类#方法、类#成员]
	 */
	public void insertSelectiveRecordKey (RecordInfo recordInfo); 
	
	/**
	 * <获取第三方系统上报数据的进度位置描述key.>
	 * <功能详细描述>
	 * @param sys_id
	 * @param data_type
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public RecordInfo getRecordInfo (Integer sys_id,String data_type);
	
	/**
	 * <更新第三方系统上报数据的进度位置描述key.>
	 * <功能详细描述>
	 * @param recordInfo
	 * @see [类、类#方法、类#成员]
	 */
	public void updateRecordKey(RecordInfo recordInfo);
	
	/**
	 * <一句话功能简述>向系统集成模块发送采集请求
	 * <功能详细描述>
	 * @param sysType 子系统类型，ibms，rfid等
	 * @param dataType 数据类型，例如person,car表示人员记录，车辆记录
	 * @throws Exception 如指定的子系统不存在，将抛出异常
	 * @see [类、类#方法、类#成员]
	 */
	public void requestCollect(String sysType, String dataType)
			throws Exception;
	
	/**
	 * <一句话功能简述>开始启动采集任务
	 * <功能详细描述>
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	public void startCollect() throws Exception;

	/**
	 * <一句话功能简述>停止一个采集任务
	 * <功能详细描述>
	 * @param sysType 子系统类型，ibms，rfid等
	 * @param dataType 数据类型，例如person,car表示人员记录，车辆记录
	 * @throws Exception 如指定的子系统不存在，将抛出异常
	 * @see [类、类#方法、类#成员]
	 */
	public void stopCollect(String sysType, String dataType) throws Exception;

	/**
	 * <一句话功能简述>停止所有采集任务
	 * <功能详细描述>
	 * @throws Exception 失败抛出异常
	 * @see [类、类#方法、类#成员]
	 */
	public void stopAllCollect() throws Exception;
	
	/**
	 * <一句话功能简述>向指定的子系统发送控制命令
	 * @param sysId 子系统id
	 * @param cmd 控制命令
	 * @param params 参数
	 * @throws Exception 失败抛出异常
	 * @see [类、类#方法、类#成员]
	 */
	public void sendControl(int sysId, String cmd, String params) throws Exception;
	
	/**
	 * <一句话功能简述>向一类子系统发送控制命令
	 * @param sysType 子系统类型，ibms，rfid等
	 * @param cmd 控制命令	
	 * @param params 参数
	 * @return 返回失败的子系统及错误信息
	 * @see [类、类#方法、类#成员]
	 */
	public Map<Integer, Exception> sendControl(String sysType, String cmd, String params);
	
}
