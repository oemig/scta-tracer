package net.oemig.scta.tracer.exception;

public class TracerException extends Exception {

	public static final int NOT_BOUND_EXCEPTION = 1;

	public static final int ACCESS_EXCEPTION = 2;

	public static final int REMOTE_EXCEPTION = 3;

	public static final int ALREADY_BOUND_EXCEPTION = 4;

	public static final int FILE_NOT_FOUND_EXCEPTION = 5;

	public static final int IO_EXCEPTION = 6;

	public static final int MAXMIMUM_NUMBER_OF_FREEZE_PROBES_REACHED = 7;

	int errorCode;

	public TracerException(int newErrorCode, Exception e) {
		super(e);
		this.errorCode = newErrorCode;

	}

	public TracerException(int newErrorCode) {
		this.errorCode = newErrorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
