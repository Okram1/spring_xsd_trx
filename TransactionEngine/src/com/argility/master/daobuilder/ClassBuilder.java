package com.argility.master.daobuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClassBuilder {

	public String className = null;
	public String pckgName = null;
	private String visib = null;
	private String type = null;
	private boolean genEmptyConstructor = false;
	
	protected List<CBVariable> variables = new ArrayList<CBVariable>();
	protected List<String> imports = new ArrayList<String>();
	protected List<String> ifaces = new ArrayList<String>();
	protected String extendsClassName = null;
	protected List<CBVariable[]> constructors = new ArrayList<CBVariable[]>();
	
	protected StringBuffer classSB = new StringBuffer();
	protected StringBuffer customClassAddition = new StringBuffer();
	
	public ClassBuilder(String pckgName, 
			String visib, 
			String type, 
			String className, 
			String extendsClassName, 
			boolean genEmptyConstructor) {
		
		this.className = className;
		this.pckgName = pckgName;
		this.extendsClassName = extendsClassName;
		this.visib = visib;
		this.type = type;
		this.genEmptyConstructor = genEmptyConstructor;
	}
	
	public void addVariable(CBVariable var) {
		variables.add(var);
	}
	
	public void addImport(String imprt) {
		imports.add(imprt);
	}
	
	public void addIface(String iface) {
		ifaces.add(iface);
	}
	
	public void addVariableConstructor(CBVariable[] vars) {
		constructors.add(vars);
	}
	
	public StringBuffer writePackage() {
		classSB.append("package " + pckgName + ";\n");
		return classSB;
	}
	
	public StringBuffer writeImports() {
		for (Iterator<String> iterator = imports.iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			classSB.append("import " + type + ";\n");
			
		}
		return classSB;
	}
	
	public StringBuffer writeClassHeader() {
		classSB.append("// This class was generated by the DAO builder\n");
		classSB.append(visib + " " + type + " " + className);
		if (extendsClassName != null) {
			classSB.append(" extends " + extendsClassName);
		}
		
		if (ifaces.size() > 0) {
			classSB.append(" implements ");
		}
		String comma = "";
		for (Iterator<String> iterator = ifaces.iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			classSB.append(comma + type + " ");
			comma = ",";
		}
		classSB.append(" {\n");

		return classSB;
	}
	
	public StringBuffer writeVarDeclaration() {
		
		for (Iterator<CBVariable> iterator = variables.iterator(); iterator.hasNext();) {
			CBVariable var = (CBVariable) iterator.next();
			classSB.append("\t" + var.getSuffix());
			
			if (var.isStatic()) classSB.append(" static");
			if (var.isFinal()) classSB.append(" final");
			classSB.append(" " + var.getType());
			classSB.append(" " + var.getName());
			
			String comma = ",";
			if (var.isStringArray()) {
				
				if (var.getArrayVariableDefaults() == null || var.getArrayVariableDefaults().length == 0) {
					classSB.append(" = new String[] {};\n");
					continue;
				}
				
				classSB.append(" = new String[] {\n");
				int cnt = 0;
				for (int i = 0; i < var.getArrayVariableDefaults().length; i++) {
					if ((var.getArrayVariableDefaults().length - 1) == cnt) {
						comma = "";
					}
					classSB.append("\t\t\t\"" + var.getArrayVariableDefaults()[i] + "\"" +  comma + "\n");
					cnt++;
					
				}
				classSB.append("\t\t\t};\n");
				newLine();
			} else {
				if (var.getDefaultValue() != null) {
					classSB.append(" = \"" + var.getDefaultValue() +"\";\n");
				} else {
					classSB.append(";\n");
				}
			}
			
		}
		
		return classSB;
	}
	
	public StringBuffer writeBeanMethods() {
		for (Iterator<CBVariable> iterator = variables.iterator(); iterator.hasNext();) {
			CBVariable var = (CBVariable) iterator.next();
			String name = var.getName();
			
			if (var.isGenerateGetter()) {

				classSB.append("\tpublic " + var.getType() + " get" + toCamelCase(name, false, true) + "() {\n");
				classSB.append("\t\treturn this." + name + ";\n");
				classSB.append("\t}\n\n");
			}
			
			if (var.isGenerateSetter()) {
				classSB.append("\tpublic void set" + toCamelCase(name, false, true) + "("+var.getType() + " " + name +") {\n");
				classSB.append("\t\tthis." + name + " = " + name + ";\n");
				classSB.append("\t}\n\n");
			}
			
		}
		
		return classSB;
	}
	
	public StringBuffer writeDefaultConstructor() {
		classSB.append("\tpublic " + className + "() {};\n");
		return classSB;
	}
	
	public StringBuffer writeCustomAddition() {
		if (customClassAddition != null && customClassAddition.length() > 0) {
			classSB.append(customClassAddition);
		}
		return classSB;
	}

	
	public StringBuffer writeVarConstructors() {
		
		if (constructors.size() == 0) {
			return classSB;
		}
		

		for (Iterator<CBVariable[]> iterator = constructors.iterator(); iterator.hasNext();) {
			CBVariable[] vars = (CBVariable[]) iterator.next();
			classSB.append("\tpublic " + className + "(");
			
			String comma = ", ";
			int cnt = 0;
			int loopCnt = 0;
			for (int i = 0; i < vars.length; i++) {
				CBVariable var = vars[i];
				
				if (cnt == 3) {
					cnt = 0;
					classSB.append("\n\t\t\t\t");
				}
				if ((vars.length - 1) == loopCnt) {
					comma = "";
				}
				classSB.append(var.getType() + " " + var.getName() + comma);
				
				cnt++;
				loopCnt++;
				
				
			}
			classSB.append(") {\n");
			
			for (int i = 0; i < vars.length; i++) {
				CBVariable var = vars[i];
				classSB.append("\t\tthis." + var.getName() + " = " + var.getName() + ";\n");
			}
			
			classSB.append("\t}\n");
			newLine();
			
		}
		
		return classSB;
	}

	public void addCustomClassAddition(StringBuffer sb) {
		customClassAddition.append(sb);
	}
	
	public void newLine() {
		classSB.append("\n");
	}
	
	public StringBuffer getClassStringBuffer() {
		
		writePackage();
		newLine();
		writeImports();
		newLine();
		
		writeClassHeader();
		newLine();
		
		writeVarDeclaration();
		newLine();
		
		if (genEmptyConstructor) {
			writeDefaultConstructor();
			newLine();
		}
		
		writeVarConstructors();
		
		newLine();
		writeCustomAddition();
		
		writeBeanMethods();
		newLine();
		
		classSB.append("}\n");
		return classSB;
	}
	
	public static String toCamelCase(String s) {
		return toCamelCase(s, false, false);
	}
	
	public static String toCamelCase(String s, boolean skipFirst, boolean firstOnly) {
		if (s == null) return null;
		
		String[] parts = s.split("_");
		if (parts.length == 0) return s;
		
		String camelCaseString = "";
		boolean first = true;

		for (String part : parts) {
			if (skipFirst && first) {
				camelCaseString += part;
			} else {
				camelCaseString = camelCaseString + toProperCase(part, firstOnly);
			}
			
			first = false;
			
		}
		return camelCaseString;
	}

	private static String toProperCase(String s, boolean firstOnly) {
		String str = s.substring(0, 1).toUpperCase() + s.substring(1);
		if (firstOnly) {
			return str;
		}
		str = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		return str;
	}

}
