package net.oemig.scta.tracer;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.oemig.scta.tracer.exception.TracerException;

public class TracerSetup {

	private static final String TRACER_LOOKUP_NAME = "TracerLookupName";
	public static final String TRACER_VERSION = "2.0";

	public static void main(String[] args) throws Exception {
		setLookAndFeel();
		new TracerSetup();
	}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public TracerSetup() throws Exception {
		

		try {
			ITracerMediator mediator = lookupMediator();

			// still here?
			new TracerColleagueImpl(mediator);
			// if binding is successful then you
			// are the administrator of the software
			// next we need to setup the data model for the
			// mediator to work with

		} catch (TracerException e) {
			ITracerMediator mediator = new TracerMediatorImpl();
			bindMediator(mediator);
		}
	}

	private ITracerMediator lookupMediator() throws TracerException {
		try {
			return (ITracerMediator) LocateRegistry.getRegistry().lookup(
					System.getProperty(TRACER_LOOKUP_NAME));
		} catch (AccessException e1) {
			throw new TracerException(TracerException.ACCESS_EXCEPTION, e1);
		} catch (RemoteException e1) {
			throw new TracerException(TracerException.REMOTE_EXCEPTION, e1);
		} catch (NotBoundException e1) {
			throw new TracerException(TracerException.NOT_BOUND_EXCEPTION, e1);
		}
	}

	private void bindMediator(final ITracerMediator mediator) throws TracerException {
		try {
			ITracerMediator stub = (ITracerMediator) UnicastRemoteObject
					.exportObject(mediator, 0);
			LocateRegistry.getRegistry().bind(System.getProperty(TRACER_LOOKUP_NAME), stub);
		} catch (RemoteException e) {
			throw new TracerException(TracerException.REMOTE_EXCEPTION, e);
		} catch (AlreadyBoundException e) {
			throw new TracerException(TracerException.ALREADY_BOUND_EXCEPTION,
					e);
		}

		// Logger.log("Tracer bound successfully.");
	}

}
