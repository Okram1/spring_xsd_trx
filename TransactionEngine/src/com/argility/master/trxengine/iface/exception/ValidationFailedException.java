package com.argility.master.trxengine.iface.exception;

import com.argility.master.context.MasterCtxFactory;
import com.argility.master.trxengine.iface.TransactionInterface;

public class ValidationFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 111L;

	public ValidationFailedException() {
	}

	public ValidationFailedException(String message) {
		super(message);
	}

	public ValidationFailedException(Throwable cause) {
		super(cause);
	}

	public ValidationFailedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ValidationFailedException(String type, Exception e, TransactionInterface trx) {
		super(e.getMessage());
		
		MasterCtxFactory.getInstance().getTrxLogger().logTransactionError(type, e, trx);
	}

}
