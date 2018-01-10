package com.hxts.unicorn.modules.resident.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.modules.resident.dao.BuildingDao;
import com.hxts.unicorn.modules.resident.dao.FloatingResidentDao;
import com.hxts.unicorn.modules.resident.dao.ForeignerInfoDao;
import com.hxts.unicorn.modules.resident.dao.HouseDao;
import com.hxts.unicorn.modules.resident.dao.HouseholdRegisteredResidentDao;
import com.hxts.unicorn.modules.resident.dao.ResidentBaseInfoDao;
import com.hxts.unicorn.modules.resident.dao.ResidentInfoTempDao;
import com.hxts.unicorn.modules.resident.dao.ResidentOfHouseDao;
import com.hxts.unicorn.modules.resident.dao.ResidentScoreDao;
import com.hxts.unicorn.modules.resident.dao.ResidentScoreRecordDao;
import com.hxts.unicorn.modules.resident.dto.ResidentTempDto;
import com.hxts.unicorn.modules.resident.model.FloatingResident;
import com.hxts.unicorn.modules.resident.model.ForeignerInfo;
import com.hxts.unicorn.modules.resident.model.HouseholdRegisteredResident;
import com.hxts.unicorn.modules.resident.model.ResidentBase;
import com.hxts.unicorn.modules.resident.model.ResidentScore;
import com.hxts.unicorn.modules.resident.model.ResidentScoreRecord;
import com.hxts.unicorn.modules.resident.service.ISrvResidentTempInfo;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.BuildingInfo;
import com.hxts.unicorn.platform.interfaces.biz.FloatingResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.HouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentBaseInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentOfHouseInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentScoreInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentTemp;


@Service
public class SrvResidentTempInfo implements ISrvResidentTempInfo {

	@Autowired
	private ResidentInfoTempDao residentInfoTempDao;
	@Autowired
	private ResidentBaseInfoDao residentBaseInfoDao;
	@Autowired
	private ForeignerInfoDao foreignerInfoDao;
	@Autowired
	private HouseholdRegisteredResidentDao householdRegisteredResidentDao;
	@Autowired
	private ResidentOfHouseDao residentOfHouseDao;
	@Autowired
	private HouseDao houseDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private FloatingResidentDao floatingResidentDao;
	@Autowired
	private ResidentScoreDao residentScoreDao;
	@Autowired
	private ResidentScoreRecordDao residnetScoreRecordDao;

	@Override
	public ResidentTemp selectByResidentBaseId(Integer residentBaseId) {
		// TODO Auto-generated method stub
		return residentInfoTempDao.selectByPrimaryKey(residentBaseId);
	}

	@Override
	public int add(ResidentTemp ResidentTemp, String openId) {
		// TODO Auto-generated method stub
		String idNo = ResidentTemp.getIdNo();
		// System.out.println(residentScoreDao.selectByOpenId(openId)
		// .getResidentBaseId().equals(residentBaseInfoDao.selectByIdNo(idNo,
		// null).getResidentBaseId()));
		if (residentInfoTempDao.selectByIdNo(idNo, 0) != null) {
			throw new AppModuleErrorException("身份证号存在！");
		}
		if (residentBaseInfoDao.selectByIdNo(idNo, null) != null && !residentScoreDao.selectByOpenId(openId)
				.getResidentBaseId().equals(residentBaseInfoDao.selectByIdNo(idNo, null).getResidentBaseId())) {
			throw new AppModuleErrorException("身份证号存在a！");
		}
		Date date=new Date();
		ResidentTemp.setDate(date);
		ResidentTemp.setType(0);
		ResidentTemp.setStatus(0);
		int a = residentInfoTempDao.insert(ResidentTemp);
		ResidentTemp.setResidentBaseId(residentInfoTempDao.selectByIdNo(idNo, 0).getResidentBaseId());
		return a;
	}

