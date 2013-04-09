package net.oemig.scta.tracer.evaluation.kpi;

import net.oemig.scta.model.binding.Trace.Session;
import net.oemig.scta.model.binding.Trace.Session.Run;
import net.oemig.scta.tracer.configuration.IConfiguration;

public class Performance {

	public static Performance getInstance(Session aSession,
			IConfiguration aConfiguration) {
		return new Performance(aSession, aConfiguration);
	}

	private double value;

	public Performance(Session aSession, IConfiguration aConfiguration) {
		int countDataCounter = 0;
		for (Run run : aSession.getRun()) {
			countDataCounter += run.getCountData().size();
		}

		double avgCountDataPerRun = countDataCounter / aSession.getRun().size();

		value = avgCountDataPerRun / aConfiguration.getRunDuration();
	}

	public double getValue() {
		return value;
	}
}
