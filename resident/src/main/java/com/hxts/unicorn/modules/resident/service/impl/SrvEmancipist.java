package com.hxts.unicorn.modules.resident.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.EmancipistDao;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.dto.EmancipistDto;
import com.hxts.unicorn.modules.resident.dto.HouseDto;
import com.hxts.unicorn.modules.resident.model.Emancipist;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.modules.resident.service.ISrvEmancipist;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.EmancipistInfo;
import com.hxts.unicorn.platform.interfaces.biz.GridItem;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.IGridService;
import com.hxts.unicorn.platform.interfaces.biz.IHouseManageService;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;

@Service
public class SrvEmancipist implements ISrvEmancipist {
	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private EmancipistDao emancipistDao;
	@Autowired
	private IHouseManageService houseManageService;
	@Override
	public int save(EmancipistInfo emancipistInfo) {
		Integer emancipistId = emancipistInfo.getEmancipistId();
		Integer residentBaseId = emancipistInfo.getResidentBaseId();
		if(residentBaseId == null){
			throw new AppModuleErrorException("刑满释放人员信息添加异常！");    
		}
		ResidentLabels labels = residentBaseInfoDao.getAllLabelId(residentBaseId, null);
		if(labels != null &&labels.getEmancipistId() != null){
			emancipistId = labels.getEmancipistId();
			emancipistInfo.setEmancipistId(emancipistId);
		}
		if(emancipistId!=null){
			return emancipistDao.updateByPrimaryKeySelective(toEntity(emancipistInfo));
		}else{
			int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
			emancipistInfo.setCreateUserId(userId);
			return emancipistDao.insert(toEntity(emancipistInfo));
		}
	}

	@Override
	public int deleteByEmancipistId(Integer emancipistId) {
		
		return emancipistDao.deleteByPrimaryKey(emancipistId);
	}
	@Override
	public EmancipistInfo selectByEmancipistId(Integer emancipistId) {
		// TODO Auto-generated method stub
		EmancipistInfo info = emancipistDao.selectByPrimaryKey(emancipistId);
		return info;
	}

	@Override
	public List<StatsResult> getEmancipistTypeStats() {
		// TODO Auto-generated method stub
		return null;
	}

	public Emancipist toEntity(EmancipistInfo info) {
		// TODO Auto-generated method stub
		Emancipist entity = new Emancipist();
		entity.setResidentBaseId(info.getResidentBaseId());
		entity.setEmancipistId(info.getEmancipistId());
		entity.setIsRecidivismId(info.getIsRecidivismId());
		entity.setOriginalCondemned(info.getOriginalCondemned());
		entity.setOriginalSentence(info.getOriginalSentence());
		entity.setPunishmentPlace(info.getPunishmentPlace());
		entity.setReleaseDate(info.getReleaseDate());
		entity.setRiskAssessmentType(info.getRiskAssessmentType());
		entity.setCohesionDate(info.getCohesionDate());
		entity.setCohesionSituation(info.getCohesionSituation());
		entity.setPlaceDate(info.getPlaceDate());
		entity.setPlaceSituation(info.getPlaceSituation());
		entity.setUnplaceReason(info.getUnplaceReason());
		entity.setHelpTeachSituation(info.getHelpTeachSituation());
		entity.setIsCrimeAgain(info.getIsCrimeAgain());
		entity.setReCondemned(info.getReCondemned());
		entity.setCreateUserId(info.getCreateUserId());
		return entity;
	}

	@Override
	public List<EmancipistDto> getEmancipistListByKeywords(Date LastcohesionDate, String riskAssessmentType,
			String placeSituation, String name, String idNo, Integer gridId) {
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
		List<EmancipistDto> list = emancipistDao.queryEmancipistListByKeywords(LastcohesionDate, riskAssessmentType, placeSituation, name, idNo, residentBaseIds);
		List<Integer> residentIds = new ArrayList<Integer>();

		for (EmancipistDto dto : list) {
			System.out.println(dto.getResidentBaseId());
			residentIds.add(dto.getResidentBaseId());
		}

		Map<Integer, List<ResidentOfHouseInfo>> houseInfos = houseManageService.getResidentOfHouseInfos(residentIds);

		for (EmancipistDto dto : list) {
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
}
