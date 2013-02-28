package net.oemig.scta.tracer;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.configuration.impl.PropertyConfigurationImpl;
import net.oemig.scta.tracer.evaluation.EvaluationResult;
import net.oemig.scta.tracer.evaluation.IEvaluation;
import net.oemig.scta.tracer.evaluation.exception.ModelMissingException;
import net.oemig.scta.tracer.evaluation.impl.SctaV1EvaluationImpl;
import net.oemig.scta.tracer.exception.TracerException;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.impl.JAXBTraceModelImpl;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.question.QuestionFactory;
import net.oemig.scta.tracer.question.QuestionType;
import net.oemig.scta.tracer.run.AssessmentRun;
import net.oemig.scta.tracer.run.IAssessmentRunListener;
import net.oemig.scta.tracer.screen.AdministrationScreen;
import net.oemig.scta.tracer.screen.IScreen;
import net.oemig.scta.tracer.util.Logger;

public class TracerMediatorImpl implements ITracerMediator,
		ITracerMediatorScreenSPI, IAssessmentRunListener {

	private static final IEvaluation DEFAULT_EVALUATION = SctaV1EvaluationImpl
			.getInstance();

	Map<String, ITracerColleague> colleagueMap;
	private String userName;
	private HashSet<IRegistrationListener> registrationListeners;
	private AssessmentRun assessmentRun;
	private IScreen adminstrationScreen;
	private ITraceModel traceModel;
	private int colleaguesInFreezeProbe;
	private IConfiguration configuration;
	private Logger log;
	private IEvaluation evaluation;

	/**
	 * Constructor
	 * 
	 * @param newUserName
	 */
	public TracerMediatorImpl(String newUserName) {
		this.userName = newUserName;
		this.colleagueMap = new HashMap<String, ITracerColleague>();
		this.configuration = new PropertyConfigurationImpl();
		this.log = new Logger();
		this.registrationListeners = new HashSet<IRegistrationListener>();

		this.traceModel = new JAXBTraceModelImpl(configuration);

		this.assessmentRun = new AssessmentRun(this.getConfiguration());
		assessmentRun.addListener(this);

		//TODO add screen as listener for assessment run to capture finish event
		this.adminstrationScreen = new AdministrationScreen(this);

		// after successful setup
		// launch the administration screen
		this.adminstrationScreen.show();

	}

	/**
	 * @see ITracerMediator#register(String, ITracerColleague)
	 */
	@Override
	public void register(String userName, ITracerColleague colleague)
			throws RemoteException {
		log.log(getClass().getName() + ": " + userName + " registered.");
		// store name and remote reference for later use
		this.colleagueMap.put(userName, colleague);

		// bring colleague to show the wait screen
		colleague
				.showWaitScreen("Welcome! Your adminstrator "
						+ this.userName
						+ " registered you for participation in the next assessment run");

		notifyRegistrationListeners();
	}

	private void notifyRegistrationListeners() {
		for (IRegistrationListener l : this.registrationListeners) {
			l.update();
		}
	}

	/**
	 * @see ITracerMediatorScreenSPI#addRegistrationListener(IRegistrationListener)
	 */
	@Override
	public void addRegistrationListener(IRegistrationListener listener) {
		this.registrationListeners.add(listener);
	}

	/**
	 * @see ITracerMediatorScreenSPI#removeRegistrationListener(IRegistrationListener)
	 */
	@Override
	public void removeRegistrationListener(IRegistrationListener listener) {
		this.registrationListeners.remove(listener);
	}

	/**
	 * @see ITracerMediatorScreenSPI#getUserName()
	 */
	@Override
	public String getUserName() {
		return userName;
	}

	/**
	 * @see ITracerMediator#saveCountData(String, String, int)
	 */
	@Override
	public void saveCountData(String participantName, String letter,
			int quantity) {

		// dispatch to model
		getTraceModel().addCountData(participantName, letter, quantity);
		// count data saved in model!

		log.log(getClass().getName() + ".saveCountData() --name: "
				+ participantName + " --letter: " + letter + " --quantitiy: "
				+ quantity);
	}

	/**
	 * @see ITracerMediator#saveResponseData(String, boolean, int)
	 */
	@Override
	public void saveResponseData(
			String participantName, 
			boolean isCorrect,
			int responseTime,
			QuestionType questionType) {

		// dispatch to trace model
		getTraceModel().addResponseData(participantName, isCorrect,
				responseTime,questionType);
		// response data saved in model!
	}

	/**
	 * @see ITracerMediatorScreenSPI#stopAssessmentRun(String)
	 */
	@Override
	public void stopAssessmentRun(String message) throws TracerException {
		// iterate over all colleagues
		// call show wait screen with message
		assessmentRun.stop();

		for (ITracerColleague c : colleagueMap.values()) {
			try {
				c.showWaitScreen(message);
			} catch (RemoteException e) {
				throw new TracerException(TracerException.REMOTE_EXCEPTION, e);
			}
		}

		// TODO delete run from model
	}

	/**
	 * @see ITracerMediatorScreenSPI#startAssessmentRun(String)
	 */
	@Override
	public void startAssessmentRun(String message) throws TracerException {
		assessmentRun.start();
		for (ITracerColleague c : colleagueMap.values()) {
			try {
				c.showDocumentScreen(assessmentRun.getDocument(), message);
			} catch (RemoteException e) {
				throw new TracerException(TracerException.REMOTE_EXCEPTION, e);
			}
		}

	}

	/**
	 * @see IAssessmentRunListener#doFreezeProbe(Question[])
	 */
	@Override
	public void doFreezeProbe() throws TracerException {
		// hand over questions to colleagues
		log.log(getClass().getName()
				+ ": launch freeze probe for all colleagues");
		for (String colleagueName : this.colleagueMap.keySet()) {
			try {
				ITracerColleague c = colleagueMap.get(colleagueName);
				c.doFreezeProbe(QuestionFactory.getMultiple(colleagueName,
						getTraceModel()));
			} catch (RemoteException e) {
				throw new TracerException(TracerException.REMOTE_EXCEPTION, e);
			}
		}

		this.colleaguesInFreezeProbe = this.colleagueMap.values().size();
	}

	/**
	 * @see ITracerMediator#unregister(String)
	 */
	@Override
	public void unregister(String userName) throws RemoteException {
		this.colleagueMap.remove(userName);
		notifyRegistrationListeners();
	}

	@Override
	public ITraceModel getTraceModel() {
		return this.traceModel;
	}

	/**
	 * @see ITracerMediator#finishedFreezeProbe(String)
	 */
	@Override
	public void finishedFreezeProbe(String userName) throws RemoteException {
		// take the user's colleague reference out of the collection
		// colleaguesInFreezeProbe.remove(this.colleagueMap.get(userName));
		colleaguesInFreezeProbe -= 1;
		// if all users finished their freeze probe
		if (colleaguesInFreezeProbe == 0) {
			for (ITracerColleague c : this.colleagueMap.values()) {
				c.showDocumentScreen(this.assessmentRun.getDocument(),
						"Please continue counting...");

				assessmentRun.resume();
			}
		}
	}

	@Override
	public IConfiguration getConfiguration() {
		return this.configuration;
	}

	@Override
	public void finishedRun() throws TracerException {
		for (ITracerColleague c : this.colleagueMap.values()) {
			try {
				c.showWaitScreen("The run has finished.. Thank you very much");
				log.log("Run finished.");
			} catch (RemoteException e) {
				throw new TracerException(TracerException.REMOTE_EXCEPTION, e);
			}
		}

	}

	@Override
	public String[] getRegisteredUsers() {
		return colleagueMap.keySet().toArray(new String[0]);
	}

	@Override
	public Logger getLog() {
		return log;
	}

	@Override
	public ITracerMediatorScreenSPI with(IEvaluation anEvaluation) {
		this.evaluation = anEvaluation;
		return this;
	}

	@Override
	public EvaluationResult runEvaluation() throws ModelMissingException {
		if (null == evaluation) {
			// use the default if nothing else
			// is specified
			evaluation = DEFAULT_EVALUATION;
		}

		return evaluation.with(configuration).run(getTraceModel());
	}

}
