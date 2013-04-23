package net.oemig.scta.tracer.evaluation;

import java.util.Set;

import net.oemig.scta.jfreechart.data.SctaDataset;
import net.oemig.scta.model.kpi.result.KpiResult;

import org.jfree.chart.JFreeChart;

public class EvaluationResult {

	private SctaDataset dataset;

	private Set<KpiResult> sessionResults;

	public void setDataset(SctaDataset dataset) {
		this.dataset = dataset;
	}

	public SctaDataset getDataset() {
		return dataset;
	}

	public JFreeChart getChart() {
		throw new UnsupportedOperationException();
	}

	public void setSessionResults(Set<KpiResult> sessionResults) {
		this.sessionResults = sessionResults;
	}

	public Set<KpiResult> getSessionResults() {
		return sessionResults;
	}

}
