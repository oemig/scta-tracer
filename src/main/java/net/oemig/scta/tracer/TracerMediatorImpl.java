package net.oemig.scta.tracer;

import java.rmi.RemoteException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JOptionPane;

import net.oemig.scta.model.data.ExperiementId;
import net.oemig.scta.model.data.Millisecond;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
import net.oemig.scta.model.exception.NoCurrentSessionSelectedException;
import net.oemig.scta.model.exception.ResponseDataMissingException;
import net.oemig.scta.tracer.awareness.AwarenessEvent;
import net.oemig.scta.tracer.awareness.IAwarenessSupportListener;
import net.oemig.scta.tracer.coordination.ICoordinationSupportListener;
import net.oemig.scta.tracer.evaluation.EvaluationResult;
import net.oemig.scta.tracer.evaluation.IEvaluation;
import net.oemig.scta.tracer.evaluation.exception.ModelMissingException;
import net.oemig.scta.tracer.evaluation.impl.SctaV1EvaluationImpl;
import net.oemig.scta.tracer.exception.TracerException;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.question.QuestionFactory;
import net.oemig.scta.tracer.question.exception.QuestionGenerationFailedException;
import net.oemig.scta.tracer.run.AssessmentRun;
import net.oemig.scta.tracer.run.IAssessmentRunListener;
import net.oemig.scta.tracer.screen.AdministrationScreen;
import net.oemig.scta.tracer.screen.IScreen;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class TracerMediatorImpl implements ITracerMediator,
		ITracerMediatorScreenSPI, 
		IAssessmentRunListener, 
		IAwarenessSupportListener, 
		ICoordinationSupportListener {

	private static final IEvaluation DEFAULT_EVALUATION = SctaV1EvaluationImpl
			.getInstance();

	private final Map<UserName, ITracerColleague> colleagueMap;
	private final UserName userName; 
	private final Set<IRegistrationListener> registrationListeners;
	private final AssessmentRun assessmentRun;
	private final IScreen adminstrationScreen;
	private int colleaguesInFreezeProbe;
	private IEvaluation evaluation;

	private final Environment envinronment;

	/**
	 * Constructor
	 * 
	 * @throws NoCurrentSessionSelectedException 
	 * @throws NoCurrentRunSelectedException 
	 * @throws TracerException 
	 */
	public TracerMediatorImpl() throws NoCurrentRunSelectedException, NoCurrentSessionSelectedException, TracerException {
		
		
		String traceName = JOptionPane
				.showInputDialog(getResourceBundle().getString("mediator.input.trace"));
		String sessionName = JOptionPane
				.showInputDialog(getResourceBundle().getString("mediator.input.session"));
		
		envinronment=Environment.create(traceName,sessionName);
		this.userName = UserName.of(JOptionPane
				.showInputDialog(getResourceBundle().getString("mediator.input.username")));
		
		this.colleagueMap = Maps.newHashMap();
		 
		this.registrationListeners = Sets.newHashSet();


		this.assessmentRun = new AssessmentRun(envinronment.getConfiguration());
		assessmentRun.addListener(this);

		//TODO add screen as listener for assessment run to capture finish event
		this.adminstrationScreen = new AdministrationScreen(this);
		assessmentRun.addListener((IAssessmentRunListener)adminstrationScreen);

		//register mediator as listener to the awareness support system
		getEnvironment().getAwarenessSupportSystem().addListener(this);
		getEnvironment().getCoordinationSupportSystem().addListener(this);
		
		// after successful setup
		// launch the administration screen
		this.adminstrationScreen.show();

	}

	/**
	 * @throws NoCurrentRunSelectedException 
	 * @see ITracerMediator#register(String, ITracerColleague)
	 */
	@Override
	public void register(final UserName userName, final ExperiementId anExperiementId, final ITracerColleague colleague)
			throws RemoteException, NoCurrentRunSelectedException {
		envinronment.getLogger().log(getClass().getName() + ": " + userName + " registered.");
		// store name and remote reference for later use
		this.colleagueMap.put(userName, colleague);
		
		envinronment.getTraceModel().addParticipant(userName, anExperiementId);

		// bring colleague to show the wait screen
		colleague
				.showWaitScreen(getResourceBundle().getString("mediator.welcome"));

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
	public UserName getUserName() {
		return userName;
	}

	@Override
	public void startCounting(UserName aUserName, String letter)
			throws RemoteException {

		envinronment.getAwarenessSupportSystem().log(AwarenessEvent.of(aUserName, letter, getResourceBundle().getString("mediator.aw.counting.next")));
	}
	/**
	 * @throws NoCurrentRunSelectedException 
	 * @see ITracerMediator#saveCountData(String, String, int)
	 */
	@Override
	public void saveCountData(final UserName participantName, final String letter,
			final int quantity) throws NoCurrentRunSelectedException {

		// dispatch to model
		envinronment.getTraceModel().addCountData(participantName, letter, quantity);
		// count data saved in model!

		envinronment.getLogger().log(getClass().getName() + ".saveCountData() --name: "
				+ participantName + " --letter: " + letter + " --quantitiy: "
				+ quantity);
		
		envinronment.getAwarenessSupportSystem().log(AwarenessEvent.of(participantName, letter, Integer.toString(quantity)));
	}

	/**
	 * @throws NoCurrentRunSelectedException 
	 * @see ITracerMediator#saveResponseData(String, boolean, int)
	 */
	@Override
	public void saveResponseData(
			final UserName participantName, 
			final boolean isCorrect,
			final Millisecond responseTime,
			final QuestionType questionType) throws NoCurrentRunSelectedException {

		// dispatch to trace model
		envinronment.getTraceModel().addResponseData(participantName, isCorrect,
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
				coordinate();
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
		envinronment.getLogger().log(getClass().getName()
				+ ": launch freeze probe for all colleagues");
		for (UserName userName : this.colleagueMap.keySet()) {
			try {
				ITracerColleague c = colleagueMap.get(userName);
				c.doFreezeProbe(QuestionFactory.getMultiple(userName,getEnvironment(),
						getResourceBundle(), colleagueMap.keySet()));
			} catch (RemoteException e) {
				throw new TracerException(TracerException.REMOTE_EXCEPTION, e);
			} catch (NoCurrentRunSelectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (QuestionGenerationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.colleaguesInFreezeProbe = this.colleagueMap.values().size();
	}

	/**
	 * @see ITracerMediator#unregister(String)
	 */
	@Override
	public void unregister(final UserName userName) throws RemoteException {
		this.colleagueMap.remove(userName);
		notifyRegistrationListeners();
	}

	/**
	 * @see ITracerMediator#finishedFreezeProbe(String)
	 */
	@Override
	public void finishedFreezeProbe(final UserName userName) throws RemoteException {
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
	public void finishedRun() throws TracerException {
		for (ITracerColleague c : this.colleagueMap.values()) {
			try {
				c.showWaitScreen(getResourceBundle().getString("ws.thankyou"));
				envinronment.getLogger().log("Run finished.");
			} catch (RemoteException e) {
				throw new TracerException(TracerException.REMOTE_EXCEPTION, e);
			}
		}

	}

	@Override
	public UserName[] getRegisteredUsers() {
		return colleagueMap.keySet().toArray(new UserName[0]);
	}


	@Override
	public ITracerMediatorScreenSPI with(IEvaluation anEvaluation) {
		this.evaluation = anEvaluation;
		return this;
	}

	@Override
	public EvaluationResult runEvaluation() throws ModelMissingException, ResponseDataMissingException {
		if (null == evaluation) {
			// use the default if nothing else
			// is specified
			evaluation = DEFAULT_EVALUATION;
		}

		return evaluation.with(envinronment.getConfiguration()).run(envinronment.getTraceModel());
	}

	@Override
	public Environment getEnvironment() {
		return envinronment;
	}

	@Override
	public void update() throws TracerException {
		for (ITracerColleague c : colleagueMap.values()) {
			try {
				c.updateAwarenessDisplay(getEnvironment().getAwarenessSupportSystem().get());
			} catch (RemoteException e) {
				throw new TracerException(TracerException.REMOTE_EXCEPTION, e);
			}
		}
	}

	@Override
	public void coordinate() throws TracerException {
		for(UserName u: colleagueMap.keySet()){
			try {
				ITracerColleague c=colleagueMap.get(u);
				c.updateCoordinationDisplay(getEnvironment().getCoordinationSupportSystem().getLetterSet(u));
			} catch (RemoteException e) {
				throw new TracerException(TracerException.REMOTE_EXCEPTION, e);
			}
		}
	}
	
	public ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle("i18n", Locale.getDefault());
	}


}
