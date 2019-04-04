
package com.sp.location;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class VendorDetailColumn extends Panel{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	private Link lnk;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public VendorDetailColumn(String id, final IModel rowModel,IModel labelModel ) {
		super(id);
		NetworkInterfaceDetail ndl = (NetworkInterfaceDetail) rowModel.getObject();
		add(new Label("interface",ndl.getNtinterface()));
		add(new Label("ipaddress",ndl.getIpaddress()));
		add(new Label("subnet",ndl.getSubnetmask()));

		
	}
}
