//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.12.23 at 08:59:49 PM SAST 
//


package com.argility.schema.cashtill.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="bankableNonCc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="bankableCc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="nonBankable" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
    "bankableNonCc",
    "bankableCc",
    "nonBankable"
})
@XmlRootElement(name = "tenderSplitData01")
public class TenderSplitData01 {

    protected double bankableNonCc;
    protected double bankableCc;
    protected double nonBankable;

    /**
     * Gets the value of the bankableNonCc property.
     * 
     */
    public double getBankableNonCc() {
        return bankableNonCc;
    }

    /**
     * Sets the value of the bankableNonCc property.
     * 
     */
    public void setBankableNonCc(double value) {
        this.bankableNonCc = value;
    }

    /**
     * Gets the value of the bankableCc property.
     * 
     */
    public double getBankableCc() {
        return bankableCc;
    }

    /**
     * Sets the value of the bankableCc property.
     * 
     */
    public void setBankableCc(double value) {
        this.bankableCc = value;
    }

    /**
     * Gets the value of the nonBankable property.
     * 
     */
    public double getNonBankable() {
        return nonBankable;
    }

    /**
     * Sets the value of the nonBankable property.
     * 
     */
    public void setNonBankable(double value) {
        this.nonBankable = value;
    }

}
