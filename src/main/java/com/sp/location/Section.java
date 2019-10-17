package com.sp.location;

import java.io.*;

public class Section implements Serializable {
	private int sectionid;
	private String sectiondesc;
	private String sectioncode;

	public Section() {
	}

	public Section(final int sectionid, final String sectiondesc, final String sectioncode) {
		this.sectionid = sectionid;
		this.sectiondesc = sectiondesc;
		this.sectioncode = sectioncode;
	}

	public int getSectionid() {
		return this.sectionid;
	}

	public void setSectionid(final int sectionid) {
		this.sectionid = sectionid;
	}

	public String getSectiondesc() {
		return this.sectiondesc;
	}

	public void setSectiondesc(final String sectiondesc) {
		this.sectiondesc = sectiondesc;
	}

	public String getSectioncode() {
		return this.sectioncode;
	}

	public void setSectioncode(final String sectioncode) {
		this.sectioncode = sectioncode;
	}
}