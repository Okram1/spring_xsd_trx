package com.argility.master.dao.sqlbuilder.rules;

import java.util.Arrays;
import java.util.List;

import com.argility.master.dao.sqlbuilder.SqlBuilderException;

public class RuleColumnsValidForTable extends SqlBuilderRule {

	private String[] columns;
	private String[] tableColumns;
	private String table;
	
	public RuleColumnsValidForTable(String[] columns, String[] tableColumns, String table) {
		super();
		this.columns = columns;
		this.tableColumns = tableColumns;
		this.table = table;
	}

	@Override
	public void checkRules() throws SqlBuilderException{
		List<String> tblCols = Arrays.asList(tableColumns);
		for (int i = 0; i < columns.length; i++) {
			if (!tblCols.contains(columns[i])) {
				throw new SqlBuilderException("Column " + columns[i] + " invalid for table " + table);
			}
		}
		
		if(getNextRule() != null) getNextRule().checkRules();

	}

}
