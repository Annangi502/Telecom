package com.sp.location;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;
public class ViewAllNetworkLocation extends ApspdclMaster{

	public ViewAllNetworkLocation(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new ViewAllNetworkLocationForm("formarea"));
	}

}
