package com.hxts.unicorn.modules.resident.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hxts.unicorn.modules.resident.dao.ResidentScoreDao;
import com.hxts.unicorn.modules.resident.dao.ResidentScoreRecordDao;
import com.hxts.unicorn.modules.resident.model.ResidentScore;
import com.hxts.unicorn.modules.resident.model.ResidentScoreRecord;
import com.hxts.unicorn.platform.interfaces.biz.ISrvScoreRecord;
import com.hxts.unicorn.platform.interfaces.biz.ResidentScoreInfo;

public class SrvScoreRecord implements ISrvScoreRecord {

	@Autowired
	private ResidentScoreDao residentScoreDao;
	@Autowired
	private ResidentScoreRecordDao residentScoreRecordDao;

	@Override
	public int addscore(String openId, int score, int residentBaseId,int recordId,
			String type, String scoreDetails) {
		// TODO Auto-generated method stub
		ResidentScoreInfo r = residentScoreDao.selectByOpenId(openId);
		r.setScore(r.getScore() + score);
		writeScoreRecord(residentBaseId, openId, score, recordId, type, scoreDetails);
		return residentScoreDao.updateByPrimaryKeySelective(toEntity(r));
	}

	@Override
	public int writeScoreRecord(int residentBaseId, String openId, int score, int recordId, String type,
			String scoreDetails) {
		// TODO Auto-generated method stub
		ResidentScoreRecord r = new ResidentScoreRecord();
		r.setOpenId(openId);
		r.setResidentBaseId(residentBaseId);
		r.setScore(score);
		r.setRecordId(recordId);
		r.setScoreDetails(scoreDetails);
		return residentScoreRecordDao.insert(r);
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
}
