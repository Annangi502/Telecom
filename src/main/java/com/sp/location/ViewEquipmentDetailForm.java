package com.sp.location;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.resource.CustomRadioChoice;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;
import com.sp.validators.StringValidator;

public class ViewEquipmentDetailForm extends Panel{
	private static final Logger log = Logger.getLogger(ViewEquipmentDetailForm.class);
	private String spcircuitid;
	private String spcircuitcode;
	private String projecttypedescription;
	private String make;
	private String makefeedback;
	private String model;
	private String modelfeedback;
	private String serialno;
	private String serialnofeedback;
	private String amc;
	private String amcfeedback;
	private String replace;
	private String replacefeedback;
	private String remark;
    private String remarkfeedback;
    private boolean replacedivflag;
    private String rmake;
	private String rmakefeedback;
	private String rmodel;
	private String rmodelfeedback;
	private String rserialno;
	private String rserialnofeedback;
    private static final List<String> TYPES = Arrays.asList("AMC", "Warranty");
    private static final List<String> R_TYPES = Arrays.asList("Replace", "Stand By");
	public ViewEquipmentDetailForm(String id,final IModel<NetworkLocationDetail> nldmodel) {
		super(id);
		// TODO Auto-generated constructor stub
		replace = "Stand By";
		/*setDefaultModel(new CompoundPropertyModel<ViewEquipmentDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("addeqpdetailform");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[]{FeedbackMessage.ERROR};
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));
		
		spcircuitid = nldmodel.getObject().getSpcircuitid();
		spcircuitcode = nldmodel.getObject().getSpciruitcode();
		projecttypedescription = nldmodel.getObject().getProjecttypedescription();
		
		TextField<String> make = new TextField<String>("make");
		make.setRequired(true).setLabel(new Model("Make"));
		make.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		make.add(new StringValidator());
		final FeedbackLabel makeFeedbackLabel = new FeedbackLabel("makefeedback", make);
		makeFeedbackLabel.setOutputMarkupId(true);
		form.add(makeFeedbackLabel);
		
		TextField<String> model = new TextField<String>("model");
		model.setRequired(true).setLabel(new Model("Model"));
		model.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		model.add(new StringValidator());
		final FeedbackLabel modelFeedbackLabel = new FeedbackLabel("modelfeedback", model);
		modelFeedbackLabel.setOutputMarkupId(true);
		form.add(modelFeedbackLabel);
		
		
		TextField<String> serialno = new TextField<String>("serialno");
		serialno.setRequired(true).setLabel(new Model("Serial No."));
		serialno.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		model.add(new StringValidator());
		final FeedbackLabel serialnoFeedbackLabel = new FeedbackLabel("serialnofeedback", serialno);
		serialnoFeedbackLabel.setOutputMarkupId(true);
		form.add(serialnoFeedbackLabel);
		
		final CustomRadioChoice<String> amc = new CustomRadioChoice("amc",TYPES);
		amc.setRequired(true).setLabel(new Model("Serial No."));
		final FeedbackLabel amcFeedbackLabel = new FeedbackLabel("amcfeedback", amc);
		amcFeedbackLabel.setOutputMarkupId(true);
		form.add(amcFeedbackLabel);
		
		
		final CustomRadioChoice<String> replaceradio = new CustomRadioChoice("replace",R_TYPES)
				{
			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}
			@Override
					protected void onSelectionChanged(Object newSelection) {
						// TODO Auto-generated method stub
						if(replace.equals("Replace"))
						{
							replacedivflag = true;
						}else
						{
							replacedivflag = false;
						}
					}
				};
		replaceradio.setRequired(true).setLabel(new Model("Replace/Stand By"));
		final FeedbackLabel replaceFeedbackLabel = new FeedbackLabel("replacefeedback", replaceradio);
		replaceFeedbackLabel.setOutputMarkupId(true);
		form.add(replaceFeedbackLabel);
		
		TextField<String> remark = new TextField<String>("remark");
		remark.setLabel(new Model("Remark"));
		remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 64));
		remark.add(new StringValidator());
		final FeedbackLabel remarkFeedbackLabel = new FeedbackLabel("remarkfeedback", remark);
		remarkFeedbackLabel.setOutputMarkupId(true);
		form.add(remarkFeedbackLabel);
		
		WebMarkupContainer repdiv = new WebMarkupContainer("replacediv")
				{
			@Override
			public boolean isVisible() {
				// TODO Auto-generated method stub
				return replacedivflag;
			}
				};

				TextField<String> rmake = new TextField<String>("rmake");
				rmake.setRequired(true).setLabel(new Model("Make"));
				rmake.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
				rmake.add(new StringValidator());
				final FeedbackLabel rmakeFeedbackLabel = new FeedbackLabel("rmakefeedback", rmake);
				rmakeFeedbackLabel.setOutputMarkupId(true);
				repdiv.add(rmakeFeedbackLabel);
				repdiv.add(rmake);
				
				TextField<String> rmodel = new TextField<String>("rmodel");
				rmodel.setRequired(true).setLabel(new Model("Model"));
				rmodel.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
				model.add(new StringValidator());
				final FeedbackLabel rmodelFeedbackLabel = new FeedbackLabel("rmodelfeedback", rmodel);
				rmodelFeedbackLabel.setOutputMarkupId(true);
				repdiv.add(rmodelFeedbackLabel);
				repdiv.add(rmodel);
				
				
				TextField<String> rserialno = new TextField<String>("rserialno");
				rserialno.setRequired(true).setLabel(new Model("Serial No."));
				rserialno.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
				model.add(new StringValidator());
				final FeedbackLabel rserialnoFeedbackLabel = new FeedbackLabel("rserialnofeedback", rserialno);
				rserialnoFeedbackLabel.setOutputMarkupId(true);
				repdiv.add(rserialnoFeedbackLabel);
				repdiv.add(rserialno);
				
		Button btncancel = new Button("btncancel")
		{
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				PageParameters parms = new PageParameters();
				ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
				setResponsePage(av);
			}
		}.setDefaultFormProcessing(false);
		
		Button btnreset = new Button("btnreset")
		{
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				PageParameters parms = new PageParameters();
				AddEquipmentDetail av = new AddEquipmentDetail(parms, nldmodel);
				setResponsePage(av);
			}
		}.setDefaultFormProcessing(false);
		
		Button btnsubmit = new Button("btnsubmit")
		{
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if(addNetworkEquipmentDetail()){
					PageParameters parms = new PageParameters();
					ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
					setResponsePage(av);
				}
			}
		}.setDefaultFormProcessing(true);
		form.add(new Label("spcircuitcode"));
		form.add(new Label("projecttypedescription"));
		form.add(make);
		form.add(model);
		form.add(serialno);
		form.add(amc);
		form.add(repdiv);
		form.add(remark);
		form.add(feedback);
		form.add(btncancel);
		form.add(btnreset);
		form.add(btnsubmit);
		form.add(replaceradio);
		add(form);*/
	}
	
