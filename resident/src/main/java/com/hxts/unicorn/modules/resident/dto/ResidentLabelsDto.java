package com.hxts.unicorn.modules.resident.dto;

import com.hxts.unicorn.platform.interfaces.biz.DisabledPeopleInfo;
import com.hxts.unicorn.platform.interfaces.biz.KeyTeenagerInfo;
import com.hxts.unicorn.platform.interfaces.biz.LeftBehindResidentInfo;
import com.hxts.unicorn.platform.interfaces.biz.LivingAloneAgedInfo;
import com.hxts.unicorn.platform.interfaces.biz.SubsistenceAllowanceInfo;

public class ResidentLabelsDto {
	private LeftBehindResidentInfo lb;
	private KeyTeenagerInfo k;
	private DisabledPeopleInfo d;
	private LivingAloneAgedInfo l;
	private SubsistenceAllowanceInfo s;
	
	public LeftBehindResidentInfo getLb() {
		return lb;
	}
	public void setLb(LeftBehindResidentInfo lb) {
		this.lb = lb;
	}
	public KeyTeenagerInfo getK() {
		return k;
	}
	public void setK(KeyTeenagerInfo k) {
		this.k = k;
	}
	public DisabledPeopleInfo getD() {
		return d;
	}
	public void setD(DisabledPeopleInfo d) {
		this.d = d;
	}
	public LivingAloneAgedInfo getL() {
		return l;
	}
	public void setL(LivingAloneAgedInfo l) {
		this.l = l;
	}
	public SubsistenceAllowanceInfo getS() {
		return s;
	}
	public void setS(SubsistenceAllowanceInfo s) {
		this.s = s;
	}
	
}
