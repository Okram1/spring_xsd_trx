package com.argility.master.daobuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.argility.master.context.SpringContextFactory;

public class DaoBuilder {

	protected static Logger log = Logger
		.getLogger(DaoBuilder.class);
	
	public static String ENTITY_SUFFIX = "Entity";
	public static String IFACE_SUFFIX = "DAO";
	public static String BASE_IMPL_SUFFIX = "BaseJdbcImplDAO";
	public static String IMPL_SUFFIX = "JdbcImplDAO";
	
	public static String ENTITY_PCKG = "dao.entity";
	public static String IFACE_PCKG = "dao";
	public static String BASE_PCKG = "dao.jdbcimpl.base";
	public static String IMPL_PCKG = "dao.jdbcimpl";
	
	public static String BASIC_DAO_PCKG = "com.argility.master.dao";
	public static String BASIC_DAO_NAME = "BasicEntityDAO";
	
	public static String ABSTRACT_DAO_PCKG = "com.argility.master.dao";
	public static String ABSTRACT_DAO_NAME = "AbstractSpringJdbcDAO";

	public StringBuffer springConfig = new StringBuffer();
	
	public DataSource ds = null;
	public String saveLocation = null;
	public String pckg = null;
	
	public DaoBuilder() {
		
	}
	
	public DaoBuilder(DataSource ds, String saveLocation, String pckg) {
		this.ds = ds;
		this.saveLocation = saveLocation;
		this.pckg = pckg;
	}
	
	public ClassBuilder getEntityClass(String table, String pckg, JdbcTableMetaData tmd) throws Exception {
		
		String name = ClassBuilder.toCamelCase(tmd.getTableName());
		CBVariable[] variables = getColumnVars(tmd.getColumns());
		CBVariable[] pks = getPrimaryKeyVars(tmd.getColumns());
		
		String ePckg = pckg + "." + ENTITY_PCKG;
		boolean impSql = false;
		
		ClassBuilder entityClass = new ClassBuilder(ePckg, "public", "class", name+ENTITY_SUFFIX, null, true);
		
		
		for (int i = 0; i < variables.length; i++) {
			if (variables[i].getType().equals("Timestamp")) {
				impSql = true;
			}
			entityClass.addVariable(variables[i]);
		}
		
		if (impSql) {
			entityClass.addImport("java.sql.*");
		}
		
		if (pks.length > 0) {
			entityClass.addVariableConstructor(pks);
		}
		entityClass.addVariableConstructor(variables);
		
		return entityClass;
	}
	
	public ClassBuilder getIfaceClass(String table, String pckg, JdbcTableMetaData tmd) throws Exception {
		
		String name = ClassBuilder.toCamelCase(tmd.getTableName());
		
		String iPckg = pckg + "." + IFACE_PCKG;
		String extend = BASIC_DAO_NAME + "<" + name + ENTITY_SUFFIX + ">";
		
		
		ClassBuilder ifaceClass = new ClassBuilder(iPckg, "public", "interface", name+IFACE_SUFFIX, extend, false);
		ifaceClass.addImport(BASIC_DAO_PCKG + "." + BASIC_DAO_NAME);
		ifaceClass.addImport(pckg + "." + ENTITY_PCKG + "." + name + ENTITY_SUFFIX);
		
		return ifaceClass;
	}
	
	public ClassBuilder getImplClass(String table, String pckg, JdbcTableMetaData tmd) throws Exception {
		
		String name = ClassBuilder.toCamelCase(tmd.getTableName());
		
		String implPckg = pckg + "." + IMPL_PCKG;
		String extend = name + BASE_IMPL_SUFFIX;
		String implement = name + IFACE_SUFFIX;
		
		ClassBuilder implClass = new ClassBuilder(implPckg, "public", "class", name+IMPL_SUFFIX, extend, false);
		implClass.addImport(pckg + "." + IFACE_PCKG + "." + name + IFACE_SUFFIX);
		implClass.addImport(pckg + "." + BASE_PCKG + "." + name + BASE_IMPL_SUFFIX);
		
		implClass.addIface(implement);
		
		return implClass;
	}
	
