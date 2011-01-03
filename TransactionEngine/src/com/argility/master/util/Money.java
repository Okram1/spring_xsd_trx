/*
 * $Id: Money.java,v 1.13 2007/01/11 15:24:33 grs Exp $
 * $Author: grs $
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
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * This class represents a monetary value.
 * 
 * @version $Revision: 1.13 $
 */
@SuppressWarnings("rawtypes")
public class Money implements java.io.Serializable, Comparable {
	private static final long serialVersionUID = -547456470136247938L;

	protected static Logger CAT = Logger.getLogger(Money.class);

	// The static fields publicly accessible
	public static final BigDecimal BG_ZERO = new BigDecimal(0);
	public static final Money ZERO = new Money(BG_ZERO);

	// The static fields for use by this class
	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
	public static final int DEFAULT_PRECISION = 2;

	protected static Locale LOCALE = Locale.getDefault();
	// mow SR26674 - Static refererence causing problems when 2 moneys being
	// used at
	// same time. Also can't make non-static as was causing out of memory.
	/*
	 * public static DecimalFormat currencyFormat =
	 * (DecimalFormat)NumberFormat.getCurrencyInstance(LOCALE); private static
	 * final DecimalFormat format = new DecimalFormat("#0.00###;-0.00###");
	 */

	// The attributes of the class
	private BigDecimal value;

	// mow SR26674
	/*
	 * static{ String pattern = currencyFormat.toPattern(); String tmp = "";
	 * if(pattern.indexOf(";")!=-1){ tmp = pattern.substring(0,
	 * pattern.indexOf(";")); tmp += "#" +
	 * pattern.substring(pattern.indexOf(";")) + "#"; pattern = tmp; }
	 * currencyFormat.applyPattern(pattern); }
	 */

	public Money() {
		// Need default constructor for web service.
	}

	public Money(BigDecimal value) {
		// PreCondition.checkNotNull(value, "value");
		// this.value = value.sucsetScale(DEFAULT_PRECISION, ROUNDING_MODE);
		this(value, DEFAULT_PRECISION);
	}

	// emc : 01/07/2003 : Allow Variable Precision If Required (eg Cost to 3
	// Decimals)
	public Money(BigDecimal value, int precision) {
		PreCondition.checkNotNull(value, "value");
		this.value = value.setScale(precision, ROUNDING_MODE);
	}

	public Money(Money value) {
		this(value.value);
	}

	/**
	 * Constructs a Money object from a double value.
	 */
	public Money(double value) {
		this(new BigDecimal(value));
	}

	public Money(double value, int precision) {
		this(new BigDecimal(value), precision);
	}

	/**
	 * Constructs a Money object from a Number value.
	 */
	public Money(Number value) {
		this(new BigDecimal(value.doubleValue()));
	}

	/**
	 * Return the absolute value of this monetary amount.
	 * 
	 * <CODE> a = abs( b ) </CODE>
	 */
	public Money abs() {
		return new Money(value.abs(), value.scale());
	}

	/**
	 * This method will add the value of this Money class to that of the one in
	 * the parameter.
	 * 
	 * Later versions of this class should support multiple currencies, and
	 * perform the necessary conversions.
	 * 
	 * <CODE> a = b + c </CODE>
	 */
	public Money add(Money value) {
		return add(value.value);
	}

	/**
	 * This method will add the value of this Money object to the value given by
	 * this Money object multiplied with the percentage given.
	 * 
	 * <CODE> a = b + c% </CODE>
	 */
	public Money add(Percent percent) {
		return add(value.multiply(percent.value));
	}

	private Money add(BigDecimal value) {
		return new Money(this.value.add(value), this.value.scale());
	}

	/**
	 * This is inverse of <CODE> add(Percent p) <CODE>
	 * 
	 * <CODE> a = b / (1+c)% </CODE>
	 */
	public Money subtract(Percent percent) {
		BigDecimal result = percent.add(new Percent(100.0)).value;
		return new Money(value.divide(result, ROUNDING_MODE), value.scale());
	}

	/**
	 * This method will multiply the value of this Money object with the
	 * percentage given by the Percent object.
	 * 
	 * <CODE> a = b * c% </CODE>
	 */
	public Money multiply(Percent percent) {
		return multiply(percent.value);
	}

	/**
	 * This method will multiply the value of this Money object with the amount
	 * given by the Numeric object.
	 * 
	 * <CODE> a = b * c </CODE>
	 */
	public Money multiply(Numeric value) {
		return multiply(value.bigDecimalValue());
	}

