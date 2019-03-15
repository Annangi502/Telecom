package com.sp.location;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class AddNetworkLocationDetail extends ApspdclMaster {

	public AddNetworkLocationDetail(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new AddNetworkLocationDetailForm("formarea"));
	}

}
