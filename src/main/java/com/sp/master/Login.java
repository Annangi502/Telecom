package com.sp.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.resource.AES;
import com.sp.resource.DataBaseConnection;

public class Login extends WebPage {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Login.class);
	private String loginname;
	private String password;
	private String errormessage;
	public Login(final PageParameters parameters) {
		super(parameters);
		setDefaultModel(new CompoundPropertyModel<Login>(this));
		StatelessForm loginForm = new StatelessForm("loginform")
		{
			@Override
			protected void onSubmit() {
				// TODO Auto-generated method stub
				log.info("Login started @ "+Calendar.getInstance().getTime());
				if (doLogin()) {
					log.info("Login successfull");
					if (getSession().isTemporary()) {
						log.info("Wicket session is temporary & binding session");
						getSession().bind();
					}
					setResponsePage(ApspdclMaster.class);
				}
				else {
					log.error("Login failed");
				}
			}
		};
		
		
		
		Label em = new Label("errormessage");
		EmailTextField tfldLogin = new EmailTextField("loginname");
		PasswordTextField ptfldPassword = new PasswordTextField("password");	
		loginForm.add(tfldLogin);
		loginForm.add(ptfldPassword);
		loginForm.add(em);
		add(loginForm);
		

    }
	 private boolean doLogin()
	  {
	    String query = "{call sp_employee_login(?,?)}";
	    Connection con = null;
	    CallableStatement stmt = null;
	    ResultSet rs = null;
	    try {
	      con = new DataBaseConnection().getConnection();
	      stmt = con.prepareCall(query);
	      stmt.setString(1, loginname);
	      stmt.setString(2, AES.encrypt(password));
	      log.info("Executing Stored Procedure { " + stmt.toString() + " }");
	      rs = stmt.executeQuery();
	      while (rs.next())
	      {
	        log.info("Loading Application Session details");
	        ((PortSession)getSession()).setEmployeeid(rs.getString(1));
	        ((PortSession)getSession()).setSessionid(rs.getInt(2));
	        ((PortSession)getSession()).setLastlogintime(rs.getString(3));
	        ((PortSession)getSession()).setEmployeename(rs.getString(4));
	        boolean isadmin = rs.getInt(5) != 0;
	        ((PortSession)getSession()).setAdmin(isadmin);
	        log.info("Successfully loaded application session details");
	      }
	    }
	    catch (SQLException e) {
	      errormessage = e.getMessage();
	      log.error("SQL Exception in doLogin() method {" + e.getMessage() + "}");
	      e.printStackTrace();
	    }
	    finally {}
	    try
	    {
	      if (rs != null)
	        rs.close();
	      if (stmt != null)
	        stmt.close();
	      if (con != null) {
	        con.close();
	      }
	    } catch (SQLException e) {
	      log.error("SQL Exception in addNetworkLocationDetails() method {" + e.getMessage() + "}");
	      e.printStackTrace();
	    }
	    return true;
	  }
	 protected void onConfigure()
	  {
	    super.onConfigure();
	    log.info("in onConfigure");
	    if ((PortSession.exists()) && (!((PortSession)getSession()).isTemporary())) {
	      log.info("in onConfigure - Session exists and redirecting to Welcome Page");
	      throw new RestartResponseException(ApspdclMaster.class);
	    }
	  }  
}
