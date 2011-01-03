package com.argility.master.dao.sqlbuilder.rules;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.argility.master.dao.sqlbuilder.SqlBuilderException;
import com.argility.master.util.StringUtils;

public class RuleIfPrimaryKeyExistsItMustBeSet extends SqlBuilderRule {

	private Object entity;
	private String[] primaryKeys;
	private boolean pkNotRequired;
	
	public RuleIfPrimaryKeyExistsItMustBeSet(Object entity, String[] primaryKeys, boolean pkNotRequired) {
		this.entity = entity;
		this.primaryKeys = primaryKeys;
		this.pkNotRequired = pkNotRequired;
	}

	@Override
	public void checkRules() throws SqlBuilderException{
		
		if (pkNotRequired && (primaryKeys == null || primaryKeys.length == 0)) {
			throw new SqlBuilderException("Entity must have a primary key");
		}
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(entity);
		for (int i = 0; i < primaryKeys.length; i++) {
			String pk = primaryKeys[i];
			String camelCase = StringUtils.toCamelCase(pk);
			if (!param.hasValue(camelCase)) {
				throw new SqlBuilderException("Entity " + entity.getClass().getName() + " has no field " + camelCase);
			}
			
			if (param.getValue(camelCase) == null) {
				throw new SqlBuilderException("Primary key column " + camelCase + " cant be NULL ");
			}
		}
		
		if(getNextRule() != null) getNextRule().checkRules();
	}
}
