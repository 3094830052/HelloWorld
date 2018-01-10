/*
 * 文 件 名:  GridStat.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年11月24日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.GIS;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hxts.unicorn.platform.interfaces.basic.SysUserInfo;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GridStat implements Serializable
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7523679647920266372L;
    
    private Integer gridId;
    
    private String region;

    private String buildings;

    private String gridName;
    
    private List<SysUserInfo> griders;
    
    private Integer hazardousCount;
    
    private Integer oldAndWeakCount;
    
    private Integer workOrderCount;
    

    /**
     * 获取 gridId
     * @return 返回 gridId
     */
    public Integer getGridId()
    {
        return gridId;
    }

    /**
     * 设置 gridId
     * @param 对gridId进行赋值
     */
    public void setGridId(Integer gridId)
    {
        this.gridId = gridId;
    }

    /**
     * 获取 region
     * @return 返回 region
     */
    public String getRegion()
    {
        return region;
    }

    /**
     * 设置 region
     * @param 对region进行赋值
     */
    public void setRegion(String region)
    {
        this.region = region;
    }

    /**
     * 获取 buildings
     * @return 返回 buildings
     */
    public String getBuildings()
    {
        return buildings;
    }

    /**
     * 设置 buildings
     * @param 对buildings进行赋值
     */
    public void setBuildings(String buildings)
    {
        this.buildings = buildings;
    }

    /**
     * 获取 gridName
     * @return 返回 gridName
     */
    public String getGridName()
    {
        return gridName;
    }

    /**
     * 设置 gridName
     * @param 对gridName进行赋值
     */
    public void setGridName(String gridName)
    {
        this.gridName = gridName;
    }

    /**
     * 获取 griders
     * @return 返回 griders
     */
    public List<SysUserInfo> getGriders()
    {
        return griders;
    }

    /**
     * 设置 griders
     * @param 对griders进行赋值
     */
    public void setGriders(List<SysUserInfo> griders)
    {
        this.griders = griders;
    }

    /**
     * 获取 hazardousCount
     * @return 返回 hazardousCount
     */
    public Integer getHazardousCount()
    {
        return hazardousCount;
    }

    /**
     * 设置 hazardousCount
     * @param 对hazardousCount进行赋值
     */
    public void setHazardousCount(Integer hazardousCount)
    {
        this.hazardousCount = hazardousCount;
    }

    /**
     * 获取 oldAndWeakCount
     * @return 返回 oldAndWeakCount
     */
    public Integer getOldAndWeakCount()
    {
        return oldAndWeakCount;
    }

    /**
     * 设置 oldAndWeakCount
     * @param 对oldAndWeakCount进行赋值
     */
    public void setOldAndWeakCount(Integer oldAndWeakCount)
    {
        this.oldAndWeakCount = oldAndWeakCount;
    }

    /**
     * 获取 workOrderCount
     * @return 返回 workOrderCount
     */
    public Integer getWorkOrderCount()
    {
        return workOrderCount;
    }

    /**
     * 设置 workOrderCount
     * @param 对workOrderCount进行赋值
     */
    public void setWorkOrderCount(Integer workOrderCount)
    {
        this.workOrderCount = workOrderCount;
    }

}
