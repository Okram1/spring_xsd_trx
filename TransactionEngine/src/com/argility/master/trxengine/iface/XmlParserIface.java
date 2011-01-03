package com.argility.master.trxengine.iface;

import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.master.trxengine.iface.exception.ValidationFailedException;

/**
 * 
 * @author marko.salic
 * Xml parser handles parsing from java object to xml and back again
 */
public interface XmlParserIface {

	/**
	 * Parse an xml to a TransactionInterface
	 */
	public TransactionInterface fromXml(String xml) throws ClassCastException;
	
	/**
	 * Convert a TransactionInterface to its xml representation
	 */
	public String toXml(TransactionInterface trx);
	
	/**
	 * Parse an xml to a TransactionInterface and validate against a schema
	 */
	public TransactionInterface fromXml(String xml, String schemaLocation) throws ValidationFailedException, TransactionException, ClassCastException;
	
	/**
	 * Convert a TransactionInterface to its xml representation and validate against a schema
	 */
	public String toXml(TransactionInterface trx, String schemaLocation) throws ValidationFailedException, TransactionException, ClassCastException;
	
	public Object fromXmlToPojo(String xml);
	
	public String toXml(Object trx);
	
	/**
	 * Validate an xml against a schema
	 */
	public void validate(String xml, String schemaLocation) throws ValidationFailedException, TransactionException;
	
	public void validateTrx(TransactionInterface trx) throws ValidationFailedException, TransactionException, ClassCastException;

}
