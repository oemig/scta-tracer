package net.oemig.scta.tracer;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.oemig.scta.tracer.question.QuestionType;

public interface ITracerMediator extends Remote {

	public void register(String userName, ITracerColleague colleague)
			throws RemoteException;

	public void unregister(String userName) throws RemoteException;

	public void saveCountData(String participantName, String letter, int result)
			throws RemoteException;

	/**
	 * Saves the response of the user to a question asked during a freeze probe.
	 * 
	 * @param participantName - the name of the participant answering
	 * @param isCorrect - indication of the correctness of this response
	 * @param responseTime - response time in ms
	 * @param questionType - {@link QuestionType} this response refers to 
	 * @throws RemoteException
	 */
	public void saveResponseData(
			String participantName, 
			boolean isCorrect,
			int responseTime,
			QuestionType questionType) throws RemoteException;

	public void finishedFreezeProbe(String userName) throws RemoteException;
}
