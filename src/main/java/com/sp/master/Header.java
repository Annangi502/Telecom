package com.sp.master;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.sp.SPNetworkLocation.PortSession;

public class Header extends Panel {


	private static final long serialVersionUID = 1L;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Header(String id)
	{
		super(id);
		//add(new BookmarkablePageLink("logout",OLLogin.class));
		add(new Label("name", ((PortSession) getSession()).getEmployeename()));
	}

}
