package com.sp.location;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sp.SPNetworkLocation.PortSession;
import com.sp.location.ViewNetworkLocationDetailForm.NetworkVendorDataProvider;
import com.sp.location.ViewNetworkLocationDetailForm.NetworkVendorList;
import com.sp.resource.CustomRadioChoice;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;
import com.sp.validators.NumberValidator;
import com.sp.validators.StringValidator;

public class ViewUPSDetailForm extends Panel {
	private static final Logger log = Logger.getLogger(ViewUPSDetailForm.class);
	private String spcircuitid;
	private String spcircuitcode;
	private String projecttypedescription;
	private NetworkUPSDetail nupsd;
	private String make;
	private String model;
	private String serialno;
	private String amc;
	private String remark;
	private String addmake;
	private String addmakefeedback;
	private String addmodel;
	private String addmodelfeedback;
	private String addserialno;
	private String addserialnofeedback;
	final int DEF_NO_OF_ROWS = 9999;
	private boolean mymodalflag;
	private String replacelbl;
	private String noofbatteries;
	private String addbatteriesfeedback;
	private String addbatteries;
	private Date installationdate;
	private String installfeedback;
	private String rremark;
	private String remarkfeedback;
	private String locationname;
	private static String PATTERN = "yyyy-MM-dd";
	private NetworkUPSReplaceList ntvnlist = new NetworkUPSReplaceList();

	public ViewUPSDetailForm(String id, final IModel<NetworkUPSDetail> nedmodel,
			final IModel<NetworkLocationDetail> nldmodel) {
		super(id);
		// TODO Auto-generated constructor stub
		setDefaultModel(new CompoundPropertyModel<ViewUPSDetailForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("addreplacedetails");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[] { FeedbackMessage.ERROR };
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));
		nupsd = nedmodel.getObject();

		ntvnlist.setList(getReplaceHistory());
		NetworkUPSProvider replaceprovider = new NetworkUPSProvider();
		spcircuitid = nldmodel.getObject().getSpcircuitid();
		spcircuitcode = nldmodel.getObject().getSpciruitcode();
		projecttypedescription = nldmodel.getObject().getProjecttypedescription();
		locationname = nldmodel.getObject().getLocationname();
		make = nupsd.getMake();
		model = nupsd.getModel();
		serialno = nupsd.getSerialnumber();
		amc = nupsd.getAmc();
		remark = nupsd.getRemark();
		replacelbl = nupsd.getIsreplace()==1?"Replace":"Stand By";
		noofbatteries = String.valueOf(nupsd.getNoofbatteries());
		WebMarkupContainer replacediv = new WebMarkupContainer("replacediv");
		replacediv.setVisible(nupsd.getIsreplace()==1?true:false);
		form.add(replacediv);
		
		List<IColumn> columns = new ArrayList<IColumn>();
		columns.add(new AbstractColumn(new Model("Sr. No.")) {
			public void populateItem(Item cell, String compId, IModel rowModel) {
				int pageNumber, noRows;
				DataTable tbl = (DataTable) get("datatable");
				if (tbl != null) {
					pageNumber = (int) tbl.getCurrentPage();
					noRows = (int) tbl.getItemsPerPage();
				} else {
					pageNumber = 0;
					noRows = 0;
				}
				int rowNumber = (pageNumber * noRows) + ((Item) cell.getParent().getParent()).getIndex() + 1;
				cell.add(new Label(compId, rowNumber));
			}
		});
		columns.add(new PropertyColumn(new Model("Make"), "rmake"));
		columns.add(new PropertyColumn(new Model("Model"), "rmodel"));
		columns.add(new PropertyColumn(new Model("Serial No."), "rserialnumber"));
		columns.add(new PropertyColumn(new Model("No. of Batteries"), "noofbatteries"));
		columns.add(new PropertyColumn(new Model("Replacement Date"), "replacedate"));
		columns.add(new PropertyColumn(new Model("Remark"), "remark"));
		columns.add(new PropertyColumn(new Model("Status"), "status"));
		final DataTable table = new DataTable("datatable", columns, replaceprovider, DEF_NO_OF_ROWS);
		table.setOutputMarkupId(true);
		table.addTopToolbar(new HeadersToolbar(table, replaceprovider));
		replacediv.add(table);

		form.add(new Label("spcircuitcode"));
		form.add(new Label("projecttypedescription"));
		form.add(new Label("locationname"));
		form.add(new Label("make"));
		form.add(new Label("model"));
		form.add(new Label("serialno"));
		form.add(new Label("amc"));
		form.add(new Label("remark"));
		form.add(new Label("replacelbl"));
		form.add(new Label("noofbatteries"));

