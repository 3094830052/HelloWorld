package com.hxts.unicorn.platform.interfaces.biz.routineJob;

import java.util.List;
import java.util.Map;

import com.hxts.unicorn.platform.interfaces.IBizModule;

public interface IConflictService extends IBizModule {
	public static final String APP_ID = "CONFLICT_DISPUTE";

	Integer addConflict(ConflictDisputeInfo conflictInfo);

	ConflictDisputeInfo selectById(Integer id);

	// flag为true,表示获取个人上报的矛盾纠纷.flag为false,表示获取所有人上报的矛盾纠纷
	List<ConflictDisputeInfo> getConflictList(Map<String, Object> map, boolean flag);

}
