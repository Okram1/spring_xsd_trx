package com.argility.schema.context;

public class SchemaCtxFactoryImpl extends SchemaCtxFactory {

	private String cashtillSchemaLocation;
	
	protected SchemaCtxFactoryImpl() {
		super();
	}
	
	@Override
	public String getCashtillSchemaLocation() {
		return cashtillSchemaLocation;
	}

	public void setCashtillSchemaLocation(String cashtillSchemaLocation) {
		this.cashtillSchemaLocation = cashtillSchemaLocation;
	}

}
