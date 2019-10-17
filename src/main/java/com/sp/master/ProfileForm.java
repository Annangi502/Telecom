package com.sp.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import com.sp.SPNetworkLocation.PortSession;
import com.sp.location.ProjectType;
import com.sp.report.LinkAbstractReportForm;
import com.sp.resource.AES;
import com.sp.resource.DataBaseConnection;
import com.sp.resource.ErrorLevelsFeedbackMessageFilter;
import com.sp.resource.FeedbackLabel;

public class ProfileForm extends Panel {
	private static final Logger log = Logger.getLogger(ProfileForm.class);
	private String mobile1;
	private String mobile2;
	private String email1;
	private String email2;
	private String oldpassword;
	private String newpassword;
	private String confirmpassword;
	private String errormessage;
	private int statusid;

	private String infofeedback;
	private String oldpasswordfeedback;
	private String newpasswordfeedback;
	private String confirmpasswordfeedback;
	private boolean flag;

	public ProfileForm(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		setDefaultModel(new CompoundPropertyModel<ProfileForm>(this));
		StatelessForm<ProfileForm> changepasswordform = new StatelessForm<ProfileForm>("changepassword");
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		changepasswordform.add(feedback);

		int[] filteredErrorLevels = new int[] { FeedbackMessage.ERROR };
		feedback.setFilter(new ErrorLevelsFeedbackMessageFilter(filteredErrorLevels));

		PasswordTextField oldpw = new PasswordTextField("oldpassword");
		oldpw.setRequired(true).setLabel(new Model("Old Password"));
		final FeedbackLabel oldpwFeedbackLabel = new FeedbackLabel("oldpasswordfeedback", oldpw);
		oldpwFeedbackLabel.setOutputMarkupId(true);
		changepasswordform.add(oldpwFeedbackLabel);

		PasswordTextField newpw = new PasswordTextField("newpassword");
		newpw.setRequired(true).setLabel(new Model("New Password"));
		final FeedbackLabel newpwFeedbackLabel = new FeedbackLabel("newpasswordfeedback", newpw);
		newpwFeedbackLabel.setOutputMarkupId(true);
		changepasswordform.add(newpwFeedbackLabel);

		PasswordTextField confirmpw = new PasswordTextField("confirmpassword");
		confirmpw.setRequired(true).setLabel(new Model("Confirm Password"));
		final FeedbackLabel confirmpwFeedbackLabel = new FeedbackLabel("confirmpasswordfeedback", confirmpw);
		confirmpwFeedbackLabel.setOutputMarkupId(true);
		changepasswordform.add(confirmpwFeedbackLabel);

		Label infofeedback1 = new Label("infofeedback");
		add(new Label("wname", ((PortSession) getSession()).getEmployeename()));
		add(new Label("lastlogin", ((PortSession) getSession()).getLastlogintime()));
		add(new Label("designation", ((PortSession) getSession()).getDesignation()));
		add(new Label("wing", ((PortSession) getSession()).getWing()));
		loadEmployeeContactDetails();
		add(new Label("mobile1"));
		add(new Label("mobile2"));
		add(new Label("email1"));
		add(new Label("email2"));

		WebMarkupContainer wmc = new WebMarkupContainer("successmodel") {
			@Override
			public boolean isVisible() {
				// TODO Auto-generated method stub
				return flag;
			}
		};

		Button changepw = new Button("btnchangepassword") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				if (oldpassword.equals(newpassword)) {
					infofeedback = "* Old and New passwords are too similar";
				} else if (!newpassword.equals(confirmpassword)) {
					infofeedback = "* New and Confirm passwords do not match";
				} else if (changePassword()) {
					flag = true;
				} else {
					infofeedback = "* " + errormessage;
				}
			}

		};

		changepasswordform.add(oldpw);
		changepasswordform.add(newpw);
		changepasswordform.add(confirmpw);
		changepasswordform.add(infofeedback1);
		changepasswordform.add(changepw);
		add(wmc);
		add(changepasswordform);
	}

	private boolean loadEmployeeContactDetails() {
		String query = "{call sp_employee_get_contact_details(?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				mobile1 = rs.getString(1);
				mobile2 = rs.getString(2);
				email1 = rs.getString(3);
				email2 = rs.getString(4);
			}
		} catch (SQLException e) {
			log.error("SQL Exception in loadEmployeeContactDetails() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in loadEmployeeContactDetails() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return true;
	}

	private boolean changePassword() {
		String query = "{call sp_employee_change_password(?,?,?,?)}";
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			con = new DataBaseConnection().getConnection();
			stmt = con.prepareCall(query);
			stmt.setString(1, ((PortSession) getSession()).getEmployeeid());
			stmt.setInt(2, ((PortSession) getSession()).getSessionid());
			stmt.setString(3, AES.encrypt(oldpassword));
			stmt.setString(4, AES.encrypt(newpassword));
			rs = stmt.executeQuery();
			log.info("Executing Stored Procedure { " + stmt.toString() + " }");
			while (rs.next()) {
				log.info("Password Successfully Changed With Employee Id" + rs.getString(1));

			}
		} catch (SQLException e) {
			log.error("SQL Exception in changePassword() method {" + e.getMessage() + "}");
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
				log.error("SQL Exception in changePassword() method {" + e2.getMessage() + "}");
				e2.printStackTrace();
			}
		}
		return true;
	}

}
