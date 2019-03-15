package com.sp.SPNetworkLocation;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;


public class PortSession extends WebSession{

	private String employeeid;
	private int sessionid;
	private String employeename;
	private boolean admin;
	private String lastlogintime;
	
	public PortSession(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	public static PortSession get() {

		HttpServletRequest request = (HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest();
		
		// If the Session id is not null & is Valid and is not new
		if(!(WebSession.get().isTemporary())){

			if(!(WebSession.exists())){
				//throw new SessionExpiredException("Session has expired!");
			}
			
			boolean expired = request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();
			if (expired) {
				//throw new SessionExpiredException("Session has expired!");
			}
		}
		return (PortSession) WebSession.get();
	}
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	public int getSessionid() {
		return sessionid;
	}
	public void setSessionid(int sessionid) {
		this.sessionid = sessionid;
	}
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getLastlogintime() {
		return lastlogintime;
	}
	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
}
