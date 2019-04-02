package com.sp.location;

import java.io.Serializable;

public class Category implements Serializable{
private int catid;
private String catdesc;
public Category() {
	// TODO Auto-generated constructor stub
}
public Category(int catid, String catdesc) {
	super();
	this.catid = catid;
	this.catdesc = catdesc;
}
public int getCatid() {
	return catid;
}
public void setCatid(int catid) {
	this.catid = catid;
}
public String getCatdesc() {
	return catdesc;
}
public void setCatdesc(String catdesc) {
	this.catdesc = catdesc;
}

}
