/*
 * 文 件 名:  HouseStat.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年11月7日
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
 * @version  [版本号, 2017年11月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HouseStat implements Serializable
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3408419281479996491L;
    
    /**
     * 房屋ID，唯一标识
     */
    private long houseId;
    
    /**
     * 单元号
     */
    private int unitNumber;

    /**
     * 楼层
     */
    private int floorNumber;

    /**
     * 房间号
     */
    private int houseNumber;
    
    /**
     * 所属楼栋号
     */
    private int buildingId;
    
    /**
     * 房屋类型
     */
    private String houseType;

    
    //住户id集合
    private List<Integer> residents;


    /**
     * 获取 houseId
     * @return 返回 houseId
     */
    public long getHouseId()
    {
        return houseId;
    }


    /**
     * 设置 houseId
     * @param 对houseId进行赋值
     */
    public void setHouseId(long houseId)
    {
        this.houseId = houseId;
    }


    /**
     * 获取 unitNumber
     * @return 返回 unitNumber
     */
    public int getUnitNumber()
    {
        return unitNumber;
    }


    /**
     * 设置 unitNumber
     * @param 对unitNumber进行赋值
     */
    public void setUnitNumber(int unitNumber)
    {
        this.unitNumber = unitNumber;
    }


    /**
     * 获取 floorNumber
     * @return 返回 floorNumber
     */
    public int getFloorNumber()
    {
        return floorNumber;
    }


    /**
     * 设置 floorNumber
     * @param 对floorNumber进行赋值
     */
    public void setFloorNumber(int floorNumber)
    {
        this.floorNumber = floorNumber;
    }


    /**
     * 获取 houseNumber
     * @return 返回 houseNumber
     */
    public int getHouseNumber()
    {
        return houseNumber;
    }


    /**
     * 设置 houseNumber
     * @param 对houseNumber进行赋值
     */
    public void setHouseNumber(int houseNumber)
    {
        this.houseNumber = houseNumber;
    }


    /**
     * 获取 buildingId
     * @return 返回 buildingId
     */
    public int getBuildingId()
    {
        return buildingId;
    }


    /**
     * 设置 buildingId
     * @param 对buildingId进行赋值
     */
    public void setBuildingId(int buildingId)
    {
        this.buildingId = buildingId;
    }


    /**
     * 获取 houseType
     * @return 返回 houseType
     */
    public String getHouseType()
    {
        return houseType;
    }


    /**
     * 设置 houseType
     * @param 对houseType进行赋值
     */
    public void setHouseType(String houseType)
    {
        this.houseType = houseType;
    }


    /**
     * 获取 residents
     * @return 返回 residents
     */
    public List<Integer> getResidents()
    {
        return residents;
    }


    /**
     * 设置 residents
     * @param 对residents进行赋值
     */
    public void setResidents(List<Integer> residents)
    {
        this.residents = residents;
    }


}
