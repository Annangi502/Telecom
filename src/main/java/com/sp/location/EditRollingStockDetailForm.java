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
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
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
import com.sp.master.ApspdclMaster;
import com.sp.report.RollingStockDetails;
import com.sp.resource.CustomRadioChoice;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;
import com.sp.validators.LandLineNumberValidator;
import com.sp.validators.MobileNoValidator;
import com.sp.validators.NumberValidator;

public class EditRollingStockDetailForm extends Panel {
	private static final Logger log = Logger.getLogger(EditRollingStockDetailForm.class);
	private int rollingstockid ;
	private String spcircuitid ;
	private EquipmentType equipmenttype;
	private String equipmenttypefeedback;
	private String serialno;
	private String serialnofeedback;
	private String remark;
	private String remarkfeedback;
	private Date connecteddate;
	private String connfeedback;
	private Date supplydate;
	private String supplydatefeed;
	private String make;
	private String makefeedback;
	private String model;
	private String modelfeedback;
	private String amc;
	private String amcfeedback;
	private String wrkstatus;
	private String wrkstatusfeedback;
	private String porderno;
	private String pordernofeedback;
	private String addremark ;
	private String addremarkfeedback;
	private Circle circle;
	private String circlefeedback;
	private int circleid;
	private Division division;
	private String divisionfeedback;
	private SubDivision subdivision;
	private String subdivisionfeedback;
	private int divisionid;
	private int subdivisionid;
	private int sectionid;
	private Section section;
	private String sectionfeedback;
	private String totalstock;
	private String totalstockfeedback;
	private NetworkLocationDetail nlcd = new NetworkLocationDetail();
	private static String PATTERN = "yyyy-MM-dd";
	private static final List<String> TYPES = Arrays.asList("Yes", "No");
	private static final List<String> amcTYPES = Arrays.asList("AMC", "Warranty", "None");
	private NetworkRollingStockDetail nrsd ; 
	
	

	/*IModel<? extends List<Circle>> circlelist = new LoadableDetachableModel<List<Circle>>() {
		@Override
		protected List<Circle> load() {
			// TODO Auto-generated method stub
			return loadCircles();
		}
	};
	IModel<? extends List<Division>> divisionlist = new LoadableDetachableModel<List<Division>>() {
		@Override
		protected List<Division> load() {
			// TODO Auto-generated method stub
			return loadDivisions(circleid);
		}
	};
	IModel<? extends List<SubDivision>> subdivisionlist = new LoadableDetachableModel<List<SubDivision>>() {
		@Override
		protected List<SubDivision> load() {
			// TODO Auto-generated method stub
			return loadSubDivisions(circleid, divisionid);
		}
	};
	IModel<? extends List<Section>> sectionlist = new LoadableDetachableModel<List<Section>>() {
		@Override
		protected List<Section> load() {
			// TODO Auto-generated method stub
			return loadSections(circleid, divisionid, subdivisionid);
		}
	};*/
	
	
	IModel<? extends List<EquipmentType>> equipmentlist = new LoadableDetachableModel<List<EquipmentType>>() {

		@Override
		protected List<EquipmentType> load() {
			// TODO Auto-generated method stub
			return loadEquipmentTypes();
		}
	};


	
	private List<String> phaselist = Arrays.asList("Phase 1", "Phase 2");

