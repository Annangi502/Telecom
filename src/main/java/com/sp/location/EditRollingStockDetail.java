package com.sp.location;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class EditRollingStockDetail extends ApspdclMaster {

	public EditRollingStockDetail(PageParameters parms, final IModel imodel, final IModel model) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new EditRollingStockDetailForm("formarea", imodel, model));
	}

}