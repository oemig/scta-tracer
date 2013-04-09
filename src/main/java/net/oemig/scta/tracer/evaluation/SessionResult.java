package net.oemig.scta.tracer.evaluation;

import net.oemig.scta.model.binding.Trace.Session;
import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.evaluation.kpi.AverageResponseTimeForgettingTimeRatio;
import net.oemig.scta.tracer.evaluation.kpi.CoordinationErrorRate;
import net.oemig.scta.tracer.evaluation.kpi.ErrorRate;
import net.oemig.scta.tracer.evaluation.kpi.Performance;

public class SessionResult {

	private String name;
	private double averageResponseTimeForgettingTimeRatio;
	private double errorRate;
	private double coordinationErrorRate;
	private double performance;

	public static SessionResult getInstance(Session aSession,
			IConfiguration aConfiguration) {
		return new SessionResult(aSession, aConfiguration);
	}

	public SessionResult(Session aSession, IConfiguration aConfiguration) {
		name = aSession.getName();

		averageResponseTimeForgettingTimeRatio = AverageResponseTimeForgettingTimeRatio
				.getInstance(aSession.getRun(), aConfiguration).getValue();

		errorRate = ErrorRate.getInstance(aSession.getRun()).getValue();

		coordinationErrorRate = CoordinationErrorRate.getInstance(
				aSession.getRun()).getValue();

		performance = Performance.getInstance(aSession, aConfiguration)
				.getValue();
	}

	public String getName() {
		return name;
	}

	public double getAverageResponseTimeForgettingTimeRatio() {
		return averageResponseTimeForgettingTimeRatio;
	}

	public double getErrorRate() {
		return errorRate;
	}

	public double getCoordinationErrorRate() {
		return coordinationErrorRate;
	}

	public double getPerformance() {
		return performance;
	}

}
