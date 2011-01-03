/*
 * $Id: Numeric.java,v 1.1.1.1 2004/12/21 08:59:05 devbuild Exp $
 * $Author: devbuild $
 *
 * Copyright 1999-2000 by UCS Group (Pty)Ltd.
 * 209 Smit Str., Braamfontein, Gauteng, 2001, South Africa
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of UCS Group (Pty)Ltd. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with UCS.
 */
package com.argility.master.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * This class represents a numeric value.
 * 
 * @version $Revision: 1.1.1.1 $
 */
@SuppressWarnings("rawtypes")
public class Numeric implements java.io.Serializable, Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Numeric ZERO = new Numeric(new BigDecimal(0));
	public static final Numeric ONE = new Numeric(new BigDecimal(1));

	public static final DecimalFormat format = new DecimalFormat("#0.00");

	private BigDecimal value;

	public Numeric(BigInteger unscaledVal, int scale) {
		this.value = new BigDecimal(unscaledVal, scale);
	}

	public Numeric(double value) {
		this.value = new BigDecimal(value);
	}

	public Numeric(BigDecimal amount) {
		this.value = amount;
	}

	public Numeric(Number amount) {
		this(amount.doubleValue());
	}

	/**
	 * This method will add the value of this Numeric class to that of the one
	 * in the parameter.
	 * 
	 * Later versions of this class should support multiple currencies, and
	 * perform the necessary conversions.
	 * 
	 * <CODE> a = b + c </CODE>
	 */
	public Numeric add(Numeric amount) {
		BigDecimal result = this.value.add(amount.value);

		return new Numeric(result);
	}

	/**
	 * This method will multiply the value of this Money object with the amount
	 * given by the Money parameter.
	 * 
	 * <CODE> a = b * c </CODE>
	 */
	public Numeric multiply(Numeric amount) {
		return new Numeric(this.value.multiply(amount.value));
	}

	/**
	 * Compare this Numaric object with the one being passed in to determine
	 * which is the greater.
	 */
	public int compareTo(Object other) {
		return this.value.compareTo(((Numeric) other).value);
	}

	/**
	 * Subtract the value of the other Money object passed as a parameter from
	 * this one.
	 * 
	 * <CODE> a = b - c </CODE>
	 */
	public Numeric subtract(Numeric amount) {
		return new Numeric(this.value.subtract(amount.value));
	}

	/**
	 * Reverses the sign of this money object
	 * 
	 * <CODE> a = a * -1 </CODE>
	 */
	public Numeric negate() {
		return new Numeric(this.value.negate());
	}

	/**
	 * Divide the value of this Money object by the Numeric object passed as the
	 * other parameter.
	 * 
	 * <CODE> a = b / c </CODE>
	 */
	public Numeric divide(Numeric amount, int roundingMode) {
		return new Numeric(this.value.divide(amount.value, roundingMode));
	}

	/**
	 * Returns the minimum of this Money and val.
	 */
	public Numeric min(Numeric val) {
		return new Numeric(this.value.min(val.value));
	}

	/**
	 * This method will return this value as a <CODE> double </CODE>
	 */
	public double doubleValue() {
		return this.value.doubleValue();
	}

	public static Numeric valueOf(String s) {
		Numeric nm = null;

		try {
			Number n = format.parse(s);
			nm = new Numeric(n);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Cannot interpret as Numeric "
					+ s);
		}
		return nm;
	}

	/**
	 * This method will return a String of the form:- <CODE> (-)x.xx </CODE>
	 */
	public String toString() {
		return format.format(value);
	}

	public BigDecimal bigDecimalValue() {
		return value;
	}

	public BigInteger unscaledValue() {
		return value.unscaledValue();
	}

	public int scale() {
		return value.scale();
	}

	public boolean equals(Object obj) {
		if (obj instanceof Numeric) {
			return value.equals(((Numeric) obj).value);
		}
		return false;
	}

}
