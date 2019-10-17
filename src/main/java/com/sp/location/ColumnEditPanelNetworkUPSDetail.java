
package com.sp.location;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ColumnEditPanelNetworkUPSDetail extends Panel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	private Link lnk;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ColumnEditPanelNetworkUPSDetail(String id, final IModel rowModel, IModel labelModel, final IModel nmodel) {
		super(id);
		lnk = new Link("link", rowModel) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				PageParameters params = new PageParameters();
				EditUPSDetail vad;

				vad = new EditUPSDetail(params, rowModel, nmodel);
				setResponsePage(vad);

			}
		};
		add(lnk);
	}
}
