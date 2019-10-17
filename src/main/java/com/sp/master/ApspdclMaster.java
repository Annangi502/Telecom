package com.sp.master;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.exception.SessionExpiredException;

public class ApspdclMaster extends WebPage {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ApspdclMaster.class);
	private Header header;
	private LeftNav leftnav;
	private FormArea formarea;

	public ApspdclMaster(String id) {
		log.info("In GruhOnLineMaster page param constructor");
		if (getSession().exists() && !(getSession().isTemporary())) {
			MDC.put("userId", ((PortSession) getSession()));
		} else {
			log.info("Session does not exist");
			throw new SessionExpiredException("Session has expired!");
		}
		add(header = new Header("header"));
		add(leftnav = new LeftNav("leftnav"));
		add(formarea = new FormArea("formarea"));
	}

	public ApspdclMaster(final PageParameters parms) {
		super(parms);
		log.info("In GruhOnLineMaster page param constructor");
		if (getSession().exists() && !(getSession().isTemporary())) {
			MDC.put("userId", ((PortSession) getSession()).getEmployeename());
		} else {
			log.info("Session does not exist");
			throw new SessionExpiredException("Session has expired!");
		}
		add(header = new Header("header"));
		add(leftnav = new LeftNav("leftnav"));
		add(formarea = new FormArea("formarea"));
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	/*
	 * public MainNav getMainnav() { return mainnav; }
	 * 
	 * public void setMainnav(MainNav mainnav) { this.mainnav = mainnav; }
	 */

	public LeftNav getLeftnav() {
		return leftnav;
	}

	public void setLeftnav(LeftNav leftnav) {
		this.leftnav = leftnav;
	}

	public FormArea getFormarea() {
		return formarea;
	}

	public void setFormarea(FormArea formarea) {
		this.formarea = formarea;
	}

}