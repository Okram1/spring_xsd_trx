package com.argility.master.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.argility.master.dao.sqlbuilder.JdbcSqlBuilder;
import com.argility.master.dao.sqlbuilder.JdbcSqlBuilderImpl;
import com.argility.master.dao.sqlbuilder.rules.RuleColumnExceptPkNotNull;
import com.argility.master.dao.sqlbuilder.rules.RuleColumnsNotNull;
import com.argility.master.dao.sqlbuilder.rules.RuleColumnsValidForTable;
import com.argility.master.dao.sqlbuilder.rules.RuleIfPrimaryKeyExistsItMustBeSet;
import com.argility.master.dao.sqlbuilder.rules.RuleValidWhereCondition;
import com.argility.master.dao.sqlbuilder.rules.SqlBuilderRule;
import com.argility.master.trxengine.header.ActH01;
import com.argility.master.util.StringUtils;

public abstract class AbstractSpringJdbcDAO<T> {

	protected transient Logger log = Logger
			.getLogger(this.getClass().getName());

	public JdbcSqlBuilder sqlBuilder = new JdbcSqlBuilderImpl();

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return namedJdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	protected abstract String getTableName();

	protected abstract String[] getColumnNames();

	protected abstract String[] getPrimaryKeyColumns();

	protected abstract String[] getIncrementOnUpdateColumns();

	public void executeSqlUpdate(String sql, Map<String, Object> paramMap, ActH01 actH01) {
		log.info("Executing: " + sql);
		getNamedJdbcTemplate().execute(sql, paramMap,
				new ReplicationSessionCallback<ReplicationSession>(actH01));
	}
	
	private T executeInsert(ActH01 actH01, T entity, String[] columns, boolean ignoreNullFields) {
		
		// Apply global insert rules
		RuleColumnsValidForTable rules = new RuleColumnsValidForTable(columns, getColumnNames(), getTableName());
		rules.setNextRule(new RuleIfPrimaryKeyExistsItMustBeSet(entity, getPrimaryKeyColumns(), false));
		
		// If we need to ignore nulls, strip out null columns
		String[] insertColumns;
		if (ignoreNullFields) {
			insertColumns = excludeNullColumns(columns, entity);
			rules.setNextRule(new RuleColumnsNotNull(entity, insertColumns, true));
		} else {
			insertColumns = columns;
		}
		
		// Run the rules
		rules.checkRules();
		
		// Build the insert SQL
		String sql = sqlBuilder.buildInsertSql(insertColumns, getTableName());
		
		//log.info("Executing insert: " + sql);
		getNamedJdbcTemplate().execute(sql, getSqlParamSource(entity), new ReplicationSessionCallback<ReplicationSession>(actH01));
		return entity;
	}
	
	public T executeUpdate(ActH01 actH01, 
			T entity, 
			String[] updateColumns, 
			String[] whereColumns, 
			boolean ignoreNullFields,
			boolean ignoreIncOnUpdate) {
		
		// Apply global update rules
		SqlBuilderRule rules = new RuleColumnsValidForTable(updateColumns, getColumnNames(), getTableName());
		rules.setNextRule(new RuleValidWhereCondition(entity, whereColumns, getTableName()));
		
		// If we need to ignore nulls, strip out null columns
		String[] updColumns;
		if (ignoreNullFields) {
			updColumns = excludeNullColumns(updateColumns, entity);
			// Verify that there is something to update
			rules.setNextRule(new RuleColumnExceptPkNotNull(entity, getPrimaryKeyColumns(), updColumns));
		} else {
			updColumns = updateColumns;
		}
		
		rules.checkRules();
		
		String sql = sqlBuilder.buildUpdateSql(updColumns, 
				whereColumns, 
				getPrimaryKeyColumns(), 
				getIncrementOnUpdateColumns(), 
				getTableName(), 
				ignoreIncOnUpdate);
		
		//log.info("Executing update: " + sql);
		getNamedJdbcTemplate().execute(sql, getSqlParamSource(entity), new ReplicationSessionCallback<ReplicationSession>(actH01));
		return entity;
	}
	
