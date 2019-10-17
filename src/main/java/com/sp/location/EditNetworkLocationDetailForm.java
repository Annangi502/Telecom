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
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
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
	private String oprcontactno ;
	private String oprcontactname ;
	private String oprcontactnofeedback;
	private String oprcontactnamefeedback ;
	private String remark;
	private String remarkfeedback;
	
	private Division division;
	private String divisionfeedback;
	private ERO ero;
	private String erofeedback;
	private SubDivision subdivision;
	private String subdivisionfeedback;
	private int circleid ;
	private int divisionid;
	private int eroid;
	private int subdivisionid;
	private int sectionid;
	private Section section;
	private String sectionfeedback;	
	private String circle;	
	private String divisiondesc ;
	private String erodes ;
	private String subdivisiondesc ;
	private String sectiondesc ;
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
	private List<String> phaselist = Arrays.asList("Phase 1", "Phase 2","Phase 3", "Phase 4","None");
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
		circleid = nld.getCircleid() ;
		divisionid = nld.getDivisionid() ;
		eroid = nld.getEroid() ;
		subdivisionid = nld.getSubdivisionid() ;
		sectionid = nld.getSectionid() ;
		divisiondesc = nld.getDivisiondesc();
		erodes = nld.getErodesc() ;
	    subdivisiondesc = nld.getSubdivisiondesc();
		sectiondesc = nld.getSectiondesc();
		spcircuitcode = nld.getLocationname();
		ismergelocation = nld.getIsmergelocation() == 1 ? "Yes" : "No";
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
		noofpoints = checkNullZero(String.valueOf(nldmodel.getObject().getNoofpointsavailable()));
		ofccontact = nldmodel.getObject().getOfficecontactno();
		ofcaddress = nldmodel.getObject().getOfficeaddress();
		contactperson = nldmodel.getObject().getLocationcontactperson();
		contact = nldmodel.getObject().getLoactioncontactno();
		oprcontactno = nldmodel.getObject().getOprcontactno() ;
		oprcontactname = nldmodel.getObject().getOprcontactname();
		remark = nldmodel.getObject().getRemark();
		
		IModel<? extends List<Division>> divisionlist = new LoadableDetachableModel<List<Division>>() {
			@Override
			protected List<Division> load() {
				// TODO Auto-generated method stub
				return loadDivisions(circleid);
			}
		};
		
		IModel<? extends List<ERO>> erolist = new LoadableDetachableModel<List<ERO>>() {
			@Override
			protected List<ERO> load() {
				// TODO Auto-generated method stub
				return loadEro(circleid, divisionid);
			}
		};
		IModel<? extends List<SubDivision>> subdivisionlist = new LoadableDetachableModel<List<SubDivision>>() {
			@Override
			protected List<SubDivision> load() {
				// TODO Auto-generated method stub
				return loadSubDivisions(circleid, divisionid,eroid);
			}
		};
		IModel<? extends List<Section>> sectionlist = new LoadableDetachableModel<List<Section>>() {
			@Override
			protected List<Section> load() {
				// TODO Auto-generated method stub
				return loadSections(circleid, divisionid, subdivisionid);
			}
		};
		
		

		try {
			if(nldmodel.getObject().getInstallationdate()==null)
			installationdate = null ;
			else
			installationdate = new SimpleDateFormat("dd MMM, yyyy").parse(nldmodel.getObject().getInstallationdate());
			if(nldmodel.getObject().getDateofconnected()==null)
			connecteddate = null;
			else
			connecteddate = new SimpleDateFormat("dd MMM, yyyy").parse(nldmodel.getObject().getDateofconnected());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final DropDownChoice<Section> sectiondd = new DropDownChoice<Section>("section", sectionlist,
				new ChoiceRenderer("sectiondesc", "sectionid"));
		sectiondd.add(new AjaxFormComponentUpdatingBehavior("change") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				if (section != null) {
					sectionid = section.getSectionid();
				}
			}

		});
		sectiondd.setNullValid(true);
		sectiondd.setLabel(new Model("Section"));
		sectiondd.setOutputMarkupId(true);
		final FeedbackLabel sectionFeedbackLabel = new FeedbackLabel("sectionfeedback", sectiondd);
		sectionFeedbackLabel.setOutputMarkupId(true);
		form.add(sectionFeedbackLabel);
		form.add(sectiondd);

		final DropDownChoice<SubDivision> subdivisiondd = new DropDownChoice<SubDivision>("subdivision",
				subdivisionlist, new ChoiceRenderer("subdivisiondesc", "subdivisionid"));
		subdivisiondd.add(new AjaxFormComponentUpdatingBehavior("change") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// TODO Auto-generated method stub.
				if (subdivision != null) {
					subdivisionid = subdivision.getSubdivisionid();
					section = null;
					target.add(sectiondd);
				} else {
					section = null;
					target.add(sectiondd);
				}
			}

		});
		subdivisiondd.setNullValid(true);
		subdivisiondd.setLabel(new Model("Sub-Division"));
		subdivisiondd.setOutputMarkupId(true);
		final FeedbackLabel subdivisionFeedbackLabel = new FeedbackLabel("subdivisionfeedback", subdivisiondd);
		subdivisionFeedbackLabel.setOutputMarkupId(true);
		form.add(subdivisionFeedbackLabel);
		form.add(subdivisiondd);
		
		final DropDownChoice<ERO> erodd = new DropDownChoice<ERO>("ero",
				erolist, new ChoiceRenderer("erodes", "eroid"));
		erodd.add(new AjaxFormComponentUpdatingBehavior("change") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// TODO Auto-generated method stub.
				if (ero != null) {
					eroid = ero.getEroid();
					section = null;
					subdivision = null;
					target.add(sectiondd);
					target.add(subdivisiondd);
					
					
				} else {
					
					section = null;
					subdivision = null;
					target.add(sectiondd);
					target.add(subdivisiondd);
					
				}
			}

		});
		erodd.setNullValid(true);
		erodd.setLabel(new Model("ERO"));
		erodd.setOutputMarkupId(true);
		final FeedbackLabel eroddFeedbackLabel = new FeedbackLabel("erofeedback", erodd);
		eroddFeedbackLabel.setOutputMarkupId(true);
		form.add(eroddFeedbackLabel);
		form.add(erodd);

		
		final DropDownChoice<Division> divisiondd = new DropDownChoice<Division>("division", divisionlist,
				new ChoiceRenderer("divisiondesc", "divisionid"));
		divisiondd.add(new AjaxFormComponentUpdatingBehavior("change") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				if (division != null) {
					divisionid = division.getDivisionid();
					section = null;
					subdivision = null;
					ero = null ;
					target.add(sectiondd);
					target.add(subdivisiondd);
					target.add(erodd);
				} else {
					section = null;
					subdivision = null;
					ero=null ;
					target.add(sectiondd);
					target.add(subdivisiondd);
					target.add(erodd);
				}
			}

		});
		divisiondd.setNullValid(true);
		divisiondd.setLabel(new Model("Division"));
		divisiondd.setOutputMarkupId(true);
		final FeedbackLabel divisionFeedbackLabel = new FeedbackLabel("divisionfeedback", divisiondd);
		divisionFeedbackLabel.setOutputMarkupId(true);
		form.add(divisionFeedbackLabel);
		form.add(divisiondd);
		

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
		instaldate.setLabel(new Model("Installation Date"));
		final FeedbackLabel insfeedback = new FeedbackLabel("installfeedback", instaldate);
		instaldate.setOutputMarkupId(true);
		instaldate.add(datePicker);
		form.add(insfeedback);

		CustromDatePicker datePicker1 = new CustromDatePicker();
		datePicker1.setShowOnFieldClick(true);
		datePicker1.setAutoHide(false);

		DateTextField condate = new DateTextField("connecteddate", new PropertyModel<Date>(this, "connecteddate"),
				new PatternDateConverter("dd MMM, yyyy", true));
		condate.setLabel(new Model("Date of Connected"));
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
		points.setLabel(new Model("No.of Points Available"));
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
		/* tname.add(new StringValidator()); */
		final FeedbackLabel mrgdescFeedbackLabel = new FeedbackLabel("mergedescfeedback", mrgdesc);
		mrgdescFeedbackLabel.setOutputMarkupId(true);
		mrgdescdiv.add(mrgdesc);
		mrgdescdiv.add(mrgdescFeedbackLabel);
		form.add(mrgdescdiv);

		if (ismergelocation.equals("Yes")) {
			mrgdescdiv.setEnabled(true);
			mrgdesc.setRequired(true).setLabel(new Model("Merge Description"));
		} else {
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
				if (ismergelocation.equals("Yes")) {
					mrgdescdiv.setEnabled(true);
					mrgdesc.setRequired(true).setLabel(new Model("Merge Description"));
				} else {
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
		ofcno.setLabel(new Model("Office Landline No."));
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
		
		TextField<String> oprcontactno = new TextField<String>("oprcontactno");
		oprcontactno.setLabel(new Model("Alternate Contact No."));
		oprcontactno.add(new MobileNoValidator());
		final FeedbackLabel oprcontactFeedbackLabel = new FeedbackLabel("oprcontactnofeedback", oprcontactno);
		oprcontactFeedbackLabel.setOutputMarkupId(true);
		form.add(oprcontactFeedbackLabel);
		
		TextField<String> oprcontactname = new TextField<String>("oprcontactname");
		oprcontactname.setLabel(new Model("Alternate Contact Name"));
		oprcontactname.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		oprcontactname.add(new StringValidator());
		final FeedbackLabel oprcontactnameFeedbackLabel = new FeedbackLabel("oprcontactnamefeedback", oprcontactname);
		oprcontactnameFeedbackLabel.setOutputMarkupId(true);
		form.add(oprcontactnameFeedbackLabel);

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
		form.add(oprcontactno);
		form.add(oprcontactname);
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
	
	private List<Division> loadDivisions(final int circleid) {
		final List<Division> list = new ArrayList<Division>();
		final String query = "{call sp_circle_get_devisions(?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, circleid);
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				if(nld.getDivisiondesc().equals(rs.getString(2)))
				{
					//System.out.println(" Divi Desc"+nld.getDivisiondesc());
					Division dv = new Division(rs.getInt(1), rs.getString(2), rs.getString(3));
					division = dv ;
					list.add(dv);
					
				}
				else				
				list.add(new Division(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadDivisions() method {" + e.getMessage() + "}");
			e.printStackTrace();
			return list;
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
				log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	private List<ERO> loadEro(final int circleid, final int divisionid) {
		final List<ERO> list = new ArrayList<ERO>();
		final String query = "{call sp_division_get_eros(?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, circleid);
			stmt.setInt(4, divisionid);
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				if(nld.getErodesc().equals(rs.getString(2))){
					//System.out.println(" ERO Desc"+nld.getErodesc());
					ERO er = new ERO(rs.getInt(1), rs.getString(2), rs.getString(3));
					ero = er ;
					list.add(er);
				}
				else
				list.add(new ERO(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadEro() method {" + e.getMessage() + "}");
			e.printStackTrace();
			return list;
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
				log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}


	private List<SubDivision> loadSubDivisions(final int circleid, final int divisionid,final int eroid) {
		final List<SubDivision> list = new ArrayList<SubDivision>();
		final String query = "{call sp_division_get_sub_divisions(?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, circleid);
			stmt.setInt(4, divisionid);
			stmt.setInt(5,eroid);
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				if(nld.getSubdivisiondesc().equals(rs.getString(2))){
					
					SubDivision sb = new SubDivision(rs.getInt(1), rs.getString(2), rs.getString(3));
					subdivision = sb ;
					list.add(sb);
				}
				else
				list.add(new SubDivision(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadSubDivisions() method {" + e.getMessage() + "}");
			e.printStackTrace();
			return list;
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
				log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}

	private List<Section> loadSections(final int circleid, final int divisionid, final int subdivision) {
		final List<Section> list = new ArrayList<Section>();
		final String query = "{call sp_sub_division_get_sections(?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, circleid);
			stmt.setInt(4, divisionid);
			stmt.setInt(5, subdivision);
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				if(nld.getSectiondesc().equals(rs.getString(2))){
					
					Section sc = new Section(rs.getInt(1), rs.getString(2), rs.getString(3));
					section = sc ;
					list.add(sc);
					
				}
				else
				list.add(new Section(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadSections() method {" + e.getMessage() + "}");
			e.printStackTrace();
			return list;
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
				log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}

	private boolean editNetworkLocationDetails() {
		String query = "{call sp_circuit_edit_details(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
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
			if(installationdate==null)
			stmt.setString(6,null);
			else
			stmt.setString(6, getFormatDate(installationdate));
			if(connecteddate==null)
			stmt.setString(7,null);
			else
			stmt.setString(7, getFormatDate(connecteddate));
			stmt.setString(8, ofccontact);
			stmt.setString(9, ofcaddress);
			stmt.setString(10, contactperson);
			stmt.setString(11, contact);
			stmt.setString(12, remark);
			stmt.setString(13, townname);
			stmt.setString(14, spcircuitid);
			stmt.setInt(15, ismergelocation.equals("Yes") ? 1 : 0);
			stmt.setString(16, ismergelocation.equals("Yes") ? mergedesc : "");
			stmt.setString(17, latitude);
			stmt.setString(18, longitude);
			stmt.setInt(19, divisionid);
			stmt.setInt(20,eroid);
			stmt.setInt(21, subdivisionid);
			stmt.setInt(22, sectionid);
			stmt.setString(23,oprcontactno);
			stmt.setString(24,oprcontactname);
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
	public String checkNullZero(String str){
		if(str==null)
			str = "0" ;
			return str ;
	}
}
