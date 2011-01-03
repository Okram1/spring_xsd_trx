package com.argility.master.trxengine.iface;

import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.master.trxengine.iface.exception.ValidationFailedException;

/**
 * 
 * @author marko.salic This services follows a commendPattern approach and is
 *         used to execute transaction logic
 */
public interface TransactionService {

	public TransactionInterface executeTransaction(String xml)
			throws TransactionException, ValidationFailedException;

	public TransactionInterface executeTransaction(TransactionInterface trx)
			throws TransactionException, ValidationFailedException;

	public XmlParserIface getXmlParser();

}
