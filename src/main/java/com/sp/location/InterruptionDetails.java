package com.sp.location;

import java.io.Serializable;

public class InterruptionDetails implements Serializable {
	
	private String circuitid;
	private String locationname ;
	private String vendorname ;
	private String linkuptime ;
	private String linkdowntime ;
	private String calllog ;
	private long hours ;
	
	
	
	public InterruptionDetails(String circuitid, String locationname, String vendorname,
			String linkdowntime, String linkuptime, String calllog) {
		super();
		this.circuitid = circuitid;
		this.locationname = locationname;
		this.vendorname = vendorname;
		this.linkuptime = linkuptime;
		this.linkdowntime = linkdowntime;
		this.calllog = calllog;
	}
	
	public InterruptionDetails(String circuitid, String locationname, String vendorname,
			String linkdowntime, String linkuptime, String calllog,long hours) {
		super();
		this.circuitid = circuitid;
		this.locationname = locationname;
		this.vendorname = vendorname;
		this.linkuptime = linkuptime;
		this.linkdowntime = linkdowntime;
		this.calllog = calllog;
		this.hours = Math.abs(hours) ;
	}
	
	public String getCircuitid() {
		return circuitid;
	}
	public void setCircuitid(String circuitid) {
		this.circuitid = circuitid;
	}
	public String getLocationname() {
		return locationname;
	}
	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public String getLinkuptime() {
		return linkuptime;
	}
	public void setLinkuptime(String linkuptime) {
		this.linkuptime = linkuptime;
	}
	public String getLinkdowntime() {
		return linkdowntime;
	}
	public void setLinkdowntime(String linkdowntime) {
		this.linkdowntime = linkdowntime;
	}
	public String getCalllog() {
		return calllog;
	}
	public void setCalllog(String calllog) {
		this.calllog = calllog;
	}

	public long getHours() {
		return hours;
	}

	public void setHours(long hours) {
		this.hours = hours;
	}
	

}
