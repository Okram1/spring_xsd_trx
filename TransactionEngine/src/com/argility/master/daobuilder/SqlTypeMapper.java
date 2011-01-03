package com.argility.master.daobuilder;

import java.util.HashMap;
import java.util.Map;

public class SqlTypeMapper {

	private static Map<String, String> map = new HashMap<String, String>();
	private static SqlTypeMapper instance = null;
	
	public static SqlTypeMapper getInstance() {
		if (instance == null) {
			instance = new SqlTypeMapper();
			instance.initMap();
		}
		
		return instance;
	}
	
	private void initMap() {
		map.put("int4", "Integer");
		map.put("text", "String");
		map.put("timestamp", "Timestamp");
		map.put("serial", "Long");
		map.put("float8", "Double");
		map.put("numeric", "Double");
		map.put("int8", "Long");
		map.put("bool", "Boolean");
	}
	
	public String getJavaType(String sqlType) throws Exception {
		Object type = map.get(sqlType);
		if (type == null) {
			throw new Exception("Unknown type " + sqlType + " please add the type to the SqlTypeMapper");
		}
		
		return (String)type;
	}
}
