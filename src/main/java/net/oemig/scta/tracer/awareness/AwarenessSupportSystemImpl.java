package net.oemig.scta.tracer.awareness;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.oemig.scta.tracer.exception.TracerException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public final class AwarenessSupportSystemImpl implements IAwarenessSupportSystem, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -26775073882636906L;

	public static AwarenessSupportSystemImpl newInstance(){
		return new AwarenessSupportSystemImpl();
	}

	private final List<IAwarenessSupportListener> listeners;
	private final Map<Date, AwarenessEvent> dateToEvent;
	
	private AwarenessSupportSystemImpl(){
		listeners=Lists.newArrayList();
		dateToEvent=Maps.newHashMap();
	}

	@Override
	public void addListener(IAwarenessSupportListener aListener) {
		listeners.add(aListener);

	}

	@Override
	public void removeListener(IAwarenessSupportListener aListener) {
		listeners.remove(aListener);
	}

	@Override
	public void log(AwarenessEvent anAwarenessEvent) {
		// TODO Auto-generated method stub
		dateToEvent.put(new Date(), anAwarenessEvent);
		notifyAllListeners();

	}

	@Override
	public Map<Date, AwarenessEvent> get() {
		return ImmutableMap.copyOf(dateToEvent);
	}
	
	private void notifyAllListeners(){
		for(IAwarenessSupportListener l:listeners){
			try {
				l.update();
			} catch (TracerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
