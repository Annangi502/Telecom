package com.sp.report;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
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
import com.sp.location.Circle;
import com.sp.location.ColumnClickPanelNetworkLocationDetail;
import com.sp.location.Division;
import com.sp.location.ERO;
import com.sp.location.NetworkLocationDetail;
import com.sp.location.Section;
import com.sp.location.SubDivision;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.GClickablePropertyColumn;

public class MergeNetworkReportForm extends Panel {
	private static final Logger log = Logger.getLogger(MergeNetworkReportForm.class);
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
	final int DEF_NO_OF_ROWS = 9999;
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

	public MergeNetworkReportForm(String id) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<MergeNetworkReportForm>(this));
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
		 */
		columns.add(new PropertyColumn(new Model("Location Name"), "locationname"));
		columns.add(new PropertyColumn(new Model("Circle"), "circledesc"));
		columns.add(new PropertyColumn(new Model("Division"), "divisiondesc"));
		columns.add(new PropertyColumn(new Model("ERO"), "erodesc"));
		columns.add(new PropertyColumn(new Model("Sub-Division"), "subdivisiondesc"));
		columns.add(new PropertyColumn(new Model("Section"), "sectiondesc"));
		columns.add(new PropertyColumn(new Model("Project"), "projecttypedescription"));	
		columns.add(new PropertyColumn(new Model("Merge Locations"), "mergelocationdesc"));
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
		
		final DropDownChoice<ERO> erodd = new DropDownChoice<ERO>("ero",
				erolist, new ChoiceRenderer("erodes", "eroid")) {
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
				subdivisionid = 0 ;
				sectionid = 0;
				ntlclist.setList(getAllNetworkLocations());
				section = null;
				subdivision = null ;
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
		erodd.setNullValid(false);
		erodd.setLabel(new Model("ERO"));
		/* subdiviiondd.setOutputMarkupId(true); */
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
				eroid  = 0 ;
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
				eroid = 0;
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

		Button btnback = new Button("btnback") {
			public void onSubmit() {
				setResponsePage(Report.class);
			};
		}.setDefaultFormProcessing(false);
		form.add(btnback);
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
			Collections.sort(ntlclist.getList(), new Comparator<NetworkLocationDetail>() {

				@Override
				public int compare(NetworkLocationDetail arg0, NetworkLocationDetail arg1) {
					// TODO Auto-generated method stub
					int dir = getSort().isAscending() ? 1 : -1;
					return dir * (arg0.getSpcircuitid().compareTo(arg1.getSpcircuitid()));
				}
			});
			return ntlclist.selectList((int) first, (int) count).iterator();
		}

		@Override
		public IModel model(Object arg0) {
			// TODO Auto-generated method stub
			NetworkLocationDetail nld = (NetworkLocationDetail) arg0;
			return new Model((Serializable) nld);
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return ntlclist.getList().size();
		}

	}

	public class NetworkLocationList implements Serializable {
		private List<NetworkLocationDetail> list;

		public NetworkLocationList() {
			// TODO Auto-generated constructor stub
			list = new ArrayList<NetworkLocationDetail>();
		}

		public void addItem(NetworkLocationDetail t) {
			list.add(t);
		}

		@SuppressWarnings("rawtypes")
		public List getList() {
			return list;
		}

		public void setList(List<NetworkLocationDetail> l) {
			list = l;
		}

		@SuppressWarnings("rawtypes")
		public List selectList(int first, int count) {
			return list.subList(first, first + count);
		}
	}

	public List<NetworkLocationDetail> getAllNetworkLocations() {
		final List<NetworkLocationDetail> list = new ArrayList<NetworkLocationDetail>();
		final String query = "{call sp_get_all_merge_locations(?,?,?,?,?,?,?)}";
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
			stmt.setInt(7, this.eroid);
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				list.add(new NetworkLocationDetail(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadProjectTypes() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}
	private List<SubDivision> loadSubDivisions(final int circleid, final int divisionid, final int eroid) {
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
			stmt.setInt(5, eroid);
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
				log.error("SQL Exception in loadEro() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}
}