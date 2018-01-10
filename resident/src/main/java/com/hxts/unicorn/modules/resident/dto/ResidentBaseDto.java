package com.hxts.unicorn.modules.resident.dto;

import javax.validation.Valid;

import com.hxts.unicorn.modules.resident.model.ForeignerInfo;
import com.hxts.unicorn.platform.interfaces.biz.FloatingResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.HouseholdResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentInfo;

public class ResidentBaseDto {
	
	@Valid
	private ResidentInfo b;
	@Valid
	private HouseholdResidentInfo h;
	@Valid
	private FloatingResidentInfo f;
	@Valid
	private ForeignerInfo fo;
	
	public ResidentInfo getB() {
		return b;
	}
	public void setB(ResidentInfo b) {
		this.b = b;
	}
	public HouseholdResidentInfo getH() {
		return h;
	}
	public void setH(HouseholdResidentInfo h) {
		this.h = h;
	}
	public FloatingResidentInfo getF() {
		return f;
	}
	public void setF(FloatingResidentInfo f) {
		this.f = f;
	}
	public ForeignerInfo getFo() {
		return fo;
	}
	public void setFo(ForeignerInfo fo) {
		this.fo = fo;
	}
	
}
