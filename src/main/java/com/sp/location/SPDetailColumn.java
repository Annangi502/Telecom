
package com.sp.location;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class SPDetailColumn extends Panel{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	private Link lnk;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SPDetailColumn(String id, final IModel rowModel,IModel labelModel ) {
		super(id);
		NetworkInterfaceDetail ndl = (NetworkInterfaceDetail) rowModel.getObject();
		add(new Label("spinterface",ndl.getSpntinterface()));
		add(new Label("spipaddress",ndl.getSpipaddress()));
		add(new Label("spsubnet",ndl.getSpsubnetmask()));
	}
}
