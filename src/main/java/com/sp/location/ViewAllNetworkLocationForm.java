package com.sp.location;

import java.io.Serializable;
import java.sql.CallableStatement;
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
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import com.sp.SPNetworkLocation.PortSession;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.GClickablePropertyColumn;


public class ViewAllNetworkLocationForm extends Panel {
	private static final Logger log = Logger.getLogger(ViewAllNetworkLocationForm.class);
	private NetworkLocationList ntlclist = new NetworkLocationList();
	final int DEF_NO_OF_ROWS=9999;
	
	public ViewAllNetworkLocationForm(String id) {
		super(id);
		ntlclist.setList(getAllNetworkLocations());
		NetworkLocationDataProvider nlprovider = new NetworkLocationDataProvider();
		List<IColumn> columns = new ArrayList<IColumn>();
		columns.add(new AbstractColumn(new Model("Sr. No.")){
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
		columns.add(new GClickablePropertyColumn(new Model("Circuit ID"), "spcircuitid"){
			public void populateItem(Item item, String componentId, IModel rowModel) {
				item.add(new ColumnClickPanelNetworkLocationDetail(componentId, rowModel, new PropertyModel(rowModel,getProperty())));
			}
		});
		/*columns.add(new PropertyColumn(new Model("Circuit ID"), "spcircuitid"));*/	
		columns.add(new PropertyColumn(new Model("Project"), "projecttypedescription"));
		columns.add(new PropertyColumn(new Model("Town Name"), "townname"));
		columns.add(new PropertyColumn(new Model("Office Description"), "officedescription"));
		columns.add(new PropertyColumn(new Model("Office Contact No."), "officecontactno"));
		columns.add(new PropertyColumn(new Model("Location Contact Person"), "locationcontactperson"));
		columns.add(new PropertyColumn(new Model("Contact No."), "loactioncontactno"));
		DataTable table = new DataTable("datatable", columns, nlprovider, DEF_NO_OF_ROWS);
		table.addTopToolbar(new HeadersToolbar(table,nlprovider));
		add(table);
	
	}
	
	public class NetworkLocationDataProvider extends  SortableDataProvider implements Serializable
	{

		public NetworkLocationDataProvider() {
			// TODO Auto-generated constructor stub
			setSort("spcircuidid",SortOrder.ASCENDING);
		}
		@Override
		public Iterator iterator(long first, long count) {
			// TODO Auto-generated method stub
			Collections.sort(ntlclist.getList(), new Comparator<NetworkLocationDetail>() {

				@Override
				public int compare(NetworkLocationDetail arg0,
						NetworkLocationDetail arg1) {
					// TODO Auto-generated method stub
					int dir=getSort().isAscending()?1:-1;
					return dir * (arg0.getSpcircuitid().compareTo(arg1.getSpcircuitid()));
				}
			});
			return ntlclist.selectList((int) first, (int) count).iterator();
		}

		@Override
		public IModel model(Object arg0) {
			// TODO Auto-generated method stub
			NetworkLocationDetail nld = (NetworkLocationDetail) arg0;
			return new Model((Serializable)nld);
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return ntlclist.getList().size();
		}
		
	}
	public class NetworkLocationList implements Serializable
	{
		private List<NetworkLocationDetail> list;
		public NetworkLocationList() {
			// TODO Auto-generated constructor stub
			list = new ArrayList<NetworkLocationDetail>();
		}
		public void addItem(NetworkLocationDetail t){
			list.add(t);
		}
		
		@SuppressWarnings("rawtypes")
		public List getList(){
			return list;
		}
		
		public void setList(List<NetworkLocationDetail> l){
			list=l;
		}
		
		@SuppressWarnings("rawtypes")
		public List selectList(int first,int count){
			return list.subList(first, first+count);
		}
	}

	public List<NetworkLocationDetail> getAllNetworkLocations()
	{
		List<NetworkLocationDetail> list = new ArrayList<NetworkLocationDetail>();
		
		String query = "{call sp_get_all_circuits(?,?)}";
		try {
			CallableStatement stmt = new DataBaseConnection().getConnection().prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
		    ResultSet rs = stmt.executeQuery();
		    log.info("Executing Stored Procedure { "+stmt.toString()+" }");
		    while(rs.next())
		    {
		    	list.add(new NetworkLocationDetail(rs.getString(1), rs.getInt(2),rs.getString(3),rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),rs.getString(13),rs.getString(14)));
		    }
		}catch (SQLException e) {
			log.error("SQL Exception in loadProjectTypes() method {"+e.getMessage()+"}");
			e.printStackTrace();
		}
		
		return list;
		
	}
}
