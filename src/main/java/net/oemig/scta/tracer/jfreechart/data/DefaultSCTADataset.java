package net.oemig.scta.tracer.jfreechart.data;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.xy.AbstractXYDataset;

public class DefaultSCTADataset extends AbstractXYDataset implements
		SCTADataset {

	private List<SCTADatasetSeries> seriesList;
	private List<String> seriesKeys;

	public static DefaultSCTADataset getInstance() {
		return new DefaultSCTADataset();
	}

	private DefaultSCTADataset() {
		this.seriesKeys = new ArrayList<String>();
		this.seriesList = new ArrayList<SCTADatasetSeries>();
	}

	public void addSeries(SCTADatasetSeries series) {
		this.seriesList.add(series);
		this.seriesKeys.add(series.getName());
	}

	@Override
	public int getItemCount(int series) {
		if ((series < 0) || (series >= getSeriesCount())) {
			throw new IllegalArgumentException("Series index out of bounds");
		}
		return this.seriesList.get(series).getItems().length;
	}

	@Override
	public Number getY(int series, int item) {
		return getErrorRate(series, item);
	}

	@Override
	public Number getPerformance(int series, int item) {
		return new Double(
				this.seriesList.get(series).getItems()[item].getPerformance());
	}

	@Override
	public Number getCoordinationErrorRate(int series, int item) {
		return new Double(
				this.seriesList.get(series).getItems()[item]
						.getCoordinationErrorRate());
	}

	@Override
	public int getSeriesCount() {
		return this.seriesList.size();
	}

	@Override
	public Comparable<String> getSeriesKey(int series) {
		if ((series < 0) || (series >= getSeriesCount())) {
			throw new IllegalArgumentException("Series index out of bounds");
		}
		return this.seriesList.get(series).getName();
	}

	@Override
	public Number getX(int series, int item) {
		return getResponseTimeHalfLifeOfAwarenessRatio(series, item);
	}

	@Override
	public String getSessionName(int series, int item) {
		return this.seriesList.get(series).getItems()[item].getSessionName();
	}

	@Override
	public Number getResponseTimeHalfLifeOfAwarenessRatio(int series, int item) {
		return new Double(
				this.seriesList.get(series).getItems()[item]
						.getResponseTimeHalfLifeOfAwarenessRatio());
	}

	@Override
	public Number getErrorRate(int series, int item) {
		return new Double(
				this.seriesList.get(series).getItems()[item].getErrorRate());
	}

}
