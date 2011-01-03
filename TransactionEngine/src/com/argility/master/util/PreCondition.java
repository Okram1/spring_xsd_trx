/*
 * $Id: PreCondition.java,v 1.1.1.1 2004/12/21 08:59:05 devbuild Exp $
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

/**
 * This class is designed primarily to provide pre-condition checking to
 * package-access classes implementing public interfaces. The liberal use of
 * this class should help to ensure that client-side programming errors are
 * minimized through early exceptions.<br><br>
 *
 * As this class tests the incoming parameters to a method, it will throw a
 * runtime exception to mark a failure to comply with the given interface
 * contracts.
 *
 * @version $Revision: 1.1.1.1 $
 */
public abstract
class PreCondition {
   //The messages are kept as static to keep performance good, otherwise, we
   //would be creating the same string on every failure.
   /** The message that is appended to the fieldName. */
   private static final String      nullMessage =
         " cannot be null. Please check the documentation for proper usage.";

   /**
    * This checks to see if the given object is null, if it is, an exception
    * is thrown.
    *
    * @param obj the object to check to see if it is null.
    * @param fieldName the name of the field that should be checked. This does
    *    not enforce non-null characteristics, although as a matter of good
    *    style, the programmer should not pass null.
    * @throws NullPointerException  if the Object argument is null.
    */
   public static void checkNotNull( Object obj, String fieldName ) {
      if( obj == null ) {
         throw new NullPointerException( fieldName + nullMessage );
      }
   }

   /**
    * This checks to see if the given value is inside the given range of
    * values. The range is inclusive of both the minimum and maximum values.
    * Mathematically:- x is element of [minValue:maxValue]
    *
    * @param value the value to be checked for inclusion in the range.
    * @param fieldName the name of the field that should be checked. This does
    *    not enforce non-null characteristics, although as a matter of good
    *    style, the programmer should not pass null.
    * @param minValue the inclusive minimum value of the range.
    * @param maxValue the inclusive maximum value of the range.
    * @throws IllegalArgumentException if the value is out of range.
    */
   public static void checkRange(
         int value, String fieldName, int minValue, int maxValue ) {
      if( value < minValue || value > maxValue ) {
         throw new IllegalArgumentException( "The value given by " + fieldName
               + '(' + value + ") is not in the range [" + minValue + ':'
               + maxValue + "]. Please refer to the documentation for proper"
               + " usage." );
      }
   }
}
