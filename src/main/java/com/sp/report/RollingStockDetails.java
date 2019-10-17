package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class RollingStockDetails extends ApspdclMaster {

	public RollingStockDetails(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new RollingStockDetailsForm("formarea"));
	}

}