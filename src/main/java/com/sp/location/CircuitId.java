package com.sp.location;

import java.io.Serializable;

public class CircuitId  implements Serializable {
	private String circuitid;
	private String circuitiddesc ;
	private String locationname ;
	private String vendor ;
	private int circleid;
	private int divisionid;
	private int eroid ;
	private int subdivisionid ;
	private int sectionid ;
	
	public CircuitId(String circuitid, String locationname,String vendor) {
		super();
		this.circuitid = circuitid;
		this.locationname = locationname;
		this.vendor = vendor;
	}
	
	public CircuitId(String circuitid, String locationname,String vendor,int circleid,int divisionid,int eroid,int subdivisionid,int sectionid) {
		super();
		this.circuitid = circuitid;
		this.locationname = locationname;
		this.vendor = vendor;
		this.circleid = circleid;
		this.divisionid = divisionid;
		this.eroid = eroid ;
		this.subdivisionid = subdivisionid ;
		this.sectionid = sectionid ;
	}

	public CircuitId(){
		
		
	}
	
	public CircuitId( String circuitiddesc) {
		super();
		this.circuitiddesc = circuitiddesc;
	}
	
/*	public CircuitId( String locationname,String vendor) {
		super();
		this.locationname = locationname;
		this.vendor = vendor;
	}*/
	
	
	
	public String getCircuitid() {
		return circuitid;
	}
	public void setCircuitid(String circuitid) {
		this.circuitid = circuitid;
	}
	public String getCircuitiddesc() {
		return circuitiddesc;
	}
	public void setCircuitiddesc(String circuitiddesc) {
		this.circuitiddesc = circuitiddesc;
	}

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public int getCircleid() {
		return circleid;
	}

	public void setCircleid(int circleid) {
		this.circleid = circleid;
	}

	public int getDivisionid() {
		return divisionid;
	}

	public void setDivisionid(int divisionid) {
		this.divisionid = divisionid;
	}

	public int getEroid() {
		return eroid;
	}

	public void setEroid(int eroid) {
		this.eroid = eroid;
	}

	public int getSubdivisionid() {
		return subdivisionid;
	}

	public void setSubdivisionid(int subdivisionid) {
		this.subdivisionid = subdivisionid;
	}

	public int getSectionid() {
		return sectionid;
	}

	public void setSectionid(int sectionid) {
		this.sectionid = sectionid;
	}
			
}