	public ClassBuilder getBaseImplClass(String table, String pckg, JdbcTableMetaData tmd) throws Exception {
		
		String name = ClassBuilder.toCamelCase(tmd.getTableName());
		
		CBVariable[] variables = getColumnVars(tmd.getColumns());
		CBVariable[] primaryKeys = getPrimaryKeyVars(tmd.getColumns());
		CBVariable[] incOnUpdate = getIncOnUpdateVars(tmd.getColumns());
		
		String[] variablesArray = new String[variables.length];
		String[] primaryKeysArray = new String[primaryKeys.length];
		String[] incOnUpdateArray = new String[incOnUpdate.length];
		
		String implPckg = pckg + "." + BASE_PCKG;
		String extend = ABSTRACT_DAO_NAME + "<" + name + ENTITY_SUFFIX + ">";
		//String implement = name + IFACE_SUFFIX;
		
		ClassBuilder baseImplClass = new ClassBuilder(implPckg, "public", "class", name+ BASE_IMPL_SUFFIX, extend, false);
		baseImplClass.addImport(ABSTRACT_DAO_PCKG + "." + ABSTRACT_DAO_NAME);
		baseImplClass.addImport(pckg + "." + ENTITY_PCKG + "." + name + ENTITY_SUFFIX);
		
		for (int i = 0; i < variables.length; i++) {
			variablesArray[i] = variables[i].getSqlName();
		}
		for (int i = 0; i < primaryKeys.length; i++) {
			primaryKeysArray[i] = primaryKeys[i].getSqlName();
		}
		for (int i = 0; i < incOnUpdate.length; i++) {
			incOnUpdateArray[i] = incOnUpdate[i].getSqlName();
		}
		
		//baseImplClass.addIface(implement);
		baseImplClass.addVariable(
				new CBVariable("TABLE_NAME", null, "String", "public", tmd.getTableName(), false, true, true, false, false, null));
		
		baseImplClass.addVariable(
				new CBVariable("COLUMN_NAMES", null, "String[]", "public", null, true, true, true, false, false, variablesArray));
		
		baseImplClass.addVariable(
				new CBVariable("PK_COLUMNS", null, "String[]", "public", null, true, true, true, false, false, primaryKeysArray));

		baseImplClass.addVariable(
				new CBVariable("INCREMENT_ON_UPDATE_COLUMNS", null, "String[]", "public", null, true, true, true, false, false, incOnUpdateArray));
		
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("\t@Override\n");
		sb.append("\tprotected String getTableName() {\n");
		sb.append("\t\treturn TABLE_NAME;\n");
		sb.append("\t}\n");
		sb.append("\n");
		
		sb.append("\t@Override\n");
		sb.append("\tprotected String[] getColumnNames() {\n");
		sb.append("\t\treturn COLUMN_NAMES;\n");
		sb.append("\t}\n");
		sb.append("\n");

		sb.append("\t@Override\n");
		sb.append("\tprotected String[] getPrimaryKeyColumns() {\n");
		sb.append("\t\treturn PK_COLUMNS;\n");
		sb.append("\t}\n");
		sb.append("\n");
		
		sb.append("\t@Override\n");
		sb.append("\tprotected String[] getIncrementOnUpdateColumns() {\n");
		sb.append("\t\treturn INCREMENT_ON_UPDATE_COLUMNS;\n");
		sb.append("\t}\n");
		sb.append("\n");
		
		baseImplClass.addCustomClassAddition(sb);
		
		return baseImplClass;
	}

	private CBVariable[] getColumnVars(JdbcColumnInfo[] columns) throws Exception {
		List<CBVariable> l = new ArrayList<CBVariable>();
		
		for (int i = 0; i < columns.length; i++) {
			JdbcColumnInfo col = columns[i];
			String colName = col.getColumnName();
			String camelCaseName = ClassBuilder.toCamelCase(colName, true, false);
			
			CBVariable var = new CBVariable(camelCaseName, colName,
					SqlTypeMapper.getInstance().getJavaType(col.getTypeName()), 
					"public", 
					null, 
					false, 
					false, 
					false, 
					true, 
					true,
					null);
			
			l.add(var);
		}
		
		return l.toArray(new CBVariable[l.size()]);

	}
	
	private CBVariable[] getPrimaryKeyVars(JdbcColumnInfo[] columns) throws Exception {
		List<CBVariable> l = new ArrayList<CBVariable>();
		
		for (int i = 0; i < columns.length; i++) {
			JdbcColumnInfo col = columns[i];
			if (!col.isPrimaryKey()) continue;
			
			String colName = col.getColumnName();
			String camelCaseName = ClassBuilder.toCamelCase(colName, true, false);
			
			CBVariable var = new CBVariable(camelCaseName, colName,
					SqlTypeMapper.getInstance().getJavaType(col.getTypeName()), 
					"public", 
					null, 
					false, 
					false, 
					false, 
					true, 
					true, 
					null);
			
			l.add(var);
		}
		
		return l.toArray(new CBVariable[l.size()]);

	}
	
	private CBVariable[] getIncOnUpdateVars(JdbcColumnInfo[] columns) throws Exception {
		List<CBVariable> l = new ArrayList<CBVariable>();
		
		for (int i = 0; i < columns.length; i++) {
			JdbcColumnInfo col = columns[i];
			if (!col.isIncrementOnUpdate()) continue;
			
			String colName = col.getColumnName();
			String camelCaseName = ClassBuilder.toCamelCase(colName, true, false);
			
			CBVariable var = new CBVariable(camelCaseName, colName,
					SqlTypeMapper.getInstance().getJavaType(col.getTypeName()), 
					"public", 
					null, 
					false, 
					false, 
					false, 
					true, 
					true, 
					null);
			
			l.add(var);
		}
		
		return l.toArray(new CBVariable[l.size()]);

	}
	
