package com.sp.location;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.location.Circle;
import com.sp.location.ColumnClickPanelNetworkLocationDetail;
import com.sp.location.ColumnEditPanelNetworkVendorDetail;
import com.sp.location.ColumnEditPanelRollingStockDetail;
import com.sp.location.CustromDatePicker;
import com.sp.location.DateTextFieldColumn;
import com.sp.location.Division;
import com.sp.location.DropDownColumn;
import com.sp.location.DropDownColumnAmc;
import com.sp.location.EquipmentType;
import com.sp.location.NetworkLocationDetail;
import com.sp.location.NetworkRollingStockDetail;
import com.sp.location.RadioChoiceColumn;
import com.sp.location.Section;
import com.sp.location.SubDivision;
import com.sp.location.TextFieldColumn;
import com.sp.location.ViewEquipmentDetail;
import com.sp.location.ViewNetworkLocationDetail;
import com.sp.master.ApspdclMaster;
import com.sp.resource.CustomRadioChoice;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.FeedbackLabel;
import com.sp.resource.GClickablePropertyColumn;
import com.sp.resource.GTextFieldPropertyColumn;

public class AddRollingStockDetailsForm extends Panel {
	private static final Logger log = Logger.getLogger(AddRollingStockDetailsForm.class);
	private Circle circle;
	private String circlefeedback;
	private int circleid;
	private EquipmentType equipmenttype;
	private String equipmenttypefeedback;
	private int equipmentid ;
	private EquipmentType equipment;
	private String equipmentfeedback;
	private String addmake;
	private String addmakefeedback;
	private String addmodel;
	private String addmodelfeedback;
	private String addserialno ;
	private String addserialnofeedback ;
	private String addporderno ;
	private String addpordernofeedback ;
	private String addremark ;
	private String addremarkfeedback ;
	private Date supplydate ;
	private String supplydatefeedback ;
	private String amc;
	private String amcfeedback;
	private String wrkstatus;
	private String wrkstatusfeedback;
	private boolean mymodalflag;
	private RollingStockList nrsdlist = new RollingStockList();
	private NetworkRollingStockDetail nrsd ; 
	final int DEF_NO_OF_ROWS = 9999;
	private static String PATTERN = "yyyy-MM-dd";
	private static final List<String> TYPES = Arrays.asList("AMC", "Warranty", "None");
	private static final List<String> WRKSTATUSTYPES = Arrays.asList("Yes", "No");
	
	IModel<? extends List<Circle>> circlelist = new LoadableDetachableModel<List<Circle>>() {
		@Override
		protected List<Circle> load() {
			// TODO Auto-generated method stub
			return loadCircles();
		}
	};
	
	IModel<? extends List<EquipmentType>> equipmenttypelist = new LoadableDetachableModel<List<EquipmentType>>() {

		@Override
		protected List<EquipmentType> load() {
			// TODO Auto-generated method stub
			return loadEquipmentTypes(false);
		}
	};
	
	IModel<? extends List<EquipmentType>> equipmentlist = new LoadableDetachableModel<List<EquipmentType>>() {

		@Override
		protected List<EquipmentType> load() {
			// TODO Auto-generated method stub
			return loadEquipmentTypes(true);
		}
	};

	
	
