/*
 * 文 件 名:  ResidentStat.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年11月7日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.GIS;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hxts.unicorn.platform.interfaces.biz.ResidentBaseInfo;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CrowdStat implements Serializable
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 685665226640940821L;

    /**
     * 楼层
     */
    private Integer floorNumber;

    /**
     * 房间号
     */
    private Integer houseNumber;

    
    /**
     * 单元号
     */
    private Integer unitNumber;
    
    /**
     * 楼栋ID，唯一标识
     */
    private Long buildingId;
    
    /**
     * 楼栋名称
     */
    private String buildingName;
    
    /**
     * 街道
     */
    private String street;
    
    /**
     * 社区
     */
    private String community;
    
    private Long uid;
    
    private String useType;
    
    private Map<String, Integer> SpecialCrowd;
    
    private List<ResidentBaseInfo> residents;

    /**
     * 获取 unitNumber
     * @return 返回 unitNumber
     */
    public Integer getUnitNumber()
    {
        return unitNumber;
    }

    /**
     * 设置 unitNumber
     * @param 对unitNumber进行赋值
     */
    public void setUnitNumber(Integer unitNumber)
    {
        this.unitNumber = unitNumber;
    }

    /**
     * 获取 buildingId
     * @return 返回 buildingId
     */
    public Long getBuildingId()
    {
        return buildingId;
    }

    /**
     * 设置 buildingId
     * @param 对buildingId进行赋值
     */
    public void setBuildingId(Long buildingId)
    {
        this.buildingId = buildingId;
    }

    /**
     * 获取 buildingName
     * @return 返回 buildingName
     */
    public String getBuildingName()
    {
        return buildingName;
    }

    /**
     * 设置 buildingName
     * @param 对buildingName进行赋值
     */
    public void setBuildingName(String buildingName)
    {
        this.buildingName = buildingName;
    }

    /**
     * 获取 street
     * @return 返回 street
     */
    public String getStreet()
    {
        return street;
    }

    /**
     * 设置 street
     * @param 对street进行赋值
     */
    public void setStreet(String street)
    {
        this.street = street;
    }

    /**
     * 获取 community
     * @return 返回 community
     */
    public String getCommunity()
    {
        return community;
    }

    /**
     * 设置 community
     * @param 对community进行赋值
     */
    public void setCommunity(String community)
    {
        this.community = community;
    }

    /**
     * 获取 specialCrowd
     * @return 返回 specialCrowd
     */
    public Map<String, Integer> getSpecialCrowd()
    {
        return SpecialCrowd;
    }

    /**
     * 设置 specialCrowd
     * @param 对specialCrowd进行赋值
     */
    public void setSpecialCrowd(Map<String, Integer> specialCrowd)
    {
        SpecialCrowd = specialCrowd;
    }

    /**
     * 获取 floorNumber
     * @return 返回 floorNumber
     */
    public Integer getFloorNumber()
    {
        return floorNumber;
    }

    /**
     * 设置 floorNumber
     * @param 对floorNumber进行赋值
     */
    public void setFloorNumber(Integer floorNumber)
    {
        this.floorNumber = floorNumber;
    }

    /**
     * 获取 houseNumber
     * @return 返回 houseNumber
     */
    public Integer getHouseNumber()
    {
        return houseNumber;
    }

    /**
     * 设置 houseNumber
     * @param 对houseNumber进行赋值
     */
    public void setHouseNumber(Integer houseNumber)
    {
        this.houseNumber = houseNumber;
    }

    /**
     * 获取 residents
     * @return 返回 residents
     */
    public List<ResidentBaseInfo> getResidents()
    {
        return residents;
    }

    /**
     * 设置 residents
     * @param 对residents进行赋值
     */
    public void setResidents(List<ResidentBaseInfo> residents)
    {
        this.residents = residents;
    }

    /**
     * 获取 uid
     * @return 返回 uid
     */
    public Long getUid()
    {
        return uid;
    }

    /**
     * 设置 uid
     * @param 对uid进行赋值
     */
    public void setUid(Long uid)
    {
        this.uid = uid;
    }

    /**
     * 获取 useType
     * @return 返回 useType
     */
    public String getUseType()
    {
        return useType;
    }

    /**
     * 设置 useType
     * @param 对useType进行赋值
     */
    public void setUseType(String useType)
    {
        this.useType = useType;
    }


    
}
