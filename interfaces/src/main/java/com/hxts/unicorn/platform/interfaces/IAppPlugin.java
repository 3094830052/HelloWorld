/**
 * 
 */
package com.hxts.unicorn.platform.interfaces;

/**
 * @author LENOVO
 * 应用模块
 */
public interface IAppPlugin extends IServiceModule {
	/**
	 * 启用或禁用
	 * @param bEnable
	 */
	void enable(boolean bEnable);
	
	boolean isEnable(); 
}