	public AddRollingStockDetailsForm(String id) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<AddRollingStockDetailsForm>(this));
		StatelessForm<Form> form = new StatelessForm("form");
		
		
		WebMarkupContainer replacediv = new WebMarkupContainer("replacediv");
		//replacediv.setVisible(ned.getIsreplace() == 1 ? true : false);
		form.add(replacediv);
		
		WebMarkupContainer mymodal = new WebMarkupContainer("mymodal") {
			@Override
			public boolean isVisible() {
				// TODO Auto-generated method stub
				return mymodalflag;
			}
		};
		
	
		
		DropDownChoice<EquipmentType> equipment = new DropDownChoice<EquipmentType>("equipment", equipmentlist,
				new ChoiceRenderer<EquipmentType>("equipmenttypedesc")){
			@Override
			protected CharSequence getDefaultChoice(String selectedValue) {
				// TODO Auto-generated method stub
				return "";
			}

			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(EquipmentType newSelection) {
				// TODO Auto-generated method stub
				
				equipmentid=newSelection.getEquipmenttypeid();
				
				nrsdlist.setList(networkGetRollingStock());
				
				//System.out.println("equipmentid"+equipmentid);
				
			}
		};
		
		equipment.setNullValid(false);
		equipment.setRequired(true).setLabel(new Model("Equipment Type"));
		final FeedbackLabel equipmentFeedbackLabel = new FeedbackLabel("equipmentfeedback", equipment);
		equipmentFeedbackLabel.setOutputMarkupId(true);
		form.add(equipmentFeedbackLabel);
		
		DropDownChoice<Circle> circle = new DropDownChoice<Circle>("circle", circlelist,
				new ChoiceRenderer("circledes", "circleid")){
			@Override
			protected CharSequence getDefaultChoice(String selectedValue) {
				// TODO Auto-generated method stub
				return "";
			}

			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(Circle newSelection) {
				// TODO Auto-generated method stub
				
				circleid=newSelection.getCircleid();
				
				nrsdlist.setList(networkGetRollingStock());
				
				//System.out.println("equipmentid"+equipmentid);
				
			}
		};
		circle.setNullValid(false);
		circle.setRequired(true).setLabel(new Model("Circle"));
		final FeedbackLabel circleFeedbackLabel = new FeedbackLabel("circlefeedback", circle);
		circleFeedbackLabel.setOutputMarkupId(true);
		form.add(circleFeedbackLabel);
		

		
		
		
		
		DropDownChoice<EquipmentType> equipmenttype = new DropDownChoice<EquipmentType>("equipmenttype", equipmenttypelist,
				new ChoiceRenderer<EquipmentType>("equipmenttypedesc"));
		equipmenttype.setNullValid(false);
		equipmenttype.setRequired(true).setLabel(new Model("Equipment Type"));
		final FeedbackLabel equipmenttypeFeedbackLabel = new FeedbackLabel("equipmenttypefeedback", equipmenttype);
		equipmenttypeFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(equipmenttypeFeedbackLabel);
		
		TextField<String> addmake = new TextField<String>("addmake");
		addmake.setRequired(true).setLabel(new Model("Make"));
		addmake.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* rmake.add(new StringValidator()); */
		final FeedbackLabel rmakeFeedbackLabel = new FeedbackLabel("addmakefeedback", addmake);
		rmakeFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(rmakeFeedbackLabel);
		
		TextField<String> addmodel = new TextField<String>("addmodel");
		addmodel.setRequired(true).setLabel(new Model("Model"));
		addmodel.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* rmake.add(new StringValidator()); */
		final FeedbackLabel addmodelFeedbackLabel = new FeedbackLabel("addmodelfeedback", addmodel);
		addmodelFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(addmodelFeedbackLabel);
		
		TextField<String> addserialno = new TextField<String>("addserialno");
		addserialno.setRequired(true).setLabel(new Model("Serial No"));
		addserialno.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* rmake.add(new StringValidator()); */
		final FeedbackLabel addserialnoFeedbackLabel = new FeedbackLabel("addserialnofeedback", addserialno);
		addserialnoFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(addserialnoFeedbackLabel);
		
		final CustomRadioChoice<String> amc = new CustomRadioChoice("amc", TYPES);
		amc.setRequired(true).setLabel(new Model("AMC/Warranty"));
		final FeedbackLabel amcFeedbackLabel = new FeedbackLabel("amcfeedback", amc);
		amcFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(amcFeedbackLabel);
		
		final CustomRadioChoice<String> wrkstatus = new CustomRadioChoice("wrkstatus", WRKSTATUSTYPES);
		wrkstatus.setRequired(true).setLabel(new Model("Working Status"));
		final FeedbackLabel wrkstatusFeedbackLabel = new FeedbackLabel("wrkstatusfeedback", wrkstatus);
		wrkstatusFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(wrkstatusFeedbackLabel);
		
		TextField<String> addporderno = new TextField<String>("addporderno");
		addporderno.setRequired(true).setLabel(new Model("Purchase Order No"));
		addporderno.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* rmake.add(new StringValidator()); */
		final FeedbackLabel addpordernoFeedbackLabel = new FeedbackLabel("addpordernofeedback", addporderno);
		addpordernoFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(addpordernoFeedbackLabel);
		
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
		final FeedbackLabel supplydatefeedback = new FeedbackLabel("supplydatefeedback", supplydate);
		supplydate.setOutputMarkupId(true);
		supplydate.add(datePicker);
		mymodal.add(supplydatefeedback);
		mymodal.add(supplydate);
		
		
		TextArea<String> addremark = new TextArea<String>("addremark");
		addremark.setLabel(new Model("Remark"));
		// remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1,
		// 64));
		/* remark.add(new StringValidator()); */
		final FeedbackLabel addremarkFeedbackLabel = new FeedbackLabel("addremarkfeedback",addremark);
		addremarkFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(addremarkFeedbackLabel);
		
		
		nrsdlist.setList(networkGetRollingStock());
		NetworkLocationDataProvider nlprovider = new NetworkLocationDataProvider();
		
		List<IColumn> columns = new ArrayList<IColumn>();
		columns.add(new AbstractColumn(new Model("Sr. No.")) {
			public void populateItem(Item cell, String compId, IModel rowModel) {
				int pageNumber, noRows;
				DataTable tbl = (DataTable) get("datatable");
				if (tbl != null) {
					pageNumber = (int) tbl.getCurrentPage();
					noRows = (int) tbl.getItemsPerPage();
				} else {
					pageNumber = 0;
					noRows = 0;
				}
				int rowNumber = (pageNumber * noRows) + ((Item) cell.getParent().getParent()).getIndex() + 1;
				cell.add(new Label(compId, rowNumber));
			}
		});
		/*
		 * columns.add(new GClickablePropertyColumn(new Model("Circuit ID"),
		 * "spciruitcode") { public void populateItem(Item item, String
		 * componentId, IModel rowModel) { item.add(new
		 * ColumnClickPanelNetworkLocationDetail(componentId, rowModel, new
		 * PropertyModel(rowModel, getProperty()))); } });
		 */
		/*
		 * columns.add(new PropertyColumn(new Model("Circuit ID"),
		 * "spcircuitid"));
		 * 
		 */
		
		columns.add(new PropertyColumn(new Model("Circle"), "circledesciption"));
		columns.add(new PropertyColumn(new Model("Equipment Name"), "equipmentname"));
		columns.add(new PropertyColumn(new Model("Make"), "make"));
		columns.add(new PropertyColumn(new Model("Model"), "model"));	
		columns.add(new PropertyColumn(new Model("Serial No"), "serialno"));
		columns.add(new PropertyColumn(new Model("AMC/Warranty"), "amc"));
		columns.add(new PropertyColumn(new Model("Working Status"), "status"));
		columns.add(new PropertyColumn(new Model("Purchase Order No"), "ponumber"));
		columns.add(new PropertyColumn(new Model("Supply Date"), "supply_date"));
		columns.add(new PropertyColumn(new Model("Remark"), "remarks"));
		
		if (((PortSession) getSession()).isAdmin()) {
			columns.add(new GClickablePropertyColumn(new Model(""), "rolling_stock_id") {
				public void populateItem(Item item, String componentId, IModel rowModel) {
					item.add(new ColumnEditPanelRollingStockDetail(componentId, rowModel,
							new PropertyModel(rowModel, getProperty()),
							new CompoundPropertyModel<NetworkRollingStockDetail>(nrsd)));
				}
			});
		}
	
		final DataTable table = new DataTable("datatable", columns, nlprovider, DEF_NO_OF_ROWS);
		table.setOutputMarkupId(true);
		table.addTopToolbar(new HeadersToolbar(table, nlprovider));
		replacediv.add(table);

		
		/*
		 * sectiondd.add(new AjaxFormComponentUpdatingBehavior("onChange") {
		 * 
		 * @Override protected void onUpdate(AjaxRequestTarget target) { // TODO
		 * Auto-generated method stub sectionid = section.getSectionid();
		 * ntlclist.setList(getAllNetworkLocations()); target.add(table); }
		 * 
		 * });
		 */
		

		
		/*
		 * subdivisiondd.add(new AjaxFormComponentUpdatingBehavior("onChange") {
		 * 
		 * @Override protected void onUpdate(AjaxRequestTarget target) { // TODO
		 * Auto-generated method stub subdivisionid =
		 * subdivision.getSubdivisionid(); sectionid = 0;
		 * ntlclist.setList(getAllNetworkLocations()); section = null;
		 * target.add(table); target.add(sectiondd); }
		 * 
		 * });
		 */
	
		/* subdivisiondd.setOutputMarkupId(true); */

		
		/*
		 * divisiondd.add(new AjaxFormComponentUpdatingBehavior("onChange") {
		 * 
		 * @Override protected void onUpdate(AjaxRequestTarget target) { // TODO
		 * Auto-generated method stub divisionid = division.getDivisionid();
		 * subdivisionid = 0; sectionid = 0;
		 * ntlclist.setList(getAllNetworkLocations()); section = null;
		 * subdivision = null; target.add(table); target.add(sectiondd);
		 * target.add(subdivisiondd); }
		 * 
		 * });
		 */
	
	
		/*
		 * circledd.add(new AjaxFormComponentUpdatingBehavior("onChange") {
		 * 
		 * @Override protected void onUpdate(AjaxRequestTarget target) { // TODO
		 * Auto-generated method stub circleid = circle.getCircleid();
		 * divisionid = 0; subdivisionid = 0; sectionid = 0;
		 * ntlclist.setList(getAllNetworkLocations()); section = null;
		 * subdivision = null; division = null; target.add(table);
		 * target.add(sectiondd); target.add(subdivisiondd);
		 * target.add(divisiondd); }
		 * 
		 * });
		 */
		

		Button btnback = new Button("btnback") {
			public void onSubmit() {
				setResponsePage(ApspdclMaster.class);
			};
		}.setDefaultFormProcessing(false);
		
		
		
		
		Button replace = (Button) new Button("replace") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				mymodalflag = true;
			}
		};
		
		Button btnback1 = new Button("back") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				PageParameters params = new PageParameters();
				AddRollingStockDetails vad = new AddRollingStockDetails(params);
				setResponsePage(vad);

			}
		}.setDefaultFormProcessing(false);
		Button btn = new Button("submit") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if (networkAddRollingStock()) {
					PageParameters params = new PageParameters();
					AddRollingStockDetails rsd = new AddRollingStockDetails(params);
					setResponsePage(rsd);
				}
			}
		};
		mymodal.add(equipmenttype);
		
		mymodal.add(addmake);
		mymodal.add(addmodel);	
		mymodal.add(addserialno);
		mymodal.add(amc);
		mymodal.add(wrkstatus);
		mymodal.add(addporderno);
		mymodal.add(addremark);
		mymodal.add(btn);
		mymodal.add(btnback1);
		form.add(equipment);
		form.add(circle);
		form.add(btnback);
		form.add(replace);
		form.add(mymodal);
		add(form);

	}

	public class NetworkLocationDataProvider extends SortableDataProvider implements Serializable {

		public NetworkLocationDataProvider() {
			// TODO Auto-generated constructor stub
			setSort("spcircuidid", SortOrder.ASCENDING);
		}

		@Override
		public Iterator iterator(long first, long count) {
			// TODO Auto-generated method stub
			Collections.sort(nrsdlist.getList(), new Comparator<NetworkRollingStockDetail>() {

				@Override
				public int compare(NetworkRollingStockDetail arg0, NetworkRollingStockDetail arg1) {
					// TODO Auto-generated method stub
					int dir = getSort().isAscending() ? 1 : -1;
					return dir * (arg0.getEquipmentname().compareTo(arg1.getEquipmentname()));
				}
			});
			return nrsdlist.selectList((int) first, (int) count).iterator();
		}

		@Override
		public IModel model(Object arg0) {
			// TODO Auto-generated method stub
			NetworkRollingStockDetail nld = (NetworkRollingStockDetail) arg0;
			return new Model((Serializable) nld);
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return nrsdlist.getList().size();
		}

	}

	public class RollingStockList implements Serializable {
		private List<NetworkRollingStockDetail> list;

		public RollingStockList() {
			// TODO Auto-generated constructor stub
			list = new ArrayList<NetworkRollingStockDetail>();
		}

		public void addItem(NetworkRollingStockDetail t) {
			list.add(t);
		}

		@SuppressWarnings("rawtypes")
		public List getList() {
			return list;
		}

		public void setList(List<NetworkRollingStockDetail> l) {
			list = l;
		}

		@SuppressWarnings("rawtypes")
		public List selectList(int first, int count) {
			return list.subList(first, first + count);
		}
	}

	public List<NetworkRollingStockDetail> networkGetRollingStock() {
		final List<NetworkRollingStockDetail> list = new ArrayList<NetworkRollingStockDetail>();
		
		final String query = "{call sp_get_rolling_stock_report(?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3,((PortSession)this.getSession()).getCircleid());
			stmt.setInt(4,equipmentid);
			stmt.setInt(5, circleid);
			/*System.out.println(" session circleid "+((PortSession)this.getSession()).getCircleid());
			System.out.println(" equipmentid "+equipmentid);
			System.out.println(" circleid "+circleid);*/
		
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored  { " + stmt.toString() + " }"));
				
			while (rs.next()) {
				
				list.add(new NetworkRollingStockDetail(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getInt(10),rs.getString(11)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in loadProjectTypes() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	private boolean networkAddRollingStock() {
		final String query = "{call sp_circuit_add_rolling_stock_details(?,?,?,?,?,?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, equipmenttype.getEquipmenttypeid());
			stmt.setString(4, addmake);
			stmt.setString(5, addmodel);
			stmt.setString(6, addserialno);
			stmt.setString(7, amc);		
			stmt.setString(8, wrkstatus);
			stmt.setString(9, addporderno);
			stmt.setString(10, getFormatDate(supplydate));
			stmt.setString(11, checkNull(addremark));
			stmt.setInt(12,((PortSession) this.getSession()).getCircleid());
			
			/*
			 System.out.println(" equipmenttype " +equipmenttype.getEquipmenttypeid());
			System.out.println(" addmake " +addmake);
			System.out.println(" addmodel " +addmodel);
			System.out.println(" addserialno " +addserialno);
			System.out.println(" amc " +amc);
			System.out.println(" wrkstatus " +wrkstatus);
			System.out.println(" addporderno " +addporderno);
			System.out.println(" supplydate " +getFormatDate(supplydate));
			System.out.println(" addremark " +checkNull(addremark));
			*/
			
			
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				log.info("Network Rolling Stock Details Added Successfully With ID :" + rs.getInt(1));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in networkAddRollingStock() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in networkAddRollingStock() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return true;
	}
	


	private List<Circle> loadCircles() {
		final List<Circle> list = new ArrayList<Circle>();
		list.add(new Circle(0, "All", "ALL"));
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
			log.error((Object) ("SQL Exception in loadCircles() method {" + e.getMessage() + "}"));
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
				log.error("SQL Exception in loadCircles() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}

	private List<Division> loadDivisions(final int circleid) {
		final List<Division> list = new ArrayList<Division>();
		list.add(new Division(0, "All", "ALL"));
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
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
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
				log.error("SQL Exception in loadDivisions() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}

	private List<SubDivision> loadSubDivisions(final int circleid, final int divisionid) {
		final List<SubDivision> list = new ArrayList<SubDivision>();
		list.add(new SubDivision(0, "All", "ALL"));
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
		list.add(new Section(0, "All", "ALL"));
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
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
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
				log.error("SQL Exception in loadSections() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	private boolean updateRollingStockDetais(RollingStockList nrsdlist){
		
		final String query = "{call sp_circuit_update_rolling_stock(?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
        for(int i=0;i<nrsdlist.getList().size();i++){
			
			NetworkRollingStockDetail nrsd = (NetworkRollingStockDetail) nrsdlist.getList().get(i);
			
			//System.out.println(nrsd.getNetworkequipid()+nrsd.getRemarks()+":"+nrsd.getPonumber()+":"+nrsd.getStatus()+":"+nrsd.getSupplydate());
			
		   try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, nrsd.getNetworkequipid());
			stmt.setString(4, checkNull(nrsd.getStatus()));
			stmt.setString(5,  checkNull(nrsd.getPonumber()));
			if(nrsd.getSupplydate()==null)
			stmt.setString(6,  null);
			else
			/*stmt.setString(6,  checkNull(getFormatDate(nrsd.getSupplydate())));*/
			stmt.setString(7,  checkNull(nrsd.getRemarks()));
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
		} catch (SQLException e) {
			log.error("SQL Exception in loadSections() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in loadSections() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		
		
		
		
			
		}
		
		
	
		return true ;
	}
	
	private List<EquipmentType> loadEquipmentTypes(boolean all) {
		List<EquipmentType> list = new ArrayList<EquipmentType>();
		String query = "{call sp_get_equipment_types(?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		if(all)
		list.add(new EquipmentType(0,"ALL"));
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setInt(3,0);
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			
			while (rs.next()) {
				list.add(new EquipmentType(rs.getInt(1), rs.getString(2)));
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
	
	private String checkNull(String str){
		
		if(str==null)
		  str= "";
		return str ;
	}
}
