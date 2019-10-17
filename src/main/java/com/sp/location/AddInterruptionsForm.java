package com.sp.location;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
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
import com.sp.report.CustomReportForm.NetworkLocationList;
import com.sp.resource.CustomRadioChoice;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;
import com.sp.validators.StringValidator;

public class AddInterruptionsForm extends Panel {
	private static final Logger log = Logger.getLogger(AddInterruptionsForm.class);
	private Circle circle;
	private int circleid = 0;
	private int circleid1 = 0;
	private Division division;
	private int divisionid = 0;
	private int divisionid1 = 0;
	private ERO ero ;
	private int eroid = 0 ;
	private int eroid1 = 0 ;
	private SubDivision subdivision;
	private int subdivisionid = 0;
	private int subdivisionid1 = 0;
	private Section section;
	private int sectionid = 0;
	private int sectionid1 = 0;
	private CircuitId circuitid;
	private String locationname;
	private String locationnamefeedback;
	private String vendor ;
	private String vendorfeedback ;
	private String circuitdesc ;
	private String linkdowndate ;
	private String linkdowndatefeed ;
	private String linkupdate ;
	private String linkupdatefeed ;
	private String calllog;
	private String calllogfeedback ;
	private String ciruitiddesc ;
	private final Date date2 = new Date();
	private final Date date3 = new Date();
	/*private String tothrs ;
	private String tothrsfeedback ;*/
	private static String PATTERN = "yyyy-MM-dd";
	
	private CircuitList ntlclist = new CircuitList();
	
	IModel<? extends List<Circle>> circlelist = new LoadableDetachableModel<List<Circle>>() {
		@Override
		protected List<Circle> load() {
			// TODO Auto-generated method stub
			return loadCircles();
		}
	};
	IModel<? extends List<Division>> divisionlist = new LoadableDetachableModel<List<Division>>() {
		@Override
		protected List<Division> load() {
			// TODO Auto-generated method stub
			return loadDivisions(circleid);
		}
	};
	IModel<? extends List<ERO>> erolist = new LoadableDetachableModel<List<ERO>>() {
		@Override
		protected List<ERO> load() {
			// TODO Auto-generated method stub
			return loadEro(circleid, divisionid);
		}
	};
	IModel<? extends List<SubDivision>> subdivisionlist = new LoadableDetachableModel<List<SubDivision>>() {
		@Override
		protected List<SubDivision> load() {
			// TODO Auto-generated method stub
			return loadSubDivisions(circleid, divisionid,eroid);
		}
	};
	IModel<? extends List<Section>> sectionlist = new LoadableDetachableModel<List<Section>>() {
		@Override
		protected List<Section> load() {
			// TODO Auto-generated method stub
			return loadSections(circleid, divisionid, subdivisionid);
		}
	};
	
