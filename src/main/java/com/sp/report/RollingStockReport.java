package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class RollingStockReport extends ApspdclMaster {

	public RollingStockReport(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new RollingStockReportForm("formarea"));
	}

}