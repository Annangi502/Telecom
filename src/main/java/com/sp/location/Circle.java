package com.sp.location;

import java.io.*;

public class Circle implements Serializable {
	private int circleid;
	private String circledes;
	private String circlecode;

	public Circle() {
	}

	public Circle(final int circleid, final String circledes, final String circlecode) {
		this.circleid = circleid;
		this.circledes = circledes;
		this.circlecode = circlecode;
	}

	public int getCircleid() {
		return this.circleid;
	}

	public void setCircleid(final int circleid) {
		this.circleid = circleid;
	}

	public String getCircledes() {
		return this.circledes;
	}

	public void setCircledes(final String circledes) {
		this.circledes = circledes;
	}

	public String getCirclecode() {
		return this.circlecode;
	}

	public void setCirclecode(final String circlecode) {
		this.circlecode = circlecode;
	}
}