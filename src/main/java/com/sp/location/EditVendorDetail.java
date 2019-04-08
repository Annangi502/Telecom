package com.sp.location;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;
public class EditVendorDetail extends ApspdclMaster {

	public EditVendorDetail(PageParameters parms,final IModel model,final IModel nmodel) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new EditVendorDetailForm("formarea",model,nmodel));
	}

}
