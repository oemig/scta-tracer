package net.oemig.scta.tracer;

import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.exporter.IExporter;
import net.oemig.scta.model.exporter.impl.CsvExporterImpl;
import net.oemig.scta.model.impl.jaxb.JAXBTraceModelImpl;
import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.configuration.impl.PropertyConfigurationImpl;
import net.oemig.scta.tracer.log.ILog;
import net.oemig.scta.tracer.log.impl.Logger;

public final class Environment {
	
	public static Environment create(
			final String aTraceName, 
			final String aSessionName){
		
		return new Environment(aTraceName,aSessionName);
	}

	private IConfiguration configuration;
	private CsvExporterImpl exporter;
	private ITraceModel model;
	private ILog logger;
	
	private Environment(final String aTraceName, final String aSessionName){
		configuration=new PropertyConfigurationImpl();
		exporter=CsvExporterImpl.create();
		
		//this decides on using JAXB for the model
		model= JAXBTraceModelImpl.create(aTraceName,aSessionName,"c:\\scta-traces", exporter);
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
	
	public Environment with(ITraceModel aTraceModel){
		model=aTraceModel;
		return this;
	}
	
	public Environment with(IConfiguration aConfiguration){
		configuration=aConfiguration;
		return this;
	}
	
	public Environment with(ILog aLog){
		logger=aLog;
		return this;
	}
	
}
