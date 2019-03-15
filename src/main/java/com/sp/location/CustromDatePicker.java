 package com.sp.location;

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.Response;

public class CustromDatePicker extends DatePicker {
	@Override
	public void afterRender(Component component) {
		// TODO Auto-generated method stub
		Response response = component.getResponse();
		response.write("\n<span class=\"yui-skin-sam\"><span style=\"");

		if (renderOnLoad())
		{
			response.write("display:block;");
		}
		else
		{
			response.write("display:none;");
			response.write("position:absolute;");
		}

		response.write("z-index: 99999;\" id=\"");
		response.write(getEscapedComponentMarkupId());
		response.write("Dp\"></span>");

		if (renderOnLoad())
		{
			response.write("<br style=\"clear:left;\"/>");
		}
		response.write("</span>");
	}
	 @Override
     protected String getAdditionalJavaScript()
     {
         return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render();";
     }
}
