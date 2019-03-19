package com.sp.location;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class Report extends ApspdclMaster {

	public Report(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new ReportForm("formarea"));
	}

}
