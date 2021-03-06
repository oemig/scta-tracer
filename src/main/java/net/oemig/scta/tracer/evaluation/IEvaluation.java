package net.oemig.scta.tracer.evaluation;

import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.exception.ResponseDataMissingException;
import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.evaluation.exception.ModelMissingException;

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
	 * @throws ResponseDataMissingException 
	 */
	public EvaluationResult run(ITraceModel aModel)
			throws ModelMissingException, ResponseDataMissingException;

	public IEvaluation with(IConfiguration aConfiguration);
}
