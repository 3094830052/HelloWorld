package com.hxts.unicorn.modules.resident.model;

public class ResidentScoreRecord {
    private Integer residentScoreRecordId;

    private Integer residentBaseId;

    private String openId;

    private Integer score;
    
    private Integer recordId;
    
    private String type;
    
    private String scoreDetails;

    public Integer getResidentScoreRecordId() {
        return residentScoreRecordId;
    }

    public void setResidentScoreRecordId(Integer residentScoreRecordId) {
        this.residentScoreRecordId = residentScoreRecordId;
    }

    public Integer getResidentBaseId() {
        return residentBaseId;
    }

    public void setResidentBaseId(Integer residentBaseId) {
        this.residentBaseId = residentBaseId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getScoreDetails() {
        return scoreDetails;
    }

    public void setScoreDetails(String scoreDetails) {
        this.scoreDetails = scoreDetails == null ? null : scoreDetails.trim();
    }

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
    
}