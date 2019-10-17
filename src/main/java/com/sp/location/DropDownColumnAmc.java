package com.sp.location;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class DropDownColumnAmc extends Panel{
	List status = Arrays.asList(new String[] { "AMC","Warranty","None"});
	   public String selected = "";
	public DropDownColumnAmc(String id, final IModel rowMode) {
		super(id);
		
		selected = rowMode.getObject().toString() ;
		
		add(new DropDownChoice("amcselection", new PropertyModel(this, "selected"), status));
		// TODO Auto-generated constructor stub
	}

}
