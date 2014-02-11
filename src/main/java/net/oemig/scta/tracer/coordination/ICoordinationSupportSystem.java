package net.oemig.scta.tracer.coordination;

import net.oemig.scta.model.data.UserName;

public interface ICoordinationSupportSystem {
	public void addListener(ICoordinationSupportListener aListener);
	public void removeListener(ICoordinationSupportListener aListener);
	public String getLetterSet(UserName aName);
}
