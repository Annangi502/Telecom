package com.sp.master;

import org.apache.wicket.request.mapper.parameter.PageParameters;

public class NetworkMap extends ApspdclMaster {

	public NetworkMap(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new NetworkMapForm("formarea"));
	}

}
