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
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.resource.CircularDependencyException;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.master.ApspdclMaster;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;
import com.sp.validators.LandLineNumberValidator;
import com.sp.validators.MobileNoValidator;
import com.sp.validators.NumberValidator;
import com.sp.validators.StringValidator;

public class EditNetworkLocationDetailForm extends Panel {
	private static final Logger log = Logger.getLogger(EditNetworkLocationDetailForm.class);
	private String spcircuitid;
	private String projecttypedescription;
	private ProjectType projecttypes;
	private String projecttypefeedback;
	private String townname;
	private String townnamefeedback;
	private Date installationdate;
	private String installfeedback;
	private Date connecteddate;
	private String connfeedback;
	private String phase;
	private String phasefeedback;
	private String noofpoints;
	private String noofpointsfeedback;
	private String ofcdescfeedback;
	private String ofccontact;
	private String ofccontactfeedback;
	private String ofcaddress;
	private String ofcaddressfeedback;
	private String contactperson;
	private String contactpersonfeedback;
	private String contact;
	private String contactfeedback;
	private String remark;
	private String remarkfeedback;
	private String circle;
	private String division;
	private String subdivision;
	private String section;
	private String spcircuitcode;
	private static String PATTERN = "yyyy-MM-dd";
	private static final List<String> TYPES = Arrays.asList("Yes", "No");
	private String ismergelocation;
	private String ismergelocationfeedback;
	private String mergedesc;
    private String mergedescfeedback;
    private String latitude;
    private String latitudefeedback;
    private String longitude;
    private String longitudefeedback;
	IModel<? extends List<ProjectType>> projectlist = new LoadableDetachableModel<List<ProjectType>>() {

		@Override
		protected List<ProjectType> load() {
			// TODO Auto-generated method stub
			return loadProjectTypes();
		}
	};
	private List<String> phaselist = Arrays.asList("Phase 1", "Phase 2");
	private NetworkLocationDetail nld;

