package id.sis.service.accounting.exception;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Log4j2
public class SISException extends Exception {

	private static final long serialVersionUID = 341192781956845817L;

	private final static Logger logger = LoggerFactory.getLogger(SISException.class);

	public SISException() {
		super();
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (int i = 1; i < 4; i++)
		{
			logger.error("[CDC] method " +
					stackTrace[i].getMethodName() + " in class " +
					stackTrace[i].getClassName() + ":" +
					stackTrace[i].getLineNumber());
		}

	}

	public SISException(String message) {
		super(message);
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		logger.error("[CDC] message : " + message);
		for (int i = 1; i < 4; i++)
		{
			logger.error("[CDC] method " +
					stackTrace[i].getMethodName() + " in class " +
					stackTrace[i].getClassName() + ":" +
					stackTrace[i].getLineNumber());
		}

	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

}