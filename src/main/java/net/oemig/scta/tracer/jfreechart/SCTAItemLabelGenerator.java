package net.oemig.scta.tracer.jfreechart;

import java.io.Serializable;

import net.oemig.scta.tracer.jfreechart.data.SCTADataset;

import org.jfree.chart.labels.AbstractXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.PublicCloneable;

public class SCTAItemLabelGenerator  extends AbstractXYItemLabelGenerator implements  XYItemLabelGenerator, Cloneable, PublicCloneable, Serializable {

	@Override
	public String generateLabel(XYDataset dataset, int series, int item) {
		if (dataset instanceof SCTADataset) {
			return ((SCTADataset) dataset).getSessionName(series, item);
		}
		
		return "???";
	}

}
