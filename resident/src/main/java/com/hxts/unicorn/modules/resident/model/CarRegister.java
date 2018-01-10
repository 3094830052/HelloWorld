package com.hxts.unicorn.modules.resident.model;

public class CarRegister {
	
	private Integer id;

	private String carNumber;

	private String ownerName;

	private String ownerTel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerTel() {
		return ownerTel;
	}

	public void setOwnTel(String ownerTel) {
		this.ownerTel = ownerTel;
	}
}
