package net.oemig.scta.tracer.awareness;

import net.oemig.scta.tracer.exception.TracerException;

/**
 * {@link IAwarenessSupportListener} provides the interface
 * for those who want to receive information from the {@link IAwarenessSupportSystem}
 * 
 * @author christoph.oemig
 *
 */
public interface IAwarenessSupportListener {
	
	public void update() throws TracerException;

}
