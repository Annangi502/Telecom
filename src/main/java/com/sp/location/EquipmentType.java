package com.sp.location;

import java.io.Serializable;

public class EquipmentType implements Serializable {
	private int equipmenttypeid;
	private String equipmenttypedesc;

	public EquipmentType() {
		// TODO Auto-generated constructor stub
	}

	public EquipmentType(int equipmenttypeid, String equipmenttypedesc) {
		super();
		this.equipmenttypeid = equipmenttypeid;
		this.equipmenttypedesc = equipmenttypedesc;
	}

	public int getEquipmenttypeid() {
		return equipmenttypeid;
	}

	public void setEquipmenttypeid(int equipmenttypeid) {
		this.equipmenttypeid = equipmenttypeid;
	}

	public String getEquipmenttypedesc() {
		return equipmenttypedesc;
	}

	public void setEquipmenttypedesc(String equipmenttypedesc) {
		this.equipmenttypedesc = equipmenttypedesc;
	}

}
