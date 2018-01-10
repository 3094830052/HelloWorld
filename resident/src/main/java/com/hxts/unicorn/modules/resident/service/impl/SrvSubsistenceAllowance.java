package com.hxts.unicorn.modules.resident.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.dao.SubsistenceAllowanceDao;
import com.hxts.unicorn.modules.resident.dto.AssistCrowdDto;
import com.hxts.unicorn.modules.resident.dto.HouseDto;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.model.SubsistenceAllowance;
import com.hxts.unicorn.modules.resident.service.ISrvSubsistenceAllowance;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.SubsistenceAllowanceInfo;

@Service
public class SrvSubsistenceAllowance implements ISrvSubsistenceAllowance {
	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private SubsistenceAllowanceDao subsistenceAllowanceDao;
	@Autowired
	private IHouseManageService houseManageService;
	
	@Override
	public int save(SubsistenceAllowanceInfo subsistenceAllowanceInfo) {
		// TODO Auto-generated method stub
		Integer subsistAllowanceId=subsistenceAllowanceInfo.getSubsistAllowanceId();
		Integer residentBaseId = subsistenceAllowanceInfo.getResidentBaseId();
		if(residentBaseId == null){
			throw new AppModuleErrorException("低保人员信息添加异常！");    
		}
		ResidentLabels labels = residentBaseInfoDao.getAllLabelId(residentBaseId, null);
		if(labels != null &&labels.getSubsistAllowanceId() != null){
			subsistAllowanceId = labels.getSubsistAllowanceId();
			subsistenceAllowanceInfo.setSubsistAllowanceId(subsistAllowanceId);
		}
		if(subsistAllowanceId!=null){
			return subsistenceAllowanceDao.updateByPrimaryKeySelective(toEntity(subsistenceAllowanceInfo));
		}else{
			int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
			subsistenceAllowanceInfo.setCreateUserId(userId);
			return subsistenceAllowanceDao.insert(toEntity(subsistenceAllowanceInfo));
		}
	}

	@Override
	public int deleteBySubsistAllowanceId(Integer subsistAllowanceId) {
		// TODO Auto-generated method stub
		return subsistenceAllowanceDao.deleteByPrimaryKey(subsistAllowanceId);
	}

	@Override
	public SubsistenceAllowanceInfo selectBySubsistAllowanceId(Integer subsistAllowanceId) {
		// TODO Auto-generated method stub
		return subsistenceAllowanceDao.selectByPrimaryKey(subsistAllowanceId);
	}
	
	@Override
	public List<StatsResult> getAssistTypeStats() {
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
		return subsistenceAllowanceDao.getAssistTypeStats(residentBaseIds);
	}
	
	@Override
	public List<Integer> getAssistTypeStatsCount() {
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
		return subsistenceAllowanceDao.getAssistTypeStatsCount(residentBaseIds);
	}
	
	public List<AssistCrowdDto> getAssistCrowdListByKeywords(String healthCondition,String assistType,String name,String idNo,List<Integer> residentBaseIds){
		List<AssistCrowdDto> list = new ArrayList<AssistCrowdDto>();
		if(residentBaseIds!=null && residentBaseIds.size()==0){
			return list;
		}
		list = subsistenceAllowanceDao.queryAssistCrowdListByKeywords(healthCondition,assistType,name,idNo,residentBaseIds);
		if(list!=null && list.size()==0){
			return list;
		}
		List<Integer> residentIds = new ArrayList<Integer>();
		for (AssistCrowdDto dto : list) {
			residentIds.add(dto.getResidentBaseId());
		}
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);
		for (AssistCrowdDto dto : list) {
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
	public SubsistenceAllowance toEntity(SubsistenceAllowanceInfo info) {
		// TODO Auto-generated method stub
		SubsistenceAllowance entity = new SubsistenceAllowance();
		entity.setSubsistAllowanceId(info.getSubsistAllowanceId());
		entity.setResidentBaseId(info.getResidentBaseId());
		entity.setHealthCondition(info.getHealthCondition());
		entity.setFamilyMember(info.getFamilyMember());
		entity.setFamilyEmployStatus(info.getFamilyEmployStatus());
		entity.setFamilyHealthCondition(info.getFamilyHealthCondition());
		entity.setFamilyMonthIncome(info.getFamilyMonthIncome());
		entity.setHasDisabled(info.getHasDisabled());
		entity.setMonthReliefFund(info.getMonthReliefFund());
		entity.setPovertyReason(info.getPovertyReason());
		entity.setSubsistAllowanceCategory(info.getSubsistAllowanceCategory());
		entity.setCreateUserId(info.getCreateUserId());
		return entity;
	}
}
