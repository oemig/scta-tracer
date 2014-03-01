package net.oemig.scta.tracer;

import java.util.Locale;
import java.util.ResourceBundle;

import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.exception.NoCurrentSessionSelectedException;
import net.oemig.scta.model.exporter.IExporter;
import net.oemig.scta.model.exporter.impl.CsvExporterImpl;
import net.oemig.scta.model.impl.jaxb.JAXBTraceModelImpl;
import net.oemig.scta.tracer.awareness.AwarenessSupportSystemImpl;
import net.oemig.scta.tracer.awareness.IAwarenessSupportSystem;
import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.configuration.impl.PropertyConfigurationImpl;
import net.oemig.scta.tracer.coordination.CoordinationSupportSystemImpl;
import net.oemig.scta.tracer.coordination.ICoordinationSupportSystem;
import net.oemig.scta.tracer.log.ILog;
import net.oemig.scta.tracer.log.impl.Logger;

/**
 * The {@link Environment} class defines the concrete implementation
 * of the {@link IConfiguration}, {@link IExporter}, {@link ITraceModel}, and
 * {@link ILog}
 * 
 * 
 * @author christoph.oemig
 *
 */
public final class Environment {
	
	public static Environment create(
			final String aTraceName, 
			final String aSessionName) throws NoCurrentSessionSelectedException{
		
		return new Environment(aTraceName,aSessionName);
	}

	private IConfiguration configuration;
	private final CsvExporterImpl exporter;
	private ITraceModel model;
	private ILog logger;
	private final IAwarenessSupportSystem awarenessSupportSystem;
	private final ICoordinationSupportSystem coordinationSupportSystem;
	
	private Environment(final String aTraceName, final String aSessionName) throws NoCurrentSessionSelectedException{
		configuration=new PropertyConfigurationImpl();
		exporter=CsvExporterImpl.create();
		
		//this decides on using JAXB for the model
		model=JAXBTraceModelImpl.builder().traceName(aTraceName).sessionName(aSessionName).exporter(exporter).build();
//		model= JAXBTraceModelImpl.create(aTraceName,aSessionName,"c:\\scta-traces", exporter);
		logger=new Logger();
		awarenessSupportSystem=AwarenessSupportSystemImpl.newInstance();
		coordinationSupportSystem=CoordinationSupportSystemImpl.newInstance(awarenessSupportSystem);
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
	
	public ResourceBundle getResourceBundle(){
		Locale l=Locale.getDefault();
		return ResourceBundle.getBundle("i18n",l);
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


	public IAwarenessSupportSystem getAwarenessSupportSystem() {
		return awarenessSupportSystem;
	}


	public ICoordinationSupportSystem getCoordinationSupportSystem() {
		return coordinationSupportSystem;
	}
	
}
