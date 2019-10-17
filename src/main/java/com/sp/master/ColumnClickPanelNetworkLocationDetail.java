
package com.sp.master;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimeZone;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.util.time.Duration;
import com.sp.location.NetworkLocationDetail;

public class ColumnClickPanelNetworkLocationDetail extends Panel {

	private static final long serialVersionUID = 1L;
	private String status = "";

	public ColumnClickPanelNetworkLocationDetail(String id, final IModel rowModel, IModel labelModel) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<ColumnClickPanelNetworkLocationDetail>(this));
		NetworkLocationDetail n = (NetworkLocationDetail) rowModel.getObject();
		Label statuslbl = new Label("status");

		if (n.isStatus()) {
			status = "";
			statuslbl.add(new AttributeModifier("class", "fa fa-circle"));
		} else {
			status = "";
			statuslbl.add(new AttributeModifier("class", "fa fa-circle-o"));
		}
		add(statuslbl);
		Label lc = new Label("locationname", new Model(n.getLocationname()));
		add(lc);

	}

	public boolean sendPingRequest(String ipAddress) {
		InetAddress geek;
		try {
			geek = InetAddress.getByName(ipAddress);
			System.out.println("Sending Ping Request to " + ipAddress);
			if (geek.isReachable(5000))
				return true;
			else
				return false;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

}
