/*
 * Copyright 20011-2012 the original author or authors.
 */
package com.neustar.workflow.core.defination;

import org.springframework.beans.factory.BeanNameAware;

/**
 * Use to identify the Application-context bean Context
 * 
 * @author lokesh.makwane
 * @since 26.07.2011
 */

public interface ContextBeanInfo extends BeanNameAware {

	/*
	 * Get the reference of Context Bean.
	 * 
	 * @return String
	 */
	String getBeanName();
}
