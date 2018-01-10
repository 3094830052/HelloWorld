package com.hxts.unicorn.platform.interfaces.basic.message;

public class AlarmSetDetail {
    private Integer tid;

    private String alarmType;

    private Boolean locationFilter;

    private String locations;
    
    /**
     * 所有的告警消息产生对应的告警记录的次数过滤条件默认是1，即一条告警消息alarmMessage对应一条告警记录alarmRecord
     */
    private Integer countFilter = 1;

    private Boolean timehorizonFilter;

    private String startTime;

    private String endTime;

    private Boolean pushToBigmap;

    private String remark;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType == null ? null : alarmType.trim();
    }

    public Boolean getLocationFilter() {
        return locationFilter;
    }

    public void setLocationFilter(Boolean locationFilter) {
        this.locationFilter = locationFilter;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations == null ? null : locations.trim();
    }
    
    public Integer getCountFilter() {
        return countFilter == null ? 1 : countFilter;
    }

    public void setCountFilter(Integer countFilter) {
        this.countFilter = countFilter;
    }

    public Boolean getTimehorizonFilter() {
        return timehorizonFilter;
    }

    public void setTimehorizonFilter(Boolean timehorizonFilter) {
        this.timehorizonFilter = timehorizonFilter;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Boolean getPushToBigmap() {
        return pushToBigmap;
    }

    public void setPushToBigmap(Boolean pushToBigmap) {
        this.pushToBigmap = pushToBigmap;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}