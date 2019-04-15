
package com.sp.location;

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
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.location.ViewAllNetworkLocationForm.NetworkLocationList;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.GClickablePropertyColumn;

public class ViewNetworkLocationDetailForm extends Panel {
	private static final Logger log = Logger.getLogger(ViewNetworkLocationDetailForm.class);
	private String spcircuitid;
	private int projecttypeid;
	private String projecttypedescription;
	private int noofpointsavailable;
	private String installationdate;
	private String dateofconnected;
	private String ismergelocation;
	private String officecontactno;
	private String officeaddress;
	private String locationcontactperson;
	private String loactioncontactno;
	private String remark;
	private String townname;
	private String phase;
	private String circle;
    private String division;
    private String subdivision;
    private String section;
    private String spcircuitcode;
    private String latitude;
    private String longitude;
	private NetworkLocationDetail nld ;
	
	final int DEF_NO_OF_ROWS=9999;
	private NetworkVendorList ntvnlist = new NetworkVendorList();
	private NetworkEquipmentList nteqlist = new NetworkEquipmentList();
	private NetworkUPSList ntupslist = new NetworkUPSList();
	private NetworkInterfaceList ntinlist = new NetworkInterfaceList();
	public ViewNetworkLocationDetailForm(String id,final IModel<NetworkLocationDetail> nldmodel) {
		super(id);
		// TODO Auto-generated constructor stub
		setDefaultModel(new CompoundPropertyModel<ViewNetworkLocationDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("detailform");
		nld = getNetworkLocationDetail(nldmodel.getObject().getSpcircuitid());
		
		circle = nld.getCircledesc();
        division = nld.getDivisiondesc();
        subdivision = nld.getSubdivisiondesc();
        section = nld.getSectiondesc();
        spcircuitid = nld.getSpcircuitid();
        spcircuitcode = nld.getSpciruitcode();
		projecttypedescription = nld.getProjecttypedescription();
		noofpointsavailable = nld.getNoofpointsavailable();
		installationdate = nld.getInstallationdate();
		dateofconnected = nld.getDateofconnected();
		ismergelocation = nld.getIsmergelocation()==1?nld.getMergelocationdesc():"No";
		officecontactno = nld.getOfficecontactno();
		officeaddress = nld.getOfficeaddress();
		locationcontactperson = nld.getLocationcontactperson();
		loactioncontactno = nld.getLoactioncontactno();
		remark = nld.getRemark();
		townname = nld.getTownname();
		phase = nld.getPhase();
		latitude = nld.getLatitude();
		longitude = nld.getLongitude();
		
		ntvnlist.setList(getVendors());
		NetworkVendorDataProvider ntvndataprovider = new NetworkVendorDataProvider();
		List<IColumn> vncolumns = new ArrayList<IColumn>();
		vncolumns.add(new AbstractColumn(new Model("Sr. No.")){
			   public void populateItem(Item cell, String compId, IModel rowModel) {
			    int pageNumber,noRows;
			    DataTable tbl=(DataTable) get("datatable");
			    if(tbl != null) {
			    	pageNumber= (int) tbl.getCurrentPage();
			    	noRows=(int) tbl.getItemsPerPage();
			    	}
			    else {
			    	pageNumber=0;
			    	noRows=0;
			    	}
			    int rowNumber = (pageNumber*noRows)+((Item) cell.getParent().getParent()).getIndex()+1;
			          cell.add(new Label(compId,rowNumber)); 
			      }
			  });

		vncolumns.add(new PropertyColumn(new Model("Vendor Name"), "vendorname"));
		vncolumns.add(new PropertyColumn(new Model("Bandwidth"), "bandwidth"));
		vncolumns.add(new PropertyColumn(new Model("Service Type"), "servicetype"));
		vncolumns.add(new PropertyColumn(new Model("Interface"), "vninterface"));
		vncolumns.add(new PropertyColumn(new Model("Media Type"), "mediatypedesc"));
		vncolumns.add(new PropertyColumn(new Model("Circuit ID"), "circuitid"));
		vncolumns.add(new PropertyColumn(new Model("Phase"), "phase"));
	/*	vncolumns.add(new PropertyColumn(new Model("Remark"), "remark"));*/
		if(((PortSession) getSession()).isAdmin()){	
		vncolumns.add(new GClickablePropertyColumn(new Model("Edit"), "vendorname") {
			public void populateItem(Item item, String componentId, IModel rowModel) {
				item.add(new ColumnEditPanelNetworkVendorDetail(componentId, rowModel,
						new PropertyModel(rowModel, getProperty()),new CompoundPropertyModel<NetworkLocationDetail>(nld)));
			}
		});
		}
		DataTable table = new DataTable("vndatatable", vncolumns, ntvndataprovider, DEF_NO_OF_ROWS);
		table.addTopToolbar(new HeadersToolbar(table,ntvndataprovider));
		form.add(table);
		
		nteqlist.setList(getEquipments());
		NetworkEquipmentDataProvider nteqdataprovider = new NetworkEquipmentDataProvider();
		
		List<IColumn> eqcolumns = new ArrayList<IColumn>();
		eqcolumns.add(new AbstractColumn(new Model("Sr. No.")){
			   public void populateItem(Item cell, String compId, IModel rowModel) {
			    int pageNumber,noRows;
			    DataTable tbl=(DataTable) get("datatable");
			    if(tbl != null) {
			    	pageNumber= (int) tbl.getCurrentPage();
			    	noRows=(int) tbl.getItemsPerPage();
			    	}
			    else {
			    	pageNumber=0;
			    	noRows=0;
			    	}
			    int rowNumber = (pageNumber*noRows)+((Item) cell.getParent().getParent()).getIndex()+1;
			          cell.add(new Label(compId,rowNumber)); 
			      }
			  });

		eqcolumns.add(new PropertyColumn(new Model("Make"), "make"));
		eqcolumns.add(new GClickablePropertyColumn(new Model("Replace/Stand By"), "isreplace") {
			public void populateItem(Item item, String componentId, IModel rowModel) {
				item.add(new ColumnClickPanelNetworkEquipmentDetail(componentId, new CompoundPropertyModel<NetworkLocationDetail>(nld),
						new PropertyModel(rowModel, getProperty())));
			}
		});
		eqcolumns.add(new PropertyColumn(new Model("Model"), "model"));
		eqcolumns.add(new PropertyColumn(new Model("Serial No."), "serialnumber"));
		eqcolumns.add(new PropertyColumn(new Model("AMC/Warranty"), "amc"));
		eqcolumns.add(new GClickablePropertyColumn(new Model("Replace/Stand By"), "isreplace") {
			public void populateItem(Item item, String componentId, IModel rowModel) {
				item.add(new ReplaceDetail(componentId, rowModel,
						new PropertyModel(rowModel, getProperty())));
			}
		});
		if(((PortSession) getSession()).isAdmin()){	
		eqcolumns.add(new GClickablePropertyColumn(new Model("Edit"), "make") {
			public void populateItem(Item item, String componentId, IModel rowModel) {
				item.add(new ColumnEditPanelNetworkEquipmentDetail(componentId, rowModel,
						new PropertyModel(rowModel, getProperty()),new CompoundPropertyModel<NetworkLocationDetail>(nld)));
			}
		});
		}
		/*eqcolumns.add(new PropertyColumn(new Model("Remark"), "remark"));*/
		DataTable eqtable = new DataTable("eqdatatable", eqcolumns, nteqdataprovider, DEF_NO_OF_ROWS);
		eqtable.addTopToolbar(new HeadersToolbar(eqtable,nteqdataprovider));
		form.add(eqtable);
		
		
		ntupslist.setList(getUPS());
		NetworkUPSDataProvider ntupsdataprovider = new NetworkUPSDataProvider();
		
		List<IColumn> upscolumns = new ArrayList<IColumn>();
		upscolumns.add(new AbstractColumn(new Model("Sr. No.")){
			   public void populateItem(Item cell, String compId, IModel rowModel) {
			    int pageNumber,noRows;
			    DataTable tbl=(DataTable) get("datatable");
			    if(tbl != null) {
			    	pageNumber= (int) tbl.getCurrentPage();
			    	noRows=(int) tbl.getItemsPerPage();
			    	}
			    else {
			    	pageNumber=0;
			    	noRows=0;
			    	}
			    int rowNumber = (pageNumber*noRows)+((Item) cell.getParent().getParent()).getIndex()+1;
			          cell.add(new Label(compId,rowNumber)); 
			      }
			  });

		upscolumns.add(new PropertyColumn(new Model("Make"), "make"));
		upscolumns.add(new PropertyColumn(new Model("Model"), "model"));
		upscolumns.add(new PropertyColumn(new Model("Serial No."), "serialnumber"));
		upscolumns.add(new PropertyColumn(new Model("No .of Batteries"), "noofbatteries"));
		upscolumns.add(new PropertyColumn(new Model("AMC/Warranty"), "amc"));
		upscolumns.add(new PropertyColumn(new Model("Remark"), "remark"));
		if(((PortSession) getSession()).isAdmin()){	
		upscolumns.add(new GClickablePropertyColumn(new Model("Edit"), "make") {
			public void populateItem(Item item, String componentId, IModel rowModel) {
				item.add(new ColumnEditPanelNetworkUPSDetail(componentId, rowModel,
						new PropertyModel(rowModel, getProperty()),new CompoundPropertyModel<NetworkLocationDetail>(nld)));
			}
		});
		}
		DataTable upstable = new DataTable("upsdatatable", upscolumns, ntupsdataprovider, DEF_NO_OF_ROWS);
		upstable.addTopToolbar(new HeadersToolbar(upstable,nteqdataprovider));
		form.add(upstable);
		
		
		ntinlist.setList(getInterfaces());
		NetworkInterfaceDataProvider ntindataprovider = new NetworkInterfaceDataProvider();
		
		List<IColumn> incolumns = new ArrayList<IColumn>();
		incolumns.add(new AbstractColumn(new Model("Sr. No.")){
			   public void populateItem(Item cell, String compId, IModel rowModel) {
			    int pageNumber,noRows;
			    DataTable tbl=(DataTable) get("datatable");
			    if(tbl != null) {
			    	pageNumber= (int) tbl.getCurrentPage();
			    	noRows=(int) tbl.getItemsPerPage();
			    	}
			    else {
			    	pageNumber=0;
			    	noRows=0;
			    	}
			    int rowNumber = (pageNumber*noRows)+((Item) cell.getParent().getParent()).getIndex()+1;
			          cell.add(new Label(compId,rowNumber)); 
			      }
			  });

		incolumns.add(new PropertyColumn(new Model("Vendor Name"), "vendorname"));
		incolumns.add(new GClickablePropertyColumn(new Model("Vendor Details"), "vendorname") {
			public void populateItem(Item item, String componentId, IModel rowModel) {
				item.add(new VendorDetailColumn(componentId, rowModel,
						new PropertyModel(rowModel, getProperty())));
			}
		});
		incolumns.add(new GClickablePropertyColumn(new Model("Spdcl Details"), "spntinterface") {
			public void populateItem(Item item, String componentId, IModel rowModel) {
				item.add(new SPDetailColumn(componentId, rowModel,
						new PropertyModel(rowModel, getProperty())));
			}
		});
		if(((PortSession) getSession()).isAdmin()){			
		incolumns.add(new GClickablePropertyColumn(new Model("Edit"), "vendorname") {
			public void populateItem(Item item, String componentId, IModel rowModel) {
				item.add(new ColumnEditPanelNetworkInterfaceDetail(componentId, rowModel,
						new PropertyModel(rowModel, getProperty()),new CompoundPropertyModel<NetworkLocationDetail>(nld)));
			}
		});
		}
		DataTable intable = new DataTable("indatatable", incolumns, ntindataprovider, DEF_NO_OF_ROWS);
		intable.addTopToolbar(new HeadersToolbar(intable,ntindataprovider));
		form.add(intable);
		
		Button btnaddvendor = new Button("btnaddvendor"){
			@Override
			public void onSubmit() {
				PageParameters parms = new PageParameters();
				AddVendorDetail av = new AddVendorDetail(parms, new CompoundPropertyModel<NetworkLocationDetail>(nld));
				setResponsePage(av);
			}
		};
		Button btnaddnetworkeqp = new Button("btnaddnteq"){
			@Override
			public void onSubmit() {
				PageParameters parms = new PageParameters();
				AddEquipmentDetail ae = new AddEquipmentDetail(parms,  new CompoundPropertyModel<NetworkLocationDetail>(nld));
				setResponsePage(ae);
			}
		};
		Button btnaddnetworkinterface = new Button("btnaddntin"){
			@Override
			public void onSubmit() {
				PageParameters parms = new PageParameters();
				AddInterfaceDetail ai = new AddInterfaceDetail(parms,  new CompoundPropertyModel<NetworkLocationDetail>(nld));
				setResponsePage(ai);
			}
		};
		Button btnaddupsd = new Button("btnaddupsd"){
			@Override
			public void onSubmit() {
				PageParameters parms = new PageParameters();
				AddUPSDetail av = new AddUPSDetail(parms,  new CompoundPropertyModel<NetworkLocationDetail>(nld));
				setResponsePage(av);
			}
		};
		Button btnback = new Button("btnback"){
			@Override
			public void onSubmit() {
				setResponsePage(ViewAllNetworkLocation.class);
			}
		};
		
		Button edit = new Button("edit"){
			@Override
			public void onSubmit() {
				PageParameters parms = new PageParameters();
				EditNetworkLocationDetail av = new EditNetworkLocationDetail(parms, new CompoundPropertyModel<NetworkLocationDetail>(nld));
				setResponsePage(av);
			}
		};
		edit.setVisible(((PortSession) getSession()).isAdmin());
		form.add(new Label("circle") );
        form.add(new Label("division"));
        form.add(new Label("subdivision"));
        form.add(new Label("section") );
        form.add(new Label("spcircuitcode"));
	/*	form.add(new Label("spcircuitid"));*/
		form.add(new Label("projecttypedescription"));
		form.add(new Label("noofpointsavailable"));
		form.add(new Label("installationdate"));
		form.add(new Label("dateofconnected"));
		form.add(new Label("ismergelocation"));
		form.add(new Label("officecontactno"));
		form.add(new Label("officeaddress"));
		form.add(new Label("locationcontactperson"));
		form.add(new Label("loactioncontactno"));
		form.add(new Label("remark"));
		form.add(new Label("townname"));
		form.add(new Label("phase"));
		form.add(new Label("latitude"));
		form.add(new Label("longitude"));
		form.add(btnaddvendor);
		form.add(btnaddnetworkeqp);
		form.add(btnaddnetworkinterface);
		form.add(btnaddupsd);
		form.add(btnback);
		form.add(edit);
		add(form);
	}
	public class NetworkVendorDataProvider extends  SortableDataProvider implements Serializable
	{

		public NetworkVendorDataProvider() {
			// TODO Auto-generated constructor stub
			setSort("spcircuidid",SortOrder.ASCENDING);
		}
		@Override
		public Iterator iterator(long first, long count) {
			// TODO Auto-generated method stub
			Collections.sort(ntvnlist.getList(), new Comparator<NetworkVendorDetail>() {

				@Override
				public int compare(NetworkVendorDetail arg0,
						NetworkVendorDetail arg1) {
					// TODO Auto-generated method stub
					int dir=getSort().isAscending()?1:-1;
					return dir * (arg0.getVendorname()  .compareTo(arg1.getVendorname()));
				}
			});
			return ntvnlist.selectList((int) first, (int) count).iterator();
		}

		@Override
		public IModel model(Object arg0) {
			// TODO Auto-generated method stub
			NetworkVendorDetail nld = (NetworkVendorDetail) arg0;
			return new Model((Serializable)nld);
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return ntvnlist.getList().size();
		}
		
	}
	public class NetworkVendorList implements Serializable
	{
		private List<NetworkVendorDetail> list;
		public NetworkVendorList() {
			// TODO Auto-generated constructor stub
			list = new ArrayList<NetworkVendorDetail>();
		}
		public void addItem(NetworkVendorDetail t){
			list.add(t);
		}
		
		@SuppressWarnings("rawtypes")
		public List getList(){
			return list;
		}
		
		public void setList(List<NetworkVendorDetail> l){
			list=l;
		}
		
		@SuppressWarnings("rawtypes")
		public List selectList(int first,int count){
			return list.subList(first, first+count);
		}
	}
	
	public class NetworkEquipmentDataProvider extends  SortableDataProvider implements Serializable
	{

		public NetworkEquipmentDataProvider() {
			// TODO Auto-generated constructor stub
			setSort("make",SortOrder.ASCENDING);
		}
		@Override
		public Iterator iterator(long first, long count) {
			// TODO Auto-generated method stub
			Collections.sort(nteqlist.getList(), new Comparator<NetworkEquipmentDetail>() {

				@Override
				public int compare(NetworkEquipmentDetail arg0,
						NetworkEquipmentDetail arg1) {
					// TODO Auto-generated method stub
					int dir=getSort().isAscending()?1:-1;
					return dir * (arg0.getMake().compareTo(arg1.getMake()));
				}
			});
			return nteqlist.selectList((int) first, (int) count).iterator();
		}

		@Override
		public IModel model(Object arg0) {
			// TODO Auto-generated method stub
			NetworkEquipmentDetail nld = (NetworkEquipmentDetail) arg0;
			return new Model((Serializable)nld);
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return nteqlist.getList().size();
		}
		
	}
	
	public class NetworkEquipmentList implements Serializable
	{
		private List<NetworkEquipmentDetail> list;
		public NetworkEquipmentList() {
			// TODO Auto-generated constructor stub
			list = new ArrayList<NetworkEquipmentDetail>();
		}
		public void addItem(NetworkEquipmentDetail t){
			list.add(t);
		}
		
		@SuppressWarnings("rawtypes")
		public List getList(){
			return list;
		}
		
		public void setList(List<NetworkEquipmentDetail> l){
			list=l;
		}
		
		@SuppressWarnings("rawtypes")
		public List selectList(int first,int count){
			return list.subList(first, first+count);
		}
	}
	
	public class NetworkUPSDataProvider extends  SortableDataProvider implements Serializable
	{

		public NetworkUPSDataProvider() {
			// TODO Auto-generated constructor stub
			setSort("make",SortOrder.ASCENDING);
		}
		@Override
		public Iterator iterator(long first, long count) {
			// TODO Auto-generated method stub
			Collections.sort(ntupslist.getList(), new Comparator<NetworkUPSDetail>() {

				@Override
				public int compare(NetworkUPSDetail arg0,
						NetworkUPSDetail arg1) {
					// TODO Auto-generated method stub
					int dir=getSort().isAscending()?1:-1;
					return dir * (arg0.getMake().compareTo(arg1.getMake()));
				}
			});
			return ntupslist.selectList((int) first, (int) count).iterator();
		}

		@Override
		public IModel model(Object arg0) {
			// TODO Auto-generated method stub
			NetworkUPSDetail nld = (NetworkUPSDetail) arg0;
			return new Model((Serializable)nld);
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return ntupslist.getList().size();
		}
		
	}
	public class NetworkUPSList implements Serializable
	{
		private List<NetworkUPSDetail> list;
		public NetworkUPSList() {
			// TODO Auto-generated constructor stub
			list = new ArrayList<NetworkUPSDetail>();
		}
		public void addItem(NetworkUPSDetail t){
			list.add(t);
		}
		
		@SuppressWarnings("rawtypes")
		public List getList(){
			return list;
		}
		
		public void setList(List<NetworkUPSDetail> l){
			list=l;
		}
		
		@SuppressWarnings("rawtypes")
		public List selectList(int first,int count){
			return list.subList(first, first+count);
		}
	}
	
	public class NetworkInterfaceDataProvider extends  SortableDataProvider implements Serializable
	{

		public NetworkInterfaceDataProvider() {
			// TODO Auto-generated constructor stub
			setSort("equipment",SortOrder.ASCENDING);
		}
		@Override
		public Iterator iterator(long first, long count) {
			// TODO Auto-generated method stub
			Collections.sort(ntinlist.getList(), new Comparator<NetworkInterfaceDetail>() {

				@Override
				public int compare(NetworkInterfaceDetail arg0,
						NetworkInterfaceDetail arg1) {
					// TODO Auto-generated method stub
					int dir=getSort().isAscending()?1:-1;
					return dir * (arg0.getVendorname().compareTo(arg1.getVendorname()));
				}
			});
			return ntinlist.selectList((int) first, (int) count).iterator();
		}

		@Override
		public IModel model(Object arg0) {
			// TODO Auto-generated method stub
			NetworkInterfaceDetail nld = (NetworkInterfaceDetail) arg0;
			return new Model((Serializable)nld);
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return ntinlist.getList().size();
		}
		
	}
	public class NetworkInterfaceList implements Serializable
	{
		private List<NetworkInterfaceDetail> list;
		public NetworkInterfaceList() {
			// TODO Auto-generated constructor stub
			list = new ArrayList<NetworkInterfaceDetail>();
		}
		public void addItem(NetworkInterfaceDetail t){
			list.add(t);
		}
		
		@SuppressWarnings("rawtypes")
		public List getList(){
			return list;
		}
		
		public void setList(List<NetworkInterfaceDetail> l){
			list=l;
		}
		
		@SuppressWarnings("rawtypes")
		public List selectList(int first,int count){
			return list.subList(first, first+count);
		}
	}
	
	public NetworkLocationDetail getNetworkLocationDetail(String circuit_id)
	{
		NetworkLocationDetail detail = null;
		
		String query = "{call sp_circuit_get_details(?,?,?)}";
		Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            con = new DataBaseConnection().getConnection();
            stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setString(3, circuit_id);
		    rs = stmt.executeQuery();
		    log.info("Executing Stored Procedure { "+stmt.toString()+" }");
		    while(rs.next())
		    {
		   
		    	detail = new NetworkLocationDetail(
		    			rs.getString(1),
		    			rs.getInt(2),
		    			rs.getString(3),
		    			rs.getInt(4),
						rs.getString(5), 
						rs.getString(6),
						rs.getString(7), 
						rs.getString(8), 
						rs.getString(9),
						rs.getString(10),
						rs.getString(11), 
						rs.getString(12), 
						rs.getString(13), 
						rs.getString(14),
						rs.getString(15),
						rs.getString(16), 
						rs.getString(17), 
						rs.getString(18), 
						rs.getInt(19),
						rs.getString(20),
						rs.getString(21),
						rs.getString(22));
		    }
		}catch (SQLException e) {
			log.error("SQL Exception in getNetworkLocationDetail() method {"+e.getMessage()+"}");
			e.printStackTrace();
		}
        finally {
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
            }
            catch (SQLException e2) {
                log.error("SQL Exception in getNetworkLocationDetail() method {" + e2.getMessage() + "}");
                e2.printStackTrace();
            }
        }
		return detail;
		
	}
	
	public List<NetworkVendorDetail> getVendors() {
        final List<NetworkVendorDetail> vnlist = new ArrayList<NetworkVendorDetail>();
        final String query = "{call sp_circuit_get_vendors(?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            con = new DataBaseConnection().getConnection();
            stmt = con.prepareCall(query);
            stmt.setString(1, ((PortSession)this.getSession()).getEmployeeid());
            stmt.setInt(2, ((PortSession)this.getSession()).getSessionid());
            stmt.setString(3, this.spcircuitid);
            rs = stmt.executeQuery();
            log.info((Object)("Executing Stored Procedure { " + stmt.toString() + " }"));
            while (rs.next()) {
                vnlist.add(new NetworkVendorDetail(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),rs.getString(9),rs.getInt(10),rs.getString(11),rs.getInt(12),rs.getString(13),rs.getInt(14)));
            }
        }
        catch (SQLException e) {
           log.error((Object)("SQL Exception in getVendors() method {" + e.getMessage() + "}"));
            e.printStackTrace();
            return vnlist;
        }
        finally {
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
            }
            catch (SQLException e2) {
                log.error("SQL Exception in getVendors() method {" + e2.getMessage() + "}");
                e2.printStackTrace();
            }
        }
        return vnlist;
    }
	
	public List<NetworkEquipmentDetail> getEquipments()
	{
		List<NetworkEquipmentDetail> vnlist = new ArrayList<NetworkEquipmentDetail>();
		String query = "{call sp_circuit_get_equipments(?,?,?)}";
		Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            con = new DataBaseConnection().getConnection();
            stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setString(3, spcircuitid);
		    rs = stmt.executeQuery();
		    log.info("Executing Stored Procedure { "+stmt.toString()+" }");
		    while(rs.next())
		    {
		    	vnlist.add(new NetworkEquipmentDetail(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6),rs.getString(7)));
		    }
		}catch (SQLException e) {
			log.error("SQL Exception in getEquipments() method {"+e.getMessage()+"}");
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
            }
            catch (SQLException e2) {
                log.error("SQL Exception in getEquipments() method {" + e2.getMessage() + "}");
                e2.printStackTrace();
            }
        }
		return vnlist;	
	}
	
	public List<NetworkUPSDetail> getUPS()
	{
		List<NetworkUPSDetail> upslist = new ArrayList<NetworkUPSDetail>();
		String query = "{call sp_circuit_get_ups(?,?,?)}";
		Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            con = new DataBaseConnection().getConnection();
            stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setString(3, spcircuitid);
		    rs = stmt.executeQuery();
		    log.info("Executing Stored Procedure { "+stmt.toString()+" }");
		    while(rs.next())
		    {
		    	upslist.add(new NetworkUPSDetail(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getInt(5), rs.getString(6), rs.getString(7)));
		    }
		}catch (SQLException e) {
			log.error("SQL Exception in getUPS() method {"+e.getMessage()+"}");
			e.printStackTrace();
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
            }
            catch (SQLException e2) {
                log.error("SQL Exception in getEquipments() method {" + e2.getMessage() + "}");
                e2.printStackTrace();
            }
        }
		return upslist;	
	}
	
	public List<NetworkInterfaceDetail> getInterfaces()
	{
		List<NetworkInterfaceDetail> upinist = new ArrayList<NetworkInterfaceDetail>();
		String query = "{call sp_circuit_get_interfaces(?,?,?)}";
		Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            con = new DataBaseConnection().getConnection();
            stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setString(3, spcircuitid);
		    rs = stmt.executeQuery();
		    log.info("Executing Stored Procedure { "+stmt.toString()+" }");
		    while(rs.next())
		    {
		    	upinist.add(new NetworkInterfaceDetail(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9),rs.getString(10) ));
		    }
		}catch (SQLException e) {
			log.error("SQL Exception in getInterfaces() method {"+e.getMessage()+"}");
			e.printStackTrace();
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
            }
            catch (SQLException e2) {
                log.error("SQL Exception in getEquipments() method {" + e2.getMessage() + "}");
                e2.printStackTrace();
            }
        }
		return upinist;	
	}
}
