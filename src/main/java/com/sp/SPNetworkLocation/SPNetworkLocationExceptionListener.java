package com.sp.SPNetworkLocation;

import com.sp.exception.AppSessionExpiredException;
import com.sp.exception.SessionExpiredException;
import com.sp.exception.SessionExpiredPage;
import org.apache.log4j.Logger;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.protocol.http.PageExpiredException;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

public class SPNetworkLocationExceptionListener extends AbstractRequestCycleListener {
	private static final Logger log = Logger.getLogger(SPNetworkLocationExceptionListener.class);

	public SPNetworkLocationExceptionListener() {
	}

	public IRequestHandler onException(RequestCycle cycle, Exception ex) {
		Exception cause = (Exception) getRootCause(ex);
		if ((cause instanceof SessionExpiredException)) {
			log.error("SessionExpired Exception");
			return new RenderPageRequestHandler(new PageProvider(SessionExpiredPage.class));
		}
		if ((cause instanceof PageExpiredException)) {
			log.error("PageExpired Exception");
			return new RenderPageRequestHandler(new PageProvider(SessionExpiredPage.class));
		}
		if ((cause instanceof AppSessionExpiredException)) {
			log.error("ApplicationSessionExpired Exception");
			return new RenderPageRequestHandler(new PageProvider(SessionExpiredPage.class));
		}
		return super.onException(cycle, ex);
	}

	public Throwable getRootCause(Throwable ex) {
		if (ex.getCause() != null)
			return getRootCause(ex.getCause());
		return ex;
	}
}