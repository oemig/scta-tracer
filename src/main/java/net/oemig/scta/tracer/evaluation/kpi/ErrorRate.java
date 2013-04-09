package net.oemig.scta.tracer.evaluation.kpi;

import java.util.List;

import net.oemig.scta.model.binding.Trace.Session.Run;
import net.oemig.scta.model.binding.Trace.Session.Run.ResponseData;

public class ErrorRate {

	public static ErrorRate getInstance(List<Run> aRunList) {
		return new ErrorRate(aRunList);
	}

	private double value;

	private ErrorRate(List<Run> aRunList) {
		int errorCounter = 0;
		int correctCounter = 0;
		for (Run run : aRunList) {
			for (ResponseData r : run.getResponseData()) {
				if (r.isCorrect()) {
					correctCounter++;
				} else {
					errorCounter++;
				}
			}
		}

		value = errorCounter / (errorCounter + correctCounter);

	}

	public double getValue() {
		return value;
	}

}
