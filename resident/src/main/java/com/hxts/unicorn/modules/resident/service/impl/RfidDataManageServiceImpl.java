package com.hxts.unicorn.modules.resident.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.DataDictionaryItem;
import com.hxts.unicorn.platform.interfaces.IModuleBroker;
import com.hxts.unicorn.platform.interfaces.IUnicornFrame;
import com.hxts.unicorn.platform.interfaces.ModuleProperty;
import com.hxts.unicorn.platform.interfaces.basic.integration.ISysIntegrationService;
import com.hxts.unicorn.platform.interfaces.basic.message.AlarmMessage;
import com.hxts.unicorn.platform.interfaces.basic.message.AlarmMessageManageService;
import com.hxts.unicorn.platform.interfaces.biz.integrationData.RfidDataManageService;

@Service
@Transactional
public class RfidDataManageServiceImpl implements RfidDataManageService {

	private Logger logger;
	private AlarmMessageManageService alarmMsgService;
	private ISysIntegrationService sysIntegrationService;

	@Override
	public List<String> getDepends() {
		return null;
	}

	@Override
	public List<String> getOptionalDepends() {
		return null;
	}

	@Override
	public String appId() {
		return APP_ID;
	}

	private static final ModuleProperty property;
	static {
		property = new ModuleProperty();
		property.name = "rfid上报数据管理";
		property.author = "";
		property.copyright = "";
		property.description = "";
		property.version = "";
		property.review = "";
	}

	@Override
	public ModuleProperty getProperty() {
		return property;
	}

	@Override
	public void initialize(IUnicornFrame frame) {
		logger = (Logger) frame.getExtenalComponentManager().getLogger();
		alarmMsgService = (AlarmMessageManageService) frame.getModuleManager()
				.getModule(AlarmMessageManageService.APP_ID);
		sysIntegrationService = (ISysIntegrationService) frame.getModuleManager()
				.getModule(ISysIntegrationService.APP_ID);
		
		try {
			for (String dataType : DATA_TYPES) {
				sysIntegrationService.requestCollect(SYSTEM_TYPE, dataType);
			}
		} catch (Exception e) {
			logger.info("rfid上报数据管理模块初始化失败！" + e.getMessage());
		}
		logger.info("rfid上报数据管理模块初始化完成！");
	}

	@Override
	public IModuleBroker getModuleBroker() {
		return null;
	}

	@Override
	public List<DataDictionaryItem> getAllDataDictionary() {
		return null;
	}

	@Override
	public List<DataDictionaryItem> getDataDictionary(String dataType) {
		return null;
	}

	@Override
	public void setDataDictionaryItem(DataDictionaryItem item) {

	}

	@Override
	public void deleteDataDictionary(String dataType, String dataCode) {

	}

	@Override
//	@KafkaListener(containerFactory = "kafkaListenerContainerFactory", topics = {
//			"rfid_alarmMsg" }, groupId = "unMotorizedCarTrack_Record", id = "unMotorizedCarTrack")
	public void getAlarmMsg(Message<String> message) {
		try {
			JsonNode jsonNode = new ObjectMapper().readTree(message.getPayload());
			AlarmMessage alarmMessage = new AlarmMessage();

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date alarmTime = simpleDateFormat.parse(jsonNode.get("alarm_time").asText());
			String phone = jsonNode.get("phone").asText();
			String ownerName = jsonNode.get("owner_name").asText();
			String ownerIdNo = jsonNode.get("owner_idno").asText();
			String position = jsonNode.get("position").asText();
            String licenseNumber = jsonNode.get("license_no").asText();
          
			if (jsonNode.isObject()) {
				alarmMessage.setAlarmSource("rfid");
				alarmMessage.setAlarmType("event");
				alarmMessage.setAlarmDesc("非机动车丢失");
				alarmMessage.setAlarmTime(alarmTime);
				alarmMessage.setAlarmLocation(position);
				alarmMessage.setRelationPerson(ownerName);
				alarmMessage.setIdentityCard(ownerIdNo);
				alarmMessage.setSupplement(phone+"/"+licenseNumber);
			} else {
				throw new AppModuleErrorException("数据格式不正确");
			}
			alarmMsgService.receiveAlarm(alarmMessage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			throw new AppModuleErrorException("时间格式不正确");
		}
	}
}
