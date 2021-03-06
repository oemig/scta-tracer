package net.oemig.scta.tracer.evaluation.impl;

import net.oemig.scta.jfreechart.data.DefaultSctaDataset;
import net.oemig.scta.jfreechart.data.SctaDataset;
import net.oemig.scta.jfreechart.data.SctaDatasetItem;
import net.oemig.scta.jfreechart.data.SctaDatasetSeries;
import net.oemig.scta.model.ISession;
import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.exception.ResponseDataMissingException;
import net.oemig.scta.model.kpi.result.KpiResult;
import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.evaluation.EvaluationResult;
import net.oemig.scta.tracer.evaluation.EvaluationResultBuilder;
import net.oemig.scta.tracer.evaluation.IEvaluation;
import net.oemig.scta.tracer.evaluation.exception.ModelMissingException;

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
			throws ModelMissingException, ResponseDataMissingException {
		if (null == aModel) {
			throw new ModelMissingException("model is null");
		}

		EvaluationResultBuilder builder = EvaluationResultBuilder.getInstance();

		for (ISession s : aModel.getCurrentTrace().getSessions()) {
			builder.add(KpiResult.getInstance(s.getName(),s.getRuns(), configuration.getRunDuration(),configuration.getForgettingTime()));
		}

		return builder.withDataset(createSampleSCTADataset()).build();
	}

	private SctaDataset createSampleSCTADataset() {
		DefaultSctaDataset d = DefaultSctaDataset.getInstance();
		SctaDatasetSeries s = new SctaDatasetSeries("Trace");

		s.add(new SctaDatasetItem("A0:Initial System", 88.0, 68.0, 0.5, 0.7));
		s.add(new SctaDatasetItem("A1:Changed wording", 44.0, 50.0, 1.0, 1.3));
		s.add(new SctaDatasetItem("A2:Improved display", 21.0, 19.0, 1.0, 2.2));
		d.addSeries(s);

		return d;

	}

	@Override
	public IEvaluation with(IConfiguration aConfiguration) {
		this.configuration = aConfiguration;
		return this;
	}

}
