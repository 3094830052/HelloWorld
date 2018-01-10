package com.hxts.unicorn.platform.interfaces.basic.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hxts.unicorn.platform.interfaces.IBasicModule;

public interface AlarmMessageManageService extends IBasicModule{
	
	public static final String APP_ID = "ALARM_RECORD";
	public static final Map<String, String> LABEL_PERSON_DICT = new HashMap<>();
	public static final Map<String, String> ALARM_TYPE = new HashMap<>();
	
	public List<AlarmSet> getAllAlarmSet();
	
	public void updateAlarmSet(AlarmSet alarmSet);
	
	public void updateAlarmSetDetails(List<AlarmSetDetail> setDetails);
	
	public void modifyDefaultSet (Integer tid);
	
	/**
	 * <1.告警模块用来接收其他三方系统以及综治平台其他内部模块产生的告警消息。传入的告警消息格式为json数据，
	 * 必须包含的字段为alarmSource、alarmType、alarmLocation、alarmTime、alarmDesc
	 * 另外添加如下说明：
	 * {
	 * 		"alarmSource": "目前有 self 表示综治平台自身综合分析数据产生报警、 ibms 表示ibms系统上报告警、
	 * 			rfid表示rfid系统上报告警、 cloudwalk表示云从人脸识别上报告警", 
	 * 		"alarmType": "目前有弱电故障weak_elec、重点人员 label_person、事件 event", 
	 * 		"alarmDesc": "描述报警的事件类型或者故障类型或者重点人员类型,例如：VideoLost、PersonCardNotFit、
	 * 			PersonGatherEvent、emancipist等等", 
	 * 		"alarmLocation": "报警发生的地点",
	 * 		"alarmTime": "报警时间,值为时间对应的毫秒值 ,例如 1513654242321", 
	 * 		"relationPerson": "在重点人员报警中需要添加的信息,重点人员名字", 
	 * 		"identityCard" : "报警信息相关联的身份证信息",
	 * 		"captureImage" : "告警相关局部抓拍图片资源",
	 * 		"fullImage"： "告警相关全景图片资源",
	 * 		"videoUrl"： "告警相关视频地址",
	 * 		"supplement": "补充说明(包括个别报警事件需要的条件说明、弱电故障需要的弱电系统名称)", 
	 * }
	 * 	2.将收到的告警消息经过设置好的告警筛选条件过滤，然后保存到数据库中，并且将告警消息通过消息推送模块推送给 “一张图”、web端
	 * > 
	 * <功能详细描述>
	 * @param alarmMsg
	 * @see [类、类#方法、类#成员]
	 */
	public void receiveAlarm(AlarmMessage alarmMsg);
	
	/**
	 * <一次接受处理多条告警信息，告警消息格式同单条上报情况相同>
	 * <功能详细描述>
	 * @param alarms
	 * @see [类、类#方法、类#成员]
	 */
	public void receiveAlarm(List<AlarmMessage> alarmMsgs);
	
//	/**
//	 * <一句话功能简述>
//	 * <功能详细描述>
//	 * @param alarm
//	 * @see [类、类#方法、类#成员]
//	 */
//	public void receiveAlarm(String alarm);
	
	public AlarmPageInfo<AlarmMsgInfo> getAlarmInfoList(Integer pageNum, Integer pageSize, String alarmType,
			String status, String statusPriority, String bigScreen);
	
	public List<AlarmMsgInfo> getAlarmInfoList(String alarmType, String status, String startTime,
			String statusPriority, String bigScreen);
	
	/**
	 * <根据消息通知得到的记录id去数据库中查询相应的告警消息内容详情。>
	 * <功能详细描述>
	 * @param alarmId
	 * @return 返回的告警消息集,id为告警记录在数据库中的储存主键,content为告警记录内容详情
	 * @see [类、类#方法、类#成员]
	 */
	public AlarmMsgInfo getAlarmInfo(Integer alarmId);
	
	public List<AlarmMsgInfo> getLatestLabelInfos(String startTime, String idno);
	
	public List<AlarmMsgInfo> getPersonalTrace(String startTime,String idno);
	
	public byte[] getImage(Integer alarmId, String imageType);
	
	/**
	 * <当告警记录通过消息推送模块发给 网格员或者坐席，创建工单后，通过该接口更改相应告警记录的状态为“已处理”。>
	 * <功能详细描述>
	 * @param alarmId
	 * @return 修改操作是否成功
	 * @see [类、类#方法、类#成员]
	 */
	public boolean modifyAlarmStatus(Integer alarmId);
	
	/**
	 * <获取指定告警记录对应的局部抓拍图片url.>
	 * <功能详细描述>
	 * @param alarmId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String getCaptureImage(Integer alarmId);
	
	/**
	 * <获取指定告警记录对应的全景图片url.>
	 * <功能详细描述>
	 * @param alarmId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String getFullImage(Integer alarmId);

}
