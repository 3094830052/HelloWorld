/**
 * 
 */
package com.hxts.unicorn.platform.interfaces;

import java.util.List;

/**
 * @author LENOVO
 *
 */
public interface IAppPluginManager {
	IAppPlugin getModule(String app_id);
	List<IAppPlugin> getAllModules();
}
