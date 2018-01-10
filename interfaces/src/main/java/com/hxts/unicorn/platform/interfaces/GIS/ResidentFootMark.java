/*
 * 文 件 名:  ResidentFootMark.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年11月7日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.GIS;

import java.io.Serializable;
import java.util.Date;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ResidentFootMark implements Serializable
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5198312931826075708L;
    
    /**
     * 人口基本信息id
     */
    private Integer residentBaseId;
    
    //姓名
    private String name;
    
    //出现时间
    private Date footTime;
    
    //扑捉踪迹设备id
    private Integer equipId;

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
     * 获取 footTime
     * @return 返回 footTime
     */
    public Date getFootTime()
    {
        return footTime;
    }

    /**
     * 设置 footTime
     * @param 对footTime进行赋值
     */
    public void setFootTime(Date footTime)
    {
        this.footTime = footTime;
    }

    /**
     * 获取 equipId
     * @return 返回 equipId
     */
    public Integer getEquipId()
    {
        return equipId;
    }

    /**
     * 设置 equipId
     * @param 对equipId进行赋值
     */
    public void setEquipId(Integer equipId)
    {
        this.equipId = equipId;
    }
    

    
}
