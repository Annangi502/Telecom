package com.sp.location;


import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class TextFieldColumn extends Panel{

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TextFieldColumn(String id, final IModel rowModel) {
		super(id);
		add(new TextField("amt",rowModel));
	}
}