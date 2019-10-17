package com.sp.master;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.WebResponse;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

public class JFreeChartImage extends Image {

	private final int width;
	private final int height;

	public JFreeChartImage(String id, JFreeChart chart, int width, int height) {
		super(id, new Model(chart));
		this.width = width;
		this.height = height;
	}

	@Override
	protected DynamicImageResource getImageResource() {
		return new DynamicImageResource() {

			@Override
			protected byte[] getImageData(Attributes attributes) {
				// TODO Auto-generated method stub
				JFreeChart chart = (JFreeChart) getDefaultModelObject();
				return toImageData(chart.createBufferedImage(width, height));
			}

		};

	}

}
