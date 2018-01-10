package com.hxts.unicorn.modules.resident.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.ForeignerInfoDao;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.dto.ForeignerInfoDto;
import com.hxts.unicorn.modules.resident.dto.HouseDto;
import com.hxts.unicorn.modules.resident.model.ForeignerInfo;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvForeignerInfo;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.ForeignerResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;

@Service
public class SrvForeignerInfo implements ISrvForeignerInfo {
	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private ForeignerInfoDao foreignerInfoDao;
	@Autowired
	private IHouseManageService houseManageService;
	
	@Override
	public int save(ForeignerResidentInfo foreignerInfo) {
		// TODO Auto-generated method stub
		Integer foreignerId=foreignerInfo.getForeignerId();
		Integer residentBaseId = foreignerInfo.getResidentBaseId();
		if(residentBaseId == null){
			throw new AppModuleErrorException("境外人员信息添加异常！");    
		}
		ResidentLabels labels = residentBaseInfoDao.getAllLabelId(residentBaseId, null);
		if(labels != null &&labels.getForeignerId() != null){
			foreignerId = labels.getForeignerId();
			foreignerInfo.setForeignerId(foreignerId);
		}
		if(foreignerId!=null){
			return foreignerInfoDao.updateByPrimaryKeySelective(toEntity(foreignerInfo));
		}else{
			int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
			foreignerInfo.setCreateUserId(userId);
			return foreignerInfoDao.insert(toEntity(foreignerInfo));
		}
	}

	@Override
	public int deleteByForeignerId(Integer foreignerId) {
		// TODO Auto-generated method stub
		return foreignerInfoDao.deleteByPrimaryKey(foreignerId);
	}
	
	@Override
	public ForeignerResidentInfo selectByForeignerId(Integer foreignerId) {
		// TODO Auto-generated method stub
		return foreignerInfoDao.selectByPrimaryKey(foreignerId);
	}
	
	public List<ForeignerInfoDto> getForeignerInfoListByKeywords(String nationality,BigDecimal integrity, String name, String idNo, List<Integer> residentBaseIds){
		List<ForeignerInfoDto> list = new ArrayList<ForeignerInfoDto>();
		if(residentBaseIds!=null && residentBaseIds.size()==0){
			return list;
		}
		list = foreignerInfoDao.queryForeignerInfoListByKeywords(nationality,integrity,name,idNo,residentBaseIds);
		if(list!=null && list.size()==0){
			return list;
		}
		List<Integer> residentIds = new ArrayList<Integer>();
		for (ForeignerInfoDto dto : list) {
			residentIds.add(dto.getResidentBaseId());
		}
		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);
		for (ForeignerInfoDto dto : list) {
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
	
	public ForeignerInfo toEntity(ForeignerResidentInfo info){
		ForeignerInfo entity = new ForeignerInfo();
		entity.setForeignerId(info.getForeignerId());
		entity.setResidentBaseId(info.getResidentBaseId());
		entity.setForeignSurname(info.getForeignSurname());
		entity.setForeignGivenName(info.getForeignGivenName());
		entity.setNationality(info.getNationality());
		entity.setCertificateCode(info.getCertificateCode());
		entity.setCertificateExpireDate(info.getCertificateExpireDate());
		entity.setPurpose(info.getPurpose());
		entity.setArriveDate(info.getArriveDate());
		entity.setExpectDepartureDate(info.getExpectDepartureDate());
		entity.setIsFocusPerson(info.getIsFocusPerson());
		entity.setCreateUserId(info.getCreateUserId());
		return entity;
	}

	@Override
	public List<StatsResult> getPurposeStats() {
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
		return foreignerInfoDao.getPurposeStats(residentBaseIds);
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
		return foreignerInfoDao.getAgeStats(residentBaseIds);
	}
}