	public EditRollingStockDetailForm(String id ,final IModel<NetworkRollingStockDetail> rsmodel,
			final IModel<NetworkLocationDetail> nldmodel) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<EditRollingStockDetailForm>(this));

		StatelessForm<Form> form = new StatelessForm<Form>("form");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[] { FeedbackMessage.ERROR };
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));
		nrsd = rsmodel.getObject();
		rollingstockid = nrsd.getRolling_stock_id() ;
	    serialno = nrsd.getSerialno() ; 
		model = nrsd.getModel() ;
		make = nrsd.getMake() ;
		amc = nrsd.getAmc();
		wrkstatus = nrsd.getStatus();
		porderno = nrsd.getPonumber();
		try {
			supplydate = new SimpleDateFormat("dd MMM, yyyy").parse(nrsd.getSupply_date());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addremark = nrsd.getRemarks();
		
		/*final DropDownChoice<Section> sectiondd = new DropDownChoice<Section>("section", sectionlist,
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
		divisiondd.setNullValid(true);
		divisiondd.setLabel(new Model("Division"));
		divisiondd.setOutputMarkupId(true);
		final FeedbackLabel divisionFeedbackLabel = new FeedbackLabel("divisionfeedback", divisiondd);
		divisionFeedbackLabel.setOutputMarkupId(true);
		form.add(divisionFeedbackLabel);
		form.add(divisiondd);

		DropDownChoice<Circle> circledd = new DropDownChoice<Circle>("circle", circlelist,
				new ChoiceRenderer("circledes", "circleid"));
		circledd.add(new AjaxFormComponentUpdatingBehavior("change") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				circleid = (circle != null) ? circle.getCircleid() : 0;
				section = null;
				subdivision = null;
				division = null;
				target.add(sectiondd);
				target.add(subdivisiondd);
				target.add(divisiondd);

			}

		});
		circledd.setNullValid(false);
		circledd.setRequired(true).setLabel(new Model("Circle"));
		final FeedbackLabel circleFeedbackLabel = new FeedbackLabel("circlefeedback", circledd);
		circleFeedbackLabel.setOutputMarkupId(true);
		form.add(circleFeedbackLabel);
		form.add(circledd);*/

		DropDownChoice<EquipmentType> equipmenttype = new DropDownChoice<EquipmentType>("equipmenttype", equipmentlist,
				new ChoiceRenderer<EquipmentType>("equipmenttypedesc"));
		equipmenttype.setNullValid(false);
		equipmenttype.setRequired(true).setLabel(new Model("Equipment Type"));
		final FeedbackLabel equipmenttypeFeedbackLabel = new FeedbackLabel("equipmenttypefeedback", equipmenttype);
		equipmenttypeFeedbackLabel.setOutputMarkupId(true);
		form.add(equipmenttypeFeedbackLabel);
		
		TextField<String> make = new TextField<String>("make");
		make.setRequired(true).setLabel(new Model("Make"));
		make.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		/* model.add(new StringValidator()); */
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

		
		
		
		final CustomRadioChoice<String> amc = new CustomRadioChoice("amc", amcTYPES);
		amc.setRequired(true).setLabel(new Model("AMC/Warranty"));
		final FeedbackLabel amcFeedbackLabel = new FeedbackLabel("amcfeedback", amc);
		amcFeedbackLabel.setOutputMarkupId(true);
		form.add(amcFeedbackLabel);
		
		final CustomRadioChoice<String> wrkstatus = new CustomRadioChoice("wrkstatus",TYPES);
		wrkstatus.setRequired(true).setLabel(new Model("AMC/Warranty"));
		final FeedbackLabel wrkstatusFeedbackLabel = new FeedbackLabel("wrkstatusfeedback", wrkstatus);
		wrkstatusFeedbackLabel.setOutputMarkupId(true);
		form.add(wrkstatusFeedbackLabel);
		
		TextField<String> porderno = new TextField<String>("porderno");
		porderno.setRequired(true).setLabel(new Model("Purchase Order No."));
		porderno.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		/* model.add(new StringValidator()); */
		final FeedbackLabel pordernoFeedbackLabel = new FeedbackLabel("pordernofeedback", porderno);
		pordernoFeedbackLabel.setOutputMarkupId(true);
		form.add(pordernoFeedbackLabel);
		
		CustromDatePicker datePicker = new CustromDatePicker();
		datePicker.setShowOnFieldClick(true);
		datePicker.setAutoHide(false);

		DateTextField supplydate = new DateTextField("supplydate",
				new PropertyModel<Date>(this, "supplydate"), new PatternDateConverter("dd MMM, yyyy", true));

		/*
		 * DateTextField instaldate = new
		 * DateTextField("installationdate","dd-mm-yyy") { protected String
		 * getInputType() { return "date"; } };
		 */
		supplydate.setRequired(true).setLabel(new Model("Supply Date"));
		final FeedbackLabel supplydatefeed = new FeedbackLabel("supplydatefeed", supplydate);
		supplydatefeed.setOutputMarkupId(true);
		supplydate.add(datePicker);
		form.add(supplydatefeed);
	
		TextArea<String> addremark = new TextArea<String>("addremark");
		addremark.setLabel(new Model("Remark"));
		// remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1,
		// 64));
		/* remark.add(new StringValidator()); */
		final FeedbackLabel addremarkFeedbackLabel = new FeedbackLabel("addremarkfeedback",addremark);
		addremarkFeedbackLabel.setOutputMarkupId(true);
		form.add(addremarkFeedbackLabel);

		Button btnback = new Button("btnback") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				setResponsePage(RollingStockDetails.class);
			}
		}.setDefaultFormProcessing(false);

		Button btnsubmit = new Button("btnsubmit") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if (editRollingStockDetails()) {
					PageParameters parms = new PageParameters();
					RollingStockDetails av = new RollingStockDetails(parms);
					setResponsePage(av);
				}
			}
		}.setDefaultFormProcessing(true);

		form.add(equipmenttype);
		form.add(make);
		form.add(model);
		form.add(serialno);
		form.add(amc);
		form.add(wrkstatus);
		form.add(porderno);
		form.add(supplydate);
		form.add(addremark);
		form.add(feedback);
		form.add(btnback);
		form.add(btnsubmit);
	
		/* form.add(cat2); */
		add(form);

		// TODO Auto-generated constructor stub
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
			stmt.setInt(3, 0);
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				EquipmentType eq = new EquipmentType(rs.getInt(1), rs.getString(2));
				if (nrsd.getEquipmentname().trim().equals(rs.getString(2).trim())) {
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
	

	
	private boolean editRollingStockDetails() {
		final String query = "{call sp_circuit_edit_rolling_stock_details(?,?,?,?,?,?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3,rollingstockid);
			stmt.setInt(4,equipmenttype.getEquipmenttypeid());
			stmt.setString(5,make);
			stmt.setString(6,model);
			stmt.setString(7,serialno);
			stmt.setString(8,amc);
			stmt.setString(9,wrkstatus);
			stmt.setString(10,porderno);
			stmt.setString(11, getFormatDate(supplydate));
			stmt.setString(12, checkNull(addremark));	
			
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			rs = stmt.executeQuery();
			while (rs.next()) {
				this.nlcd.setSpcircuitid(rs.getString(1));
				log.info("Rolling Stock Details Updated Successfully With ID :" + rs.getString(1));
			}
		} catch (Exception e) {
			log.error("SQL Exception in editRollingStockDetails() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in editRollingStockDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return true;
	}

	private List<Circle> loadCircles() {
		final List<Circle> list = new ArrayList<Circle>();
		final String query = "{call sp_get_circles(?,?)}";
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
				list.add(new Circle(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadCircles() method {" + e.getMessage() + "}");
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

	private List<SubDivision> loadSubDivisions(final int circleid, final int divisionid) {
		final List<SubDivision> list = new ArrayList<SubDivision>();
		final String query = "{call sp_division_get_sub_divisions(?,?,?,?)}";
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

	private String getFormatDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
		return simpleDateFormat.format(date);

	}
	
	private String checkNull(String str){
		
		if(str==null)
		  str= "";
		return str ;
	}

	private String getLocationname(Category cat, String opnft, String locationname) {
		/*
		 * System.out.println("In Condition"); System.out.println("Main Con 1:"+
		 * (cat==null)); System.out.println(cat);
		 */
		// System.out.println("Main Con
		// 2:"+(cat.getCatid()==100)+"::"+cat==null);
		if (cat == null) {
			/*
			 * System.out.println("Condition s"+(opnft==null));
			 * System.out.println("Open TF"+opnft);
			 */
			return opnft == null ? locationname : opnft + "-" + locationname;
		} else {
			return opnft == null ? cat.getCatdesc() + "-" + locationname
					: cat.getCatdesc() + "-" + opnft + "-" + locationname;
		}
	}
}