	private boolean addNetworkEquipmentDetail() {
        final String query = "{call sp_circuit_add_network_equipment_details(?,?,?,?,?,?,?,?,?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            con = new DataBaseConnection().getConnection();
            stmt = con.prepareCall(query);
            stmt.setString(1, ((PortSession)this.getSession()).getEmployeeid());
            stmt.setInt(2, ((PortSession)this.getSession()).getSessionid());
            stmt.setString(3, spcircuitid);
            stmt.setString(4, make);
            stmt.setString(5, model);
            stmt.setString(6, serialno);
            stmt.setString(7, amc);
            stmt.setInt(8, replace.equals("Replace")?1:0);
            stmt.setString(9, rmake);
            stmt.setString(10,rmodel);
            stmt.setString(11, rserialno);
            stmt.setString(12, remark);
            log.info("Executing Stored Procedure { " + stmt.toString() + " }");
            rs = stmt.executeQuery();
            while (rs.next()) {
             log.info("Network Equipment Detail Added Successfully With ID :" + rs.getInt(1));
            }
        }
        catch (SQLException e) {
            log.error("SQL Exception in addNetworkEquipmentDetail() method {" + e.getMessage() + "}");
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
               log.error("SQL Exception in addNetworkEquipmentDetail() method {" + e2.getMessage() + "}");
                e2.printStackTrace();
            }
        }
        return true;
    }

}
