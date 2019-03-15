package com.sp.exception;

import org.apache.wicket.*;

public class SessionExpiredException extends WicketRuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public SessionExpiredException(final String message) {
        super(message);
    }
    
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}