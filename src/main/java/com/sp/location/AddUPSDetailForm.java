package com.sp.location;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
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
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.resource.CustomRadioChoice;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;
import com.sp.validators.NumberValidator;
import com.sp.validators.StringValidator;

public class AddUPSDetailForm extends Panel {
	private static final Logger log = Logger.getLogger(AddEquipmentDetailForm.class);
	private String spcircuitid;
	private String spcircuitcode;
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
	private String batteries;
	private String batteriesfeedback;
	private String replace;
	private String replacefeedback;
	private boolean replacedivflag;
	private String rmake;
	private String rmakefeedback;
	private String rmodel;
	private String rmodelfeedback;
	private String rserialno;
	private String rserialnofeedback;
	private String rbatteries;
	private String rbatteriesfeedback;
	private String locationname;
	private Date installationdate;
	private String installfeedback;
	private Date replacementdate;
	private String replacedatefeedback;
	private static String PATTERN = "yyyy-MM-dd";
	private static final List<String> TYPES = Arrays.asList("AMC", "Warranty", "None");
	private static final List<String> R_TYPES = Arrays.asList("Replace", "Stand By","Existing");

	public AddUPSDetailForm(String id, final IModel<NetworkLocationDetail> nldmodel) {
		super(id);
		// TODO Auto-generated constructor stub
		replace = "Existing";
		setDefaultModel(new CompoundPropertyModel<AddUPSDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("addeqpdetailform");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[] { FeedbackMessage.ERROR };
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));

		spcircuitid = nldmodel.getObject().getSpcircuitid();
		spcircuitcode = nldmodel.getObject().getSpciruitcode();
		projecttypedescription = nldmodel.getObject().getProjecttypedescription();
		locationname = nldmodel.getObject().getLocationname();

		TextField<String> make = new TextField<String>("make");
		make.setRequired(true).setLabel(new Model("Make"));
		make.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* make.add(new StringValidator()); */
		final FeedbackLabel makeFeedbackLabel = new FeedbackLabel("makefeedback", make);
		makeFeedbackLabel.setOutputMarkupId(true);
		form.add(makeFeedbackLabel);

		TextField<String> model = new TextField<String>("model");
		model.setRequired(true).setLabel(new Model("Model"));
		model.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		/* model.add(new StringValidator()); */
		final FeedbackLabel modelFeedbackLabel = new FeedbackLabel("modelfeedback", model);
		modelFeedbackLabel.setOutputMarkupId(true);
		form.add(modelFeedbackLabel);

		TextField<String> serialno = new TextField<String>("serialno");
		serialno.setRequired(true).setLabel(new Model("Serial No."));
		serialno.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		/* model.add(new StringValidator()); */
		final FeedbackLabel serialnoFeedbackLabel = new FeedbackLabel("serialnofeedback", serialno);
		serialnoFeedbackLabel.setOutputMarkupId(true);
		form.add(serialnoFeedbackLabel);

		final CustomRadioChoice<String> amc = new CustomRadioChoice("amc", TYPES);
		amc.setRequired(true).setLabel(new Model("AMC/Warranty"));
		amc.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		amc.add(new StringValidator());
		final FeedbackLabel amcFeedbackLabel = new FeedbackLabel("amcfeedback", amc);
		amcFeedbackLabel.setOutputMarkupId(true);
		form.add(amcFeedbackLabel);

		TextField<String> batteries = new TextField<String>("batteries");
		batteries.setRequired(true).setLabel(new Model("No.Of Batteries"));
		batteries.add(new NumberValidator());
		final FeedbackLabel batteriesFeedbackLabel = new FeedbackLabel("batteriesfeedback", batteries);
		batteriesFeedbackLabel.setOutputMarkupId(true);
		form.add(batteriesFeedbackLabel);

		TextField<String> remark = new TextField<String>("remark");
		remark.setLabel(new Model("Remark"));
		/*
		 * remark.add(org.apache.wicket.validation.validator.StringValidator.
		 * lengthBetween(1, 1028));
		 */
		/* remark.add(new StringValidator()); */
		final FeedbackLabel remarkFeedbackLabel = new FeedbackLabel("remarkfeedback", remark);
		remarkFeedbackLabel.setOutputMarkupId(true);
		form.add(remarkFeedbackLabel);
		final CustomRadioChoice<String> replaceradio = new CustomRadioChoice("replace", R_TYPES) {
			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(Object newSelection) {
				// TODO Auto-generated method stub
				if (replace.equals("Replace")) {
					replacedivflag = true;
				} else {
					replacedivflag = false;
				}
			}
		};
		replaceradio.setRequired(true).setLabel(new Model("Replace/Stand By"));
		final FeedbackLabel replaceFeedbackLabel = new FeedbackLabel("replacefeedback", replaceradio);
		replaceFeedbackLabel.setOutputMarkupId(true);
		form.add(replaceFeedbackLabel);

		WebMarkupContainer repdiv = new WebMarkupContainer("replacediv") {
			@Override
			public boolean isVisible() {
				// TODO Auto-generated method stub
				return replacedivflag;
			}
		};
		TextField<String> rmake = new TextField<String>("rmake");
		rmake.setRequired(true).setLabel(new Model("Make"));
		rmake.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* rmake.add(new StringValidator()); */
		final FeedbackLabel rmakeFeedbackLabel = new FeedbackLabel("rmakefeedback", rmake);
		rmakeFeedbackLabel.setOutputMarkupId(true);
		repdiv.add(rmakeFeedbackLabel);
		repdiv.add(rmake);

