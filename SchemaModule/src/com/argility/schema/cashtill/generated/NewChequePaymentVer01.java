//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.12.23 at 08:59:49 PM SAST 
//


package com.argility.schema.cashtill.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &lt;element ref="{}chequeData01"/>
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
    "chequeData01"
})
@XmlRootElement(name = "newChequePaymentVer01")
public class NewChequePaymentVer01 {

    @XmlElement(required = true)
    protected ChequeData01 chequeData01;

    /**
     * Gets the value of the chequeData01 property.
     * 
     * @return
     *     possible object is
     *     {@link ChequeData01 }
     *     
     */
    public ChequeData01 getChequeData01() {
        return chequeData01;
    }

    /**
     * Sets the value of the chequeData01 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChequeData01 }
     *     
     */
    public void setChequeData01(ChequeData01 value) {
        this.chequeData01 = value;
    }

}
