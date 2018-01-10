package com.hxts.unicorn.modules.resident.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.dto.HouseDto;
import com.hxts.unicorn.modules.resident.dto.RenterInfoDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvRenterInfo;
import com.hxts.unicorn.modules.resident.service.ISrvResidentBaseInfo;
import com.hxts.unicorn.platform.interfaces.basic.organization.IOrganizationService;
import com.hxts.unicorn.platform.interfaces.basic.organization.OrgPosition;
import com.hxts.unicorn.platform.interfaces.basic.organization.OrgStructure;
import com.hxts.unicorn.platform.interfaces.biz.BuildingInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.RenterInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;

@Service
public class SrvRenterInfo implements ISrvRenterInfo {
	@Autowired
	private ResidentBaseInfoDao residentBaseDao;
	@Autowired
	private ISrvResidentBaseInfo srvResidentBaseInfo;
	@Autowired
	private IHouseManageService houseManageService;

	@Override
	public List<RenterInfoDto> getRenterInfoList(List<Integer> residentBaseIds, String name,
			String idNo, BigDecimal integrity, String ethnicity) {
		// TODO Auto-generated method stub
		List<RenterInfoDto> list = new ArrayList<RenterInfoDto>();
		if(residentBaseIds!=null && residentBaseIds.size()==0){
			return list;
		}
		list = residentBaseDao.getRenterInfoList(name, idNo, integrity, ethnicity, residentBaseIds);
		if(list!=null && list.size()==0){
			return list;
		}
		List<Integer> residentIds = new ArrayList<Integer>();
		for (RenterInfoDto dto : list) {
			residentIds.add(dto.getResidentBaseId());
		}
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);
		for (RenterInfoDto dto : list) {
			dto.setHouseInfos(toHouseDtoList(houseInfos.get(dto.getResidentBaseId())));
		}
		return list;
	}

	@Override
	public int save(RenterInfo renterInfo ) {
		// TODO Auto-generated method stub
		return houseManageService.associate(renterInfo.getHouseId(), renterInfo.getResidentBaseId(), "02", renterInfo.getRentType(), renterInfo.getHiddenDanger());
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

	@Override
	public List<StatsResult> getAgeStats() {
		// TODO Auto-generated method stub
		List<StatsResult> result = new ArrayList<StatsResult>();
		List<Integer> gridIds = new ArrayList<Integer>();
		IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IGridService.APP_ID);
		List<GridItem> gridItems = gridService.getCurrentUserGrid();
		for(GridItem gridItem:gridItems){	
			gridIds.add(gridItem.getGridId());
		}
		List<Integer> residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, "02");
		if(residentBaseIds!=null && residentBaseIds.size()>0){
			result = residentBaseDao.getRenterAgeStats(residentBaseIds);
		}
		return result;
	}
	
	@Override
	public List<StatsResult> getRentHouseNumsStats() {
		// TODO Auto-generated method stub
		List<Integer> gridIds = new ArrayList<Integer>();
		List<StatsResult> resultList= new ArrayList<StatsResult>();
		IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IGridService.APP_ID);
		List<GridItem> gridItems = gridService.getCurrentUserGrid();
		for(GridItem gridItem:gridItems){	
			gridIds.add(gridItem.getGridId());
		}
		List<BuildingInfo> buildingInfos = houseManageService.queryBuildingByGridIds(gridIds);
		Map<Integer, Integer> buildingStats = houseManageService.getRentHouseNumOfBuildings(buildingInfos);
		int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
		IOrganizationService organizationServiceImpl= (IOrganizationService)ServiceModuleFactory.getFramework().getModuleManager().getModule(IOrganizationService.APP_ID);
		List<OrgPosition> positionList=organizationServiceImpl.getUserPosition (userId);
		if(positionList.get(0).typeName.equals("网格员")){
			for(Integer key:buildingStats.keySet()){
				BuildingInfo buildingInfo = houseManageService.getBaseBuildingById(key);
				StatsResult statsResult = new StatsResult();
				statsResult.setName(buildingInfo.getBuildingName());
				statsResult.setTotal(buildingStats.get(key));
				resultList.add(statsResult);
			}
		}else if(positionList.get(0).typeName.equals("社区坐席")){
				for(GridItem gridItem:gridItems){	
					for(Integer key:buildingStats.keySet()){
						BuildingInfo buildingInfo = houseManageService.getBaseBuildingById(key);
						if(buildingInfo.getGridId()==gridItem.getGridId()){
							StatsResult statsResult = new StatsResult();
							statsResult.setName(gridItem.getGridName());
							statsResult.setTotal(buildingStats.get(key));
							boolean flag = false;
							int index = 0;
							for(int i=0; i < resultList.size(); i++){
								if(resultList.get(i).getName()== gridItem.getGridName()){
									flag = true;
									index = i;
									continue;
								}
							}
							if(!flag){
								resultList.add(statsResult);
							}else{
								statsResult.setTotal(statsResult.getTotal()+resultList.get(index).getTotal());
								resultList.set(index, statsResult);
							}
						}
					}
				}
		}else if(positionList.get(0).typeName.equals("街道坐席")){
			OrgStructure orgStructure = organizationServiceImpl.getOrgStructure(positionList.get(0).orgId,true,false,false);
			List<OrgStructure> communityOrgList=orgStructure.subOrg;
			for(OrgStructure communityOrg:communityOrgList){
				List<Integer> gridIdss =  new ArrayList<Integer>();
				getManagedGrids(communityOrg,gridIdss);
				for(Integer gridId:gridIdss){
					for(Integer key:buildingStats.keySet()){
						BuildingInfo buildingInfo = houseManageService.getBaseBuildingById(key);
						if(buildingInfo.getGridId() == gridId){
							StatsResult statsResult = new StatsResult();
							statsResult.setName(communityOrg.orgName);
							statsResult.setTotal(buildingStats.get(key));
							boolean flag = false;
							int index = 0;
							for(int i=0; i < resultList.size(); i++){
								if(resultList.get(i).getName()== communityOrg.orgName){
									flag = true;
									index = i;
									continue;
								}
							}
							if(!flag){
								resultList.add(statsResult);
							}else{
								statsResult.setTotal(statsResult.getTotal()+resultList.get(index).getTotal());
								resultList.set(index, statsResult);
							}	
						}
					}
				}
			}
		}
		return resultList;
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
