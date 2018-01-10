package com.hxts.unicorn.modules.resident.service;

import java.util.List;

import com.hxts.unicorn.modules.resident.dto.DrugDto;
import com.hxts.unicorn.modules.resident.dto.SpecialCrowdDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.DrugInfo;


public interface ISrvDrug {

	/**
	 * 
	 * <保存新增或编辑吸毒者信息>
	 * @param DrugInfo 吸毒者标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(DrugInfo DrugInfo);
	/**
	 * 
	 * <根据吸毒者id删除吸毒者信息>
	 * @param DrugInfoId 吸毒者id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByDrugInfoId(Integer DrugInfoId);
	/**
	 * 
	 * <根据吸毒者id查询吸毒者信息>
	 * @param DrugInfoId 吸毒者id
	 * @return 人口信息
	 * @see [类、类#方法、类#成员]
	 */
	DrugInfo selectByDrugInfoId(Integer DrugInfoId);
	/**
	 * 
	 * <根据关键字查询吸毒者信息列表>
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param gridId 所属网格
	 * @param datefirstdiscover 初次发现日期
	 * @param controlsituation 管控情况
	 * @param iscrime 有无犯罪史
	 * @return 流动人口信息列表
	 * @see [类、类#方法、类#成员]
	 */
	public List<DrugDto> getDrugInfoListByKeywords(String name, String idNo,Integer gridId,Integer iscrime,String controlsituation,Integer datefirstdiscover);
	
    /**
     * 
     * <吸毒者页面，按照人员类型分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
	
	  /**
     * 
     * <特殊人群页面，按照特殊人群类型分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getSpecialTypeStats();
    
    List<Integer> getSpecialTypeStatsCount();
      
    /**
     * 
     * <特殊人群页面，按照特殊人群分布统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getGridStats();
    
    /**
	 * <根据关键词查询特殊人群信息列表>
	 * 
	 * @param  gridId 所属网格
	 * @param specialType 特殊人员类型
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param pageNum 当前页 可选 ，默认 1
	 * @param pageSize 每页显示信息条数， 可选， 默认10
	 * @return 户籍人口信息及分页信息
	 * @see [类、类#方法、类#成员]
	 */
    List<SpecialCrowdDto> getSpecialCrowdListByKeywords(String specialType,String name,String idNo,List<Integer> residentBaseIds);
}
