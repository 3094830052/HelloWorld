/**
 * 
 */
package com.hxts.unicorn.platform.interfaces;

import java.util.List;

/**
 * @author LENOVO
 * 业务基础模块
 */
public interface IBizModule extends IServiceModule {
	/**
	 * 该模块依赖的其他模块，String参数是AppId，依赖主要为了处理初始化的顺序
	 * @return
	 */
	List<String> getDepends();
	
	/**
	 * 可选的依赖关系即如果存在则依赖，否则忽略
	 * @return
	 */
	List<String> getOptionalDepends();
}
