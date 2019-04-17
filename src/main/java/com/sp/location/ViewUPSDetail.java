package com.sp.location;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class ViewUPSDetail extends ApspdclMaster {

	public ViewUPSDetail(PageParameters parms,final IModel model,final IModel nldmodel) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new ViewUPSDetailForm("formarea",model,nldmodel));
	}

}
