package net.oemig.scta.tracer.configuration;

import net.oemig.scta.model.data.Millisecond;
import net.oemig.scta.tracer.exception.TracerException;

public interface IConfiguration {

	public abstract Millisecond getForgettingTime();

	public abstract void setForgettingTime(Millisecond t);

	public abstract int getLettersPerWord();

	public abstract void setLettersPerWord(int l);

	public abstract int getDocumentSize();

	public abstract void setDocumentSize(int s);

	public abstract int getQuestionsPerFreezeProbe();

	public abstract void setQuestionsPerFreezeProbe(int q);

	public abstract int getFreezeProbesPerRun();

	public abstract void setFreezeProbesPerRun(int f);

	public abstract Millisecond getRunDuration();

	public abstract void setRunDuration(Millisecond d);

	public abstract String getTraceFileDirectory();

	public abstract void setTraceFileDirectory(String aPath);

	public abstract void store() throws TracerException;

}