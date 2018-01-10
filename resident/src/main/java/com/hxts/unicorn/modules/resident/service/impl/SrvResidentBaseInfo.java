package com.hxts.unicorn.modules.resident.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.hxts.unicorn.modules.resident.dto.HouseDto;
import com.hxts.unicorn.modules.resident.dto.KeyTeenagerDto;
import com.hxts.unicorn.modules.resident.dto.ResidentBaseInfoDto;
import com.hxts.unicorn.modules.resident.dto.SpecialCrowdDto;
import com.hxts.unicorn.modules.resident.model.ResidentBase;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvForeignerInfo;
import com.hxts.unicorn.modules.resident.service.ISrvResidentBaseInfo;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.DataDictionaryItem;
import com.hxts.unicorn.platform.interfaces.biz.ForeignerResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.ResidentBaseInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;

import java.lang.reflect.Method;

@Service
public class SrvResidentBaseInfo implements ISrvResidentBaseInfo {
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
	private ISrvForeignerInfo srvForeignerInfo;
	@Autowired
	private IHouseManageService houseManageService;
	
	@Override
	public ResidentBaseInfo selectByResidentBaseId(Integer residentBaseId){
		return residentBaseInfoDao.selectByPrimaryKey(residentBaseId);
	}
	
	@Override
	public int save(ResidentBaseInfo residentBaseInfo) {
		// TODO Auto-generated method stub
		ResidentBase residentBase = toEntity(residentBaseInfo);
		ResidentBaseInfo info=residentBaseInfoDao.selectByIdNo(residentBaseInfo.getIdNo(),null);
		BigDecimal integrity=claculateIntegrity(residentBaseInfo);
		residentBase.setIntegrity(integrity);
		if(info==null){
			int addId = residentBaseInfoDao.insert(residentBase);
			residentBaseInfo.setResidentBaseId(residentBase.getResidentBaseId());
			return addId;
		}else{
			residentBase.setResidentBaseId(info.getResidentBaseId());
			int updateId = residentBaseInfoDao.updateByPrimaryKeySelective(residentBase);
			residentBaseInfo.setResidentBaseId(info.getResidentBaseId());
			return updateId;
		}
	}
	
	@Override
	public int save(ResidentBaseInfo residentBaseInfo,ForeignerResidentInfo foreignerResidentInfo) {
		// TODO Auto-generated method stub
		ResidentBase residentBase = toEntity(residentBaseInfo);
		ResidentBaseInfo info = residentBaseInfoDao.selectByIdNo(residentBaseInfo.getIdNo(),null);
		BigDecimal integrity = claculateIntegrity(residentBaseInfo,foreignerResidentInfo);
		residentBase.setIntegrity(integrity);
		int saveId = 0;
		if(info == null){
			int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
			residentBase.setCreateUserId(userId);
			saveId = residentBaseInfoDao.insert(residentBase);
			residentBaseInfo.setResidentBaseId(residentBase.getResidentBaseId());
		}else{
			residentBase.setResidentBaseId(info.getResidentBaseId());
			saveId = residentBaseInfoDao.updateByPrimaryKeySelective(residentBase);
			residentBaseInfo.setResidentBaseId(info.getResidentBaseId());
		}
		foreignerResidentInfo.setResidentBaseId(residentBase.getResidentBaseId());
		srvForeignerInfo.save(foreignerResidentInfo);
		return saveId; 
	}	
	
	@Override
	public int deleteByResidentId(Integer residentBaseId) {
		// TODO Auto-generated method stub
		return residentBaseInfoDao.deleteByPrimaryKey(residentBaseId);
	}
	
	public ResidentBaseInfo selectByIdNo(String idNo){
		List<Integer> residentBaseIds = getCurUserResidentBaseIds();
		return residentBaseInfoDao.selectByIdNo(idNo,residentBaseIds);
	}
	
