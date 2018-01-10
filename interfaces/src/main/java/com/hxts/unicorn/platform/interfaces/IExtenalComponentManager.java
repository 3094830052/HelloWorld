/**
 * 
 */
package com.hxts.unicorn.platform.interfaces;


/**
 * @author LENOVO
 *
 */
public interface IExtenalComponentManager {
	/**
	 * 返回一个openfire服务器的客户端连接对象，拥有openfire系统管理员权限
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	Object getOpenfireConnection();
	/**
	 * 取得kafka的实例
	 * 返回KafkaTemplate
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	Object getKafkaTemplate();

	/**
	 * 取得一个redis服务实例
	 * 返回StringRedisTemplate，如果没有空闲实例，抛出异常RuntimeException
	 * 
	 * @param appId 模块id，每个模块只分配一个redis实例
	 * @return 
	 * @see [类、类#方法、类#成员]
	 */
	Object getStringRedisTemplate(String appId);
	
	/**
	 * 得到日志对象：org.slf4j.Logger
	 * @return org.slf4j.Logger
	 * @see [类、类#方法、类#成员]
	 */
	Object getLogger();
}