		TextField<String> rmodel = new TextField<String>("rmodel");
		rmodel.setRequired(true).setLabel(new Model("Model"));
		rmodel.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		/* model.add(new StringValidator()); */
		final FeedbackLabel rmodelFeedbackLabel = new FeedbackLabel("rmodelfeedback", rmodel);
		rmodelFeedbackLabel.setOutputMarkupId(true);
		repdiv.add(rmodelFeedbackLabel);
		repdiv.add(rmodel);

		TextField<String> rserialno = new TextField<String>("rserialno");
		rserialno.setRequired(true).setLabel(new Model("Serial No."));
		rserialno.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		/* model.add(new StringValidator()); */
		final FeedbackLabel rserialnoFeedbackLabel = new FeedbackLabel("rserialnofeedback", rserialno);
		rserialnoFeedbackLabel.setOutputMarkupId(true);
		repdiv.add(rserialnoFeedbackLabel);
		repdiv.add(rserialno);

		TextField<String> rbatteries = new TextField<String>("rbatteries");
		rbatteries.setRequired(true).setLabel(new Model("No.Of Batteries"));
		rbatteries.add(new NumberValidator());
		final FeedbackLabel rbatteriesFeedbackLabel = new FeedbackLabel("rbatteriesfeedback", rbatteries);
		rbatteriesFeedbackLabel.setOutputMarkupId(true);
		repdiv.add(rbatteriesFeedbackLabel);
		repdiv.add(rbatteries);

		CustromDatePicker datePicker = new CustromDatePicker();
		datePicker.setShowOnFieldClick(true);
		datePicker.setAutoHide(false);

		DateTextField instaldate = new DateTextField("installationdate",
				new PropertyModel<Date>(this, "installationdate"), new PatternDateConverter("dd MMM, yyyy", true));

		/*
		 * DateTextField instaldate = new
		 * DateTextField("installationdate","dd-mm-yyy") { protected String
		 * getInputType() { return "date"; } };
		 */
		instaldate.setRequired(true).setLabel(new Model("Installation Date"));
		final FeedbackLabel insfeedback = new FeedbackLabel("installfeedback", instaldate);
		instaldate.setOutputMarkupId(true);
		instaldate.add(datePicker);
		form.add(insfeedback);
		form.add(instaldate);

		CustromDatePicker datePicker1 = new CustromDatePicker();
		datePicker1.setShowOnFieldClick(true);
		datePicker1.setAutoHide(false);

		DateTextField instaldate1 = new DateTextField("replacementdate",
				new PropertyModel<Date>(this, "replacementdate"), new PatternDateConverter("dd MMM, yyyy", true));

		/*
		 * DateTextField instaldate = new
		 * DateTextField("installationdate","dd-mm-yyy") { protected String
		 * getInputType() { return "date"; } };
		 */
		instaldate1.setRequired(true).setLabel(new Model("Replacement Date"));
		final FeedbackLabel insfeedback1 = new FeedbackLabel("replacedatefeedback", instaldate1);
		instaldate1.setOutputMarkupId(true);
		instaldate1.add(datePicker1);
		repdiv.add(insfeedback1);
		repdiv.add(instaldate1);

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
				if (addNetworkUPSDetail()) {
					PageParameters parms = new PageParameters();
					ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
					setResponsePage(av);
				}
			}
		}.setDefaultFormProcessing(true);
		add(new Label("spcircuitcode"));
		form.add(new Label("projecttypedescription"));
		form.add(new Label("locationname"));
		form.add(make);
		form.add(model);
		form.add(serialno);
		form.add(amc);
		form.add(batteries);
		form.add(remark);
		form.add(feedback);
		form.add(btncancel);
		form.add(btnreset);
		form.add(btnsubmit);
		form.add(replaceradio);
		form.add(repdiv);
		add(form);
	}

	private boolean addNetworkUPSDetail() {
		String query = "{call sp_circuit_add_network_ups_details(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setString(3, spcircuitid);
			stmt.setString(4, make);
			stmt.setString(5, model);
			stmt.setString(6, serialno);
			stmt.setString(7, amc);
			stmt.setString(8, remark);
			stmt.setInt(9, Integer.parseInt(batteries));
			stmt.setInt(10, replace.equals("Replace") ? 1 : 0);
			stmt.setString(11, rmake);
			stmt.setString(12, rmodel);
			stmt.setString(13, rserialno);
			stmt.setInt(14, replace.equals("Replace") ? Integer.parseInt(rbatteries) : 0);
			stmt.setString(15, getFormatDate(installationdate));
			if (replace.equals("Replace"))
				stmt.setString(16, getFormatDate(replacementdate));
			else
				stmt.setString(16, null);
			    stmt.setString(17, replace);
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			rs = stmt.executeQuery();
			while (rs.next()) {
				log.info("Network UPS Detail Added Successfully With ID :" + rs.getInt(1));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in addNetworkUPSDetail() method {" + e.getMessage() + "}");
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
			} catch (SQLException e2) {
				log.error("SQL Exception in addNetworkUPSDetail() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return true;
	}

	private String getFormatDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
		return simpleDateFormat.format(date);

	}

}
