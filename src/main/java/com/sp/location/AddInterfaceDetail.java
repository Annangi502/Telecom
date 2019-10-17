package com.sp.location;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class AddInterfaceDetail extends ApspdclMaster {

	public AddInterfaceDetail(PageParameters parms, final IModel model) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new AddInterfaceDetailForm("formarea", model));
	}

}
