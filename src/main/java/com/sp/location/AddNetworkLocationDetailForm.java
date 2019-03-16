package com.sp.location;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import com.sp.SPNetworkLocation.PortSession;
import com.sp.master.ApspdclMaster;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;
import com.sp.validators.LandLineNumberValidator;
import com.sp.validators.MobileNoValidator;
import com.sp.validators.NumberValidator;
import com.sp.validators.StringValidator;

public class AddNetworkLocationDetailForm extends Panel{
	private static final Logger log = Logger.getLogger(AddNetworkLocationDetailForm.class);
	private ProjectType projecttypes;
	private String projecttypefeedback;
	private String townname;
	private String townnamefeedback;
	private Date installationdate;
	private String installfeedback;
	private Date connecteddate;
	private String connfeedback;
	private String phase;
    private String phasefeedback;
    private String noofpoints;
    private String noofpointsfeedback;
    private String ofcdesc;
    private String ofcdescfeedback;
    private String ofccontact;
    private String ofccontactfeedback;
    private String ofcaddress;
    private String ofcaddressfeedback;
    private String contactperson;
    private String contactpersonfeedback;
    private String contact;
    private String contactfeedback;
    private String remark;
    private String remarkfeedback;
    private Circle circle;
    private String circlefeedback;
    private int circleid;
    private Division division;
    private String divisionfeedback;
    private SubDivision subdivision;
    private String subdivisionfeedback;
    private int divisionid;
    private int subdivisionid;
    private int sectionid;
    private Section section;
    private String sectionfeedback;
    private NetworkLocationDetail nlcd = new NetworkLocationDetail();
    private static String PATTERN = "yyyy-MM-dd";
    IModel<? extends List<Circle>> circlelist=new LoadableDetachableModel<List<Circle>>() {
		@Override
		protected List<Circle> load() {
			// TODO Auto-generated method stub
			return loadCircles();
		}
	};
    IModel<? extends List<Division>> divisionlist =  new LoadableDetachableModel<List<Division>>() {
		@Override
		protected List<Division> load() {
			// TODO Auto-generated method stub
			return loadDivisions(circleid);
		}
	};
    IModel<? extends List<SubDivision>> subdivisionlist =  new LoadableDetachableModel<List<SubDivision>>() {
		@Override
		protected List<SubDivision> load() {
			// TODO Auto-generated method stub
			return loadSubDivisions(circleid, divisionid);
		}
	};
    IModel<? extends List<Section>> sectionlist =  new LoadableDetachableModel<List<Section>>() {
		@Override
		protected List<Section> load() {
			// TODO Auto-generated method stub
			return loadSections(circleid, divisionid, subdivisionid);
		}
	};
	IModel<? extends List<ProjectType>> projectlist = new LoadableDetachableModel<List<ProjectType>>() {

		@Override
		protected List<ProjectType> load() {
			// TODO Auto-generated method stub
			return loadProjectTypes();
		}
	};
	
