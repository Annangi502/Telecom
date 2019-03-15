package com.sp.location;

import java.io.Serializable;

public class NetworkInterfaceDetail  implements Serializable{

	private int interfaceid;
	private String equipment;
	private String ntinterface;
	private String ipaddress;
	private String subnetmask;
	private String vendor;
	private String remark;
	public NetworkInterfaceDetail() {
		// TODO Auto-generated constructor stub
	}
	public NetworkInterfaceDetail(int interfaceid, String equipment,
			String ntinterface, String ipaddress, String subnetmask,
			String vendor, String remark) {
		super();
		this.interfaceid = interfaceid;
		this.equipment = equipment;
		this.ntinterface = ntinterface;
		this.ipaddress = ipaddress;
		this.subnetmask = subnetmask;
		this.vendor = vendor;
		this.remark = remark;
	}
	public int getInterfaceid() {
		return interfaceid;
	}
	public void setInterfaceid(int interfaceid) {
		this.interfaceid = interfaceid;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
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
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
