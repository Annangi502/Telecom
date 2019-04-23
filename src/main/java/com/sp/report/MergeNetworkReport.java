package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class MergeNetworkReport extends ApspdclMaster {

	public MergeNetworkReport(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new MergeNetworkReportForm("formarea"));
	}

}
