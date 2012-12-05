package net.oemig.scta.tracer;

import net.oemig.scta.tracer.util.Logger;

public interface ITracerColleagueScreenSPI {
	public String getUserName();

	public ITracerMediator getMediator();

	public void finishedFreezeProbe();

	public Logger getLog();
}