	@Override
	public int binding(String name, String idNo, String contact, Integer verificationCode, String openId) {
		// TODO Auto-generated method stub
		// String openId = ResidentTemp.getOpenId();
		if (idNo == null) {
			throw new AppModuleErrorException("请输入身份证号！");
		}
		if (name == null) {
			throw new AppModuleErrorException("请输入姓名！");
		}
		if (contact == null) {
			throw new AppModuleErrorException("请输入联系方式！");
		}
		if (verificationCode == null) {
			throw new AppModuleErrorException("请输入验证码！");
		}
		if (residentInfoTempDao.selectByIdNo(idNo, 0) != null || residentBaseInfoDao.selectByIdNo(idNo, null) != null) {
			throw new AppModuleErrorException("身份证号存在,此信息已绑定！");
		} else {
			if (residentScoreDao.selectByOpenId(openId) != null
					&& residentScoreDao.selectByOpenId(openId).getStatus() == 2) {
				ResidentScoreInfo r = residentScoreDao.selectByOpenId(openId);
				ResidentTemp ResidentTemp = residentInfoTempDao.selectByOpenId(openId);
				if (ResidentTemp != null) {
					ResidentTemp.setIdNo(idNo);
					ResidentTemp.setName(name);
					ResidentTemp.setContact(contact);
					ResidentTemp.setType(1);
					ResidentTemp.setStatus(0);
					ResidentTemp.setOpenId(openId);
					residentInfoTempDao.updateByPrimaryKeySelective(ResidentTemp);
					r.setIdNo(idNo);
					r.setName(name);
					r.setContact(contact);
					r.setStatus(1);
					residentScoreDao.updateByPrimaryKeySelective(toS(r));
					return residentInfoTempDao.selectByIdNo(idNo, 1).getResidentBaseId();
				}

			}
			if (residentScoreDao.selectByOpenId(openId) != null
					&& residentScoreDao.selectByOpenId(openId).getStatus() == 1)
				throw new AppModuleErrorException("此微信账号信息已绑定！");
			else {
				ResidentTemp ResidentTemp = new ResidentTemp();
				ResidentTemp.setIdNo(idNo);
				ResidentTemp.setName(name);
				ResidentTemp.setContact(contact);
				ResidentTemp.setType(1);
				ResidentTemp.setStatus(0);
				ResidentTemp.setOpenId(openId);
				ResidentBase residentBase = toR(ResidentTemp);
				residentBase.setResidentBaseId(null);
				residentInfoTempDao.insert(ResidentTemp);
				residentBaseInfoDao.insert(residentBase);
				ResidentScore r = new ResidentScore();
				r.setResidentBaseId(residentBaseInfoDao.selectByIdNo(idNo, null).getResidentBaseId());
				r.setOpenId(openId);
				r.setIdNo(idNo);
				r.setName(name);
				r.setContact(contact);
				r.setStatus(1);
				residentScoreDao.insert(r);
				return residentBaseInfoDao.selectByIdNo(idNo, null).getResidentBaseId();
			}
		}
	}

	@Override
	public int deleteByResidentId(Integer residentBaseId) {
		// TODO Auto-generated method stub
		return residentInfoTempDao.deleteByPrimaryKey(residentBaseId);
	}

	@Override
	public ResidentTemp selectByIdNo(String idNo, Integer type) {
		// TODO Auto-generated method stub
		return residentInfoTempDao.selectByIdNo(idNo, type);
	}

