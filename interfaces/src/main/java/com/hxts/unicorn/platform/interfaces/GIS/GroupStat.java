/*
 * 文 件 名:  PositionStat.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年12月12日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.platform.interfaces.GIS;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年12月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GroupStat implements Serializable
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6646158498226234983L;
    
    private int groupId;
    
    private String groupName;
    
    private Map<String, Integer> onlineStat;
    
    private List<GridUser> users;


    /**
     * 获取 onlineStat
     * @return 返回 onlineStat
     */
    public Map<String, Integer> getOnlineStat()
    {
        return onlineStat;
    }

    /**
     * 设置 onlineStat
     * @param 对onlineStat进行赋值
     */
    public void setOnlineStat(Map<String, Integer> onlineStat)
    {
        this.onlineStat = onlineStat;
    }

    /**
     * 获取 users
     * @return 返回 users
     */
    public List<GridUser> getUsers()
    {
        return users;
    }

    /**
     * 设置 users
     * @param 对users进行赋值
     */
    public void setUsers(List<GridUser> users)
    {
        this.users = users;
    }

    /**
     * 获取 groupId
     * @return 返回 groupId
     */
    public int getGroupId()
    {
        return groupId;
    }

    /**
     * 设置 groupId
     * @param 对groupId进行赋值
     */
    public void setGroupId(int groupId)
    {
        this.groupId = groupId;
    }

    /**
     * 获取 groupName
     * @return 返回 groupName
     */
    public String getGroupName()
    {
        return groupName;
    }

    /**
     * 设置 groupName
     * @param 对groupName进行赋值
     */
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    
}
