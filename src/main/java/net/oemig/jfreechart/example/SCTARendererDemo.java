package net.oemig.jfreechart.example;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import net.oemig.scta.tracer.jfreechart.SCTAItemLabelGenerator;
import net.oemig.scta.tracer.jfreechart.SCTARenderer;
import net.oemig.scta.tracer.jfreechart.data.SCTADataset;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration of the {@link XYLineAndShapeRenderer} class.
 */
public class SCTARendererDemo extends ApplicationFrame {

	/**
	 * Constructs the demo application.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public SCTARendererDemo(final String title) {

		super(title);
		XYDataset dataset = createSCTADataset();
		JFreeChart chart = ChartFactory.createXYLineChart(title, "X", "Y",
				dataset, PlotOrientation.VERTICAL, true, false, false);
		XYLineAndShapeRenderer renderer = new SCTARenderer();

		// background image
		URL picURL = getClass().getResource("test.jpg");
		try {
			Image image = ImageIO.read(picURL);
			chart.getPlot().setBackgroundImage(image);

		} catch (IOException e) {
			e.printStackTrace();
		}

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.getDomainAxis().setAutoRange(false);
		plot.getDomainAxis().setRange(new Range(0, 100));
		plot.getRangeAxis().setAutoRange(false);
		plot.getRangeAxis().setRange(new Range(0, 100));

		renderer.setBaseItemLabelGenerator(new SCTAItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setSeriesVisibleInLegend(0, false);

		chart.getXYPlot().setRenderer(renderer);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
		setContentPane(chartPanel);

	}

	private SCTADataset createSCTADataset() {
		// DefaultSCTADataset d=new DefaultSCTADataset();
		// SCTADatasetSeries s=new SCTADatasetSeries("Trace");
		//
		// s.add(new SCTADatasetItem("A0", 88.0, 68.0, 0.5, 2.0));
		// s.add(new SCTADatasetItem("A1", 44.0, 50.0, 1.0, 1.0));
		// s.add(new SCTADatasetItem("A2", 10.0, 11.0, 1.0, 1.0));
		// d.addSeries(s);
		//
		// return d;
		return null;
	}

	// ****************************************************************************
	// * JFREECHART DEVELOPER GUIDE *
	// * The JFreeChart Developer Guide, written by David Gilbert, is available
	// *
	// * to purchase from Object Refinery Limited: *
	// * *
	// * http://www.object-refinery.com/jfreechart/guide.html *
	// * *
	// * Sales are used to provide funding for the JFreeChart project - please *
	// * support us so that we can continue developing free software. *
	// ****************************************************************************

	/**
	 * Starting point for the demonstration application.
	 * 
	 * @param args
	 *            ignored.
	 */
	public static void main(final String[] args) {

		final SCTARendererDemo demo = new SCTARendererDemo("SCTARenderer Demo");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}
