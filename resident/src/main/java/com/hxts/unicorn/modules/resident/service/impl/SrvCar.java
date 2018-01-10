package com.hxts.unicorn.modules.resident.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxts.unicorn.modules.resident.dao.CarInfoDao;
import com.hxts.unicorn.modules.resident.model.Car;
import com.hxts.unicorn.modules.resident.service.ISrvCar;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.CarInfo;
@Service
public class SrvCar implements ISrvCar{

	@Autowired
	private CarInfoDao carInfoDao;
	@Override
	public int save(CarInfo carInfo) {
		// TODO Auto-generated method stub
		Integer carId=carInfo.getCarId();
		Integer residentBaseId=carInfo.getResidentBaseId();
		if (residentBaseId == null) {
			throw new AppModuleErrorException("人车信息添加异常！");
		}
		if(carInfoDao.selectByResidentBaseId(residentBaseId)!=null){
			return carInfoDao.updateByPrimaryKeySelective(toEntity(carInfo));
		}
		else {
			return carInfoDao.insert(toEntity(carInfo));
		}

	}

	@Override
	public int deleteByCarInfo(Integer CarInfoId) {
		// TODO Auto-generated method stub
		return carInfoDao.deleteByPrimaryKey(CarInfoId);
	}

	@Override
	public CarInfo selectByCarInfoId(Integer CarInfoId) {
		// TODO Auto-generated method stub
		return carInfoDao.selectByPrimaryKey(CarInfoId);
	}

	@Override
	public List<CarInfo> getcarInfo(Integer residentBaseId) {
		// TODO Auto-generated method stub
		if(residentBaseId==null){
			return carInfoDao.getall();
		}
		return carInfoDao.getcarInfo(residentBaseId);
	}
	
	public Car toEntity(CarInfo carInfo){
		Car c=new Car();
		c.setCarColor(carInfo.getCarColor());
		c.setCarId(carInfo.getCarId());
		c.setCarNo(carInfo.getCarNo());
		c.setCarUsage(carInfo.getCarUsage());
		c.setResidentBaseId(carInfo.getResidentBaseId());
		return c;
	}

}
