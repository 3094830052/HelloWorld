package com.hxts.unicorn.modules.resident.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.DisabledPeopleDao;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.model.DisabledPeople;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.service.ISrvDisabledPeople;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.DisabledPeopleInfo;

@Service
public class SrvDisabledPeople implements ISrvDisabledPeople {
	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private DisabledPeopleDao disabledPeopleDao;
	@Override
	public int save(DisabledPeopleInfo disabledPeopleInfo) {
		// TODO Auto-generated method stub
		Integer disabledPeopleId=disabledPeopleInfo.getDisabledPeopleId();
		Integer residentBaseId = disabledPeopleInfo.getResidentBaseId();
		if(residentBaseId == null){
			throw new AppModuleErrorException("残障人士信息添加异常！");    
		}
		ResidentLabels labels = residentBaseInfoDao.getAllLabelId(residentBaseId, null);
		if(labels != null &&labels.getDisabledPeopleId() != null){
			disabledPeopleId = labels.getDisabledPeopleId();
			disabledPeopleInfo.setDisabledPeopleId(disabledPeopleId);;
		}
		if(disabledPeopleId!=null){
			return disabledPeopleDao.updateByPrimaryKeySelective(toEntity(disabledPeopleInfo));
		}else{
			int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
			disabledPeopleInfo.setCreateUserId(userId);
			return disabledPeopleDao.insert(toEntity(disabledPeopleInfo));
		}
	}

	@Override
	public int deleteByDisabledPeopleId(Integer disabledPeopleId) {
		// TODO Auto-generated method stub
		return disabledPeopleDao.deleteByPrimaryKey(disabledPeopleId);
	}

	@Override
	public DisabledPeopleInfo selectByDisabledPeopleId(Integer disabledPeopleId) {
		// TODO Auto-generated method stub
		return disabledPeopleDao.selectByPrimaryKey(disabledPeopleId);
	}
	
	public DisabledPeople toEntity(DisabledPeopleInfo info) {
		// TODO Auto-generated method stub
		DisabledPeople entity = new DisabledPeople();
		entity.setDisabledPeopleId(info.getDisabledPeopleId());
		entity.setResidentBaseId(info.getResidentBaseId());
		entity.setHealthCondition(info.getHealthCondition());
		entity.setDisabledCategory(info.getDisabledCategory());
		entity.setDisabledDegree(info.getDisabledDegree());
		entity.setDisabledNo(info.getDisabledNo());
		entity.setDisabledReason(info.getDisabledReason());
		entity.setDisabledParts(info.getDisabledParts());
		entity.setGuarderName(info.getGuarderName());
		entity.setGuarderIdNo(info.getGuarderIdNo());
		entity.setGuarderContact(info.getGuarderContact());
		entity.setFamilyDisabled(info.getFamilyDisabled());
		entity.setFamilyMonthIncome(info.getFamilyMonthIncome());
		entity.setIsSubsistAllowance(info.getIsSubsistAllowance());
		entity.setEmploymentStatus(info.getEmploymentStatus());
		entity.setSocialSecurityStatus(info.getSocialSecurityStatus());
		entity.setCreateUserId(info.getCreateUserId());
		return entity;
	}
}
