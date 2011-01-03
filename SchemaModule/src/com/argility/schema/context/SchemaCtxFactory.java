package com.argility.schema.context;

import org.apache.log4j.Logger;

import com.argility.master.context.SpringContextFactory;

public abstract class SchemaCtxFactory {
	static Logger log = Logger.getLogger(SchemaCtxFactory.class.getName());
	
	private static String CONTEXT_NAME = "schemaCtxFactory";
	private static SchemaCtxFactory instance;

	public static SchemaCtxFactory getInstance() {
		if (instance == null) {
			log.info("Creating a new instance of SchemaCtxFactory");
			instance = SpringContextFactory.getApplicationContext().getBean(
					CONTEXT_NAME, SchemaCtxFactory.class);
		}
		
		return instance;
	}
	
	public abstract String getCashtillSchemaLocation();

}
