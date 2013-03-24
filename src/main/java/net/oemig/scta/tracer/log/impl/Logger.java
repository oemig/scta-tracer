package net.oemig.scta.tracer.log.impl;

import java.util.ArrayList;
import java.util.List;

import net.oemig.scta.tracer.log.ILog;
import net.oemig.scta.tracer.log.ILogListener;

public class Logger implements ILog{

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
