package com.sp.location;

import java.sql.CallableStatement;
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
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
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
    private NetworkLocationDetail nlcd = new NetworkLocationDetail();
    private static String PATTERN = "yyyy-MM-dd";

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
        
		
		TextField<String> ofcadd = new TextField<String>("ofcaddress");
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
				System.out.println("DATE: "+installationdate.toString()+"...."+connecteddate.toString());
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
		try {
			CallableStatement stmt = new DataBaseConnection().getConnection().prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
		    ResultSet rs = stmt.executeQuery();
		    log.info("Executing Stored Procedure { "+stmt.toString()+" }");
		    while(rs.next())
		    {
		    	list.add(new ProjectType(rs.getInt(1),rs.getString(2)));
		    }
		}catch (SQLException e) {
			log.error("SQL Exception in loadProjectTypes() method {"+e.getMessage()+"}");
			e.printStackTrace();
		}
		return list;
	}
	
	private boolean addNetworkLocationDetails()
	{
		String query = "{call sp_circuit_add_details(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		try {
			CallableStatement stmt = new DataBaseConnection().getConnection().prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setInt(3,projecttypes.getProjecttypeid());
			stmt.setString(4, phase);
			stmt.setInt(5, Integer.parseInt(noofpoints));
			stmt.setString(6, getFormatDate(installationdate));
			stmt.setString(7,getFormatDate(connecteddate));
			stmt.setString(8,ofcdesc);
			stmt.setString(9,ofccontact);
			stmt.setString(10,ofcaddress);
			stmt.setString(11,contactperson);
			stmt.setString(12,contact);
			stmt.setString(13,remark);
			stmt.setString(14, townname);
			log.info("Executing Stored Procedure { "+stmt.toString()+" }");
			
		    ResultSet rs = stmt.executeQuery();
		    while(rs.next())
		    {
		    	nlcd.setSpcircuitid(rs.getString(1));
		    	log.info("Circuit Detail Added Successfully With ID :"+rs.getString(1));
		    }
		}catch (SQLException e) {
			log.error("SQL Exception in addNetworkLocationDetails() method {"+e.getMessage()+"}");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private String getFormatDate(Date date)
	{
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
	    return simpleDateFormat.format(date);
		
	}
}
