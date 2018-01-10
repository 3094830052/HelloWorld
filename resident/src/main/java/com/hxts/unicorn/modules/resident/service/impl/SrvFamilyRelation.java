package com.hxts.unicorn.modules.resident.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.modules.resident.dao.FamilyRelationDao;
import com.hxts.unicorn.modules.resident.dto.FamilyRelationDto;
import com.hxts.unicorn.modules.resident.model.FamilyRelation;
import com.hxts.unicorn.modules.resident.service.ISrvFamilyRelation;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.FamilyRelationInfo;

@Service
public class SrvFamilyRelation implements ISrvFamilyRelation {
	@Autowired
	private FamilyRelationDao familyRelationDao;

	@Override
	public List<FamilyRelationDto> getFamilyRelation(Integer selfId) {
		// TODO Auto-generated method stub
		List<FamilyRelationDto> list=familyRelationDao.getFamilyRelationBySelfId(selfId);
		return list;
	}
	
	@Override
	public List<FamilyRelationDto> getFamilyRelations(String idNo){
		List<FamilyRelationDto> list=familyRelationDao.getFamilyRelationByIdNo(idNo);
		return list;
	}
	
	public void setFamilyRelation(FamilyRelationInfo familyRelationInfo) {
		Integer familyRelationId=familyRelationInfo.getFamilyRelationId();
		if(familyRelationId != null){
			FamilyRelation info = familyRelationDao.selectByPrimaryKey(familyRelationInfo.getFamilyRelationId());
			if(info!=null){
				familyRelationDao.updateByPrimaryKeySelective(toEntity(familyRelationInfo));
			}else{
				throw new AppModuleErrorException("家庭关系添加异常!");
			}
		}else{
			familyRelationDao.insert(toEntity(familyRelationInfo));
		}
	}
	
	public void deleteFamilyRelation(Integer familyRelationId) {
		// TODO Auto-generated method stub
		familyRelationDao.deleteByPrimaryKey(familyRelationId);
	}
	
	public FamilyRelation toEntity(FamilyRelationInfo dto){
		FamilyRelation entity = new FamilyRelation();
		entity.setFamilyRelationId(dto.getFamilyRelationId());
		entity.setSelfId(dto.getSelfId());
		entity.setOtherId(dto.getOtherId());
		entity.setRelation(dto.getRelation());
		return entity;
	}
}
