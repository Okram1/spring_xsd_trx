package com.argility.master.trxengine.iface.exception;

public class FinderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1212L;

	public FinderException() {
	}

	public FinderException(String message) {
		super(message);
	}

	public FinderException(Throwable cause) {
		super(cause);
	}

	public FinderException(String message, Throwable cause) {
		super(message, cause);
	}

}
