package com.hxts.unicorn.modules.resident.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.util.DateUtil;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.AuthorityItem;
import com.hxts.unicorn.platform.interfaces.DataDictionaryItem;
import com.hxts.unicorn.platform.interfaces.IModuleBroker;
import com.hxts.unicorn.platform.interfaces.IModuleManager;
import com.hxts.unicorn.platform.interfaces.IUnicornFrame;
import com.hxts.unicorn.platform.interfaces.ModuleProperty;
import com.hxts.unicorn.platform.interfaces.RequestAuthority;
import com.hxts.unicorn.platform.interfaces.basic.IAuthorityService;
import com.hxts.unicorn.platform.interfaces.basic.integration.ISysIntegrationService;
import com.hxts.unicorn.platform.interfaces.basic.integration.RecordInfo;
import com.hxts.unicorn.platform.interfaces.basic.integration.SystemInfo;
import com.hxts.unicorn.platform.interfaces.basic.message.AlarmMessage;
import com.hxts.unicorn.platform.interfaces.basic.message.AlarmMessageManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.ResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.integrationData.FaceRecognitionDataManageService;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2018年1月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Transactional
public class FaceRecognitionDataManageServiceImpl implements FaceRecognitionDataManageService {

//	private static final String FULL_PANORAMA_QUERY = "/cw-afaps/extService/capture/panoramaImg";
	private static final String FULL_IMAGE_QUERY = "/cw-afaps/extService/capture/getCaptureRecord";
	private static final String ALARM_MSGS_QUERY = "/cw-afaps/extService/warningRecord/select";
	private static final String CAMERA_DETAIL_QUERY = "/cw-afaps/extService/camera/select";
	private static final String USER_ID = "c11aa5249fb64ba5bfc10f93e123320a";

	private ISysIntegrationService integrationService;
	private AlarmMessageManageService alarmManageService;
	private IPersonManageService personManageService;
	private static Logger logger;

	private static String faceRecognition_ip;
	private static int faceRecognition_port;
	private static boolean preparedState = false;
	private static final Map<String, String> locations = new HashMap<>();
	
	
	private RecordInfo recordInfo;
	private IModuleBroker broker;

	@Resource(name = "integrationHttpClient")
	private RestTemplate httpClient;

	private static final ModuleProperty property;
	static {
		property = new ModuleProperty();
		property.name = "人脸识别上报数据管理";
		property.author = "";
		property.copyright = "";
		property.description = "";
		property.version = "";
		property.review = "";
	}

	@Override
	public List<String> getDepends() {
		List<String> depends = new ArrayList<String>();
//		depends.add(ISysIntegrationService.APP_ID);
//		depends.add(AlarmMessageManageService.APP_ID);
		depends.add(IPersonManageService.APP_ID);
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
		return property;
	}

