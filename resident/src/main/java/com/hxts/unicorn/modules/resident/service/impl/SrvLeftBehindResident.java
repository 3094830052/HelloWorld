package com.hxts.unicorn.modules.resident.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.LeftBehindResidentDao;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.dto.HouseDto;
import com.hxts.unicorn.modules.resident.dto.LeftBehindResidentDto;
import com.hxts.unicorn.modules.resident.model.LeftBehindResident;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvLeftBehindResident;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.basic.organization.IOrganizationService;
import com.hxts.unicorn.platform.interfaces.basic.organization.OrgPosition;
import com.hxts.unicorn.platform.interfaces.basic.organization.OrgStructure;
import com.hxts.unicorn.platform.interfaces.biz.BuildingInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;
import com.hxts.unicorn.platform.interfaces.biz.LeftBehindResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;

@Service
public class SrvLeftBehindResident implements ISrvLeftBehindResident {
	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private LeftBehindResidentDao leftBehindResidentDao;
	@Autowired
	private IHouseManageService houseManageService;
	@Override
	public int save(LeftBehindResidentInfo leftBehindResidentInfo) {
		// TODO Auto-generated method stub
		Integer leftBehindId=leftBehindResidentInfo.getLeftBehindId();
		Integer residentBaseId = leftBehindResidentInfo.getResidentBaseId();
		if(residentBaseId == null){
			throw new AppModuleErrorException("留守人口信息添加异常！");    
		}
		ResidentLabels labels = residentBaseInfoDao.getAllLabelId(residentBaseId, null);
		if(labels != null &&labels.getLeftBehindId() != null){
			leftBehindId = labels.getLeftBehindId();
			leftBehindResidentInfo.setLeftBehindId(leftBehindId);
		}
		if(leftBehindId!=null){
			return leftBehindResidentDao.updateByPrimaryKeySelective(toEntity(leftBehindResidentInfo));			
		}else{
			int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
			leftBehindResidentInfo.setCreateUserId(userId);
			return leftBehindResidentDao.insert(toEntity(leftBehindResidentInfo));
		}
	}

	@Override
	public int deleteByLeftBehindId(Integer leftBehindId) {
		// TODO Auto-generated method stub
		return leftBehindResidentDao.deleteByPrimaryKey(leftBehindId);
	}

	@Override
	public LeftBehindResidentInfo selectByLeftBehindId(Integer leftBehindId) {
		// TODO Auto-generated method stub
		return leftBehindResidentDao.selectByPrimaryKey(leftBehindId);
	}

