/*
 * Copyright 20011-2012 the original author or authors.
 *
 */
package com.neustar.workflow.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.neustar.workflow.core.defination.GetSqlStmt;

/**
 * An abstract class which defines generic methods used to execute an SQL
 * statement. Should be extended to deal with the result obtained.
 * 
 * @author lokesh.makwane
 * @since 26.07.2011
 * 
 */
public abstract class GetSQLConnection extends GetBeanContext implements
GetSqlStmt {

	private DataSourceTransactionManager transactionManager;// Use for managing
	// the transaction
	protected Connection dbConn = null; // Occupy the instance of DB Connection
	private TransactionTemplate txTemplate; // Transaction template
	protected String sqlStatment; // For Sql Statement

	
	/* (non-Javadoc)
	 * @see com.neustar.workflow.core.defination.GetSqlStmt#getSqlStatement()
	 */
	public String getSqlStatement() {
		// TODO Auto-generated method stub
		return sqlStatment;
	}

	/**
	 * @return
	 */
	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	/**
	 * @return
	 */
	public TransactionTemplate getTxTemplate() {
		return txTemplate;
	}

	/**
	 * Initializing the DB Connection
	 * @throws SQLException
	 */
	protected void initialize() throws SQLException {
		if (ConnectionFactory.getDataSource() == null)
			throw new NullPointerException("DB Connection not set "
					+ getBeanName());
		if (getSqlStatement() == null || getSqlStatement() == "")
			throw new NullPointerException("SQL Statement not set "
					+ getBeanName());

		dbConn = ConnectionFactory.getConnection();

	}

	/**
	 * @param sqlStatment
	 */
	public void setSqlStatment(String sqlStatment) {
		this.sqlStatment = sqlStatment;
	}

	/**
	 * @param transactionManager
	 */
	public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
		this.txTemplate = new TransactionTemplate(getTransactionManager());
		this.txTemplate
		.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	}
}
