//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.12.23 at 08:52:14 PM SAST 
//


package com.argility.master.trxengine.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oboBrCde" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oboAudId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "oboBrCde",
    "oboAudId"
})
@XmlRootElement(name = "oboInData01")
public class OboInData01 {

    @XmlElement(required = true)
    protected String oboBrCde;
    @XmlElement(required = true, type = String.class)
    @XmlSchemaType(name = "integer")
    protected Integer oboAudId;

    /**
     * Gets the value of the oboBrCde property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOboBrCde() {
        return oboBrCde;
    }

    /**
     * Sets the value of the oboBrCde property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOboBrCde(String value) {
        this.oboBrCde = value;
    }

    /**
     * Gets the value of the oboAudId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Integer getOboAudId() {
        return oboAudId;
    }

    /**
     * Sets the value of the oboAudId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOboAudId(Integer value) {
        this.oboAudId = value;
    }

}
