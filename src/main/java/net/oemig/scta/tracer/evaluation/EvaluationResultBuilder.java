package net.oemig.scta.tracer.evaluation;

import net.oemig.scta.tracer.jfreechart.data.SctaDataset;

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

	public EvaluationResultBuilder add(SessionResult aSessionResult) {
		e.getSessionResults().add(aSessionResult);
		return this;
	}

	public EvaluationResult build() {
		return e;
	}
}
