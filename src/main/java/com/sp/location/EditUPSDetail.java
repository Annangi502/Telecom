package com.sp.location;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class EditUPSDetail extends ApspdclMaster {

	public EditUPSDetail(PageParameters parms, final IModel upsmodel, final IModel model) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new EditUPSDetailForm("formarea", upsmodel, model));
	}

}
