package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class TotalLocationsReport extends ApspdclMaster {

	public TotalLocationsReport(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new TotalLocationsReportForm("formarea"));
	}

}
