package com.sp.location;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;
import com.sp.validators.StringValidator;

public class EditInterfaceDetailForm extends Panel {
	private static final Logger log = Logger.getLogger(EditInterfaceDetailForm.class);
	private String spcircuitid;
	private String spcircuitcode;
	private String projecttypedescription;
	private String ntinterface;
	private String ntinterfacefeedback;
	private String ipaddress;
	private String ipaddressfeedback;
	private String subnetmask;
	private String subnetmaskfeedback;
	private String spntinterface;
	private String spntinterfacefeedback;
	private String spipaddress;
	private String spipaddressfeedback;
	private String spsubnetmask;
	private String spsubnetmaskfeedback;
	private NetworkVendorDetail vendor;
	private String vendorfeedback;
	private String remark;
	private String remarkfeedback;
	private String locationname;
	/*	private String equipment;
	private String equipmentfeedback;*/
	private NetworkInterfaceDetail nid;
	 IModel<? extends List<NetworkVendorDetail>> vendorlist=new LoadableDetachableModel<List<NetworkVendorDetail>>() {
			@Override
			protected List<NetworkVendorDetail> load() {
				// TODO Auto-generated method stub
				return getVendors();
			}
		};
	public EditInterfaceDetailForm(String id, final IModel<NetworkInterfaceDetail> nidmodel, final IModel<NetworkLocationDetail> nldmodel) {
		super(id);
		// TODO Auto-generated constructor stub

		setDefaultModel(new CompoundPropertyModel<EditInterfaceDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("addnetworkinterfaceform");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[] { FeedbackMessage.ERROR };
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));

		nid = nidmodel.getObject();
		ntinterface = nid.getNtinterface();
		ipaddress = nid.getIpaddress();
		subnetmask = nid.getSubnetmask();
		spntinterface = nid.getSpntinterface();
		spipaddress = nid.getSpipaddress();
		spsubnetmask = nid.getSpsubnetmask();
		remark = nid.getRemark();
		
		spcircuitid = nldmodel.getObject().getSpcircuitid();
		spcircuitcode = nldmodel.getObject().getSpciruitcode();
		projecttypedescription = nldmodel.getObject().getProjecttypedescription();
		locationname = nldmodel.getObject().getLocationname();
		WebMarkupContainer wmodal = new WebMarkupContainer("warningmodal");
		wmodal.add(new Link("closelink"){
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				PageParameters parms = new PageParameters();
				ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
				setResponsePage(av);
			}
		});
		add(wmodal);
		if(vendorlist.getObject().size()==0){
			wmodal.setVisible(true);
		}else{
			wmodal.setVisible(false);
		}

		/*TextField<String> equipment = new TextField<String>("equipment");
		equipment.setRequired(true).setLabel(new Model("Make"));
		equipment.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		equipment.add(new StringValidator());
		final FeedbackLabel equipmentFeedbackLabel = new FeedbackLabel("equipmentfeedback", equipment);
		equipmentFeedbackLabel.setOutputMarkupId(true);
		form.add(equipmentFeedbackLabel);*/

		TextField<String> ntinterface = new TextField<String>("ntinterface");
		ntinterface.setRequired(true).setLabel(new Model("Interface"));
		ntinterface.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* ntinterface.add(new StringValidator()); */
		final FeedbackLabel ntinterfaceFeedbackLabel = new FeedbackLabel("ntinterfacefeedback", ntinterface);
		ntinterfaceFeedbackLabel.setOutputMarkupId(true);
		form.add(ntinterfaceFeedbackLabel);

		TextField<String> ipaddress = new TextField<String>("ipaddress");
		ipaddress.setRequired(true).setLabel(new Model("Ip Address"));
		ipaddress.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* ipaddress.add(new StringValidator()); */
		final FeedbackLabel ipaddressFeedbackLabel = new FeedbackLabel("ipaddressfeedback", ntinterface);
		ipaddressFeedbackLabel.setOutputMarkupId(true);
		form.add(ipaddressFeedbackLabel);

		TextField<String> subnetmask = new TextField<String>("subnetmask");
		subnetmask.setRequired(true).setLabel(new Model("SubNet Mask"));
		subnetmask.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* ipaddress.add(new StringValidator()); */
		final FeedbackLabel subnetmaskFeedbackLabel = new FeedbackLabel("subnetmaskfeedback", subnetmask);
		subnetmaskFeedbackLabel.setOutputMarkupId(true);
		form.add(subnetmaskFeedbackLabel);
		
		Label vname = (Label) new Label("vendorname",(vendorlist.getObject().size()==1?vendorlist.getObject().get(0).getVendorname():"")).setVisible(vendorlist.getObject().size()==1?true:false);
		form.add(vname);
		DropDownChoice<NetworkVendorDetail> vendor = (DropDownChoice<NetworkVendorDetail>) new DropDownChoice<NetworkVendorDetail>("vendor", vendorlist, new ChoiceRenderer<NetworkVendorDetail>("vendorname")).setVisible(vendorlist.getObject().size()>1?true:false);;
