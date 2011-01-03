package com.argility.master.trxengine;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.argility.master.trxengine.iface.TransactionInterface;
import com.argility.master.trxengine.iface.XmlParserIface;
import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.master.trxengine.iface.exception.ValidationFailedException;

public class XmlParserJaxbImpl implements XmlParserIface {

	@Override
	public TransactionInterface fromXml(String xml) throws ClassCastException {
		JAXBContext context;
		TransactionInterface ti = null;
		try {
			context = getContext(this.getClass());
			Unmarshaller um = context.createUnmarshaller();
			ByteArrayInputStream bs = new ByteArrayInputStream(xml.getBytes());
			ti = (TransactionInterface) um.unmarshal(bs);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return ti;
	}

	@Override
	public String toXml(TransactionInterface trx) {
		JAXBContext context;
		StringWriter sw = new StringWriter();
		String output = null;
		try {
			context = getContext("com.argility.test.trxengine");
			Marshaller m = context.createMarshaller();
			m.marshal(trx, sw);
			output = sw.toString();
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return output;
	}

	@Override
	public TransactionInterface fromXml(String xml, String schemaLocation)
			throws ValidationFailedException, TransactionException, ClassCastException {

		return null;
	}

	@Override
	public String toXml(TransactionInterface trx, String schemaLocation)
			throws ValidationFailedException, TransactionException, ClassCastException {

		return null;
	}

	@Override
	public Object fromXmlToPojo(String xml) {

		return null;
	}

	@Override
	public String toXml(Object trx) {

		return null;
	}

	@Override
	public void validate(String xml, String schemaLocation)
			throws ValidationFailedException, TransactionException {


	}
	
	private JAXBContext getContext(Class<?> c) throws JAXBException {
		JAXBContext context = JAXBContext
			.newInstance(c);
		return context;
	}
	
	private JAXBContext getContext(String s) throws JAXBException {
		JAXBContext context = JAXBContext
			.newInstance(s);
		return context;
	}

	@Override
	public void validateTrx(TransactionInterface trx)
			throws ValidationFailedException, TransactionException,
			ClassCastException {
		
	}

}
