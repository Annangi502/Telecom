package com.sp.SPNetworkLocation;

import org.apache.wicket.protocol.http.*;
import org.apache.wicket.request.*;
import javax.servlet.http.*;
import org.apache.wicket.request.cycle.*;
import com.sp.exception.*;

public class PortSession extends WebSession
{
    private String employeeid;
    private int sessionid;
    private String employeename;
    private boolean admin;
    private String lastlogintime;
    
    public PortSession(final Request request) {
        super(request);
    }
    
    public static PortSession get() {
        final HttpServletRequest request = (HttpServletRequest)RequestCycle.get().getRequest().getContainerRequest();
        if (!WebSession.get().isTemporary()) {
            if (!WebSession.exists()) {
                throw new SessionExpiredException("Session has expired!");
            }
            final boolean expired = request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();
            if (expired) {
                throw new SessionExpiredException("Session has expired!");
            }
        }
        return (PortSession)WebSession.get();
    }
    
    public String getEmployeeid() {
        return this.employeeid;
    }
    
    public void setEmployeeid(final String employeeid) {
        this.employeeid = employeeid;
    }
    
    public int getSessionid() {
        return this.sessionid;
    }
    
    public void setSessionid(final int sessionid) {
        this.sessionid = sessionid;
    }
    
    public String getEmployeename() {
        return this.employeename;
    }
    
    public void setEmployeename(final String employeename) {
        this.employeename = employeename;
    }
    
    public boolean isAdmin() {
        return this.admin;
    }
    
    public void setAdmin(final boolean admin) {
        this.admin = admin;
    }
    
    public String getLastlogintime() {
        return this.lastlogintime;
    }
    
    public void setLastlogintime(final String lastlogintime) {
        this.lastlogintime = lastlogintime;
    }
}