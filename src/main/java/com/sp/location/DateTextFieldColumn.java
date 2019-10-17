package com.sp.location;

import java.util.Date;

import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.sp.resource.FeedbackLabel;

public class DateTextFieldColumn extends Panel {
	private static int i;
	public DateTextFieldColumn(String id, IModel<Date> rowmodel) {
		super(id, rowmodel);
		// TODO Auto-generated constructor stub
		CustromDatePicker datePicker = new CustromDatePicker();
		datePicker.setShowOnFieldClick(true);
		datePicker.setAutoHide(false);
		DateTextField instaldate = new DateTextField("installationdate",
				rowmodel, new PatternDateConverter("dd MMM, yyyy", true));
		instaldate.setOutputMarkupId(true);
		instaldate.setMarkupId("installationdate"+(i++));
		instaldate.add(datePicker);
		add(instaldate);
	}

}
