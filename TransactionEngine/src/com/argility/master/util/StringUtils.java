package com.argility.master.util;

public class StringUtils {

	public static String getParamName(String columnName) {
		return toCamelCase(columnName);
	}
	
	public static String toCamelCase(String s) {
		if (s == null) return null;
		
		String[] parts = s.split("_");
		if (parts.length == 0) return s;
		
		String camelCaseString = "";
		boolean first = true;
		for (String part : parts) {
			if (first) {
				camelCaseString += part;
			} else {
				camelCaseString = camelCaseString + toProperCase(part);
			}
			first = false;
		}
		return camelCaseString;
	}

	public static String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
}
