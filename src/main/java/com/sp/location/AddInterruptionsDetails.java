package com.sp.location;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class AddInterruptionsDetails extends ApspdclMaster {

	public AddInterruptionsDetails(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new AddInterruptionsForm("formarea"));
	}

}
