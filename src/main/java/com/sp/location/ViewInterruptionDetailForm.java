package com.sp.location;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.location.ViewAllNetworkLocationForm.NetworkLocationDataProvider;
import com.sp.location.ViewAllNetworkLocationForm.NetworkLocationList;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.GClickablePropertyColumn;

public class ViewInterruptionDetailForm  extends Panel{
	
	private static final Logger log = Logger.getLogger(ViewInterruptionDetailForm.class);
	private Circle circle;
	private int circleid = 0;
	private Division division;
	private int divisionid = 0;
	private int eroid = 0 ;
	private ERO ero ;
	private SubDivision subdivision;
	private int subdivisionid = 0;
	private Section section;
	private int sectionid = 0;
	private NetworkLocationList ntlclist = new NetworkLocationList();
	private InterruptionDetails nld;
	final int DEF_NO_OF_ROWS = 9999;
	private  Date date2 ;
	private  Date date3 ;
	
	IModel<? extends List<Circle>> circlelist = new LoadableDetachableModel<List<Circle>>() {
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

	public ViewInterruptionDetailForm(String id) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<ViewInterruptionDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm("form");
		ntlclist.setList(getAllNetworkLocations());
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
		columns.add(new PropertyColumn(new Model("Circuit ID"), "circuitid"));
		columns.add(new PropertyColumn(new Model("Location Name"), "locationname"));
		columns.add(new PropertyColumn(new Model("Vendor Name"), "vendorname"));
		columns.add(new PropertyColumn(new Model("Link Down Time"), "linkdowntime"));
		columns.add(new PropertyColumn(new Model("Link Up Time"), "linkuptime"));
		columns.add(new PropertyColumn(new Model("Call Log"), "calllog"));
		if (((PortSession) getSession()).isAdmin()) {
			columns.add(new GClickablePropertyColumn(new Model("Edit"), "circuitid") {
				public void populateItem(Item item, String componentId, IModel rowModel) {
					item.add(new ColumnEditPanelInterruptionDetails(componentId, rowModel,
							new PropertyModel(rowModel, getProperty()),
							new CompoundPropertyModel<InterruptionDetails>(nld)));
				}
			});
		}
		/* columns.add(new PropertyColumn(new Model("Remark"), "remark")); */
		final DataTable table = new DataTable("datatable", columns, nlprovider, DEF_NO_OF_ROWS);
		table.setOutputMarkupId(true);
		table.addTopToolbar(new HeadersToolbar(table, nlprovider));
		form.add(table);

		final DropDownChoice<Section> sectiondd = new DropDownChoice<Section>("section", sectionlist,
				new ChoiceRenderer("sectiondesc", "sectionid")) {
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
			protected void onSelectionChanged(Section newSelection) {
				// TODO Auto-generated method stub
				sectionid = section.getSectionid();
				ntlclist.setList(getAllNetworkLocations());
			}
		};
		/*
		 * sectiondd.add(new AjaxFormComponentUpdatingBehavior("onChange") {
		 * 
		 * @Override protected void onUpdate(AjaxRequestTarget target) { // TODO
		 * Auto-generated method stub sectionid = section.getSectionid();
		 * ntlclist.setList(getAllNetworkLocations()); target.add(table); }
		 * 
		 * });
		 */
		sectiondd.setNullValid(false);
		sectiondd.setLabel(new Model("Section"));
		/* sectiondd.setOutputMarkupId(true); */
		form.add(sectiondd);

		final DropDownChoice<SubDivision> subdivisiondd = new DropDownChoice<SubDivision>("subdivision",
				subdivisionlist, new ChoiceRenderer("subdivisiondesc", "subdivisionid")) {
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
			protected void onSelectionChanged(SubDivision newSelection) {
				// TODO Auto-generated method stub
				subdivisionid = subdivision.getSubdivisionid();
				sectionid = 0;
				ntlclist.setList(getAllNetworkLocations());
				section = null;
			}
		};
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
		subdivisiondd.setNullValid(false);
		subdivisiondd.setLabel(new Model("Sub-Division"));
		/* subdivisiondd.setOutputMarkupId(true); */
		
		final DropDownChoice<ERO> erodd = new DropDownChoice<ERO>("ero", erolist,
				new ChoiceRenderer("erodes", "eroid")) {
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
			protected void onSelectionChanged(ERO newSelection) {
				// TODO Auto-generated method stub
				
				eroid = ero.getEroid() ;
				subdivisionid = 0;
				sectionid = 0;
				ntlclist.setList(getAllNetworkLocations());
				section = null;
				subdivision = null;
				
			}
		};
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
		erodd.setNullValid(false);
		erodd.setLabel(new Model("ERO"));
		/* divisiondd.setOutputMarkupId(true); */
		form.add(erodd);

		final DropDownChoice<Division> divisiondd = new DropDownChoice<Division>("division", divisionlist,
				new ChoiceRenderer("divisiondesc", "divisionid")) {
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
			protected void onSelectionChanged(Division newSelection) {
				// TODO Auto-generated method stub
				divisionid = division.getDivisionid();
				eroid = 0 ;
				subdivisionid = 0;
				sectionid = 0;
				ntlclist.setList(getAllNetworkLocations());
				section = null;
				subdivision = null;
				ero = null ;
			}
		};
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
		divisiondd.setNullValid(false);
		divisiondd.setLabel(new Model("Division"));
		/* divisiondd.setOutputMarkupId(true); */
		form.add(divisiondd);

		DropDownChoice<Circle> circledd = new DropDownChoice<Circle>("circle", circlelist,
				new ChoiceRenderer("circledes", "circleid")) {
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
				circleid = circle.getCircleid();
				divisionid = 0;
				eroid = 0 ;
				subdivisionid = 0;
				sectionid = 0;
				ntlclist.setList(getAllNetworkLocations());
				section = null;
				subdivision = null;
				ero = null ;
				division = null;
			}
		};
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
		circledd.setNullValid(false);
		circledd.setRequired(true).setLabel(new Model("Circle"));
		form.add(circledd);
		form.add(subdivisiondd);
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
			Collections.sort(ntlclist.getList(), new Comparator<InterruptionDetails>() {

				@Override
				public int compare(InterruptionDetails arg0, InterruptionDetails arg1) {
					// TODO Auto-generated method stub
					int dir = getSort().isAscending() ? 1 : -1;
					return dir * (arg0.getCircuitid().compareTo(arg1.getCircuitid()));
				}
			});
			return ntlclist.selectList((int) first, (int) count).iterator();
		}

		@Override
		public IModel model(Object arg0) {
			// TODO Auto-generated method stub
			InterruptionDetails nld = (InterruptionDetails) arg0;
			return new Model((Serializable) nld);
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return ntlclist.getList().size();
		}

	}

	public class NetworkLocationList implements Serializable {
		private List<InterruptionDetails> list;

		public NetworkLocationList() {
			// TODO Auto-generated constructor stub
			list = new ArrayList<InterruptionDetails>();
		}

		public void addItem(InterruptionDetails t) {
			list.add(t);
		}

		@SuppressWarnings("rawtypes")
		public List getList() {
			return list;
		}

		public void setList(List<InterruptionDetails> l) {
			list = l;
		}

		@SuppressWarnings("rawtypes")
		public List selectList(int first, int count) {
			return list.subList(first, first + count);
		}
	}

	public List<InterruptionDetails> getAllNetworkLocations() {
		final List<InterruptionDetails> list = new ArrayList<InterruptionDetails>();
		final String query = "{call sp_get_interruption_details(?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, this.circleid);
			stmt.setInt(4, this.divisionid);
			stmt.setInt(5, this.subdivisionid);
			stmt.setInt(6, this.sectionid);
			stmt.setInt(7, eroid);
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				
				   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				    date2 = sdf.parse(rs.getString(4));
				    long millis = date2.getTime();		    
				    date2 = new Date(millis);
				    
				    date3 = sdf.parse(rs.getString(5));
				    long millis1 = date3.getTime();		    
				    date3 = new Date(millis1);
				    
				    ZonedDateTime start = ZonedDateTime.ofInstant(date2.toInstant(), ZoneId.systemDefault());
				       ZonedDateTime end = ZonedDateTime.ofInstant(date3.toInstant(), ZoneId.systemDefault());

				       // get the total duration of minutes between them
				       Duration total = Duration.ofMinutes(ChronoUnit.MINUTES.between(start, end));

				       // use the duration to determine the hours and minutes values
				       long hours = Math.abs(total.toHours());
				       
				       //System.out.println(" hours "+hours);
				
				    DateFormat dateFormatMDY = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			        String linkdown = dateFormatMDY.format(date2);
			        String linkup = dateFormatMDY.format(date3);
			        
			        
				
				list.add(new InterruptionDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6),hours));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in getAllNetworkLocations() method {" + e.getMessage() + "}");
			e.printStackTrace();
			return list;
		}
		 catch (Exception e) {
				log.error("SQL Exception in getAllNetworkLocations() method {" + e.getMessage() + "}");
				e.printStackTrace();
				return list;
			}finally {
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
				log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
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
				log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}

	private List<SubDivision> loadSubDivisions(final int circleid, final int divisionid,final int eroid) {
		final List<SubDivision> list = new ArrayList<SubDivision>();
		list.add(new SubDivision(0, "All", "ALL"));
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
				log.error("SQL Exception  method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	private List<ERO> loadEro(final int circleid, final int divisionid) {
		final List<ERO> list = new ArrayList<ERO>();
		list.add(new ERO(0, "All", "ALL"));
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

}
