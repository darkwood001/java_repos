package cn.einino.commons.core.exception;

public class CacheException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1931318703645124463L;

	public CacheException() {
	}

	public CacheException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}

}
