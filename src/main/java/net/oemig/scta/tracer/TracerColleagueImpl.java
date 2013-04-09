package net.oemig.scta.tracer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import net.oemig.scta.model.data.ExperiementId;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.tracer.log.impl.Logger;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.run.Document;
import net.oemig.scta.tracer.screen.DocumentScreen;
import net.oemig.scta.tracer.screen.FreezeProbeScreen;
import net.oemig.scta.tracer.screen.WaitScreen;

public class TracerColleagueImpl implements ITracerColleague,
		ITracerColleagueScreenSPI, Serializable {

	private static final long serialVersionUID = 1134304563074106510L;

	private ITracerMediator mediator;
	private UserName userName;
	private WaitScreen waitScreen;
	private DocumentScreen documentScreen;
	private FreezeProbeScreen freezeProbeScreen;
	private Logger log;

	public TracerColleagueImpl(ITracerMediator newMediator)
			throws RemoteException {
		this.log = new Logger();
		this.mediator = newMediator;
		
		this.userName = UserName.of(JOptionPane
				.showInputDialog("Please enter a user name!"));
		
		this.waitScreen = new WaitScreen("Connecting...");
		this.documentScreen = new DocumentScreen(this);
		this.freezeProbeScreen = new FreezeProbeScreen(this);

		final ExperiementId experimentId = ExperiementId.of(JOptionPane
				.showInputDialog("Please enter an experiment id!"));
		
		this.mediator.register(userName,experimentId,
				(ITracerColleague) UnicastRemoteObject.exportObject(this, 0));
		waitScreen.show();
	}

	@Override
	public void showWaitScreen(String message) {
		this.documentScreen.hide();
		this.freezeProbeScreen.hide();
		waitScreen.setMessage(message);
		this.waitScreen.show();
	}

	@Override
	public void showDocumentScreen(Document document, String message) {
		this.waitScreen.hide();
		this.freezeProbeScreen.hide();
		this.documentScreen.setDocument(document);
		this.documentScreen.show();
	}

	@Override
	public void doFreezeProbe(Question[] questions) throws RemoteException {
		log.log(getClass().getName() + ": doFreezeProbe..");
		this.waitScreen.hide();
		this.documentScreen.hide();
		this.freezeProbeScreen.show();
		this.freezeProbeScreen.doFreezeProbe(questions);

	}

	/**
	 * @see ITracerColleagueScreenSPI#getUserName()
	 */
	@Override
	public UserName getUserName() {
		return this.userName;
	}

	/**
	 * @see ITracerColleagueScreenSPI#getMediator()
	 */
	@Override
	public ITracerMediator getMediator() {
		return this.mediator;
	}

	@Override
	public void finishedFreezeProbe() {
		// tell the mediator that we are finshed
		try {
			freezeProbeScreen.hide();
			this.mediator.finishedFreezeProbe(userName);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Logger getLog() {
		return log;
	}
}
