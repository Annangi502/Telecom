package com.sp.location;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class ViewEquipmentDetail extends ApspdclMaster {

	public ViewEquipmentDetail(PageParameters parms, final IModel model, final IModel nldmodel) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new ViewEquipmentDetailForm("formarea", model, nldmodel));
	}

}
