package net.oemig.scta.tracer.model;

import net.oemig.scta.tracer.data.ExperiementId;
import net.oemig.scta.tracer.data.UserName;
import net.oemig.scta.tracer.model.binding.Trace;
import net.oemig.scta.tracer.model.binding.Trace.Session;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run;
import net.oemig.scta.tracer.question.QuestionType;

public interface ITraceModel {

	public Trace getCurrentTrace();


	public Session getCurrentSession();


	public Run getCurrentRun();


	public void addParticipant(final UserName name, 
			final ExperiementId experimentId);

	public void addCountData(
			final UserName participantName, 
			final String letter, 
			final int quantity);

	public void addResponseData(
			final UserName participantName, 
			final boolean isCorrect,
			final int responseTime,
			final QuestionType questionType);

	public void save();
	
	public void export();

}