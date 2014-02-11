package net.oemig.scta.tracer;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.oemig.scta.model.data.ExperiementId;
import net.oemig.scta.model.data.Millisecond;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.model.exception.NoCurrentRunSelectedException;

/**
 * {@link ITracerMediator} is the central remote interface for the center
 * piece of the mediator pattern (Gamma et al.) used in the SCTA tracer.
 * 
 * @author oemig
 *
 */
public interface ITracerMediator extends Remote {

	/**
	 * Register a {@link UserName} for participating in an experiment specified
	 * by the {@link ExperiementId}.
	 * @param anUserName
	 * @param anExperimentId
	 * @param aColleague
	 * @throws RemoteException
	 * @throws NoCurrentRunSelectedException 
	 */
	public void register(
			final UserName anUserName, 
			final ExperiementId anExperimentId, 
			final ITracerColleague aColleague)
			throws RemoteException, NoCurrentRunSelectedException;

	/**
	 * Unregisters an experiement's participant with the mediator.
	 * 
	 * @param userName
	 * @throws RemoteException
	 */
	public void unregister(final UserName userName) throws RemoteException;

	/**
	 * Saves the counting result from a user for a certain letter 
	 * in the mediator's model.
	 * 
	 * @param participantName
	 * @param letter
	 * @param result
	 * @throws RemoteException
	 * @throws NoCurrentRunSelectedException 
	 */
	public void saveCountData(
			final UserName participantName, 
			final String letter, 
			final int result)
			throws RemoteException, NoCurrentRunSelectedException;

	/**
	 * Saves the response of the user to a question asked during a freeze probe.
	 * 
	 * @param participantName - the name of the participant answering
	 * @param isCorrect - indication of the correctness of this response
	 * @param responseTime - response time in ms
	 * @param questionType - {@link QuestionType} this response refers to 
	 * @throws RemoteException
	 * @throws NoCurrentRunSelectedException 
	 */
	public void saveResponseData(
			final UserName participantName, 
			final boolean isCorrect,
			final Millisecond responseTime,
			final QuestionType questionType) throws RemoteException, NoCurrentRunSelectedException;

	/**
	 * FIXME add documentation
	 * @param userName
	 * @throws RemoteException
	 */
	public void finishedFreezeProbe(UserName userName) throws RemoteException;
	
	
}
