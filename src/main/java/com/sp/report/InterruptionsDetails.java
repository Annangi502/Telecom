package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class InterruptionsDetails extends ApspdclMaster {

	public InterruptionsDetails(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new InterruptionsReport("formarea"));
	}

}
