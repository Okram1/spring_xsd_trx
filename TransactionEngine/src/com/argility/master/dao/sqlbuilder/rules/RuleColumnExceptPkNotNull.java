package com.argility.master.dao.sqlbuilder.rules;

import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.argility.master.dao.sqlbuilder.SqlBuilderException;
import com.argility.master.util.StringUtils;

public class RuleColumnExceptPkNotNull extends SqlBuilderRule {

	public RuleColumnExceptPkNotNull(Object entity, String[] primaryKeys,
			String[] columns) {
		super();
		this.entity = entity;
		this.primaryKeys = primaryKeys;
		this.columns = columns;
	}

	private Object entity;
	private String[] primaryKeys;
	private String[] columns;
	
	@Override
	public void checkRules() throws SqlBuilderException{
		boolean foundCol = false;
		List<String> pkCols = Arrays.asList(primaryKeys);
		SqlParameterSource param = new BeanPropertySqlParameterSource(entity);
		
		for (int i = 0; i < columns.length; i++) {
			String col = columns[i];
			String camelCase = StringUtils.toCamelCase(col);
			
			// skip primary keys
			if (pkCols.contains(col)) {
				continue;
			}
			
			if (param.hasValue(camelCase) && param.getValue(camelCase) != null) {
				foundCol = true;
				break;
			}
		}

		if (!foundCol) {
			throw new SqlBuilderException("All columns besides the primary key columns are null");
		}
		
		if(getNextRule() != null) getNextRule().checkRules();
	}

}
