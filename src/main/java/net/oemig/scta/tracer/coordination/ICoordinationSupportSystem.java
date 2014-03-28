package net.oemig.scta.tracer.coordination;

import net.oemig.scta.model.data.UserName;
import net.oemig.scta.tracer.coordination.exception.CoordinationSupportUnavailableException;

public interface ICoordinationSupportSystem {
	public void addListener(ICoordinationSupportListener aListener) throws CoordinationSupportUnavailableException;
	public void removeListener(ICoordinationSupportListener aListener) throws CoordinationSupportUnavailableException;
	public String getLetterSet(UserName aName) throws CoordinationSupportUnavailableException;
	
	public void on();
	public void off();
}
