package com.sp.exception;

import org.apache.wicket.*;

public class AppSessionExpiredException extends WicketRuntimeException {
	private static final long serialVersionUID = 1L;

	public AppSessionExpiredException(final String message) {
		super(message);
	}

	public synchronized Throwable fillInStackTrace() {
		return null;
	}
}