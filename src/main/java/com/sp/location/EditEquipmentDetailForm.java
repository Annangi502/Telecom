package com.sp.location;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.resource.CustomRadioChoice;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;
import com.sp.validators.StringValidator;

public class EditEquipmentDetailForm extends Panel {
	private static final Logger log = Logger.getLogger(EditEquipmentDetailForm.class);
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
	private String itsposition ;
	private String replace;
	private String replacefeedback;
	private String remark;
	private String remarkfeedback;
	private boolean replacedivflag;
	private String rmake;
	private String rmakefeedback;
	private String rmodel;
	private String rmodelfeedback;
	private String rserialno;
	private String rserialnofeedback;
	private Date installationdate;
	private String installfeedback;
	private String rremark;
	private String rremarkfeedback;
	private EquipmentType equipmenttype;
	private String equipmenttypefeedback;
	private Date installdate;
	private String installfeed;
	private static final List<String> TYPES = Arrays.asList("AMC", "Warranty", "None");
	private static final List<String> R_TYPES = Arrays.asList("Replace", "Stand By","Existing");
	private static String PATTERN = "yyyy-MM-dd";
	private NetworkEquipmentDetail ned;
	private String locationname;
	IModel<? extends List<EquipmentType>> equipmentlist = new LoadableDetachableModel<List<EquipmentType>>() {

		@Override
		protected List<EquipmentType> load() {
			// TODO Auto-generated method stub
			return loadEquipmentTypes();
		}
	};

