package com.hxts.unicorn.platform.interfaces.basic.integration;

public class RecordInfo {
    /**
     * This field corresponds to the database column tb_record_info.sys_id
     */
    private Integer sysId;

    /**
     * This field corresponds to the database column tb_record_info.data_type
     */
    private String dataType;

    /**
     * This field corresponds to the database column tb_record_info.last_key
     */
    private String lastKey;

    /**
     * This method returns the value of the database column tb_record_info.sys_id
     *
     * @return the value of tb_record_info.sys_id
     */
    public Integer getSysId() {
        return sysId;
    }

    /**
     * This method sets the value of the database column tb_record_info.sys_id
     *
     * @param sysId the value for tb_record_info.sys_id
     */
    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    /**
     * This method returns the value of the database column tb_record_info.data_type
     *
     * @return the value of tb_record_info.data_type
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * This method sets the value of the database column tb_record_info.data_type
     *
     * @param dataType the value for tb_record_info.data_type
     */
    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    /**
     * This method returns the value of the database column tb_record_info.last_key
     *
     * @return the value of tb_record_info.last_key
     */
    public String getLastKey() {
        return lastKey;
    }

    /**
     * This method sets the value of the database column tb_record_info.last_key
     *
     * @param lastKey the value for tb_record_info.last_key
     */
    public void setLastKey(String lastKey) {
        this.lastKey = lastKey == null ? null : lastKey.trim();
    }
    
    @Override
	public String toString() {
		return "RecordInfo [sysId=" + sysId + ", dataType=" + dataType
				+ ", lastKey=" + lastKey + "]";
	}
}