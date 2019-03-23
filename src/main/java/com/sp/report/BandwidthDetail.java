package com.sp.report;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class BandwidthDetail implements Serializable{
private int bandwidthid;
private String bandwidthdesc;
private List<VendorBandwidthDetail> vendors;
private int total;
public BandwidthDetail() {
	// TODO Auto-generated constructor stub
}
public BandwidthDetail(int bandwidthid, String bandwidthdesc, List<VendorBandwidthDetail> vendors) {
	super();
	this.bandwidthid = bandwidthid;
	this.bandwidthdesc = bandwidthdesc;
	this.vendors = vendors;
	Iterator<VendorBandwidthDetail> itr = vendors.iterator();
	while(itr.hasNext())
	{
		total = total+itr.next().getCount();
	}
}
public int getBandwidthid() {
	return bandwidthid;
}
public void setBandwidthid(int bandwidthid) {
	this.bandwidthid = bandwidthid;
}
public String getBandwidthdesc() {
	return bandwidthdesc;
}
public void setBandwidthdesc(String bandwidthdesc) {
	this.bandwidthdesc = bandwidthdesc;
}
public List<VendorBandwidthDetail> getVendors() {
	return vendors;
}
public void setVendors(List<VendorBandwidthDetail> vendors) {
	this.vendors = vendors;
}
public int getTotal() {
	return total;
}

}
