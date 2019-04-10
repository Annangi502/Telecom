package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class CustomReport extends ApspdclMaster{

	public CustomReport(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new CustomReportForm("formarea"));
	}

}
