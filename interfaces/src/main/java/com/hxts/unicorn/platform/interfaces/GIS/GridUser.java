/*
 * 文 件 名:  GridUser.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年12月12日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.GIS;

import java.io.Serializable;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年12月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GridUser implements Serializable
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3004246734456553030L;
    
    private int userId;
    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号码
     */
    private String mobilephone;
    
    /**
     * 身份证号
     */
    private String idcard;
    
    private int onLineState;


    /**
     * 所属网格名称
     */
    private String gridNames;


    /**
     * 获取 realName
     * @return 返回 realName
     */
    public String getRealName()
    {
        return realName;
    }


    /**
     * 设置 realName
     * @param 对realName进行赋值
     */
    public void setRealName(String realName)
    {
        this.realName = realName;
    }


    /**
     * 获取 mobilephone
     * @return 返回 mobilephone
     */
    public String getMobilephone()
    {
        return mobilephone;
    }


    /**
     * 设置 mobilephone
     * @param 对mobilephone进行赋值
     */
    public void setMobilephone(String mobilephone)
    {
        this.mobilephone = mobilephone;
    }


    /**
     * 获取 idcard
     * @return 返回 idcard
     */
    public String getIdcard()
    {
        return idcard;
    }


    /**
     * 设置 idcard
     * @param 对idcard进行赋值
     */
    public void setIdcard(String idcard)
    {
        this.idcard = idcard;
    }


    /**
     * 获取 gridNames
     * @return 返回 gridNames
     */
    public String getGridNames()
    {
        return gridNames;
    }


    /**
     * 设置 gridNames
     * @param 对gridNames进行赋值
     */
    public void setGridNames(String gridNames)
    {
        this.gridNames = gridNames;
    }


    /**
     * 获取 userId
     * @return 返回 userId
     */
    public int getUserId()
    {
        return userId;
    }


    /**
     * 设置 userId
     * @param 对userId进行赋值
     */
    public void setUserId(int userId)
    {
        this.userId = userId;
    }


    /**
     * 获取 onLineState
     * @return 返回 onLineState
     */
    public int getOnLineState()
    {
        return onLineState;
    }


    /**
     * 设置 onLineState
     * @param 对onLineState进行赋值
     */
    public void setOnLineState(int onLineState)
    {
        this.onLineState = onLineState;
    }
    
}
