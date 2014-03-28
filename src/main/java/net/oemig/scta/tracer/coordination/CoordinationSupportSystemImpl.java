package net.oemig.scta.tracer.coordination;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.oemig.scta.model.data.UserName;
import net.oemig.scta.tracer.awareness.AwarenessEvent;
import net.oemig.scta.tracer.awareness.IAwarenessSupportListener;
import net.oemig.scta.tracer.awareness.IAwarenessSupportSystem;
import net.oemig.scta.tracer.coordination.exception.CoordinationSupportUnavailableException;
import net.oemig.scta.tracer.exception.TracerException;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

/**
 * A {@link CoordinationSupportSystemImpl} is also known as a coordination
 * strategy. That is a certain approach on coordinating through a task.
 * 
 * @author christoph.oemig
 * 
 */
public class CoordinationSupportSystemImpl implements
		ICoordinationSupportSystem, IAwarenessSupportListener {

	public static CoordinationSupportSystemImpl newInstance(
			IAwarenessSupportSystem anAwarenessSupport) {
		return new CoordinationSupportSystemImpl(anAwarenessSupport);
	}

	private final IAwarenessSupportSystem awarenessSupportSystem;
	private final String letterSetA = "aAbBcCdDeEfFgGHhiIJjkKlLM";
	private final String letterSetB = "mNnOoPpQqRrSsTtuUVvWwXxYyZz";
	private final Set<String> availableLetterSets;
	private final Map<UserName, String> userToLetterSet;

	private CoordinationSupportSystemImpl(
			IAwarenessSupportSystem anAwarenessSupport) {
		listeners = Lists.newArrayList();
		awarenessSupportSystem = anAwarenessSupport;
		awarenessSupportSystem.addListener(this);
		availableLetterSets = Sets.newHashSet(letterSetA, letterSetB);
		userToLetterSet = Maps.newHashMap();
		userToEvents = ArrayListMultimap.create();

		//per default turned on
		on=true;
	}

	private final List<ICoordinationSupportListener> listeners;
	private final Multimap<UserName, AwarenessEvent> userToEvents;
	private boolean on;

	@Override
	public void addListener(ICoordinationSupportListener aListener)
			throws CoordinationSupportUnavailableException {
		if (on) {
			listeners.add(aListener);
		} else {
			throw new CoordinationSupportUnavailableException(
					"System currently turned off");
		}
	}

	@Override
	public void removeListener(ICoordinationSupportListener aListener)
			throws CoordinationSupportUnavailableException {
		if (on) {
			listeners.remove(aListener);
		} else {
			throw new CoordinationSupportUnavailableException(
					"System currently turned off");
		}
	}

	private void notifyListeners() throws TracerException {
		for (ICoordinationSupportListener l : listeners) {
			l.coordinate();
		}
	}

	/**
	 * Callback from the awareness support system
	 */
	@Override
	public void update() throws TracerException {
		// get the information from the awareness support system
		Map<Date, AwarenessEvent> dateToEvent = awarenessSupportSystem.get();
		// do something with it

		for (AwarenessEvent e : dateToEvent.values()) {
			userToEvents.put(e.getUserName(), e);
		}

		// notify own listeners
		notifyListeners();

	}

	@Override
	public String getLetterSet(UserName aName) throws CoordinationSupportUnavailableException {
		if (on) {
			String letterSet = userToLetterSet.get(aName);
			if (null == letterSet) {
				letterSet = (availableLetterSets.toArray(new String[0]))[0];
				availableLetterSets.remove(letterSet);
				userToLetterSet.put(aName, letterSet);
			}

			// might be null if there were no events yet
			if (null != userToEvents.get(aName)) {
				// now we have a letter set for the user
				for (AwarenessEvent e : userToEvents.get(aName)) {
					letterSet = letterSet.replace(e.getLetter().charAt(0),
							"-".charAt(0));
				}
			}
			return letterSet;
		} else {
			throw new CoordinationSupportUnavailableException(
					"System currently turned off");
		}
	}

	@Override
	public void on() {
		on = true;
	}

	@Override
	public void off() {
		on = false;
	}

}
