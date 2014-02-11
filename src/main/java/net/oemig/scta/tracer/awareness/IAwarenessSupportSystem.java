package net.oemig.scta.tracer.awareness;

import java.util.Date;
import java.util.Map;

/**
 * An {@link IAwarenessSupportSystem} implementation provides
 * awareness cues in an implementation specific way.
 * 
 * @author christoph.oemig
 *
 */
public interface IAwarenessSupportSystem {
	
	public void addListener(IAwarenessSupportListener aListener);
	public void removeListener(IAwarenessSupportListener aListener);
	public void log(AwarenessEvent anAwarenessEvent);
	public Map<Date, AwarenessEvent> get();

}
