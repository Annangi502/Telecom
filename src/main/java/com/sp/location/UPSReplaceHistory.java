package com.sp.location;

import java.io.Serializable;

public class UPSReplaceHistory implements Serializable {
private String rmake;
private String rmodel;
private String rserialnumber;
private int noofbatteries;
private String status;
public UPSReplaceHistory() {
	// TODO Auto-generated constructor stub
}
public UPSReplaceHistory(String rmake, String rmodel, String rserialnumber,int noofbatteries,String status) {
	super();
	this.rmake = rmake;
	this.rmodel = rmodel;
	this.rserialnumber = rserialnumber;
	this.noofbatteries = noofbatteries;
	this.status = status;
}
public String getRmake() {
	return rmake;
}
public void setRmake(String rmake) {
	this.rmake = rmake;
}
public String getRmodel() {
	return rmodel;
}
public void setRmodel(String rmodel) {
	this.rmodel = rmodel;
}
public String getRserialnumber() {
	return rserialnumber;
}
public void setRserialnumber(String rserialnumber) {
	this.rserialnumber = rserialnumber;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public int getNoofbatteries() {
	return noofbatteries;
}
public void setNoofbatteries(int noofbatteries) {
	this.noofbatteries = noofbatteries;
}


}
