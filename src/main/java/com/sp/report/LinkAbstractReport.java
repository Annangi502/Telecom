package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class LinkAbstractReport extends ApspdclMaster{

	public LinkAbstractReport(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new LinkAbstractReportForm("formarea"));
	}

}
