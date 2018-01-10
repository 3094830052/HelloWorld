package com.hxts.unicorn.modules.resident.model;

import java.util.Date;

/**
 * <人员出入记录>
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年10月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PersonInOutRecord {
	/**
	 * 人员出入recordId，唯一标识符
	 */
	private Integer recordId;
	/**
	 * 对应表tb_thirdsys‌_info中注册的第三方子系统id
	 */
	private Integer sysId;
	/**
	 * 与房屋绑定的门禁卡号
	 */
	private String cardId;
	/**
	 * 进出类型;true表示出，对应表中的1；false表示进，对应表中的0
	 */
	private Boolean inOut;
	/**
	 * 经过门禁时间,表中格式为yyyy-MM-dd HH:mm:ss
	 */
	private Date passTime;
	/**
	 * 门禁具体位置,门禁类型通过门禁位置来区分,小区和单元关键字调整为：小区出入口、单元出入口
	 * 如：
	 * 佛祖令试点小区/F区/12栋/1单元/单元出入口
	 * 佛祖令试点小区/F区/小区出入口1
	 */
	private String position;

	/**
	 * 记录过期时间，格式为yyyy-MM-dd
	 */
	private Date dueTime;
	
	/**
	 * 刷卡设备ID
	 */
	private String deviceId;
	
	/**
	 * 开门方式,目前有IC卡、身份证、人脸三种
	 */
    private String openType;
    
    /**
     * 出入门禁时通过人脸识别捕捉到的留影照片地址
     */
    private String pictureUrl;
    
    /**
     * 人脸识别捕捉到的留影照片和业主留存的底库照片的匹配度，值为0-100
     */
    private Integer compResult;

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getSysId() {
		return sysId;
	}

	public void setSysId(Integer sysId) {
		this.sysId = sysId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId == null ? null : cardId.trim();
	}

	public Boolean getInOut() {
		return inOut;
	}

	public void setInOut(Boolean inOut) {
		this.inOut = inOut;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position == null ? null : position.trim();
	}

	public Date getDueTime() {
		return dueTime;
	}

	public void setDueTime(Date dueTime) {
		this.dueTime = dueTime;
	}
	
	public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType == null ? null : openType.trim();
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
    }

    public Integer getCompResult() {
        return compResult;
    }

    public void setCompResult(Integer compResult) {
        this.compResult = compResult;
    }
}