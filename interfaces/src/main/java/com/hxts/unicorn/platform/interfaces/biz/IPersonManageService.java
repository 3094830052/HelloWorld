/*
 * 文 件 名:  IResidentManager.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月26日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.biz;

import java.util.List;
import java.util.Map;

import com.hxts.unicorn.platform.interfaces.IBizModule;

/**
 * 人口管理
 * 人口数据登记、维护，查询，导入导出，统计
 * 
 * @author  夏世念 0380008621
 * @version  [版本号, 2017年9月26日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface IPersonManageService extends IBizModule {
	public static final String APP_ID = "RESIDENT";
	/**
	 * 标签类型   人口基础信息
	 */
	public static final String LABEL_BASE = "base";
	/**
	 * 标签类型  户籍人口信息
	 */
	public static final String LABEL_HOUSEHOLD = "household";
	/**
	 * 标签类型 流动人口信息
	 */
	public static final String LABEL_FLOATING = "floating";
	/**
	 * 标签类型  境外人口信息
	 */
	public static final String LABEL_FOREIGN = "foreigner";
	/**
	 * 标签类型  留守人口信息
	 */
	public static final String LABEL_LEFT_BEHIND = "leftBehind";
	/**
	 * 标签类型  出租屋承租人信息
	 */
	public static final String LABEL_RENTER = "renter";
	/**
	 * 标签类型  低保人员信息
	 */
	public static final String LABEL_SUB_ALLOW = "suballow";
	/**
	 * 标签类型 独居老人信息
	 */
	public static final String LABEL_LIVE_ALONE = "livealone";
	/**
	 * 标签类型  残障人士信息
	 */
	public static final String LABEL_DISABLED = "disabled";
	/**
	 * 标签类型  重点青少年信息
	 */
	public static final String LABEL_TEENAGER = "teenager";
	/**
	 * 人群类型  帮扶人群
	 */
	public static final String LABEL_ASSIST = "assist";
	/**
	 * 人群类型  重点人员(特殊人群+重点青少年)
	 */
	public static final String LABEL_KEY_PERSON = "keyperson";
	/**
	 * 人群类型  关注人群(帮扶人群+特殊人群+重点青少年)
	 */
	public static final String LABEL_KEY_CROWD = "keycrowd";
	/**
	 * 人群类型  特殊人群
	 */
	public static final String LABEL_SPECIAL = "special";
	/**
	 * 标签类型   吸毒者
	 */
	public static final String LABEL_DRUG = "drug";
	/**
	 * 标签类型 刑满释放人员
	 */
	public static final String LABEL_EMANCIPIST = "emancipist";
	/**
	 * 标签类型   非法上访人员
	 */
	public static final String LABEL_PERTITION = "pertition";
	/**
	 * 标签类型   社区矫正人员
	 */
	public static final String LABEL_RECTIFY = "rectify";
	/**
	 * 标签类型   艾滋病
	 */
	public static final String LABEL_AIDS = "aids";
	/**
	 * 标签类型   精神障碍
	 */
	public static final String LABEL_ALLOEOSIS = "alloeosis";
	/**
	 * 
	 * <获取人口信息>
	 * <注：标签为base时，默认返回所有的标签；标签为除base外的其它标签时，仅返回基本信息和传入的标签信息>
	 * @param label 标签：base,household,floating,foreigner,leftBehind,renter,suballow,livealone,disabled,teenager
	 * @param labelId 标签id
	 * @param idNo 身份证号码
	 * @param isReturnLabel 是否返回标签的属性信息，true返回标签类型和属性信息 ，false时只返回标签类型(查看ResidentInfo labels属性)。
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public ResidentInfo getResidentInfo(String label,Integer labelId,String idNo,boolean isReturnLabel);
	/**
	 * 
	 * <根据关键字对人口基础信息进行模糊查询>
	 * <功能详细描述>
	 * @param idNo 身份证号码
	 * @param name 姓名
	 * @param contract 联系方式
	 * @return 人口基础信息列表
	 * @see [类、类#方法、类#成员]
	 */
	public List<ResidentBaseInfo> getResidentListByKeywords(String idNo,String name,String contact);
	
	
	/**
	 * 
	 * <根据姓名模糊查询>
	 * <功能详细描述>
	 * @param name 姓名
	 * @return 姓名 ,人口基本信息id列表
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String,List<Integer>> getResidentInfoByName(String name);
	
	/**
	 * 
	 * <获取重点人员或帮扶人员所在房屋名称>
	 * <功能详细描述>
	 * @param type 人群类型   帮扶人群、重点人员,已定义常量
	 * @return 房屋名称列表
	 * @see [类、类#方法、类#成员]
	 */
	public List<String> getSpecialsHouseNames(String type);
	/**
	 * 
	 * <获取特定标签人员所在房屋名称列表>
	 * <功能详细描述>
	 * @param type列表 人员类型列表   已定义常量
	 * @return 房屋名称列表
	 * @see [类、类#方法、类#成员]
	 */
	public List<String> getSpecialsHouseNamesByType(List<String> types);
	
	/**
	 * 
	 * <获取重点人员或帮扶人员人口基础信息列表>
	 * <功能详细描述>
	 * @param residentBaseIds 人口基础信息id列表
	 * @param type 人群类型   帮扶人群、重点人员,已定义常量
	 * @return 人口基础信息列表
	 * @see [类、类#方法、类#成员]
	 */
	public List<ResidentBaseInfo> getSpecialsInfoByIds(List<Integer> residentBaseIds, String type);
	
	/**
	 * 
	 * <通过给定的人口id列表，统计特殊标签的数量>
	 * <功能详细描述>
	 * @param residentBaseIds 给定的人口基础信息id列表
	 * @param labels 所需要统计的标签列表：household,floating,foreigner,leftBehind,renter,suballow,livealone,disabled,teenager
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String,Integer> getResidentLabelsByIds(List<Integer> residentBaseIds,List<String> labels);
	
	/**
	 * 
	 * <人口管理页面，根据用户id，获取当天人口数据的更新动态，例如新增多少条，更新多少条>
	 * <新增的key是insert，更新的key是update>
	 * @param userId 用户id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, Integer> getDataUpdateStats(Integer userId);
}
