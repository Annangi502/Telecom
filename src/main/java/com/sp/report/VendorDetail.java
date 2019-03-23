package com.sp.report;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
public class VendorDetail  implements Serializable {
	private int vendorid;
	private String vendorname;
	private List<ProjectDetail> project;
	private int total ;
	public VendorDetail() {
		// TODO Auto-generated constructor stub
	}
	public VendorDetail(int vendorid, String vendorname) {
		super();
		this.vendorid = vendorid;
		this.vendorname = vendorname;
	}
	public VendorDetail(int vendorid, String vendorname, List<ProjectDetail> project) {
		super();
		this.vendorid = vendorid;
		this.vendorname = vendorname;
		this.project = project;
		Iterator<ProjectDetail> itr = project.iterator();
		while(itr.hasNext())
		{
			total = total+itr.next().getCount();
		}
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
	public List<ProjectDetail> getProject() {
		return project;
	}
	public void setProject(List<ProjectDetail> project) {
		this.project = project;
	}
	public int getTotal(){
		return total;
		
	}
	

}
