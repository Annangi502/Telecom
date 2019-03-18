package com.sp.master;

import org.apache.wicket.request.mapper.parameter.PageParameters;

public class Dashboard extends ApspdclMaster{

	public Dashboard(PageParameters id) {
		super(id);
		// TODO Auto-generated constructor stub
		replace(new DashboardForm("formarea"));
	}

}
