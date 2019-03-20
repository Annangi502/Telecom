package com.sp.SPNetworkLocation;

import com.sp.location.AddNetworkLocationDetail;
import com.sp.master.ApspdclMaster;
import com.sp.master.ErrorPage404;
import com.sp.master.Index;
import com.sp.report.Report;
import com.sp.master.Login;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycleListenerCollection;
import org.apache.wicket.settings.IApplicationSettings;
import org.apache.wicket.settings.IDebugSettings;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.settings.IMarkupSettings;

public class SpNetworkLocationApplication extends WebApplication
{
  public SpNetworkLocationApplication() {}
  
  public Class<? extends org.apache.wicket.Page> getHomePage()
  {
    return Index.class;
  }
  
  protected void init()
  {
    super.init();
    getRequestCycleListeners().add(new SPNetworkLocationExceptionListener());
    IApplicationSettings asSettings = getApplicationSettings();
    asSettings.setInternalErrorPage(com.sp.master.InternalErrorPage.class);
    getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
    

    getMarkupSettings().setDefaultBeforeDisabledLink("<strong>");
    getMarkupSettings().setDefaultBeforeDisabledLink("</strong>");
    getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
    getDebugSettings().setAjaxDebugModeEnabled(false);
    
    mountPage("error404", ErrorPage404.class);
    mountPackage("/addequipment", AddNetworkLocationDetail.class);
    mountPackage("/master", ApspdclMaster.class);
    mountPackage("/master", Login.class);
    mountPackage("/report", Report.class);
  }
  



  public PortSession newSession(Request request, Response response)
  {
    return new PortSession(request);
  }
}
