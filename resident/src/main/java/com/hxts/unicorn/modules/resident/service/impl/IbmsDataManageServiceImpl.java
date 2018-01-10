package com.hxts.unicorn.modules.resident.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.modules.resident.dao.CarInOutRecordMapper;
import com.hxts.unicorn.modules.resident.dao.CarRegisterMapper;
import com.hxts.unicorn.modules.resident.dao.PersonInOutRecordMapper;
import com.hxts.unicorn.modules.resident.dao.PersonRecordPositionMapper;
import com.hxts.unicorn.modules.resident.dao.VisitorInOutRecordMapper;
import com.hxts.unicorn.modules.resident.model.CarInOutRecord;
import com.hxts.unicorn.modules.resident.model.CarRegister;
import com.hxts.unicorn.modules.resident.model.PersonInOutRecord;
import com.hxts.unicorn.modules.resident.model.VisitorInOutRecord;
import com.hxts.unicorn.modules.resident.util.DateUtil;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.DataDictionaryItem;
import com.hxts.unicorn.platform.interfaces.IExtenalComponentManager;
import com.hxts.unicorn.platform.interfaces.IModuleBroker;
import com.hxts.unicorn.platform.interfaces.IModuleManager;
import com.hxts.unicorn.platform.interfaces.IUnicornFrame;
import com.hxts.unicorn.platform.interfaces.ModuleProperty;
import com.hxts.unicorn.platform.interfaces.basic.integration.ISysIntegrationService;
import com.hxts.unicorn.platform.interfaces.basic.message.AlarmMessage;
import com.hxts.unicorn.platform.interfaces.basic.message.AlarmMessageManageService;
import com.hxts.unicorn.platform.interfaces.biz.CarInOutConditionModel;
import com.hxts.unicorn.platform.interfaces.biz.CarInOutRecordInfo;
import com.hxts.unicorn.platform.interfaces.biz.ICarManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.PersonInOutConditionModel;
import com.hxts.unicorn.platform.interfaces.biz.PersonInOutRecordInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.VisitorInOutRecordInfo;
import com.hxts.unicorn.platform.interfaces.biz.integrationData.IbmsDataManageService;
/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年12月26日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@EnableScheduling
@Transactional
public class IbmsDataManageServiceImpl implements IbmsDataManageService {

	private Logger logger;
	private ISysIntegrationService sysIntegrationService;
	private AlarmMessageManageService alarmMessageManageService;
	private IPersonManageService personManageService;
	private ICarManageService carManageService;

	/**
	 * 业主门禁记录、访客门禁记录以及停车记录有效时间(单位：月)
	 */
	private static final int VALID_TIME = 3;
	
	/**
	 * 业主出入记录人脸匹配度的阈值,低于阈值的记录相应产生一条告警消息
	 */
	private static final int MATCHING_THRESHOLD = 80;

	@Autowired
	private PersonRecordPositionMapper personPositionMapper;
	@Autowired
	private PersonInOutRecordMapper personRecordMapper;
	@Autowired
	private CarInOutRecordMapper carInOutRecordMapper;
	@Autowired
	private CarRegisterMapper carRegisterMapper;
	@Autowired
	private VisitorInOutRecordMapper visitorRecordMapper;

	private static final ModuleProperty PROPERTY;
	static {
		PROPERTY = new ModuleProperty();
		PROPERTY.name = "ibms上报数据管理";
		PROPERTY.author = "";
		PROPERTY.copyright = "";
		PROPERTY.description = "";
		PROPERTY.version = "";
		PROPERTY.review = "";
	}

	@Override
	public List<String> getDepends() {
		List<String> depends = new ArrayList<String>();
		depends.add(IPersonManageService.APP_ID);
//		depends.add(ICarManageService.APP_ID);
		return depends;
	}

	@Override
	public List<String> getOptionalDepends() {
		return null;
	}

	@Override
	public String appId() {
		return APP_ID;
	}

	@Override
	public ModuleProperty getProperty() {
		return PROPERTY;
	}

