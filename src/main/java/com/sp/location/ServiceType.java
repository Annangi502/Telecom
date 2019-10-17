package com.sp.location;

import java.io.Serializable;

public class ServiceType implements Serializable {
	private int servicetypeid;
	private String servicetypedesc;

	public ServiceType() {
		// TODO Auto-generated constructor stub
	}

	public ServiceType(int servicetypeid, String servicetypedesc) {
		super();
		this.servicetypeid = servicetypeid;
		this.servicetypedesc = servicetypedesc;
	}

	public int getServicetypeid() {
		return servicetypeid;
	}

	public void setServicetypeid(int servicetypeid) {
		this.servicetypeid = servicetypeid;
	}

	public String getServicetypedesc() {
		return servicetypedesc;
	}

	public void setServicetypedesc(String servicetypedesc) {
		this.servicetypedesc = servicetypedesc;
	}

}
