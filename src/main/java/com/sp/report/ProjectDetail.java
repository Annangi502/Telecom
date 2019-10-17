package com.sp.report;

import java.io.Serializable;

public class ProjectDetail implements Serializable {
	private int projecttypeid;
	private String projectdesc;
	private int count;

	public ProjectDetail() {
		// TODO Auto-generated constructor stub
	}

	public ProjectDetail(int projecttypeid, String projectdesc, int count) {
		super();
		this.projecttypeid = projecttypeid;
		this.projectdesc = projectdesc;
		this.count = count;
	}

	public int getProjecttypeid() {
		return projecttypeid;
	}

	public void setProjecttypeid(int projecttypeid) {
		this.projecttypeid = projecttypeid;
	}

	public String getProjectdesc() {
		return projectdesc;
	}

	public void setProjectdesc(String projectdesc) {
		this.projectdesc = projectdesc;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
