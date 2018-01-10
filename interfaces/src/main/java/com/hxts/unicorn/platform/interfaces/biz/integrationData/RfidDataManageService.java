package com.hxts.unicorn.platform.interfaces.biz.integrationData;

import org.springframework.messaging.Message;

import com.hxts.unicorn.platform.interfaces.IBizModule;

public interface RfidDataManageService extends IBizModule {

	public static final String APP_ID = "RFID_DATA";
	
	public static final String SYSTEM_TYPE = "rfid";
	
	public static final String[] DATA_TYPES = {"alarmMsg"};
	
	void getAlarmMsg(Message<String> message);
}
