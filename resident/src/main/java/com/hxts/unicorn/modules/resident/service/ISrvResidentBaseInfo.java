package com.hxts.unicorn.modules.resident.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxts.unicorn.modules.resident.dto.ResidentBaseInfoDto;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.ForeignerResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentBaseInfo;

public interface ISrvResidentBaseInfo {
	/**
	 * 
	 * <根据人口基础信息id查询人口基础信息>
	 * @param residentBaseId 人口基础信息id
	 * @return 人口基础信息
	 * @see [类、类#方法、类#成员]
	 */
	ResidentBaseInfo selectByResidentBaseId(Integer residentBaseId);
	/**
	 * 
	 * <保存新增或编辑人口基础信息>
	 * @param residentBase 人口基础信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(ResidentBaseInfo residentBaseInfo);
	/**
	 * 
	 * <保存新增或编辑人口基础信息，此接口只用于境外人口，保存人口基础信息和计算信息完整度用>
	 * @param residentBaseInfo 人口基础信息
	 * @param foreignerResidentInfo 境外人口独有信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(ResidentBaseInfo residentBaseInfo, ForeignerResidentInfo foreignerResidentInfo);
	/**
	 * 
	 * <根据人口基础信息id删除人口基础信息>
	 * @param residentBaseId 人口基础信息id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByResidentId(Integer residentBaseId);
	/**
	 * 
	 * <根据身份号码查询人口基础信息>
	 * @param leftBehindId 留守人口id
	 * @return 人口基础信息
	 * @see [类、类#方法、类#成员]
	 */
	ResidentBaseInfo selectByIdNo(String idNo);
	/**
	 * 
	 * <根据关键字查询人口基础信息列表>
	 * <功能详细描述>
	 * @param gridId 网格id
	 * @param name 姓名
	 * @param idNo 身份证号码
	 * @param integrity 基础信息完整度
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<ResidentBaseInfoDto> getResidentInfoByKeywords(List<Integer> residentBaseIds, String name, String idNo,BigDecimal integrity);
	/**
     * 
     * <通过人口基础信息id或者身份证号码查询人口的基础信息id和所有的标签id>
     * <如果人口基础信息id为null，表示没有对应的人口基础信息和标签信息；如果对应的标签id为null，表示该人口无此标签信息>
     * @param residentBaseId 人口基础信息id
     * @param idNo 身份证号码或者证件号码
     * @return
     * @see [类、类#方法、类#成员]
     */
    ResidentLabels getAllLabelId(@Param("residentBaseId")Integer residentBaseId,@Param("idNo")String idNo);
	/**
     * 
     * <实有人口页面统计，按居民属性>
     * <根据居民属性：户籍、流动、境外分类统计>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getTagStats();
    /**
     * 
     * <实有人口页面统计，按学历>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getDegreeStats();
    /**
     * 
     * <实有人口页面统计，按年龄分布>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getAgeStats();
    /**
     * 
     * <根据人口ID，姓名身份证号查询人口基础信息>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */ 
    List<ResidentBaseInfo> getResidentBaseInfo(List<Integer> residentBaseIds,String idNo, String name);
    
    List<ResidentBaseInfoDto> getPeopleInfoList(String type);
}
