package com.hxts.unicorn.modules.resident.service;
import java.util.Date;
import java.util.List;

import com.hxts.unicorn.modules.resident.dto.EmancipistDto;
import com.hxts.unicorn.modules.resident.model.StatsResult;
import com.hxts.unicorn.platform.interfaces.biz.EmancipistInfo;


public interface ISrvEmancipist{
	/**
	 * 
	 * <保存新增或编辑刑满释放人员信息>
	 * @param emancipistInfo 刑满释放人员标签信息
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int save(EmancipistInfo emancipistInfo);
	/**
	 * 
	 * <根据刑满释放人员id删除刑满释放人员信息>
	 * @param emancipistId 刑满释放人员id
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByEmancipistId(Integer emancipistId);
	/**
	 * 
	 * <根据刑满释放人员id查询刑满释放人员信息>
	 * @param emancipistId 刑满释放人员id
	 * @return 刑满释放人员标签信息
	 * @see [类、类#方法、类#成员]
	 */
	EmancipistInfo selectByEmancipistId(Integer emancipistId);
	/**
	 * 
	 * <根据关键字查询刑满释放人员信息列表>
	 * @param  LastcohesionDate 最近衔接时间
	 * @param  riskAssessmentType 危险性评估
	 * @param  placeSituation 安置情况
	 * @param name 姓名
	 * @param idNo 身份号码
	 * @param gridId 所属网格
	 * @return 刑满释放人员信息列表
	 * @see [类、类#方法、类#成员]
	 */
	public List<EmancipistDto> getEmancipistListByKeywords(Date LastcohesionDate,String riskAssessmentType,String placeSituation,String name, String idNo,Integer gridId);
	
    /**
     * 
     * <刑满释放人员页面，按照人员类型分类统计人数>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<StatsResult> getEmancipistTypeStats();

}