	IModel<? extends List<CircuitId>> circuitidlist = new LoadableDetachableModel<List<CircuitId>>() {

		@Override
		protected List<CircuitId> load() {
			// TODO Auto-generated method stub
			return loadCircuitIds();
		}
	};
	TextField<String> locationnametf;
	TextField<String> vendortf;
	public AddInterruptionsForm(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		setDefaultModel(new CompoundPropertyModel<AddInterruptionsForm>(this));
		StatelessForm<Form> form = new StatelessForm<Form>("addinterruptionform");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		int[] filteredErrorLevels = new int[] { FeedbackMessage.ERROR };
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));
		
		
		
		
		
		
		
		final DropDownChoice<CircuitId> circuitidselect = new DropDownChoice<CircuitId>("circuitid", circuitidlist,
				new ChoiceRenderer("circuitid")) {
			@Override
			protected CharSequence getDefaultChoice(String selectedValue) {
				// TODO Auto-generated method stub
				return "";
			}

			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(CircuitId newSelection) {
				// TODO Auto-generated method stub
				
				//System.out.println(newSelection.getLocationname());
				
				
				
				ciruitiddesc = new String(newSelection.getCircuitid());
				locationname = newSelection.getLocationname();
				vendor = newSelection.getVendor() ;
				circleid1 = newSelection.getCircleid() ;
				divisionid1 = newSelection.getDivisionid();
				eroid1 = newSelection.getEroid();
				subdivisionid1 = newSelection.getSubdivisionid();
				sectionid1 = newSelection.getSectionid() ;
				
				//CircuitId();
				
				//System.out.println(" circleid 111 "+circleid);
				
				
				
				TextField<String> templocationname = new TextField<String>("locationname");
				locationnametf.replaceWith(templocationname);
				locationnametf = templocationname;
				
				TextField<String> tempvendor = new TextField<String>("vendor");
				vendortf.replaceWith(tempvendor);
				vendortf = tempvendor;
				
				/*circuitdesc = circuitid.getCircuitiddesc();
				System.out.println("circuitdesc"+circuitdesc);
				
				ntlclist.setList(getDetails(circuitdesc));
				
				locationname = circuitid.getLocationname() ;
				
				System.out.println(" locationname "+locationname);*/
				
				
			}
		};
		
		//System.out.println(" locationname 222 "+locationname);
		
		
		circuitidselect.setNullValid(false);
		circuitidselect.setLabel(new Model("Circuit Id"));
		/* sectiondd.setOutputMarkupId(true); */
		form.add(circuitidselect);
						
		final DropDownChoice<Section> sectiondd = new DropDownChoice<Section>("section", sectionlist,
				new ChoiceRenderer("sectiondesc", "sectionid")) {
			@Override
			protected CharSequence getDefaultChoice(String selectedValue) {
				// TODO Auto-generated method stub
				return "";
			}

			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(Section newSelection) {
				// TODO Auto-generated method stub
				sectionid = section.getSectionid();
				ntlclist.setList(loadCircuitIds());
			}
			
			
		};
		
		
		
		/*
		 * sectiondd.add(new AjaxFormComponentUpdatingBehavior("onChange") {
		 * 
		 * @Override protected void onUpdate(AjaxRequestTarget target) { // TODO
		 * Auto-generated method stub sectionid = section.getSectionid();
		 * ntlclist.setList(getAllNetworkLocations()); target.add(table); }
		 * 
		 * });
		 */
		sectiondd.setNullValid(false);
		sectiondd.setLabel(new Model("Section"));
		/* sectiondd.setOutputMarkupId(true); */
		form.add(sectiondd);

		final DropDownChoice<SubDivision> subdivisiondd = new DropDownChoice<SubDivision>("subdivision",
				subdivisionlist, new ChoiceRenderer("subdivisiondesc", "subdivisionid")) {
			@Override
			protected CharSequence getDefaultChoice(String selectedValue) {
				// TODO Auto-generated method stub
				return "";
			}

			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(SubDivision newSelection) {
				// TODO Auto-generated method stub
				subdivisionid = subdivision.getSubdivisionid();
				sectionid = 0;
				ntlclist.setList(loadCircuitIds());
				section = null;
			}
		};
		/*
		 * subdivisiondd.add(new AjaxFormComponentUpdatingBehavior("onChange") {
		 * 
		 * @Override protected void onUpdate(AjaxRequestTarget target) { // TODO
		 * Auto-generated method stub subdivisionid =
		 * subdivision.getSubdivisionid(); sectionid = 0;
		 * ntlclist.setList(getAllNetworkLocations()); section = null;
		 * target.add(table); target.add(sectiondd); }
		 * 
		 * });
		 */
		subdivisiondd.setNullValid(false);
		subdivisiondd.setLabel(new Model("Sub-Division"));
		/* subdivisiondd.setOutputMarkupId(true); */
		
		
		 final DropDownChoice<ERO> erodd = new DropDownChoice<ERO>("ero", erolist, new ChoiceRenderer("erodes", "eroid"))
		    {
				@Override
				protected CharSequence getDefaultChoice(String selectedValue) {
					// TODO Auto-generated method stub
					return "";
				}
				@Override
				protected boolean wantOnSelectionChangedNotifications() {
					// TODO Auto-generated method stub
					return true;
				}
				@Override
				protected void onSelectionChanged(ERO newSelection) {
					// TODO Auto-generated method stub
					eroid = ero.getEroid();
					subdivisionid = 0;				
					sectionid = 0;
					//ntlclist.setList(getAllNetworkLocations());
					section = null;
					subdivision = null;
				}
				};
		    /*divisiondd.add(new AjaxFormComponentUpdatingBehavior("onChange")
			{
				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					// TODO Auto-generated method stub
					divisionid = division.getDivisionid();
					subdivisionid = 0;
					sectionid = 0;
					ntlclist.setList(getAllNetworkLocations());
					section = null;
					subdivision = null;
					target.add(table);
					target.add(sectiondd);
					target.add(subdivisiondd);
				}
		
			});*/
				erodd.setNullValid(false);
				erodd.setLabel(new Model("ERO"));
		    /*divisiondd.setOutputMarkupId(true);	 */   
		    form.add(erodd);

		final DropDownChoice<Division> divisiondd = new DropDownChoice<Division>("division", divisionlist,
				new ChoiceRenderer("divisiondesc", "divisionid")) {
			@Override
			protected CharSequence getDefaultChoice(String selectedValue) {
				// TODO Auto-generated method stub
				return "";
			}

			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(Division newSelection) {
				// TODO Auto-generated method stub
				divisionid = division.getDivisionid();
				eroid = 0 ;
				subdivisionid = 0;
				sectionid = 0;
				ntlclist.setList(loadCircuitIds());
				section = null;
				subdivision = null;
				ero = null ;
			}
		};
		/*
		 * divisiondd.add(new AjaxFormComponentUpdatingBehavior("onChange") {
		 * 
		 * @Override protected void onUpdate(AjaxRequestTarget target) { // TODO
		 * Auto-generated method stub divisionid = division.getDivisionid();
		 * subdivisionid = 0; sectionid = 0;
		 * ntlclist.setList(getAllNetworkLocations()); section = null;
		 * subdivision = null; target.add(table); target.add(sectiondd);
		 * target.add(subdivisiondd); }
		 * 
		 * });
		 */
		divisiondd.setNullValid(false);
		divisiondd.setLabel(new Model("Division"));
		/* divisiondd.setOutputMarkupId(true); */
		form.add(divisiondd);

		DropDownChoice<Circle> circledd = new DropDownChoice<Circle>("circle", circlelist,
				new ChoiceRenderer("circledes", "circleid")) {
			@Override
			protected CharSequence getDefaultChoice(String selectedValue) {
				// TODO Auto-generated method stub
				return "";
			}

			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(Circle newSelection) {
				// TODO Auto-generated method stub
				circleid = circle.getCircleid();
				divisionid = 0;
				eroid = 0 ;
				subdivisionid = 0;
				sectionid = 0;
				ntlclist.setList(loadCircuitIds());
				section = null;
				subdivision = null;
				ero = null ;
				division = null;
			}
		};
		/*
		 * circledd.add(new AjaxFormComponentUpdatingBehavior("onChange") {
		 * 
		 * @Override protected void onUpdate(AjaxRequestTarget target) { // TODO
		 * Auto-generated method stub circleid = circle.getCircleid();
		 * divisionid = 0; subdivisionid = 0; sectionid = 0;
		 * ntlclist.setList(getAllNetworkLocations()); section = null;
		 * subdivision = null; division = null; target.add(table);
		 * target.add(sectiondd); target.add(subdivisiondd);
		 * target.add(divisiondd); }
		 * 
		 * });
		 */
		circledd.setNullValid(false);
		circledd.setRequired(true).setLabel(new Model("Circle"));
		form.add(circledd);
		form.add(subdivisiondd);
		
		 locationnametf = new TextField<String>("locationname");
		 locationnametf.setRequired(true).setLabel(new Model("Serial No."));
		 locationnametf.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		final FeedbackLabel locationnameFeedbackLabel = new FeedbackLabel("locationnamefeedback", locationnametf);
		locationnameFeedbackLabel.setOutputMarkupId(true);
		form.add(locationnameFeedbackLabel);
		
		vendortf = new TextField<String>("vendor");
		vendortf.setRequired(true).setLabel(new Model("Serial No."));
		vendortf.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		final FeedbackLabel vendorFeedbackLabel = new FeedbackLabel("vendorfeedback", vendortf);
		vendorFeedbackLabel.setOutputMarkupId(true);
		form.add(vendorFeedbackLabel);
		
		CustromDatePicker datePicker1 = new CustromDatePicker();
		datePicker1.setShowOnFieldClick(true);
		datePicker1.setAutoHide(false);

		DateTimeField linkdowndate1 = new DateTimeField("linkdowndate", new PropertyModel<Date>(this, "date2")){
			
			protected DateTextField newDateTextField(String id, PropertyModel<Date> dateFieldModel)
		    {
		        return new DateTextField(id, dateFieldModel, new PatternDateConverter("dd MMM, yyyy", true));
		    }
			
		};	
		linkdowndate1.setRequired(true).setLabel(new Model("Link Down Date"));
		final FeedbackLabel linkdowndatefeed1 = new FeedbackLabel("linkdowndatefeed", linkdowndate1);
		linkdowndate1.setOutputMarkupId(true);
		//form.add(new DateTimeField("linkdowndate", new PropertyModel<Date>(this, "linkdowndate")));
		form.add(linkdowndatefeed1);
		form.add(linkdowndate1);
		
		CustromDatePicker datePicker2 = new CustromDatePicker();
		datePicker2.setShowOnFieldClick(true);
		datePicker2.setAutoHide(false);

		DateTimeField linkupdate1 = new DateTimeField("linkupdate", new PropertyModel<Date>(this, "date3")){
			
			protected DateTextField newDateTextField(String id, PropertyModel<Date> dateFieldModel)
		    {
		        return new DateTextField(id, dateFieldModel, new PatternDateConverter("dd MMM, yyyy", true));
		    }
			
		};	
		linkupdate1.setRequired(true).setLabel(new Model("Link Down Date"));
		final FeedbackLabel linkupdatefeed1 = new FeedbackLabel("linkupdatefeed", linkupdate1);
		linkupdate1.setOutputMarkupId(true);
		form.add(linkupdatefeed1);
		form.add(linkupdate1);
		
		
		TextField<String> calllog = new TextField<String>("calllog");
		calllog.setRequired(true).setLabel(new Model("Call Log Number"));
		final FeedbackLabel calllogFeedbackLabel = new FeedbackLabel("calllogfeedback", calllog);
		calllogFeedbackLabel.setOutputMarkupId(true);
		form.add(calllogFeedbackLabel);
		
		/*TextField<String> tothrs = new TextField<String>("tothrs");
		tothrs.setRequired(true).setLabel(new Model("Total Hours"));
		tothrs.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1, 32));
		tothrs.add(new StringValidator()); 
		final FeedbackLabel tothrsFeedbackLabel = new FeedbackLabel("tothrsfeedback", tothrs);
		tothrsFeedbackLabel.setOutputMarkupId(true);
		form.add(tothrsFeedbackLabel);*/
		
		
	
		
		
		/*DropDownChoice<EquipmentType> equipmenttype = new DropDownChoice<EquipmentType>("equipmenttype", equipmentlist,
				new ChoiceRenderer<EquipmentType>("equipmenttypedesc"));
		equipmenttype.setNullValid(false);
		equipmenttype.setRequired(true).setLabel(new Model("Equipment Type"));
		final FeedbackLabel equipmenttypeFeedbackLabel = new FeedbackLabel("equipmenttypefeedback", equipmenttype);
		equipmenttypeFeedbackLabel.setOutputMarkupId(true);
		form.add(equipmenttypeFeedbackLabel);*/
		

		/* spcircuitid = nldmodel.getObject().getSpcircuitid();
		spcircuitcode = nldmodel.getObject().getSpciruitcode();
		projecttypedescription = nldmodel.getObject().getProjecttypedescription();
		
		locationname = nldmodel.getObject().getLocationname();

		DropDownChoice<EquipmentType> equipmenttype = new DropDownChoice<EquipmentType>("equipmenttype", equipmentlist,
				new ChoiceRenderer<EquipmentType>("equipmenttypedesc"));
		equipmenttype.setNullValid(false);
		equipmenttype.setRequired(true).setLabel(new Model("Equipment Type"));
		final FeedbackLabel equipmenttypeFeedbackLabel = new FeedbackLabel("equipmenttypefeedback", equipmenttype);
		equipmenttypeFeedbackLabel.setOutputMarkupId(true);
		form.add(equipmenttypeFeedbackLabel);

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

		final CustomRadioChoice<String> amc = new CustomRadioChoice("amc", TYPES);
		amc.setRequired(true).setLabel(new Model("AMC/Warranty"));
		final FeedbackLabel amcFeedbackLabel = new FeedbackLabel("amcfeedback", amc);
		amcFeedbackLabel.setOutputMarkupId(true);
		form.add(amcFeedbackLabel);

		final CustomRadioChoice<String> replaceradio = new CustomRadioChoice("replace", R_TYPES) {
			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected void onSelectionChanged(Object newSelection) {
				// TODO Auto-generated method stub
				if (replace.equals("Replace")) {
					replacedivflag = true;
				} else {
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

		WebMarkupContainer repdiv = new WebMarkupContainer("replacediv") {
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

		CustromDatePicker datePicker = new CustromDatePicker();
		datePicker.setShowOnFieldClick(true);
		datePicker.setAutoHide(false);

		DateTextField instaldate = new DateTextField("installationdate",
				new PropertyModel<Date>(this, "installationdate"), new PatternDateConverter("dd MMM, yyyy", true));

		
		 * DateTextField instaldate = new
		 * DateTextField("installationdate","dd-mm-yyy") { protected String
		 * getInputType() { return "date"; } };
		 
		instaldate.setRequired(true).setLabel(new Model("Installation Date"));
		final FeedbackLabel insfeedback = new FeedbackLabel("installfeedback", instaldate);
		instaldate.setOutputMarkupId(true);
		instaldate.add(datePicker);
		repdiv.add(insfeedback);
		repdiv.add(instaldate);

		CustromDatePicker datePicker1 = new CustromDatePicker();
		datePicker1.setShowOnFieldClick(true);
		datePicker1.setAutoHide(false);

		DateTextField instaldate1 = new DateTextField("installdate", new PropertyModel<Date>(this, "installdate"),
				new PatternDateConverter("dd MMM, yyyy", true));

		
		 * DateTextField instaldate = new
		 * DateTextField("installationdate","dd-mm-yyy") { protected String
		 * getInputType() { return "date"; } };
		 
		instaldate1.setRequired(true).setLabel(new Model("Installation Date"));
		final FeedbackLabel insfeedback1 = new FeedbackLabel("installfeed", instaldate1);
		instaldate1.setOutputMarkupId(true);
		instaldate1.add(datePicker1);
		form.add(insfeedback1);
		form.add(instaldate1);

		TextArea<String> rremark = new TextArea<String>("rremark");
		rremark.setLabel(new Model("Remark"));
		// remark.add(org.apache.wicket.validation.validator.StringValidator.lengthBetween(1,
		// 64));
		 remark.add(new StringValidator()); 
		final FeedbackLabel rremarkFeedbackLabel = new FeedbackLabel("rremarkfeedback", rremark);
		rremarkFeedbackLabel.setOutputMarkupId(true);
		repdiv.add(rremarkFeedbackLabel);
		repdiv.add(rremark);

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
				AddEquipmentDetail av = new AddEquipmentDetail(parms, nldmodel);
				setResponsePage(av);
			}
		}.setDefaultFormProcessing(false);

		Button btnsubmit = new Button("btnsubmit") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if (addNetworkEquipmentDetail()) {
					PageParameters parms = new PageParameters();
					ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
					setResponsePage(av);
				}
			}
		}.setDefaultFormProcessing(true);
		add(new Label("spcircuitcode"));
		form.add(new Label("projecttypedescription"));
		form.add(new Label("locationname"));
		
		form.add(make);
		form.add(model);
		form.add(serialno);
		form.add(amc);
		form.add(repdiv);
		form.add(remark);
		
		form.add(btncancel);
		form.add(btnreset);
		form.add(btnsubmit);
		form.add(replaceradio);
		;*/
		Button btncancel = new Button("btncancel") {
			@Override
			public void onSubmit() {
				setResponsePage(ApspdclMaster.class);
			}
		}.setDefaultFormProcessing(false);

		Button btnreset = new Button("btnreset") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				/*PageParameters parms = new PageParameters();
				AddEquipmentDetail av = new AddEquipmentDetail(parms, nldmodel);
				setResponsePage(av);*/
			}
		}.setDefaultFormProcessing(false);
		Button btnsubmit = new Button("btnsubmit") {
			@Override
			public void onSubmit() {
				// TODO Auto-generated method stub
				if (addInterrutionDetails()) {
					PageParameters parms = new PageParameters();
					
					ViewInterruptionDetail av = new ViewInterruptionDetail(parms);
					
					//ViewNetworkLocationDetail av = new ViewNetworkLocationDetail(parms, nldmodel);
					setResponsePage(av);
				}
			}
		}.setDefaultFormProcessing(true);
		
		form.add(circuitidselect);
		form.add(feedback);
		form.add(locationnametf);
		form.add(vendortf);
		form.add(calllog);
		form.add(btncancel);
		form.add(btnreset);
		form.add(btnsubmit);
		/*form.add(tothrs);*/
		add(form);
	}

	private boolean addInterrutionDetails() {
		
        DateFormat dateFormatMDY = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String linkdown = dateFormatMDY.format(date2);
        String linkup = dateFormatMDY.format(date3);
        
       /* long diff = date3.getTime() - date2.getTime();
        
        System.out.println(" diff "+diff);
  */
        
        
		final String query = "{call sp_circuit_add_interrutions_details(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		//System.out.println(" circuitid "+ciruitiddesc);
		//System.out.println(" Link Down Date "+linkdown+" = "+date2);
		//System.out.println(" Link Up Date "+linkup+date3);
		//System.out.println(" locationnametf "+locationname);
		//System.out.println(" vendortf "+vendor);
		//System.out.println(" calllog "+calllog);
		
	
        
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setString(3, ciruitiddesc);
			stmt.setString(4, locationname);
			stmt.setString(5, vendor);
			stmt.setString(6,linkdown);
			stmt.setString(7,linkup);
			stmt.setString(8, calllog);
			stmt.setInt(9, circleid1);
			stmt.setInt(10, divisionid1);
			stmt.setInt(11, subdivisionid1);
			stmt.setInt(12, sectionid1);
			stmt.setInt(13, eroid1);
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			rs = stmt.executeQuery();
			while (rs.next()) {
				log.info("Add Interruptions Detail Added Successfully With Circuit ID is :" + rs.getString(1));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in sp_circuit_add_interrutions_details() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in sp_circuit_add_interrutions_details() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return true;
	}
	
	public class CircuitList implements Serializable {
		private List<CircuitId> list;

		public CircuitList() {
			// TODO Auto-generated constructor stub
			list = new ArrayList<CircuitId>();
		}

		public void addItem(CircuitId t) {
			list.add(t);
		}

		@SuppressWarnings("rawtypes")
		public List getList() {
			return list;
		}

		public void setList(List<CircuitId> l) {
			list = l;
		}

		@SuppressWarnings("rawtypes")
		public List selectList(int first, int count) {
			return list.subList(first, first + count);
		}
	}

	private List<CircuitId> loadCircuitIds() {
		List<CircuitId> list = new ArrayList<CircuitId>();
		String query = "{call sp_get_interruption_circuitid(?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) this.getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) this.getSession()).getSessionid());
			stmt.setInt(3, this.circleid);
			stmt.setInt(4, this.divisionid);
			stmt.setInt(5, this.subdivisionid);
			stmt.setInt(6, this.sectionid);
			stmt.setInt(7, this.eroid);
			
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			list.add(new CircuitId("[Select One]","",""));
			while (rs.next()) {
				list.add(new CircuitId(rs.getString(1),rs.getString(2),rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadCircuitIds() method {" + e.getMessage() + "}");
			e.printStackTrace();
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
				log.error("SQL Exception in loadProjectTypes() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}
	

	private String getFormatDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
		return simpleDateFormat.format(date);

	}
	
	private List<Circle> loadCircles() {
		final List<Circle> list = new ArrayList<Circle>();
		list.add(new Circle(0, "All", "ALL"));
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
			log.error((Object) ("SQL Exception in loadCircles() method {" + e.getMessage() + "}"));
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
				log.error("SQL Exception in sp_circuit_add_interrutions_details() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}

	private List<Division> loadDivisions(final int circleid) {
		final List<Division> list = new ArrayList<Division>();
		list.add(new Division(0, "All", "ALL"));
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
			log.info((Object) ("Executing Stored Procedure { " + stmt.toString() + " }"));
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
				log.error("SQL Exception in sp_circuit_add_interrutions_details() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}

	private List<SubDivision> loadSubDivisions(final int circleid, final int divisionid,final int eroid) {
		final List<SubDivision> list = new ArrayList<SubDivision>();
		list.add(new SubDivision(0, "All", "ALL"));
		final String query = "{call sp_division_get_sub_divisions(?,?,?,?,?)}";
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
			stmt.setInt(5, eroid);
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
				log.error("SQL Exception in sp_circuit_add_interrutions_details() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}

	private List<Section> loadSections(final int circleid, final int divisionid, final int subdivision) {
		final List<Section> list = new ArrayList<Section>();
		list.add(new Section(0, "All", "ALL"));
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
				log.error("SQL Exception in sp_circuit_add_interrutions_details() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	private List<ERO> loadEro(final int circleid, final int divisionid) {
		final List<ERO> list = new ArrayList<ERO>();
		list.add(new ERO(0, "All", "ALL"));
		final String query = "{call sp_division_get_eros(?,?,?,?)}";
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
				list.add(new ERO(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadEro() method {" + e.getMessage() + "}");
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

}
