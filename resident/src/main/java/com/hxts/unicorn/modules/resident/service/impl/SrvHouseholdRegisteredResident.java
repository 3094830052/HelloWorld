package com.hxts.unicorn.modules.resident.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.HouseholdRegisteredResidentDao;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.dto.HouseDto;
import com.hxts.unicorn.modules.resident.dto.HouseholdRegisteredResidentDto;
import com.hxts.unicorn.modules.resident.model.HouseholdRegisteredResident;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvHouseholdRegisteredResident;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.HouseholdResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;

@Service
public class SrvHouseholdRegisteredResident implements ISrvHouseholdRegisteredResident{
	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private HouseholdRegisteredResidentDao householdRegisteredResidentDao;
	@Autowired
	private IHouseManageService houseManageService;
	
	@Override
	public int save(HouseholdResidentInfo householdResidentInfo) {
		// TODO Auto-generated method stub
		Integer householdId = householdResidentInfo.getHouseholdId();
		Integer residentBaseId = householdResidentInfo.getResidentBaseId();
		if(residentBaseId == null){
			throw new AppModuleErrorException("户籍人口信息添加异常！");    
		}
		ResidentLabels labels = residentBaseInfoDao.getAllLabelId(residentBaseId, null);
		if(labels != null &&labels.getHouseholdId() != null){
			householdId = labels.getHouseholdId();
			householdResidentInfo.setHouseholdId(householdId);
		}
		if(householdId!=null){
			return householdRegisteredResidentDao.updateByPrimaryKeySelective(toEntity(householdResidentInfo));
		}else{
			int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
			householdResidentInfo.setCreateUserId(userId);
			return householdRegisteredResidentDao.insert(toEntity(householdResidentInfo));
		}
	}

	@Override
	public int deleteByHouseholdId(Integer householdId) {
		// TODO Auto-generated method stub
		return householdRegisteredResidentDao.deleteByPrimaryKey(householdId);
	}

	@Override
	public HouseholdResidentInfo selectByHouseholdId(Integer householdId) {
		// TODO Auto-generated method stub
		return householdRegisteredResidentDao.selectByPrimaryKey(householdId);
	}
	
	@Override
	public List<HouseholdResidentInfo> selectByKeyWord(String householdNo,String idNo) {
		// TODO Auto-generated method stub
		List<HouseholdResidentInfo> infoList=householdRegisteredResidentDao.selectByKeyWord(householdNo,idNo);
		return infoList;
	}
	
	public List<HouseholdRegisteredResidentDto> getHouseholdRegisterListByKeywords(String uniformityFlag,BigDecimal integrity, String name, String idNo, List<Integer> residentBaseIds){
		List<HouseholdRegisteredResidentDto> list = new ArrayList<HouseholdRegisteredResidentDto>();
		if(residentBaseIds!=null && residentBaseIds.size()==0){
			return list;
		}
		list = householdRegisteredResidentDao.queryHouseholdRegisterListByKeywords(uniformityFlag,integrity,name,idNo,residentBaseIds);
		if(list!=null && list.size()==0){
			return list;
		}
		List<Integer> residentIds = new ArrayList<Integer>();
		for (HouseholdRegisteredResidentDto dto : list) {
			residentIds.add(dto.getResidentBaseId());
		}
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);
		for (HouseholdRegisteredResidentDto dto : list) {
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
	
	public HouseholdRegisteredResident toEntity(HouseholdResidentInfo dto){
		HouseholdRegisteredResident entity = new HouseholdRegisteredResident();
		entity.setHouseholdId(dto.getHouseholdId());
		entity.setResidentBaseId(dto.getResidentBaseId());
		entity.setUniformityFlag(dto.getUniformityFlag());
		entity.setHouseholdNo(dto.getHouseholdNo());
		entity.setHouseholderIdcardNo(dto.getHouseholderIdcardNo());
		entity.setHouseholderName(dto.getHouseholderName());
		entity.setHouseholderRelationship(dto.getHouseholderRelationship());
		entity.setHouseholderContact(dto.getHouseholderContact());
		entity.setCreateUserId(dto.getCreateUserId());
		return entity;
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
		return householdRegisteredResidentDao.getDegreeStats(residentBaseIds);
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
		return householdRegisteredResidentDao.getAgeStats(residentBaseIds);
	}
}
