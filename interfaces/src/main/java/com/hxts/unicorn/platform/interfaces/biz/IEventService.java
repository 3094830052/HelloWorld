package com.hxts.unicorn.platform.interfaces.biz;

import java.util.List;
import java.util.Map;

import com.hxts.unicorn.platform.interfaces.IBizModule;

public interface IEventService extends IBizModule {

	public static final String APP_ID = "EVENT";

	public static final String OPER_MODULE = "事件管理";

	int submitEvent(EventItem record);

	List<EventItem> getEvent(Map<String, Object> map, boolean flag);

	EventItem selectById(Integer id);

	int updateState(Integer id);

	// 通过图片相对位置名称判断是不是该用户上传的图片
	Integer selectByCondiction(String pictures);
	
	EventItem selectByIdWithNoControl(Integer id);
}
