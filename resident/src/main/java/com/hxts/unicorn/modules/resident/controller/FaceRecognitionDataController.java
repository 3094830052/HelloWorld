package com.hxts.unicorn.modules.resident.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hxts.unicorn.platform.interfaces.basic.message.RespModel;
import com.hxts.unicorn.platform.interfaces.biz.integrationData.FaceRecognitionDataManageService;

@RestController
public class FaceRecognitionDataController {
	
	@Autowired
	private FaceRecognitionDataManageService faceRecognitionDataManageService;
	
	@RequestMapping(value="/face/pushAlarm",method= {RequestMethod.POST})
	public RespModel receiveLabelPersonAlarmMessage(@RequestBody String jsonParam) {
		RespModel respModel = new RespModel();
		try {
			String captureId = faceRecognitionDataManageService.receiveLabelPersonAlarmMessage(jsonParam);
			respModel.setRespCode("200");
			respModel.setRespDesc("综治平台接收报警记录成功,抓拍编号为： " + captureId);
		} catch (Exception e) {
			respModel.setRespCode("500");
			respModel.setRespDesc("综治平台接收报警记录失败,出现异常： " + e.getMessage());
		}
		return respModel;
	}
	
	/**
	 * <获取指定报警记录,主键id为alarmId绑定的远程服务器上的图片资源，将其转换成字节数组资源。>
	 * <功能详细描述>
	 * @param imageType 有两种图片资源：抓拍图片和全景图片；分别用capture和full来区分两种类型
	 * @param alarmId
	 * @param response
	 * @throws IOException
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/m/face/image/{alarmId}/{imageType}/{randomStr}.jpg", method = { RequestMethod.GET })
	public void getImage(@PathVariable(required = true, value = "alarmId") Integer alarmId,
			@PathVariable(required = true, value = "imageType") String imageType, HttpServletResponse response)
			throws IOException {
		byte[] image = faceRecognitionDataManageService.getImage(alarmId, imageType);
		if (image == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} else {
			response.getOutputStream().write(image);
		}
	}
}
