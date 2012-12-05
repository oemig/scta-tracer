package net.oemig.scta.tracer.util;

import java.util.ArrayList;
import java.util.List;

public class Logger {

	List<ILogListener> listeners = new ArrayList<ILogListener>();

	public void log(String message) {
		for (ILogListener l : listeners) {
			l.onEntry(message);
		}
		// System.out.println(message);
	}

	public void addListener(ILogListener ll) {
		listeners.add(ll);
	}

}
