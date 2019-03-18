package com.sp.location;

import java.sql.CallableStatement;
import java.sql.Connection;
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

public class AddInterfaceDetailForm extends Panel {
	private static final Logger log = Logger.getLogger(AddInterfaceDetailForm.class);
	private String spcircuitid;
	private String spciruitcode;
	private String projecttypedescription;
	private String equipment;
	private String equipmentfeedback;
	private String ntinterface;
	private String ntinterfacefeedback;
	private String ipaddress;
	private String ipaddressfeedback;
	private String subnetmask;
	private String subnetmaskfeedback;
	private String vendor;
	private String vendorfeedback;
	private String remark;
	private String remarkfeedback;

	public AddInterfaceDetailForm(String id, final IModel<NetworkLocationDetail> nldmodel) {
		super(id);
		// TODO Auto-generated constructor stub

		setDefaultModel(new CompoundPropertyModel<AddInterfaceDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("addnetworkinterfaceform");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[] { FeedbackMessage.ERROR };
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));

		spcircuitid = nldmodel.getObject().getSpcircuitid();
		spciruitcode = nldmodel.getObject().getSpciruitcode();
		projecttypedescription = nldmodel.getObject().getProjecttypedescription();

		TextField<String> equipment = new TextField<String>("equipment");
		equipment.setRequired(true).setLabel(new Model("Make"));
		equipment.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		equipment.add(new StringValidator());
		final FeedbackLabel equipmentFeedbackLabel = new FeedbackLabel("equipmentfeedback", equipment);
		equipmentFeedbackLabel.setOutputMarkupId(true);
		form.add(equipmentFeedbackLabel);

		TextField<String> ntinterface = new TextField<String>("ntinterface");
		ntinterface.setRequired(true).setLabel(new Model("Interface"));
		ntinterface.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* ntinterface.add(new StringValidator()); */
		final FeedbackLabel ntinterfaceFeedbackLabel = new FeedbackLabel("ntinterfacefeedback", ntinterface);
		ntinterfaceFeedbackLabel.setOutputMarkupId(true);
		form.add(ntinterfaceFeedbackLabel);

		TextField<String> ipaddress = new TextField<String>("ipaddress");
		ipaddress.setRequired(true).setLabel(new Model("Ip Address"));
		ipaddress.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* ipaddress.add(new StringValidator()); */
		final FeedbackLabel ipaddressFeedbackLabel = new FeedbackLabel("ipaddressfeedback", ntinterface);
		ipaddressFeedbackLabel.setOutputMarkupId(true);
		form.add(ipaddressFeedbackLabel);

		TextField<String> subnetmask = new TextField<String>("subnetmask");
		subnetmask.setRequired(true).setLabel(new Model("SubNet Mask"));
		subnetmask.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* ipaddress.add(new StringValidator()); */
		final FeedbackLabel subnetmaskFeedbackLabel = new FeedbackLabel("subnetmaskfeedback", subnetmask);
		subnetmaskFeedbackLabel.setOutputMarkupId(true);
		form.add(subnetmaskFeedbackLabel);

		TextField<String> vendor = new TextField<String>("vendor");
		vendor.setRequired(true).setLabel(new Model("Vendor"));
		vendor.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* ipaddress.add(new StringValidator()); */
		final FeedbackLabel vendorFeedbackLabel = new FeedbackLabel("vendorfeedback", vendor);
		vendorFeedbackLabel.setOutputMarkupId(true);
		form.add(vendorFeedbackLabel);

		TextField<String> remark = new TextField<String>("remark");
		remark.setLabel(new Model("Remark"));
		remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 64));
		/* remark.add(new StringValidator()); */
		final FeedbackLabel remarkFeedbackLabel = new FeedbackLabel("remarkfeedback", remark);
		remarkFeedbackLabel.setOutputMarkupId(true);
		form.add(remarkFeedbackLabel);

		Button btncancel = new Button("btncancel") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				PageParameters parms = new PageParameters();
				ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
				setResponsePage(av);
			}
		}.setDefaultFormProcessing(false);

		Button btnreset = new Button("btnreset") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				PageParameters parms = new PageParameters();
				AddUPSDetail av = new AddUPSDetail(parms, nldmodel);
				setResponsePage(av);
			}
		}.setDefaultFormProcessing(false);

		Button btnsubmit = new Button("btnsubmit") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if (addNetworkInterfaceDetail()) {
					PageParameters parms = new PageParameters();
					ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
					setResponsePage(av);
				}
			}
		}.setDefaultFormProcessing(true);

		form.add(new Label("spciruitcode"));
		form.add(new Label("projecttypedescription"));
		form.add(equipment);
		form.add(ntinterface);
		form.add(ipaddress);
		form.add(subnetmask);
		form.add(vendor);
		form.add(remark);
		form.add(feedback);
		form.add(btncancel);
		form.add(btnreset);
		form.add(btnsubmit);
		add(form);

	}

	private boolean addNetworkInterfaceDetail() {
		String query = "{call sp_circuit_add_network_interface_details(?,?,?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setString(3, spcircuitid);
			stmt.setString(4, equipment);
			stmt.setString(5, ntinterface);
			stmt.setString(6, ipaddress);
			stmt.setString(7, subnetmask);
			stmt.setString(8, vendor);
			stmt.setString(9, remark);
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			rs = stmt.executeQuery();
			while (rs.next()) {
				log.info("Network Interface Detail Added Successfully With ID :" + rs.getInt(1));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in addNetworkInterfaceDetail() method {" + e.getMessage() + "}");
			e.printStackTrace();
			return false;
		} finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e2) {
               log.error("SQL Exception in addNetworkInterfaceDetail() method {" + e2.getMessage() + "}");
                e2.printStackTrace();
            }
        }
		return true;
	}

}
