package com.sp.report;

import java.io.Serializable;

public class VendorBandwidthDetail implements Serializable {
	private int vendorid;
	private String vendorname;
	private int count;

	public VendorBandwidthDetail() {
		// TODO Auto-generated constructor stub
	}

	public VendorBandwidthDetail(int vendorid, String vendorname, int count) {
		super();
		this.vendorid = vendorid;
		this.vendorname = vendorname;
		this.count = count;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
