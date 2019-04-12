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
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.location.ColumnClickPanelNetworkLocationDetail;
import com.sp.location.NetworkLocationDetail;
import com.sp.location.ViewAllNetworkLocationForm;
import com.sp.location.ViewAllNetworkLocationForm.NetworkLocationDataProvider;
import com.sp.location.ViewAllNetworkLocationForm.NetworkLocationList;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.GClickablePropertyColumn;

public class CustomReportForm extends Panel {
	private static final Logger log = Logger.getLogger(CustomReportForm.class);
	private NetworkLocationList ntlclist = new NetworkLocationList();
	final int DEF_NO_OF_ROWS = 9999;
	private boolean circuitid = true;
	private DataTable table;
	private int circuitidsize;
	private boolean projecttype = true;
	private int projecttypesize;
	private boolean locationname = true;
	private int locationnamesize;
	private PropertyColumn p1 = new PropertyColumn(new Model("Circuit ID"), "spciruitcode");
	private PropertyColumn p2 = new PropertyColumn(new Model("Project"), "projecttypedescription");
	private PropertyColumn p3 = new PropertyColumn(new Model("Location Name"), "locationname");
	

	public CustomReportForm(String id) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<CustomReportForm>(this));
		ntlclist.setList(getAllNetworkLocations());
		final NetworkLocationDataProvider nlprovider = new NetworkLocationDataProvider();

		final List<IColumn> columns = new ArrayList<IColumn>();
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
				cell.add(new Label(compId, ""));
			}
		});
		columns.add(p1);
		columns.add(p2);
		columns.add(p3);

		/*
		 * columns.add(new PropertyColumn(new Model("Circle"), "circledesc"));
		 * columns.add(new PropertyColumn(new Model("Division"),
		 * "divisiondesc")); columns.add(new PropertyColumn(new
		 * Model("Sub-Division"), "subdivisiondesc")); columns.add(new
		 * PropertyColumn(new Model("Section"), "sectiondesc")); columns.add(new
		 * PropertyColumn(new Model("Project"), "projecttypedescription"));
		 * columns.add(new PropertyColumn(new Model("Location Name"),
		 * "locationname")); columns.add(new PropertyColumn(new Model("Phase"),
		 * "phase"));
		 */
		/* columns.add(new PropertyColumn(new Model("Remark"), "remark")); */

		table = new DataTable("datatable", columns, nlprovider, DEF_NO_OF_ROWS);
		table.setOutputMarkupId(true);
		table.addTopToolbar(new HeadersToolbar(table, nlprovider));
		add(table);
		final CheckBox circuitid = new CheckBox("circuitid") {
			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(Boolean newSelection) {
				// TODO Auto-generated method stub
				if (newSelection) {
					columns.add(p1);
					replaceTable(columns, nlprovider);

				} else {
					columns.remove(p1);
					replaceTable(columns, nlprovider);
				}
			}
		};
		add(circuitid);
		final CheckBox projecttype = new CheckBox("projecttype") {
			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(Boolean newSelection) {
				// TODO Auto-generated method stub
				if (newSelection) {
					columns.add(p2);
					replaceTable(columns, nlprovider);

				} else {
					columns.remove(p2);
					replaceTable(columns, nlprovider);
				}
			}
		};
		add(projecttype);
		final CheckBox locationname = new CheckBox("locationname") {
			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(Boolean newSelection) {
				// TODO Auto-generated method stub
				if (newSelection) {
					columns.add(p3);
					replaceTable(columns, nlprovider);

				} else {
					columns.remove(p3);
					replaceTable(columns, nlprovider);
				}
			}
		};
		add(locationname);

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
		final String query = "{call sp_get_all_circuits(?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, 0);
			stmt.setInt(4, 0);
			stmt.setInt(5, 0);
			stmt.setInt(6, 0);
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				for(int i=0;i<max(rs.getString(1));i++)
				list.add(new NetworkLocationDetail(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9),
						rs.getString(10)));
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

	private void replaceTable(List<IColumn> columns, NetworkLocationDataProvider nlprovider) {
		DataTable temptable = new DataTable("datatable", columns, nlprovider, DEF_NO_OF_ROWS);
		temptable.setOutputMarkupId(true);
		temptable.addTopToolbar(new HeadersToolbar(table, nlprovider));
		table.replaceWith(temptable);
		table = temptable;
	}
	public  int max(String circuitid) {
		int vc = 0;
		int ec = 0;
		int ic = 0;
		int uc = 0;
		final String query = "{call sp_circuit_get_count(?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setString(3, circuitid);
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				vc = rs.getInt(1); ec = rs.getInt(2); ic = rs.getInt(3); uc = rs.getInt(4);
			}
		} catch (SQLException e) {
			log.error("SQL Exception in max() method {" + e.getMessage() + "}");
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
	    int max = vc;
	    if (ec > max)
	        max = ec;
	    if (ic > max)
	        max = ic;
	    if (uc > max)
	        max = uc;
	     return max;
	}
}
