/*
 * 文 件 名:  ModuleFactory.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月19日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.jarentry.resident;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;

import com.hxts.unicorn.platform.interfaces.IModuleFactory;
import com.hxts.unicorn.platform.interfaces.IServiceModule;
import com.hxts.unicorn.platform.interfaces.IUnicornFrame;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.integrationData.FaceRecognitionDataManageService;
import com.hxts.unicorn.platform.interfaces.biz.integrationData.IbmsDataManageService;
import com.hxts.unicorn.platform.interfaces.biz.integrationData.RfidDataManageService;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年9月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceModuleFactory implements IModuleFactory {
	// @Autowired
	private static ServiceModuleFactory instance = new ServiceModuleFactory();
	private static IUnicornFrame frame;

	public static ServiceModuleFactory getInstance(ApplicationContext context, IUnicornFrame frame) {
		instance.personManageServiceImpl = context.getBean("personManageServiceImpl", IPersonManageService.class);
		instance.houseManageServiceImpl = context.getBean("houseManageServiceImpl", IHouseManageService.class);
		instance.ibmsDataManageServiceImpl = context.getBean("ibmsDataManageServiceImpl", IbmsDataManageService.class);
		instance.faceRecognitionDataManageServiceImpl = context.getBean("faceRecognitionDataManageServiceImpl",
				FaceRecognitionDataManageService.class);
		instance.rfidDataManageServiceImpl = context.getBean("rfidDataManageServiceImpl", RfidDataManageService.class);
		ServiceModuleFactory.frame = frame;
		return instance;
	}

	private IPersonManageService personManageServiceImpl;

	private IHouseManageService houseManageServiceImpl;

	private IbmsDataManageService ibmsDataManageServiceImpl;

	private FaceRecognitionDataManageService faceRecognitionDataManageServiceImpl;

	private RfidDataManageService rfidDataManageServiceImpl;
	
	public static final IUnicornFrame getFramework() {
		return frame;
	}
	/**
	 * 重载方法
	 * 
	 * @return
	 */
	public ArrayList<IServiceModule> getModuleList() {
		ArrayList<IServiceModule> modules = new ArrayList<IServiceModule>();
		modules.add(personManageServiceImpl);
		modules.add(houseManageServiceImpl);
		modules.add(ibmsDataManageServiceImpl);
		modules.add(faceRecognitionDataManageServiceImpl);
		modules.add(rfidDataManageServiceImpl);
		return modules;
	}

}
