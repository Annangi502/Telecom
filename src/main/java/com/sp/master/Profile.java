package com.sp.master;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.location.AddEquipmentDetailForm;

public class Profile extends ApspdclMaster {

	public Profile(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new ProfileForm("formarea"));
	}

}
