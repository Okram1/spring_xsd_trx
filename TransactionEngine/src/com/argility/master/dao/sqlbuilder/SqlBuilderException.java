package com.argility.master.dao.sqlbuilder;

public class SqlBuilderException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SqlBuilderException() {
	}

	public SqlBuilderException(String message) {
		super(message);
	}

	public SqlBuilderException(Throwable cause) {
		super(cause);
	}

	public SqlBuilderException(String message, Throwable cause) {
		super(message, cause);
	}

}
