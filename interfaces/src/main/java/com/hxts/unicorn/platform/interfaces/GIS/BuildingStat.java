/*
 * 文 件 名:  BuildingStat.java
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
public class BuildingStat implements Serializable
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4569290550104898449L;
    
    /**
     * 楼栋ID，唯一标识
     */
    private long buildingId;
    
    private String buildingName;
    
    private String buildingPurpose;
    
    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    
    /**
     * 街道
     */
    private String street;
    
    /**
     * 社区
     */
    private String community;
    
    /**
     * 所属网格ID
     */
    private int gridId;
    
    //商铺数
    private int shops;
    
    //自住数
    private int owners;
    
    //租户数
    private int tenants;
    
    //企业房间号
    private List<String> firms;
    
    //租住百分比
    private float usage;
    
    //楼栋图片
    private String picUrl;

    /**
     * 获取 buildingId
     * @return 返回 buildingId
     */
    public long getBuildingId()
    {
        return buildingId;
    }

    /**
     * 设置 buildingId
     * @param 对buildingId进行赋值
     */
    public void setBuildingId(long buildingId)
    {
        this.buildingId = buildingId;
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
     * 获取 gridId
     * @return 返回 gridId
     */
    public int getGridId()
    {
        return gridId;
    }

    /**
     * 设置 gridId
     * @param 对gridId进行赋值
     */
    public void setGridId(int gridId)
    {
        this.gridId = gridId;
    }

    /**
     * 获取 shops
     * @return 返回 shops
     */
    public int getShops()
    {
        return shops;
    }

    /**
     * 设置 shops
     * @param 对shops进行赋值
     */
    public void setShops(int shops)
    {
        this.shops = shops;
    }

    /**
     * 获取 owners
     * @return 返回 owners
     */
    public int getOwners()
    {
        return owners;
    }

    /**
     * 设置 owners
     * @param 对owners进行赋值
     */
    public void setOwners(int owners)
    {
        this.owners = owners;
    }

    /**
     * 获取 tenants
     * @return 返回 tenants
     */
    public int getTenants()
    {
        return tenants;
    }

    /**
     * 设置 tenants
     * @param 对tenants进行赋值
     */
    public void setTenants(int tenants)
    {
        this.tenants = tenants;
    }

    /**
     * 获取 firms
     * @return 返回 firms
     */
    public List<String> getFirms()
    {
        return firms;
    }

    /**
     * 设置 firms
     * @param 对firms进行赋值
     */
    public void setFirms(List<String> firms)
    {
        this.firms = firms;
    }

    /**
     * 获取 usage
     * @return 返回 usage
     */
    public float getUsage()
    {
        return usage;
    }

    /**
     * 设置 usage
     * @param 对usage进行赋值
     */
    public void setUsage(float usage)
    {
        this.usage = usage;
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
     * 获取 longitude
     * @return 返回 longitude
     */
    public String getLongitude()
    {
        return longitude;
    }

    /**
     * 设置 longitude
     * @param 对longitude进行赋值
     */
    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    /**
     * 获取 latitude
     * @return 返回 latitude
     */
    public String getLatitude()
    {
        return latitude;
    }

    /**
     * 设置 latitude
     * @param 对latitude进行赋值
     */
    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    /**
     * 获取 buildingPurpose
     * @return 返回 buildingPurpose
     */
    public String getBuildingPurpose()
    {
        return buildingPurpose;
    }

    /**
     * 设置 buildingPurpose
     * @param 对buildingPurpose进行赋值
     */
    public void setBuildingPurpose(String buildingPurpose)
    {
        this.buildingPurpose = buildingPurpose;
    }

    /**
     * 获取 picUrl
     * @return 返回 picUrl
     */
    public String getPicUrl()
    {
        return picUrl;
    }

    /**
     * 设置 picUrl
     * @param 对picUrl进行赋值
     */
    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }


    
    
}
