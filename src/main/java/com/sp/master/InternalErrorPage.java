package com.sp.master;

import org.apache.wicket.markup.html.*;
import org.apache.wicket.request.mapper.parameter.*;
import org.apache.wicket.*;
import org.apache.wicket.markup.html.link.*;

public class InternalErrorPage extends WebPage
{
    private static final long serialVersionUID = 1L;
    
    public InternalErrorPage(final PageParameters params) {
        super(params);
        add(new BookmarkablePageLink("home", ApspdclMaster.class));
    }
}