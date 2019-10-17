package com.sp.location;

import java.io.Serializable;

public class ERO implements Serializable{
	private int eroid;
	private String erodes;
	private String erocode;
	
	
	public ERO() {
	}


	public ERO(final int eroid, final String erodes, final String erocode) {
		this.eroid = eroid;
		this.erodes = erodes;
		this.erocode = erocode;
	}


	public int getEroid() {
		return eroid;
	}


	public void setEroid(int eroid) {
		this.eroid = eroid;
	}


	public String getErodes() {
		return erodes;
	}


	public void setErodes(String erodes) {
		this.erodes = erodes;
	}


	public String getErocode() {
		return erocode;
	}


	public void setErocode(String erocode) {
		this.erocode = erocode;
	}
	
	

}
