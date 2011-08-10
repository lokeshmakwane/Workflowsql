/*
 * Copyright 20011-2012 the original author or authors.
 */
package com.neustar.workflow.jdbc;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.neustar.workflow.core.defination.GetSQLParam;

/**
 *An abstract class which defines methods used to execute an SQL statement.
 * 
 * @author lokesh.makwane
 * @since 26.07.2011
 */
public abstract class GetSQLCmdExceute extends GetSQLConnection implements
GetSQLParam {

	private JdbcTemplate jdbcTemplate;
	private CreateStatement createStmt;

	private List<?> parameter = null;

	/**
	 * Set The Callable Statement with Parameter.
	 * 
	 * @param pstmt
	 * @throws SQLException
	 */
	private void setParam(PreparedStatement pstmt) throws SQLException {
		// TODO Auto-generated method stub
		if (getParameter() != null) {
			if (getParameter().isEmpty()) {
				//System.out.println("List is Empty");
				return;
			}
			int mapsize = getParameter().size();
			Iterator<?> keyValuePairs = getParameter().iterator();

			for (int i = 0; i < mapsize; i++) {
				String expr = (String) keyValuePairs.next();
				StringTokenizer st = new StringTokenizer(expr, ":");
				if (st.countTokens() != 2)
					throw new SQLException("Unrecognized expression " + expr
							+ " in bean " + getBeanName());
				String entry = st.nextToken();
				String type = st.nextToken();

				if (type.equalsIgnoreCase("NUMBER")) {
					pstmt.setInt(i + 1, Integer.parseInt(entry));
				} else if (type.equalsIgnoreCase("DATE")) {
				} else if (type.equalsIgnoreCase("LONG")) {
					pstmt.setObject(i + 1, entry);
				} else if (type.equalsIgnoreCase("BLOB")) {
					byte[] byt = entry.getBytes();
					int length = byt.length;
					ByteArrayInputStream inputStrm = new ByteArrayInputStream(
							byt);
					pstmt.setBinaryStream(i + 1, inputStrm, length);
				} else if (type.equalsIgnoreCase("CLOB")) {
					byte[] byt = entry.getBytes();
					int length = byt.length;
					ByteArrayInputStream inputStrm = new ByteArrayInputStream(
							byt);
					pstmt.setBinaryStream(i + 1, inputStrm, length);
				} else {
					pstmt.setString(i + 1, entry);
				}
			}
		}
	}

	/**
	 * Use to excute the sql query
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Object execute() throws SQLException,BadSqlGrammarException {
		jdbcTemplate = new JdbcTemplate(ConnectionFactory.getDataSource());
		createStmt = new CreateStatement();
		createStmt.createPreparedStatement(dbConn);

		if (getSqlStatement().substring(0, 6).equalsIgnoreCase("select")) {
			List<?> rs = jdbcTemplate.query(createStmt, new ObjectRowMapper());
			return rs;
		} else {
			Integer i = jdbcTemplate.update(createStmt);
			return i;
		}
	}

	public List getParameter() {
		return parameter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neustar.workflow.core.defination.GetSQLParam#setParameter(java.util
	 * .List)
	 */
	public void setParameter(List<?> ex) {
		// TODO Auto-generated method stub
		parameter = ex;
	}

	/*
	 * Anonymous inner class for setting the sql statement and setting the
	 * parameter for query
	 * 
	 * @author lokesh.makwane
	 */
	class CreateStatement implements PreparedStatementCreator {
		public PreparedStatement createPreparedStatement(Connection connection)
		throws SQLException {
			PreparedStatement pstmt = connection
			.prepareStatement(getSqlStatement());
			setParam(pstmt);
			return pstmt;
		}
	}

	/*
	 * Anonymous inner class for returning the result of the query
	 * 
	 * @author lokesh.makwane
	 */

	public class ObjectRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int line) throws SQLException,BadSqlGrammarException {
			ListResultSetExtractor extractor = new ListResultSetExtractor();
			return extractor.extractData(rs);
		}

		/*
		 * Anonymous inner class for returning the result of the query
		 * 
		 * @author lokesh.makwane
		 */
		public class ListResultSetExtractor implements ResultSetExtractor {

			public Object extractData(ResultSet rs) throws SQLException,BadSqlGrammarException {
				List list = new ArrayList();
				ResultSetMetaData data = rs.getMetaData();
				for (int i = 1; i <= data.getColumnCount(); i++) {
					list.add(rs.getString(i));
				}
				return list;
			}

		}

	}

}
