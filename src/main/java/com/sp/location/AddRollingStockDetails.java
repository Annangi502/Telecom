package com.sp.location;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class AddRollingStockDetails extends ApspdclMaster {

	public AddRollingStockDetails(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new AddRollingStockDetailsForm("formarea"));
	}

}