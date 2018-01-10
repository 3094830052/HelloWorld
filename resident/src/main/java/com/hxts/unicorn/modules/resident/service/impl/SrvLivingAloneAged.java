package com.hxts.unicorn.modules.resident.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dao.LivingAloneAgedDao;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.model.LivingAloneAged;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.service.ISrvLivingAloneAged;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.LivingAloneAgedInfo;

@Service
public class SrvLivingAloneAged implements ISrvLivingAloneAged {
	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private LivingAloneAgedDao livingAloneAgedDao;
	@Override
	public int save(LivingAloneAgedInfo livingAloneAgedInfo) {
		// TODO Auto-generated method stub
		Integer livingAloneId=livingAloneAgedInfo.getLivingAloneId();
		Integer residentBaseId = livingAloneAgedInfo.getResidentBaseId();
		if(residentBaseId == null){
			throw new AppModuleErrorException("独居老人信息添加异常！");    
		}
		ResidentLabels labels = residentBaseInfoDao.getAllLabelId(residentBaseId, null);
		if(labels != null &&labels.getLivingAloneId() != null){
			livingAloneId = labels.getLivingAloneId();
			livingAloneAgedInfo.setLivingAloneId(livingAloneId);
		}
		if(livingAloneId!=null){
			return livingAloneAgedDao.updateByPrimaryKeySelective(toEntity(livingAloneAgedInfo));
		}else{
			int userId = ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
			livingAloneAgedInfo.setCreateUserId(userId);
			return livingAloneAgedDao.insert(toEntity(livingAloneAgedInfo));
		}
	}

	@Override
	public int deleteByLivingAloneAgedId(Integer livingAloneAgedId) {
		// TODO Auto-generated method stub
		return livingAloneAgedDao.deleteByPrimaryKey(livingAloneAgedId);
	}

	@Override
	public LivingAloneAgedInfo selectByLivingAloneAgedId(Integer livingAloneAgedId) {
		// TODO Auto-generated method stub
		return livingAloneAgedDao.selectByPrimaryKey(livingAloneAgedId);
	}
	
	public LivingAloneAged toEntity(LivingAloneAgedInfo info) {
		// TODO Auto-generated method stub
		LivingAloneAged entity = new LivingAloneAged();
		entity.setLivingAloneId(info.getLivingAloneId());
		entity.setResidentBaseId(info.getResidentBaseId());
		entity.setHealthCondition(info.getHealthCondition());
		entity.setMentalState(info.getMentalState());
		entity.setHasChildSupport(info.getHasChildSupport());
		entity.setLivingCondition(info.getLivingCondition());
		entity.setFinancialResource(info.getFinancialResource());
		entity.setLivingAloneReason(info.getLivingAloneReason());
		entity.setCreateUserId(info.getCreateUserId());
		return entity;
	}
}