	/**
	 * This method will multiply the value of this Money object by an integer
	 * amount.
	 * 
	 * <CODE> a = b * c </CODE>
	 */
	public Money multiply(int amount) {
		return multiply(new BigDecimal(amount));
	}

	public Money multiply(float qty) {
		return multiply(new BigDecimal(qty));
	}

	private Money multiply(BigDecimal value) {
		return new Money(this.value.multiply(value), this.value.scale());
	}

	/**
	 * This method will multiply the value of this Money object by the exchange
	 * rate
	 * 
	 * <CODE> a = b * c% </CODE>
	 */

	public Money convCurrency(double exchangerate) {
		double ans = value.doubleValue() * exchangerate;
		return new Money(ans, value.scale());
	}

	/**
	 * Subtract the value of the other Money object passed as a parameter from
	 * this one.
	 * 
	 * <CODE> a = b - c </CODE>
	 */
	public Money subtract(Money amount) {
		return new Money(this.value.subtract(amount.value), this.value.scale());
	}

	/**
	 * Reverses the sign of this money object
	 * 
	 * <CODE> a = a * -1 </CODE>
	 */
	public Money negate() {
		return new Money(this.value.negate(), this.value.scale());
	}

	/**
	 * Divide the value of this Money object by the Numeric object passed as the
	 * other parameter.
	 * 
	 * <CODE> a = b / c </CODE>
	 */
	public Money divide(Numeric amount) {
		return divide(amount.bigDecimalValue());
	}

	/**
	 * Divide the value of this Money object by the float value passed as the
	 * other parameter.
	 * 
	 * <CODE> a = b / c </CODE>
	 */
	public Money divide(float amount) {
		return divide(new Numeric(amount).bigDecimalValue());
	}

	/**
	 * This method will multiply the value of this Money object with the
	 * percentage given by the Percent object.
	 * 
	 * <CODE> a = b / c% </CODE>
	 */
	public Money divide(Percent percent) {
		return divide(percent.value);
	}

	private Money divide(BigDecimal value) {
		return new Money(this.value.divide(value, ROUNDING_MODE),
				this.value.scale());
	}

	public Percent percentOf(Money value) {
		BigDecimal result;
		// avoid divide by Zero - SR 14506
		if (value.doubleValue() > 0) {
			result = this.value.divide(value.value, DEFAULT_PRECISION * 2,
					ROUNDING_MODE);
		} else {
			result = new BigDecimal(0);
		}
		return new Percent(result.multiply(new BigDecimal(100)));
	}

