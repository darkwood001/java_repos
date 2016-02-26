package cn.einino.commons.timer.exception;

public class InterceptorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 615340681693443103L;

	public InterceptorException() {
		super();
	}

	public InterceptorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InterceptorException(String message, Throwable cause) {
		super(message, cause);
	}

	public InterceptorException(String message) {
		super(message);
	}

	public InterceptorException(Throwable cause) {
		super(cause);
	}

}