/*		TextField<String> vendor = new TextField<String>("vendor");
		vendor.setRequired(true).setLabel(new Model("Vendor"));
		vendor.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));*/
		/* ipaddress.add(new StringValidator()); */
		final FeedbackLabel vendorFeedbackLabel = new FeedbackLabel("vendorfeedback", vendor);
		vendorFeedbackLabel.setOutputMarkupId(true);
		form.add(vendorFeedbackLabel);
		
		
		
		TextField<String> spntinterface = new TextField<String>("spntinterface");
		spntinterface.setRequired(true).setLabel(new Model("Interface"));
		spntinterface.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* ntinterface.add(new StringValidator()); */
		final FeedbackLabel spntinterfaceFeedbackLabel = new FeedbackLabel("spntinterfacefeedback", spntinterface);
		spntinterfaceFeedbackLabel.setOutputMarkupId(true);
		form.add(spntinterfaceFeedbackLabel);

		TextField<String> spipaddress = new TextField<String>("spipaddress");
		spipaddress.setRequired(true).setLabel(new Model("Ip Address"));
		spipaddress.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* ipaddress.add(new StringValidator()); */
		final FeedbackLabel spipaddressFeedbackLabel = new FeedbackLabel("spipaddressfeedback", spipaddress);
		spipaddressFeedbackLabel.setOutputMarkupId(true);
		form.add(spipaddressFeedbackLabel);

		TextField<String> spsubnetmask = new TextField<String>("spsubnetmask");
		spsubnetmask.setRequired(true).setLabel(new Model("SubNet Mask"));
		spsubnetmask.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		/* ipaddress.add(new StringValidator()); */
		final FeedbackLabel spsubnetmaskFeedbackLabel = new FeedbackLabel("spsubnetmaskfeedback", spsubnetmask);
		spsubnetmaskFeedbackLabel.setOutputMarkupId(true);
		form.add(spsubnetmaskFeedbackLabel);

		TextField<String> remark = new TextField<String>("remark");
		remark.setLabel(new Model("Remark"));
		remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 64));
		/* remark.add(new StringValidator()); */
		final FeedbackLabel remarkFeedbackLabel = new FeedbackLabel("remarkfeedback", remark);
		remarkFeedbackLabel.setOutputMarkupId(true);
		form.add(remarkFeedbackLabel);

		Button btncancel = new Button("btncancel") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				PageParameters parms = new PageParameters();
				ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
				setResponsePage(av);
			}
		}.setDefaultFormProcessing(false);

		Button btnreset = new Button("btnreset") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				PageParameters parms = new PageParameters();
				EditInterfaceDetail av = new EditInterfaceDetail(parms,nidmodel, nldmodel);
				setResponsePage(av);
			}
		}.setDefaultFormProcessing(false);

		Button btnsubmit = new Button("btnsubmit") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if (editNetworkInterfaceDetail()) {
					PageParameters parms = new PageParameters();
					ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
					setResponsePage(av);
				}
			}
		}.setDefaultFormProcessing(true);

		add(new Label("spcircuitcode"));
		form.add(new Label("projecttypedescription"));
		form.add(new Label("locationname"));
		/*form.add(equipment);*/
		form.add(ntinterface);
		form.add(ipaddress);
		form.add(subnetmask);
		form.add(spntinterface);
		form.add(spipaddress);
		form.add(spsubnetmask);
		form.add(vendor);
		form.add(remark);
		form.add(feedback);
		form.add(btncancel);
		form.add(btnreset);
		form.add(btnsubmit);
		add(form);

	}

	private boolean editNetworkInterfaceDetail() {
		String query = "{call sp_circuit_edit_network_interface_details(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setString(3, spcircuitid);
			if(vendorlist.getObject().size()==1){
			stmt.setInt(4, vendorlist.getObject().get(0).getVendorid());
			stmt.setString(5, vendorlist.getObject().get(0).getVendorname());
			}else
			{
			stmt.setInt(4, vendor.getVendorid());
			stmt.setString(5, vendor.getVendorname());
			}
			stmt.setString(6, ntinterface);
			stmt.setString(7, ipaddress);
			stmt.setString(8, subnetmask);
			stmt.setString(9, spntinterface);
			stmt.setString(10, spipaddress);
			stmt.setString(11,spsubnetmask);
			stmt.setString(12, remark);
			stmt.setInt(13, nid.getInterfaceid());
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			rs = stmt.executeQuery();
			while (rs.next()) {
				log.info("Network Interface Detail Edited Successfully With ID :" + rs.getInt(1));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in editNetworkInterfaceDetail() method {" + e.getMessage() + "}");
			e.printStackTrace();
			return false;
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
            }
            catch (SQLException e2) {
               log.error("SQL Exception in editNetworkInterfaceDetail() method {" + e2.getMessage() + "}");
                e2.printStackTrace();
            }
        }
		return true;
	}
	public List<NetworkVendorDetail> getVendors() {
        final List<NetworkVendorDetail> vnlist = new ArrayList<NetworkVendorDetail>();
        final String query = "{call sp_circuit_get_vendors(?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            con = new DataBaseConnection().getConnection();
            stmt = con.prepareCall(query);
            stmt.setString(1, ((PortSession)this.getSession()).getEmployeeid());
            stmt.setInt(2, ((PortSession)this.getSession()).getSessionid());
            stmt.setString(3, this.spcircuitid);
            rs = stmt.executeQuery();
            log.info((Object)("Executing Stored Procedure { " + stmt.toString() + " }"));
            while (rs.next()) {
            	NetworkVendorDetail nvd = new NetworkVendorDetail(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),rs.getString(9),rs.getInt(10),rs.getString(11),rs.getInt(12),rs.getString(13),rs.getInt(14));
/*            	System.out.println(nid.getVendorid()+nid.getVendorname()+" == "+nvd.getVendorid()+nvd.getVendorname());*/
            	if(nid.getVendorid() == nvd.getVendorid()){
            	   vendor = nvd;  
               }
            	vnlist.add(nvd);
            }
        }
        catch (SQLException e) {
           log.error((Object)("SQL Exception in getVendors() method {" + e.getMessage() + "}"));
            e.printStackTrace();
            return vnlist;
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
                log.error("SQL Exception in getVendors() method {" + e2.getMessage() + "}");
                e2.printStackTrace();
            }
        }
        return vnlist;
    }

}
