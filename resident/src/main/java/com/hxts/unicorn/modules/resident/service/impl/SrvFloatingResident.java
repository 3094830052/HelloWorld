package com.hxts.unicorn.modules.resident.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.FloatingResidentDao;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.dto.FloatingResidentDto;
import com.hxts.unicorn.modules.resident.dto.HouseDto;
import com.hxts.unicorn.modules.resident.model.FloatingResident;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvFloatingResident;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.FloatingResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;

@Service
public class SrvFloatingResident implements ISrvFloatingResident{
	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private FloatingResidentDao floatingResidentDao;
	@Autowired
	private IHouseManageService houseManageService;

	@Override
	public int save(FloatingResidentInfo floatingResidentInfo) {
		// TODO Auto-generated method stub
		Integer floatingId=floatingResidentInfo.getFloatingId();
		Integer residentBaseId = floatingResidentInfo.getResidentBaseId();
		if(residentBaseId == null){
			throw new AppModuleErrorException("流动人口信息添加异常！");    
		}
		ResidentLabels labels = residentBaseInfoDao.getAllLabelId(residentBaseId, null);
		if(labels != null &&labels.getFloatingId() != null){
			floatingId = labels.getFloatingId();
			floatingResidentInfo.setFloatingId(floatingId);
		}
		if(floatingId!=null){
			return floatingResidentDao.updateByPrimaryKeySelective(toEntity(floatingResidentInfo));
		}else{
			int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
			floatingResidentInfo.setCreateUserId(userId);
			return floatingResidentDao.insert(toEntity(floatingResidentInfo));
		}
	}

	@Override
	public int deleteByFloatingId(Integer floatingId) {
		// TODO Auto-generated method stub
		return floatingResidentDao.deleteByPrimaryKey(floatingId);
	}

	@Override
	public FloatingResidentInfo selectByFloatingId(Integer floatingId) {
		// TODO Auto-generated method stub
		return floatingResidentDao.selectByPrimaryKey(floatingId);
	}
	
	public List<FloatingResidentDto> getFloatingResidentListByKeywords(String inflowReason,String isFocusPerson,String certificateHandlingType,String certificateExpireDate,String name, String idNo, List<Integer> residentBaseIds){
		List<FloatingResidentDto> list = new ArrayList<FloatingResidentDto>();
		if(residentBaseIds!=null && residentBaseIds.size()==0){
			return list;
		}
		list = floatingResidentDao.queryFloatingResidentListByKeywords(inflowReason,isFocusPerson,certificateHandlingType,certificateExpireDate,name,idNo,residentBaseIds);
		if(list!=null && list.size()==0){
			return list;
		}
		List<Integer> residentIds = new ArrayList<Integer>();
		for (FloatingResidentDto dto : list) {
			residentIds.add(dto.getResidentBaseId());
		}
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);
		for (FloatingResidentDto dto : list) {
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
	
	public FloatingResident toEntity(FloatingResidentInfo dto){
		FloatingResident entity = new FloatingResident();
		entity.setFloatingId(dto.getFloatingId());
		entity.setResidentBaseId(dto.getResidentBaseId());
		entity.setInflowReason(dto.getInflowReason());
		entity.setCertificateHandlingType(dto.getCertificateHandlingType());
		entity.setCertificateNo(dto.getCertificateNo());
		entity.setRegisterDate(dto.getRegisterDate());
		entity.setCertificateExpireDate(dto.getCertificateExpireDate());
		entity.setResidenceType(dto.getResidenceType());
		entity.setIsFocusPerson(dto.getIsFocusPerson());
		entity.setCreateUserId(dto.getCreateUserId());
		return entity;
	}

	@Override
	public List<StatsResult> getInflowReasonStats() {
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
		return floatingResidentDao.getInflowReasonStats(residentBaseIds);
	}

	@Override
	public List<StatsResult> getCertificateExpireStats() {
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
		return floatingResidentDao.getCertificateExpireStats(residentBaseIds);
	}
}
