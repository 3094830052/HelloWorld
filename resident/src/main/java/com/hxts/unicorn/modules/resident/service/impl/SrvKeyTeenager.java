package com.hxts.unicorn.modules.resident.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.KeyTeenagerDao;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.dto.HouseDto;
import com.hxts.unicorn.modules.resident.dto.KeyTeenagerDto;
import com.hxts.unicorn.modules.resident.model.KeyTeenager;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvKeyTeenager;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.KeyTeenagerInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;

@Service
public class SrvKeyTeenager implements ISrvKeyTeenager {
	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private KeyTeenagerDao keyTeenagerDao;
	@Autowired
	private IHouseManageService houseManageService;
	@Override
	public int save(KeyTeenagerInfo keyTeenagerInfo) {
		// TODO Auto-generated method stub
		Integer keyTeenagerId=keyTeenagerInfo.getKeyTeenagerId();
		Integer residentBaseId = keyTeenagerInfo.getResidentBaseId();
		if(residentBaseId == null){
			throw new AppModuleErrorException("重点青少年信息添加异常！");    
		}
		ResidentLabels labels = residentBaseInfoDao.getAllLabelId(residentBaseId, null);
		if(labels != null &&labels.getKeyTeenagerId() != null){
			keyTeenagerId = labels.getKeyTeenagerId();
			keyTeenagerInfo.setKeyTeenagerId(keyTeenagerId);
		}
		if(keyTeenagerId!=null){
			return keyTeenagerDao.updateByPrimaryKeySelective(toEntity(keyTeenagerInfo));
		}else{
			int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
			keyTeenagerInfo.setCreateUserId(userId);
			return keyTeenagerDao.insert(toEntity(keyTeenagerInfo));
		}
	}

	@Override
	public int deleteByKeyTeenagerId(Integer keyTeenagerId) {
		// TODO Auto-generated method stub
		return keyTeenagerDao.deleteByPrimaryKey(keyTeenagerId);
	}

	@Override
	public KeyTeenagerInfo selectByKeyTeenagerId(Integer keyTeenagerId) {
		// TODO Auto-generated method stub
		return keyTeenagerDao.selectByPrimaryKey(keyTeenagerId);
	}
	
	public List<KeyTeenagerDto> getKeyTeenagerListByKeywords(String teenagerType,String name, String idNo, List<Integer> residentBaseIds){
		List<KeyTeenagerDto> list = new ArrayList<KeyTeenagerDto>();
		if(residentBaseIds!=null && residentBaseIds.size()==0){
			return list;
		}
		list = keyTeenagerDao.queryKeyTeenagerListByKeywords(teenagerType,name,idNo,residentBaseIds);
		if(list!=null && list.size()==0){
			return list;
		}
		List<Integer> residentIds = new ArrayList<Integer>();
		for (KeyTeenagerDto dto : list) {
			residentIds.add(dto.getResidentBaseId());
		}
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);
		for (KeyTeenagerDto dto : list) {
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
	
	@Override
	public List<StatsResult> getTeenagerTypeStats() {
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
		return keyTeenagerDao.getTeenagerTypeStats(residentBaseIds);
	}
	
	public KeyTeenager toEntity(KeyTeenagerInfo info) {
		// TODO Auto-generated method stub
		KeyTeenager entity = new KeyTeenager();
		entity.setKeyTeenagerId(info.getKeyTeenagerId());
		entity.setResidentBaseId(info.getResidentBaseId());
		entity.setTeenagerType(info.getTeenagerType());
		entity.setFamilySituation(info.getFamilySituation());
		entity.setGuarderIdNo(info.getGuarderIdNo());
		entity.setGuarderName(info.getGuarderName());
		entity.setGuarderRelationship(info.getGuarderRelationship());
		entity.setGuarderAddr(info.getGuarderAddr());
		entity.setIsCrime(info.getIsCrime());
		entity.setAssisterName(info.getAssisterName());
		entity.setAssisterContact(info.getAssisterContact());
		entity.setAssistMeasure(info.getAssistMeasure());
		entity.setAssistState(info.getAssistState());
		entity.setCreateUserId(info.getCreateUserId());
		return entity;
	}
}
