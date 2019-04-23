package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class NoNetworkReport extends ApspdclMaster {

	public NoNetworkReport(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new NoNetworkReportForm("formarea"));
	}

}
