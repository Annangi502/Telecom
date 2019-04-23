package com.sp.resource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.wicket.Application;
import com.sp.location.AddNetworkLocationDetailForm;

public class DataBaseConnection {

	private Connection con;
	private Context envContext;
	private static final Logger log = Logger.getLogger(DataBaseConnection.class);
	private enum ConfigurationType {DEVELOPMENT,STAGING,DEPLOYMENT};
	public DataBaseConnection() {
		// TODO Auto-generated constructor stub
	con = null;
	envContext = null;
	}
	public Connection getConnection()
	{
		ConfigurationType ctype = ConfigurationType.valueOf(Application.get().getConfigurationType().toString().toUpperCase());
		try {
			envContext=new InitialContext();
			Context initContext=(Context) envContext.lookup("java:comp/env");
			DataSource ds=(DataSource) initContext.lookup(ctype.toString());
			con=ds.getConnection();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("SQL Exception while connecting to Database");
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
}
