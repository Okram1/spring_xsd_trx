package com.argility.master.trxengine.iface;

import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.master.trxengine.iface.exception.ValidationFailedException;

/**
 * 
 * This services follows a command pattern approach and is used to execute transaction logic for 
 * all transaction interface implementations
 */
public interface TransactionService {

	/**
	 * 
	 * @param xml
	 * 		Xml representation of the transaction you are executing, you can use the getXmlParser method 
	 * 		to convert you TransactionInterface into an xml representation
	 * @return
	 * 		Return the executed TransactionInterface
	 * @throws TransactionException
	 * @throws ValidationFailedException
	 */
	public TransactionInterface executeTransaction(String xml)
			throws TransactionException, ValidationFailedException;

	/**
	 * 
	 * @param trx
	 * 		The TransactionInterface you wish to execute
	 * @return
	 * 		Return the executed TransactionInterface
	 * @throws TransactionException
	 * @throws ValidationFailedException
	 */
	public TransactionInterface executeTransaction(TransactionInterface trx)
			throws TransactionException, ValidationFailedException;

	public XmlParserIface getXmlParser();

}
