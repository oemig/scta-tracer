package net.oemig.scta.tracer.log;

public interface ILog {

	public void log(String message);
	public void addListener(ILogListener ll);
}
