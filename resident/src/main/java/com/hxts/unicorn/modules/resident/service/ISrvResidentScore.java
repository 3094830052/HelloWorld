package com.hxts.unicorn.modules.resident.service;

import com.hxts.unicorn.modules.resident.model.ResidentScoreRecord;
import com.hxts.unicorn.platform.interfaces.biz.ResidentScoreInfo;

public interface ISrvResidentScore {

	/**
	 * 
	 * <保存新增或编辑人口积分信息>
	 * @param ResidentScoreInfo 人口积分信息 re 积分记录详情
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(ResidentScoreInfo residentscoreinfo,ResidentScoreRecord re);	
	/**
	 * 
	 * <根据积分信息ID删除人员积分信息>
	 * @param ResidentScoreId 积分信息ID
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByResidentScoreId(Integer ResidentScoreId);
	/**
	 * 
	 * <根据积分信息ID查找人员积分信息>
	 * @param ResidentScoreId 积分信息ID
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	ResidentScoreInfo selectByResidentScoreId(Integer ResidentScoreId);
	/**
	 * 
	 * <根据人口ID查找人员积分信息>
	 * @param ResidentBaseId 人口ID
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	ResidentScoreInfo selectByResidentBaseId(Integer ResidentBaseId);
	/**
	 * 
	 * <根据OpenId查找人员积分信息>
	 * @param openId 微信ID
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	ResidentScoreInfo selectByOpenId(String openId);
}
