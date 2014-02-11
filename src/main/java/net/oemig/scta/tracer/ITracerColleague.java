package net.oemig.scta.tracer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Map;

import net.oemig.scta.tracer.awareness.AwarenessEvent;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.run.Document;

public interface ITracerColleague extends Remote {

	public void showWaitScreen(String message) throws RemoteException;

	public void showDocumentScreen(Document document, String message)
			throws RemoteException;

	public void doFreezeProbe(Question[] questions) throws RemoteException;
	
	public void updateAwarenessDisplay(Map<Date, AwarenessEvent> someEvents) throws RemoteException;
	public void updateCoordinationDisplay(String someContents)throws RemoteException;
}
