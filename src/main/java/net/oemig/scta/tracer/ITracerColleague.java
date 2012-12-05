package net.oemig.scta.tracer;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.run.Document;

public interface ITracerColleague extends Remote {

	public void showWaitScreen(String message) throws RemoteException;

	public void showDocumentScreen(Document document, String message)
			throws RemoteException;

	public void doFreezeProbe(Question[] questions) throws RemoteException;
}
