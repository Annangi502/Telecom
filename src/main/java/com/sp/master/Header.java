package com.sp.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import com.sp.SPNetworkLocation.PortSession;
import com.sp.resource.DataBaseConnection;

public class Header extends Panel {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Header.class);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Header(String id)
	{
		super(id);
		//add(new BookmarkablePageLink("logout",OLLogin.class));
		add(new Label("name", ((PortSession) getSession()).getEmployeename()));
		add(new Link("logout")
				{
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				if(getSession().exists()){
					MDC.put("userId", ((PortSession)getSession()).getEmployeename());
				}

				if (doLogout()) {
					log.info("Logged out successfully ["+ PortSession.get().getEmployeeid() + "]");
				} else {
					log.error("Logging out failed for ["+ PortSession.get().getEmployeename() + "]");
				}
				MDC.put("userId","");
				getSession().invalidate();
				setResponsePage(Login.class);
				
			}
				});
	}

	    private boolean doLogout() {
	        final String query = "{call sp_employee_logout(?,?)}";
	        Connection con = null;
	        CallableStatement stmt = null;
	        ResultSet rs = null;
	        try {
	            con = new DataBaseConnection().getConnection();
	            stmt = con.prepareCall(query);
	            stmt.setString(1, ((PortSession)this.getSession()).getEmployeeid());
	            stmt.setInt(2, ((PortSession)this.getSession()).getSessionid());
	            log.info((Object)("Executing Stored Procedure { " + stmt.toString() + " }"));
	            rs = stmt.executeQuery();
	            while (rs.next()) {
	             log.info("Logout  Successfully With ID :" + rs.getString(1));
	            }
	        }
	        catch (SQLException e) {
	            log.error("SQL Exception in employee_id method {" + e.getMessage() + "}");
	            e.printStackTrace();
	            return false;
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
	                log.error("SQL Exception in doLogout() method {" + e2.getMessage() + "}");
	                e2.printStackTrace();
	            }
	        }

	        return true;
	    }

}

