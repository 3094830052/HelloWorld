package com.hxts.unicorn.platform.interfaces.biz.integrationData;

import com.hxts.unicorn.platform.interfaces.IBizModule;

public interface FaceRecognitionDataManageService extends IBizModule {

	public static final String APP_ID = "FACE_RECOGNITION_DATA";
	
	public static final String SYSTEM_TYPE = "faceRecognition";
	
	public static final String DATA_TYPE = "alarmMsg";
	
	/**
	 * <接收人脸识别系统推送的报警消息，补充其他内容然后传递给告警模块进行条件化持久管理>
	 * <功能详细描述>
	 * @param jsonParam
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	public String receiveLabelPersonAlarmMessage(String jsonParam) throws Exception;

	/**
	 * <获取指定报警记录,主键id为alarmId绑定的远程服务器上的图片资源，将其转换成字节数组资源。>
	 * <功能详细描述>
	 * @param alarmId 
	 * @param imageType
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public byte[] getImage(Integer alarmId, String imageType);
}