	@Override
	public List<LeftBehindResidentDto> getLeftBehindListByKeywords(
			String healthCondition, String leftBehindResidentType, String name,
			String idNo, List<Integer> residentBaseIds) {
		// TODO Auto-generated method stub
		List<LeftBehindResidentDto> list = new ArrayList<LeftBehindResidentDto>();
		if(residentBaseIds==null){
			return list;
		}
		list = leftBehindResidentDao.getLeftBehindListByKeywords(healthCondition,leftBehindResidentType,name,idNo,residentBaseIds);
		if(list!=null && list.size()==0){
			return list;
		}
		List<Integer> residentIds = new ArrayList<Integer>();
		for (LeftBehindResidentDto dto : list) {
			residentIds.add(dto.getResidentBaseId());
		}
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);
		for (LeftBehindResidentDto dto : list) {
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
			if (houseInfo != null) {
				dto.setGridId(houseInfo.getBuildingInfo().getGridId());
				dto.setGridName(houseInfo.getBuildingInfo().getGridName());
				dto.setHouseAddress(houseInfo.getBuildingInfo().getBuildingName()+houseInfo.getUnitNumber()+"单元"+houseInfo.getFloorNumber()+"0"+houseInfo.getHouseNumber());
			}
			dtos.add(dto);
		}
		return dtos;
	}
	
	public LeftBehindResident toEntity(LeftBehindResidentInfo info) {
		// TODO Auto-generated method stub
		LeftBehindResident entity = new LeftBehindResident();
		entity.setLeftBehindId(info.getLeftBehindId());
		entity.setResidentBaseId(info.getResidentBaseId());
		entity.setHealthCondition(info.getHealthCondition());
		entity.setPersonAnnualIncome(info.getPersonAnnualIncome());
		entity.setUniformityFlag(info.getUniformityFlag());
		entity.setLeftBehindResidentType(info.getLeftBehindResidentType());
		entity.setMainMemberIdcardNo(info.getMainMemberIdcardNo());
		entity.setMainMemberName(info.getMainMemberName());
		entity.setMainMemberHealthCondition(info.getMainMemberHealthCondition());
		entity.setLeftBehindResidentRelationship(info.getLeftBehindResidentRelationship());
		entity.setMainMemberContact(info.getMainMemberContact());
		entity.setMainMemberAddress(info.getMainMemberAddress());
		entity.setFamilyAnnualIncome(info.getFamilyAnnualIncome());
		entity.setDifficultAppeal(info.getDifficultAppeal());
		entity.setAssistState(info.getAssistState());
		entity.setCreateUserId(info.getCreateUserId());
		return entity;
	}

	@Override
	public List<StatsResult> getLeftBehindTypeStats() {
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
		return leftBehindResidentDao.getLeftBehindTypeStats(residentBaseIds);
	}

	@Override
	public List<StatsResult> getGridStats() {
		// TODO Auto-generated method stub
		List<StatsResult> statsResults = new ArrayList<StatsResult>();
		int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
		IOrganizationService organizationServiceImpl= (IOrganizationService)ServiceModuleFactory.getFramework().getModuleManager().getModule(IOrganizationService.APP_ID);
		List<OrgPosition> positionList=organizationServiceImpl.getUserPosition (userId);
		if(positionList.get(0).typeName.equals("网格员")){
			Map<Integer, Integer> buildingsMap = houseManageService.getLabelResidentNum(IPersonManageService.LABEL_LEFT_BEHIND);
			for(Integer key:buildingsMap.keySet()){
				BuildingInfo buildingInfo = houseManageService.getBaseBuildingById(key);
				StatsResult statsResult = new StatsResult();
				statsResult.setName(buildingInfo.getBuildingName());
				statsResult.setTotal(buildingsMap.get(key));
				statsResults.add(statsResult);
			}
		}else if(positionList.get(0).typeName.equals("社区坐席")){
			IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for(GridItem gridItem:gridItems){	
				List<Integer> gridIdss =  new ArrayList<Integer>();
				gridIdss.add(gridItem.getGridId());
				List<Integer> residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIdss, null);
				List<LeftBehindResidentDto> list = leftBehindResidentDao.getLeftBehindListByKeywords(null,null,null,null,residentBaseIds);
				StatsResult statsResult = new StatsResult();
				statsResult.setName(gridItem.getGridName());
				statsResult.setTotal(list.size());
				statsResults.add(statsResult);
			}
		}else if(positionList.get(0).typeName.equals("街道坐席")){
			OrgStructure orgStructure = organizationServiceImpl.getOrgStructure(positionList.get(0).orgId,true,false,false);
			List<OrgStructure> communityOrgList=orgStructure.subOrg;
			for(OrgStructure communityOrg:communityOrgList){
				List<Integer> gridIdss =  new ArrayList<Integer>();
				getManagedGrids(communityOrg,gridIdss);
				if(gridIdss !=null && gridIdss.size()==0){
					StatsResult statsResult = new StatsResult();
					statsResult.setName(communityOrg.orgName);
					statsResult.setTotal(0);
					statsResults.add(statsResult);
					continue;
				}
				List<Integer> residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIdss, null);
				List<LeftBehindResidentDto> list = leftBehindResidentDao.getLeftBehindListByKeywords(null,null,null,null,residentBaseIds);
				StatsResult statsResult = new StatsResult();
				statsResult.setName(communityOrg.orgName);
				statsResult.setTotal(list.size());
				statsResults.add(statsResult);
			}
		}
		return statsResults;
	}
	
	private void getManagedGrids(OrgStructure orgStructure, List<Integer> grids){
		if ("网格".equals(orgStructure.typeName)) {
			grids.add(orgStructure.orgId);
		}else {
			List<OrgStructure> subOrgs = orgStructure.subOrg;
			if(subOrgs != null && subOrgs.size() > 0){
				for (OrgStructure subOrg : subOrgs) {
					getManagedGrids(subOrg,grids);
				}
			}
		}
	}
}
