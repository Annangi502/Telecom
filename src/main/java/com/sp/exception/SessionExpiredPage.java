package com.sp.exception;

import org.apache.wicket.markup.html.*;
import org.apache.wicket.request.mapper.parameter.*;
import org.apache.wicket.*;
import com.sp.master.*;
import org.apache.wicket.markup.html.link.*;

public class SessionExpiredPage extends WebPage
{
    private static final long serialVersionUID = 1L;
    
    public SessionExpiredPage(final PageParameters params) {
        super(params);
        add(new BookmarkablePageLink("login",Index.class) );
    }
}