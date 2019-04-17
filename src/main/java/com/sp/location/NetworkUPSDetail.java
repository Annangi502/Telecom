package com.sp.location;

import java.io.Serializable;

public class NetworkUPSDetail implements Serializable {
	private int upsid;
	private String make;
	private String model;
	private String serialnumber;
	private int noofbatteries;
	private String amc;
	private String remark;
	private int isreplace;
	public NetworkUPSDetail() {
		// TODO Auto-generated constructor stub
	}
	public NetworkUPSDetail(int equipmentid, String make, String model,
			String serialnumber, int noofbatteries, String amc, String remark,int isreplace) {
		super();
		this.upsid = equipmentid;
		this.make = make;
		this.model = model;
		this.serialnumber = serialnumber;
		this.noofbatteries = noofbatteries;
		this.amc = amc;
		this.remark = remark;
		this.isreplace = isreplace;
	}
	public int getUpsid() {
		return upsid;
	}
	public void setUpsid(int equipmentid) {
		this.upsid = equipmentid;
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
	public int getNoofbatteries() {
		return noofbatteries;
	}
	public void setNoofbatteries(int noofbatteries) {
		this.noofbatteries = noofbatteries;
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
	public int getIsreplace() {
		return isreplace;
	}
	public void setIsreplace(int isreplace) {
		this.isreplace = isreplace;
	}
	
}