	public EditEquipmentDetailForm(String id, final IModel<NetworkEquipmentDetail> eqmodel,
			final IModel<NetworkLocationDetail> nldmodel) {
		super(id);

		setDefaultModel(new CompoundPropertyModel<EditEquipmentDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("addeqpdetailform");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[] { FeedbackMessage.ERROR };
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));
		try {
			ned = eqmodel.getObject();

			make = ned.getMake();
			model = ned.getModel();
			serialno = ned.getSerialnumber();
			amc = ned.getAmc();
			itsposition = checkNull(ned.getItsposition()) ;
			replace = ned.getIsreplace() == 1 ? "Replace" : "Stand By";
			remark = ned.getRemark();
			installdate = new SimpleDateFormat("dd MMM, yyyy").parse(ned.getInstalldate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//	System.out.println(" replace "+replace);
		
		if (itsposition.equals("Replace")) {
			replacedivflag = true;
		} else {
			replacedivflag = false;
		}
		getReplaceDetails();

		spcircuitid = nldmodel.getObject().getSpcircuitid();
		spcircuitcode = nldmodel.getObject().getSpciruitcode();
		projecttypedescription = nldmodel.getObject().getProjecttypedescription();
		locationname = nldmodel.getObject().getLocationname();

		DropDownChoice<EquipmentType> equipmenttype = new DropDownChoice<EquipmentType>("equipmenttype", equipmentlist,
				new ChoiceRenderer<EquipmentType>("equipmenttypedesc"));
		equipmenttype.setNullValid(false);
		equipmenttype.setRequired(true).setLabel(new Model("Equipment Type"));
		final FeedbackLabel equipmenttypeFeedbackLabel = new FeedbackLabel("equipmenttypefeedback", equipmenttype);
		equipmenttypeFeedbackLabel.setOutputMarkupId(true);
		form.add(equipmenttypeFeedbackLabel);

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
		final FeedbackLabel amcFeedbackLabel = new FeedbackLabel("amcfeedback", amc);
		amcFeedbackLabel.setOutputMarkupId(true);
		form.add(amcFeedbackLabel);

		final CustomRadioChoice<String> replaceradio = new CustomRadioChoice("itsposition", R_TYPES) {
			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(Object newSelection) {
				// TODO Auto-generated method stub
				
				if (itsposition.equals("Replace")) {
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

		TextField<String> remark = new TextField<String>("remark");
		remark.setLabel(new Model("Remark"));
		remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 64));
		/* remark.add(new StringValidator()); */
		final FeedbackLabel remarkFeedbackLabel = new FeedbackLabel("remarkfeedback", remark);
		remarkFeedbackLabel.setOutputMarkupId(true);
		form.add(remarkFeedbackLabel);

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
		rmake.add(new StringValidator());
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
		repdiv.add(insfeedback);
		repdiv.add(instaldate);

		CustromDatePicker datePicker1 = new CustromDatePicker();
		datePicker1.setShowOnFieldClick(true);
		datePicker1.setAutoHide(false);

		DateTextField instaldate1 = new DateTextField("installdate", new PropertyModel<Date>(this, "installdate"),
				new PatternDateConverter("dd MMM, yyyy", true));

		/*
		 * DateTextField instaldate = new
		 * DateTextField("installationdate","dd-mm-yyy") { protected String
		 * getInputType() { return "date"; } };
		 */
		instaldate1.setRequired(true).setLabel(new Model("Installation Date"));
		final FeedbackLabel insfeedback1 = new FeedbackLabel("installfeed", instaldate1);
		instaldate1.setOutputMarkupId(true);
		instaldate1.add(datePicker1);
		form.add(insfeedback1);
		form.add(instaldate1);

		TextArea<String> rremark = new TextArea<String>("rremark");
		rremark.setLabel(new Model("Remark"));
		// remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1,
		// 64));
		/* remark.add(new StringValidator()); */
		final FeedbackLabel rremarkFeedbackLabel = new FeedbackLabel("rremarkfeedback", rremark);
		rremarkFeedbackLabel.setOutputMarkupId(true);
		repdiv.add(rremarkFeedbackLabel);
		repdiv.add(rremark);

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
				EditEquipmentDetail av = new EditEquipmentDetail(parms, eqmodel, nldmodel);
				setResponsePage(av);
			}
		}.setDefaultFormProcessing(false);

		Button btnsubmit = new Button("btnsubmit") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if (editNetworkEquipmentDetail()) {
					PageParameters parms = new PageParameters();
					ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
					setResponsePage(av);
				}
			}
		}.setDefaultFormProcessing(true);
		add(new Label("spcircuitcode"));
		form.add(new Label("projecttypedescription"));
		form.add(new Label("locationname"));
		form.add(equipmenttype);
		form.add(make);
		form.add(model);
		form.add(serialno);
		form.add(amc);
		form.add(repdiv);
		form.add(remark);
		form.add(feedback);
		form.add(btncancel);
		form.add(btnreset);
		form.add(btnsubmit);
		form.add(replaceradio);
		add(form);
	}

	private boolean editNetworkEquipmentDetail() {
		final String query = "{call sp_circuit_edit_network_equipment_details(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setString(3, spcircuitid);
			stmt.setString(4, make);
			stmt.setString(5, model);
			stmt.setString(6, serialno);
			stmt.setString(7, amc);
			stmt.setInt(8, itsposition.equals("Replace") ? 1 : 0);
			stmt.setString(9, rmake);
			stmt.setString(10, rmodel);
			stmt.setString(11, rserialno);
			stmt.setString(12, remark);
			stmt.setInt(13, ned.getEquipmentid());
			stmt.setString(14, itsposition.equals("Replace") ? getFormatDate(installationdate) : null);
			stmt.setString(15, rremark);
			stmt.setInt(16, equipmenttype.getEquipmenttypeid());
			stmt.setString(17, getFormatDate(installdate));
			stmt.setString(18, itsposition);
			/*
			System.out.println(" replace " + (replace.equals("Replace") ? 1 : 0));
			System.out.println(" spcircuitid " + spcircuitid);
			System.out.println(" replace " + (replace.equals("Replace") ? 1 : 0));*/
			
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			rs = stmt.executeQuery();
			while (rs.next()) {
				log.info("Network Equipment Detail Edited Successfully With ID :" + rs.getInt(1));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in editNetworkEquipmentDetail() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in editNetworkEquipmentDetail() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return true;
	}

	private List<EquipmentType> loadEquipmentTypes() {
		List<EquipmentType> list = new ArrayList<EquipmentType>();
		String query = "{call sp_get_equipment_types(?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setInt(3, 1);
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			System.out.println(" getEquipmentdesc "+ned.getEquipmentdesc().trim());
			while (rs.next()) {
				EquipmentType eq = new EquipmentType(rs.getInt(1), rs.getString(2));
				if (ned.getEquipmentdesc().trim().equals(rs.getString(2).trim())) {
					equipmenttype = eq;
				}
				list.add(eq);
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadProjectTypes() method {" + e.getMessage() + "}");
			e.printStackTrace();
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
				log.error("SQL Exception in loadProjectTypes() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}

	private String getFormatDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
		return simpleDateFormat.format(date);

	}

	public boolean getReplaceDetails() {
		String query = "{call sp_circuit_equipment_get_replace_details(?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setInt(3, ned.getEquipmentid());
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				rmake = rs.getString(1);
				rmodel = rs.getString(2);
				rserialno = rs.getString(3);
				//System.out.println(" getString "+rs.getString(4));
				if (itsposition.equals("Replace"))
					installationdate = new SimpleDateFormat("dd MMM, yyyy").parse(rs.getString(4));
				else
					installationdate = null;
				rremark = rs.getString(5);
			}
		} catch (SQLException e) {
			log.error("SQL Exception in getReplaceDetails() method {" + e.getMessage() + "}");
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				log.error("SQL Exception in getReplaceDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return true;
	}
	
	public String checkNull(String str){
		
		if(str==null)
			str = "" ;
		return str ;
	}

}
