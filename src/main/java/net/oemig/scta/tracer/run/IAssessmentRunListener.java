package net.oemig.scta.tracer.run;

import net.oemig.scta.tracer.exception.TracerException;

public interface IAssessmentRunListener {
	public void doFreezeProbe() throws TracerException;

	public void finishedRun() throws TracerException;
}
