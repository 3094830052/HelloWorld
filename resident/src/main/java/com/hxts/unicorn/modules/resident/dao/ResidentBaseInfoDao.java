package com.hxts.unicorn.modules.resident.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hxts.unicorn.modules.resident.dto.RenterInfoDto;
import com.hxts.unicorn.modules.resident.dto.ResidentBaseInfoDto;
import com.hxts.unicorn.modules.resident.model.ResidentBase;
import com.hxts.unicorn.modules.resident.model.ResidentLabels;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.ResidentBaseInfo;

@Repository
public interface ResidentBaseInfoDao {
	int deleteByPrimaryKey(Integer residentBaseId);

	int insert(ResidentBase record);

	int insertSelective(ResidentBase record);

	ResidentBaseInfo selectByPrimaryKey(Integer residentBaseId);

	int updateByPrimaryKeySelective(ResidentBase record);

	int updateByPrimaryKey(ResidentBase record);

	ResidentBaseInfo selectByIdNo(@Param("idNo") String idNo,@Param("residentBaseIds") List<Integer> residentBaseIds);

	/**
	 * 
	 * <通过人口基础信息id或者身份证号码查询人口的基础信息id和所有的标签id>
	 * <如果人口基础信息id为null，表示没有对应的人口基础信息和标签信息；如果对应的标签id为null，表示该人口无此标签信息>
	 * 
	 * @param residentBaseId
	 *            人口基础信息id
	 * @param idNo
	 *            身份证号码或者证件号码
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	ResidentLabels getAllLabelId(@Param("residentBaseId") Integer residentBaseId, @Param("idNo") String idNo);

	List<ResidentBaseInfoDto> getResidentInfoForList(@Param("name") String name, @Param("idNo") String idNo,
			@Param("integrity") BigDecimal integrity, @Param("residentBaseIds") List<Integer> residentBaseIds);

	/**
	 * 
	 * <根据关键词查询出租屋承租人列表> <功能详细描述>
	 * 
	 * @param name
	 * @param idNo
	 * @param integrity
	 * @param ethnicity
	 * @param residentBaseIds
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<RenterInfoDto> getRenterInfoList(@Param("name") String name, @Param("idNo") String idNo,
			@Param("integrity") BigDecimal integrity, @Param("ethnicity") String ethnicity,
			@Param("residentBaseIds") List<Integer> residentBaseIds);

	List<ResidentBaseInfo> getResidentInfoByKeywords(@Param("name") String name, @Param("idNo") String idNo,
			@Param("contact") String contact,@Param("residentBaseIds") List<Integer> residentBaseIds);

	/**
	 * 
	 * <实有人口页面统计，按居民属性> <根据居民属性：户籍、流动、境外分类统计>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<StatsResult> getTagStats(@Param("residentBaseIds") List<Integer> residentBaseIds);

	/**
	 * 
	 * <实有人口页面统计，按学历> <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<StatsResult> getDegreeStats(@Param("residentBaseIds") List<Integer> residentBaseIds);

	/**
	 * 
	 * <实有人口页面统计，按年龄分布> <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<StatsResult> getAgeStats(@Param("residentBaseIds") List<Integer> residentBaseIds);

	/**
	 * 
	 * <从房屋模块获取所有的租住人的id，再统计其年龄分布> <功能详细描述>
	 * 
	 * @param residentBaseIds
	 *            人口id列表
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<StatsResult> getRenterAgeStats(@Param("residentBaseIds") List<Integer> residentBaseIds);

	/**
	 * 
	 * <根据给定的id列表，统计里面的标签数量> <功能详细描述>
	 * 
	 * @param residentBaseIds
	 * @param isLeftBehind
	 * @param isSuballow
	 * @param isLivealone
	 * @param isDisabled
	 * @param isTeenager
	 * @param isDrug
	 * @param isEmancipist
	 * @param isIllegalPertitioner
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<StatsResult> getLabelCountByIds(@Param("residentBaseIds") List<Integer> residentBaseIds,
			@Param("isLeftBehind") boolean isLeftBehind, @Param("isSuballow") boolean isSuballow,
			@Param("isLivealone") boolean isLivealone, @Param("isDisabled") boolean isDisabled,
			@Param("isTeenager") boolean isTeenager, @Param("isDrug") boolean isDrug,
			@Param("isEmancipist") boolean isEmancipist, @Param("isIllegalPertitioner") boolean isIllegalPertitioner);

	List<ResidentBaseInfo> getResidentBaseInfo(@Param("residentBaseIds") List<Integer> residentBaseIds,
			@Param("idNo") String idNo, @Param("name") String name);

}