	public void buildDaos(DataSource ds, String table, String pckg) throws Exception {
		MetaDataBuilder mdb = new MetaDataBuilder(ds);
		
		JdbcTableMetaData tmd = mdb.getTableMetaData(table);
		
		ClassBuilder entityClass = getEntityClass(table, pckg, tmd);
		ClassBuilder ifaceClass = getIfaceClass(table, pckg, tmd);
		ClassBuilder implClass = getImplClass(table, pckg, tmd);
		ClassBuilder baseImplClass = getBaseImplClass(table, pckg, tmd);
		
		log.info(entityClass.getClassStringBuffer().toString());
		log.info(ifaceClass.getClassStringBuffer().toString());
		log.info(implClass.getClassStringBuffer().toString());
		log.info(baseImplClass.getClassStringBuffer().toString());

	}
	
	public void writeClass(String baseLoc, ClassBuilder c) throws IOException {
		String loc = c.pckgName;
		loc = loc.replaceAll("\\.", java.util.regex.Matcher.quoteReplacement(File.separator));
		loc = baseLoc + loc;
		
		File f = new File(loc);
		f.mkdirs();
		
		FileWriter fw = new FileWriter(f.getAbsolutePath() + File.separator + c.className +".java");
		fw.write(c.getClassStringBuffer().toString());
		fw.flush();
		fw.close();

	}
	
	public void buildTableEntities(JdbcTableMetaData tmd, String saveLoc, String pckg, boolean onlyBaseClasses) throws Exception {
		log.info("Building DAOS for table " + tmd.getTableName());
		
		ClassBuilder entityClass = getEntityClass(tmd.getTableName(), pckg, tmd);
		ClassBuilder ifaceClass = getIfaceClass(tmd.getTableName(), pckg, tmd);
		ClassBuilder implClass = getImplClass(tmd.getTableName(), pckg, tmd);
		ClassBuilder baseImplClass = getBaseImplClass(tmd.getTableName(), pckg, tmd);

		//<bean id="cashtillUpdateDAO" class="com.argility.cashtill.dao.impl.CashtillUpdateDAOImpl"
		//	parent="abstractDAO">
		//</bean>
		
		writeClass(saveLoc, entityClass);
		writeClass(saveLoc, ifaceClass);
		if (!onlyBaseClasses) {
			writeClass(saveLoc, implClass);
			writeClass(saveLoc, baseImplClass);
		}
		
		writeSpringConfig(implClass, ifaceClass);

	}
	
	public void writeSpringConfig(ClassBuilder implClass, ClassBuilder ifaceClass) {
		
		String lower = ifaceClass.className.substring(0, 1).toLowerCase() + ifaceClass.className.substring(1);
		springConfig.append("\t<bean id=\""+lower+"\" class=\""+implClass.pckgName+"."+implClass.className+"\"\n");
		springConfig.append("\t\tparent=\"abstractSpringJdbcDAO\">\n");
		springConfig.append("\t</bean>\n\n");
	}
	
	public static void main(String[] args) {
		String saveLoc = null;
		//saveLoc = "C:\\Users\\Mare\\Documents\\workspace_cordys\\DaoModule\\src\\";
		
		saveLoc = "/home/UCS-SOFTWARE/marko.salic/ws_cordys/cordys_retail_src/DaoModule/src/";
		String pckg = "com.argility";
		
		boolean onlyBaseClasses = false;
		boolean generateALLDaos = false;
		
		String[] tables = new String[] {"cash_tran", "cup_tend", "cash_till", "petty_draw"};
		
		DaoBuilder db = new DaoBuilder();
		//TODO - move this logic into a calling class
		DataSource dataSource = SpringContextFactory.getApplicationContext().getBean(
				javax.sql.DataSource.class);
				
		try {
			MetaDataBuilder mdb = new MetaDataBuilder(dataSource);
			
			// If we want to generate DAO's for the FULL schema
			if (generateALLDaos) {
				JdbcTableMetaData[] tmdatas = mdb.getAllTableMetaDatas();
				for (int i = 0; i < tmdatas.length; i++) {
					db.buildTableEntities(tmdatas[i], saveLoc, pckg, onlyBaseClasses);
				}
			} else {
				// Only generate daos for the specified tables
				JdbcTableMetaData tmd = null;
				
				for (int i = 0; i < tables.length; i++) {
					tmd = mdb.getTableMetaData(tables[i]);
					db.buildTableEntities(tmd, saveLoc, pckg, onlyBaseClasses);
				}
			}
			
			System.out.println("Add/replace the following bean declaration inside 'daoSpringContext.xml'");
			System.out.println(db.springConfig.toString());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
