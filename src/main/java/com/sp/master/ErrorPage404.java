package com.sp.master;

import org.apache.wicket.markup.html.*;
import org.apache.wicket.request.mapper.parameter.*;
import org.apache.wicket.*;
import org.apache.wicket.markup.html.link.*;

public class ErrorPage404 extends WebPage
{
    private static final long serialVersionUID = 1L;
    
    public ErrorPage404(final PageParameters parameters) {
        add(new BookmarkablePageLink("home", Index.class));
    }
}