	private T executeDelete(ActH01 actH01, T entity, String[] whereColumns, boolean orOperationForWhereCondition) {
		RuleValidWhereCondition rules = new RuleValidWhereCondition(entity, whereColumns, getTableName());
		
		rules.checkRules();
		
		String sql = sqlBuilder.buildDeleteSql(whereColumns, getTableName(), orOperationForWhereCondition);
		//log.info("Executing delete: " + sql);
		getNamedJdbcTemplate().execute(sql, getSqlParamSource(entity), new ReplicationSessionCallback<ReplicationSession>(actH01));
		return entity;
	}
	
	private String[] excludeNullColumns(String[] columns, T entity) {
		List<String> list = new ArrayList<String>();
		SqlParameterSource param = new BeanPropertySqlParameterSource(entity);
		
		for (int i = 0; i < columns.length; i++) {
			String camelCase = StringUtils.toCamelCase(columns[i]);
			
			if (param.hasValue(camelCase) && param.getValue(camelCase) != null) {
				list.add(columns[i]);
			}
		}
		
		return list.toArray(new String[list.size()]);
	}
	
	private SqlParameterSource getSqlParamSource(T entity) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(entity);
		return param;
	}

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @return
	 * 		Persisted entity
	 */
	public T insertEntity(ActH01 actH01, T entity) {
		return insertEntity(actH01, entity, false);
	}

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param ignoreNullFields
	 * 		Specify if fields on the entity that are null should be excluded from the insert
	 * @return
	 * 		Persisted entity
	 */
	public T insertEntity(ActH01 actH01, T entity, boolean ignoreNullFields) {
		return insertEntity(actH01, entity, getColumnNames(), ignoreNullFields);	
	}

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param insertColumns
	 * 		Insert only these columns from the entity object
	 * @param ignoreNullFields
	 * 		Specify if fields on the entity that are null should be excluded from the insert
	 * @return
	 * 		Persisted entity
	 */
	public T insertEntity(ActH01 actH01, T entity, String[] insertColumns, boolean ignoreNullFields) {
		return executeInsert(actH01, entity, insertColumns, ignoreNullFields);
	}

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param ignoreNullFields
	 * 		Specify if fields on the entity that are null should be excluded from the update
	 * @return
	 * 		Persisted entity
	 */
	public T updateEntity(ActH01 actH01, T entity) {
		return updateEntity(actH01, entity, false);
	}
	
	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param ignoreNullFields
	 * 		Specify if fields on the entity that are null should be excluded from the update
	 * @return
	 * 		Persisted entity
	 */
	public T updateEntity(ActH01 actH01, T entity, boolean ignoreNullFields) {
		return updateEntity(actH01, entity, ignoreNullFields, false);
	}

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param ignoreNullFields
	 * 		Specify if fields on the entity that are null should be excluded from the update
	 * @param noIncrementOnUpdate
	 * 		If this fields default is to add the value to the field, this option can override this functionality
	 * @return
	 * 		Persisted entity
	 */
	public T updateEntity(ActH01 actH01, T entity, boolean ignoreNullFields,
			boolean noIncrementOnUpdate) {
		return updateEntity(actH01, entity, getColumnNames(), getPrimaryKeyColumns(), ignoreNullFields, noIncrementOnUpdate);
	}

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param updateColumns
	 * 		Only these columns will be updated and not the full entity
	 * @return
	 * 		Persisted entity
	 */
	public T updateEntity(ActH01 actH01, T entity, String[] updateColumns) {
		return updateEntity(actH01, entity, updateColumns, false);
	}

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param updateColumns
	 * 		Only these columns will be updated and not the full entity
	 * @param ignoreNullFields
	 * 		Specify if fields on the entity that are null should be excluded from the update
	 * @return
	 * 		Persisted entity
	 */
	public T updateEntity(ActH01 actH01, T entity,
			String[] updateColumns, boolean ignoreNullFields) {
		return updateEntity(actH01, entity, updateColumns, ignoreNullFields, false);
	}
	
	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param updateColumns
	 * 		Only these columns will be updated and not the full entity
	 * @param ignoreNullFields
	 * 		Specify if fields on the entity that are null should be excluded from the update
	 * @param noIncrementOnUpdate
	 * 		If this fields default is to add the value to the field, this option can override this functionality
	 * @return
	 * 		Persisted entity
	 */
	public T updateEntity(ActH01 actH01, T entity,
			String[] updateColumns, boolean ignoreNullFields, boolean noIncrementOnUpdate) {
		return updateEntity(actH01, entity, updateColumns, getPrimaryKeyColumns(), ignoreNullFields, noIncrementOnUpdate);
	}

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param updateColumns
	 * 		Only these columns will be updated and not the full entity
	 * @param whereColumns
	 * 		These fields will be used to build the sql WHERE clause
	 * @return
	 * 		Persisted entity
	 */
	public T updateEntity(ActH01 actH01, T entity,
			String[] updateColumns, String[] whereColumns) {
		return updateEntity(actH01, entity, updateColumns, whereColumns, false);
	}

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param updateColumns
	 * 		Only these columns will be updated and not the full entity
	 * @param whereColumns
	 * 		These fields will be used to build the sql WHERE clause
	 * @param ignoreNullFields
	 * 		Specify if fields on the entity that are null should be excluded from the update
	 * @return
	 * 		Persisted entity
	 */
	public T updateEntity(ActH01 actH01, T entity,
			String[] updateColumns, String[] whereColumns, boolean ignoreNullFields) {
		return updateEntity(actH01, entity, updateColumns, whereColumns, ignoreNullFields, false);
	}

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param updateColumns
	 * 		Only these columns will be updated and not the full entity
	 * @param whereColumns
	 * 		These fields will be used to build the sql WHERE clause
	 * @param ignoreNullFields
	 * 		Specify if fields on the entity that are null should be excluded from the update
	 * @param noIncrementOnUpdate
	 * 		If this fields default is to add the value to the field, this option can override this functionality
	 * @return
	 */
	public T updateEntity(ActH01 actH01, 
							T entity,
							String[] updateColumns, 
							String[] whereColumns, 
							boolean ignoreNullFields,
							boolean noIncrementOnUpdate) {		
		return executeUpdate(actH01, entity, updateColumns, whereColumns, ignoreNullFields, noIncrementOnUpdate);
	}

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @return
	 * 		Persisted entity
	 */
	public T deleteEntity(ActH01 actH01, T entity) {
		return deleteEntity(actH01, entity, false);
	}
	
	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param orOperationForWhereCondition
	 * 		if set to true, the WHERE clause will be using OR instead of AND to split the fields
	 * @return
	 * 		Persisted entity
	 */
	public T deleteEntity(ActH01 actH01, T entity, boolean orOperationForWhereCondition) {
		return deleteEntity(actH01, entity, getPrimaryKeyColumns());
	}

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param whereColumns
	 * 		These fields will be used to build the sql WHERE clause
	 * @return
	 * 		Persisted entity
	 */
	public T deleteEntity(ActH01 actH01, T entity, String[] whereColumns) {
		return executeDelete(actH01, entity, whereColumns, false);
	}
	
	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @param whereColumns
	 * 		These fields will be used to build the sql WHERE clause
	 * @param orOperationForWhereCondition
	 * 		if set to true, the WHERE clause will be using OR instead of AND to split the fields
	 * @return
	 * 		Persisted entity
	 */
	public T deleteEntity(ActH01 actH01, T entity, String[] whereColumns, boolean orOperationForWhereCondition) {
		return executeDelete(actH01, entity, whereColumns, orOperationForWhereCondition);
	}
	
	public JdbcSqlBuilder getSqlBuilder() {
		return sqlBuilder;
	}

	public void setSqlBuilder(JdbcSqlBuilder sqlBuilder) {
		this.sqlBuilder = sqlBuilder;
	}


}