		WebMarkupContainer mymodal = new WebMarkupContainer("mymodal") {
			@Override
			public boolean isVisible() {
				// TODO Auto-generated method stub
				return mymodalflag;
			}
		};
		TextField<String> rmake = new TextField<String>("addmake");
		rmake.setRequired(true).setLabel(new Model("Make"));
		rmake.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 128));
		rmake.add(new StringValidator());
		final FeedbackLabel rmakeFeedbackLabel = new FeedbackLabel("addmakefeedback", rmake);
		rmakeFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(rmakeFeedbackLabel);

		TextField<String> model = new TextField<String>("addmodel");
		model.setRequired(true).setLabel(new Model("Model"));
		model.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		/* model.add(new StringValidator()); */
		final FeedbackLabel modelFeedbackLabel = new FeedbackLabel("addmodelfeedback", model);
		modelFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(modelFeedbackLabel);

		TextField<String> serialno = new TextField<String>("addserialno");
		serialno.setRequired(true).setLabel(new Model("Serial No."));
		serialno.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		/* model.add(new StringValidator()); */
		final FeedbackLabel serialnoFeedbackLabel = new FeedbackLabel("addserialnofeedback", serialno);
		serialnoFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(serialnoFeedbackLabel);
		
		TextField<String> batteries = new TextField<String>("addbatteries");
		batteries.setRequired(true).setLabel(new Model("No.Of Batteries"));
		batteries.add(new NumberValidator());
		final FeedbackLabel batteriesFeedbackLabel = new FeedbackLabel("addbatteriesfeedback", batteries);
		batteriesFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(batteriesFeedbackLabel);

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
        mymodal.add(insfeedback);
        mymodal.add(instaldate);
        
        TextArea<String> remark = new TextArea<String>("rremark");
		remark.setLabel(new Model("Remark"));
		//remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 64));
		/*remark.add(new StringValidator());*/
		final FeedbackLabel remarkFeedbackLabel = new FeedbackLabel("remarkfeedback", remark);
		remarkFeedbackLabel.setOutputMarkupId(true);
		mymodal.add(remarkFeedbackLabel);
		mymodal.add(remark);
		
		Button btnback = new Button("back") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				PageParameters params = new PageParameters();
				ViewUPSDetail vad = new ViewUPSDetail(params, nedmodel, nldmodel);
				setResponsePage(vad);

			}
		}.setDefaultFormProcessing(false);

		Button btn = new Button("submit") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if (networkUPSAddReplaceDetails()) {
					PageParameters params = new PageParameters();
					ViewUPSDetail vad = new ViewUPSDetail(params, nedmodel, nldmodel);
					setResponsePage(vad);
				}
			}
		};
		mymodal.add(btn);
		mymodal.add(btnback);
		mymodal.add(rmake);
		mymodal.add(model);
		mymodal.add(serialno);
		mymodal.add(batteries);
		Button replace = (Button) new Button("replace") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				mymodalflag = true;
			}
		}.setVisible(nupsd.getIsreplace()==1?true:false);;
		Button pback = new Button("pback") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				PageParameters parms = new PageParameters();
				ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
				setResponsePage(av);
			}
		};
		form.add(pback);
		form.add(mymodal);
		form.add(replace);
		form.add(feedback);
		add(form);

	}

	public class NetworkUPSProvider extends SortableDataProvider implements Serializable {

		public NetworkUPSProvider() {
			// TODO Auto-generated constructor stub
			setSort("spcircuidid", SortOrder.ASCENDING);
		}

		@Override
		public Iterator iterator(long first, long count) {
			// TODO Auto-generated method stub
			Collections.sort(ntvnlist.getList(), new Comparator<UPSReplaceHistory>() {

				@Override
				public int compare(UPSReplaceHistory arg0, UPSReplaceHistory arg1) {
					// TODO Auto-generated method stub
					int dir = getSort().isAscending() ? 1 : -1;
					return dir * (arg0.getRmake().compareTo(arg1.getRmake()));
				}
			});
			return ntvnlist.selectList((int) first, (int) count).iterator();
		}

		@Override
		public IModel model(Object arg0) {
			// TODO Auto-generated method stub
			UPSReplaceHistory nld = (UPSReplaceHistory) arg0;
			return new Model((Serializable) nld);
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return ntvnlist.getList().size();
		}

	}

	public class NetworkUPSReplaceList implements Serializable {
		private List<UPSReplaceHistory> list;

		public NetworkUPSReplaceList() {
			// TODO Auto-generated constructor stub
			list = new ArrayList<UPSReplaceHistory>();
		}

		public void addItem(UPSReplaceHistory t) {
			list.add(t);
		}

		@SuppressWarnings("rawtypes")
		public List getList() {
			return list;
		}

		public void setList(List<UPSReplaceHistory> l) {
			list = l;
		}

		@SuppressWarnings("rawtypes")
		public List selectList(int first, int count) {
			return list.subList(first, first + count);
		}
	}

	public List<UPSReplaceHistory> getReplaceHistory() {
		final List<UPSReplaceHistory> vnlist = new ArrayList<UPSReplaceHistory>();
		final String query = "{call sp_circuit_ups_get_all_replacements(?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, nupsd.getUpsid());
			rs = stmt.executeQuery();
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
			while (rs.next()) {
				vnlist.add(new UPSReplaceHistory(rs.getString(1), rs.getString(2), rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6),rs.getString(7)));
			}
		} catch (SQLException e) {
			log.error((Object) ("SQL Exception in getVendors() method {" + e.getMessage() + "}"));
			e.printStackTrace();
			return vnlist;
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
				log.error("SQL Exception in getVendors() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return vnlist;
	}

	private boolean networkUPSAddReplaceDetails() {
		final String query = "{call sp_circuit_network_ups_add_replace_details(?,?,?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, nupsd.getUpsid());
			stmt.setString(4, addmake);
			stmt.setString(5, addmodel);
			stmt.setString(6, addserialno);
			stmt.setInt(7, Integer.parseInt(addbatteries));
			stmt.setString(8,getFormatDate(installationdate));
			stmt.setString(9, rremark);

			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			rs = stmt.executeQuery();
			while (rs.next()) {
				log.info("Network Equipment Replace Detail Added Successfully With ID :" + rs.getInt(1));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in networkEquipmentAddReplaceDetails() method {" + e.getMessage() + "}");
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
			} catch (SQLException e2) {
				log.error("SQL Exception in networkEquipmentAddReplaceDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return true;
	}
	private String getFormatDate(Date date)
	{
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
	    return simpleDateFormat.format(date);
		
	}
}
