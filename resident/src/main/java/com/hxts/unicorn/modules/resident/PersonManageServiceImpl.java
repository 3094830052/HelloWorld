/*
 * 文 件 名:  PersonManageServiceImpl.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月26日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.modules.resident;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.DisabledPeopleDao;
import com.hxts.unicorn.modules.resident.dao.DrugInfoDao;
import com.hxts.unicorn.modules.resident.dao.EmancipistDao;
import com.hxts.unicorn.modules.resident.dao.FamilyRelationDao;
import com.hxts.unicorn.modules.resident.dao.FloatingResidentDao;
import com.hxts.unicorn.modules.resident.dao.ForeignerInfoDao;
import com.hxts.unicorn.modules.resident.dao.HouseholdRegisteredResidentDao;
import com.hxts.unicorn.modules.resident.dao.IllegalPertitionerDao;
import com.hxts.unicorn.modules.resident.dao.KeyTeenagerDao;
import com.hxts.unicorn.modules.resident.dao.LeftBehindResidentDao;
import com.hxts.unicorn.modules.resident.dao.LivingAloneAgedDao;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.dao.ResidentDictDao;
import com.hxts.unicorn.modules.resident.dao.SubsistenceAllowanceDao;
import com.hxts.unicorn.modules.resident.dto.AssistCrowdDto;
import com.hxts.unicorn.modules.resident.dto.KeyTeenagerDto;
import com.hxts.unicorn.modules.resident.dto.LeftBehindResidentDto;
import com.hxts.unicorn.modules.resident.dto.SpecialCrowdDto;
import com.hxts.unicorn.modules.resident.model.ResidentDict;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.AuthorityItem;
import com.hxts.unicorn.platform.interfaces.DataDictionaryItem;
import com.hxts.unicorn.platform.interfaces.IModuleBroker;
import com.hxts.unicorn.platform.interfaces.IUnicornFrame;
import com.hxts.unicorn.platform.interfaces.ModuleProperty;
import com.hxts.unicorn.platform.interfaces.RequestAuthority;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.basic.IAuthorityService;
import com.hxts.unicorn.platform.interfaces.basic.organization.IOrganizationService;
import com.hxts.unicorn.platform.interfaces.biz.DisabledPeopleInfo;
import com.hxts.unicorn.platform.interfaces.biz.DrugInfo;
import com.hxts.unicorn.platform.interfaces.biz.EmancipistInfo;
import com.hxts.unicorn.platform.interfaces.biz.FloatingResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.ForeignerResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseholdResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.IllegalPertitionerInfo;
import com.hxts.unicorn.platform.interfaces.biz.KeyTeenagerInfo;
import com.hxts.unicorn.platform.interfaces.biz.LeftBehindResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.LivingAloneAgedInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentBaseInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.SubsistenceAllowanceInfo;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年9月26日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
public class PersonManageServiceImpl implements IPersonManageService {
	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private HouseholdRegisteredResidentDao householdRegisteredResidentDao;
	@Autowired
	private FloatingResidentDao floatingResidentDao;
	@Autowired
	private LeftBehindResidentDao leftBehindResidentDao;
	@Autowired
	private ForeignerInfoDao foreignerInfoDao;
	@Autowired
	private SubsistenceAllowanceDao subsistenceAllowanceDao;
	@Autowired
	private LivingAloneAgedDao livingAloneAgedDao;
	@Autowired
	private DisabledPeopleDao disabledPeopleDao;
	@Autowired
	private KeyTeenagerDao keyTeenagerDao;
	@Autowired
	private ResidentDictDao residentDictDao;
	@Autowired
	private FamilyRelationDao familyRelationDao;
	@Autowired
	private DrugInfoDao drugInfoDao;
	@Autowired
	private EmancipistDao emancipistDao;
	@Autowired
	private IllegalPertitionerDao illegalPertitionerDao;
	@Autowired
	private IHouseManageService houseManageService;
	
	private IUnicornFrame frame = null;
	private IModuleBroker broker = null;
	
	public static Logger logger;

	private static final ModuleProperty property;
	static {
		property = new ModuleProperty();
		property.name = "人口管理";
		property.author = "";
		property.copyright = "";
		property.description = "";
		property.version = "";
		property.review = "";
	}
	
	/** 
	 * <默认构造函数>
	 */
	public PersonManageServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 重载方法
	 * @return
	 */
	@Override
	public ArrayList<String> getDepends() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 重载方法
	 * @return
	 */
	@Override
	public ArrayList<String> getOptionalDepends() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 重载方法
	 * @return
	 */
	@Override
	public String appId() {
		// TODO Auto-generated method stub
		return IPersonManageService.APP_ID;
	}

	/**
	 * 重载方法
	 * @return
	 */
	@Override
	public ModuleProperty getProperty() {
		// TODO Auto-generated method stub
		return property;
	}

	/**
	 * 重载方法
	 * @param frame
	 */
	@Override
	public void initialize(IUnicornFrame frame) {
		// TODO Auto-generated method stub
		this.frame = frame;
		logger = (Logger) frame.getExtenalComponentManager().getLogger();
	}

	/**
	 * 重载方法
	 * @return
	 */
	@Override
	public IModuleBroker getModuleBroker() {
		// TODO Auto-generated method stub
		if (broker == null){
			broker = new PersonManageBroker();
		}
        return broker;
	}
	
	@Override
	public List<DataDictionaryItem> getAllDataDictionary() {
		// TODO Auto-generated method stub
		List<DataDictionaryItem> list=residentDictDao.getAllDict();
		return list;
	}

	/**
	 * 重载方法
	 * @return
	 */
	@Override
	public List<DataDictionaryItem> getDataDictionary(String dataType) {
		// TODO Auto-generated method stub
		List<DataDictionaryItem> list=residentDictDao.getDictByDataType(dataType);
		return list;
	}
	
	@Override
	public void setDataDictionaryItem(DataDictionaryItem item) {
		// TODO Auto-generated method stub
		
		ResidentDict record=new ResidentDict();
		record.setDataType(item.dataType);
		record.setDataCode(item.dataCode);
		record.setDataName(item.dataName);
		List<ResidentDict> residentDicts = residentDictDao.selectByKeyWords(item.dataType, item.dataCode);
		if (residentDicts != null && residentDicts.size() > 0) {
			for (ResidentDict residentDict : residentDicts) {
				record.setId(residentDict.getId());
				residentDictDao.updateByPrimaryKeySelective(record);
			}
		}else {
			residentDictDao.insert(record);
		}
	}

	@Override
	public void deleteDataDictionary(String dataType, String dataCode) {
		// TODO Auto-generated method stub
		residentDictDao.deleteByKeyWords(dataType, dataCode);
	}
	
	private static final AuthorityItem AUTH_ACCESS = new AuthorityItem(
			"Resident_Data_Accessibility",
			"获取人口数据权限",
			"主要涉及：根据关键字查询人口列表，查看人口信息详情，查看统计图表，查询人员关系");
	private static final AuthorityItem AUTH_MODIFY = new AuthorityItem(
			"Resident_Data_Modification",
			"修改人口数据权限",
			"主要涉及：新增或者编辑人口信息");
	private static final AuthorityItem AUTH_DEL = new AuthorityItem(
			"Resident_Data_Delete",
			"删除人口数据权限",
			"主要涉及：删除人口信息");
	
	class PersonManageBroker implements IModuleBroker{

		/**
		 * 重载方法
		 * @return
		 */
		@Override
		public List<AuthorityItem> getAuthorityList() {
			return Arrays.asList(AUTH_ACCESS, AUTH_MODIFY, AUTH_DEL);
		}
	
		@Override
		public boolean checkRequestAuthorityAndScope(HttpServletRequest request,
				RequestAuthority output) {
			// TODO Auto-generated method stub
			if (output == null) {
				return false;
			}
			//处理微信的url请求
			String uri = request.getRequestURI();
			if (uri.startsWith("/g/resident/")) {
				output.needAuthorize = false;
				if (uri.startsWith("/g/resident/home")) {
					output.needLogon = false;
				} else {
					output.needLogon = true;
				}
				return true;
			}
			
			String[] paths = request.getRequestURI().split("/");
			if (paths != null && paths.length >= 4) {
				if ("m".equals(paths[1]) && "resident".equals(paths[2])) {
					if (paths.length == 4) {
						if ("stats".equals(paths[3])) {
							output.needAuthorize = true;
							output.orgnizId = 0;
							output.authKey = AUTH_ACCESS.authKey;
							return true;
						}else if("view".equals(paths[3])){
							output.needAuthorize = true;
							output.orgnizId = 0;
							output.authKey = AUTH_ACCESS.authKey;
							return true;
						}else if("save".equals(paths[3])){
							output.needAuthorize = true;
							output.orgnizId = 0;
							output.authKey = AUTH_MODIFY.authKey;
						}
					}else if (paths.length >= 5) {
						if ("relationlists".equals(paths[3])) {
							output.needAuthorize = true;
							output.orgnizId = 0;
							output.authKey = AUTH_ACCESS.authKey;
							return true;
						}else if ("stats".equals(paths[4]) || "list".equals(paths[4])
								|| "view".equals(paths[4])) {
							output.needAuthorize = true;
							output.orgnizId = 0;
							output.authKey = AUTH_ACCESS.authKey;
							return true;
						}else if ("save".equals(paths[4])) {
							output.needAuthorize = true;
							output.orgnizId = 0;
							output.authKey = AUTH_MODIFY.authKey;
							return true;
						}else if ("delete".equals(paths[4])) {
							output.needAuthorize = true;
							output.orgnizId = 0;
							output.authKey = AUTH_DEL.authKey;
							return true;
						} else if (paths.length >= 6 && "assist".equals(paths[3])){
							//帮扶人群模块层级更深
							if ("stats".equals(paths[5]) || "list".equals(paths[5])
									|| "view".equals(paths[5])) {
								output.needAuthorize = true;
								output.orgnizId = 0;
								output.authKey = AUTH_ACCESS.authKey;
								return true;
							}else if ("save".equals(paths[5])) {
								output.needAuthorize = true;
								output.orgnizId = 0;
								output.authKey = AUTH_MODIFY.authKey;
								return true;
							}else if ("delete".equals(paths[5])) {
								output.needAuthorize = true;
								output.orgnizId = 0;
								output.authKey = AUTH_DEL.authKey;
								return true;
							}
						}
					}
				}
			}
			return false;
		}
	
		@Override
		public List<String> getAllowableAction() {
			// TODO Auto-generated method stub
			List<String> functions = new ArrayList<String>();
			try {
				int userId = frame.getCurrentSession().getCurrentSysUser().getUserId();
				IAuthorityService authorityService = (IAuthorityService) frame.getModuleManager().getModule(IAuthorityService.APP_ID);
				List<AuthorityItem> authorities = authorityService.getUserAuthority(userId, true);

				for (AuthorityItem item : authorities) {
					if (item.getAuthKey().equals(AUTH_ACCESS.authKey)) {
						functions.add(IUnicornFrame.ACT_VIEW[0]);
						functions.add(IUnicornFrame.ACT_QUERY[0]);
					}
					if (item.getAuthKey().equals(AUTH_MODIFY.authKey)) {
						functions.add(IUnicornFrame.ACT_ADD[0]);
						functions.add(IUnicornFrame.ACT_EDIT[0]);
					}
					if (item.getAuthKey().equals(AUTH_DEL.authKey)) {
						functions.add("del");
					}
				}
			} catch (Exception e) {
				throw new AppModuleErrorException("系统错误，" + e.getMessage());
			}
			return functions;
		}

		/**
		 * 重载方法
		 * @return
		 */
		@Override
		public boolean isAnyAccessible() {
			int userId = frame.getCurrentSession().getCurrentSysUser().getUserId();
			IAuthorityService authorityService = (IAuthorityService) frame
					.getModuleManager().getModule(IAuthorityService.APP_ID);
			List<AuthorityItem> authorities = authorityService.getUserAuthority(userId, true);
			return false;
		}
	}

	@Override
	public ResidentInfo getResidentInfo(String label, Integer labelId, String idNo, boolean isReturnLabel) {
		// TODO Auto-generated method stub
		if (label == null) {
			throw new AppModuleErrorException("请输入标签");
		}
		if (idNo == null && labelId == null) {
			throw new AppModuleErrorException("标签id和idNo不能都为空，请输入任意一项");
		}
		ResidentInfo residentInfo = new ResidentInfo();
		Integer residentBaseId = null;
		if (!LABEL_BASE.equals(label)) {
			if (LABEL_HOUSEHOLD.equals(label)) {
				HouseholdResidentInfo householdResidentInfo = householdRegisteredResidentDao.selectByPrimaryKey(labelId);
				if(householdResidentInfo != null){
					residentInfo.setH(householdResidentInfo);
					residentBaseId = householdResidentInfo.getResidentBaseId();
				}
			} else if (LABEL_FLOATING.equals(label)) {
				FloatingResidentInfo floatingResidentInfo = floatingResidentDao.selectByPrimaryKey(labelId);
				if(floatingResidentInfo != null){
					residentInfo.setF(floatingResidentInfo);
					residentBaseId = floatingResidentInfo.getResidentBaseId();
				}
			} else if (LABEL_LEFT_BEHIND.equals(label)) {
				LeftBehindResidentInfo leftBehindResidentInfo = leftBehindResidentDao.selectByPrimaryKey(labelId);
				if(leftBehindResidentInfo != null){
					residentInfo.setLb(leftBehindResidentInfo);
					residentBaseId = leftBehindResidentInfo.getResidentBaseId();
				}
			} else if (LABEL_FOREIGN.equals(label)) {
				ForeignerResidentInfo foreignerResidentInfo = foreignerInfoDao.selectByPrimaryKey(labelId);
				if(foreignerResidentInfo != null){
					residentInfo.setFo(foreignerResidentInfo);
					residentBaseId = foreignerResidentInfo.getResidentBaseId();
				}
			}else if (LABEL_SUB_ALLOW.equals(label)) {
				SubsistenceAllowanceInfo subsistenceAllowanceInfo = subsistenceAllowanceDao.selectByPrimaryKey(labelId);
				if(subsistenceAllowanceInfo != null){
					residentInfo.setS(subsistenceAllowanceInfo);
					residentBaseId = subsistenceAllowanceInfo.getResidentBaseId();
				}
			}else if (LABEL_LIVE_ALONE.equals(label)) {
				LivingAloneAgedInfo livingAloneAgedInfo = livingAloneAgedDao.selectByPrimaryKey(labelId);
				if(livingAloneAgedInfo != null){
					residentInfo.setL(livingAloneAgedInfo);
					residentBaseId = livingAloneAgedInfo.getResidentBaseId();
				}
			}else if (LABEL_DISABLED.equals(label)) {
				DisabledPeopleInfo disabledPeopleInfo = disabledPeopleDao.selectByPrimaryKey(labelId);
				if(disabledPeopleInfo != null){
					residentInfo.setD(disabledPeopleInfo);
					residentBaseId = disabledPeopleInfo.getResidentBaseId();
				}
			}else if (LABEL_TEENAGER.equals(label)) {
				KeyTeenagerInfo keyTeenagerInfo = keyTeenagerDao.selectByPrimaryKey(labelId);
				if(keyTeenagerInfo != null){
					residentInfo.setK(keyTeenagerInfo);
					residentBaseId = keyTeenagerInfo.getResidentBaseId();
				}
			}
			else if (LABEL_DRUG.equals(label)) {
				DrugInfo drugInfo=drugInfoDao.selectByPrimaryKey(labelId);
				if(drugInfo!=null){
					residentInfo.setDr(drugInfo);
					residentBaseId=drugInfo.getResidentBaseId();
				}
			}
			else if (LABEL_EMANCIPIST.equals(label)) {
				EmancipistInfo EmancipistInfo = emancipistDao.selectByPrimaryKey(labelId);
				if(EmancipistInfo!=null){
					residentInfo.setE(EmancipistInfo);
					residentBaseId=EmancipistInfo.getResidentBaseId();
				}
			}
			else if (LABEL_PERTITION.equals(label)) {
				IllegalPertitionerInfo illegalPertitionerInfo=illegalPertitionerDao.selectByPrimaryKey(labelId);
				if(illegalPertitionerInfo!=null){
					residentInfo.setI(illegalPertitionerInfo);
					residentBaseId=illegalPertitionerInfo.getResidentBaseId();
				}
			}
		}else {
			residentBaseId = labelId;
		}
		ResidentLabels labelIds = residentBaseInfoDao.getAllLabelId(residentBaseId, idNo);
		if (labelIds != null) {
			ResidentBaseInfo residentBaseInfo = residentBaseInfoDao.selectByPrimaryKey(labelIds.getResidentBaseId());
			residentInfo.setB(residentBaseInfo);
			Map<String, Integer> labels = new HashMap<String, Integer>();
			
			Integer householdId = labelIds.getHouseholdId();
			if (householdId != null) {
				labels.put(LABEL_HOUSEHOLD, householdId);
				if (isReturnLabel && LABEL_BASE.equals(label)) {
					HouseholdResidentInfo householdResidentInfo = householdRegisteredResidentDao.selectByPrimaryKey(householdId);
					if(householdResidentInfo != null){
						residentInfo.setH(householdResidentInfo);
					}
				}
			}
			
			Integer floatingId = labelIds.getFloatingId();
			if (floatingId != null) {
				labels.put(LABEL_FLOATING, floatingId);
				if (isReturnLabel && LABEL_BASE.equals(label)) {
					FloatingResidentInfo floatingResidentInfo = floatingResidentDao.selectByPrimaryKey(floatingId);
					if(floatingResidentInfo != null){
						residentInfo.setF(floatingResidentInfo);
					}
				}
			}
			
			Integer leftBehindId = labelIds.getLeftBehindId();
			if (leftBehindId != null) {
				labels.put(LABEL_LEFT_BEHIND, leftBehindId);
				if (isReturnLabel && LABEL_BASE.equals(label)) {
					LeftBehindResidentInfo leftBehindResidentInfo = leftBehindResidentDao.selectByPrimaryKey(leftBehindId);
					if(leftBehindResidentInfo != null){
						residentInfo.setLb(leftBehindResidentInfo);
					}
				}
			}
			
			Integer foreignerId= labelIds.getForeignerId();
			if (foreignerId != null) {
				labels.put(LABEL_FOREIGN, foreignerId);
				if (isReturnLabel && LABEL_BASE.equals(label)) {
					ForeignerResidentInfo foreignerResidentInfo = foreignerInfoDao.selectByPrimaryKey(foreignerId);
					if(foreignerResidentInfo != null){
						residentInfo.setFo(foreignerResidentInfo);
					}
				}
			}
			
			Integer keyTeenagerId= labelIds.getKeyTeenagerId();
			if (keyTeenagerId != null) {
				labels.put(LABEL_TEENAGER, keyTeenagerId);
				if (isReturnLabel && LABEL_BASE.equals(label)) {
					KeyTeenagerInfo keyTeenagerInfo = keyTeenagerDao.selectByPrimaryKey(keyTeenagerId);
					if(keyTeenagerInfo != null){
						residentInfo.setK(keyTeenagerInfo);
					}
				}
			}
			
			Integer subsistAllowanceId= labelIds.getSubsistAllowanceId();
			if (subsistAllowanceId != null) {
				labels.put(LABEL_SUB_ALLOW, subsistAllowanceId);
				if (isReturnLabel && LABEL_BASE.equals(label)) {
					SubsistenceAllowanceInfo subsistenceAllowanceInfo = subsistenceAllowanceDao.selectByPrimaryKey(subsistAllowanceId);
					if(subsistenceAllowanceInfo != null){
						residentInfo.setS(subsistenceAllowanceInfo);
					}
				}
			}
			
			Integer livingAloneId= labelIds.getLivingAloneId();
			if (livingAloneId != null) {
				labels.put(LABEL_LIVE_ALONE, livingAloneId);
				if (isReturnLabel && LABEL_BASE.equals(label)) {
					LivingAloneAgedInfo livingAloneAgedInfo = livingAloneAgedDao.selectByPrimaryKey(livingAloneId);
					if(livingAloneAgedInfo != null){
						residentInfo.setL(livingAloneAgedInfo);
					}
				}
			}
			
			Integer disabledPeopleId= labelIds.getDisabledPeopleId();
			if (disabledPeopleId != null) {
				labels.put(LABEL_DISABLED, disabledPeopleId);
				if (isReturnLabel && LABEL_BASE.equals(label)) {
					DisabledPeopleInfo disabledPeopleInfo = disabledPeopleDao.selectByPrimaryKey(disabledPeopleId);
					if(disabledPeopleInfo != null){
						residentInfo.setD(disabledPeopleInfo);
					}
				}
			}
			
			Integer drugId= labelIds.getDrugId();
			if (drugId != null) {
				labels.put(LABEL_DRUG, drugId);
				if (isReturnLabel && LABEL_BASE.equals(label)) {
					DrugInfo drugInfo = drugInfoDao.selectByPrimaryKey(drugId);
					if(drugInfo != null){
						residentInfo.setDr(drugInfo);
					}
				}
			}
			
			Integer emancipistId= labelIds.getEmancipistId();
			if (emancipistId != null) {
				labels.put(LABEL_EMANCIPIST, emancipistId);
				if (isReturnLabel && LABEL_BASE.equals(label)) {
					EmancipistInfo emancipistInfo = emancipistDao.selectByPrimaryKey(emancipistId);
					if(emancipistInfo != null){
						residentInfo.setE(emancipistInfo);
					}
				}
			}
			
			Integer IllegalPertitionerId= labelIds.getIllegalPertitionerId();
			if (IllegalPertitionerId != null) {
				labels.put(LABEL_PERTITION, IllegalPertitionerId);
				if (isReturnLabel && LABEL_BASE.equals(label)) {
					IllegalPertitionerInfo illegalPertitionerInfo = illegalPertitionerDao.selectByPrimaryKey(IllegalPertitionerId);
					if(illegalPertitionerInfo != null){
						residentInfo.setI(illegalPertitionerInfo);
					}
				}
			}
			
			residentInfo.setLabels(labels);
		}else {
			residentInfo = null;
		}
		return residentInfo;
	}

	@Override
	public Map<String, Integer> getResidentLabelsByIds(List<Integer> residentBaseIds, List<String> labels) {
		// TODO Auto-generated method stub
		if (residentBaseIds == null || residentBaseIds.size() == 0) {
			throw new AppModuleErrorException("人口id列表不能为空");
		}
		if (labels == null || labels.size() == 0) {
			throw new AppModuleErrorException("标签列表不能为空");
		}
		boolean isLeftBehind = false;
		boolean isSuballow = false;
		boolean isLivealone = false;
		boolean isDisabled = false;
		boolean isTeenager = false;
		boolean isDrug = false;
		boolean isEmancipist = false;
		boolean isIllegalPertitioner = false;
		for (String label : labels) {
			if (LABEL_LEFT_BEHIND.equals(label)) {
				isLeftBehind = true;
				continue;
			}
			if (LABEL_SUB_ALLOW.equals(label)) {
				isSuballow = true;
				continue;
			}
			if (LABEL_LIVE_ALONE.equals(label)) {
				isLivealone = true;
				continue;
			}
			if (LABEL_DISABLED.equals(label)) {
				isDisabled = true;
				continue;
			}
			if (LABEL_TEENAGER.equals(label)) {
				isTeenager = true;
				continue;
			}
			if (LABEL_DRUG.equals(label)) {
				isDrug = true;
				continue;
			}
			if (LABEL_EMANCIPIST.equals(label)) {
				isEmancipist = true;
				continue;
			}
			if (LABEL_PERTITION.equals(label)) {
				isIllegalPertitioner = true;
				continue;
			}
		}
		List<StatsResult> rs = residentBaseInfoDao.getLabelCountByIds(residentBaseIds,isLeftBehind, isSuballow, isLivealone, isDisabled, isTeenager ,isDrug, isEmancipist,isIllegalPertitioner);
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		if (rs != null && rs.size() > 0) {
			for (StatsResult statsResult : rs) {
				if (statsResult.getTotal() > 0) {
					resultMap.put(statsResult.getName(), statsResult.getTotal());
				}
			}
		}
		return resultMap;
	}

	@Override
	public List<ResidentBaseInfo> getResidentListByKeywords(String idNo,
			String name, String contact) {
		// TODO Auto-generated method stub
		List<Integer> residentBaseIds = getCurUserResidentBaseIds();
		return residentBaseInfoDao.getResidentInfoByKeywords(name, idNo, contact,residentBaseIds);
	}

	@Override
	public Map<String, Integer> getDataUpdateStats(Integer userId) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String todayStr = df.format(new Date());
		String start = todayStr + " 00:00:00";
		String end = todayStr + " 23:59:59";
		IOrganizationService organizationServiceImpl= (IOrganizationService)ServiceModuleFactory.getFramework().getModuleManager().getModule(IOrganizationService.APP_ID);
		List<Integer> userIds = organizationServiceImpl.getSubordinate(userId);//获取下属人员
		userIds.add(userId);
		IActionLogService logServiceImpl = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IActionLogService.APP_ID);
		int insertCount = logServiceImpl.countActionSysLog("人口管理", "新建", null, start, end, userIds);
		int updateCount = logServiceImpl.countActionSysLog("人口管理", "修改", null, start, end, userIds);
		Map<String, Integer> dataUpdateStats = new HashMap<String, Integer>();
		dataUpdateStats.put("insert", insertCount);
		dataUpdateStats.put("update", updateCount);
		return dataUpdateStats;
	}
	
	@Override
	public List<String> getSpecialsHouseNames(String type){
		IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IGridService.APP_ID);
		List<GridItem> gridItems = gridService.getCurrentUserGrid();
		List<Integer> gridIds = new ArrayList<Integer>();
		List<Integer> residentIds = new ArrayList<Integer>();
		for(GridItem gridItem:gridItems){	
			gridIds.add(gridItem.getGridId());
		}
		List<Integer> residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, null);
		if(type.equals(IPersonManageService.LABEL_LEFT_BEHIND)){//留守人员
			List<LeftBehindResidentDto> infos = leftBehindResidentDao.getLeftBehindListByKeywords(null, null, null, null, residentBaseIds);
			for(LeftBehindResidentDto leftBehindResidentDto:infos){
				residentIds.add(leftBehindResidentDto.getResidentBaseId());
			}
		}else if(type.equals(IPersonManageService.LABEL_ASSIST)){//帮扶人群
			List<AssistCrowdDto> infos = subsistenceAllowanceDao.queryAssistCrowdListByKeywords(null, null, null, null, residentBaseIds);
			for(AssistCrowdDto assistCrowdDto:infos){
				residentIds.add(assistCrowdDto.getResidentBaseId());
			}
		}else if(type.equals(IPersonManageService.LABEL_SUB_ALLOW)){//低保人员
			List<AssistCrowdDto> infos = subsistenceAllowanceDao.queryAssistCrowdListByKeywords(null, "subAllow", null, null, residentBaseIds);
			for(AssistCrowdDto assistCrowdDto:infos){
				residentIds.add(assistCrowdDto.getResidentBaseId());
			}
		}else if(type.equals(IPersonManageService.LABEL_LIVE_ALONE)){//独居老人
			List<AssistCrowdDto> infos = subsistenceAllowanceDao.queryAssistCrowdListByKeywords(null, "liveAlone", null, null, residentBaseIds);
			for(AssistCrowdDto assistCrowdDto:infos){
				residentIds.add(assistCrowdDto.getResidentBaseId());
			}
		}else if(type.equals(IPersonManageService.LABEL_DISABLED)){//残障人士
			List<AssistCrowdDto> infos = subsistenceAllowanceDao.queryAssistCrowdListByKeywords(null, "disabled", null, null, residentBaseIds);
			for(AssistCrowdDto assistCrowdDto:infos){
				residentIds.add(assistCrowdDto.getResidentBaseId());
			}
		}else if(type.equals(IPersonManageService.LABEL_TEENAGER)){//重点青少年
			List<KeyTeenagerDto> infos = keyTeenagerDao.queryKeyTeenagerListByKeywords(null, null, null, residentBaseIds);
			for(KeyTeenagerDto keyTeenagerDto:infos){
				residentIds.add(keyTeenagerDto.getResidentBaseId());
			}
		}else if(type.equals(IPersonManageService.LABEL_SPECIAL)){//特殊人群
			List<SpecialCrowdDto> infos = drugInfoDao.querySpecialCrowdListByKeywords(null, null, null, residentBaseIds);
			for(SpecialCrowdDto specialCrowdDto:infos){
				residentIds.add(specialCrowdDto.getResidentBaseId());
			}
		}else if(type.equals(IPersonManageService.LABEL_DRUG)){//吸毒人员
			List<SpecialCrowdDto> infos = drugInfoDao.querySpecialCrowdListByKeywords(IPersonManageService.LABEL_DRUG, null, null, residentBaseIds);
			for(SpecialCrowdDto specialCrowdDto:infos){
				residentIds.add(specialCrowdDto.getResidentBaseId());
			}
		}else if(type.equals(IPersonManageService.LABEL_EMANCIPIST)){//刑满释放人员
			List<SpecialCrowdDto> infos = drugInfoDao.querySpecialCrowdListByKeywords(IPersonManageService.LABEL_EMANCIPIST, null, null, residentBaseIds);
			for(SpecialCrowdDto specialCrowdDto:infos){
				residentIds.add(specialCrowdDto.getResidentBaseId());
			}
		}else if(type.equals(IPersonManageService.LABEL_PERTITION)){//非法上访人员
			List<SpecialCrowdDto> infos = drugInfoDao.querySpecialCrowdListByKeywords(IPersonManageService.LABEL_PERTITION, null, null, residentBaseIds);
			for(SpecialCrowdDto specialCrowdDto:infos){
				residentIds.add(specialCrowdDto.getResidentBaseId());
			}
		}else if(type.equals(IPersonManageService.LABEL_KEY_PERSON)){//重点人员
			List<SpecialCrowdDto> specialInfos = drugInfoDao.querySpecialCrowdListByKeywords(null, null, null, residentBaseIds);
			for(SpecialCrowdDto specialCrowdDto:specialInfos){
				residentIds.add(specialCrowdDto.getResidentBaseId());
			}
			List<KeyTeenagerDto> teenagerInfos = keyTeenagerDao.queryKeyTeenagerListByKeywords(null, null, null, residentBaseIds);
			for(KeyTeenagerDto keyTeenagerDto:teenagerInfos){
				residentIds.add(keyTeenagerDto.getResidentBaseId());
			}
		}
		List<String> houseNames = new ArrayList<String>();
		if (residentIds.isEmpty()) return houseNames;
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);		
		for (Integer residentId : residentIds) {
			List<ResidentOfHouseInfo> residentOfHouseInfos=houseInfos.get(residentId);
			for(ResidentOfHouseInfo residentOfHouseInfo:residentOfHouseInfos){
				houseNames.add(residentOfHouseInfo.getHouseInfo().getBuildingInfo().getFullBuildingName()+"/"+residentOfHouseInfo.getHouseInfo().getDoorplate());
			}
		}
		return houseNames;
	}
	
	@Override
	public List<String> getSpecialsHouseNamesByType(List<String> types){
		IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IGridService.APP_ID);
		List<GridItem> gridItems = gridService.getCurrentUserGrid();
		List<Integer> gridIds = new ArrayList<Integer>();
		List<Integer> residentIds = new ArrayList<Integer>();
		for(GridItem gridItem:gridItems){	
			gridIds.add(gridItem.getGridId());
		}
		List<Integer> residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, null);
		for(String type:types){
			if(type.equals(IPersonManageService.LABEL_LEFT_BEHIND)){//留守人员
				List<LeftBehindResidentDto> infos = leftBehindResidentDao.getLeftBehindListByKeywords(null, null, null, null, residentBaseIds);
				for(LeftBehindResidentDto leftBehindResidentDto:infos){
					residentIds.add(leftBehindResidentDto.getResidentBaseId());
				}
			}else if(type.equals(IPersonManageService.LABEL_ASSIST)){//帮扶人群
				List<AssistCrowdDto> infos = subsistenceAllowanceDao.queryAssistCrowdListByKeywords(null, null, null, null, residentBaseIds);
				for(AssistCrowdDto assistCrowdDto:infos){
					residentIds.add(assistCrowdDto.getResidentBaseId());
				}
			}else if(type.equals(IPersonManageService.LABEL_SUB_ALLOW)){//低保人员
				List<AssistCrowdDto> infos = subsistenceAllowanceDao.queryAssistCrowdListByKeywords(null, "subAllow", null, null, residentBaseIds);
				for(AssistCrowdDto assistCrowdDto:infos){
					residentIds.add(assistCrowdDto.getResidentBaseId());
				}
			}else if(type.equals(IPersonManageService.LABEL_LIVE_ALONE)){//独居老人
				List<AssistCrowdDto> infos = subsistenceAllowanceDao.queryAssistCrowdListByKeywords(null, "liveAlone", null, null, residentBaseIds);
				for(AssistCrowdDto assistCrowdDto:infos){
					residentIds.add(assistCrowdDto.getResidentBaseId());
				}
			}else if(type.equals(IPersonManageService.LABEL_DISABLED)){//残障人士
				List<AssistCrowdDto> infos = subsistenceAllowanceDao.queryAssistCrowdListByKeywords(null, "disabled", null, null, residentBaseIds);
				for(AssistCrowdDto assistCrowdDto:infos){
					residentIds.add(assistCrowdDto.getResidentBaseId());
				}
			}else if(type.equals(IPersonManageService.LABEL_TEENAGER)){//重点青少年
				List<KeyTeenagerDto> infos = keyTeenagerDao.queryKeyTeenagerListByKeywords(null, null, null, residentBaseIds);
				for(KeyTeenagerDto keyTeenagerDto:infos){
					residentIds.add(keyTeenagerDto.getResidentBaseId());
				}
			}else if(type.equals(IPersonManageService.LABEL_SPECIAL)){//特殊人群
				List<SpecialCrowdDto> infos = drugInfoDao.querySpecialCrowdListByKeywords(null, null, null, residentBaseIds);
				for(SpecialCrowdDto specialCrowdDto:infos){
					residentIds.add(specialCrowdDto.getResidentBaseId());
				}
			}else if(type.equals(IPersonManageService.LABEL_DRUG)){//吸毒人员
				List<SpecialCrowdDto> infos = drugInfoDao.querySpecialCrowdListByKeywords(IPersonManageService.LABEL_DRUG, null, null, residentBaseIds);
				for(SpecialCrowdDto specialCrowdDto:infos){
					residentIds.add(specialCrowdDto.getResidentBaseId());
				}
			}else if(type.equals(IPersonManageService.LABEL_EMANCIPIST)){//刑满释放人员
				List<SpecialCrowdDto> infos = drugInfoDao.querySpecialCrowdListByKeywords(IPersonManageService.LABEL_EMANCIPIST, null, null, residentBaseIds);
				for(SpecialCrowdDto specialCrowdDto:infos){
					residentIds.add(specialCrowdDto.getResidentBaseId());
				}
			}else if(type.equals(IPersonManageService.LABEL_PERTITION)){//非法上访人员
				List<SpecialCrowdDto> infos = drugInfoDao.querySpecialCrowdListByKeywords(IPersonManageService.LABEL_PERTITION, null, null, residentBaseIds);
				for(SpecialCrowdDto specialCrowdDto:infos){
					residentIds.add(specialCrowdDto.getResidentBaseId());
				}
			}else if(type.equals(IPersonManageService.LABEL_KEY_PERSON)){//重点人员
				List<SpecialCrowdDto> specialInfos = drugInfoDao.querySpecialCrowdListByKeywords(null, null, null, residentBaseIds);
				for(SpecialCrowdDto specialCrowdDto:specialInfos){
					residentIds.add(specialCrowdDto.getResidentBaseId());
				}
				List<KeyTeenagerDto> teenagerInfos = keyTeenagerDao.queryKeyTeenagerListByKeywords(null, null, null, residentBaseIds);
				for(KeyTeenagerDto keyTeenagerDto:teenagerInfos){
					residentIds.add(keyTeenagerDto.getResidentBaseId());
				}
			}
		}
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);
		List<String> houseNames = new ArrayList<String>();
		for (Integer residentId : residentIds) {
			List<ResidentOfHouseInfo> residentOfHouseInfos=houseInfos.get(residentId);
			for(ResidentOfHouseInfo residentOfHouseInfo:residentOfHouseInfos){
				houseNames.add(residentOfHouseInfo.getHouseInfo().getBuildingInfo().getFullBuildingName()+"/"+residentOfHouseInfo.getHouseInfo().getDoorplate());
			}
		}
		return houseNames;
	}
	
	@Override
	public Map<String,List<Integer>> getResidentInfoByName(String name){
		List<Integer> residentBaseIds = getCurUserResidentBaseIds();
		List<ResidentBaseInfo> infos = residentBaseInfoDao.getResidentInfoByKeywords(name, null, null,residentBaseIds);
		 Map<String,List<Integer>> residentInfo = new HashMap<String,List<Integer>>();
		for(ResidentBaseInfo info:infos){
			List<Integer> ids = new ArrayList<Integer>();
			ids.add(info.getResidentBaseId());
			if(residentInfo.get(info.getName())!=null){
				List<Integer> idss =residentInfo.get(info.getName());
				for(Integer id:idss){
					ids.add(id);
				}
			}
			residentInfo.put(info.getName(), ids);
		}
		return residentInfo;
	}
	
	@Override
	public List<ResidentBaseInfo> getSpecialsInfoByIds(List<Integer> residentBaseIds, String type){
		List<ResidentBaseInfo> infos = new ArrayList<ResidentBaseInfo>(); 
		if(residentBaseIds!=null && residentBaseIds.size()==0){
			return infos;
		}
	    if(type.equals(IPersonManageService.LABEL_LEFT_BEHIND)){
			infos = leftBehindResidentDao.queryBaseInfoByKeywords(null, null, null, null, residentBaseIds);	
		}else if(type.equals(IPersonManageService.LABEL_ASSIST)){
			infos = subsistenceAllowanceDao.queryBaseInfoByKeywords(null, null, null, null, residentBaseIds);
		}else if(type.equals(IPersonManageService.LABEL_SUB_ALLOW)){
			infos = subsistenceAllowanceDao.queryBaseInfoByKeywords(null, IPersonManageService.LABEL_SUB_ALLOW, null, null, residentBaseIds);
		}else if(type.equals(IPersonManageService.LABEL_LIVE_ALONE)){
			infos = subsistenceAllowanceDao.queryBaseInfoByKeywords(null, IPersonManageService.LABEL_LIVE_ALONE, null, null, residentBaseIds);
		}else if(type.equals(IPersonManageService.LABEL_DISABLED)){
			infos = subsistenceAllowanceDao.queryBaseInfoByKeywords(null, IPersonManageService.LABEL_DISABLED, null, null, residentBaseIds);
		}else if(type.equals(IPersonManageService.LABEL_SPECIAL)){
			infos = drugInfoDao.queryBaseInfoByKeywords(null, null, null, residentBaseIds);	
		}else if(type.equals(IPersonManageService.LABEL_DRUG)){
			infos = drugInfoDao.queryBaseInfoByKeywords(IPersonManageService.LABEL_DRUG, null, null, residentBaseIds);	
		}else if(type.equals(IPersonManageService.LABEL_EMANCIPIST)){
			infos = drugInfoDao.queryBaseInfoByKeywords(IPersonManageService.LABEL_EMANCIPIST, null, null, residentBaseIds);	
		}else if(type.equals(IPersonManageService.LABEL_PERTITION)){
			infos = drugInfoDao.queryBaseInfoByKeywords(IPersonManageService.LABEL_PERTITION, null, null, residentBaseIds);	
		}else if(type.equals(IPersonManageService.LABEL_TEENAGER)){
			infos = keyTeenagerDao.queryBaseInfoByKeywords(null, null, null, residentBaseIds);	
		}else if(type.equals(IPersonManageService.LABEL_KEY_PERSON)){
			List<ResidentBaseInfo>infos1 = drugInfoDao.queryBaseInfoByKeywords(null, null, null, residentBaseIds);
			List<ResidentBaseInfo>infos2 = keyTeenagerDao.queryBaseInfoByKeywords(null, null, null, residentBaseIds);
			if(infos1!=null && !infos1.isEmpty()){
				infos.addAll(infos1);
			}
			if(infos2!=null && !infos2.isEmpty()){
				infos.addAll(infos2);
			}
		}
		return infos;
	}
	
	/**
	 * 
	 * <获取当前用户可以访问的人口基础信息id列表>
	 * @return 人口基础信息id列表
	 * @see [类、类#方法、类#成员]
	 */
	private List<Integer> getCurUserResidentBaseIds(){
		List<Integer> gridIds = new ArrayList<Integer>();
		List<Integer> residentBaseIds = new ArrayList<Integer>();
		IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IGridService.APP_ID);
		List<GridItem> gridItems = gridService.getCurrentUserGrid();
		for(GridItem gridItem:gridItems){	
			gridIds.add(gridItem.getGridId());
		}	
		residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, null);
		return residentBaseIds;
	} 
}
