package com.sp.location;

import java.io.Serializable;

public class NetworkLocationDetail implements Serializable {
private String spcircuitid;
private int projecttypeid;
private String projecttypedescription;
private int noofpointsavailable;
private String installationdate;
private String dateofconnected;
private String officedescription;
private String officecontactno;
private String officeaddress;
private String locationcontactperson;
private String loactioncontactno;
private String remark;
private String townname;
private String phase;
public NetworkLocationDetail() {
	// TODO Auto-generated constructor stub
}


public NetworkLocationDetail(String spcircuitid, int projecttypeid,
		String projecttypedescription, int noofpointsavailable,
		String installationdate, String dateofconnected,
		String officedescription, String officecontactno, String officeaddress,
		String locationcontactperson, String loactioncontactno, String remark, String townname,String phase) {
	super();
	this.spcircuitid = spcircuitid;
	this.projecttypeid = projecttypeid;
	this.projecttypedescription = projecttypedescription;
	this.noofpointsavailable = noofpointsavailable;
	this.installationdate = installationdate;
	this.dateofconnected = dateofconnected;
	this.officedescription = officedescription;
	this.officecontactno = officecontactno;
	this.officeaddress = officeaddress;
	this.locationcontactperson = locationcontactperson;
	this.loactioncontactno = loactioncontactno;
	this.remark = remark;
	this.townname = townname;
	this.phase = phase;
}


public String getSpcircuitid() {
	return spcircuitid;
}
public void setSpcircuitid(String spcircuitid) {
	this.spcircuitid = spcircuitid;
}
public int getProjecttypeid() {
	return projecttypeid;
}
public void setProjecttypeid(int projecttypeid) {
	this.projecttypeid = projecttypeid;
}
public String getProjecttypedescription() {
	return projecttypedescription;
}
public void setProjecttypedescription(String projecttypedescription) {
	this.projecttypedescription = projecttypedescription;
}
public int getNoofpointsavailable() {
	return noofpointsavailable;
}
public void setNoofpointsavailable(int noofpointsavailable) {
	this.noofpointsavailable = noofpointsavailable;
}
public String getInstallationdate() {
	return installationdate;
}
public void setInstallationdate(String installationdate) {
	this.installationdate = installationdate;
}
public String getDateofconnected() {
	return dateofconnected;
}
public void setDateofconnected(String dateofconnected) {
	this.dateofconnected = dateofconnected;
}
public String getOfficedescription() {
	return officedescription;
}
public void setOfficedescription(String officedescription) {
	this.officedescription = officedescription;
}
public String getOfficecontactno() {
	return officecontactno;
}
public void setOfficecontactno(String officecontactno) {
	this.officecontactno = officecontactno;
}
public String getOfficeaddress() {
	return officeaddress;
}
public void setOfficeaddress(String officeaddress) {
	this.officeaddress = officeaddress;
}
public String getLocationcontactperson() {
	return locationcontactperson;
}
public void setLocationcontactperson(String locationcontactperson) {
	this.locationcontactperson = locationcontactperson;
}
public String getLoactioncontactno() {
	return loactioncontactno;
}
public void setLoactioncontactno(String loactioncontactno) {
	this.loactioncontactno = loactioncontactno;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}


public String getTownname() {
	return townname;
}


public void setTownname(String townname) {
	this.townname = townname;
}


public String getPhase() {
	return phase;
}


public void setPhase(String phase) {
	this.phase = phase;
}


}
