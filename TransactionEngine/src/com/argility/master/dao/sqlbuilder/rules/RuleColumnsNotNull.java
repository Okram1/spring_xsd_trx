package com.argility.master.dao.sqlbuilder.rules;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.argility.master.dao.sqlbuilder.SqlBuilderException;
import com.argility.master.util.StringUtils;

public class RuleColumnsNotNull extends SqlBuilderRule {

	private Object entity;
	private String[] columns;
	private boolean colMustExist;
	
	public RuleColumnsNotNull(Object entity, String[] columns, boolean colMustExist) {
		super();
		this.entity = entity;
		this.columns = columns;
		this.colMustExist = colMustExist;
	}
	
	@Override
	public void checkRules() throws SqlBuilderException {
		SqlParameterSource param = new BeanPropertySqlParameterSource(entity);
		
		if (colMustExist && (columns == null || columns.length == 0)) {
			throw new SqlBuilderException("There are no columns to update on entity " + entity.getClass().getName());
		}
		
		for (int i = 0; i < columns.length; i++) {
			String colName = columns[i];
			String camelCase = StringUtils.toCamelCase(colName);
			if (!param.hasValue(camelCase) || param.getValue(camelCase) == null) {
				throw new SqlBuilderException("Column " + camelCase + " is not set or NULL");
			}
		}
		
		if(getNextRule() != null) getNextRule().checkRules();

	}

}
