
package com.sp.location;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.resource.DataBaseConnection;

public class UPSReplaceDetail extends Panel {
	private static final Logger log = Logger.getLogger(UPSReplaceDetail.class);
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	private Link lnk;
	private String rmake;
	private String rmodel;
	private String rserial;
	private String rnoofbatteries;
	private String replacementdate;
	NetworkUPSDetail nud;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UPSReplaceDetail(String id, final IModel rowModel, IModel labelModel) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<UPSReplaceDetail>(this));
		nud = (NetworkUPSDetail) rowModel.getObject();
		getReplaceDetails();
		WebMarkupContainer mydiv = new WebMarkupContainer("replacedivision");
		mydiv.add(new Label("rmake"));
		mydiv.add(new Label("rmodel"));
		mydiv.add(new Label("rserial"));
		mydiv.add(new Label("rnoofbatteries"));
		mydiv.add(new Label("replacementdate"));
		Label lab = new Label("replbl", nud.getIstsposition());
		add(lab);
		add(mydiv);
		if (checkNull(nud.getIstsposition()).equals("Replace")) {
			mydiv.setVisible(true);
			lab.setVisible(false);
		} else {
			mydiv.setVisible(false);
			lab.setVisible(true);
		}
		/*
		 * lnk = new Link("link",rowModel){ private static final long
		 * serialVersionUID = 1L;
		 * 
		 * @Override public void onClick() { PageParameters params = new
		 * PageParameters(); ViewNetworkLocationDetail vad;
		 * 
		 * vad = new ViewNetworkLocationDetail(params,rowModel);
		 * setResponsePage(vad);
		 * 
		 * 
		 * } }; lnk.add(new AttributeModifier("style", "text-decoration:none"));
		 * lnk.add(new Label("accountchartname",labelModel)); add(lnk);
		 */
	}

	public boolean getReplaceDetails() {
		String query = "{call sp_circuit_ups_get_replace_details(?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setInt(3, nud.getUpsid());
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				rmake = rs.getString(1);
				rmodel = rs.getString(2);
				rserial = rs.getString(3);
				rnoofbatteries = rs.getString(4);
				// System.out.println(" getString replace "+rs.getString(5));
				replacementdate = rs.getString(5);
			}
		} catch (SQLException e) {
			log.error("SQL Exception in getReplaceDetails() method {" + e.getMessage() + "}");
			e.printStackTrace();
		} catch (Exception e) {
			log.error("SQL Exception in getReplaceDetails() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in getReplaceDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return true;
	}
	
	public String checkNull(String str){
		
		if(str==null)
         str = "" ;
			return str ;
	}

}
