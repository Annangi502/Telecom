package com.sp.location;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class AddVendorDetail extends ApspdclMaster {

	public AddVendorDetail(PageParameters parms, final IModel model) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new AddVendorDetailForm("formarea", model));
	}

}
