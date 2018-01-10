package com.hxts.unicorn.modules.resident.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.modules.resident.dao.ResidentScoreDao;
import com.hxts.unicorn.modules.resident.dao.ResidentScoreRecordDao;
import com.hxts.unicorn.modules.resident.model.ResidentScore;
import com.hxts.unicorn.modules.resident.model.ResidentScoreRecord;
import com.hxts.unicorn.modules.resident.service.ISrvResidentScore;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.ResidentScoreInfo;

@Service
public class SrvResidentScore implements ISrvResidentScore {

	@Autowired
	private ResidentScoreDao residentScoreDao;
	@Autowired
	private ResidentScoreRecordDao residentScoreRecordDao;

	@Override
	public int save(ResidentScoreInfo residentscoreinfo, ResidentScoreRecord re) {
		// TODO Auto-generated method stub
		Integer residentBaseId = residentscoreinfo.getResidentBaseId();
		String openId = residentscoreinfo.getOpenId();
		if (residentBaseId == null) {
			throw new AppModuleErrorException("积分信息添加异常！");
		}
		if (openId == null) {
			throw new AppModuleErrorException("积分信息添加异常！");
		}
		ResidentScoreInfo res = residentScoreDao.selectByResidentBaseId(residentBaseId);
		res.setScore(residentscoreinfo.getScore());
		return residentScoreDao.updateByPrimaryKeySelective(toEntity(res)) & residentScoreRecordDao.insert(re);

	}

	@Override
	public int deleteByResidentScoreId(Integer ResidentScoreId) {
		// TODO Auto-generated method stub
		return residentScoreDao.deleteByPrimaryKey(ResidentScoreId);
	}

	@Override
	public ResidentScoreInfo selectByResidentScoreId(Integer ResidentScoreId) {
		// TODO Auto-generated method stub
		return residentScoreDao.selectByPrimaryKey(ResidentScoreId);
	}

	public ResidentScore toEntity(ResidentScoreInfo residentScoreInfo) {
		ResidentScore residentScore = new ResidentScore();
		residentScore.setOpenId(residentScoreInfo.getOpenId());
		residentScore.setResidentBaseId(residentScoreInfo.getResidentBaseId());
		residentScore.setResidentScoreId(residentScoreInfo.getResidentScoreId());
		residentScore.setScore(residentScoreInfo.getScore());
		residentScore.setIdNo(residentScoreInfo.getIdNo());
		residentScore.setName(residentScoreInfo.getName());
		residentScore.setContact(residentScoreInfo.getContact());
		return residentScore;
	}

	@Override
	public ResidentScoreInfo selectByResidentBaseId(Integer ResidentBaseId) {
		// TODO Auto-generated method stub
		return residentScoreDao.selectByResidentBaseId(ResidentBaseId);
	}

	@Override
	public ResidentScoreInfo selectByOpenId(String openId) {
		// TODO Auto-generated method stub
		return residentScoreDao.selectByOpenId(openId);
	}

}
