package net.oemig.scta.tracer.evaluation.impl;

import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.evaluation.EvaluationResult;
import net.oemig.scta.tracer.evaluation.EvaluationResultBuilder;
import net.oemig.scta.tracer.evaluation.IEvaluation;
import net.oemig.scta.tracer.evaluation.SessionResult;
import net.oemig.scta.tracer.evaluation.exception.ModelMissingException;
import net.oemig.scta.tracer.jfreechart.data.DefaultSCTADataset;
import net.oemig.scta.tracer.jfreechart.data.SCTADataset;
import net.oemig.scta.tracer.jfreechart.data.SCTADatasetItem;
import net.oemig.scta.tracer.jfreechart.data.SCTADatasetSeries;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.Trace.Session;

public class SctaV1EvaluationImpl implements IEvaluation {

	public static SctaV1EvaluationImpl getInstance() {
		return new SctaV1EvaluationImpl();
	}

	private IConfiguration configuration;

	// private constructor
	private SctaV1EvaluationImpl() {
	}

	@Override
	public EvaluationResult run(ITraceModel aModel)
			throws ModelMissingException {
		if (null == aModel) {
			throw new ModelMissingException("model is null");
		}

		EvaluationResultBuilder builder = EvaluationResultBuilder.getInstance();

		for (Session s : aModel.getCurrentTrace().getSession()) {
			builder.add(SessionResult.getInstance(s, configuration));
		}

		return builder.withDataset(createSampleSCTADataset()).build();
	}

	private SCTADataset createSampleSCTADataset() {
		DefaultSCTADataset d = DefaultSCTADataset.getInstance();
		SCTADatasetSeries s = new SCTADatasetSeries("Trace");

		s.add(new SCTADatasetItem("A0:Initial System", 88.0, 68.0, 0.5, 0.7));
		s.add(new SCTADatasetItem("A1:Changed wording", 44.0, 50.0, 1.0, 1.3));
		s.add(new SCTADatasetItem("A2:Improved display", 21.0, 19.0, 1.0, 2.2));
		d.addSeries(s);

		return d;

	}

	@Override
	public IEvaluation with(IConfiguration aConfiguration) {
		this.configuration = aConfiguration;
		return this;
	}

}
