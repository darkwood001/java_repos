package cn.einino.commons.util.exception;

public class HttpException extends Exception {

	private static final long serialVersionUID = -2787010648565367715L;

	public HttpException() {
		super();
	}

	public HttpException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HttpException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpException(String message) {
		super(message);
	}

	public HttpException(Throwable cause) {
		super(cause);
	}

}
