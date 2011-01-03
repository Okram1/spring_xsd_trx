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
 *         &lt;element ref="{}allocationData01"/>
 *         &lt;element ref="{}tenderData01" maxOccurs="unbounded"/>
 *         &lt;element ref="{}payRegData01" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="initials" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="policyNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{}accountData01" minOccurs="0"/>
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
    "allocationData01",
    "tenderData01",
    "payRegData01",
    "comment",
    "title",
    "initials",
    "surname",
    "id",
    "policyNo",
    "accountData01"
})
@XmlRootElement(name = "sundryReceiptV01")
public class SundryReceiptV01 {

    @XmlElement(required = true)
    protected AllocationData01 allocationData01;
    @XmlElement(required = true)
    protected List<TenderData01> tenderData01;
    protected List<PayRegData01> payRegData01;
    protected String comment;
    protected String title;
    protected String initials;
    protected String surname;
    protected String id;
    protected String policyNo;
    protected AccountData01 accountData01;

    /**
     * Gets the value of the allocationData01 property.
     * 
     * @return
     *     possible object is
     *     {@link AllocationData01 }
     *     
     */
    public AllocationData01 getAllocationData01() {
        return allocationData01;
    }

    /**
     * Sets the value of the allocationData01 property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllocationData01 }
     *     
     */
    public void setAllocationData01(AllocationData01 value) {
        this.allocationData01 = value;
    }

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
     * Gets the value of the payRegData01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the payRegData01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPayRegData01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PayRegData01 }
     * 
     * 
     */
    public List<PayRegData01> getPayRegData01() {
        if (payRegData01 == null) {
            payRegData01 = new ArrayList<PayRegData01>();
        }
        return this.payRegData01;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the initials property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitials() {
        return initials;
    }

    /**
     * Sets the value of the initials property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitials(String value) {
        this.initials = value;
    }

    /**
     * Gets the value of the surname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the value of the surname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurname(String value) {
        this.surname = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the policyNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyNo() {
        return policyNo;
    }

    /**
     * Sets the value of the policyNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyNo(String value) {
        this.policyNo = value;
    }

    /**
     * Gets the value of the accountData01 property.
     * 
     * @return
     *     possible object is
     *     {@link AccountData01 }
     *     
     */
    public AccountData01 getAccountData01() {
        return accountData01;
    }

    /**
     * Sets the value of the accountData01 property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountData01 }
     *     
     */
    public void setAccountData01(AccountData01 value) {
        this.accountData01 = value;
    }

}