	/**
	 * Returns the minimum of this Money and val.
	 */
	public Money min(Money value) {
		return new Money(this.value.min(value.value), this.value.scale());
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Money))
			return false;
		return this.value.equals(((Money) obj).value);
	}

	/**
	 * This method will return this value as a <CODE> double </CODE>
	 */
	public double doubleValue() {
		return this.value.doubleValue();
	}

	public BigDecimal bigDecimalValue() {
		return value;
	}

	public static Money valueOf(String s) {
		/*
		 * try { Number n = format.parse(s); return new Money(n); } catch
		 * (ParseException e) { throw new
		 * IllegalArgumentException("Cannot interpret as money " + s); }
		 */
		return valueOf(s, DEFAULT_PRECISION);
	}

	// emc : SR16058 : Confirm Amounts On Tran = Document Total
	public static synchronized Money valueOf(String s, int precision) {
		try {
			// mow SR26674
			// Number n = format.parse(s);
			Number n = new DecimalFormat("#0.00###;-0.00###").parse(s);
			return new Money(new BigDecimal(n.doubleValue()), precision);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Cannot interpret as money " + s);
		}
	}

	public static Money valueOfMinorCurrencyUnits(String strVal)
			throws IllegalArgumentException {
		int digitFraction = NumberFormat.getInstance().getCurrency()
				.getDefaultFractionDigits();
		return valueOf(strVal, digitFraction);
	}

	/**
	 * This method will return a String of the form:- <CODE> x.xx(-) </CODE>
	 */
	public String toString() {
		// mow SR26674
		// return format.format(value);
		return new DecimalFormat("#0.00###;-0.00###").format(value);
	}

	public String toCurrencyString() {
		// mow SR26674
		// return currencyFormat.format(value);
		return getCurrencyFormat().format(value);
	}

	public String getValue() {
		// mow SR26674
		// return format.format(value);
		return new DecimalFormat("#0.00###;-0.00###").format(value);
	}

	public void setValue(String strValue) {
		this.value = Money.valueOf(strValue).bigDecimalValue();
	}

	/**
	 * Compares this object with the specified object for order. Returns
	 * respectively a negative integer, zero, or a positive integer as this
	 * object is less than, equal to, or greater than the specified object.
	 */
	public int compareTo(Object obj) {
		if (obj == null)
			return -1;
		Money m = (Money) obj;
		return value.compareTo(m.value);
	}

	/**
	 * Compare this Money object with the one being passed in to determine which
	 * is the greater. Compares this object with the specified object for order.
	 * Returns respectively a negative integer, zero, or a positive integer as
	 * this object is less than, equal to, or greater than the specified object.
	 */
	public int compareTo(Money other) {
		return this.value.compareTo(other.value);
	}

	public BigInteger unscaledValue() {
		return value.unscaledValue();
	}

	public int scale() {
		return value.scale();
	}

	public void setBaseUnits(int intVal) {
		// Needed only method for bean structure.
	}

	// Return base units ie cents / pennies
	public int getBaseUnits() {
		// return new BigDecimal(this.value.doubleValue())
		// .multiply(new BigDecimal(100)).intValue();
		return this.value.multiply(new BigDecimal(100)).intValue();
	}

	/**
	 * Return minor currency units ie cents / pennies. Note: Essentially the
	 * same as getBaseUnits(), however here it assumes nothing about the base
	 * unit, rather work from the LOCAL ISO 4217 specific info about the
	 * different locales.
	 */
	public int getMinorCurrencyUnits() {
		int digitFraction = NumberFormat.getInstance().getCurrency()
				.getDefaultFractionDigits();
		if (digitFraction < 1) {
			return this.value.intValue();
		}

		return this.value.multiply(
				new BigDecimal(Math.round(Math.pow(10, digitFraction))))
				.intValue();
	}

	public void setMinorCurrencyUnits(int intVal) {
		// Needed only method for bean structure.
	}

	public static Money correctSign(Money amount, boolean isCredit,
			boolean reverseSign) {
		amount = correctSign(amount, isCredit);
		if (reverseSign) {
			amount = amount.multiply(-1);
		}
		return amount;
	}

	public static Money correctSign(Money amount, boolean isCredit) {
		amount = amount.abs();
		if (isCredit) {
			amount = amount.multiply(-1);
		}
		return amount;
	}

	public static double getCurrency(String str) {
		CAT.debug("returned: " + str);
		double value;
		// When we try and get the double value from a string
		// we assume that it is in the locale currency format.
		// There are some cases where the string is not in a
		// currency format but does have a valid value to return.
		// When this occurs we will try to parse the string to a double
		// if this fails we return 0.
		try {
			// mow SR26674
			value = getCurrencyFormat().parse(str).doubleValue();
		} catch (ParseException ex) {
			try {
				value = Double.parseDouble(str);
			} catch (NumberFormatException e) {
				value = 0;
			}
		}
		return value;
	}

	public static double getCurrency(String str, boolean isForeign) {
		CAT.debug("returned: " + str);
		double value;
		StringBuffer buf = null;
		if (isForeign) {
			buf = new StringBuffer(str);
			while (buf.indexOf(",") > 0) {
				int i = buf.indexOf(",");
				buf.deleteCharAt(i);
			}

			str = buf.toString();
		}
		// When we try and get the double value from a string
		// we assume that it is in the locale currency format.
		// There are some cases where the string is not in a
		// currency format but does have a valid value to return.
		// When this occurs we will try to parse the string to a double
		// if this fails we return 0.
		try {
			// mow SR26674
			// value = currencyFormat.parse(str).doubleValue();
			value = getCurrencyFormat().parse(str).doubleValue();
		} catch (ParseException ex) {
			try {
				value = Double.parseDouble(str);
			} catch (NumberFormatException e) {
				value = 0;
			}
		}
		return value;
	}

	public static void setCurrency(double doubleVal) {
		// Needed only method for bean structure.
	}

	// return without the Locale
	public static String formatDouble(double d) {
		// mow SR26674
		// String temp = format.format(d);
		String temp = new DecimalFormat("#0.00###;-0.00###").format(d);
		if (temp.startsWith("BWP"))
			return temp.substring(2);
		else
			return temp.substring(0);
	}

	public static DecimalFormat getCurrencyFormat() {
		DecimalFormat currencyFormat = (DecimalFormat) NumberFormat
				.getCurrencyInstance(LOCALE);
		String pattern = currencyFormat.toPattern();
		String tmp = "";
		if (pattern.indexOf(";") != -1) {
			tmp = pattern.substring(0, pattern.indexOf(";"));
			tmp += "#" + pattern.substring(pattern.indexOf(";")) + "#";
			pattern = tmp;
		}
		currencyFormat.applyPattern(pattern);
		return currencyFormat;
	}
}
