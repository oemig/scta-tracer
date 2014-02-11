package net.oemig.scta.tracer.coordination;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.oemig.scta.model.data.UserName;
import net.oemig.scta.tracer.awareness.AwarenessEvent;
import net.oemig.scta.tracer.awareness.IAwarenessSupportListener;
import net.oemig.scta.tracer.awareness.IAwarenessSupportSystem;
import net.oemig.scta.tracer.exception.TracerException;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

/**
 * A {@link CoordinationSupportSystemImpl} is also known
 * as a coordination strategy. That is a certain approach
 * on coordinating through a task.
 * 
 * @author christoph.oemig
 *
 */
public class CoordinationSupportSystemImpl implements ICoordinationSupportSystem, IAwarenessSupportListener{

	public static CoordinationSupportSystemImpl newInstance(IAwarenessSupportSystem anAwarenessSupport){
		return new CoordinationSupportSystemImpl(anAwarenessSupport);
	}

	private final IAwarenessSupportSystem awarenessSupportSystem;
	private final String letterSetA="aAbBcCdDeEfFgGHhiIJjkK";
	private final String letterSetB="lLMmNnOoPpQqRrSsTtuUVvWwXxYyZz";
	private final Set<String> availableLetterSets;
	private final Map<UserName, String> userToLetterSet;
	
	private CoordinationSupportSystemImpl(IAwarenessSupportSystem anAwarenessSupport){
		listeners=Lists.newArrayList();
		awarenessSupportSystem=anAwarenessSupport;
		awarenessSupportSystem.addListener(this);
		availableLetterSets=Sets.newHashSet(letterSetA,letterSetB);
		userToLetterSet=Maps.newHashMap();
	}
	
	private final List<ICoordinationSupportListener> listeners;
	private Multimap<UserName,AwarenessEvent>  userToEvents;
	
	@Override
	public void addListener(ICoordinationSupportListener aListener) {
		listeners.add(aListener);
	}

	@Override
	public void removeListener(ICoordinationSupportListener aListener) {
		listeners.remove(aListener);
	}
	
	private void notifyListeners() throws TracerException{
		for(ICoordinationSupportListener l:listeners){
			l.coordinate();
		}
	}

	/**
	 * Callback from the awareness support system
	 */
	@Override
	public void update() throws TracerException {
		//get the information from the awareness support system
		Map<Date,AwarenessEvent> dateToEvent=awarenessSupportSystem.get();
		//do something with it
		userToEvents=ArrayListMultimap.create();
		for(AwarenessEvent e:dateToEvent.values()){
			userToEvents.put(e.getUserName(), e);
		}
		
		//notify own listeners
		notifyListeners();
		
	}

	@Override
	public String getLetterSet(UserName aName) {
		String letterSet=userToLetterSet.get(aName);
		if(null==letterSet){
			letterSet=(availableLetterSets.toArray(new String[0]))[0];
			availableLetterSets.remove(letterSet);
			userToLetterSet.put(aName, letterSet);
		}
		
		//now we have a letter set for the user
		for(AwarenessEvent e:userToEvents.get(aName)){
			letterSet=letterSet.replace(e.getLetter().charAt(0), "-".charAt(0));
		}
		
		return letterSet;
	}

}
