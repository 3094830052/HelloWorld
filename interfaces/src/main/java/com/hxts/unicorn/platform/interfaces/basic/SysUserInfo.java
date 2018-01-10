/*
 * 文 件 名:  SysUserInfo.java
 * 版    权:  武汉虹信技术服务有限责任公司. Copyright 2017-YYYY,  All rights reserved
 * 描    述:  
 * 修 改 人:  LENOVO
 * 修改时间:  2017年10月12日
 * 修改内容:  
 */
package com.hxts.unicorn.platform.interfaces.basic;

import java.io.Serializable;
import java.util.Date;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SysUserInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6842100980287004975L;

	/**
	 * 用户ID，唯一，自增长
	 */
	private int userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 手机号码
	 */
	private String mobilephone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 民族
	 */
	private String nation;

	/**
	 * 政治面貌
	 */
	private String politicsStatus;

	/**
	 * 身份证号
	 */
	private String idcard;

	/**
	 * 出生年月日 YYMMDD
	 */
	private Date birthday;

	/**
	 * 级别 ：省 厅 县 乡 正 副 职
	 */
	private String level;

	/**
	 * 职责
	 */
	private String duty;

	/**
	 * 专业 特长
	 */
	private String profession;

	/**
	 * 学历
	 */
	private String education;

	/**
	 * 固定电话
	 */
	private String telephone;

	/**
	 * 上次登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 头像
	 */
	private String pic;

	/**
	 * 在线状态：0不在线，1在线
	 */
	private int onLineState;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPoliticsStatus() {
		return politicsStatus;
	}

	public void setPoliticsStatus(String politicsStatus) {
		this.politicsStatus = politicsStatus;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public int getOnLineState() {
		return onLineState;
	}

	public void setOnLineState(int onLineState) {
		this.onLineState = onLineState;
	}

	@Override
	public String toString() {
		return "SysUserInfo [realName=" + realName + ", mobilePhone=" + mobilephone + ", email=" + email + "]";
	}

}
