package com.sp.location;

import java.io.Serializable;

public class Bandwidth implements Serializable{
private int bandwidthid;
private String bandwidthdesc;
private int bandwidth;
private int bandwidthunittypeid;
private int bandwidthinkilobytes;
public Bandwidth() {
	// TODO Auto-generated constructor stub
}
public Bandwidth(int bandwidthid, String bandwidthdesc, int bandwidth,int bandwidthunittypeid, int bandwidthinkilobytes) {
	super();
	this.bandwidthid = bandwidthid;
	this.bandwidthdesc = bandwidthdesc;
	this.bandwidth = bandwidth;
	this.bandwidthunittypeid = bandwidthunittypeid;
	this.bandwidthinkilobytes = bandwidthinkilobytes;
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
public int getBandwidthunittypeid() {
	return bandwidthunittypeid;
}
public void setBandwidthunittypeid(int bandwidthunittypeid) {
	this.bandwidthunittypeid = bandwidthunittypeid;
}
public int getBandwidthinkilobytes() {
	return bandwidthinkilobytes;
}
public void setBandwidthinkilobytes(int bandwidthinkilobytes) {
	this.bandwidthinkilobytes = bandwidthinkilobytes;
}
public int getBandwidth() {
	return bandwidth;
}
public void setBandwidth(int bandwidth) {
	this.bandwidth = bandwidth;
}

}
