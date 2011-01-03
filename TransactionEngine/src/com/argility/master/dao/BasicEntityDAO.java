package com.argility.master.dao;

import com.argility.master.trxengine.header.ActH01;

public interface BasicEntityDAO<T> {

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @return
	 * 		Persisted entity
	 */
	public abstract T insertEntity(ActH01 actH01, T entity);

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
	public abstract T insertEntity(ActH01 actH01, T entity,
			boolean ignoreNullFields);

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
	public abstract T insertEntity(ActH01 actH01, T entity,
			String[] insertColumns, boolean ignoreNullFields);

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
	public abstract T updateEntity(ActH01 actH01, T entity);

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
	public abstract T updateEntity(ActH01 actH01, T entity,
			boolean ignoreNullFields);

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
	public abstract T updateEntity(ActH01 actH01, T entity,
			boolean ignoreNullFields, boolean noIncrementOnUpdate);

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
	public abstract T updateEntity(ActH01 actH01, T entity,
			String[] updateColumns);

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
	public abstract T updateEntity(ActH01 actH01, T entity,
			String[] updateColumns, boolean ignoreNullFields);

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
	public abstract T updateEntity(ActH01 actH01, T entity,
			String[] updateColumns, boolean ignoreNullFields,
			boolean noIncrementOnUpdate);

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
	public abstract T updateEntity(ActH01 actH01, T entity,
			String[] updateColumns, String[] whereColumns);

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
	public abstract T updateEntity(ActH01 actH01, T entity,
			String[] updateColumns, String[] whereColumns,
			boolean ignoreNullFields);

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
	public abstract T updateEntity(ActH01 actH01, T entity,
			String[] updateColumns, String[] whereColumns,
			boolean ignoreNullFields, boolean noIncrementOnUpdate);

	/**
	 * 
	 * @param actH01
	 * 		Transaction action header, this is where the replication SQL will we written
	 * @param entity
	 * 		Entity object to persists
	 * @return
	 * 		Persisted entity
	 */
	public abstract T deleteEntity(ActH01 actH01, T entity);

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
	public abstract T deleteEntity(ActH01 actH01, T entity,
			boolean orOperationForWhereCondition);

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
	public abstract T deleteEntity(ActH01 actH01, T entity,
			String[] whereColumns);

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
	public abstract T deleteEntity(ActH01 actH01, T entity,
			String[] whereColumns, boolean orOperationForWhereCondition);

}