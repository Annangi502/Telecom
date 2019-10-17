package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class NetworkInterfaceReport extends ApspdclMaster {

	public NetworkInterfaceReport(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new NetworkInterfaceReportForm("formarea"));
	}

}