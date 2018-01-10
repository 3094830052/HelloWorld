package com.hxts.unicorn.modules.resident.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.IllegalPertitionerDao;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.dto.HouseDto;
import com.hxts.unicorn.modules.resident.dto.IllegalPertitionerDto;
import com.hxts.unicorn.modules.resident.model.IllegalPertitioner;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvIllegalPertitioner;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.IllegalPertitionerInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;

@Service
public class SrvIllegalPertitioner implements ISrvIllegalPertitioner{
	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private IllegalPertitionerDao illegalPertitionerDao;
	@Autowired
	private IHouseManageService houseManageService;
	
	@Override
	public int save(IllegalPertitionerInfo illegalPertitionerInfo) {
		// TODO Auto-generated method stub
		Integer illegalPertitionerId = illegalPertitionerInfo.getIllegalPertitionerId();
		Integer residentBaseId = illegalPertitionerInfo.getResidentBaseId();
		if(residentBaseId == null){
			throw new AppModuleErrorException("非法上访人口信息添加异常！");    
		}
		ResidentLabels labels = residentBaseInfoDao.getAllLabelId(residentBaseId, null);
		if(labels != null &&labels.getIllegalPertitionerId() != null){
			illegalPertitionerId = labels.getHouseholdId();
			illegalPertitionerInfo.setIllegalPertitionerId(illegalPertitionerId);
		}
		if(illegalPertitionerId!=null){
			return illegalPertitionerDao.updateByPrimaryKeySelective(toEntity(illegalPertitionerInfo));
		}else{
			int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
			illegalPertitionerInfo.setCreateUserId(userId);
			return illegalPertitionerDao.insert(toEntity(illegalPertitionerInfo));
		}
	}

	@Override
	public int deleteByIllegalPertitionerId(Integer illegalPertitionerId) {
		// TODO Auto-generated method stub
		return illegalPertitionerDao.deleteByPrimaryKey(illegalPertitionerId);
	}

	@Override
	public IllegalPertitionerInfo selectByIllegalPertitionerId(Integer IllegalPertitionerId) {
		// TODO Auto-generated method stub
		return illegalPertitionerDao.selectByPrimaryKey(IllegalPertitionerId);
	}
	
	public List<IllegalPertitionerDto> getIllegalPertitionerListByKeywords(String pertitionType,String lastPertitionTime,String isCrime, String name, String idNo, Integer gridId){
		List<Integer> gridIds = new ArrayList<Integer>();
		if(gridId==null){
			IGridService gridService = (IGridService) ServiceModuleFactory.getFramework().getModuleManager().getModule(IGridService.APP_ID);
			List<GridItem> gridItems = gridService.getCurrentUserGrid();
			for(GridItem gridItem:gridItems){	
				gridIds.add(gridItem.getGridId());
			}
		}else{
			gridIds.add(gridId); 
		}
		List<Integer> residentBaseIds = houseManageService.getResidentIdsOfGrid(gridIds, null);
		List<IllegalPertitionerDto> list = illegalPertitionerDao.getIllegalPertitionerListByKeywords(pertitionType,lastPertitionTime,isCrime,name,idNo,residentBaseIds);
		List<Integer> residentIds = new ArrayList<Integer>();
		for (IllegalPertitionerDto dto : list) {
			residentIds.add(dto.getResidentBaseId());
		}
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);
		for (IllegalPertitionerDto dto : list) {
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
	
	public IllegalPertitioner toEntity(IllegalPertitionerInfo dto){
		IllegalPertitioner entity = new IllegalPertitioner();
		entity.setIllegalPertitionerId(dto.getIllegalPertitionerId());
		entity.setResidentBaseId(dto.getResidentBaseId());
		entity.setPertitionType(dto.getPertitionType());
		entity.setPertitionAppeal(dto.getPertitionAppeal());
		entity.setFirstPertitionTime(dto.getFirstPertitionTime());
		entity.setLastPertitionTime(dto.getLastPertitionTime());
		entity.setIsCrime(dto.getIsCrime());
		entity.setCrimeStatus(dto.getCrimeStatus());
		entity.setCreateUserId(dto.getCreateUserId());
		return entity;
	}

	@Override
	public List<StatsResult> getDegreeStats() {
		// TODO Auto-generated method stub
		//return illegalPertitionerDao.getDegreeStats();
		return null;
	}

	@Override
	public List<StatsResult> getAgeStats() {
		// TODO Auto-generated method stub
		//return illegalPertitionerDao.getAgeStats();
		return null;
	}
}
