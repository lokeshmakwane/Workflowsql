/*
 * Copyright 20011-2012 the original author or authors.
 */
package com.neustar.workflow.core.defination;

import java.util.List;

/**
 * Use to determine the SqlParameter
 * 
 * @author lokesh.makwane
 * @since 05.08.2011
 * 
 */
public interface GetSQLParam {
	/*
	 * To Get Sql Parameter
	 */
	void setParameter(List<?> ex);

}
