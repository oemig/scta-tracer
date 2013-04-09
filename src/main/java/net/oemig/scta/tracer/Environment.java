package net.oemig.scta.tracer;

import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.exporter.IExporter;
import net.oemig.scta.model.exporter.impl.CsvExporterImpl;
import net.oemig.scta.model.impl.JAXBTraceModelImpl;
import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.configuration.impl.PropertyConfigurationImpl;
import net.oemig.scta.tracer.log.ILog;
import net.oemig.scta.tracer.log.impl.Logger;

public final class Environment {
	
	public static Environment getInstance(){
		return new Environment();
	}

	private IConfiguration configuration;
	private CsvExporterImpl exporter;
	private JAXBTraceModelImpl model;
	private Logger logger;
	
	private Environment(){
		configuration=new PropertyConfigurationImpl();
		exporter=CsvExporterImpl.getInstance();
		model= JAXBTraceModelImpl.with("c:\\scta-traces", exporter);
		logger=new Logger();
	}

	
	public IConfiguration getConfiguration(){
		return configuration;
	}
	public IExporter getExporter(){
		return exporter;
	}
	
	public ILog getLogger(){
		return logger;
	}
	
	public ITraceModel getTraceModel(){
		return model;
	}
	
}