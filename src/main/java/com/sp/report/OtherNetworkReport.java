package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class OtherNetworkReport extends ApspdclMaster {

	public OtherNetworkReport(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new OtherNetworkReportForm("formarea"));
	}

}
