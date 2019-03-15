package com.sp.location;

import java.io.Serializable;

public class ProjectType implements Serializable{
private int projecttypeid;
private String projecttypedescription;
public ProjectType() {
	// TODO Auto-generated constructor stub
}
public ProjectType(int projecttypeid, String projecttypedescription) {
	super();
	this.projecttypeid = projecttypeid;
	this.projecttypedescription = projecttypedescription;
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

}
