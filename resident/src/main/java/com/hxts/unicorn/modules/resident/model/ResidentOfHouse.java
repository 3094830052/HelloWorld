package com.hxts.unicorn.modules.resident.model;

import java.util.Date;

public class ResidentOfHouse {
    private Long residentOfHouseId;

    private Long houseId;

    private Long residentBaseId;

    private String residentType;

    private String rentType;

    private String hiddenDanger;

    private Date disassociateTime;

    private Date associateTime;

    public Long getResidentOfHouseId() {
        return residentOfHouseId;
    }

    public void setResidentOfHouseId(Long residentOfHouseId) {
        this.residentOfHouseId = residentOfHouseId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getResidentBaseId() {
        return residentBaseId;
    }

    public void setResidentBaseId(Long residentBaseId) {
        this.residentBaseId = residentBaseId;
    }

    public String getResidentType() {
        return residentType;
    }

    public void setResidentType(String residentType) {
        this.residentType = residentType == null ? null : residentType.trim();
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType == null ? null : rentType.trim();
    }

    public String getHiddenDanger() {
        return hiddenDanger;
    }

    public void setHiddenDanger(String hiddenDanger) {
        this.hiddenDanger = hiddenDanger == null ? null : hiddenDanger.trim();
    }

    public Date getDisassociateTime() {
        return disassociateTime;
    }

    public void setDisassociateTime(Date disassociateTime) {
        this.disassociateTime = disassociateTime;
    }

    public Date getAssociateTime() {
        return associateTime;
    }

    public void setAssociateTime(Date associateTime) {
        this.associateTime = associateTime;
    }
}