package net.oemig.scta.tracer.coordination;

import net.oemig.scta.tracer.exception.TracerException;

/**
 * Callback for the {@link ICoordinationSupportSystem} implementation
 * to notify the {@link ICoordinationSupportListener} instances
 * to call for an update.
 * 
 * @author christoph.oemig
 *
 */
public interface ICoordinationSupportListener {
	public void coordinate() throws TracerException;
}
