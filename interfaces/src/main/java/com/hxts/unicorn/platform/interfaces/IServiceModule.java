package com.hxts.unicorn.platform.interfaces;

import java.util.List;

/**
 * 
 * @author LENOVO
 *
 */
public interface IServiceModule {
	/**
	 * 模块唯一标识符
	 * @return 
	 */
	String appId();
	
	/**
	 * 返回模块属性：模块名、描述、作者、版权等信息
	 * @return
	 */
	ModuleProperty getProperty();
	
	/**
	 *  初始化
	 */
	void initialize(IUnicornFrame frame);
	
	/**
	 *  返回模块的Broker对象，Broker管理Controllers
	 * @return
	 */
	IModuleBroker getModuleBroker();
	
	/**
	 * 返回本模块的所有数据字典项
	 * @param dataType TODO
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<DataDictionaryItem> getAllDataDictionary();
	
	/**
	 * 返回本模块某种类型的数据字典项
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<DataDictionaryItem> getDataDictionary(String dataType);
	
	/**
	 * 设置/添加一个数据字典项
	 * @param item
	 * @see [类、类#方法、类#成员]
	 */
	
	void setDataDictionaryItem(DataDictionaryItem item);
	
	/**
	 * 删除一个数据字典项
	 * @param dataType 模块自定义类别
	 * @param dataCode 编码
	 * @see [类、类#方法、类#成员]
	 */
	void deleteDataDictionary(String dataType, String dataCode);

}
