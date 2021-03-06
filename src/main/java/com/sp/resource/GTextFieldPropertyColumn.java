package com.sp.resource;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;

@SuppressWarnings("rawtypes")
public class GTextFieldPropertyColumn extends PropertyColumn{

	private static final long serialVersionUID = 1L;
	private String property;
	
	@SuppressWarnings({ "unchecked" })
	public GTextFieldPropertyColumn(IModel displayModel,Object sortProperty, String propertyExpression) {
		super(displayModel, sortProperty, propertyExpression);
		this.property=propertyExpression;
	}

	@SuppressWarnings("unchecked")
	public GTextFieldPropertyColumn(IModel displayModel,String propertyExpression) {
		super(displayModel, propertyExpression);
		this.property=propertyExpression;
	}
	
	public String getProperty(){
		return this.property;
	}
}
