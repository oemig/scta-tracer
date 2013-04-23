package net.oemig.scta.tracer.configuration.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import net.oemig.scta.model.data.Millisecond;
import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.exception.TracerException;

public class PropertyConfigurationImpl implements IConfiguration {

	private static final String FORGETTING_TIME = "FORGETTING_TIME";
	private static final String LETTERS_PER_WORD = "LETTERS_PER_WORD";
	private static final String DOCUMENT_SIZE = "DOCUMENT_SIZE";
	private static final String QUESTIONS_PER_FREEZE_PROBE = "QUESTIONS_PER_FREEZE_PROBE";
	private static final String FREEZE_PROBES_PER_RUN = "FREEZE_PROBES_PER_RUN";
	private static final String RUN_DURATION = "RUN_DURATION";
	private static final String TRACE_FILE_DIR="TRACE_FILE_DIR";
	private static final String CONFIGURATION_FILE_NAME = "SCTATracerConfiguration";

	private Properties applicationProperties;

	public PropertyConfigurationImpl() {
		// setup default properties
		applicationProperties = new Properties(this.createDefaultProperties());

		// try to load properties from file
		// if not possible use defaults
		try {
			this.load();
		} catch (TracerException e) {
			// Logger.log("Cannot load configuration from file. Use default configuration instead.");
		}
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#getForgettingTime()
	 */
	@Override
	public Millisecond getForgettingTime() {
		return Millisecond.of(Integer.parseInt(this.applicationProperties
				.getProperty(FORGETTING_TIME)));
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#setForgettingTime(int)
	 */
	@Override
	public void setForgettingTime(Millisecond t) {
		this.applicationProperties.setProperty(FORGETTING_TIME,
				Integer.toString(t.intValue()));
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#getLettersPerWord()
	 */
	@Override
	public int getLettersPerWord() {
		return Integer.parseInt(this.applicationProperties
				.getProperty(LETTERS_PER_WORD));
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#setLettersPerWord(int)
	 */
	@Override
	public void setLettersPerWord(int l) {
		this.applicationProperties.setProperty(LETTERS_PER_WORD,
				Integer.toString(l));
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#getDocumentSize()
	 */
	@Override
	public int getDocumentSize() {
		return Integer.parseInt(this.applicationProperties
				.getProperty(DOCUMENT_SIZE));
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#setDocumentSize(int)
	 */
	@Override
	public void setDocumentSize(int s) {
		this.applicationProperties.setProperty(DOCUMENT_SIZE,
				Integer.toString(s));
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#getQuestionsPerFreezeProbe()
	 */
	@Override
	public int getQuestionsPerFreezeProbe() {
		return Integer.parseInt(this.applicationProperties
				.getProperty(QUESTIONS_PER_FREEZE_PROBE));
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#setQuestionsPerFreezeProbe(int)
	 */
	@Override
	public void setQuestionsPerFreezeProbe(int q) {
		this.applicationProperties.setProperty(QUESTIONS_PER_FREEZE_PROBE,
				Integer.toString(q));
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#getFreezeProbesPerRun()
	 */
	@Override
	public int getFreezeProbesPerRun() {
		return Integer.parseInt(this.applicationProperties
				.getProperty(FREEZE_PROBES_PER_RUN));
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#setFreezeProbesPerRun(int)
	 */
	@Override
	public void setFreezeProbesPerRun(int f) {
		this.applicationProperties.setProperty(FREEZE_PROBES_PER_RUN,
				Integer.toString(f));
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#getRunDuration()
	 */
	@Override
	public Millisecond getRunDuration() {
		return Millisecond.of(Integer.parseInt(this.applicationProperties
				.getProperty(RUN_DURATION)));
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#setRunDuration(int)
	 */
	@Override
	public void setRunDuration(Millisecond d) {
		this.applicationProperties.setProperty(RUN_DURATION,
				Integer.toString(d.intValue()));
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#getTraceFileDirectory()
	 */
	@Override
	public String getTraceFileDirectory(){
		return applicationProperties.getProperty(TRACE_FILE_DIR);
	}
	
	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#setTraceFileDirectory(java.lang.String)
	 */
	@Override
	public void setTraceFileDirectory(String aPath){
		applicationProperties.setProperty(TRACE_FILE_DIR, aPath);
	}
	
	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.configuration.IConfiguration#store()
	 */
	@Override
	public void store() throws TracerException {

		try {
			FileOutputStream out = new FileOutputStream(CONFIGURATION_FILE_NAME);
			this.applicationProperties.store(out, "---no comment---");
			out.close();
		} catch (FileNotFoundException e) {
			throw new TracerException(TracerException.FILE_NOT_FOUND_EXCEPTION,
					e);
		} catch (IOException e) {
			throw new TracerException(TracerException.IO_EXCEPTION, e);
		}

	}

	private void load() throws TracerException {

		try {
			FileInputStream in = new FileInputStream(CONFIGURATION_FILE_NAME);
			this.applicationProperties.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			throw new TracerException(TracerException.FILE_NOT_FOUND_EXCEPTION,
					e);
		} catch (IOException e) {
			throw new TracerException(TracerException.IO_EXCEPTION, e);
		}
	}

	private Properties createDefaultProperties() {
		Properties dp = new Properties();
		dp.setProperty(RUN_DURATION, Integer.toString(15));
		dp.setProperty(FREEZE_PROBES_PER_RUN, Integer.toString(1));
		dp.setProperty(QUESTIONS_PER_FREEZE_PROBE, Integer.toString(4));
		dp.setProperty(DOCUMENT_SIZE, Integer.toString(2250));
		dp.setProperty(LETTERS_PER_WORD, Integer.toString(5));
		dp.setProperty(FORGETTING_TIME, Integer.toString(10));
		dp.setProperty(TRACE_FILE_DIR, "c:/scta-traces");
		return dp;
	}

}