	public AddNetworkLocationDetailForm(String id) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<AddNetworkLocationDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("form");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[]{FeedbackMessage.ERROR};
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));
		final DropDownChoice<Section> sectiondd = new DropDownChoice<Section>("section", sectionlist, new ChoiceRenderer("sectiondesc", "sectionid"));
	    sectiondd.add(new AjaxFormComponentUpdatingBehavior("change")
	    		{
					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						// TODO Auto-generated method stub
						if(section!=null){
						sectionid  = section.getSectionid();
						}
					}
	    	
	    		});
	    sectiondd.setNullValid(true);
	    sectiondd.setLabel(new Model("Section"));
	    sectiondd.setOutputMarkupId(true);
	    final FeedbackLabel sectionFeedbackLabel = new FeedbackLabel("sectionfeedback", sectiondd);
        sectionFeedbackLabel.setOutputMarkupId(true);
        form.add(sectionFeedbackLabel);
	    form.add(sectiondd);
	    
	    final DropDownChoice<SubDivision> subdivisiondd = new DropDownChoice<SubDivision>("subdivision", subdivisionlist, new ChoiceRenderer("subdivisiondesc", "subdivisionid"));
	    subdivisiondd.add(new AjaxFormComponentUpdatingBehavior("change")
		{
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// TODO Auto-generated method stub.
				if(subdivision!=null){
				subdivisionid = subdivision.getSubdivisionid();
				section = null;
				target.add(sectiondd);}
				else{
					section = null;
					target.add(sectiondd);	
				}
			}
	
		});
	    subdivisiondd.setNullValid(true);
	    subdivisiondd.setLabel(new Model("Sub-Division"));
	    subdivisiondd.setOutputMarkupId(true);
	    final FeedbackLabel subdivisionFeedbackLabel = new FeedbackLabel("subdivisionfeedback",subdivisiondd);
        subdivisionFeedbackLabel.setOutputMarkupId(true);
        form.add(subdivisionFeedbackLabel);
	    form.add(subdivisiondd);
	    
	    
	    final DropDownChoice<Division> divisiondd = new DropDownChoice<Division>("division", divisionlist, new ChoiceRenderer("divisiondesc", "divisionid"));
	    divisiondd.add(new AjaxFormComponentUpdatingBehavior("change")
		{
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				if(division!=null){
				divisionid = division.getDivisionid();
				section = null;
				subdivision = null;
				target.add(sectiondd);
				target.add(subdivisiondd);
				}else
				{
					section = null;
					subdivision = null;
					target.add(sectiondd);
					target.add(subdivisiondd);	
				}
			}
	
		});
	    divisiondd.setNullValid(true);
	    divisiondd.setLabel(new Model("Division"));
	    divisiondd.setOutputMarkupId(true);	 
	    final FeedbackLabel divisionFeedbackLabel = new FeedbackLabel("divisionfeedback", divisiondd);
        divisionFeedbackLabel.setOutputMarkupId(true);
        form.add(divisionFeedbackLabel);
	    form.add(divisiondd);
	    
	    DropDownChoice<Circle> circledd = new DropDownChoice<Circle>("circle", circlelist, new ChoiceRenderer("circledes", "circleid"));
	    circledd.add(new AjaxFormComponentUpdatingBehavior("change")
		{
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				circleid = (circle != null)?circle.getCircleid():0;
				System.out.println(divisionid);
				System.out.println(subdivisionid);
				System.out.println(sectionid);
				section = null;
				subdivision = null;
				division = null;
				target.add(sectiondd);
				target.add(subdivisiondd);
				target.add(divisiondd);
				
			}
	
		});
	    circledd.setNullValid(false);
	    circledd.setRequired(true).setLabel(new Model("Circle")); 
	    final FeedbackLabel circleFeedbackLabel = new FeedbackLabel("circlefeedback",circledd);
        circleFeedbackLabel.setOutputMarkupId(true);
        form.add(circleFeedbackLabel);
	    form.add(circledd);
		
		
		
		DropDownChoice<ProjectType> projecttype = new DropDownChoice<ProjectType>("projecttypes", projectlist, new ChoiceRenderer<ProjectType>("projecttypedescription"));
		projecttype.setNullValid(false);
		projecttype.setRequired(true).setLabel(new Model("Project Type"));
		final FeedbackLabel projecttypeFeedbackLabel = new FeedbackLabel("projecttypefeedback", projecttype);
		projecttypeFeedbackLabel.setOutputMarkupId(true);
		form.add(projecttypeFeedbackLabel);
		
		
		TextField<String> tname = new TextField<String>("townname");
		tname.setRequired(true).setLabel(new Model("Town Name"));
		tname.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 20));
		tname.add(new StringValidator());
		final FeedbackLabel townnameFeedbackLabel = new FeedbackLabel("townnamefeedback", tname);
		townnameFeedbackLabel.setOutputMarkupId(true);
		form.add(townnameFeedbackLabel);
		
		CustromDatePicker datePicker = new CustromDatePicker();
        datePicker.setShowOnFieldClick(true);
        datePicker.setAutoHide(false);
		
        
        DateTextField instaldate = new DateTextField("installationdate",new PropertyModel<Date>(
		            this, "installationdate"),new PatternDateConverter("dd MMM, yyyy", true));
		
		/*DateTextField instaldate = new DateTextField("installationdate","dd-mm-yyy")
		{
			  protected String getInputType()
	            {
	                return "date";
	            }  
	        };*/
        instaldate.setRequired(true).setLabel(new Model("Installation Date"));
        final FeedbackLabel insfeedback = new FeedbackLabel("installfeedback", instaldate);
        instaldate.setOutputMarkupId(true);
        instaldate.add(datePicker);
        form.add(insfeedback);
        
        
        CustromDatePicker datePicker1 = new CustromDatePicker();
        datePicker1.setShowOnFieldClick(true);
        datePicker1.setAutoHide(false);
        
        DateTextField condate = new DateTextField("connecteddate", new PropertyModel<Date>(
                this, "connecteddate"),new PatternDateConverter("dd MMM, yyyy", true));
        condate.setRequired(true).setLabel(new Model("Date of Connected"));
        final FeedbackLabel connfeedback = new FeedbackLabel("connfeedback", condate);
        condate.setOutputMarkupId(true);
        condate.add(datePicker1);
        form.add(connfeedback);
        
        
        TextField<String> phase = new TextField<String>("phase");
        phase.setRequired(true).setLabel(new Model("Phase"));
        phase.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 20));
      /*  phase.add(new StringValidator());*/
		final FeedbackLabel phaseFeedbackLabel = new FeedbackLabel("phasefeedback", phase);
		phaseFeedbackLabel.setOutputMarkupId(true);
		form.add(phaseFeedbackLabel);
        
		
		TextField<String> points = new TextField<String>("noofpoints");
		points.setRequired(true).setLabel(new Model("No.of Points Available"));
		points.add(new NumberValidator());
		final FeedbackLabel pointsFeedbackLabel = new FeedbackLabel("noofpointsfeedback", points);
		pointsFeedbackLabel.setOutputMarkupId(true);
		form.add(pointsFeedbackLabel);
		
		
		TextField<String> ofc = new TextField<String>("ofcdesc");
		ofc.setRequired(true).setLabel(new Model("Office Description"));
		ofc.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 20));
		ofc.add(new StringValidator());
		final FeedbackLabel ofcFeedbackLabel = new FeedbackLabel("ofcdescfeedback", ofc);
		ofcFeedbackLabel.setOutputMarkupId(true);
		form.add(ofcFeedbackLabel);
		
		TextField<String> ofcno = new TextField<String>("ofccontact");
		ofcno.setRequired(true).setLabel(new Model("Office Landline No."));
		ofcno.add(new LandLineNumberValidator());
		final FeedbackLabel ofcnoFeedbackLabel = new FeedbackLabel("ofccontactfeedback", ofcno);
		ofcnoFeedbackLabel.setOutputMarkupId(true);
		form.add(ofcnoFeedbackLabel);
        
		
		TextArea<String> ofcadd = new TextArea<String>("ofcaddress");
		ofcadd.setRequired(true).setLabel(new Model("Office Address"));
		ofcadd.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 120));
		/*ofcadd.add(new StringValidator());*/
		final FeedbackLabel ofcaddFeedbackLabel = new FeedbackLabel("ofcaddressfeedback", ofcadd);
		ofcaddFeedbackLabel.setOutputMarkupId(true);
		form.add(ofcaddFeedbackLabel);
		
		
		TextField<String> conperson = new TextField<String>("contactperson");
		conperson.setRequired(true).setLabel(new Model("Location Contact Person "));
		conperson.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 64));
		conperson.add(new StringValidator());
		final FeedbackLabel conpersonFeedbackLabel = new FeedbackLabel("contactpersonfeedback", conperson);
		conpersonFeedbackLabel.setOutputMarkupId(true);
		form.add(conpersonFeedbackLabel);
		
		TextField<String> contact = new TextField<String>("contact");
		contact.setRequired(true).setLabel(new Model("Contact No."));
		contact.add(new MobileNoValidator());
		final FeedbackLabel contactFeedbackLabel = new FeedbackLabel("contactfeedback", contact);
		contactFeedbackLabel.setOutputMarkupId(true);
		form.add(contactFeedbackLabel);
		
		
		TextField<String> remark = new TextField<String>("remark");
		remark.setLabel(new Model("Remark"));
		remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 64));
		/*remark.add(new StringValidator());*/
		final FeedbackLabel remarkFeedbackLabel = new FeedbackLabel("remarkfeedback", remark);
		remarkFeedbackLabel.setOutputMarkupId(true);
		form.add(remarkFeedbackLabel);
		
		Button btncancel = new Button("btncancel")
		{
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				setResponsePage(ApspdclMaster.class);
			}
		}.setDefaultFormProcessing(false);
		
		Button btnreset = new Button("btnreset")
		{
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				setResponsePage(AddNetworkLocationDetail.class);
			}
		}.setDefaultFormProcessing(false);
		
		Button btnsubmit = new Button("btnsubmit")
		{
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if(addNetworkLocationDetails())
				{
					PageParameters parms = new PageParameters();
					ViewNetworkLocationDetail vnld = new ViewNetworkLocationDetail(parms, new CompoundPropertyModel<NetworkLocationDetail>(nlcd));
				setResponsePage(vnld);
				}
			}
		}.setDefaultFormProcessing(true);
		
		
        form.add(projecttype);
        form.add(tname);
        form.add(instaldate);
        form.add(condate);
        form.add(phase);
        form.add(points);
        form.add(ofc);
        form.add(ofcno);
        form.add(ofcadd);
        form.add(conperson);
        form.add(contact);
        form.add(remark);
		form.add(feedback);
		form.add(btncancel);
		form.add(btnreset);
		form.add(btnsubmit);
		
		add(form);
		
		// TODO Auto-generated constructor stub
	}

	private List<ProjectType> loadProjectTypes()
	{
		List<ProjectType> list = new ArrayList<ProjectType>();
		String query = "{call sp_get_project_types(?,?)}";
		Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            con = new DataBaseConnection().getConnection();
            stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
		    rs = stmt.executeQuery();
		    log.info("Executing Stored Procedure { "+stmt.toString()+" }");
		    while(rs.next())
		    {
		    	list.add(new ProjectType(rs.getInt(1),rs.getString(2)));
		    }
		}catch (SQLException e) {
			log.error("SQL Exception in loadProjectTypes() method {"+e.getMessage()+"}");
			e.printStackTrace();
		}finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e2) {
                log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
                e2.printStackTrace();
            }
		}
		return list;
	}
	
	private boolean addNetworkLocationDetails() {
        final String query = "{call sp_circuit_add_details(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            con = new DataBaseConnection().getConnection();
            stmt = con.prepareCall(query);
            stmt.setString(1, ((PortSession)this.getSession()).getEmployeeid());
            stmt.setInt(2, ((PortSession)this.getSession()).getSessionid());
            stmt.setInt(3, this.projecttypes.getProjecttypeid());
            stmt.setString(4, this.phase);
            stmt.setInt(5, Integer.parseInt(this.noofpoints));
            stmt.setString(6, this.getFormatDate(this.installationdate));
            stmt.setString(7, this.getFormatDate(this.connecteddate));
            stmt.setString(8, this.ofcdesc);
            stmt.setString(9, this.ofccontact);
            stmt.setString(10, this.ofcaddress);
            stmt.setString(11, this.contactperson);
            stmt.setString(12, this.contact);
            stmt.setString(13, this.remark);
            stmt.setString(14, this.townname);
            stmt.setInt(15, this.circleid);
            stmt.setInt(16, this.divisionid);
            stmt.setInt(17, this.subdivisionid);
            stmt.setInt(18, this.sectionid);
            AddNetworkLocationDetailForm.log.info((Object)("Executing Stored Procedure { " + stmt.toString() + " }"));
            rs = stmt.executeQuery();
            while (rs.next()) {
                this.nlcd.setSpcircuitid(rs.getString(1));
               log.info("Circuit Detail Added Successfully With ID :" + rs.getString(1));
            }
        }
        catch (SQLException e) {
            log.error("SQL Exception in addNetworkLocationDetails() method {" + e.getMessage() + "}");
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e2) {
                log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
                e2.printStackTrace();
            }
        }       
        return true;
    }
	private List<Circle> loadCircles() {
		final List<Circle> list = new ArrayList<Circle>();
		final String query = "{call sp_get_circles(?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				list.add(new Circle(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadCircles() method {" + e.getMessage() + "}");
			e.printStackTrace();
			return list;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e2) {
				log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}

	private List<Division> loadDivisions(final int circleid) {
		final List<Division> list = new ArrayList<Division>();
		final String query = "{call sp_circle_get_devisions(?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, circleid);
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				list.add(new Division(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadDivisions() method {" + e.getMessage() + "}");
			e.printStackTrace();
			return list;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e2) {
				log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}

	private List<SubDivision> loadSubDivisions(final int circleid, final int divisionid) {
		final List<SubDivision> list = new ArrayList<SubDivision>();
		final String query = "{call sp_division_get_sub_divisions(?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, circleid);
			stmt.setInt(4, divisionid);
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				list.add(new SubDivision(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadSubDivisions() method {" + e.getMessage() + "}");
			e.printStackTrace();
			return list;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e2) {
				log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}

	private List<Section> loadSections(final int circleid, final int divisionid, final int subdivision) {
		final List<Section> list = new ArrayList<Section>();
		final String query = "{call sp_sub_division_get_sections(?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, circleid);
			stmt.setInt(4, divisionid);
			stmt.setInt(5, subdivision);
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				list.add(new Section(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadSections() method {" + e.getMessage() + "}");
			e.printStackTrace();
			return list;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e2) {
				log.error("SQL Exception in addNetworkLocationDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}
	private String getFormatDate(Date date)
	{
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
	    return simpleDateFormat.format(date);
		
	}
}
