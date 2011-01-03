//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.12.23 at 08:52:14 PM SAST 
//

package com.argility.master.trxengine.header;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.argility.master.dao.ReplicationSession;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}uiD01"/>
 *         &lt;element ref="{}transData01"/>
 *         &lt;element ref="{}seqD01" minOccurs="0"/>
 *         &lt;element ref="{}refToInfo01" minOccurs="0"/>
 *         &lt;element ref="{}docValueData01" minOccurs="0"/>
 *         &lt;element ref="{}oboInData01" minOccurs="0"/>
 *         &lt;element ref="{}oboOutData01" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "uiD01", "transData01", "seqD01",
		"refToInfo01", "docValueData01", "oboInData01", "oboOutData01" })
@XmlRootElement(name = "actH01")
public class ActH01 implements ReplicationSession {

	@XmlElement(required = true)
	protected UiD01 uiD01;
	@XmlElement(required = true)
	protected TransData01 transData01;
	protected SeqD01 seqD01;
	protected RefToInfo01 refToInfo01;
	protected DocValueData01 docValueData01;
	protected OboInData01 oboInData01;
	protected OboOutData01 oboOutData01;

	private transient List<String> sqlStatements = new ArrayList<String>();

	/**
	 * Gets the value of the uiD01 property.
	 * 
	 * @return possible object is {@link UiD01 }
	 * 
	 */
	public UiD01 getUiD01() {
		return uiD01;
	}

	/**
	 * Sets the value of the uiD01 property.
	 * 
	 * @param value
	 *            allowed object is {@link UiD01 }
	 * 
	 */
	public void setUiD01(UiD01 value) {
		this.uiD01 = value;
	}

	/**
	 * Gets the value of the transData01 property.
	 * 
	 * @return possible object is {@link TransData01 }
	 * 
	 */
	public TransData01 getTransData01() {
		return transData01;
	}

	/**
	 * Sets the value of the transData01 property.
	 * 
	 * @param value
	 *            allowed object is {@link TransData01 }
	 * 
	 */
	public void setTransData01(TransData01 value) {
		this.transData01 = value;
	}

	/**
	 * Gets the value of the seqD01 property.
	 * 
	 * @return possible object is {@link SeqD01 }
	 * 
	 */
	public SeqD01 getSeqD01() {
		return seqD01;
	}

	/**
	 * Sets the value of the seqD01 property.
	 * 
	 * @param value
	 *            allowed object is {@link SeqD01 }
	 * 
	 */
	public void setSeqD01(SeqD01 value) {
		this.seqD01 = value;
	}

	/**
	 * Gets the value of the refToInfo01 property.
	 * 
	 * @return possible object is {@link RefToInfo01 }
	 * 
	 */
	public RefToInfo01 getRefToInfo01() {
		return refToInfo01;
	}

	/**
	 * Sets the value of the refToInfo01 property.
	 * 
	 * @param value
	 *            allowed object is {@link RefToInfo01 }
	 * 
	 */
	public void setRefToInfo01(RefToInfo01 value) {
		this.refToInfo01 = value;
	}

	/**
	 * Gets the value of the docValueData01 property.
	 * 
	 * @return possible object is {@link DocValueData01 }
	 * 
	 */
	public DocValueData01 getDocValueData01() {
		return docValueData01;
	}

	/**
	 * Sets the value of the docValueData01 property.
	 * 
	 * @param value
	 *            allowed object is {@link DocValueData01 }
	 * 
	 */
	public void setDocValueData01(DocValueData01 value) {
		this.docValueData01 = value;
	}

	/**
	 * Gets the value of the oboInData01 property.
	 * 
	 * @return possible object is {@link OboInData01 }
	 * 
	 */
	public OboInData01 getOboInData01() {
		return oboInData01;
	}

	/**
	 * Sets the value of the oboInData01 property.
	 * 
	 * @param value
	 *            allowed object is {@link OboInData01 }
	 * 
	 */
	public void setOboInData01(OboInData01 value) {
		this.oboInData01 = value;
	}

	/**
	 * Gets the value of the oboOutData01 property.
	 * 
	 * @return possible object is {@link OboOutData01 }
	 * 
	 */
	public OboOutData01 getOboOutData01() {
		return oboOutData01;
	}

	/**
	 * Sets the value of the oboOutData01 property.
	 * 
	 * @param value
	 *            allowed object is {@link OboOutData01 }
	 * 
	 */
	public void setOboOutData01(OboOutData01 value) {
		this.oboOutData01 = value;
	}

	public List<String> getSqlStatements() {
		return sqlStatements;
	}

	// NO setter! this is to apply a rule that once a replication session is
	// started and running you can only add and not change what executed already
	public boolean addSqlStatement(String sql) {
		if (sqlStatements == null) {
			sqlStatements = new ArrayList<String>();
		}

		return sqlStatements.add(sql);
	}

}