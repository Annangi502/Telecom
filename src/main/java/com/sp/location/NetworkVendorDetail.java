package com.sp.location;

import java.io.*;

public class NetworkVendorDetail implements Serializable {
	private int vendorid;
	private String vendorname;
	private String bandwidth;
	private String servicetype;
	private String vninterface;
	private String circuitid;
	private String remark;
	private String mediatypedesc;
	private String phase;
	private int vendortypeid;
	private String vendortypedesc;
	private int bandwidthtypeid;
	private String bandwidthtypedesc;
	private int bandwidthunittypeid;
	private String commissionedate;

	public NetworkVendorDetail() {
	}

	public NetworkVendorDetail(int vendorid, String vendorname, String bandwidth, String servicetype,
			String vninterface, String circuitid, String remark, String mediatypedesc, String phase, int vendortypeid,
			String vendortypedesc, int bandwidthtypeid, String bandwidthtypedesc, int bandwidthunittypeid,
			String commissionedate) {
		super();
		this.vendorid = vendorid;
		this.vendorname = vendorname;
		this.bandwidth = bandwidth;
		this.servicetype = servicetype;
		this.vninterface = vninterface;
		this.circuitid = circuitid;
		this.remark = remark;
		this.mediatypedesc = mediatypedesc;
		this.phase = phase;
		this.vendortypeid = vendortypeid;
		this.vendortypedesc = vendortypedesc;
		this.bandwidthtypeid = bandwidthtypeid;
		this.bandwidthtypedesc = bandwidthtypedesc;
		this.bandwidthunittypeid = bandwidthunittypeid;
		this.commissionedate = commissionedate;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public int getVendorid() {
		return this.vendorid;
	}

	public void setVendorid(final int vendorid) {
		this.vendorid = vendorid;
	}

	public String getVendorname() {
		return this.vendorname;
	}

	public void setVendorname(final String vendorname) {
		this.vendorname = vendorname;
	}

	public String getBandwidth() {
		return this.bandwidth;
	}

	public void setBandwidth(final String bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getServicetype() {
		return this.servicetype;
	}

	public void setServicetype(final String servicetype) {
		this.servicetype = servicetype;
	}

	public String getCircuitid() {
		return this.circuitid;
	}

	public void setCircuitid(final String circuitid) {
		this.circuitid = circuitid;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(final String remark) {
		this.remark = remark;
	}

	public String getVninterface() {
		return this.vninterface;
	}

	public void setVninterface(final String vninterface) {
		this.vninterface = vninterface;
	}

	public String getMediatypedesc() {
		return this.mediatypedesc;
	}

	public void setMediatypedesc(final String mediatypedesc) {
		this.mediatypedesc = mediatypedesc;
	}

	public int getVendortypeid() {
		return vendortypeid;
	}

	public void setVendortypeid(int vendortypeid) {
		this.vendortypeid = vendortypeid;
	}

	public String getVendortypedesc() {
		return vendortypedesc;
	}

	public void setVendortypedesc(String vendortypedesc) {
		this.vendortypedesc = vendortypedesc;
	}

	public int getBandwidthtypeid() {
		return bandwidthtypeid;
	}

	public void setBandwidthtypeid(int bandwidthtypeid) {
		this.bandwidthtypeid = bandwidthtypeid;
	}

	public String getBandwidthtypedesc() {
		return bandwidthtypedesc;
	}

	public void setBandwidthtypedesc(String bandwidthtypedesc) {
		this.bandwidthtypedesc = bandwidthtypedesc;
	}

	public int getBandwidthunittypeid() {
		return bandwidthunittypeid;
	}

	public void setBandwidthunittypeid(int bandwidthunittypeid) {
		this.bandwidthunittypeid = bandwidthunittypeid;
	}

	public String getCommissionedate() {
		return commissionedate;
	}

	public void setCommissionedate(String commissionedate) {
		this.commissionedate = commissionedate;
	}

}