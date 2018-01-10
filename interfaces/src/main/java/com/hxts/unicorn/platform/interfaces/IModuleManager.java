/**
 * 
 */
package com.hxts.unicorn.platform.interfaces;

/**
 * @author LENOVO
 *
 */
public interface IModuleManager {
	IServiceModule getModule(String app_id);
	java.util.List<IServiceModule> getAllModules();
}
