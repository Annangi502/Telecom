package com.sp.master;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class ContactDetail extends ApspdclMaster {

	public ContactDetail(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new ContactDetailForm("formarea"));
	}

}
