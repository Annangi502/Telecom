package com.sp.master;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import com.sp.SPNetworkLocation.PortSession;

public class ProfileForm extends Panel{

	public ProfileForm(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		add(new Label("wname", ((PortSession) getSession()).getEmployeename()));
		add(new Label("lastlogin", ((PortSession) getSession()).getLastlogintime()));
	}



}
