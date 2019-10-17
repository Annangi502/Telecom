package com.sp.location;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class RadioChoiceColumn extends Panel {
	List status = Arrays.asList(new String[] { "Yes","No" });
	public RadioChoiceColumn(String id, final IModel rowMode) {
		super(id);
		add(new RadioChoice("site",rowMode, status));
		// TODO Auto-generated constructor stub
	}

}
