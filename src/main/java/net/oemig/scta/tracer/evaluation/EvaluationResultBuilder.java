package net.oemig.scta.tracer.evaluation;

import net.oemig.scta.jfreechart.data.SctaDataset;
import net.oemig.scta.model.kpi.result.KpiResult;

public class EvaluationResultBuilder {

	private EvaluationResult e;

	public static EvaluationResultBuilder getInstance() {
		return new EvaluationResultBuilder();
	}

	private EvaluationResultBuilder() {
		e = new EvaluationResult();
	}

	public EvaluationResultBuilder withDataset(SctaDataset aDataset) {
		e.setDataset(aDataset);
		return this;
	}

	public EvaluationResultBuilder add(KpiResult aSessionResult) {
		e.getSessionResults().add(aSessionResult);
		return this;
	}

	public EvaluationResult build() {
		return e;
	}
}
