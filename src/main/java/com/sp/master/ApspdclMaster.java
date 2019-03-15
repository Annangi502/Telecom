package com.sp.master;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;



public class ApspdclMaster extends WebPage {

	private static final long serialVersionUID = 1L;
	private Header header;
	private LeftNav leftnav;
	private FormArea formarea;
	public ApspdclMaster(String id)
	{
		add(header = new Header("header"));
		add(leftnav = new LeftNav("leftnav"));
		add(formarea = new FormArea("formarea"));
	}
	
	public ApspdclMaster(final PageParameters parms){
		super(parms);
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

/*	public MainNav getMainnav() {
		return mainnav;
	}

	public void setMainnav(MainNav mainnav) {
		this.mainnav = mainnav;
	}*/

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