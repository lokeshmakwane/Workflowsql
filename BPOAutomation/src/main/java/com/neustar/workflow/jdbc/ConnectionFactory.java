/*
 * Copyright 20011-2012 the original author or authors.
 */
package com.neustar.workflow.jdbc;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * Use to create the Connection Factory.
 * 
 * @author lokesh.makwane
 * @since 05.08.2011
 * 
 */
public class ConnectionFactory {

	static private DataSource dataSource; // Holds the DataSource Context
	static protected Connection dbConnect = null; // Holds the DB connection

	
//	 static {
//		 	if(dbConnect==null || dbConnect!=null)
//		 		dbConnect = DataSourceUtils.getConnection(getDataSource());
//		 	
//		 }
	 
	public static DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSrc) {
		dataSource = dataSrc;
	}

	public static Connection getConnection() {
		if (dbConnect == null) {
			dbConnect = DataSourceUtils.getConnection(getDataSource());
		}
		return dbConnect;
	}

}
