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
import com.sp.master.ApspdclMaster;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;
import com.sp.validators.NumberValidator;
import com.sp.validators.StringValidator;

public class AddVendorDetailForm extends Panel{
	private static final Logger log = Logger.getLogger(AddVendorDetailForm.class);
	private String vendorname;
	private String vendornamefeedback;
	private String bandwidth;
	private String bandwidthfeedback;
	private String servicetype;
	private String servicetypefeedback;
	private String ntinterface;
	private String ntinterfacefeedback;
	private String circuitid;
	private String circuitidfeedback;
	private String remark;
    private String remarkfeedback;
    private String spcircuitid;
	private String projecttypedescription;
	public AddVendorDetailForm(String id,final IModel<NetworkLocationDetail> nldmodel) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<AddVendorDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("addvendorform");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[]{FeedbackMessage.ERROR};
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));
		
		spcircuitid = nldmodel.getObject().getSpcircuitid();
		projecttypedescription = nldmodel.getObject().getProjecttypedescription();
		
		TextField<String> vendorname = new TextField<String>("vendorname");
		vendorname.setRequired(true).setLabel(new Model("Name of Vendor"));
		vendorname.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 120));
		vendorname.add(new StringValidator());
		final FeedbackLabel vendornameFeedbackLabel = new FeedbackLabel("vendornamefeedback", vendorname);
		vendornameFeedbackLabel.setOutputMarkupId(true);
		form.add(vendornameFeedbackLabel);
		
		TextField<String> bandwidth = new TextField<String>("bandwidth");
		bandwidth.setRequired(true).setLabel(new Model("Bandwidth"));
		bandwidth.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 120));
		/*bandwidth.add(new StringValidator());*/
		final FeedbackLabel bandwidthFeedbackLabel = new FeedbackLabel("bandwidthfeedback", bandwidth);
		bandwidthFeedbackLabel.setOutputMarkupId(true);
		form.add(bandwidthFeedbackLabel);
		
		TextField<String> servicetype = new TextField<String>("servicetype");
		servicetype.setRequired(true).setLabel(new Model("Service Type"));
		servicetype.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 120));
		/*servicetype.add(new StringValidator());*/
		final FeedbackLabel servicetypeFeedbackLabel = new FeedbackLabel("servicetypefeedback", servicetype);
		servicetypeFeedbackLabel.setOutputMarkupId(true);
		form.add(servicetypeFeedbackLabel);
		
		
		TextField<String> ntinterface = new TextField<String>("ntinterface");
		ntinterface.setRequired(true).setLabel(new Model("Interface"));
		ntinterface.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 120));
		/*ntinterface.add(new StringValidator());*/
		final FeedbackLabel ntinterfaceFeedbackLabel = new FeedbackLabel("ntinterfacefeedback", ntinterface);
		ntinterfaceFeedbackLabel.setOutputMarkupId(true);
		form.add(ntinterfaceFeedbackLabel);
		
		TextField<String> circuitid = new TextField<String>("circuitid");
		circuitid.setRequired(true).setLabel(new Model("Circuit ID"));
		circuitid.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 10));
		circuitid.add(new NumberValidator());
		final FeedbackLabel circuitidFeedbackLabel = new FeedbackLabel("circuitidfeedback", circuitid);
		circuitidFeedbackLabel.setOutputMarkupId(true);
		form.add(circuitidFeedbackLabel);
		
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
				AddVendorDetail av = new AddVendorDetail(parms, nldmodel);
				setResponsePage(av);
			}
		}.setDefaultFormProcessing(false);
		
		Button btnsubmit = new Button("btnsubmit")
		{
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if(addNetworkVendorDetail()){
					PageParameters parms = new PageParameters();
					ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
					setResponsePage(av);
				}
			}
		}.setDefaultFormProcessing(true);
		
		form.add(new Label("spcircuitid"));
		form.add(new Label("projecttypedescription"));
		form.add(vendorname);
		form.add(bandwidth);
		form.add(servicetype);
		form.add(ntinterface);
		form.add(circuitid);
		form.add(remark);
		form.add(btncancel);
		form.add(btnreset);
		form.add(btnsubmit);
		form.add(feedback);
		add(form);
	}
	private boolean addNetworkVendorDetail()
	{
		String query = "{call sp_circuit_add_network_vendor_details(?,?,?,?,?,?,?,?,?)}";
		try {
			CallableStatement stmt = new DataBaseConnection().getConnection().prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setString(3,spcircuitid);
			stmt.setString(4, vendorname);
			stmt.setString(5, bandwidth);
			stmt.setString(6, servicetype);
			stmt.setString(7,ntinterface);
			stmt.setInt(8,Integer.parseInt(circuitid));
			stmt.setString(9,remark);
			log.info("Executing Stored Procedure { "+stmt.toString()+" }");
			
		    ResultSet rs = stmt.executeQuery();
		    while(rs.next())
		    {
		    	log.info("Network Vendor Detail Added Successfully With ID :"+rs.getInt(1));
		    }
		}catch (SQLException e) {
			log.error("SQL Exception in addNetworkVendorDetail() method {"+e.getMessage()+"}");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
