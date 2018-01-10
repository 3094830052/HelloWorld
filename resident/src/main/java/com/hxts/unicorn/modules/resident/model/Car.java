package com.hxts.unicorn.modules.resident.model;

import java.io.Serializable;

/**
 * <人车信息>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Car implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4681359933231564275L;
	/**
	 * 车辆ID
	 */
	private Integer carId;
	/**
	 * 人员基本信息id
	 */
    private Integer residentBaseId;
    /**
     * 车牌号
     */
    private String carNo;
    /**
     * 车辆颜色
     */
    private String carColor;
    /**
     * 车辆用途
     */
    private String carUsage;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getResidentBaseId() {
        return residentBaseId;
    }

    public void setResidentBaseId(Integer residentBaseId) {
        this.residentBaseId = residentBaseId;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor == null ? null : carColor.trim();
    }

    public String getCarUsage() {
        return carUsage;
    }

    public void setCarUsage(String carUsage) {
        this.carUsage = carUsage == null ? null : carUsage.trim();
    }
}