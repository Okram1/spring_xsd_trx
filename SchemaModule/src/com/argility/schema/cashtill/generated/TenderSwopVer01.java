//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.12.23 at 08:59:49 PM SAST 
//


package com.argility.schema.cashtill.generated;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{}tenderData01" maxOccurs="2"/>
 *         &lt;element ref="{}tenderSplitData01" minOccurs="0"/>
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
    "tenderData01",
    "tenderSplitData01"
})
@XmlRootElement(name = "tenderSwopVer01")
public class TenderSwopVer01 {

    @XmlElement(required = true)
    protected List<TenderData01> tenderData01;
    protected TenderSplitData01 tenderSplitData01;

    /**
     * Gets the value of the tenderData01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tenderData01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTenderData01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TenderData01 }
     * 
     * 
     */
    public List<TenderData01> getTenderData01() {
        if (tenderData01 == null) {
            tenderData01 = new ArrayList<TenderData01>();
        }
        return this.tenderData01;
    }

    /**
     * Gets the value of the tenderSplitData01 property.
     * 
     * @return
     *     possible object is
     *     {@link TenderSplitData01 }
     *     
     */
    public TenderSplitData01 getTenderSplitData01() {
        return tenderSplitData01;
    }

    /**
     * Sets the value of the tenderSplitData01 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TenderSplitData01 }
     *     
     */
    public void setTenderSplitData01(TenderSplitData01 value) {
        this.tenderSplitData01 = value;
    }

}
