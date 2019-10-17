package com.sp.master;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ThreadContext;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
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
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView.ItemsIterator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.util.thread.Task;
import org.apache.wicket.util.time.Duration;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;
import com.sp.SPNetworkLocation.PortSession;
import com.sp.location.Circle;
import com.sp.location.Division;
import com.sp.location.NetworkLocationDetail;
import com.sp.location.Section;
import com.sp.location.SubDivision;
import com.sp.report.BandwidthDetail;
import com.sp.report.LinkAbstractReportForm;
import com.sp.report.ProjectDetail;
import com.sp.report.Report;
import com.sp.report.TotalLocationsReportForm;
import com.sp.report.VendorBandwidthDetail;
import com.sp.report.VendorDetail;
import com.sp.report.TotalLocationsReportForm.NetworkLocationDataProvider;
import com.sp.report.TotalLocationsReportForm.NetworkLocationList;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.GClickablePropertyColumn;

import org.jfree.chart.labels.StandardPieSectionLabelGenerator; 
import org.jfree.chart.plot.PiePlot;  

public class DashboardForm extends Panel{
	private static final Logger log = Logger.getLogger(DashboardForm.class);
	private DefaultPieDataset d2 = new DefaultPieDataset();
	private String ttllocation;
	private String othnet;
	private String nonet;
	private String merloc;
	private List<NetworkLocationDetail> mainlist  =  new ArrayList<NetworkLocationDetail>();
	private  DataTablePanel dp;
	
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
       
       dp = new DataTablePanel("lazy",mainlist);
       add(dp);
 
       final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	   final Date date = new Date(); 
       final Application app = ThreadContext.getApplication();
	   final RequestCycle requestCycle = ThreadContext.getRequestCycle(); 
	   final  Session session = ThreadContext.getSession(); 
	    Runnable r1= new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ThreadContext.setApplication(app); 
		        ThreadContext.setRequestCycle(requestCycle); 
		        ThreadContext.setSession(session);
		        getAllNetworkLocations(8);
		        
			}
		};
		Thread t1 = new Thread(r1);
		t1.start();
		/*try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	 
										
									/*	try {
											t10.join();
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}*/
      /* add(new AjaxLazyLoadPanel("lazy")
       {

           @Override
           public Component getLazyLoadComponent(String id)
           {
               // sleep for 5 seconds to show the behavior
        	   
        	   
        	    final Application app = ThreadContext.getApplication();
         	   final RequestCycle requestCycle = ThreadContext.getRequestCycle(); 
         	   final  Session session = ThreadContext.getSession();
        	    ExecutorService executor = Executors.newFixedThreadPool(10);
        	    List<Future<List<NetworkLocationDetail>>> listexe = new ArrayList<Future<List<NetworkLocationDetail>>>();
        	    Callable<List<NetworkLocationDetail>> callable = new Callable<List<NetworkLocationDetail>>() {
					
					@Override
					public List<NetworkLocationDetail> call() throws Exception {
						// TODO Auto-generated method stub
						ThreadContext.setApplication(app); 
				        ThreadContext.setRequestCycle(requestCycle); 
				        ThreadContext.setSession(session);
						return getAllNetworkLocations(0);
					}
				};
				Future<List<NetworkLocationDetail>> future = executor.submit(callable);
				listexe.add(future);
				Callable<List<NetworkLocationDetail>> callable2 = new Callable<List<NetworkLocationDetail>>() {
					
					@Override
					public List<NetworkLocationDetail> call() throws Exception {
						// TODO Auto-generated method stub
						ThreadContext.setApplication(app); 
				        ThreadContext.setRequestCycle(requestCycle); 
				        ThreadContext.setSession(session);
						return getAllNetworkLocations(2);
					}
				};
				Future<List<NetworkLocationDetail>> future2 = executor.submit(callable2);
				listexe.add(future2);
				executor.shutdown();
				
        	 

        	   list.addAll(getAllNetworkLocations(2));
        	   list.addAll(getAllNetworkLocations(3));
        	   list.addAll(getAllNetworkLocations(4));
        	   list.addAll(getAllNetworkLocations(5));
        	   list.addAll(getAllNetworkLocations(6));
        	   list.addAll(getAllNetworkLocations(7));
        	   list.addAll(getAllNetworkLocations(8));
        	   list.addAll(getAllNetworkLocations(9));
        	   list.addAll(getAllNetworkLocations(10));
				 for(Future<List<NetworkLocationDetail>> fut : listexe){
			            try {
			                //print the return value of Future, notice the output delay in console
			               list.addAll(fut.get());
			            } catch (InterruptedException e) {
			                e.printStackTrace();
			            } catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
        	   System.out.println("Start Time"+formatter.format(date)+"End Time"+formatter.format(new Date()) );  
               return new DataTablePanel(id,list);
           }

           @Override
        public Component getLoadingComponent(String markupId) {
        	// TODO Auto-generated method stub
        	   IRequestHandler handler = new ResourceReferenceRequestHandler(
        				AbstractDefaultAjaxBehavior.INDICATOR);
        			return new Label(markupId, "<div class='text-center'><img class='text-center' alt=\"Loading...\" src= '../loading.gif'/></div>").setEscapeModelStrings(false);
        }
       });*/


	}
	public List<NetworkLocationDetail> getAllNetworkLocations(int circleid) {
		final List<NetworkLocationDetail> list = new ArrayList<NetworkLocationDetail>();
		final String query = "{call sp_get_all_vendor_interfaces(?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, circleid);
			stmt.setInt(4, 0);
			stmt.setInt(5, 0);
			stmt.setInt(6, 0);
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				mainlist.add(new NetworkLocationDetail(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9),
						rs.getString(10),rs.getString(11),(rs.getString(11).trim().length()==0?false:sendPingRequest(rs.getString(11).trim()))));
			
			}
			
		} catch (SQLException e) {
			log.error("SQL Exception in getAllNetworkLocations() method {" + e.getMessage() + "}");
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

	public boolean  sendPingRequest(String ipAddress) {
		InetAddress geek;
		log.info("Sending Ping Request to " + ipAddress);
		try {
			geek = InetAddress.getByName(ipAddress);
			if (geek.isReachable(1)){
				log.info("Yes, We can reach to this host");
				return true;
			}
			else{
				log.info("Sorry ! We can't reach to this host");
				return false;
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
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

