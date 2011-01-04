package com.argility.master.trxengine.iface;

import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.master.trxengine.iface.exception.ValidationFailedException;

/**
 * 
 * This services follows a command pattern approach and is used to execute transaction logic for 
 * all transaction interface implementaions
 */
public interface TransactionService {

	public TransactionInterface executeTransaction(String xml)
			throws TransactionException, ValidationFailedException;

	public TransactionInterface executeTransaction(TransactionInterface trx)
			throws TransactionException, ValidationFailedException;

	public XmlParserIface getXmlParser();

}
