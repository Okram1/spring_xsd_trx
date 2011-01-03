package com.argility.master.dao.sqlbuilder.rules;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.argility.master.dao.sqlbuilder.SqlBuilderException;
import com.argility.master.util.StringUtils;

public class RuleValidWhereCondition extends SqlBuilderRule {

	private Object entity;
	private String[] whereColumns;
	private String table;
	
	public RuleValidWhereCondition(Object entity, String[] whereColumns,
			String table) {
		super();
		this.entity = entity;
		this.whereColumns = whereColumns;
		this.table = table;
	}

	@Override
	public void checkRules() throws SqlBuilderException {
		SqlParameterSource param = new BeanPropertySqlParameterSource(entity);
		
		if (whereColumns == null || whereColumns.length == 0) {
			throw new SqlBuilderException("You must supply the where condition colums");
		}
		
		for (int i = 0; i < whereColumns.length; i++) {
			String camelCase = StringUtils.toCamelCase(whereColumns[i]);
			if (!param.hasValue(camelCase)) {
				throw new SqlBuilderException("Column " + whereColumns[i] + " is not valid for table " + table);
			}
		}
		
		if(getNextRule() != null) getNextRule().checkRules();

	}

}