	/**
	 * <通知集成模块准备上传的记录类型和子系统类型 重载方法> 重载方法
	 * 
	 * @param frame
	 */
	@Override
	public void initialize(IUnicornFrame frame) {
		IModuleManager moduleManager = frame.getModuleManager();
		sysIntegrationService = (ISysIntegrationService) moduleManager.getModule(ISysIntegrationService.APP_ID);
		alarmMessageManageService = (AlarmMessageManageService) moduleManager
				.getModule(AlarmMessageManageService.APP_ID);
		personManageService = (IPersonManageService) moduleManager.getModule(IPersonManageService.APP_ID);
		carManageService = (ICarManageService) moduleManager.getModule(ICarManageService.APP_ID);

		IExtenalComponentManager extenalComponentManager = frame.getExtenalComponentManager();
		logger = (Logger) extenalComponentManager.getLogger();

		personPositionMapper.dropTableAboutPositon("personinout_guard_location");
		personPositionMapper.creatTableAboutPositon("personinout_guard_location");
		List<String> positions = personRecordMapper.selectDistinctPositions();
		if (!positions.isEmpty()) {
			personPositionMapper.addNewPositions(positions);
		}

		try {
			for (String dataType : DATA_TYPES) {
				sysIntegrationService.requestCollect(SYSTEM_TYPE, dataType);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		// alarmManageService =
		// (AlarmMessageManageService)frame.getModuleManager().getModule(AlarmMessageManageService.APP_ID);
		// DataDictionaryItem item = new DataDictionaryItem();
		// item.setDataType("重点人员");
		// item.setDataCode("sick");
		// item.setDataName("麻风病人");
		// alarmManageService.setDataDictionaryItem(item);
		//
		// alarmManageService.deleteDataDictionary("重点人员", "sick");
		//
		// AlarmMessage alarm = new AlarmMessage();
		// alarm.setAlarmSource("ibms");
		// alarm.setAlarmTime(new Date());
		// alarm.setAlarmLocation("天城三小");
		// alarm.setAlarmType("weak_elec");
		// alarm.setSupplement("视频监控");
		// alarm.setAlarmDesc("设备掉线");
		//
		// ObjectMapper obMapper = new ObjectMapper();
		// try {
		// alarmManageService.receiveAlarm(obMapper.writeValueAsString(alarm));
		// } catch (JsonProcessingException e) {
		// e.printStackTrace();
		// }
		// Map<String, String> alarmContent = alarmManageService.getAlarmContent(1);
		// System.out.println(alarmContent);

		logger.info("ibms上报数据管理模块初始化完成！");
	}

	@Override
	public IModuleBroker getModuleBroker() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DataDictionaryItem> getAllDataDictionary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DataDictionaryItem> getDataDictionary(String dataType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataDictionaryItem(DataDictionaryItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteDataDictionary(String dataType, String dataCode) {
		// TODO Auto-generated method stub

	}

	@Override
//	@KafkaListener(containerFactory = "kafkaListenerContainerFactory", topics = {
//			"ibms_person" }, groupId = "personInOut_Record", id = "personInOut")
	public void savePersonInOutRecord(Message<String> message) {
		try {
			/**
			 * 将ibms上报的业主出入信息持久化,保存到数据库中
			 */
			JsonNode jsonNode = new ObjectMapper().readTree(message.getPayload());
			PersonInOutRecord inOutRecord = new PersonInOutRecord();

			inOutRecord.setSysId(jsonNode.get("sysId").asInt());
			String cardId = jsonNode.get("owner_id").asText();
			inOutRecord.setCardId(cardId);
			String inOut = jsonNode.get("inout").asText();
			if ("进".equals(inOut)) {
				inOutRecord.setInOut(false);
			} else if ("出".equals(inOut)) {
				inOutRecord.setInOut(true);
			}
			String position = jsonNode.get("position").asText();
			inOutRecord.setPosition(position);

			Date passTime = DateUtil.convertToDate(jsonNode.get("occu_time").asText());
			inOutRecord.setPassTime(passTime);

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(passTime);
			calendar.add(Calendar.MONTH, VALID_TIME);
			inOutRecord.setDueTime(calendar.getTime());

			String pictureUrl = jsonNode.get("picture_url").asText();
			String deviceId = jsonNode.get("device_id").asText();
			inOutRecord.setDeviceId(deviceId);
			inOutRecord.setOpenType(jsonNode.get("open_type").asText());
			inOutRecord.setPictureUrl(pictureUrl);
			int compResult = jsonNode.get("comp_result").asInt();
			inOutRecord.setCompResult(compResult);

			if (personPositionMapper.checkExistence(position) == null) {
				personPositionMapper.addNewPosition(position);
			}
			personRecordMapper.insertSelective(inOutRecord);

			/**
			 * 检验上报的业主出入记录中相关人是否为重点人员，如果是生成一条告警消息，发送给告警模块进行处理
			 */
			ResidentInfo residentInfo = null;
			try {
				residentInfo = personManageService.getResidentInfo(IPersonManageService.LABEL_BASE, null, cardId,
						false);
			} catch (Exception e) {

			}
			if (residentInfo != null && !residentInfo.getLabels().isEmpty()) {
				Set<String> labels = residentInfo.getLabels().keySet();
				ArrayList<String> list = new ArrayList<>(labels);
				for (String label : labels) {
					if (!AlarmMessageManageService.LABEL_PERSON_DICT.containsKey(label)) {
						list.remove(label);
					}
				}
				if (list.size() > 0) {
					AlarmMessage alarmMsg = new AlarmMessage();
					alarmMsg.setAlarmSource("ibms");
					alarmMsg.setAlarmType("label_person");
					alarmMsg.setAlarmDesc(StringUtils.collectionToDelimitedString(list, "、"));
					alarmMsg.setAlarmLocation(position);
					alarmMsg.setAlarmTime(passTime);
					alarmMsg.setRelationPerson(residentInfo.getB().getName());
					alarmMsg.setIdentityCard(cardId);
					alarmMsg.setDeviceId(deviceId);
					alarmMsg.setCaptureImage(pictureUrl);
					alarmMessageManageService.receiveAlarm(alarmMsg);
				} 
//				else {
//					logger.info("身份证号为" + cardId + "的业主不是重点人员!");
//				}
			}
			
			/**
			 * 检验业主出入记录的人脸匹配值是否低于设定的阈值
			 */
			if (compResult >= 0 && compResult < MATCHING_THRESHOLD) {
				AlarmMessage alarmMsg = new AlarmMessage();
				alarmMsg.setAlarmSource("ibms");
				alarmMsg.setAlarmType("event");
				alarmMsg.setAlarmDesc("人卡不一");
				alarmMsg.setAlarmLocation(position);
				alarmMsg.setAlarmTime(passTime);
				/**
				 * 业主出入时，可以采用 ic卡、身份证、人脸等三种方式开门，如果出入时没用采集到人脸则没有对比结果，结果设为0； 另外ic卡及人脸都与身份证号进行绑定
				 */
				alarmMsg.setRelationPerson(residentInfo.getB().getName());
				alarmMsg.setIdentityCard(cardId);
				alarmMsg.setDeviceId(deviceId);
				alarmMsg.setCaptureImage(pictureUrl);

				alarmMessageManageService.receiveAlarm(alarmMsg);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Override
//	@KafkaListener(containerFactory = "kafkaListenerContainerFactory", topics = {
//			"ibms_car" }, groupId = "carInOut_Record", id = "carInOut")
	public void saveCarInOutRecord(Message<String> message) {
		try {
			JsonNode jsonNode = new ObjectMapper().readTree(message.getPayload());
			CarInOutRecord inOutRecord = new CarInOutRecord();
			inOutRecord.setSysId(jsonNode.get("sysId").asInt());
			Date passTime = DateUtil.convertToDate(jsonNode.get("occu_time").asText());
			inOutRecord.setPassTime(passTime);
			inOutRecord.setCarNumber(jsonNode.get("car_number").asText());
			String inOut = jsonNode.get("inout").asText();
			// false表示进，true表示出
			if ("进".equals(inOut)) {
				inOutRecord.setInOut(false);
			} else if ("出".equals(inOut)) {
				inOutRecord.setInOut(true);
			}
			String position = jsonNode.get("position").asText();
			inOutRecord.setPosition(position);
			inOutRecord.setDeviceId(jsonNode.get("device_id").asText());
			inOutRecord.setPictureUrl(jsonNode.get("picture_url").asText());
			inOutRecord.setOwnerName(jsonNode.get("owner_name").asText());
			inOutRecord.setOwnerTel(jsonNode.get("owner_tel").asText());
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(passTime);
			//测试环节将过期时间设置为天
			calendar.add(Calendar.MONTH, VALID_TIME);
			inOutRecord.setDueTime(calendar.getTime());
			// 记录插入
			if (inOutRecord.getInOut() == false) {
				carInOutRecordMapper.insertSelective(inOutRecord);
			} else {
				List<CarInOutRecord> carInOutRecordList = carInOutRecordMapper
						.selectByPassTime(inOutRecord.getCarNumber());
				if (carInOutRecordList != null && carInOutRecordList.size() > 0) {
					CarInOutRecord carInOutRecord = carInOutRecordList.get(0);
					carInOutRecord.setOutTime(inOutRecord.getPassTime());
					calendar.setTime(inOutRecord.getPassTime());
					calendar.add(Calendar.MONTH, VALID_TIME);
					carInOutRecord.setDueTime(calendar.getTime());
					carInOutRecordMapper.updateByPrimaryKeySelective(carInOutRecord);
				} else {
					inOutRecord.setOutTime(inOutRecord.getPassTime());
					inOutRecord.setPassTime(null);
					carInOutRecordMapper.insertSelective(inOutRecord);
					logger.info("未找到车牌号为:"+inOutRecord.getCarNumber()+"的车辆进入时的信息记录");
					throw new AppModuleErrorException("没有找到该车最近的进入辆记录");
				}
			}
			// 车辆登记
			List<CarRegister> list = carRegisterMapper.selectByCarNumber(inOutRecord.getCarNumber());
			if (list == null || list.size() == 0) {
				carRegisterMapper.insertSelective(getCarRegister(inOutRecord));
			} else {
				int i = 0;
				for (; i < list.size();) {
					if (list.get(i).getOwnerName().equals(inOutRecord.getOwnerName())
							&& list.get(i).getOwnerTel().equals(inOutRecord.getOwnerTel())) {
						break;
					} else {
						i++;
						if (i == list.size()) {
							carRegisterMapper.insertSelective(getCarRegister(inOutRecord));
						}
					}
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
//	@KafkaListener(containerFactory = "kafkaListenerContainerFactory", topics = {
//			"ibms_visitor" }, groupId = "visitorInOut_Record", id = "visitorInOut")
	public void saveVisitorInOutRecord(Message<String> message) {
		try {
			JsonNode jsonNode = new ObjectMapper().readTree(message.getPayload());
			VisitorInOutRecord inOutRecord = new VisitorInOutRecord();
			inOutRecord.setSysId(jsonNode.get("sysId").asInt());
			
			Date passTime = DateUtil.convertToDate(jsonNode.get("occu_time").asText());
			inOutRecord.setPassTime(passTime);

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(passTime);
			calendar.add(Calendar.MONTH, VALID_TIME);
			inOutRecord.setDueTime(calendar.getTime());
			
			inOutRecord.setVisitorName(jsonNode.get("visitor_name").asText());
			inOutRecord.setVisitorId(jsonNode.get("visitor_id").asText());
			inOutRecord.setDeviceId(jsonNode.get("device_id").asText());
			inOutRecord.setOpenType(jsonNode.get("open_type").asText());
			inOutRecord.setOwnerName(jsonNode.get("owner_name").asText());
			inOutRecord.setOwnerRoomId(jsonNode.get("owner_room_id").asText());
			inOutRecord.setPictureUrl(jsonNode.get("picture_url").asText());
			String position = jsonNode.get("position").asText();
			inOutRecord.setPosition(position);
			String inOut=jsonNode.get("inout").asText();
			if("进".equals(inOut)){
				inOutRecord.setInOut(false);;
			}
			else if("出".equals(inOut)){
				inOutRecord.setInOut(true);
			}
			
//			if (visitorPositionMapper.checkExistence(position) == null) {
//				visitorPositionMapper.addNewPosition(position);
//			}
			visitorRecordMapper.insertSelective(inOutRecord);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
//	@KafkaListener(containerFactory = "kafkaListenerContainerFactory", topics = {
//			"ibms_alarm" }, groupId = "videoMonitor_Record", id = "videoMonitor")
	public void getAlarmMessage(Message<String> message) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(message.getPayload());
			ObjectNode objectNode = (ObjectNode) jsonNode;
			AlarmMessage alarmMessage = new AlarmMessage();
			if (jsonNode.isObject()) {
				alarmMessage.setAlarmSource("ibms");
				alarmMessage.setAlarmType(objectNode.get("alarmType").asText());
				alarmMessage.setAlarmLocation(objectNode.get("alarmLocation").asText());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d = simpleDateFormat.parse(objectNode.get("alarmTime").asText());
				alarmMessage.setAlarmTime(d);
				alarmMessage.setAlarmDesc(objectNode.get("alarmDesc").asText());
				alarmMessage.setSupplement(objectNode.get("supplement").asText());
			} else {
				throw new AppModuleErrorException("数据格式不正确");
			}
			alarmMessageManageService.receiveAlarm(alarmMessage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			throw new AppModuleErrorException("时间格式不正确");
		}
	}
	
	/**
	 * <通过选定的具体开始时间和结束时间来查询该时间区间内的选定人员出入记录，按pass_time降序排序，通过分页插件分页 > <功能详细描述>
	 * 
	 * @param pageNum
	 *            分页显示所需的每页显示记录数
	 * @param pageSize
	 *            分页显示所需的当前页数
	 * @param model
	 *            封装了查询人员出入记录时可能有的各种条件。其中有两个条件字段timeCondition和startTime(endTime),
	 *            如果有timeCondition条件不为null,则将其解析成相应的startTime和endTime并覆盖model中原来的数值；如果timeCondition为null,
	 *            以model封装的startTime和endTime作为查询条件。另外还有两个字段position和guardType，两者只能二选一设置具体的查询条件值。
	 *            guardType 只有 小区出入口、单元出入口 两种值
	 * @return 通过分页插件处理后的查询结果
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public PageInfo<PersonInOutRecordInfo> selectByConditions(int pageNum, int pageSize,
			PersonInOutConditionModel model) {
		String timeCondition = model.getTimeCondition();
		if (timeCondition != null) {
			Map<String, Date> timeMap = DateUtil.explainTimeCondition(timeCondition);
			model.setStartTime(timeMap.get("startTime"));
			model.setStartTime(timeMap.get("endTime"));
		}
		if (model.getPosition() != null) {
			model.setGuardType(null);
		}
		PageHelper.startPage(pageNum, pageSize);
		PageHelper.orderBy("pass_time desc");
		List<PersonInOutRecordInfo> list = personRecordMapper.selectByVariableConditions(model);
		PageInfo<PersonInOutRecordInfo> pageInfo = new PageInfo<>(list);

		PageHelper.clearPage();
		return pageInfo;
	}
	
	@Override
	public PageInfo<CarInOutRecordInfo> selectByConditions(int pageNum, int pageSize, CarInOutConditionModel model) {
		String timeCondition = model.getTimeCondition();
		if (timeCondition != null) {
			Map<String, Date> timeMap = DateUtil.explainTimeCondition(timeCondition);
			model.setStartTime(timeMap.get("startTime"));
			model.setStartTime(timeMap.get("endTime"));
		}

		PageHelper.startPage(pageNum, pageSize);
		PageHelper.orderBy("pass_time desc");
		List<CarInOutRecordInfo> list = carInOutRecordMapper.selectByVariableConditions(model);
		PageInfo<CarInOutRecordInfo> pageInfo = new PageInfo<>(list);
		
		PageHelper.clearPage();
		return pageInfo;
	}
	
//	@Override
//	public PageInfo<VisitorInOutRecordInfo> selectByConditions(int pageNum, int pageSize,
//			VisitorInOutConditionModel model) {
//		// TODO Auto-generated method stub
//		String timeCondition = model.getTimeCondition();
//		if (timeCondition != null) {
//			Map<String, Date> timeMap = DateUtil.explainTimeCondition(timeCondition);
//			model.setStartTime(timeMap.get("startTime"));
//			model.setStartTime(timeMap.get("endTime"));
//		}
//		if (model.getPosition() != null) {
//			model.setOpenType(null);                  
//		}
//		PageHelper.startPage(pageNum, pageSize);
//		PageHelper.orderBy("pass_time desc");
//		List<VisitorInOutRecordInfo> list = visitorRecordMapper.selectByVariableConditions(model);
//		return new PageInfo<>(list);
//	}


	@Override
	public List<String> selectSimilarPositions(int pageNum, int pageSize, String reference) {
		PageHelper.startPage(pageNum, pageSize);
		List<String> positions = personPositionMapper.selectSimilarPosition(reference);
		PageHelper.clearPage();
		return positions;
	}

	@Override
	@Scheduled(cron = "1 0 0 ? * *")
	public void deleteOverdueRecord() {
		/**
		 * 人员出入记录的过期数据处理
		 */
		List<PersonInOutRecord> personRecords = personRecordMapper.selectOverdueRecord(new Date());
		if (!personRecords.isEmpty()) {
			List<Integer> recordIds = new ArrayList<>();
			for (PersonInOutRecord personInOutRecord : personRecords) {
				recordIds.add(personInOutRecord.getRecordId());
			}
			personRecordMapper.transferToHistoryBase(personRecords);
			personRecordMapper.deleteByPrimaryKeys(recordIds);
		}
		
		/**
		 * 车辆出入记录的过期数据处理
		 */
		List<CarInOutRecord> carRecords = carInOutRecordMapper.selectOverdueRecord(new Date());
		if (!carRecords.isEmpty()) {
			List<Integer> recordIds = new ArrayList<>();
			for (CarInOutRecord carInOutRecord : carRecords) {
				recordIds.add(carInOutRecord.getRecordId());
			}
			carInOutRecordMapper.transferToHistoryBase(carRecords);
			carInOutRecordMapper.deleteByPrimaryKeys(recordIds);
		}
		
		/**
		 * 访客出入记录的过期数据处理
		 */
		List<VisitorInOutRecord> overdueRecords = visitorRecordMapper.selectOverdueRecord(new Date());
		if (!overdueRecords.isEmpty()) {
			List<Integer> recordIds = new ArrayList<>();
			for (VisitorInOutRecord visitorInOutRecord : overdueRecords) {
				recordIds.add(visitorInOutRecord.getRecordId());
			}
			visitorRecordMapper.transferToHistoryBase(overdueRecords);
			visitorRecordMapper.deleteByPrimaryKeys(recordIds);
		}
		if (logger != null) {
			logger.info("执行一次定时任务： 清除过期ibms上报出入记录数据！");
		}
	}	

	// 获取成车辆登记关键字段，转换成车辆登记记录
	private CarRegister getCarRegister(CarInOutRecord record) {
		CarRegister carRegister = new CarRegister();
		carRegister.setCarNumber(record.getCarNumber());
		carRegister.setOwnerName(record.getOwnerName());
		carRegister.setOwnTel(record.getOwnerTel());
		return carRegister;
	}
	
	@SuppressWarnings("unused")
	private PersonInOutRecord packageEntity(PersonInOutRecordInfo recordInfo) {
		PersonInOutRecord entity = new PersonInOutRecord();
		entity.setRecordId(recordInfo.getRecordId());
		entity.setSysId(recordInfo.getSysId());
		entity.setCardId(recordInfo.getCardId());
		entity.setInOut(recordInfo.getInOut());
		entity.setPassTime(recordInfo.getPassTime());
		entity.setPosition(recordInfo.getPosition());
		entity.setDeviceId(recordInfo.getDeviceId());
		entity.setOpenType(recordInfo.getOpenType());
		entity.setPictureUrl(recordInfo.getPictureUrl());
		entity.setCompResult(recordInfo.getCompResult());

		return entity;
	}
	
	@SuppressWarnings("unused")
	private CarInOutRecord packageEntity(CarInOutRecordInfo recordInfo) {
		CarInOutRecord entity = new CarInOutRecord();
		entity.setRecordId(recordInfo.getRecordId());
		entity.setSysId(recordInfo.getSysId());
		entity.setPassTime(recordInfo.getPassTime());
		entity.setCarNumber(recordInfo.getCarNumber());
		entity.setInOut(recordInfo.getInOut());
		entity.setPosition(recordInfo.getPosition());
		entity.setDeviceId(recordInfo.getDeviceId());
		entity.setPictureUrl(recordInfo.getPictureUrl());
		entity.setOwnerName(recordInfo.getOwnerName());
		entity.setOwnerTel(recordInfo.getOwnerTel());
		entity.setOutTime(recordInfo.getOutTime());
		entity.setDueTime(recordInfo.getDueTime());
		
		return entity;
	}
	
	@SuppressWarnings("unused")
	private VisitorInOutRecord packageEntity(VisitorInOutRecordInfo recordInfo) {
		VisitorInOutRecord entity = new VisitorInOutRecord();
		entity.setRecordId(recordInfo.getRecordId());
		entity.setSysId(recordInfo.getSysId());
		entity.setPassTime(recordInfo.getPassTime());
		entity.setVisitorName(recordInfo.getVisitorName());
		entity.setVisitorId(recordInfo.getVisitorId());
		entity.setDeviceId(recordInfo.getDeviceId());
		entity.setOpenType(recordInfo.getOpenType());
		entity.setOwnerName(recordInfo.getOwnerName());
		entity.setOwnerRoomId(recordInfo.getVisitorRoomId());
		entity.setPictureUrl(recordInfo.getPictureUrl());
		entity.setInOut(recordInfo.getInOut());
		entity.setPosition(recordInfo.getPosition());
		
		return entity;
	}
}
