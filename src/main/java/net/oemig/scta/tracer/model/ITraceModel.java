package net.oemig.scta.tracer.model;

import net.oemig.scta.tracer.model.binding.Trace;
import net.oemig.scta.tracer.model.binding.Trace.Session;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run;

public interface ITraceModel {

	public Trace getCurrentTrace();

	public void setCurrentTrace(Trace currentTrace);

	public Session getCurrentSession();

	public void setCurrentSession(Session currentSession);

	public Run getCurrentRun();

	public void setCurrentRun(Run currentRun);

	public void addParticipant(String name);

	public void addCountData(String participantName, String letter, int quantity);

	public void addResponseData(String participantName, boolean isCorrect,
			int responseTime);

	public void save();

}