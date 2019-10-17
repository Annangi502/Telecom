package com.sp.location;

import java.io.Serializable;

public class UnitType implements Serializable {
	private int unittypeid;
	private String unittypedesc;

	public UnitType() {
		// TODO Auto-generated constructor stub
	}

	public UnitType(int unittypeid, String unittypedesc) {
		super();
		this.unittypeid = unittypeid;
		this.unittypedesc = unittypedesc;
	}

	public int getUnittypeid() {
		return unittypeid;
	}

	public void setUnittypeid(int unittypeid) {
		this.unittypeid = unittypeid;
	}

	public String getUnittypedesc() {
		return unittypedesc;
	}

	public void setUnittypedesc(String unittypedesc) {
		this.unittypedesc = unittypedesc;
	}

}
