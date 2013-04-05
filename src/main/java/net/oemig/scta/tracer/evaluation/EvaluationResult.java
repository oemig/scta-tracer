package net.oemig.scta.tracer.evaluation;

import java.util.Set;

import net.oemig.scta.jfreechart.data.SctaDataset;

import org.jfree.chart.JFreeChart;

public class EvaluationResult {

	private SctaDataset dataset;

	private Set<SessionResult> sessionResults;

	public void setDataset(SctaDataset dataset) {
		this.dataset = dataset;
	}

	public SctaDataset getDataset() {
		return dataset;
	}

	public JFreeChart getChart() {
		throw new UnsupportedOperationException();
	}

	public void setSessionResults(Set<SessionResult> sessionResults) {
		this.sessionResults = sessionResults;
	}

	public Set<SessionResult> getSessionResults() {
		return sessionResults;
	}

}
