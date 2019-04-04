package com.sp.location;

import java.io.Serializable;

public class NetworkInterfaceDetail  implements Serializable{

	private int interfaceid;
	private int vendorid;
	private String vendorname;
	private String ntinterface;
	private String ipaddress;
	private String subnetmask;
	private String spntinterface;
	private String spipaddress;
	private String spsubnetmask;
	private String remark;
	public NetworkInterfaceDetail() {
		// TODO Auto-generated constructor stub
	}
	public NetworkInterfaceDetail(int interfaceid, int vendorid, String vendorname, String ntinterface,
			String ipaddress, String subnetmask, String spntinterface, String spipaddress, String spsubnetmask,
			String remark) {
		super();
		this.interfaceid = interfaceid;
		this.vendorid = vendorid;
		this.vendorname = vendorname;
		this.ntinterface = ntinterface;
		this.ipaddress = ipaddress;
		this.subnetmask = subnetmask;
		this.spntinterface = spntinterface;
		this.spipaddress = spipaddress;
		this.spsubnetmask = spsubnetmask;
		this.remark = remark;
	}
	public int getInterfaceid() {
		return interfaceid;
	}
	public void setInterfaceid(int interfaceid) {
		this.interfaceid = interfaceid;
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
	public String getNtinterface() {
		return ntinterface;
	}
	public void setNtinterface(String ntinterface) {
		this.ntinterface = ntinterface;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getSubnetmask() {
		return subnetmask;
	}
	public void setSubnetmask(String subnetmask) {
		this.subnetmask = subnetmask;
	}
	public String getSpntinterface() {
		return spntinterface;
	}
	public void setSpntinterface(String spntinterface) {
		this.spntinterface = spntinterface;
	}
	public String getSpipaddress() {
		return spipaddress;
	}
	public void setSpipaddress(String spipaddress) {
		this.spipaddress = spipaddress;
	}
	public String getSpsubnetmask() {
		return spsubnetmask;
	}
	public void setSpsubnetmask(String spsubnetmask) {
		this.spsubnetmask = spsubnetmask;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
