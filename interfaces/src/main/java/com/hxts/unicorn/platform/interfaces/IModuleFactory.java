/**
 * 
 */
package com.hxts.unicorn.platform.interfaces;

/**
 * @author LENOVO
 * 工厂接口定义，每个业务jar包实现一个工厂以便取得业务模块
 */
public interface IModuleFactory {
	java.util.List<IServiceModule> getModuleList();
}
