package net.oemig.scta.tracer.exporter;

import net.oemig.scta.tracer.model.ITraceModel;

public interface IExporter {
	public void export(ITraceModel aModel);
}
