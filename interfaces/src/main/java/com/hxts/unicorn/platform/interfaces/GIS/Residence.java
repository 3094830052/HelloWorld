/*
 * 文 件 名:  Residence.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年11月14日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.GIS;

import java.io.Serializable;
import java.util.List;


/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Residence implements Serializable
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 531040565513052666L;
    
    /**
     * 人口基本信息id
     */
    private Integer residentBaseId;
    /**
     * 公民身份号码
     */
    private String idNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 曾用名
     */
    private String formerName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 民族
     */
    private String ethnicity;
    /**
     * 籍贯
     */
    private String nativePlace;
    
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 照片路径
     */
    private String picture;
    
    private String profession;

    private List<?> houses;
    /**
     * 获取 residentBaseId
     * @return 返回 residentBaseId
     */
    public Integer getResidentBaseId()
    {
        return residentBaseId;
    }

    /**
     * 设置 residentBaseId
     * @param 对residentBaseId进行赋值
     */
    public void setResidentBaseId(Integer residentBaseId)
    {
        this.residentBaseId = residentBaseId;
    }

    /**
     * 获取 idNo
     * @return 返回 idNo
     */
    public String getIdNo()
    {
        return idNo;
    }

    /**
     * 设置 idNo
     * @param 对idNo进行赋值
     */
    public void setIdNo(String idNo)
    {
        this.idNo = idNo;
    }

    /**
     * 获取 name
     * @return 返回 name
     */
    public String getName()
    {
        return name;
    }

    /**
     * 设置 name
     * @param 对name进行赋值
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * 获取 formerName
     * @return 返回 formerName
     */
    public String getFormerName()
    {
        return formerName;
    }

    /**
     * 设置 formerName
     * @param 对formerName进行赋值
     */
    public void setFormerName(String formerName)
    {
        this.formerName = formerName;
    }

    /**
     * 获取 sex
     * @return 返回 sex
     */
    public String getSex()
    {
        return sex;
    }

    /**
     * 设置 sex
     * @param 对sex进行赋值
     */
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    /**
     * 获取 ethnicity
     * @return 返回 ethnicity
     */
    public String getEthnicity()
    {
        return ethnicity;
    }

    /**
     * 设置 ethnicity
     * @param 对ethnicity进行赋值
     */
    public void setEthnicity(String ethnicity)
    {
        this.ethnicity = ethnicity;
    }

    /**
     * 获取 nativePlace
     * @return 返回 nativePlace
     */
    public String getNativePlace()
    {
        return nativePlace;
    }

    /**
     * 设置 nativePlace
     * @param 对nativePlace进行赋值
     */
    public void setNativePlace(String nativePlace)
    {
        this.nativePlace = nativePlace;
    }

    /**
     * 获取 contact
     * @return 返回 contact
     */
    public String getContact()
    {
        return contact;
    }

    /**
     * 设置 contact
     * @param 对contact进行赋值
     */
    public void setContact(String contact)
    {
        this.contact = contact;
    }

    /**
     * 获取 picture
     * @return 返回 picture
     */
    public String getPicture()
    {
        return picture;
    }

    /**
     * 设置 picture
     * @param 对picture进行赋值
     */
    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    /**
     * 获取 houses
     * @return 返回 houses
     */
    public List<?> getHouses()
    {
        return houses;
    }

    /**
     * 设置 houses
     * @param 对houses进行赋值
     */
    public void setHouses(List<?> houses)
    {
        this.houses = houses;
    }

    /**
     * 获取 profession
     * @return 返回 profession
     */
    public String getProfession()
    {
        return profession;
    }

    /**
     * 设置 profession
     * @param 对profession进行赋值
     */
    public void setProfession(String profession)
    {
        this.profession = profession;
    }

}
