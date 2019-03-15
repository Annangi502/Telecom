package com.sp.location;

import java.io.Serializable;

public class NetworkVendorDetail implements Serializable{
	private int vendorid;
	private String vendorname;
	private String bandwidth;
	private String servicetype;
	private String vninterface;
	private String circuitid;
	private String remark;
	public NetworkVendorDetail() {
		// TODO Auto-generated constructor stub
	}
	public NetworkVendorDetail(int vendorid, String vendorname,
			String bandwidth, String servicetype,String vninterface, String circuitid,
			String remark) {
		super();
		this.vendorid = vendorid;
		this.vendorname = vendorname;
		this.bandwidth = bandwidth;
		this.servicetype = servicetype;
		this.circuitid = circuitid;
		this.remark = remark;
		this.vninterface = vninterface;
	}
	public int getVendorid() {
		return vendorid;
	}
	public void setVendorid(int vendorid) {
		this.vendorid = vendorid;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getServicetype() {
		return servicetype;
	}
	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}
	public String getCircuitid() {
		return circuitid;
	}
	public void setCircuitid(String circuitid) {
		this.circuitid = circuitid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVninterface() {
		return vninterface;
	}
	public void setVninterface(String vninterface) {
		this.vninterface = vninterface;
	}
	
}
