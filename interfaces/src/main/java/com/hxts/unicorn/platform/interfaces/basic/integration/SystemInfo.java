package com.hxts.unicorn.platform.interfaces.basic.integration;

public class SystemInfo {
    /**
     * This field corresponds to the database column tb_thirdSys‌_info.id
     */
    private Integer id;

    /**
     * This field corresponds to the database column tb_thirdSys‌_info.sys_type
     */
    private String sysType;

    /**
     * This field corresponds to the database column tb_thirdSys‌_info.sys_name
     */
    private String sysName;

    /**
     * This field corresponds to the database column tb_thirdSys‌_info.ip
     */
    private String ip;

    /**
     * This field corresponds to the database column tb_thirdSys‌_info.port
     */
    private Integer port;

    /**
     * This field corresponds to the database column tb_thirdSys‌_info.ip
     */
    private String path;
    
	/**
     * This field corresponds to the database column tb_thirdSys‌_info.account
     */
    private String account;

    /**
     * This field corresponds to the database column tb_thirdSys‌_info.password
     */
    private String password;

    /**
     * This method returns the value of the database column tb_thirdSys‌_info.id
     *
     * @return the value of tb_register.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method sets the value of the database column tb_thirdSys‌_info.id
     *
     * @param id the value for tb_register.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column tb_thirdSys‌_info.sys_type
     *
     * @return the value of tb_register.sys_type
     */
    public String getSysType() {
        return sysType;
    }

    /**
     * This method sets the value of the database column tb_thirdSys‌_info.sys_type
     *
     * @param sysType the value for tb_register.sys_type
     */
    public void setSysType(String sysType) {
        this.sysType = sysType == null ? null : sysType.trim();
    }

    /**
     * This method returns the value of the database column tb_thirdSys‌_info.sys_name
     *
     * @return the value of tb_register.sys_name
     */
    public String getSysName() {
        return sysName;
    }

    /**
     * This method sets the value of the database column tb_thirdSys‌_info.sys_name
     *
     * @param sysName the value for tb_register.sys_name
     */
    public void setSysName(String sysName) {
        this.sysName = sysName == null ? null : sysName.trim();
    }

    /**
     * This method returns the value of the database column tb_thirdSys‌_info.ip
     *
     * @return the value of tb_register.ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method sets the value of the database column tb_thirdSys‌_info.ip
     *
     * @param ip the value for tb_register.ip
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method returns the value of the database column tb_thirdSys‌_info.port
     *
     * @return the value of tb_register.port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * This method sets the value of the database column tb_thirdSys‌_info.port
     *
     * @param port the value for tb_register.port
     */
    public void setPort(Integer port) {
        this.port = port;
    }
    
    /**
     * This method returns the value of the database column tb_thirdSys‌_info.path
     *
     * @return the value of tb_register.path
     */
    public String getPath() {
		return path;
	}
    
    /**
     * This method sets the value of the database column tb_thirdSys‌_info.path
     *
     * @param port the value for tb_register.path
     */
	public void setPath(String path) {
		this.path = path;
	}
	
    /**
     * This method returns the value of the database column tb_thirdSys‌_info.account
     *
     * @return the value of tb_register.account
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method sets the value of the database column tb_thirdSys‌_info.account
     *
     * @param account the value for tb_register.account
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method returns the value of the database column tb_thirdSys‌_info.password
     *
     * @return the value of tb_register.password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method sets the value of the database column tb_thirdSys‌_info.password
     *
     * @param password the value for tb_register.password
     */
    public void setPassword(String password) {
    	this.password = password == null ? null : password.trim();
    }

	@Override
	public String toString() {
		return "SystemInfo [sysType=" + sysType + ", sysName=" + sysName
				+ ", ip=" + ip + "]";
	}
    
}