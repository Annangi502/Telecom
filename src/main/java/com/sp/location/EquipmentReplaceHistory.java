package com.sp.location;

import java.io.Serializable;

public class EquipmentReplaceHistory implements Serializable {
	private String rmake;
	private String rmodel;
	private String rserialnumber;
	private String status;
	private String replacedate;
	private String remark;

	public EquipmentReplaceHistory(String rmake, String rmodel, String rserialnumber, String status, String replacedate,
			String remark) {
		super();
		this.rmake = rmake;
		this.rmodel = rmodel;
		this.rserialnumber = rserialnumber;
		this.status = status;
		this.replacedate = replacedate;
		this.remark = remark;
	}

	public String getReplacedate() {
		return replacedate;
	}

	public void setReplacedate(String replacedate) {
		this.replacedate = replacedate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public EquipmentReplaceHistory() {
		// TODO Auto-generated constructor stub
	}

	public EquipmentReplaceHistory(String rmake, String rmodel, String rserialnumber, String status) {
		super();
		this.rmake = rmake;
		this.rmodel = rmodel;
		this.rserialnumber = rserialnumber;
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

}
