package com.argility.master.trxengine;

import java.io.ByteArrayInputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.argility.master.trxengine.iface.TransactionInterface;
import com.argility.master.trxengine.iface.XmlParserIface;
import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.master.trxengine.iface.exception.ValidationFailedException;
import com.thoughtworks.xstream.XStream;

public class XmlParserXstreamImpl implements XmlParserIface {

	protected transient Logger log = Logger
			.getLogger(this.getClass().getName());

	private XStream xs = new XStream();

	@Override
	public TransactionInterface fromXml(String xml) throws ClassCastException {
		if (xml == null) {
			return null;
		}
		TransactionInterface ti = (TransactionInterface) xs.fromXML(xml);
		return ti;
	}

	@Override
	public String toXml(TransactionInterface trx) {
		if (trx == null) {
			return null;
		}
		return xs.toXML(trx);
	}

	@Override
	public TransactionInterface fromXml(String xml, String schemaLocation)
			throws ValidationFailedException, TransactionException,
			ClassCastException {
		validate(xml, schemaLocation);
		return fromXml(xml);
	}

	@Override
	public String toXml(TransactionInterface trx, String schemaLocation)
			throws ValidationFailedException, TransactionException,
			ClassCastException {
		String xml = toXml(trx);
		validate(xml, schemaLocation);
		return xml;
	}

	@Override
	public Object fromXmlToPojo(String xml) {
		if (xml == null) {
			return null;
		}
		return xs.fromXML(xml);
	}

	@Override
	public String toXml(Object obj) {
		if (obj == null) {
			return null;
		}
		return xs.toXML(obj);
	}

	public void validate(String xml, String schemaLocation)
			throws ValidationFailedException, TransactionException {
		log.info("Running schema validation using schema '" + schemaLocation
				+ "'");
		SchemaFactory sf = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema mySchema = null;

		ByteArrayInputStream bs = new ByteArrayInputStream(xml.getBytes());
		URL schemaLocationURL = null;
		DocumentBuilder parser = null;
		Document document = null;

		try {
			if (schemaLocation == null) {
				throw new ValidationFailedException("Invalid schema location '"
						+ schemaLocation + "'");
			}
			parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = parser.parse(bs);
			schemaLocationURL = getClass().getClassLoader().getResource(
					schemaLocation);
			mySchema = sf.newSchema(schemaLocationURL);
			Validator validator = mySchema.newValidator();
			validator.validate(new DOMSource(document));
		} catch (SAXException e) {
			e.printStackTrace();
			log.error("INVALID XML IS: \n " + xml);
			throw new ValidationFailedException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new TransactionException(e.getMessage());
		}

		log.info("Schema validation PASSED");

	}

	public void validateTrx(TransactionInterface trx)
			throws ValidationFailedException, TransactionException,
			ClassCastException {
		String xml = toXml(trx);
		validate(xml, trx.getSchemaLocation());
	}

}
