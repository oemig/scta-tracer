package net.oemig.scta.tracer;

import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.evaluation.EvaluationResult;
import net.oemig.scta.tracer.evaluation.IEvaluation;
import net.oemig.scta.tracer.evaluation.exception.ModelMissingException;
import net.oemig.scta.tracer.exception.TracerException;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.util.Logger;

public interface ITracerMediatorScreenSPI {
	public void startAssessmentRun(String message) throws TracerException;

	public void stopAssessmentRun(String message) throws TracerException;

	public void addRegistrationListener(IRegistrationListener listener);

	public void removeRegistrationListener(IRegistrationListener listener);

	public String getUserName();

	public ITraceModel getTraceModel();

	public IConfiguration getConfiguration();

	public String[] getRegisteredUsers();

	public Logger getLog();

	public ITracerMediatorScreenSPI with(IEvaluation anEvaluation);

	public EvaluationResult runEvaluation() throws ModelMissingException;

}
