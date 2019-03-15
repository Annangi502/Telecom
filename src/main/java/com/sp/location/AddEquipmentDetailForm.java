package com.sp.location;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;
import com.sp.validators.StringValidator;

public class AddEquipmentDetailForm extends Panel{
	private static final Logger log = Logger.getLogger(AddEquipmentDetailForm.class);
	private String spcircuitid;
	private String projecttypedescription;
	private String make;
	private String makefeedback;
	private String model;
	private String modelfeedback;
	private String serialno;
	private String serialnofeedback;
	private String amc;
	private String amcfeedback;
	private String remark;
    private String remarkfeedback;
	public AddEquipmentDetailForm(String id,final IModel<NetworkLocationDetail> nldmodel) {
		super(id);
		// TODO Auto-generated constructor stub
		
		setDefaultModel(new CompoundPropertyModel<AddEquipmentDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("addeqpdetailform");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[]{FeedbackMessage.ERROR};
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));
		
		spcircuitid = nldmodel.getObject().getSpcircuitid();
		projecttypedescription = nldmodel.getObject().getProjecttypedescription();
		
		TextField<String> make = new TextField<String>("make");
		make.setRequired(true).setLabel(new Model("Make"));
		make.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		make.add(new StringValidator());
		final FeedbackLabel makeFeedbackLabel = new FeedbackLabel("makefeedback", make);
		makeFeedbackLabel.setOutputMarkupId(true);
		form.add(makeFeedbackLabel);
		
		TextField<String> model = new TextField<String>("model");
		model.setRequired(true).setLabel(new Model("Model"));
		model.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		/*model.add(new StringValidator());*/
		final FeedbackLabel modelFeedbackLabel = new FeedbackLabel("modelfeedback", model);
		modelFeedbackLabel.setOutputMarkupId(true);
		form.add(modelFeedbackLabel);
		
		
		TextField<String> serialno = new TextField<String>("serialno");
		serialno.setRequired(true).setLabel(new Model("Serial No."));
		serialno.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		/*model.add(new StringValidator());*/
		final FeedbackLabel serialnoFeedbackLabel = new FeedbackLabel("serialnofeedback", serialno);
		serialnoFeedbackLabel.setOutputMarkupId(true);
		form.add(serialnoFeedbackLabel);
		
		
		TextField<String> amc = new TextField<String>("amc");
		amc.setRequired(true).setLabel(new Model("Serial No."));
		amc.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		amc.add(new StringValidator());
		final FeedbackLabel amcFeedbackLabel = new FeedbackLabel("amcfeedback", amc);
		amcFeedbackLabel.setOutputMarkupId(true);
		form.add(amcFeedbackLabel);
		
		TextField<String> remark = new TextField<String>("remark");
		remark.setLabel(new Model("Remark"));
		remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 64));
		/*remark.add(new StringValidator());*/
		final FeedbackLabel remarkFeedbackLabel = new FeedbackLabel("remarkfeedback", remark);
		remarkFeedbackLabel.setOutputMarkupId(true);
		form.add(remarkFeedbackLabel);
		
		Button btncancel = new Button("btncancel")
		{
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				PageParameters parms = new PageParameters();
				ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
				setResponsePage(av);
			}
		}.setDefaultFormProcessing(false);
		
		Button btnreset = new Button("btnreset")
		{
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				PageParameters parms = new PageParameters();
				AddEquipmentDetail av = new AddEquipmentDetail(parms, nldmodel);
				setResponsePage(av);
			}
		}.setDefaultFormProcessing(false);
		
		Button btnsubmit = new Button("btnsubmit")
		{
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if(addNetworkEquipmentDetail()){
					PageParameters parms = new PageParameters();
					ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
					setResponsePage(av);
				}
			}
		}.setDefaultFormProcessing(true);
		form.add(new Label("spcircuitid"));
		form.add(new Label("projecttypedescription"));
		form.add(make);
		form.add(model);
		form.add(serialno);
		form.add(amc);
		form.add(remark);
		form.add(feedback);
		form.add(btncancel);
		form.add(btnreset);
		form.add(btnsubmit);
		add(form);
	}
	
	private boolean addNetworkEquipmentDetail()
	{
		String query = "{call sp_circuit_add_network_equipment_details(?,?,?,?,?,?,?,?)}";
		try {
			CallableStatement stmt = new DataBaseConnection().getConnection().prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setString(3,spcircuitid);
			stmt.setString(4, make);
			stmt.setString(5, model);
			stmt.setString(6, serialno);
			stmt.setString(7,amc);
			stmt.setString(8,remark);
			log.info("Executing Stored Procedure { "+stmt.toString()+" }");
			
		    ResultSet rs = stmt.executeQuery();
		    while(rs.next())
		    {
		    	log.info("Network Equipment Detail Added Successfully With ID :"+rs.getInt(1));
		    }
		}catch (SQLException e) {
			log.error("SQL Exception in addNetworkEquipmentDetail() method {"+e.getMessage()+"}");
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
