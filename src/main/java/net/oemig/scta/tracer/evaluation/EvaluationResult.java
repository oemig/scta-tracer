package net.oemig.scta.tracer.evaluation;

import java.util.Set;

import net.oemig.scta.tracer.jfreechart.data.SCTADataset;

import org.jfree.chart.JFreeChart;

public class EvaluationResult {

	private SCTADataset dataset;

	private Set<SessionResult> sessionResults;

	public void setDataset(SCTADataset dataset) {
		this.dataset = dataset;
	}

	public SCTADataset getDataset() {
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
