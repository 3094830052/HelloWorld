package com.hxts.unicorn.platform.interfaces.biz.routineJob;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hxts.unicorn.platform.interfaces.IBizModule;

/**
 * 
 * <日常办公>
 * <包括专项工作和工单中心>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface IRoutineJobService extends IBizModule {
	/**
	 * app_id
	 */
	public static final String APP_ID = "ROUTINE_JOB";
	
	public static final String OPER_MODULE = "日常办公";
	public static final String OPER_TARGET_ORDER= "工单";
	/**
	 * 工单状态：未读 
	 */
	public static final byte WORK_ORDER_STATUS_UNREAD = 1;
	/**
	 * 工单状态：部分已读
	 */
	public static final byte WORK_ORDER_STATUS_PARTIAL_READ = 2;
	/**
	 * 工单状态：已读
	 */
	public static final byte WORK_ORDER_STATUS_READ = 3;
	/**
	 * 工单状态：关闭
	 */
	public static final byte WORK_ORDER_STATUS_CLOSED = 4;
	/**
	 * 工单状态：部分受理中
	 */
	public static final byte WORK_ORDER_STATUS_PARTIAL_HANDLING = 5;
	/**
	 * 工单状态：受理中
	 */
	public static final byte WORK_ORDER_STATUS_HANDLING = 6;
	/**
	 * 工单状态：部分已完结
	 */
	public static final byte WORK_ORDER_STATUS_PARTIAL_END = 7;
	/**
	 * 工单状态：已完结
	 */
	public static final byte WORK_ORDER_STATUS_END = 8;
	
	/**
	 * 附件业务类型：创建时添加的附件
	 */
	public static final byte ATTACH_BUSINESS_TYPE_CREATE = 1;
	/**
	 * 附件业务类型：创建时添加的文档模版
	 */
	public static final byte ATTACH_BUSINESS_TYPE_DOC = 2;
	/**
	 * 附件业务类型：回复时添加的附件
	 */
	public static final byte ATTACH_BUSINESS_TYPE_RESPONSE= 3;
	/**
	 * 人员类型：受理人
	 */
	public static final byte PERSON_TYPE_RECEIVER = 1;
	/**
	 * 人员类型：当事人
	 */
	public static final byte PERSON_TYPE_PARTY = 2;
	/**
	 * 人员类型：协助人
	 */
	public static final byte PERSON_TYPE_ASSISTANT = 3;
	/**
	 * 人员类型：转发人
	 */
	public static final byte PERSON_TYPE_FORWARDER = 4;
	/**
	 * 人员类型：抄送人
	 */
	public static final byte PERSON_TYPE_COPY = 5;
	/**
	 * 日志类型：创建
	 */
	public static final byte LOG_TYPE_CREATE = 1;
	/**
	 * 日志类型：已读
	 */
	public static final byte LOG_TYPE_READ = 2;
	/**
	 * 日志类型：关闭
	 */
	public static final byte LOG_TYPE_CLOSE = 3;
	/**
	 * 日志类型：重启工单
	 */
	public static final byte LOG_TYPE_RESUME = 4;
	/**
	 * 日志类型：开始受理
	 */
	public static final byte LOG_TYPE_HANDLING = 5;
	/**
	 * 日志类型：添加回复
	 */
	public static final byte LOG_TYPE_ADD_RESPONSE = 6;
	/**
	 * 日志类型：添加附件
	 */
	public static final byte LOG_TYPE_ADD_ATTACH = 7;
	/**
	 * 日志类型：添加详细地址
	 */
	public static final byte LOG_TYPE_ADD_ADDRESS = 8;
	/**
	 * 日志类型：添加当事人
	 */
	public static final byte LOG_TYPE_ADD_PARTY = 9;
	/**
	 * 日志类型：转发
	 */
	public static final byte LOG_TYPE_FORWARD = 10;
	/**
	 * 日志类型：请求协助
	 */
	public static final byte LOG_TYPE_REQUEST_ASSISTANCE = 11;
	/**
	 * 日志类型：完结
	 */
	public static final byte LOG_TYPE_END = 12;
	/**
	 * 日志类型：发送提醒
	 */
	public static final byte LOG_TYPE_REMIND = 13;
	/**
	 * 上传附件类型：图片
	 */
	public static final byte FILE_TYPE_PICTURE = 1;
	/**
	 * 上传附件类型：视频
	 */
	public static final byte FILE_TYPE_VIDEO = 2;
	/**
	 * 上传附件类型：音频
	 */
	public static final byte FILE_TYPE_AUDIO = 3;
	/**
	 * 上传附件类型：其它
	 */
	public static final byte FILE_TYPE_OTHER = 4;
	
	/***************************************专项工作 start****************************************************/
	/**
	 * 
	 * <创建专项工作>
	 * <功能详细描述>
	 * @param workSpecialInfo 专项工作信息
	 * @param attachments 附件列表
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean addWorkSpecial(WorkSpecialInfo workSpecialInfo,List<AttachmentInfo> attachments);
	/**
	 * 
	 * <根据给定条件查询专项工作列表>
	 * <功能详细描述>
	 * @param userId 用户id
	 * @param status 订单状态
	 * @param keyWords 专项标题关键字或者标签
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<WorkSpecialInfo> getWorkSpecialByKeyWords(Byte priorityLevel,Byte type,Integer userId,Byte status,String keyWords,Date startDate,Date endDate);
	/**
	 * 
	 * <获取受理人为该用户的所有专项工作列表>
	 * <功能详细描述>
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<WorkSpecialInfo> getMyWorkSpecialList(Integer userId);
	/**
	 * 
	 * <获取专项工作详情>
	 * <功能详细描述>
	 * @param specialId 专项工作id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	WorkSpecialInfo getWorkSpecial(Integer specialId);
	/**
	 * 
	 * <专项工作的概要信息>
	 * <功能详细描述>
	 * @param specialId 专项工作id
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	WorkSpecialSummaryInfo getWorkSpecialSummaryInfo(Integer specialId,Integer userId);
	/**
	 * 
	 * <查询专项工作的回复详情，根据社区分类>
	 * <功能详细描述>
	 * @param specialId 专项工作id
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	Map<String, List<WorkOrderInfo>> getWorkSpecialResponseDetail(Integer specialId,Integer userId);
	/**
	 * 
	 * <查询专项工作的附件详情，根据附件类型（图片、音频、视频）分类>
	 * <功能详细描述>
	 * @param specialId 专项工作id
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	Map<String, List<AttachmentInfo>> getWorkSpecialAttachDetail(Integer specialId,Integer userId);
	/***************************************专项工作 end  ****************************************************/
	/***************************************工单中心 start****************************************************/
	/**
	 * 
	 * <新建工单>
	 * <功能详细描述>
	 * @param order 工单信息
	 * @param attachments 创建工单时添加的附件和文档模版
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	Integer addWorkOrder(WorkOrderInfo order,List<AttachmentInfo> attachments);
	/**
	 * 
	 * <给未读工单发送提醒信息>
	 * <功能详细描述>
	 * @param workOrderId 工单id
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean sendRemindInfo(Integer workOrderId,Integer userId);
	/**
	 * 
	 * <关闭工单>
	 * <工单状态为受理中之前，都可以关闭>
	 * @param workOrderId 工单id
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean closeWorkOrder(Integer workOrderId,Integer userId);
	/**
	 * 
	 * <重启工单>
	 * <将关闭的工单重启>
	 * @param workOrderId 工单id
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean resumeWorkOrder(Integer workOrderId,Integer userId);
	/**
	 * 
	 * <获取工单详情>
	 * <如果工单状态是未读，并且当前用户是受理人，顺便更新状态为已读>
	 * @param workOrderId 工单id
	 * @param userId 用户id
	 * @param isReturnLog 是否返回工单的处理日志
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	WorkOrderInfo getWorkOrderDetail(Integer workOrderId,Integer userId,boolean isReturnLog);
	/**
	 * 
	 * <开始受理工单>
	 * <功能详细描述>
	 * @param workOrderId 工单id
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean startHandlingWorkOrder(Integer workOrderId,Integer userId);
	/**
	 * 
	 * <完结工单>
	 * <功能详细描述>
	 * @param workOrderId 工单id
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean endWorkOrder(Integer workOrderId,Integer userId);
	/**
	 * 
	 * <添加工单回复>
	 * <功能详细描述>
	 * @param workOrderId 工单id
	 * @param userId 用户id
	 * @param content 回复内容
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean addResponse(Integer workOrderId,Integer userId,String content);
	/**
	 * 
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @param workOrderId 工单id
	 * @param userId 用户id
	 * @param street 街道
	 * @param community 社区
	 * @param buildingName 楼栋
	 * @param unitNumber 单元
	 * @param floorNumber 层数
	 * @param houseNumber 房号
	 * @param buildingId 楼栋id
	 * @param houseId 房屋id
	 * @param address 详细地址
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean addAddress(Integer workOrderId,Integer userId,String street,String community,String buildingName,
			Integer unitNumber, Integer floorNumber, Integer houseNumber, Integer buildingId,Integer houseId,String address);
	/**
	 * 
	 * <添加工单回复附件>
	 * <功能详细描述>
	 * @param workOrderId 工单id
	 * @param userId 用户id
	 * @param attachments 附件列表
	 * @param content 文字说明
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean addAttachments(Integer workOrderId,Integer userId,List<AttachmentInfo> attachments,String content);
	/**
	 * 
	 * <添加工单当事人>
	 * <功能详细描述>
	 * @param workOrderId 工单id
	 * @param userId 用户id
	 * @param party 当事人列表
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean addParty(Integer workOrderId,Integer userId,List<OrderPersonInfo> party);
	/**
	 * 
	 * <工单转发>
	 * <功能详细描述>
	 * @param workOrderId 工单id
	 * @param userId 用户id
	 * @param forwarders 转发给谁
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean forward(Integer workOrderId,Integer userId,List<Integer> forwarders);
	/**
	 * 
	 * <请求协助>
	 * <功能详细描述>
	 * @param workOrderId 工单id
	 * @param userId 用户id
	 * @param assistants 协助人
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean requestAssistance(Integer workOrderId,Integer userId,List<Integer> assistants);
	/**
	 * 
	 * <获取发起人为该用户的所有工单>
	 * <功能详细描述>
	 * @param userId 用户id
	 * @param status 工单状态列表
	 * @param infoSources 信息来源
     * @param type 工单类型
     * @param status 工单状态
     * @param orderBys 排序字段列表
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<WorkOrderInfo> getMyInitiatedWorkOrderList(Integer userId,Byte priorityLevel,Byte infoSources,Byte type,Byte status,List<String> orderBys);
	/**
	 * 
	 * <获取受理人为该用户和工单状态为给定状态的所有工单>
	 * <功能详细描述>
	 * @param userId 用户id
	 * @param status 工单状态列表
	 * @param infoSources 信息来源
     * @param type 工单类型
	 * @param orderBys 排序字段
	 * @param isExpired 是否是已逾期，只有在状态是受理中时才有用
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<WorkOrderInfo> getMyReceivedWorkOrderList(Integer userId,List<Byte> status,Byte priorityLevel,Byte infoSources,Byte type,List<String> orderBys,String isExpired);
	/**
	 * 
	 * <获取协助人为该用户的所有工单>
	 * <功能详细描述>
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<WorkOrderInfo> getMyAssistedWorkOrderList(Integer userId);
	/**
	 * 
	 * <获取转发人为该用户的所有工单>
	 * <功能详细描述>
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<WorkOrderInfo> getMyForwardWorkOrderList(Integer userId);
	/**
	 * 
	 * <获取抄送人为该用户的所有工单>
	 * <功能详细描述>
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<WorkOrderInfo> getMyCopiedWorkOrderList(Integer userId);
	/**
     * 
     * <工单查询>
     * <功能详细描述>
     * @param priorityLevel 优先级
     * @param infoSources 信息来源
     * @param title 标题
     * @param initiators 发起人列表
     * @param type 工单类型
     * @param persons 人员列表，如受理人、当事人、抄送人等等
     * @param status 工单状态
     * @param startDate 开始日期
	 * @param endDate 结束日期
     * @param receiverAndStatus 受理人及工单状态
     * @param orderBys 排序字段
     * @param isExpired 是否是已逾期，只有在状态是受理中时才有用
     * @param partyInfo 当事人信息，目前仅支持对当事人姓名模糊查询
     * @return
     * @see [类、类#方法、类#成员]
     */
	List<WorkOrderInfo> getWorkOrderListByKeyWords(Byte priorityLevel,Byte infoSources,String title,List<Integer> initiators,Byte type,
			List<OrderPersonInfo> persons,Byte status,Date startDate,Date endDate,List<OrderPersonInfo> receiverAndStatus,List<String> orderBys,String isExpired,String partyInfo);
	
	/**
	 * 
	 * <查询工单日志>
	 * <功能详细描述>
	 * @param workOrderId 工单id
	 * @param logTypes 筛选的日志类型列表
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<OrderLogInfo> getWorkOrderLogList(Integer workOrderId,List<Byte> logTypes);
	/**
	 * 
	 * <获取附件的详细信息，主要用于下载>
	 * <功能详细描述>
	 * @param attachId 附件id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	AttachmentInfo getDetailAttachmentInfo(Integer attachId);
	/**
	 * 
	 * <工单首页统计>
	 * <功能详细描述>
	 * @param orgIds 选定的机构列表
	 * @param yearMonths 选定的月份
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	Map<String, Object> getWorkOrderHomePageStats(List<Integer> orgIds,List<String> yearMonths);	
	/**
	 * 
	 * <查询该用户底下所有的待处理工单，以网格分组>
	 * <该接口主要面向管理者>
	 * @param userId 用户id
	 * @return key为gridId(网格id) value为数量
	 * @see [类、类#方法、类#成员]
	 */
	Map<Integer, Integer> getPendingCountGroupByGrid(Integer userId);
	/**
	 * 
	 * <查询该用户底下所有的待处理工单，以楼栋分组>
	 * <该接口主要面向网格员>
	 * @param userId 用户id
	 * @return key为楼栋id，value为数量
	 * @see [类、类#方法、类#成员]
	 */
	Map<Integer, Integer> getPendingCountGroupByBuilding(Integer userId);
	/**
	 * 
	 * <获取用户的帮扶次数，按月份分类>
	 * <功能详细描述>
	 * @param startDate 开始日期，格式yyyyMM，例如201701
	 * @param endDate 结束日期，格式yyyyMM，例如201711
	 * @param userId 用户id
	 * @return key为月份，格式yyyyMM，value为次数
	 * @see [类、类#方法、类#成员]
	 */
	Map<String, Integer> getAssistStats(String startDate, String endDate, Integer userId);
	/**
	 * 
	 * <获取时间范围内的所有工单，按工单状态分组>
     * <startDate，endDate都为null时，搜索用户的所有工单>
     * @param userId 用户id
     * @param startDate 开始日期
     * @param endDate 结束日期
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	Map<Integer, Integer> getPersonalStatusStats(Integer userId,Date startDate,Date endDate);
	/**
	 * 
	 * <获取时间范围内的工单完成率和总的工单完成率>
	 * <功能详细描述>
	 * @param userId 用户id
     * @param startDate 开始日期
     * @param endDate 结束日期
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	Map<String, String> getPersonalSummaryStats(Integer userId,Date startDate,Date endDate);
	/**
	 * 
	 * <根据网格id，获取工单的完成数和总数，按网格id分组>
	 * <功能详细描述>
	 * @param gridIds 网格id列表
	 * @param startDate 开始日期
     * @param endDate 结束日期
	 * @return key为gridId，value为数组，数组的长度为工单类型长度的2倍，数组的偶数索引存完成数、奇数索引存总数
	 * @see [类、类#方法、类#成员]
	 */
	Map<Integer, int[]> getWorkOrderCountByGridId(List<Integer> gridIds,Date startDate,Date endDate);
	/***************************************工单中心 end  ****************************************************/
}
