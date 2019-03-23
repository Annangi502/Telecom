package com.sp.master;



import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import com.sp.SPNetworkLocation.PortSession;

public class FormArea extends Panel {

	
	private static final long serialVersionUID = 1L;

	public FormArea(String id){
		super(id);
		add(new Label("wname", ((PortSession) getSession()).getEmployeename()));
		add(new Label("lastlogin", ((PortSession) getSession()).getLastlogintime()));
		add(new Label("designation", ((PortSession) getSession()).getDesignation()));
		add(new Label("wing", ((PortSession) getSession()).getWing()));
	}
}
