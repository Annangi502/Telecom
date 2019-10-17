package com.sp.location;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class EditEquipmentDetail extends ApspdclMaster {

	public EditEquipmentDetail(PageParameters parms, final IModel eqmodel, final IModel model) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new EditEquipmentDetailForm("formarea", eqmodel, model));
	}

}
