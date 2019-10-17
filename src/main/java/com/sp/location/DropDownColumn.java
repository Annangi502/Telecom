package com.sp.location;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.resource.DataBaseConnection;

public class DropDownColumn extends Panel{
	private static final Logger log = Logger.getLogger(DropDownColumn.class);
	
	  private List<String> equipmentname = loadEquipmentTypes();
	   public String selected = "B";
	public DropDownColumn(String id, final IModel rowMode) {
		super(id);
		//System.out.println(" rowMode "+rowMode.getObject().toString());
		
		selected = rowMode.getObject().toString() ;
		
	     add(new DropDownChoice("connective", new PropertyModel(this, "selected"), equipmentname));
	     
		// TODO Auto-generated constructor stub
	}
	
	
	private List<String> loadEquipmentTypes() {
		List<String> list = new ArrayList<String>();
		String query = "{call sp_get_equipment_types(?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setInt(3,0);
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				list.add(rs.getString(2));
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
	
	

}
