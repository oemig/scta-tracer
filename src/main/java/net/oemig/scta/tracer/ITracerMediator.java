package net.oemig.scta.tracer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITracerMediator extends Remote {

	public void register(String userName, ITracerColleague colleague)
			throws RemoteException;

	public void unregister(String userName) throws RemoteException;

	public void saveCountData(String participantName, String letter, int result)
			throws RemoteException;

	public void saveResponseData(String participantName, boolean isCorrect,
			int responseTime) throws RemoteException;

	public void finishedFreezeProbe(String userName) throws RemoteException;
}
