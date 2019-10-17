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
import org.apache.wicket.ajax.AjaxRequestTarget;
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

import com.sp.SPNetworkLocation.PortSession;
import com.sp.master.ApspdclMaster;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;
import com.sp.validators.NumberValidator;
import com.sp.validators.StringValidator;

public class EditVendorDetailForm extends Panel {
	private static final Logger log = Logger.getLogger(EditVendorDetailForm.class);
	private String vendorname;
	private String vendornamefeedback;
	private String bandwidth;
	private String bandwidthfeedback;
	private ServiceType servicetype;
	private String servicetypefeedback;
	private String ntinterface;
	private String ntinterfacefeedback;
	private String circuitid;
	private String circuitidfeedback;
	private String remark;
	private String remarkfeedback;
	private String spcircuitid;
	private String projecttypedescription;
	private MediaType mediatype;
	private String mediatypefeedback;
	private String spcircuitcode;
	private Vendor vendornamedd;
	private String vendornameddfeedback;
	private boolean vendornameflag;
	private Bandwidth bandwidthdd;
	private String bandwidthddfeedback;
	private boolean bandwidthflag;
	private UnitType unittype;
	private String unittypefeedback;
	private String phase;
	private String phasefeedback;
	private NetworkVendorDetail nvd;
	private int nt_vendor_id;
	private boolean servicetypedivflag;
	private boolean mediatypedivflag;
	private String locationname;
	private Date datecommissioned;
	private String datefeedback;
	private static String PATTERN = "yyyy-MM-dd";
	private List<String> phaselist = Arrays.asList("Phase 1", "Phase 2");
	IModel<? extends List<MediaType>> medialist = new LoadableDetachableModel<List<MediaType>>() {

		@Override
		protected List<MediaType> load() {
			// TODO Auto-generated method stub
			return loadMediaTypes();
		}
	};
	IModel<? extends List<Vendor>> vendorlist = new LoadableDetachableModel<List<Vendor>>() {

		@Override
		protected List<Vendor> load() {
			// TODO Auto-generated method stub
			return loadVendors();
		}
	};

	IModel<? extends List<Bandwidth>> bandwidthlist = new LoadableDetachableModel<List<Bandwidth>>() {

		@Override
		protected List<Bandwidth> load() {
			// TODO Auto-generated method stub
			return loadBandwidths();
		}
	};

	IModel<? extends List<UnitType>> unittypelist = new LoadableDetachableModel<List<UnitType>>() {

		@Override
		protected List<UnitType> load() {
			// TODO Auto-generated method stub
			return loadUnitTypes();
		}
	};

	IModel<? extends List<ServiceType>> servicetypelist = new LoadableDetachableModel<List<ServiceType>>() {

		@Override
		protected List<ServiceType> load() {
			// TODO Auto-generated method stub
			return loadServiceTypes();
		}
	};

