package com.argility.dao.entity;


// THIS IS AN AUTOGENERATED CLASS
public class CashTillEntity {

	public String usrId;
	public Integer ctillLstCsh;
	public Double ctillFloat;
	public Double ctillAmt;
	public Double ctillMendAmt;
	public Integer ctillLstEos;
	public Integer ctillPrevEos;

	public CashTillEntity() {};

	public CashTillEntity(String usrId) {
		this.usrId = usrId;
	}

	public CashTillEntity(String usrId, Integer ctillLstCsh, Double ctillFloat, 
				Double ctillAmt, Double ctillMendAmt, Integer ctillLstEos, 
				Integer ctillPrevEos) {
		this.usrId = usrId;
		this.ctillLstCsh = ctillLstCsh;
		this.ctillFloat = ctillFloat;
		this.ctillAmt = ctillAmt;
		this.ctillMendAmt = ctillMendAmt;
		this.ctillLstEos = ctillLstEos;
		this.ctillPrevEos = ctillPrevEos;
	}


	public String getUsrId() {
		return this.usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public Integer getCtillLstCsh() {
		return this.ctillLstCsh;
	}

	public void setCtillLstCsh(Integer ctillLstCsh) {
		this.ctillLstCsh = ctillLstCsh;
	}

	public Double getCtillFloat() {
		return this.ctillFloat;
	}

	public void setCtillFloat(Double ctillFloat) {
		this.ctillFloat = ctillFloat;
	}

	public Double getCtillAmt() {
		return this.ctillAmt;
	}

	public void setCtillAmt(Double ctillAmt) {
		this.ctillAmt = ctillAmt;
	}

	public Double getCtillMendAmt() {
		return this.ctillMendAmt;
	}

	public void setCtillMendAmt(Double ctillMendAmt) {
		this.ctillMendAmt = ctillMendAmt;
	}

	public Integer getCtillLstEos() {
		return this.ctillLstEos;
	}

	public void setCtillLstEos(Integer ctillLstEos) {
		this.ctillLstEos = ctillLstEos;
	}

	public Integer getCtillPrevEos() {
		return this.ctillPrevEos;
	}

	public void setCtillPrevEos(Integer ctillPrevEos) {
		this.ctillPrevEos = ctillPrevEos;
	}


}