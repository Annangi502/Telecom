package com.sp.report;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.master.ApspdclMaster;

public class BandwidthAbstractReport extends ApspdclMaster {

	public BandwidthAbstractReport(PageParameters parms) {
		super(parms);
		// TODO Auto-generated constructor stub
		replace(new BandwidthAbstractReportForm("formarea"));
	}

}
