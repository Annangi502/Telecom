package com.sp.master;

import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.GridView.ItemsIterator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.report.BandwidthDetail;
import com.sp.report.LinkAbstractReportForm;
import com.sp.report.ProjectDetail;
import com.sp.report.VendorBandwidthDetail;
import com.sp.report.VendorDetail;
import com.sp.resource.DataBaseConnection;

import org.jfree.chart.labels.StandardPieSectionLabelGenerator; 
import org.jfree.chart.plot.PiePlot;  

public class DashboardForm extends Panel{
	private static final Logger log = Logger.getLogger(DashboardForm.class);
	private DefaultPieDataset d2 = new DefaultPieDataset();
	private String ttllocation;
	private String othnet;
	private String nonet;
	private String merloc;
	public DashboardForm(String id) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<DashboardForm>(this));
		getDashboardDetails();
		add(new Label("ttllocation"));
		add(new Label("othnet"));
		add(new Label("nonet"));
		add(new Label("merloc"));
		DefaultPieDataset d = new DefaultPieDataset();
		Iterator<VendorDetail> itr = loadVendors().iterator();
		while(itr.hasNext()){
			VendorDetail vd = itr.next();
			d.setValue(vd.getVendorname(),vd.getTotal());	
		}
        JFreeChart chart = ChartFactory.createPieChart("", d,
                 true,      // Show legend 
                 true,      // Show tooltips
                 true);     // Show urls
        chart.setBackgroundPaint(Color.white);
        chart.setBorderVisible(false);
      
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);
        ((PiePlot) chart.getPlot()).setBackgroundPaint( Color.WHITE );
        
        PiePlot plot = (PiePlot) chart.getPlot();
       /* plot.setSectionPaint("RAILTEL", Color.black);
        plot.setSectionPaint("TTSL", new Color(120, 0, 120));*/
        plot.setOutlinePaint(null);
        // or do this, if you are using an older version of JFreeChart:
        //plot.setSectionPaint(1, Color.black);
        //plot.setSectionPaint(3, new Color(120, 0, 120));
       // add(new JFreeChartImage("image", chart, 450, 450));
        
        DefaultPieDataset d1 = new DefaultPieDataset();
        Iterator<BandwidthDetail> itr1 = loadBandwidths().iterator();
        while(itr1.hasNext()){
        	BandwidthDetail db = itr1.next();
        	if(db.getTotal()!=0)
        	d1.setValue(db.getBandwidthdesc(),db.getTotal());
        }
        JFreeChart chart1 = ChartFactory.createPieChart("", d1,
                true,      // Show legend 
                true,      // Show tooltips
                true);     // Show urls
       chart1.setBackgroundPaint(Color.white);
       chart1.setBorderVisible(false);
     
       PieSectionLabelGenerator labelGenerator1 = new StandardPieSectionLabelGenerator("{0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
       ((PiePlot) chart1.getPlot()).setLabelGenerator(labelGenerator1);
       ((PiePlot) chart1.getPlot()).setBackgroundPaint( Color.WHITE );
       
       PiePlot plot1 = (PiePlot) chart1.getPlot();
      /* plot.setSectionPaint("RAILTEL", Color.black);
       plot.setSectionPaint("TTSL", new Color(120, 0, 120));*/
       plot1.setOutlinePaint(null);
       // or do this, if you are using an older version of JFreeChart:
       //plot.setSectionPaint(1, Color.black);
       //plot.setSectionPaint(3, new Color(120, 0, 120));
        //add(new JFreeChartImage("image1", chart1, 450, 450));
        
        getProjectTypeSummary();

        JFreeChart chart2 = ChartFactory.createPieChart("", d2,
                true,      // Show legend 
                true,      // Show tooltips
                true);     // Show urls
       chart2.setBackgroundPaint(Color.white);
       chart2.setBorderVisible(false);
     
       PieSectionLabelGenerator labelGenerator2 = new StandardPieSectionLabelGenerator("{0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
       ((PiePlot) chart2.getPlot()).setLabelGenerator(labelGenerator2);
       ((PiePlot) chart2.getPlot()).setBackgroundPaint( Color.WHITE );
       
       PiePlot plot2 = (PiePlot) chart2.getPlot();
      /* plot.setSectionPaint("RAILTEL", Color.black);
       plot.setSectionPaint("TTSL", new Color(120, 0, 120));*/
       plot2.setOutlinePaint(null);
       // or do this, if you are using an older version of JFreeChart:
       //plot.setSectionPaint(1, Color.black);
       //plot.setSectionPaint(3, new Color(120, 0, 120));
       // add(new JFreeChartImage("image2", chart2, 450, 450));

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
				list.add(new VendorDetail(rs.getInt(1), rs.getString(2), vendorGetProjects(rs.getInt(1), 0)));
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
				list.add(new BandwidthDetail(rs.getInt(1), rs.getString(2), vendorGetBandwidth(rs.getInt(1),0)));
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
	
	private boolean getProjectTypeSummary() {
		final String query = "{call sp_circuit_get_project_type_summary(?,?)}";
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
				d2.setValue(rs.getString(2), rs.getInt(3));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in getProjectTypeSummary() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in getProjectTypeSummary() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}

		return true;
	}
	
	private boolean getDashboardDetails() {
		final String query = "{call sp_circuit_get_dashboard_detail(?,?)}";
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
				ttllocation = rs.getString(1);
				othnet = rs.getString(4);
				nonet = rs.getString(3);
				merloc = rs.getString(2);
			}
		} catch (SQLException e) {
			log.error("SQL Exception in getDashboardDetails() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in getDashboardDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}

		return true;
	}
}

