package com.argility.schema.context;

import org.apache.log4j.Logger;

import com.argility.master.context.SpringContextFactory;

/**
 * Factory for the schemas/xsd's
 * @author marko.salic
 *
 */
public abstract class SchemaCtxFactory {
	static Logger log = Logger.getLogger(SchemaCtxFactory.class.getName());
	
	// This is the spring context name used to deploy this factory
	private static String CONTEXT_NAME = "schemaCtxFactory";
	private static SchemaCtxFactory instance;

	// Returns a singleton instance of the schema factory implementation
	public static SchemaCtxFactory getInstance() {
		if (instance == null) {
			log.info("Creating a new instance of SchemaCtxFactory");
			instance = SpringContextFactory.getApplicationContext().getBean(
					CONTEXT_NAME, SchemaCtxFactory.class);
		}
		
		return instance;
	}
	
	/**
	 * Where can we find the cashtill module schema
	 * @return
	 * 		Classpath location of the schema
	 */
	
	public abstract String getCashtillSchemaLocation();

}