	public ResidentBase toEntity(ResidentBaseInfo dto){
		ResidentBase entity = new ResidentBase();
		entity.setResidentBaseId(dto.getResidentBaseId());
		entity.setIdNo(dto.getIdNo());
		entity.setName(dto.getName());
		entity.setFormerName(dto.getFormerName());
		entity.setSex(dto.getSex());
		entity.setBirthDate(dto.getBirthDate());
		entity.setEthnicity(dto.getEthnicity());
		entity.setNativePlace(dto.getNativePlace());
		entity.setMaritalStatus(dto.getMaritalStatus());
		entity.setPoliticalStatus(dto.getPoliticalStatus());
		entity.setEducationalDegree(dto.getEducationalDegree());
		entity.setReligiousBelief(dto.getReligiousBelief());
		entity.setOccupationCategory(dto.getOccupationCategory());
		entity.setOccupation(dto.getOccupation());
		entity.setEmployer(dto.getEmployer());
		entity.setContact(dto.getContact());
		entity.setRegisteredResidence(dto.getRegisteredResidence());
		entity.setRegisteredResidenceAddress(dto.getRegisteredResidenceAddress());
		entity.setCurrentResidence(dto.getCurrentResidence());
		entity.setCurrentResidenceAddress(dto.getCurrentResidenceAddress());
		entity.setCreateUserId(dto.getCreateUserId());
		entity.setIntegrity(dto.getIntegrity());
		entity.setPicture(dto.getPicture());
		return entity;
	}

