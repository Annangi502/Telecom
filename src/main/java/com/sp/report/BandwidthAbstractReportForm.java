package com.sp.report;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
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
import com.sp.location.ProjectType;
import com.sp.resource.DataBaseConnection;

public class BandwidthAbstractReportForm extends Panel {
	private static final Logger log = Logger.getLogger(BandwidthAbstractReportForm.class);
	private HashMap<Integer, Integer> hm;
	private ProjectType projecttypes;
	private int projecttypeid ;
	private ListView<BandwidthDetail> bandwidths;
	private ListView<Integer> ttllist;
	private Label lblttl;
	IModel<? extends List<ProjectType>> projectlist = new LoadableDetachableModel<List<ProjectType>>() {

		@Override
		protected List<ProjectType> load() {
			// TODO Auto-generated method stub
			return loadProjectTypes();
		}
	};

	public BandwidthAbstractReportForm(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		setDefaultModel(new CompoundPropertyModel<BandwidthAbstractReportForm>(this));
		hm = new HashMap<Integer, Integer>();
		ListView<VendorDetail> vendors = new ListView<VendorDetail>("vendors", loadVendors()) {
			@Override
			protected void populateItem(ListItem<VendorDetail> item) {
				item.add(new Label("vendorname", item.getModelObject().getVendorname()));
			}
		};
		 bandwidths = new ListView<BandwidthDetail>("bandwidths", loadBandwidths()) {

			@Override
			protected void populateItem(ListItem<BandwidthDetail> item) {
				// TODO Auto-generated method stub
				item.add(new Label("bandwidthdesc", item.getModelObject().getBandwidthdesc()));
				ListView<VendorBandwidthDetail> projects = new ListView<VendorBandwidthDetail>("vendorbandwidth",
						item.getModelObject().getVendors()) {

					@Override
					protected void populateItem(ListItem<VendorBandwidthDetail> item) {
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

		DropDownChoice<ProjectType> projecttype = new DropDownChoice<ProjectType>("projecttypes", projectlist,
				new ChoiceRenderer<ProjectType>("projecttypedescription")) {
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
					protected void onSelectionChanged(ProjectType newSelection) {
						// TODO Auto-generated method stub
				hm = new HashMap<Integer, Integer>();
				projecttypeid  = newSelection.getProjecttypeid();
				ListView<BandwidthDetail> tempbandwidths = new ListView<BandwidthDetail>("bandwidths", loadBandwidths()) {

						@Override
						protected void populateItem(ListItem<BandwidthDetail> item) {
							// TODO Auto-generated method stub
							item.add(new Label("bandwidthdesc", item.getModelObject().getBandwidthdesc()));
							ListView<VendorBandwidthDetail> projects = new ListView<VendorBandwidthDetail>("vendorbandwidth",
									item.getModelObject().getVendors()) {

								@Override
								protected void populateItem(ListItem<VendorBandwidthDetail> item) {
									// TODO Auto-generated method stub
									item.add(new Label("count", item.getModelObject().getCount()));
								}
							};
							item.add(new Label("total", item.getModelObject().getTotal()));
							item.add(projects);

						}
					};
					bandwidths.replaceWith(tempbandwidths);
					bandwidths = tempbandwidths;
					
					
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

		add(vendors);
		add(bandwidths);
		add(ttllist);
		add(lblttl);
		add(projecttype);
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
				list.add(new VendorDetail(rs.getInt(1), rs.getString(2)));
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
			Collections.sort(list, new Comparator<VendorDetail>() {

				@Override
				public int compare(VendorDetail o1, VendorDetail o2) {
					// TODO Auto-generated method stub
					return o1.getVendorid() - o2.getVendorid();
				}
			});
		}
		return list;
	}

	private List<BandwidthDetail> loadBandwidths() {
		final List<BandwidthDetail> list = new ArrayList<BandwidthDetail>();
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
				list.add(new BandwidthDetail(rs.getInt(1), rs.getString(2), vendorGetBandwidth(rs.getInt(1),projecttypeid)));
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

	private List<VendorBandwidthDetail> vendorGetBandwidth(int bandwidthid,int projecttypeid) {
		final List<VendorBandwidthDetail> list = new ArrayList<VendorBandwidthDetail>();
		final String query = "{call sp_circuit_vendor_get_bandwidth(?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, bandwidthid);
			stmt.setInt(4, projecttypeid);
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				VendorBandwidthDetail pd = new VendorBandwidthDetail(rs.getInt(1), rs.getString(2), rs.getInt(3));
				list.add(pd);
				if (hm.containsKey(pd.getVendorid())) {
					hm.put(pd.getVendorid(), hm.get(pd.getVendorid()) + pd.getCount());
				} else {
					hm.put(pd.getVendorid(), pd.getCount());
				}
			}
		} catch (SQLException e) {
			log.error("SQL Exception in vendorGetBandwidth() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in vendorGetBandwidth() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		Collections.sort(list, new Comparator<VendorBandwidthDetail>() {

			@Override
			public int compare(VendorBandwidthDetail o1, VendorBandwidthDetail o2) {
				// TODO Auto-generated method stub
				return o1.getVendorid() - o2.getVendorid();
			}
		});
		return list;
	}

	private List<ProjectType> loadProjectTypes() {
		List<ProjectType> list = new ArrayList<ProjectType>();
		String query = "{call sp_get_project_types(?,?)}";
		list.add(new ProjectType(0, "All"));
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
				log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}
}
