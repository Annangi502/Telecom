package com.sp.location;

import java.io.Serializable;

public class NetworkEquipmentDetail implements Serializable {
	private int equipmentid;
	private String make;
	private String model;
	private String serialnumber;
	private String amc;
	private String remark;
	public NetworkEquipmentDetail() {
		// TODO Auto-generated constructor stub
	}
	public NetworkEquipmentDetail(int equipmentid, String make, String model,
			String serialnumber, String amc, String remark) {
		super();
		this.equipmentid = equipmentid;
		this.make = make;
		this.model = model;
		this.serialnumber = serialnumber;
		this.amc = amc;
		this.remark = remark;
	}
	public int getEquipmentid() {
		return equipmentid;
	}
	public void setEquipmentid(int equipmentid) {
		this.equipmentid = equipmentid;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getAmc() {
		return amc;
	}
	public void setAmc(String amc) {
		this.amc = amc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