	@Override
	public List<ResidentTempDto> getResidentTemp(Integer status,String openId) {
		// TODO Auto-generated method stub
		List<ResidentTemp> l = new ArrayList<ResidentTemp>();
		if (status == null) {
			l = residentInfoTempDao.getall(openId);
		} else
			l = residentInfoTempDao.getResidentTemp(status,openId);
		if (l == null) {
			return null;
		}
		List<ResidentTempDto> li = new ArrayList<ResidentTempDto>(l.size());
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getStatus() == 1) {
				ResidentTempDto resident = new ResidentTempDto();
				List<ResidentScoreRecord> re = residnetScoreRecordDao
						.selectByResidentBaseId(l.get(i).getResidentBaseId());
				resident.setName(l.get(i).getName());
				resident.setStatus(l.get(i).getStatus());
				resident.setDate(l.get(i).getDate());
				resident.setResidentBaseId(l.get(i).getResidentBaseId());
				int score=0;
				for(int j=0;j<re.size();j++){
					score+=re.get(j).getScore();
				}
				resident.setScore(score);
				li.add(i, resident);
			} else {
				ResidentTempDto resident = new ResidentTempDto();
				resident.setName(l.get(i).getName());
				resident.setStatus(l.get(i).getStatus());
				resident.setDate(l.get(i).getDate());
				resident.setResidentBaseId(l.get(i).getResidentBaseId());
				resident.setScore(0);
				li.add(i, resident);
			}

		}
		return li;
	}

	@Override
	public int save(ResidentTemp ResidentTemp) {
		// TODO Auto-generated method stub
		int r = 1;
		int f = 1;
		int h = 1;
		int fl = 1;
		if (ResidentTemp != null) {
			ResidentTemp.setStatus(1);
			ResidentBase residentBase = toR(ResidentTemp);
			residentInfoTempDao.updateByPrimaryKeySelective(ResidentTemp);
			if (residentBaseInfoDao.selectByIdNo(ResidentTemp.getIdNo(), null) != null) {
				residentBase.setResidentBaseId(
						residentBaseInfoDao.selectByIdNo(ResidentTemp.getIdNo(), null).getResidentBaseId());
				r = residentBaseInfoDao.updateByPrimaryKeySelective(residentBase);
			} else {
				r = residentBaseInfoDao.insert(residentBase);
			}
			ResidentTemp.setResidentBaseId(
					residentBaseInfoDao.selectByIdNo(ResidentTemp.getIdNo(), null).getResidentBaseId());
			if (ResidentTemp.getNationality() != null && !ResidentTemp.getNationality().equals("156")) {

				f = foreignerInfoDao.insert(toF(ResidentTemp));
			}
			if (ResidentTemp.getHouseholderRelationship() != null) {
				h = householdRegisteredResidentDao.insert(toH(ResidentTemp));
			}
			if (ResidentTemp.getInflowReason() != null) {
				fl = floatingResidentDao.insert(toFl(ResidentTemp));
			}
			if (ResidentTemp.getHouseId() != null) {
				HouseInfo house = houseDao.queryBaseHouseById(ResidentTemp.getHouseId());
				house.setIdCode(ResidentTemp.getIdCode());
				house.setIdNumber(ResidentTemp.getIdNumber());
				house.setHouseType(ResidentTemp.getHouseType());
				houseDao.modifyHouse(house);
				residentOfHouseDao.updateResidentOfHouse((toRe(ResidentTemp)));
			}
		}
		return r & f & h & fl;
	}

	@Override
	public int reject(ResidentTemp ResidentTemp) {
		// TODO Auto-generated method stub
		ResidentTemp.setStatus(2);
		return residentInfoTempDao.updateByPrimaryKeySelective(ResidentTemp);
	}

	@Override
	public int update(ResidentTemp ResidentTemp) {
		// TODO Auto-generated method stub
		return residentInfoTempDao.updateByPrimaryKeySelective(ResidentTemp);
	}

	@Override
	public int getPoints() {
		// TODO Auto-generated method stub
		return 100;
	}

	public ResidentBase toR(ResidentTemp r) {
		ResidentBase residentBase = new ResidentBase();
		residentBase.setBirthDate(r.getBirthDate());
		residentBase.setContact(r.getContact());
		residentBase.setCreateUserId(r.getCreateUserId());
		residentBase.setCurrentResidence(r.getCurrentResidence());
		residentBase.setCurrentResidenceAddress(r.getCurrentResidenceAddress());
		residentBase.setEducationalDegree(r.getEducationalDegree());
		residentBase.setEmployer(r.getEmployer());
		residentBase.setEthnicity(r.getEthnicity());
		residentBase.setFormerName(r.getFormerName());
		residentBase.setIdNo(r.getIdNo());
		residentBase.setMaritalStatus(r.getMaritalStatus());
		residentBase.setName(r.getName());
		residentBase.setNativePlace(r.getNativePlace());
		residentBase.setOccupation(r.getOccupation());
		residentBase.setOccupationCategory(r.getOccupationCategory());
		residentBase.setPicture(r.getPicture());
		residentBase.setPoliticalStatus(r.getPoliticalStatus());
		residentBase.setRegisteredResidence(r.getRegisteredResidence());
		residentBase.setRegisteredResidenceAddress(r.getRegisteredResidenceAddress());
		residentBase.setResidentBaseId(r.getResidentBaseId());
		residentBase.setReligiousBelief(r.getReligiousBelief());
		residentBase.setSex(r.getSex());
		return residentBase;

	}

	public HouseholdRegisteredResident toH(ResidentTemp r) {
		HouseholdRegisteredResident h = new HouseholdRegisteredResident();
		h.setResidentBaseId(r.getResidentBaseId());
		h.setHouseholderRelationship(r.getHouseholderRelationship());
		h.setHouseholderName(r.getHouseholderName());
		h.setHouseholderIdcardNo(r.getHouseholderIdcardNo());
		h.setHouseholderContact(r.getHouseholderContact());
		return h;
	}

	public ForeignerInfo toF(ResidentTemp r) {
		ForeignerInfo f = new ForeignerInfo();
		f.setResidentBaseId(r.getResidentBaseId());
		f.setNationality(r.getNationality());
		return f;
	}

	public ResidentOfHouseInfo toRe(ResidentTemp r) {
		ResidentOfHouseInfo re = new ResidentOfHouseInfo();
		re.setResidentBaseId(r.getResidentBaseId().longValue());
		re.setHouseId(r.getHouseId());
		return re;
	}

	public FloatingResident toFl(ResidentTemp r) {
		FloatingResident fl = new FloatingResident();
		fl.setResidentBaseId(r.getResidentBaseId());
		fl.setInflowReason(r.getInflowReason());
		fl.setResidenceType(r.getResidenceType());
		fl.setCertificateExpireDate(r.getCertificateExpireDate());
		fl.setCertificateHandlingType(r.getCertificateHandlingType());
		fl.setCertificateNo(r.getCertificateNo());
		fl.setRegisterDate(r.getRegisterDate());
		return fl;
	}

	public ResidentScore toS(ResidentScoreInfo r) {
		ResidentScore s = new ResidentScore();
		s.setContact(r.getContact());
		s.setIdNo(r.getIdNo());
		s.setName(r.getName());
		s.setOpenId(r.getOpenId());
		s.setResidentBaseId(r.getResidentBaseId());
		s.setResidentScoreId(r.getResidentScoreId());
		s.setScore(r.getScore());
		s.setStatus(r.getStatus());
		return s;
	}

	@Override
	public int addhouse(Integer unitNumber, Integer floorNumber, Integer houseNumber, String street, String community,
			String buildingName, String idCode, String idNumber, String houseType, Long residentBaseId) {
		// TODO Auto-generated method stub
		if (residentBaseId == null) {
			throw new AppModuleErrorException("人房信息添加异常！");
		} else {
			ResidentTemp residentTemp = residentInfoTempDao.selectByPrimaryKey(residentBaseId.intValue());
			List<BuildingInfo> build = buildingDao.queryBaseBuildingsByNameAndAddress(street, community, buildingName,
					null);
			Long buildingId = build.get(0).getBuildingId();
			List<HouseInfo> houseInfo = houseDao.queryBaseHouses(buildingId, unitNumber, floorNumber, houseNumber);
			residentTemp.setResidentBaseId(residentBaseId.intValue());
			residentTemp.setHouseId(houseInfo.get(0).getHouseId());
			residentTemp.setStatus(0);
			residentTemp.setBuildingId(buildingId);
			residentTemp.setUnitNumber(unitNumber);
			residentTemp.setFloorNumber(floorNumber);
			residentTemp.setHouseNumber(houseNumber);
			residentTemp.setIdNumber(idNumber);
			residentTemp.setIdCode(idCode);
			residentTemp.setHouseType(houseType);
			return residentInfoTempDao.updateByPrimaryKeySelective(residentTemp);
		}
	}

	@Override
	public int examine(ResidentScore residentScore, int status) {
		// TODO Auto-generated method stub
		if (status == 1) {
			ResidentTemp residentTemp = residentInfoTempDao.selectByIdNo(residentScore.getIdNo(), 1);
			residentTemp.setStatus(1);
			ResidentBase residentBase = toR(residentTemp);
			residentInfoTempDao.updateByPrimaryKeySelective(residentTemp);
			if (residentBaseInfoDao.selectByIdNo(residentTemp.getIdNo(), null) != null) {
				residentBase.setResidentBaseId(
						residentBaseInfoDao.selectByIdNo(residentTemp.getIdNo(), null).getResidentBaseId());
				residentBaseInfoDao.updateByPrimaryKeySelective(residentBase);
			} else {
				residentBaseInfoDao.insert(residentBase);
			}
			residentScore.setResidentBaseId(
					residentBaseInfoDao.selectByIdNo(residentScore.getIdNo(), null).getResidentBaseId());
			residentScore.setStatus(1);
			residentScoreDao.updateByPrimaryKeySelective(residentScore);
		} else if (status == 2) {
			residentScore.setStatus(2);
			residentScoreDao.updateByPrimaryKeySelective(residentScore);
		}
		return residentScore.getStatus();
	}

}
