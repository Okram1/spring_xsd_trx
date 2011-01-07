package com.argility.master.trxengine.iface;

import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.master.trxengine.iface.exception.ValidationFailedException;

/**
 * 
 * @author marko.salic
 * Xml parser handles parsing from java object to xml and back again as well as schema validation
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
	
	public String toXml(Object o);
	
	/**
	 * Validate an xml against a schema
	 */
	public void validate(String xml, String schemaLocation) throws ValidationFailedException, TransactionException;
	
	/**
	 * Validate the transaction against it's schema, the schema will be retrieved using getSchemaLocation method on 
	 * the TransactionInterface and MUST be set
	 */
	public void validateTrx(TransactionInterface trx) throws ValidationFailedException, TransactionException, ClassCastException;

}
