package com.hxts.unicorn.platform.interfaces.biz.integrationData;

import java.util.List;

import org.springframework.messaging.Message;

import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.platform.interfaces.IBizModule;
import com.hxts.unicorn.platform.interfaces.biz.CarInOutConditionModel;
import com.hxts.unicorn.platform.interfaces.biz.CarInOutRecordInfo;
import com.hxts.unicorn.platform.interfaces.biz.PersonInOutConditionModel;
import com.hxts.unicorn.platform.interfaces.biz.PersonInOutRecordInfo;

public interface IbmsDataManageService extends IBizModule {
	
	public static final String APP_ID = "IBMS_DATA";
	
	public static final String SYSTEM_TYPE = "ibms";
	
	public static final String[] DATA_TYPES = {"car","person","visitor","alarm"};
	
	/**
	 * <通过kafka消费者监听收到的上报记录，将其持久化到数据库中>  
	 * <功能详细描述>
	 * 
	 * @param message
	 * @see [类、类#方法、类#成员]
	 */
	void savePersonInOutRecord(Message<String> message);
	
	void saveCarInOutRecord(Message<String> message);
	
	void saveVisitorInOutRecord(Message<String> message);
	/**
	 * 
	 * IBMS视频告警数据
	 * <功能详细描述>
	 * @param message
	 * @see [类、类#方法、类#成员]
	 */
	void getAlarmMessage(Message<String> message);

	/**
	 * <通过选定的具体开始时间和结束时间来查询该时间区间内的选定人员出入记录，按pass_time降序排序，通过分页插件分页 >
	 * <功能详细描述>
	 * @param pageNum 分页显示所需的每页显示记录数
	 * @param pageSize 分页显示所需的当前页数
	 * @param model 封装了查询人员出入记录时可能有的各种条件。其中有两个条件字段timeCondition和startTime(endTime),
	 * 如果有timeCondition条件不为null,则将其解析成相应的startTime和endTime并覆盖model中原来的数值；如果timeCondition为null,
	 * 以model封装的startTime和endTime作为查询条件。另外还有两个字段position和guardType，两者只能二选一设置具体的查询条件值。
	 * @return 通过分页插件处理后的查询结果
	 * @see [类、类#方法、类#成员]
	 */
	PageInfo<PersonInOutRecordInfo> selectByConditions(int pageNum, int pageSize, PersonInOutConditionModel model);

	PageInfo<CarInOutRecordInfo> selectByConditions(int pageNum, int pageSize, CarInOutConditionModel model);

//	PageInfo<VisitorInOutRecordInfo> selectByConditions(int pageNum, int pageSize, VisitorInOutConditionModel model);
	
	/**
	 * <通过传入的部分关键字查询出包含关键字的所有门禁位置>
	 * <功能详细描述>
	 * @param pageNum 分页显示所需的每页显示记录数
	 * @param pageSize 分页显示所需的当前页数
	 * @param reference 查询门禁位置的关键字
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<String> selectSimilarPositions(int pageNum, int pageSize, String reference);
	
	/**
	 * <定时清除人员出入记录表中中的过期数据，将过期数据转移到历史备份库中,设置的时间戳为每天凌晨00：00：01>
	 * <功能详细描述>
	 * @see [类、类#方法、类#成员]
	 */
	void deleteOverdueRecord();
	
}
