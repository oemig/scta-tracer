package net.oemig.scta.tracer.run;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.exception.TracerException;
import net.oemig.scta.tracer.question.Question;

/**
 * The assessment run class represents all features of an assessment run: <li>It
 * contains a {@link Document} which is used for the counting task during the
 * run <li>It has a {@link Timer} which triggers the {@link FreezeProbe}s which
 * contain a configured amount of {@link Question} instances.
 * 
 */
public class AssessmentRun {

	private List<IAssessmentRunListener> listeners;
	private Document document;
	private IConfiguration configuration;
	private int timerCounter = 0;
	private Timer timer;

	/**
	 * Constructor
	 * 
	 * @param newConfiguration
	 *            - the configuration needed for timer, document and question
	 *            setup
	 */
	public AssessmentRun(IConfiguration newConfiguration) {

		this.configuration = newConfiguration;
		this.listeners = new ArrayList<IAssessmentRunListener>();
		this.document = Document.getInstance(this.configuration);

		// setup internal timer
		int runDurationInMillis = this.configuration.getRunDuration() * 1000;
		int millisBetweenFreezeProbes = runDurationInMillis
				/ this.configuration.getFreezeProbesPerRun();

		ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				triggerFreezeProbe();
			}
		};
		this.timer = new Timer(millisBetweenFreezeProbes, taskPerformer);

	}

	public Document getDocument() {
		return this.document;
	}

	/**
	 * Call back method for freeze probe timer.
	 * 
	 * @throws TracerException
	 */
	public void triggerFreezeProbe() {
		// call back to the mediator who
		// distributes the freeze probe among the
		// registered colleagues

		// Logger.log(getClass().getName()
		// + ": triggering freeze probe on number of listeners "
		// + listeners.size());

		timer.stop();
		// create question set
		for (IAssessmentRunListener l : this.listeners) {
			try {

				l.doFreezeProbe();
			} catch (TracerException e) {
				// Logger.log(getClass().getName() + ": " + e.getMessage());
			}
		}
	}

	public void stop() {
		this.timer.stop();
		// Logger.log(getClass().getName() + ": Timer stopped.");
	}

	public void start() {
		this.timer.start();
		timerCounter = 0;
		// Logger.log(getClass().getName() + ": Timer started (delay:"
		// + this.timer.getDelay() + " ms)");
	}

	public void resume() {
		// we resume the run.. but we restart the internal timer for this
		// if number of run phases (i.e. the number of timer restarts) is
		// reached
		// no further restart is possible.. the run is over (every run ends with
		// the
		// last freeze probe!!
		if (this.timerCounter <= this.configuration.getFreezeProbesPerRun()) {

			this.timer.restart();
			// Logger.log(getClass().getName()
			// + ": Timer restarted (timer counter: " + this.timerCounter
			// + "; delay:" + this.timer.getDelay() + " ms)");

			this.timerCounter++;

		} else {
			this.timer.stop();
			for (IAssessmentRunListener l : this.listeners) {
				try {
					l.finishedRun();
				} catch (TracerException e) {
					// Logger.log(getClass().getName() + ": " +
					// e.getStackTrace());
				}
			}

		}
	}

	public void addListener(IAssessmentRunListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(IAssessmentRunListener listener) {
		this.listeners.remove(listener);
	}

}