	@Override
	public void initialize(IUnicornFrame frame) {
		IModuleManager moduleManager = frame.getModuleManager();
		if (integrationService == null)
			integrationService = (ISysIntegrationService) moduleManager.getModule(ISysIntegrationService.APP_ID);
		if (alarmManageService == null)
			alarmManageService = (AlarmMessageManageService) moduleManager.getModule(AlarmMessageManageService.APP_ID);
		if (personManageService == null)
			personManageService = (IPersonManageService) moduleManager.getModule(IPersonManageService.APP_ID);
		if (logger == null)
			logger = (Logger) frame.getExtenalComponentManager().getLogger();

		SystemInfo systemInfo = integrationService.getSystemInfoByType(SYSTEM_TYPE);
		if (systemInfo == null) {
			throw new AppModuleErrorException(
					"现在综治平台还没有集成类型为 ==" + SYSTEM_TYPE + "==的第三方系统，请先在平台第三方系统集成板块中对该类服务系统注册！！！");
		}
		faceRecognition_ip = systemInfo.getIp();
		faceRecognition_port = systemInfo.getPort();
		/**
		 * 获取人脸识别系统相机部署安装位置信息
		 */
		queryCameraInstallAddr();
		
		/**
		 * 初始化时完成与人脸识别系统报警记录的同步,报警时间alarmTime作为标识上报记录进度顺序的字段,
		 * 存入数据库的格式为yyyy-MM-dd HH:mm:ss
		 */
		recordInfo = integrationService.getRecordInfo(systemInfo.getId(), DATA_TYPE);
		if (recordInfo == null) {
			recordInfo = new RecordInfo();
			recordInfo.setSysId(systemInfo.getId());
			recordInfo.setDataType(DATA_TYPE);
			recordInfo.setLastKey("");
			integrationService.insertSelectiveRecordKey(recordInfo);
		}
		/**
		 * 报警记录更新同步时，分页查询的 单页记录数为 rowsOfPage 设为固定值，当前页数为currentPage默认设置为固定值1，
		 * userId暂时为固定值，设置报警开始条件date_star 格式为yyyy-MM-dd HH:mm:ss ，设置返回结果按升序排列sort 1降序、2升序,默认为降序
		 */
		synchronizeAlarmMsg();
		
		preparedState = true;
		logger.info("人脸识别上报数据管理模块初始化完成！！！");
	}

	private void queryCameraInstallAddr() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			HttpHeaders reqHeader = new HttpHeaders();
			reqHeader.set("Content-Type", "application/json;charset=utf-8");
			Map<String, Object> reqParam = new HashMap<>();
			reqParam.put("userId", USER_ID);
			
			String json = objectMapper.writeValueAsString(reqParam);
			String result = httpClient.postForObject(
					"http://" + faceRecognition_ip + ":" + faceRecognition_port + CAMERA_DETAIL_QUERY,
					new HttpEntity<String>(json, reqHeader), String.class);

