package com.hxts.unicorn.platform.interfaces.biz;


public interface ISrvScoreRecord  {
	public static final String APP_ID = "Score";

	/**
	 * 
	 * <添加积分,自动写到积分记录日志表>
	 * <功能详细描述>
	 * @param openId 微信账号
	 * @param score 获得/减少的积分
	 * @param residentBaseId 提交人人员基础ID
	 * @param recordId 获得积分记录ID(人员ID，事件ID等等)
	 * @param type 获得积分类型(提交人员信息/事件信息/消费等)
	 * @param scoreDetails 积分得失详情
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int addscore(String openId, int score, int residentBaseId,int recordId,
			String type, String scoreDetails);

	/**
	 * <写积分记录表>
	 * @param residentBaseId
	 * @param openId
	 * @param score
	 * @param recordId
	 * @param type
	 * @param scoreDetails
	 * @return
	 */
	int writeScoreRecord(int residentBaseId, String openId, int score, int recordId, String type, String scoreDetails);
}
