/*
 * $Id: Percent.java,v 1.1.1.1 2004/12/21 08:59:05 devbuild Exp $
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
 * This interface represents a percentage value.
 * 
 * @version $Revision: 1.1.1.1 $
 */
public class Percent implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6628542034123425522L;

	public static final Percent ZERO = new Percent(new BigDecimal(0));

	public static final int DEFAULT_PRECISION = 4;
	public static final int DISPLAY_PRECISION = 2;

	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

	public static final DecimalFormat format = new DecimalFormat("%#0.00");
	BigDecimal value;

	// emc : Changed To "private" used internally only ?
	// Assume flag is to get different method "signature"
	// emc : Changed to use old "flag" as prevents natural sign i.e can't negate
	private Percent(BigDecimal value, boolean allowSign) {
		if (!allowSign) {
			if (value.compareTo(new BigDecimal(0)) < 0) {
				value = value.negate();
			}
		}
		this.value = value;
	}

	public Percent() {
		// Need default constructor for web service.
	}

	public Percent(BigDecimal percent) {
		if (percent.compareTo(new BigDecimal(0)) < 0) {
			percent = percent.negate();
		}
		this.value = percent.divide(new BigDecimal(100), DEFAULT_PRECISION,
				ROUNDING_MODE);
	}

	public Percent(double percent) {
		this(new BigDecimal(percent));
	}

	public Percent(Percent percent) {
		this.value = percent.bigDecimalValue();
	}

	public double doubleValue() {
		return this.value.multiply(new BigDecimal(100))
				.setScale(DISPLAY_PRECISION, ROUNDING_MODE).doubleValue();
	}

	public BigDecimal bigDecimalValue() {
		return this.value.multiply(new BigDecimal(100)).setScale(
				DISPLAY_PRECISION, ROUNDING_MODE);
	}

	public static Percent valueOf(String s) {
		Percent p = null;
		try {
			Number n = format.parse(s);
			p = new Percent(new BigDecimal(n.doubleValue()), true);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Cannot interpret as percent "
					+ s);
		}

		return p;
	}

	public static Percent factorOf(double a, double b) {
		return new Percent(new BigDecimal((a - b) / a), true);
	}

	/**
	 * This method will return a String of the form:- <CODE> x.xx </CODE>
	 */
	public String toString() {
		return format.format(value);
	}

	public String toStringFormat(DecimalFormat format2) {
		return format2.format(value);
	}

	/**
	 * This method will add the value of this Percent class to that of the one
	 * in the parameter.
	 * 
	 * Later versions of this class should support multiple currencies, and
	 * perform the necessary conversions.
	 * 
	 * <CODE> a = b + c </CODE>
	 */
	public Percent add(Percent amount) {
		BigDecimal result = this.value.add(amount.value);
		return new Percent(result, true);
	}

	/**
	 * This method will multiply the value of this Percent object with the
	 * amount given by the Numeric parameter.
	 * 
	 * <CODE> a = b * c </CODE>
	 */
	public Percent multiply(Numeric amount) {
		return new Percent(this.value.multiply(amount.bigDecimalValue()), true);
	}

	/**
	 * Compare this Percent object with the one being passed in to determine
	 * which is the greater.
	 */
	public int compareTo(Percent other) {
		return this.value.compareTo(other.value);
	}

	/**
	 * Subtract the value of the other Money object passed as a parameter from
	 * this one.
	 * 
	 * <CODE> a = b - c </CODE>
	 */
	public Percent subtract(Percent amount) {
		return new Percent(this.value.subtract(amount.value), true);
	}

	/**
	 * Reverses the sign of this money object
	 * 
	 * <CODE> a = a * -1 </CODE>
	 */
	public Percent negate() {
		return new Percent(this.value.negate(), true);
	}

	public BigInteger unscaledValue() {
		return value.unscaledValue();
	}

	public int scale() {
		return value.scale();
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Percent))
			return false;
		return value.equals(((Percent) obj).value);
	}

	public String getValue() {
		return format.format(value);
	}

	public void setValue(String strValue) {
		this.value = valueOf(strValue).bigDecimalValue();
	}

}
