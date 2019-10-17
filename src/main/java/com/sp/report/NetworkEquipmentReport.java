package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class NetworkEquipmentReport extends ApspdclMaster {

	public NetworkEquipmentReport(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new NetworkEquipmentReportForm("formarea"));
	}

}