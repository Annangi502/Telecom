
package com.sp.location;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ReplaceDetail extends Panel{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	private Link lnk;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ReplaceDetail(String id, final IModel rowModel,IModel labelModel ) {
		super(id);
		NetworkEquipmentDetail ndl = (NetworkEquipmentDetail) rowModel.getObject();
		WebMarkupContainer mydiv = new WebMarkupContainer("replacedivision");
		mydiv.add(new Label("rmake",ndl.getRmake()));
		mydiv.add(new Label("rmodel",ndl.getModel()));
		mydiv.add(new Label("rserial",ndl.getRserialnumber()));
		Label lab = new Label("replbl",ndl.getIsreplace()==1?"Replace":"Stand By");
		add(lab);
		add(mydiv);
		if(ndl.getIsreplace()==1){
			mydiv.setVisible(true);
			lab.setVisible(false);
		}else
		{
			mydiv.setVisible(false);
			lab.setVisible(true);
		}
		/*lnk = new Link("link",rowModel){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				PageParameters params = new PageParameters();
				ViewNetworkLocationDetail vad;
				
				vad = new ViewNetworkLocationDetail(params,rowModel);
					setResponsePage(vad);
				
				
			}
		};
		lnk.add(new AttributeModifier("style", "text-decoration:none"));
		lnk.add(new Label("accountchartname",labelModel));
		add(lnk);*/
	}
}