			JsonNode resultNode = objectMapper.readTree(result);
			if (resultNode.get("data").get("totalRows").asInt() > 0) {
				JsonNode dataNode = resultNode.get("data").get("datas");
				if (dataNode.isArray()) {
					ArrayNode arrNode = (ArrayNode) dataNode;
					for (JsonNode jsonNode : arrNode) {
						locations.put(jsonNode.get("id").asText(), jsonNode.get("name").asText());
					}
				}
			} else {
				throw new AppModuleErrorException("人脸识别系统没有相机安装位置信息！");
			}
		} catch (Exception e) {
			throw new AppModuleErrorException("无法获取人脸识别系统相机安装位置列表！");
		}
	}
	
	/**
	 * <人脸识别报警记录更新同步.>
	 * <功能详细描述>
	 * @see [类、类#方法、类#成员]
	 */
	private void synchronizeAlarmMsg() {
		ObjectMapper objectMapper = new ObjectMapper();
		HttpHeaders reqHeader = new HttpHeaders();
		reqHeader.set("Content-Type", "application/json;charset=utf-8");
		Map<String, Object> reqParam = new HashMap<>();
		reqParam.put("rowsOfPage", 20);
		reqParam.put("currentPage", 1);
		reqParam.put("userId", USER_ID);
		/**
		 * 设置报警类型，1为黑名单报警、0为陌生人报警
		 */
		reqParam.put("isHit", 1);
		/**
		 * 设置升降序,1降序、2升序,默认为降序
		 */
		reqParam.put("sort", 2);
		while (true) {
			try {
				String lastKey = recordInfo.getLastKey();
				/**
				 * 人脸识别系统关于报警记录查询的开始时间条件设置为  ＞=
				 */
				if (lastKey != null && !"".equals(lastKey)) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(DateUtil.convertToDate(lastKey));
					calendar.add(Calendar.SECOND, 1);
					reqParam.put("date_star", DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
				}
				String json = objectMapper.writeValueAsString(reqParam);
				String result = httpClient.postForObject(
						"http://" + faceRecognition_ip + ":" + faceRecognition_port + ALARM_MSGS_QUERY,
						new HttpEntity<String>(json, reqHeader), String.class);

				JsonNode resultNode = objectMapper.readTree(result);
				if ("500".equals(resultNode.get("respCode").asText())) {
					throw new AppModuleErrorException("获取历史报警记录，同步更新异常！！！");
				}
				/**
				 * 人脸识别系统中历史记录全部已经同步更新
				 */
				if (Integer.parseInt(resultNode.findValuesAsText("totalRows").get(0)) == 0) {
					break;
				}
				
				JsonNode dataNode = resultNode.get("data").get("datas");
				if (dataNode.isArray()) {
					ArrayNode arrayNode = (ArrayNode) dataNode;
					List<AlarmMessage> alarmMsgs = new ArrayList<>();
//					ObjectNode alarmNode = JsonNodeFactory.instance.objectNode();
					for (int i = 0; i < arrayNode.size(); i++) {
						/**
						 * 将告警记录json串处理修饰传给告警模块处理
						 */
						JsonNode jsonNode = arrayNode.get(i);
						String alarmLocation = locations.get(jsonNode.get("cameraId").asText());
						long alarmTime = jsonNode.get("alarmTime").asLong();
						String captureId = jsonNode.has("captureId")? jsonNode.get("captureId").asText():null;
						Map<String, String> resultMap = null;
						try {
							resultMap = getFullImageUrl(objectMapper, captureId);
						} catch (Exception e) {
						}
						if (resultMap == null)
							throw new AppModuleErrorException("获取报警记录抓拍编号为" + captureId + "对应的全景图片及抓拍图url失败！");
						
						String fullImage = resultMap.get("fullImage");
						String captureImage = resultMap.get("captureImage");
						String deviceId = jsonNode.has("cameraId") ? jsonNode.get("cameraId").asText() : null;
						String cardId = jsonNode.has("cardId") ? jsonNode.get("cardId").asText() : null;
						String name = jsonNode.has("name") ? jsonNode.get("name").asText() : null;
						
//						alarmNode.removeAll();
//						alarmNode.put("alarmSource", "faceRecognition");
//						alarmNode.put("alarmType", "label_person");
//						alarmNode.put("alarmLocation", alarmLocation);
//						alarmNode.put("alarmTime", alarmTime);
//						alarmNode.put("relationPerson", jsonNode.get("name").asText());
//						alarmNode.put("identityCard", cardId);
//						alarmNode.put("captureImage", captureImage);
//						alarmNode.put("fullImage", fullImage);
						
						AlarmMessage alarmMsg = new AlarmMessage();
						alarmMsg.setAlarmSource("faceRecognition");
						alarmMsg.setAlarmType("label_person");
						alarmMsg.setAlarmLocation(alarmLocation);
						alarmMsg.setAlarmTime(new Date(alarmTime));
						alarmMsg.setRelationPerson(name);
						alarmMsg.setIdentityCard(cardId);
						alarmMsg.setDeviceId(deviceId);
						alarmMsg.setCaptureImage(captureImage);
						alarmMsg.setFullImage(fullImage);
						
						ResidentInfo residentInfo = null;
						try {
							residentInfo = personManageService.getResidentInfo(IPersonManageService.LABEL_BASE, null, cardId, false);
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
							alarmMsg.setAlarmDesc(StringUtils.collectionToDelimitedString(list, "、"));
//							alarmNode.put("alarmDesc", StringUtils.collectionToDelimitedString(list, "、"));
						} else {
							alarmMsg.setAlarmDesc(IPersonManageService.LABEL_KEY_PERSON);
//							alarmNode.set("alarmDesc", null);
							logger.info("重点人员告警信息中包含的身份证号" + cardId + "查不到相应的特殊标签信息！");
						}
						
						alarmMsgs.add(alarmMsg);
						/**
						 * 将json格式的人脸告警记录传递给告警模块处理,并且更新接收报警记录的位置location_key
						 */
						if (i == arrayNode.size() - 1) {
							try {
								alarmManageService.receiveAlarm(alarmMsgs);
//								System.out.println(alarmMsgs);
								
								recordInfo
										.setLastKey(DateUtil.convertToStr(new Date(alarmTime), "yyyy-MM-dd HH:mm:ss"));
								integrationService.updateRecordKey(recordInfo);
							} catch (Exception e) {
								logger.info(e.getMessage());
							}
						}
					}
				}
			} catch (Exception e) {
				throw new AppModuleErrorException("获取历史报警记录，同步更新操作失败！！！");
			}
		}
	}
	
	private static final AuthorityItem AUTH_ACCESS = new AuthorityItem("FaceRecog_Data_Accessibility", "查看人脸识别报警消息",
			"主要涉及：获取报警相关的抓拍照片和全景照片");
	
	class FaceRecogDataManageServiceBroker implements IModuleBroker {

		@Override
		public List<AuthorityItem> getAuthorityList() {
			return Arrays.asList(AUTH_ACCESS);
		}

		@Override
		public boolean checkRequestAuthorityAndScope(HttpServletRequest request, RequestAuthority output) {
			if (output == null) return false;
			String uri = request.getRequestURI();
			if (uri != null) {
				if (uri.startsWith("/m/face/image") && uri.split("/").length >= 5) {
					output.needAuthorize = true;
					output.orgnizId = 0;
					output.authKey = AUTH_ACCESS.authKey;
					return true;
				}
			}
			return false;
		}

		@Override
		public List<String> getAllowableAction() {
			List<String> functions = new ArrayList<>();
			try {
				IUnicornFrame frame = ServiceModuleFactory.getFramework();
				int userId = frame.getCurrentSession().getCurrentSysUser().getUserId();
				IAuthorityService authorityService = (IAuthorityService) frame.getModuleManager()
						.getModule(IAuthorityService.APP_ID);
				
				List<AuthorityItem> authorities = authorityService.getUserAuthority(userId, true);
				for (AuthorityItem item : authorities) {
					if (item.getAuthKey().equals(AUTH_ACCESS.authKey)) {
						functions.add(IUnicornFrame.ACT_VIEW[0]);
					}
				}
			} catch (Exception e) {
				throw new AppModuleErrorException("系统错误，" + e.getMessage());
			}
			return functions;
		}

		@Override
		public boolean isAnyAccessible() {
			IUnicornFrame frame = ServiceModuleFactory.getFramework();
			int userId = frame.getCurrentSession().getCurrentSysUser().getUserId();
			IAuthorityService authorityService = (IAuthorityService) frame.getModuleManager()
					.getModule(IAuthorityService.APP_ID);
			List<AuthorityItem> authorities = authorityService.getUserAuthority(userId, true);
			return !authorities.isEmpty();
		}
		
	}
	
	@Override
	public IModuleBroker getModuleBroker() {
		if (broker == null) {
			broker = new FaceRecogDataManageServiceBroker();
		}
		return broker;
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
	public String receiveLabelPersonAlarmMessage(String jsonParam) throws Exception {
		
		if (preparedState) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode paramNode = objectMapper.readTree(jsonParam);
			if (paramNode == null) {
				throw new AppModuleErrorException("推送的信息为空！");
			}
			
			List<String> alertTypes = paramNode.findValuesAsText("alertType");
			/**
			 * 	alertType报警类型，1为黑名单报警、0为陌生人报警
			 * 	报警阈值设置的是0.9 然而对比了库里面的相似度最高的也只有0.4XXX 
			 * 	人脸识别系统识别为陌生人，此时对该报警数据不做处理
			 */
			if (alertTypes.contains("0")) {
//				logger.info("接收人脸识别系统报警信息为陌生人报警！");
				throw new AppModuleErrorException("综治平台对陌生人报警不做处理！");
			}
			
			String captureId = paramNode.get("captureId").asText();
			logger.info("接收人脸识别系统报警信息： 抓拍id为 " + captureId);
			
			Map<String, String> resultMap = null;
			try {
				resultMap = getFullImageUrl(objectMapper, captureId);
//				logger.info("接收人脸识别系统报警信息： 全景图片地址为 " + fullPanoramaUrl);
			} catch (Exception e) {
				
			}
			if (resultMap == null)
				throw new AppModuleErrorException("获取报警记录时抓拍编号为" + captureId + "对应的全景图片及抓拍图url失败！");

//			String fullImage = downLoadImage(fullImageUrl);
//			if (fullImage == null)
//				throw new AppModuleErrorException("获取报警记录时抓拍编号为" + captureId + "对应的全景图片失败！");

//			ObjectNode resultNode = JsonNodeFactory.instance.objectNode();
//			resultNode.put("fullImage", fullImage);
//			resultNode.put("alarmSource", "faceRecognition");
//			resultNode.put("alarmType", "label_person");
//			resultNode.put("alarmLocation", locations.get(paramNode.get("cameraId").asText()));
//			resultNode.put("alarmTime", paramNode.get("time").asText());
//			resultNode.put("captureImage", paramNode.get("faceImage").asText());
			
			AlarmMessage alarmMsg = new AlarmMessage();
			alarmMsg.setAlarmSource("faceRecognition");
			alarmMsg.setAlarmType("label_person");
			alarmMsg.setAlarmLocation(locations.get(paramNode.get("cameraId").asText()));
			alarmMsg.setAlarmTime(new Date(paramNode.get("time").asLong()));
			alarmMsg.setDeviceId(paramNode.get("cameraId").asText());
			alarmMsg.setCaptureImage(resultMap.get("captureImage"));
			alarmMsg.setFullImage(resultMap.get("fullImage"));
			/**
			 * 在上报的报警记录中添加关联人员的重点人员标签信息
			 */
			addPersonLabels(paramNode, alarmMsg);
			/**
			 * 将json格式的人脸告警记录传递给告警模块处理,并且更新接收报警记录的位置location_key
			 */
			try {
				alarmManageService.receiveAlarm(alarmMsg);

				recordInfo.setLastKey(DateUtil.convertToStr(alarmMsg.getAlarmTime(), "yyyy-MM-dd HH:mm:ss"));
				integrationService.updateRecordKey(recordInfo);
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			return captureId;
		} else {
			throw new AppModuleErrorException("综治平台启动还没有完成初始化，不接收上报告警信息！！！");
		}
	}
	
	/**
	 * <根据上报的报警记录中的抓拍id通过http请求获取对应的全景图片url以及抓拍图片url。>
	 * <功能详细描述>
	 * @param objectMapper
	 * @param captureId 人脸识别系统抓拍id
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	private Map<String, String> getFullImageUrl(ObjectMapper objectMapper, String captureId) throws Exception {
		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.set("Content-Type", "application/json;charset=utf-8");

		Map<String, String> requestParam = new HashMap<>();
		requestParam.put("userId", USER_ID);
		requestParam.put("captureId", captureId);

		String json = objectMapper.writeValueAsString(requestParam);
//		ResponseEntity<String> responseEntity = httpClient.postForEntity(
//				"http://" + faceRecognition_ip + ":" + faceRecognition_port + FULL_PANORAMA_QUERY,
//				new HttpEntity<String>(json, requestHeader), String.class);
		ResponseEntity<String> responseEntity = httpClient.postForEntity(
				"http://" + faceRecognition_ip + ":" + faceRecognition_port + FULL_IMAGE_QUERY,
				new HttpEntity<String>(json, requestHeader), String.class);
		JsonNode result = objectMapper.readTree(responseEntity.getBody());
//		if ("500".equals(result.get("respCode").asText())) {
//			throw new AppModuleErrorException("获取报警记录时抓拍编号为" + captureId + "对应的全景图片及抓拍图片失败！");
//		}
		Map<String, String> resultMap = null;
		JsonNode dataNode = result.get("data");
		if (dataNode != null) {
			resultMap = new HashMap<>();
			resultMap.put("captureImage", dataNode.get("fullFaceUrl").asText());
			resultMap.put("fullImage", dataNode.get("fullPanoramaUrl").asText());
		}
		return resultMap;
	}

	private void addPersonLabels(JsonNode paramNode, AlarmMessage alarmMsg) {
		/**
		 * 在每个符合抓拍的底库人脸信息中增加相应的重点人员标签
		 */
		JsonNode listNode = paramNode.get("list");
		if (listNode != null && listNode.isArray()) {
			ArrayNode arrNode = (ArrayNode)listNode;
			if (arrNode.size() > 1) {
				StringBuilder nameSb = new StringBuilder();
				StringBuilder cardSb = new StringBuilder();
				for (int i = 0; i < arrNode.size(); i++) {
					if (i > 0) {
						nameSb.append("或");
						cardSb.append("、");
					}
					JsonNode sonNode = arrNode.get(i);
					nameSb.append(sonNode.has("name")?sonNode.get("name").asText():null);
					cardSb.append(sonNode.has("cardId") ? sonNode.get("cardId").asText() : null);
				}
				alarmMsg.setRelationPerson(nameSb.toString());
				alarmMsg.setIdentityCard(cardSb.toString());
				alarmMsg.setAlarmDesc(IPersonManageService.LABEL_KEY_PERSON);
			} else {
				JsonNode jsonNode = arrNode.get(0);
				if (jsonNode != null) {
					String cardId = jsonNode.has("cardId") ? jsonNode.get("cardId").asText() : null;
					alarmMsg.setIdentityCard(cardId);
					alarmMsg.setRelationPerson(jsonNode.has("name") ? jsonNode.get("name").asText() : null);
					ResidentInfo residentInfo = null;
					try {
						residentInfo = personManageService.getResidentInfo(IPersonManageService.LABEL_BASE, null, cardId, false);
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
						alarmMsg.setAlarmDesc(StringUtils.collectionToDelimitedString(list, "、"));
					} else {
						alarmMsg.setAlarmDesc(IPersonManageService.LABEL_KEY_PERSON);
						logger.info("重点人员告警信息中包含的身份证号" + cardId + "查不到相应的特殊标签信息！");
					}
				}
			}
		}
	}

	@Override
	public byte[] getImage(Integer alarmId, String imageType) {
		String imageUrl = null;
		byte[] byteImage = null;
		if ("capture".equals(imageType)) {
			imageUrl = alarmManageService.getCaptureImage(alarmId);
		} else if ("full".equals(imageType)) {
			imageUrl = alarmManageService.getFullImage(alarmId);
		} else {
			return byteImage;
		}
		if (imageUrl == null) {
			return byteImage;
		}
		HttpURLConnection connection = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream byteArrOS = null;
		try {
			URL sourceUrl = new URL(imageUrl);
			connection = (HttpURLConnection) sourceUrl.openConnection();
			connection.connect();
			if (connection.getResponseCode() == 200) {
				bis = new BufferedInputStream(connection.getInputStream());
				byteArrOS = new ByteArrayOutputStream();

				byte[] buf = new byte[1024];
				int size = 0;
				while ((size = bis.read(buf)) != -1) {
					byteArrOS.write(buf, 0, size);
				}
				byteArrOS.flush();
//				base64Image = Base64.encodeBase64String(byteArrOS.toByteArray());
				byteImage = byteArrOS.toByteArray();
			}
		} catch (Exception e) {
		} finally {
			try {
				if (byteArrOS != null) byteArrOS.close();
				if (bis != null) bis.close();
				if (connection != null) connection.disconnect();
			} catch (IOException e) {
			}
		}
		return byteImage;
	}

}
