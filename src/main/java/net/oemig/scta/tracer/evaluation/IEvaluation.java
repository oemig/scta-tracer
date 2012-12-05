package net.oemig.scta.tracer.evaluation;

import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.evaluation.exception.ModelMissingException;
import net.oemig.scta.tracer.model.ITraceModel;

/**
 * Standard interface for evaluations. There may be serveral differnt evaluation
 * kinds, yet they all share the same interface
 * 
 * @author chris
 * 
 */
public interface IEvaluation {

	/**
	 * Starts the evaluation and returns an {@link EvaluationResult}.
	 * 
	 * @return
	 * @throws ModelMissingException
	 */
	public EvaluationResult run(ITraceModel aModel)
			throws ModelMissingException;

	public IEvaluation with(IConfiguration aConfiguration);
}
