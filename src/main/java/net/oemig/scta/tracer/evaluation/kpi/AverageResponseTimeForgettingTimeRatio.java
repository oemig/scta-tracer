package net.oemig.scta.tracer.evaluation.kpi;

import java.util.List;

import net.oemig.scta.model.binding.Trace.Session.Run;
import net.oemig.scta.model.binding.Trace.Session.Run.ResponseData;
import net.oemig.scta.tracer.configuration.IConfiguration;

public class AverageResponseTimeForgettingTimeRatio {

	public static AverageResponseTimeForgettingTimeRatio getInstance(
			List<Run> aRunList, IConfiguration aConfiguration) {

		return new AverageResponseTimeForgettingTimeRatio(aRunList,
				aConfiguration);
	}

	private double value;
	private double avgResponseTime;

	private AverageResponseTimeForgettingTimeRatio(List<Run> aRunList,
			IConfiguration aConfiguration) {

		int responseTimeSum = 0;
		int responseTimeSize = 0;
		for (Run run : aRunList) {
			for (ResponseData r : run.getResponseData()) {
				responseTimeSum += r.getResponsetime().intValue();
			}
			responseTimeSize += run.getResponseData().size();
		}
		avgResponseTime = responseTimeSum / responseTimeSize;

		value = avgResponseTime / aConfiguration.getForgettingTime();
	}

	public double getValue() {
		return this.value;
	}

	public double getAverageResponseTime() {
		return avgResponseTime;
	}

}
