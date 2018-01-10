package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import com.hxts.unicorn.modules.resident.model.CarRegister;



public interface CarRegisterMapper {
	   int deleteByPrimaryKey(Integer id);

	    int insert(CarRegister record);

	    int insertSelective(CarRegister record);

	    CarRegister selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(CarRegister record);

	    int updateByPrimaryKey(CarRegister record);
		
	    // 通过车牌号判断
		List<CarRegister> selectByCarNumber(String carNumber);
}
