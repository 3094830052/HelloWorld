package com.hxts.unicorn.modules.resident.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.modules.resident.dto.ResidentTempDto;
import com.hxts.unicorn.modules.resident.model.ResidentScore;
import com.hxts.unicorn.platform.interfaces.biz.ResidentTemp;

public interface ISrvResidentTempInfo {
	/**
	 * 
	 * <根据人口基础信息id查询人口基础信息>
	 * 
	 * @param residentBaseId
	 *            人口基础信息id
	 * @return 人口基础信息
	 * @see [类、类#方法、类#成员]
	 */
	ResidentTemp selectByResidentBaseId(Integer residentBaseId);

	/**
	 * 
	 * <微信新增人口基础信息>
	 * 
	 * @param ResidentTemp
	 *            人口基础信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int add(ResidentTemp ResidentTemp, String openId);

	/**
	 * 
	 * <微信新增房屋信息>
	 * 
	 * @param 房屋地址
	 *            房屋证件代码 证件号码
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int addhouse(Integer unitNumber, Integer floorNumber, Integer houseNumber, String street, String community,
			String buildingName, String idCode, String idNumber, String houseType, Long residentBaseId);

	/**
	 * 
	 * <微信绑定人口基础信息>
	 * 
	 * @param residentBase
	 *            人口基础信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int binding(String name, String idNo, String contact, Integer verificationCode, String openId);

	/**
	 * 
	 * <根据人口基础信息id删除人口基础信息>
	 * 
	 * @param residentBaseId
	 *            人口基础信息id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByResidentId(Integer residentBaseId);

	/**
	 * 
	 * <根据身份号码和绑定或新增类型查询人口基础信息>
	 * 
	 * @param residentBaseId
	 *            人口基础信息id type 用户认证信息或提交信息
	 * @return 人口基础信息
	 * @see [类、类#方法、类#成员]
	 */
	ResidentTemp selectByIdNo(String idNo, Integer type);

	/**
	 * <根据网格员确认的状态查询人口基础信息>
	 * 
	 * @param status
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	List<ResidentTempDto> getResidentTemp(Integer status,String openId);

	/**
	 * <网格员选择保存到正式表>
	 * 
	 * @param ResidentTemp
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(ResidentTemp ResidentTemp);

	/**
	 * <网格员选择驳回用户提交信息>
	 * 
	 * @param ResidentTemp
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int reject(ResidentTemp ResidentTemp);

	/**
	 * <网格员选择修改用户提交信息>
	 * 
	 * @param ResidentTemp
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int update(ResidentTemp ResidentTemp);

	/**
	 * <用户提交信息所获得的积分>
	 * 
	 * @param
	 * @return
	 */
	int getPoints();
	
	/**
	 * <网格员审核用户认证>
	 * 
	 * @param residentScore
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int examine(ResidentScore residentScore,int status);
}
