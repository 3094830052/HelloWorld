package com.hxts.unicorn.modules.resident.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.DrugInfoDao;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.dto.DrugDto;
import com.hxts.unicorn.modules.resident.dto.HouseDto;
import com.hxts.unicorn.modules.resident.dto.SpecialCrowdDto;
import com.hxts.unicorn.modules.resident.model.Drug;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvDrug;
import com.hxts.unicorn.platform.interfaces.basic.organization.IOrganizationService;
import com.hxts.unicorn.platform.interfaces.basic.organization.OrgPosition;
import com.hxts.unicorn.platform.interfaces.basic.organization.OrgStructure;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.BuildingInfo;
import com.hxts.unicorn.platform.interfaces.biz.DrugInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IPersonManageService;

@Service
public class SrvDrug implements ISrvDrug {

	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private DrugInfoDao drugInfoDao;
	@Autowired
	private IHouseManageService houseManageService;

	@Override
	public int save(DrugInfo DrugInfo) {
		// TODO Auto-generated method stub
		Integer DrugId = DrugInfo.getDrugAddictId();
		Integer residentBaseId = DrugInfo.getResidentBaseId();
		if (residentBaseId == null) {
			throw new AppModuleErrorException("吸毒者信息添加异常！");
		}
		ResidentLabels labels = residentBaseInfoDao.getAllLabelId(residentBaseId, null);
		if (labels != null && labels.getDrugId() != null) {
			DrugId = labels.getDrugId();
			DrugInfo.setDrugAddictId(DrugId);
		}
		if (DrugId != null) {
			return drugInfoDao.updateByPrimaryKeySelective(toEntity(DrugInfo));
		} else {
			int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
			DrugInfo.setCreateUserId(userId);
			return drugInfoDao.insert(toEntity(DrugInfo));
		}

	}

	@Override
	public int deleteByDrugInfoId(Integer DrugInfoId) {
		// TODO Auto-generated method stub
		return drugInfoDao.deleteByPrimaryKey(DrugInfoId);
	}

	@Override
	public DrugInfo selectByDrugInfoId(Integer DrugInfoId) {
		// TODO Auto-generated method stub
		return drugInfoDao.selectByPrimaryKey(DrugInfoId);
	}

	public Drug toEntity(DrugInfo info) {
		// TODO Auto-generated method stub
		Drug entity = new Drug();
		entity.setDrugAddictId(info.getDrugAddictId());
		entity.setControlName(info.getControlName());
		entity.setControlPhone(info.getControlPhone());
		entity.setControlSituation(info.getControlSituation());
		entity.setCreateUserId(info.getCreateUserId());
		entity.setCrimeCondition(info.getCrimeCondition());
		entity.setDateFirstDiscovery(info.getDateFirstDiscovery());
		entity.setIsCrime(info.getIsCrime());
		entity.setDrugConsequence(info.getDrugConsequence());
		entity.setDrugReasons(info.getDrugReasons());
		entity.setResidentBaseId(info.getResidentBaseId());
		entity.setSupportName(info.getSupportName());
		entity.setSupportPhone(info.getSupportPhone());
		entity.setSupportSituation(info.getSupportSituation());
		return entity;
	}

	public List<DrugDto> getDrugInfoListByKeywords(String name, String idNo, Integer gridId, Integer iscrime,
			String controlsituation,Integer datefirstdiscover) {
		// TODO Auto-generated method stub
		List<Integer> gridIds = new ArrayList<Integer>();
		if (gridId == null) {
			IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager()
					.getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for (GridItem gridItem : gridItems) {
				gridIds.add(gridItem.getGridId());
			}
		} else {
			gridIds.add(gridId);
		}
		List<Integer> residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, null);
		List<DrugDto> list = drugInfoDao.querydrugInfoListByKeywords(name, idNo, residentBaseIds, iscrime,
				controlsituation,datefirstdiscover);
		List<Integer> residentIds = new ArrayList<Integer>();
		for (DrugDto dto : list) {
			residentIds.add(dto.getResidentBaseId());
		}
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);
		for (DrugDto dto : list) {
			dto.setHouseInfos(toHouseDtoList(houseInfos.get(dto.getResidentBaseId())));
		}
		return list;
	}

	public List<HouseDto> toHouseDtoList(List<ResidentOfHouseInfo> infos) {
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
				dto.setHouseAddress(houseInfo.getBuildingInfo().getBuildingName() + houseInfo.getUnitNumber() + "单元"
						+ houseInfo.getFloorNumber() + "0" + houseInfo.getHouseNumber());
			}
			dtos.add(dto);
		}
		return dtos;
	}
	
	@Override
	public List<StatsResult> getSpecialTypeStats() {
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
		return drugInfoDao.getSpecialTypeStats(residentBaseIds);
	}
	
	@Override
	public List<Integer> getSpecialTypeStatsCount() {
		// TODO Auto-generated method stub
		List<Integer> gridIds = new ArrayList<Integer>();
		IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IGridService.APP_ID);
		List<GridItem> gridItems = gridService.getCurrentUserGrid();
		for(GridItem gridItem:gridItems){	
			gridIds.add(gridItem.getGridId());
		}
		if(gridIds!=null && gridIds.size()==0){
			return gridIds;
		}
		List<Integer> residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, null);
		return drugInfoDao.getSpecialTypeStatsCount(residentBaseIds);
	}
	
	@Override
	public List<StatsResult> getGridStats() {
		// TODO Auto-generated method stub
		List<StatsResult> statsResults = new ArrayList<StatsResult>();
		int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
		IOrganizationService organizationServiceImpl= (IOrganizationService)ServiceModuleFactory.getFramework().getModuleManager().getModule(IOrganizationService.APP_ID);
		List<OrgPosition> positionList=organizationServiceImpl.getUserPosition (userId);
		if(positionList.get(0).typeName.equals("网格员")){
			Map<Integer, Integer> buildingsMap = houseManageService.getLabelResidentNum(IPersonManageService.LABEL_SPECIAL);
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
				List<SpecialCrowdDto> list = drugInfoDao.querySpecialCrowdListByKeywords(null,null,null,residentBaseIds);
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
				List<SpecialCrowdDto> list = drugInfoDao.querySpecialCrowdListByKeywords(null,null,null,residentBaseIds);
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
	
	public List<SpecialCrowdDto> getSpecialCrowdListByKeywords(String specialType,String name,String idNo,List<Integer> residentBaseIds){
		List<SpecialCrowdDto> list = new ArrayList<SpecialCrowdDto>();
		if(residentBaseIds!=null && residentBaseIds.size()==0){
			return list;
		}
		list = drugInfoDao.querySpecialCrowdListByKeywords(specialType,name,idNo,residentBaseIds);
		if(list!=null && list.size()==0){
			return list;
		}
		List<Integer> residentIds = new ArrayList<Integer>();
		for (SpecialCrowdDto dto : list) {
			residentIds.add(dto.getResidentBaseId());
		}
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);
		for (SpecialCrowdDto dto : list) {
			dto.setHouseInfos(toHouseDtoList(houseInfos.get(dto.getResidentBaseId())));
		}
		return list;
	}
}
