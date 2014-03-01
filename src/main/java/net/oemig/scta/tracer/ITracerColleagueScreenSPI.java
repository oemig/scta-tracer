package net.oemig.scta.tracer;

import java.util.ResourceBundle;

import net.oemig.scta.model.data.UserName;
import net.oemig.scta.tracer.log.impl.Logger;

public interface ITracerColleagueScreenSPI {
	public UserName getUserName();

	public ITracerMediator getMediator();

	public void finishedFreezeProbe();

	public Logger getLog();
	
	public ResourceBundle getResourceBundle();
}