	public EditNetworkLocationDetailForm(String id, final IModel<NetworkLocationDetail> nldmodel) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<EditNetworkLocationDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("form");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[] { FeedbackMessage.ERROR };
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));

		nld = nldmodel.getObject();

		spcircuitid = nldmodel.getObject().getSpcircuitid();
		projecttypedescription = nldmodel.getObject().getProjecttypedescription();
		circle = nld.getCircledesc();
		division = nld.getDivisiondesc();
		subdivision = nld.getSubdivisiondesc();
		section = nld.getSectiondesc();
		spcircuitcode = nld.getSpciruitcode();
		ismergelocation = nld.getIsmergelocation()==1?"Yes":"No";
		mergedesc = nld.getMergelocationdesc();
		latitude = nld.getLatitude();
		longitude = nld.getLongitude();
		/*
		 * projecttypes = new
		 * ProjectType(nldmodel.getObject().getProjecttypeid(),
		 * projecttypedescription);
		 */
		townname = nldmodel.getObject().getTownname();
		phase = nldmodel.getObject().getPhase();
		noofpoints = String.valueOf(nldmodel.getObject().getNoofpointsavailable());
		ofccontact = nldmodel.getObject().getOfficecontactno();
		ofcaddress = nldmodel.getObject().getOfficeaddress();
		contactperson = nldmodel.getObject().getLocationcontactperson();
		contact = nldmodel.getObject().getLoactioncontactno();
		remark = nldmodel.getObject().getRemark();

		try {
			installationdate = new SimpleDateFormat("dd MMM, yyyy").parse(nldmodel.getObject().getInstallationdate());
			connecteddate = new SimpleDateFormat("dd MMM, yyyy").parse(nldmodel.getObject().getDateofconnected());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DropDownChoice<ProjectType> projecttype = new DropDownChoice<ProjectType>("projecttypes", projectlist,
				new ChoiceRenderer<ProjectType>("projecttypedescription"));
		projecttype.setNullValid(false);
		projecttype.setRequired(true).setLabel(new Model("Project Type"));
		final FeedbackLabel projecttypeFeedbackLabel = new FeedbackLabel("projecttypefeedback", projecttype);
		projecttypeFeedbackLabel.setOutputMarkupId(true);
		form.add(projecttypeFeedbackLabel);

		TextField<String> tname = new TextField<String>("townname");
		tname.setRequired(true).setLabel(new Model("Town Name"));
		tname.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 64));
		/* tname.add(new StringValidator()); */
		final FeedbackLabel townnameFeedbackLabel = new FeedbackLabel("townnamefeedback", tname);
		townnameFeedbackLabel.setOutputMarkupId(true);
		form.add(townnameFeedbackLabel);

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

		CustromDatePicker datePicker1 = new CustromDatePicker();
		datePicker1.setShowOnFieldClick(true);
		datePicker1.setAutoHide(false);

		DateTextField condate = new DateTextField("connecteddate", new PropertyModel<Date>(this, "connecteddate"),
				new PatternDateConverter("dd MMM, yyyy", true));
		condate.setRequired(true).setLabel(new Model("Date of Connected"));
		final FeedbackLabel connfeedback = new FeedbackLabel("connfeedback", condate);
		condate.setOutputMarkupId(true);
		condate.add(datePicker1);
		form.add(connfeedback);

		DropDownChoice<String> phase = new DropDownChoice<String>("phase", phaselist);
		/* TextField<String> phase = new TextField<String>("phase"); */
		phase.setRequired(true).setLabel(new Model("Phase"));
		/* phase.add(new StringValidator()); */
		final FeedbackLabel phaseFeedbackLabel = new FeedbackLabel("phasefeedback", phase);
		phaseFeedbackLabel.setOutputMarkupId(true);
		form.add(phaseFeedbackLabel);

		TextField<String> points = new TextField<String>("noofpoints");
		points.setRequired(true).setLabel(new Model("No.of Points Available"));
		points.add(new NumberValidator());
		final FeedbackLabel pointsFeedbackLabel = new FeedbackLabel("noofpointsfeedback", points);
		pointsFeedbackLabel.setOutputMarkupId(true);
		form.add(pointsFeedbackLabel);

		
		final WebMarkupContainer mrgdescdiv = new WebMarkupContainer("mrgdescdiv");
		mrgdescdiv.setEnabled(false);
		mrgdescdiv.setOutputMarkupId(true);
        final TextField<String> mrgdesc = new TextField<String>("mergedesc");
        mrgdesc.setOutputMarkupId(true);
        mrgdesc.setRequired(false).setLabel(new Model("Merge Description"));
        mrgdesc.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/*tname.add(new StringValidator());*/
		final FeedbackLabel mrgdescFeedbackLabel = new FeedbackLabel("mergedescfeedback", mrgdesc);
		mrgdescFeedbackLabel.setOutputMarkupId(true);
		mrgdescdiv.add(mrgdesc);
		mrgdescdiv.add(mrgdescFeedbackLabel);
		form.add(mrgdescdiv);
		
		 if(ismergelocation.equals("Yes"))
			{
				mrgdescdiv.setEnabled(true);
				mrgdesc.setRequired(true).setLabel(new Model("Merge Description"));
			}
			else{
				mrgdescdiv.setEnabled(false);
				mrgdesc.setRequired(false).setLabel(new Model("Merge Description"));
			}
		 
		final RadioChoice<String> ismergelocationrc = new RadioChoice("ismergelocation", TYPES) {
			@Override
			public String getSuffix() {
				// TODO Auto-generated method stub
				return "&emsp;&emsp;&emsp;&emsp;";
			}
		};
		ismergelocationrc.add(new AjaxFormChoiceComponentUpdatingBehavior() {

			  @Override
			  protected void onUpdate(AjaxRequestTarget target) {
				  if(ismergelocation.equals("Yes"))
					{
						mrgdescdiv.setEnabled(true);
						mrgdesc.setRequired(true).setLabel(new Model("Merge Description"));
					}
					else{
						mrgdescdiv.setEnabled(false);
						mrgdesc.setRequired(false).setLabel(new Model("Merge Description"));
					}
				  target.add(mrgdesc);
				  target.add(mrgdescdiv);
			  }

			});

		/* TextField<String> ofc = new TextField<String>("ofcdesc"); */
		ismergelocationrc.setRequired(true).setLabel(new Model("Merge Location"));
		/* ofc.add(new StringValidator()); */
		final FeedbackLabel ismergelocationfeedback = new FeedbackLabel("ismergelocationfeedback", ismergelocationrc);
		ismergelocationfeedback.setOutputMarkupId(true);
		form.add(ismergelocationfeedback);

		TextField<String> ofcno = new TextField<String>("ofccontact");
		ofcno.setRequired(true).setLabel(new Model("Office Landline No."));
		ofcno.add(new LandLineNumberValidator());
		final FeedbackLabel ofcnoFeedbackLabel = new FeedbackLabel("ofccontactfeedback", ofcno);
		ofcnoFeedbackLabel.setOutputMarkupId(true);
		form.add(ofcnoFeedbackLabel);

		TextField<String> ofcadd = new TextField<String>("ofcaddress");
		ofcadd.setRequired(true).setLabel(new Model("Office Address"));
		ofcadd.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 120));
		/* ofcadd.add(new StringValidator()); */
		final FeedbackLabel ofcaddFeedbackLabel = new FeedbackLabel("ofcaddressfeedback", ofcadd);
		ofcaddFeedbackLabel.setOutputMarkupId(true);
		form.add(ofcaddFeedbackLabel);

		TextField<String> conperson = new TextField<String>("contactperson");
		conperson.setRequired(true).setLabel(new Model("Location Contact Person "));
		conperson.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		conperson.add(new StringValidator());
		final FeedbackLabel conpersonFeedbackLabel = new FeedbackLabel("contactpersonfeedback", conperson);
		conpersonFeedbackLabel.setOutputMarkupId(true);
		form.add(conpersonFeedbackLabel);

		TextField<String> contact = new TextField<String>("contact");
		contact.setRequired(true).setLabel(new Model("Contact No."));
		contact.add(new MobileNoValidator());
		final FeedbackLabel contactFeedbackLabel = new FeedbackLabel("contactfeedback", contact);
		contactFeedbackLabel.setOutputMarkupId(true);
		form.add(contactFeedbackLabel);

		TextField<String> remark = new TextField<String>("remark");
		remark.setLabel(new Model("Remark"));
		remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* remark.add(new StringValidator()); */
		final FeedbackLabel remarkFeedbackLabel = new FeedbackLabel("remarkfeedback", remark);
		remarkFeedbackLabel.setOutputMarkupId(true);
		form.add(remarkFeedbackLabel);
		
		
		TextField<String> latitude = new TextField<String>("latitude");
		latitude.setLabel(new Model("Latitude"));
		final FeedbackLabel latitudeFeedbackLabel = new FeedbackLabel("latitudefeedback", latitude);
		latitudeFeedbackLabel.setOutputMarkupId(true);
		form.add(latitudeFeedbackLabel);
		
		
		TextField<String> longitude = new TextField<String>("longitude");
		longitude.setLabel(new Model("Longitude"));	
		final FeedbackLabel longitudeFeedbackLabel = new FeedbackLabel("longitudefeedback", longitude);
		longitudeFeedbackLabel.setOutputMarkupId(true);
		form.add(longitudeFeedbackLabel);

		Button btncancel = new Button("btncancel") {
			@Override
			public void onSubmit() {
				PageParameters parms = new PageParameters();
				ViewNetworkLocationDetail vnld = new ViewNetworkLocationDetail(parms, nldmodel);
				setResponsePage(vnld);
			}
		}.setDefaultFormProcessing(false);

		/*
		 * Button btnreset = new Button("btnreset") {
		 * 
		 * @Override public void onSubmit() { // TODO Auto-generated method stub
		 * setResponsePage(EditNetworkLocationDetail.class); }
		 * }.setDefaultFormProcessing(false);
		 */

		Button btnsubmit = new Button("btnsubmit") {
			@Override
			public void onSubmit() {
				if (editNetworkLocationDetails()) {
					PageParameters parms = new PageParameters();
					ViewNetworkLocationDetail vnld = new ViewNetworkLocationDetail(parms,
							new CompoundPropertyModel<NetworkLocationDetail>(nldmodel));
					setResponsePage(vnld);
				}
			}
		}.setDefaultFormProcessing(true);

		form.add(new Label("circle"));
		form.add(new Label("division"));
		form.add(new Label("subdivision"));
		form.add(new Label("section"));
		form.add(new Label("spcircuitcode"));
		form.add(projecttype);
		form.add(tname);
		form.add(instaldate);
		form.add(condate);
		form.add(phase);
		form.add(points);
		form.add(ismergelocationrc);
		form.add(ofcno);
		form.add(ofcadd);
		form.add(conperson);
		form.add(contact);
		form.add(remark);
		form.add(feedback);
		form.add(latitude);
		form.add(longitude);
		form.add(btncancel);
		/* form.add(btnreset); */
		form.add(btnsubmit);

		add(form);

		// TODO Auto-generated constructor stub
	}

	private List<ProjectType> loadProjectTypes() {
		List<ProjectType> list = new ArrayList<ProjectType>();
		String query = "{call sp_get_project_types(?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				if (nld.getProjecttypeid() == rs.getInt(1)) {
					ProjectType pt = new ProjectType(rs.getInt(1), rs.getString(2));
					projecttypes = pt;
					list.add(pt);
				} else {
					list.add(new ProjectType(rs.getInt(1), rs.getString(2)));
				}
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

	private boolean editNetworkLocationDetails() {
		String query = "{call sp_circuit_edit_details(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setInt(3, projecttypes.getProjecttypeid());
			stmt.setString(4, phase);
			stmt.setInt(5, Integer.parseInt(noofpoints));
			stmt.setString(6, getFormatDate(installationdate));
			stmt.setString(7, getFormatDate(connecteddate));
			stmt.setString(8, ofccontact);
			stmt.setString(9,ofcaddress );
			stmt.setString(10, contactperson);
			stmt.setString(11, contact);
			stmt.setString(12,remark );
			stmt.setString(13, townname);
			stmt.setString(14,spcircuitid );
			stmt.setInt(15, ismergelocation.equals("Yes")?1:0);
			stmt.setString(16, ismergelocation.equals("Yes")?mergedesc:"");
			stmt.setString(17, latitude);
            stmt.setString(18, longitude);
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			rs = stmt.executeQuery();
			while (rs.next()) {
				log.info("Circuit Detail Edited Successfully With ID :" + rs.getString(1));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in editNetworkLocationDetails() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in editNetworkLocationDetails() method {" + e2.getMessage() + "}");
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
