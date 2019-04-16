package com.sp.location;

import java.io.Serializable;

public class EquipmentReplaceHistory implements Serializable {
private String rmake;
private String rmodel;
private String rserialnumber;
public EquipmentReplaceHistory() {
	// TODO Auto-generated constructor stub
}
public EquipmentReplaceHistory(String rmake, String rmodel, String rserialnumber) {
	super();
	this.rmake = rmake;
	this.rmodel = rmodel;
	this.rserialnumber = rserialnumber;
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


}
