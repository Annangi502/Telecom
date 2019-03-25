package com.sp.report;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.location.AddNetworkLocationDetailForm;
import com.sp.location.Circle;
import com.sp.location.ProjectType;
import com.sp.location.Section;
import com.sp.location.Vendor;
import com.sp.resource.DataBaseConnection;

public class LinkAbstractReportForm extends Panel {
	private static final Logger log = Logger.getLogger(LinkAbstractReportForm.class);
	private HashMap<Integer, Integer> hm;
	private Circle circle;
	private int circleid;
	private ListView<VendorDetail> vendors;
	private ListView<Integer> ttllist;
	private Label lblttl;
	IModel<? extends List<Circle>> circlelist = new LoadableDetachableModel<List<Circle>>() {
		@Override
		protected List<Circle> load() {
			// TODO Auto-generated method stub
			return loadCircles();
		}
	};

	public LinkAbstractReportForm(String id) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<LinkAbstractReportForm>(this));
		hm = new HashMap<Integer, Integer>();
		ListView<ProjectType> projecttypes = new ListView<ProjectType>("projecttypes", loadProjectTypes()) {
			@Override
			protected void populateItem(ListItem<ProjectType> item) {
				/* WebMarkupContainer wmc = new WebMarkupContainer("wmc"); */
				item.add(new Label("projectname", item.getModelObject().getProjecttypedescription()));
				// item.add(wmc);
			}
		};

		vendors = new ListView<VendorDetail>("vendors", loadVendors()) {

			@Override
			protected void populateItem(ListItem<VendorDetail> item) {
				// TODO Auto-generated method stub
				item.add(new Label("vendorname", item.getModelObject().getVendorname()));
				ListView<ProjectDetail> projects = new ListView<ProjectDetail>("vendorprojects",
						item.getModelObject().getProject()) {

					@Override
					protected void populateItem(ListItem<ProjectDetail> item) {
						// TODO Auto-generated method stub
						item.add(new Label("count", item.getModelObject().getCount()));
					}
				};
				item.add(new Label("total", item.getModelObject().getTotal()));
				item.add(projects);

			}
		};

		ttllist = new ListView<Integer>("ttllist", new ArrayList<Integer>(hm.values())) {
			@Override
			protected void populateItem(ListItem<Integer> item) {
				// TODO Auto-generated method stub
				item.add(new Label("projecttotal", item.getModelObject()));
			}
		};

		int grandtotal = 0;
		for (Integer val : hm.values()) {
			grandtotal += val;
		}
		lblttl = new Label("grandtotal", grandtotal);

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
				hm = new HashMap<Integer, Integer>();
				circleid = newSelection.getCircleid();
				ListView<VendorDetail> tempvendors = new ListView<VendorDetail>("vendors", loadVendors()) {

					@Override
					protected void populateItem(ListItem<VendorDetail> item) {
						// TODO Auto-generated method stub
						item.add(new Label("vendorname", item.getModelObject().getVendorname()));
						ListView<ProjectDetail> projects = new ListView<ProjectDetail>("vendorprojects",
								item.getModelObject().getProject()) {

							@Override
							protected void populateItem(ListItem<ProjectDetail> item) {
								// TODO Auto-generated method stub
								item.add(new Label("count", item.getModelObject().getCount()));
							}
						};
						item.add(new Label("total", item.getModelObject().getTotal()));
						item.add(projects);

					}
				};

				vendors.replaceWith(tempvendors);
				vendors = tempvendors;

				ListView<Integer> tempttllist = new ListView<Integer>("ttllist", new ArrayList<Integer>(hm.values())) {
					@Override
					protected void populateItem(ListItem<Integer> item) {
						// TODO Auto-generated method stub
						item.add(new Label("projecttotal", item.getModelObject()));
					}
				};

				ttllist.replaceWith(tempttllist);
				ttllist = tempttllist;

				int grandtotal = 0;
				for (Integer val : hm.values()) {
					grandtotal += val;
				}
				Label templblttl = new Label("grandtotal", grandtotal);
				lblttl.replaceWith(templblttl);
				lblttl = templblttl;
			}
		};
		add(circledd);
		add(lblttl);
		add(projecttypes);
		add(vendors);
		add(ttllist);
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
				list.add(new ProjectType(rs.getInt(1), rs.getString(2)));
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
		Collections.sort(list, new Comparator<ProjectType>() {

			@Override
			public int compare(ProjectType o1, ProjectType o2) {
				// TODO Auto-generated method stub
				return o1.getProjecttypeid() - o2.getProjecttypeid();
			}
		});
		return list;
	}

	private List<VendorDetail> loadVendors() {
		final List<VendorDetail> list = new ArrayList<VendorDetail>();
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
				list.add(new VendorDetail(rs.getInt(1), rs.getString(2), vendorGetProjects(rs.getInt(1), circleid)));
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

	private List<ProjectDetail> vendorGetProjects(int vendorid, int circleid) {
		final List<ProjectDetail> list = new ArrayList<ProjectDetail>();
		final String query = "{call sp_circuit_vendor_get_projects(?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, vendorid);
			stmt.setInt(4, circleid);
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				ProjectDetail pd = new ProjectDetail(rs.getInt(1), rs.getString(2), rs.getInt(3));
				list.add(pd);
				if (hm.containsKey(pd.getProjecttypeid())) {
					hm.put(pd.getProjecttypeid(), hm.get(pd.getProjecttypeid()) + pd.getCount());
				} else {
					hm.put(pd.getProjecttypeid(), pd.getCount());
				}
			}
		} catch (SQLException e) {
			log.error("SQL Exception in vendorGetProjects() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in vendorGetProjects() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		Collections.sort(list, new Comparator<ProjectDetail>() {
			@Override
			public int compare(ProjectDetail o1, ProjectDetail o2) {
				return o1.getProjecttypeid() - o2.getProjecttypeid();
			}
		});
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

}