	@Override
	public List<ResidentBaseInfoDto> getResidentInfoByKeywords(List<Integer> residentBaseIds,
			String name, String idNo, BigDecimal integrity) {
		// TODO Auto-generated method stub
		List<ResidentBaseInfoDto> list = new ArrayList<ResidentBaseInfoDto>();
		if(residentBaseIds!=null && residentBaseIds.size()==0){
			return list;
		}
		list = residentBaseInfoDao.getResidentInfoForList(name, idNo, integrity, residentBaseIds);
		if(list!=null && list.size()==0){
			return list;
		}
		List<Integer> residentIds = new ArrayList<Integer>();
		for (ResidentBaseInfoDto dto : list) {
			residentIds.add(dto.getResidentBaseId());
		}
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);
		for (ResidentBaseInfoDto dto : list) {
			dto.setHouseInfos(toHouseDtoList(houseInfos.get(dto.getResidentBaseId())));
		}
		return list;
	}
	
	public List<HouseDto> toHouseDtoList(List<ResidentOfHouseInfo> infos){
		if (infos == null || infos.size() == 0) {
			return null;
		}
		List<HouseDto> dtos = new ArrayList<HouseDto>();
		for (ResidentOfHouseInfo info : infos) {
			HouseDto dto = new HouseDto();
			dto.setHouseId(info.getHouseId());
			HouseInfo houseInfo = info.getHouseInfo();
			if (houseInfo != null && houseInfo.getBuildingInfo()!=null ) {
				dto.setGridId(houseInfo.getBuildingInfo().getGridId());
				dto.setGridName(houseInfo.getBuildingInfo().getGridName());
				dto.setHouseAddress(houseInfo.getBuildingInfo().getBuildingName()+houseInfo.getUnitNumber()+"一单元"+houseInfo.getFloorNumber()+"0"+houseInfo.getHouseNumber());
			}
			dtos.add(dto);
		}
		return dtos;
	}
	
	@Override
	public ResidentLabels getAllLabelId(Integer residentBaseId, String idNo) {
		// TODO Auto-generated method stub
		return residentBaseInfoDao.getAllLabelId(residentBaseId, idNo);
	}
	
	/**
	 * 
	 * <计算人口基础信息完整度>
	 *
	 * @param residentBaseInfo 
	 * @return 完整度(0-100)
	 * @see [类、类#方法、类#成员]
	 */
	public BigDecimal claculateIntegrity(ResidentBaseInfo residentBaseInfo){
		int count=0;
		int methodCount=0;
		BigDecimal integrity = new BigDecimal(0);
        Method[] methods=residentBaseInfo.getClass().getDeclaredMethods();
        for(Method m : methods) {
        	if(m.getName().contains("get")&& !m.getName().equals("getResidentBaseId") && !m.getName().equals("getCreateUserId")
        			&& !m.getName().equals("getIntegrity")){
        		
                   try{
                   String value=(String)m.invoke(residentBaseInfo);
                   methodCount=methodCount+1;
                   if(value!=null){  
                	  count=count+1;
                   }
                   }catch(Exception e){
                	   e.printStackTrace();
                	   throw new AppModuleErrorException("信息完整度计算异常");
                   }
        	}
        }
		integrity=new BigDecimal(count).multiply(new BigDecimal(100)).divide(new BigDecimal(methodCount),2,BigDecimal.ROUND_HALF_EVEN);
		return integrity;
	}
	
	/**
	 * 
	 * <境外人口计算基础信息完整度专用>
	 * <功能详细描述>
	 * @param residentBaseInfo
	 * @param foreignerResidentInfo
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public BigDecimal claculateIntegrity(ResidentBaseInfo residentBaseInfo,ForeignerResidentInfo foreignerResidentInfo){
		int count=0;
		int methodCount=17;
		BigDecimal integrity = new BigDecimal(0);
		if (foreignerResidentInfo.getForeignGivenName() != null) {
			count++;
		}
		if (foreignerResidentInfo.getForeignSurname() != null) {
			count++;
		}
		if (residentBaseInfo.getFormerName() != null) {
			count++;
		}
		if (residentBaseInfo.getSex() != null) {
			count++;
		}
		if (foreignerResidentInfo.getNationality() != null) {
			count++;
		}
		if (residentBaseInfo.getReligiousBelief() != null) {
			count++;
		}
		if (foreignerResidentInfo.getCertificateCode() != null) {
			count++;
		}
		if (residentBaseInfo.getIdNo() != null) {
			count++;
		}
		if (residentBaseInfo.getContact() != null) {
			count++;
		}
		if (foreignerResidentInfo.getArriveDate() != null) {
			count++;
		}
		if (residentBaseInfo.getPicture() != null) {
			count++;
		}
		if (residentBaseInfo.getOccupationCategory() != null) {
			count++;
		}
		if (residentBaseInfo.getOccupation() != null) {
			count++;
		}
		if (residentBaseInfo.getEmployer() != null) {
			count++;
		}
		if (residentBaseInfo.getCurrentResidence() != null) {
			count++;
		}
		if (residentBaseInfo.getCurrentResidenceAddress() != null) {
			count++;
		}
		if (foreignerResidentInfo.getExpectDepartureDate() != null) {
			count++;
		}
		integrity=new BigDecimal(count).multiply(new BigDecimal(100)).divide(new BigDecimal(methodCount),2,BigDecimal.ROUND_HALF_EVEN);
		return integrity;
	}
	
	@Override
	public List<StatsResult> getTagStats() {
		// TODO Auto-generated method stub
		List<Integer> gridIds = new ArrayList<Integer>();
		IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IGridService.APP_ID);
		List<GridItem> gridItems = gridService.getCurrentUserGrid();
		for(GridItem gridItem:gridItems){	
			gridIds.add(gridItem.getGridId());
		}
		if(gridIds!=null && gridIds.size()==0){
			return null;
		}
		List<Integer> residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, null);
		return residentBaseInfoDao.getTagStats(residentBaseIds);
	}

	@Override
	public List<StatsResult> getDegreeStats() {
		// TODO Auto-generated method stub
		List<Integer> gridIds = new ArrayList<Integer>();
		IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IGridService.APP_ID);
		List<GridItem> gridItems = gridService.getCurrentUserGrid();
		for(GridItem gridItem:gridItems){	
			gridIds.add(gridItem.getGridId());
		}
		if(gridIds!=null && gridIds.size()==0){
			return null;
		}
		List<Integer> residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, null);
		return residentBaseInfoDao.getDegreeStats(residentBaseIds);
	}

	@Override
	public List<StatsResult> getAgeStats() {
		// TODO Auto-generated method stub
		List<Integer> gridIds = new ArrayList<Integer>();
		IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IGridService.APP_ID);
		List<GridItem> gridItems = gridService.getCurrentUserGrid();
		for(GridItem gridItem:gridItems){	
			gridIds.add(gridItem.getGridId());
		}
		if(gridIds!=null && gridIds.size()==0){
			return null;
		}
		List<Integer> residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, null);
		return residentBaseInfoDao.getAgeStats(residentBaseIds);
	}

	@Override
	public List<ResidentBaseInfo> getResidentBaseInfo(List<Integer> residentBaseIds, String idNo, String name) {
		// TODO Auto-generated method stub
		List<ResidentBaseInfo> list= new ArrayList<ResidentBaseInfo>();
		List<Integer> residentIds = getCurUserResidentBaseIds();
		List<Integer> residentBaseIdss = new ArrayList<Integer>();
		if(residentBaseIds!=null && residentBaseIds.size()>0){
			for(Integer residentBaseId:residentBaseIds){
				if(residentIds.contains(residentBaseId)){
					residentBaseIdss.add(residentBaseId);
				}
			}
		}else{
			residentBaseIdss.addAll(residentBaseIdss);
		}
		list = residentBaseInfoDao.getResidentBaseInfo(residentBaseIdss, idNo, name);
		return list;
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

	@Override
	public List<ResidentBaseInfoDto> getPeopleInfoList(String type) {
		// TODO Auto-generated method stub
		String teenagerType = null;
		if(type.equals("关注人群")){
			type = IPersonManageService.LABEL_KEY_CROWD;
		}else if(type.equals("帮扶人群")){
			type = IPersonManageService.LABEL_ASSIST;
		}else if(type.equals("特殊人群")){
			type = IPersonManageService.LABEL_SPECIAL;
		}else if(type.equals("重点青少年")){
			type = IPersonManageService.LABEL_TEENAGER;
		}else if(type.equals("低保")){
			type = IPersonManageService.LABEL_SUB_ALLOW;
		}else if(type.equals("独居老人")){
			type = IPersonManageService.LABEL_LIVE_ALONE;
		}else if(type.equals("残障")){
			type = IPersonManageService.LABEL_DISABLED;
		}else if(type.equals("吸毒")){
			type = IPersonManageService.LABEL_DRUG;
		}else if(type.equals("刑满释放")){
			type = IPersonManageService.LABEL_EMANCIPIST;
		}else if(type.equals("非法上访")){
			type = IPersonManageService.LABEL_PERTITION;
		}else if(type.equals("艾滋病")){
			type = IPersonManageService.LABEL_AIDS;
		}else if(type.equals("社区矫正")){
			type = IPersonManageService.LABEL_RECTIFY;
		}else if(type.equals("精神障碍")){
			type = IPersonManageService.LABEL_ALLOEOSIS;
		}else{
			type = IPersonManageService.LABEL_TEENAGER;
			List<DataDictionaryItem> list=residentDictDao.getDictByDataType("人员类型");
			for(DataDictionaryItem teenagerTypeItem:list){
				if(type.equals(teenagerTypeItem.dataName)){
					teenagerType = teenagerTypeItem.dataCode;
					break;
				}	
			}
		}
		List<ResidentBaseInfoDto> infosDto = new ArrayList<ResidentBaseInfoDto>();
	    if(type.equals(IPersonManageService.LABEL_KEY_CROWD) || type.equals(IPersonManageService.LABEL_ASSIST)){
	    	List<AssistCrowdDto> infos = subsistenceAllowanceDao.queryAssistCrowdListByKeywords(null,null,null,null,null);
	    	List<Integer> residentBaseIds = new ArrayList<Integer>();
			for (AssistCrowdDto info : infos) {
				residentBaseIds.add(info.getResidentBaseId());
			}
			Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentBaseIds);
			for (AssistCrowdDto info : infos) {
				ResidentBaseInfoDto dto =new ResidentBaseInfoDto();
				dto.setResidentBaseId(info.getResidentBaseId());
				dto.setName(info.getName());
				dto.setIdNo(info.getIdNo());
				dto.setResidentType(info.getAssistType());
				dto.setHouseInfos(toHouseDtoList(houseInfos.get(info.getResidentBaseId())));
				infosDto.add(dto);
			}
		}
	    if(type.equals(IPersonManageService.LABEL_SUB_ALLOW)){
			List<ResidentBaseInfo> infos = subsistenceAllowanceDao.queryBaseInfoByKeywords(null, IPersonManageService.LABEL_SUB_ALLOW, null, null, null);
			List<Integer> residentBaseIds = new ArrayList<Integer>();
			for (ResidentBaseInfo info : infos) {
				residentBaseIds.add(info.getResidentBaseId());
			}
			Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentBaseIds);
			for (ResidentBaseInfo info : infos) {
				ResidentBaseInfoDto dto =new ResidentBaseInfoDto();
				dto.setResidentBaseId(info.getResidentBaseId());
				dto.setName(info.getName());
				dto.setIdNo(info.getIdNo());
				dto.setResidentType(IPersonManageService.LABEL_SUB_ALLOW);
				dto.setHouseInfos(toHouseDtoList(houseInfos.get(info.getResidentBaseId())));
				infosDto.add(dto);
			}
		}
	    if(type.equals(IPersonManageService.LABEL_LIVE_ALONE)){
			List<ResidentBaseInfo> infos = subsistenceAllowanceDao.queryBaseInfoByKeywords(null, IPersonManageService.LABEL_LIVE_ALONE, null, null, null);
			List<Integer> residentBaseIds = new ArrayList<Integer>();
			for (ResidentBaseInfo info : infos) {
				residentBaseIds.add(info.getResidentBaseId());
			}
			Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentBaseIds);
			for (ResidentBaseInfo info : infos) {
				ResidentBaseInfoDto dto =new ResidentBaseInfoDto();
				dto.setResidentBaseId(info.getResidentBaseId());
				dto.setName(info.getName());
				dto.setIdNo(info.getIdNo());
				dto.setResidentType(IPersonManageService.LABEL_LIVE_ALONE);
				dto.setHouseInfos(toHouseDtoList(houseInfos.get(info.getResidentBaseId())));
				infosDto.add(dto);
			}
		}
	    if(type.equals(IPersonManageService.LABEL_DISABLED)){
			List<ResidentBaseInfo> infos = subsistenceAllowanceDao.queryBaseInfoByKeywords(null, IPersonManageService.LABEL_DISABLED, null, null, null);
			List<Integer> residentBaseIds = new ArrayList<Integer>();
			for (ResidentBaseInfo info : infos) {
				residentBaseIds.add(info.getResidentBaseId());
			}
			Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentBaseIds);
			for (ResidentBaseInfo info : infos) {
				ResidentBaseInfoDto dto =new ResidentBaseInfoDto();
				dto.setResidentBaseId(info.getResidentBaseId());
				dto.setName(info.getName());
				dto.setIdNo(info.getIdNo());
				dto.setResidentType(IPersonManageService.LABEL_DISABLED);
				dto.setHouseInfos(toHouseDtoList(houseInfos.get(info.getResidentBaseId())));
				infosDto.add(dto);
			}
		}
	    if(type.equals(IPersonManageService.LABEL_KEY_CROWD) || type.equals(IPersonManageService.LABEL_SPECIAL)){
			List<SpecialCrowdDto> infos = drugInfoDao.querySpecialCrowdListByKeywords(null,null,null,null);
			List<Integer> residentBaseIds = new ArrayList<Integer>();
			for (SpecialCrowdDto info : infos) {
				residentBaseIds.add(info.getResidentBaseId());
			}
			Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentBaseIds);
			for (SpecialCrowdDto info : infos) {
				ResidentBaseInfoDto dto =new ResidentBaseInfoDto();
				dto.setResidentBaseId(info.getResidentBaseId());
				dto.setName(info.getName());
				dto.setIdNo(info.getIdNo());
				dto.setResidentType(info.getSpecialType());
				dto.setHouseInfos(toHouseDtoList(houseInfos.get(info.getResidentBaseId())));
				infosDto.add(dto);
			}
		}
	    if(type.equals(IPersonManageService.LABEL_DRUG)){
			List<ResidentBaseInfo> infos = drugInfoDao.queryBaseInfoByKeywords(IPersonManageService.LABEL_DRUG, null, null, null);
			List<Integer> residentBaseIds = new ArrayList<Integer>();
			for (ResidentBaseInfo info : infos) {
				residentBaseIds.add(info.getResidentBaseId());
			}
			Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentBaseIds);
			for (ResidentBaseInfo info : infos) {
				ResidentBaseInfoDto dto =new ResidentBaseInfoDto();
				dto.setResidentBaseId(info.getResidentBaseId());
				dto.setName(info.getName());
				dto.setIdNo(info.getIdNo());
				dto.setResidentType(IPersonManageService.LABEL_DRUG);
				dto.setHouseInfos(toHouseDtoList(houseInfos.get(info.getResidentBaseId())));
				infosDto.add(dto);
			}
		}
	    if(type.equals(IPersonManageService.LABEL_EMANCIPIST)){
			List<ResidentBaseInfo> infos = drugInfoDao.queryBaseInfoByKeywords(IPersonManageService.LABEL_EMANCIPIST, null, null, null);
			List<Integer> residentBaseIds = new ArrayList<Integer>();
			for (ResidentBaseInfo info : infos) {
				residentBaseIds.add(info.getResidentBaseId());
			}
			Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentBaseIds);
			for (ResidentBaseInfo info : infos) {
				ResidentBaseInfoDto dto =new ResidentBaseInfoDto();
				dto.setResidentBaseId(info.getResidentBaseId());
				dto.setName(info.getName());
				dto.setIdNo(info.getIdNo());
				dto.setResidentType(IPersonManageService.LABEL_EMANCIPIST);
				dto.setHouseInfos(toHouseDtoList(houseInfos.get(info.getResidentBaseId())));
				infosDto.add(dto);
			}
		}
	    if(type.equals(IPersonManageService.LABEL_PERTITION)){
			List<ResidentBaseInfo> infos = drugInfoDao.queryBaseInfoByKeywords(IPersonManageService.LABEL_PERTITION, null, null, null);
			List<Integer> residentBaseIds = new ArrayList<Integer>();
			for (ResidentBaseInfo info : infos) {
				residentBaseIds.add(info.getResidentBaseId());
			}
			Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentBaseIds);
			for (ResidentBaseInfo info : infos) {
				ResidentBaseInfoDto dto =new ResidentBaseInfoDto();
				dto.setResidentBaseId(info.getResidentBaseId());
				dto.setName(info.getName());
				dto.setIdNo(info.getIdNo());
				dto.setResidentType(IPersonManageService.LABEL_PERTITION);
				dto.setHouseInfos(toHouseDtoList(houseInfos.get(info.getResidentBaseId())));
				infosDto.add(dto);
			}
		}
	    if(type.equals(IPersonManageService.LABEL_KEY_CROWD) || type.equals(IPersonManageService.LABEL_TEENAGER)){
			List<KeyTeenagerDto> infos = keyTeenagerDao.queryKeyTeenagerListByKeywords(teenagerType, null, null, null);
			List<Integer> residentBaseIds = new ArrayList<Integer>();
			for (KeyTeenagerDto info : infos) {
				residentBaseIds.add(info.getResidentBaseId());
			}
			Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentBaseIds);
			for (KeyTeenagerDto info : infos) {
				ResidentBaseInfoDto dto =new ResidentBaseInfoDto();
				dto.setResidentBaseId(info.getResidentBaseId());
				dto.setName(info.getName());
				dto.setIdNo(info.getIdNo());
				dto.setResidentType(info.getTeenagerType());
				dto.setHouseInfos(toHouseDtoList(houseInfos.get(info.getResidentBaseId())));
				infosDto.add(dto);
			}
		}
		return infosDto;
	} 
}
