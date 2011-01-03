package com.argility.master.daobuilder;

public class CBVariable {

	private String name;
	private String sqlName;
	private String type;
	private String suffix;
	private String defaultValue = "null";
	private boolean isStringArray;
	private boolean isStatic;
	private boolean isFinal;
	private boolean generateGetter;
	private boolean generateSetter;
	private String[] arrayVariableDefaults;
	
	public CBVariable(String name, String sqlName, String type, String suffix, String defaultValue,
			boolean isStringArray, boolean isStatic, boolean isFinal,
			boolean generateGetter, boolean generateSetter, String[] arrayVariableDefaults) {
		
		super();
		this.name = name;
		this.sqlName = sqlName;
		this.type = type;
		this.suffix = suffix;
		this.defaultValue = defaultValue;
		this.isStringArray = isStringArray;
		this.isStatic = isStatic;
		this.isFinal = isFinal;
		this.generateGetter = generateGetter;
		this.generateSetter = generateSetter;
		this.arrayVariableDefaults = arrayVariableDefaults;
	}

	public CBVariable() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public boolean isStringArray() {
		return isStringArray;
	}

	public void setStringArray(boolean isStringArray) {
		this.isStringArray = isStringArray;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	public String[] getArrayVariableDefaults() {
		return arrayVariableDefaults;
	}

	public void setArrayVariableDefaults(String[] arrayVariableDefaults) {
		this.arrayVariableDefaults = arrayVariableDefaults;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isGenerateGetter() {
		return generateGetter;
	}

	public void setGenerateGetter(boolean generateGetter) {
		this.generateGetter = generateGetter;
	}

	public boolean isGenerateSetter() {
		return generateSetter;
	}

	public void setGenerateSetter(boolean generateSetter) {
		this.generateSetter = generateSetter;
	}

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

}