	public EditVendorDetailForm(String id, final IModel<NetworkVendorDetail> nvdmodel,
			final IModel<NetworkLocationDetail> nldmodel) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<EditVendorDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("addvendorform");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[] { FeedbackMessage.ERROR };
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));

		try {
			nvd = nvdmodel.getObject();
			nt_vendor_id = nvd.getVendorid();
			vendorname = nvd.getVendorname();
			if (nvd.getVendortypeid() == 100) {
				vendornameflag = true;
			} else {
				vendornameflag = false;
			}

			if (nvd.getVendortypeid() == 5) {
				servicetypedivflag = true;
			} else {
				mediatypedivflag = true;
			}

			bandwidth = nvd.getBandwidth().replace("Kbps", "").replace("Mbps", "");
			if (nvd.getBandwidthtypeid() == 100) {
				bandwidthflag = true;
			} else {
				bandwidthflag = false;
			}
			ntinterface = nvd.getVninterface();
			circuitid = nvd.getCircuitid();
			phase = nvd.getPhase();
			remark = nvd.getRemark();

			spcircuitid = nldmodel.getObject().getSpcircuitid();
			spcircuitcode = nldmodel.getObject().getSpciruitcode();
			projecttypedescription = nldmodel.getObject().getProjecttypedescription();
			locationname = nldmodel.getObject().getLocationname();
			datecommissioned = new SimpleDateFormat("dd MMM, yyyy").parse(nvd.getCommissionedate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WebMarkupContainer vendortfdiv = new WebMarkupContainer("vendortextfileddivision") {
			@Override
			public boolean isVisible() {
				// TODO Auto-generated method stub
				return vendornameflag;
			}
		};
		;
		vendortfdiv.setOutputMarkupId(true);
		TextField<String> vendorname = new TextField<String>("vendorname");
		vendorname.setRequired(true).setLabel(new Model("Vendor Name"));
		vendorname.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 120));
		vendorname.add(new StringValidator());
		final FeedbackLabel vendornameFeedbackLabel = new FeedbackLabel("vendornamefeedback", vendorname);
		vendornameFeedbackLabel.setOutputMarkupId(true);
		vendortfdiv.add(vendornameFeedbackLabel);
		vendortfdiv.add(vendorname);

		final DropDownChoice<Vendor> vendor = new DropDownChoice<Vendor>("vendornamedd", vendorlist,
				new ChoiceRenderer("vendorname")) {
			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(Vendor newSelection) {
				// TODO Auto-generated method stub
				if (vendornamedd != null) {
					if (vendornamedd.getVendorid() == 100) {
						vendornameflag = true;
					} else {
						vendornameflag = false;
					}
					if (vendornamedd.getVendorid() == 5) {
						servicetypedivflag = true;
						mediatypedivflag = false;
					} else {
						servicetypedivflag = false;
						mediatypedivflag = true;
					}
				}
			}
		};
		vendor.setNullValid(true);
		vendor.setRequired(true).setLabel(new Model("Vendor"));
		final FeedbackLabel vendorFeedbackLabel = new FeedbackLabel("vendornameddfeedback", vendor);
		vendorFeedbackLabel.setOutputMarkupId(true);
		form.add(vendorFeedbackLabel);

		final DropDownChoice<Bandwidth> bandwidth = new DropDownChoice<Bandwidth>("bandwidthdd", bandwidthlist,
				new ChoiceRenderer("bandwidthdesc")) {
			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(Bandwidth newSelection) {
				// TODO Auto-generated method stub
				if (bandwidthdd != null) {
					if (bandwidthdd.getBandwidthid() == 100) {
						bandwidthflag = true;
					} else {
						bandwidthflag = false;
					}
				}
			}
		};
		bandwidth.setNullValid(true);
		bandwidth.setRequired(true).setLabel(new Model("Vendor"));
		final FeedbackLabel bandwidthddFeedbackLabel = new FeedbackLabel("bandwidthddfeedback", bandwidth);
		bandwidthddFeedbackLabel.setOutputMarkupId(true);
		form.add(bandwidthddFeedbackLabel);

		WebMarkupContainer bandwidthfdiv = new WebMarkupContainer("bandwidthfileddivision") {
			@Override
			public boolean isVisible() {
				// TODO Auto-generated method stub
				return bandwidthflag;
			}
		};

		TextField<String> bandwidthtf = new TextField<String>("bandwidth");
		bandwidthtf.setRequired(true).setLabel(new Model("Bandwidth"));
		bandwidthtf.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 120));
		/* bandwidth.add(new StringValidator()); */
		final FeedbackLabel bandwidthFeedbackLabel = new FeedbackLabel("bandwidthfeedback", bandwidthtf);
		bandwidthFeedbackLabel.setOutputMarkupId(true);
		bandwidthfdiv.add(bandwidthFeedbackLabel);
		bandwidthfdiv.add(bandwidthtf);

		final DropDownChoice<UnitType> unittype = new DropDownChoice<UnitType>("unittype", unittypelist,
				new ChoiceRenderer("unittypedesc")) {
			@Override
			protected String getNullValidDisplayValue() {
				return "Select Unit Type";
			}
		};
		unittype.setNullValid(true);
		unittype.setRequired(true).setLabel(new Model("Unit Type"));
		final FeedbackLabel unittypeFeedbackLabel = new FeedbackLabel("unittypefeedback", unittype);
		unittypeFeedbackLabel.setOutputMarkupId(true);
		bandwidthfdiv.add(unittypeFeedbackLabel);
		bandwidthfdiv.add(unittype);

		WebMarkupContainer servicetypediv = new WebMarkupContainer("servicetypediv") {
			@Override
			public boolean isVisible() {
				// TODO Auto-generated method stub
				return servicetypedivflag;
			}
		};
		form.add(servicetypediv);

		WebMarkupContainer mediatypediv = new WebMarkupContainer("mediatypediv") {
			@Override
			public boolean isVisible() {
				// TODO Auto-generated method stub
				return mediatypedivflag;
			}
		};
		form.add(mediatypediv);

		final DropDownChoice<MediaType> mediatypes = new DropDownChoice<MediaType>("mediatype", medialist,
				new ChoiceRenderer("mediatypedesc"));
		mediatypes.setNullValid(false);
		mediatypes.setRequired(true).setLabel(new Model("Media Type"));
		final FeedbackLabel mediatypeFeedbackLabel = new FeedbackLabel("mediatypefeedback", mediatypes);
		mediatypeFeedbackLabel.setOutputMarkupId(true);
		mediatypediv.add(mediatypeFeedbackLabel);
		mediatypediv.add(mediatypes);

		DropDownChoice<ServiceType> servicetype = new DropDownChoice<ServiceType>("servicetype", servicetypelist,
				new ChoiceRenderer("servicetypedesc"));
		// TextField<String> servicetype = new TextField<String>("servicetype");
		servicetype.setRequired(true).setLabel(new Model("Service Type"));
		/*
		 * servicetype.add(org.apache.wicket.validation.validator.
		 * StringValidator.lengthBetween(1, 120));
		 */
		/* servicetype.add(new StringValidator()); */
		final FeedbackLabel servicetypeFeedbackLabel = new FeedbackLabel("servicetypefeedback", servicetype);
		servicetypeFeedbackLabel.setOutputMarkupId(true);
		servicetypediv.add(servicetypeFeedbackLabel);
		servicetypediv.add(servicetype);

		TextField<String> ntinterface = new TextField<String>("ntinterface");
		ntinterface.setRequired(true).setLabel(new Model("Interface"));
		ntinterface.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 120));
		/* ntinterface.add(new StringValidator()); */
		final FeedbackLabel ntinterfaceFeedbackLabel = new FeedbackLabel("ntinterfacefeedback", ntinterface);
		ntinterfaceFeedbackLabel.setOutputMarkupId(true);
		form.add(ntinterfaceFeedbackLabel);

		TextField<String> circuitid = new TextField<String>("circuitid");
		circuitid.setRequired(true).setLabel(new Model("Circuit ID"));
		circuitid.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		/* circuitid.add(new NumberValidator()); */
		final FeedbackLabel circuitidFeedbackLabel = new FeedbackLabel("circuitidfeedback", circuitid);
		circuitidFeedbackLabel.setOutputMarkupId(true);
		form.add(circuitidFeedbackLabel);

		TextField<String> remark = new TextField<String>("remark");
		remark.setLabel(new Model("Remark"));
		remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 64));
		/* remark.add(new StringValidator()); */
		final FeedbackLabel remarkFeedbackLabel = new FeedbackLabel("remarkfeedback", remark);
		remarkFeedbackLabel.setOutputMarkupId(true);
		form.add(remarkFeedbackLabel);

		DropDownChoice<String> phase = new DropDownChoice<String>("phase", phaselist);
		phase.setRequired(true).setLabel(new Model("Phase"));
		final FeedbackLabel phaseFeedbackLabel = new FeedbackLabel("phasefeedback", phase);
		phaseFeedbackLabel.setOutputMarkupId(true);
		form.add(phaseFeedbackLabel);

		CustromDatePicker datePicker = new CustromDatePicker();
		datePicker.setShowOnFieldClick(true);
		datePicker.setAutoHide(false);

		DateTextField commdate = new DateTextField("datecommissioned",
				new PropertyModel<Date>(this, "datecommissioned"), new PatternDateConverter("dd MMM, yyyy", true));

		/*
		 * DateTextField instaldate = new
		 * DateTextField("installationdate","dd-mm-yyy") { protected String
		 * getInputType() { return "date"; } };
		 */
		commdate.setRequired(true).setLabel(new Model("Date of Commissioned"));
		final FeedbackLabel datefeedback = new FeedbackLabel("datefeedback", commdate);
		commdate.setOutputMarkupId(true);
		commdate.add(datePicker);
		form.add(datefeedback);

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
				EditVendorDetail av = new EditVendorDetail(parms, nvdmodel, nldmodel);
				setResponsePage(av);
			}
		}.setDefaultFormProcessing(false);

		Button btnsubmit = new Button("btnsubmit") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if (editNetworkVendorDetail()) {
					PageParameters parms = new PageParameters();
					ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
					setResponsePage(av);
				}
			}
		}.setDefaultFormProcessing(true);

		add(new Label("spcircuitcode"));
		form.add(new Label("projecttypedescription"));
		form.add(new Label("locationname"));
		form.add(vendor);
		form.add(vendortfdiv);
		form.add(bandwidthfdiv);
		form.add(bandwidth);
		form.add(ntinterface);
		form.add(circuitid);
		form.add(remark);
		form.add(btncancel);
		form.add(btnreset);
		form.add(btnsubmit);
		form.add(feedback);
		form.add(phase);
		form.add(commdate);
		add(form);
	}

	private boolean editNetworkVendorDetail() {
		final String query = "{call sp_circuit_edit_network_vendor_details(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setString(3, spcircuitid);
			stmt.setInt(4, vendornamedd.getVendorid());
			stmt.setString(5, (vendornamedd.getVendorid() == 100) ? vendorname : vendornamedd.getVendorname());
			stmt.setInt(6, bandwidthdd.getBandwidthid());
			stmt.setString(7, (bandwidthdd.getBandwidthid() == 100) ? bandwidth + " " + unittype.getUnittypedesc()
					: bandwidthdd.getBandwidthdesc());
			stmt.setInt(8,
					(bandwidthdd.getBandwidthid() == 100) ? Integer.parseInt(bandwidth) : bandwidthdd.getBandwidth());
			stmt.setInt(9, (bandwidthdd.getBandwidthid() == 100) ? unittype.getUnittypeid()
					: bandwidthdd.getBandwidthunittypeid());
			stmt.setInt(10, (vendornamedd.getVendorid() == 5) ? servicetype.getServicetypeid() : 0);
			stmt.setString(11, ntinterface);
			stmt.setString(12, this.circuitid);
			stmt.setInt(13, (vendornamedd.getVendorid() != 5) ? mediatype.getMediatypeid() : 0);
			stmt.setString(14, remark);
			stmt.setString(15, phase);
			stmt.setInt(16, nt_vendor_id);
			stmt.setString(17, this.getFormatDate(datecommissioned));
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			rs = stmt.executeQuery();
			while (rs.next()) {
				log.info("Network Vendor Detail Edited Successfully With ID :" + rs.getInt(1));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in editNetworkVendorDetail() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in editNetworkVendorDetail() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return true;
	}

	private String getFormatDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
		return simpleDateFormat.format(date);

	}

	private List<MediaType> loadMediaTypes() {
		final List<MediaType> list = new ArrayList<MediaType>();
		final String query = "{call sp_get_media_types(?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				MediaType m = new MediaType(rs.getInt(1), rs.getString(2));
				if (nvd.getMediatypedesc().trim().equals(rs.getString(2).trim())) {
					mediatype = m;
				}
				list.add(m);
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadMediaTypes() method {" + e.getMessage() + "}");
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

	private List<Vendor> loadVendors() {
		final List<Vendor> list = new ArrayList<Vendor>();
		final String query = "{call sp_get_vendors(?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				Vendor v = new Vendor(rs.getInt(1), rs.getString(2));
				if (nvd.getVendortypeid() == rs.getInt(1)) {
					vendornamedd = v;
				}
				list.add(v);
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadVendors() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in loadVendors() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}

		return list;
	}

	private List<Bandwidth> loadBandwidths() {
		final List<Bandwidth> list = new ArrayList<Bandwidth>();
		final String query = "{call sp_get_bandwidths(?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				Bandwidth b = new Bandwidth(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				if (nvd.getBandwidthtypeid() == rs.getInt(1)) {
					bandwidthdd = b;
				}
				list.add(b);
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadBandwidths() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in loadBandwidths() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}

		return list;
	}

	private List<UnitType> loadUnitTypes() {
		final List<UnitType> list = new ArrayList<UnitType>();
		final String query = "{call sp_get_unittypes(?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				UnitType u = new UnitType(rs.getInt(1), rs.getString(2));
				if (nvd.getBandwidthunittypeid() == rs.getInt(1)) {
					unittype = u;
				}
				list.add(u);
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadUnitTypes() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in loadUnitTypes() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}

		return list;
	}

	private List<ServiceType> loadServiceTypes() {
		final List<ServiceType> list = new ArrayList<ServiceType>();
		final String query = "{call sp_get_service_types(?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				ServiceType s = new ServiceType(rs.getInt(1), rs.getString(2));
				if (nvd.getServicetype().trim().equals(rs.getString(2).trim())) {
					servicetype = s;
				}
				list.add(s);
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadServiceTypes() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in loadServiceTypes() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}

		return list;
	}
}
