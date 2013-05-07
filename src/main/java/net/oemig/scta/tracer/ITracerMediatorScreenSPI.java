package net.oemig.scta.tracer;

import net.oemig.scta.model.data.UserName;
import net.oemig.scta.model.exception.ResponseDataMissingException;
import net.oemig.scta.tracer.evaluation.EvaluationResult;
import net.oemig.scta.tracer.evaluation.IEvaluation;
import net.oemig.scta.tracer.evaluation.exception.ModelMissingException;
import net.oemig.scta.tracer.exception.TracerException;

public interface ITracerMediatorScreenSPI {
	public void startAssessmentRun(String message) throws TracerException;

	public void stopAssessmentRun(String message) throws TracerException;

	public void addRegistrationListener(IRegistrationListener listener);

	public void removeRegistrationListener(IRegistrationListener listener);

	public UserName getUserName();

	public Environment getEnvironment();

	public UserName[] getRegisteredUsers();

	public ITracerMediatorScreenSPI with(IEvaluation anEvaluation);

	public EvaluationResult runEvaluation() throws ModelMissingException, ResponseDataMissingException;

}
