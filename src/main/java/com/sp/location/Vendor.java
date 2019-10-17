package com.sp.location;

import java.io.Serializable;

public class Vendor implements Serializable {
	private int vendorid;
	private String vendorname;

	public Vendor() {
		// TODO Auto-generated constructor stub
	}

	public Vendor(int vendorid, String vendorname) {
		super();
		this.vendorid = vendorid;
		this.vendorname = vendorname;
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

}
