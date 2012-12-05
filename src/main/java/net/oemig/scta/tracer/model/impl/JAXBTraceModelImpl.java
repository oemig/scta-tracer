package net.oemig.scta.tracer.model.impl;

import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXB;

import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.ObjectFactory;
import net.oemig.scta.tracer.model.binding.Trace;
import net.oemig.scta.tracer.model.binding.Trace.Session;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.CountData;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.Participant;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.ResponseData;

public class JAXBTraceModelImpl implements ITraceModel {

	private static final String TRACE_FILE_PREFIX = "scta_";

	private ObjectFactory objectFactory;
	private Trace currentTrace;
	private Session currentSession;
	private Run currentRun;
	private IConfiguration configuration;

	public JAXBTraceModelImpl(IConfiguration aConfiguration) {
		this.configuration=aConfiguration;
		this.objectFactory = new ObjectFactory();
		// is there an existing trace file
		// yes
		// load trace file
		// select existing session
		// yes
		// no
		// create new session object
		// no
		// create new trace object
		// create new session object
		String traceName = JOptionPane
				.showInputDialog("Please enter a trace name!");
		try{
			//try to find trace file
			File traceFile=new File(configuration.getTraceFileDirectory()+File.separator+TRACE_FILE_PREFIX+traceName+".xml");
			setCurrentTrace(JAXB.unmarshal(traceFile, Trace.class));
		}catch(Exception e){
			//not found.. create a new one
			setCurrentTrace(this.objectFactory.createTrace());
			getCurrentTrace().setName(traceName);
		}

		String sessionName = JOptionPane
				.showInputDialog("Please enter a session name!");

		setCurrentSession(this.objectFactory.createTraceSession());
		getCurrentSession().setName(sessionName);
		getCurrentTrace().getSession().add(getCurrentSession());

		setCurrentRun(this.objectFactory.createTraceSessionRun());
		getCurrentSession().getRun().add(getCurrentRun());

	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.model.ITraceModel#getCurrentTrace()
	 */
	@Override
	public Trace getCurrentTrace() {
		return currentTrace;
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.model.ITraceModel#setCurrentTrace(net.oemig.scta.tracer.model.binding.Trace)
	 */
	@Override
	public void setCurrentTrace(Trace currentTrace) {
		this.currentTrace = currentTrace;
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.model.ITraceModel#getCurrentSession()
	 */
	@Override
	public Session getCurrentSession() {
		return currentSession;
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.model.ITraceModel#setCurrentSession(net.oemig.scta.tracer.model.binding.Trace.Session)
	 */
	@Override
	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.model.ITraceModel#getCurrentRun()
	 */
	@Override
	public Run getCurrentRun() {
		return currentRun;
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.model.ITraceModel#setCurrentRun(net.oemig.scta.tracer.model.binding.Trace.Session.Run)
	 */
	@Override
	public void setCurrentRun(Run currentRun) {
		this.currentRun = currentRun;
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.model.ITraceModel#addParticipant(java.lang.String)
	 */
	@Override
	public void addParticipant(String name) {
		Participant participant = objectFactory
				.createTraceSessionRunParticipant();
		participant.setName(name);
		getCurrentRun().getParticipant().add(participant);
	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.model.ITraceModel#addCountData(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void addCountData(String participantName, String letter, int quantity) {
		CountData countData = objectFactory.createTraceSessionRunCountData();
		countData.setParticipant(participantName);
		countData.setLetter(letter);
		countData.setQuantity(quantity);

		getCurrentRun().getCountData().add(countData);

	}

	/* (non-Javadoc)
	 * @see net.oemig.scta.tracer.model.ITraceModel#addResponseData(java.lang.String, boolean, int)
	 */
	@Override
	public void addResponseData(String participantName, boolean isCorrect,
			int responseTime) {
		ResponseData responseData = this.objectFactory
				.createTraceSessionRunResponseData();
		responseData.setParticipant(participantName);
		responseData.setCorrect(isCorrect);
		responseData.setResponsetime(new Integer(responseTime));

		getCurrentRun().getResponseData().add(responseData);

	}

	@Override
	public void save() {
		JAXB.marshal(getCurrentTrace(), configuration.getTraceFileDirectory()+File.separator+TRACE_FILE_PREFIX+getCurrentTrace().getName()+".xml");		
	}

}
