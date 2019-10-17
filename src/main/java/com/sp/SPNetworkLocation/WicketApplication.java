package com.sp.SPNetworkLocation;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import com.sp.location.AddNetworkLocationDetail;
import com.sp.master.Login;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see com.sp.SPNetworkLocation.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return Login.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	public PortSession newSession(Request request, Response response) {
		return new PortSession(request);
	};

	@Override
	public void init() {
		super.init();
		mountPackage("/addequipment", AddNetworkLocationDetail.class);
		// add your configuration here
	}
}
