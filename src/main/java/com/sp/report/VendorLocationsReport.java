package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class VendorLocationsReport extends ApspdclMaster {

	public VendorLocationsReport(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new VendorLocationsReportForm("formarea"));
	}

}
