/*
 * Copyright 20011-2012 the original author or authors.
 */
package com.neustar.workflow.jdbc;

import com.neustar.workflow.core.defination.ContextBeanInfo;

/**
 * Use to setting the Application-context bean Context
 * 
 * @author lokesh.makwane
 * @since 26.07.2011
 */
public abstract class GetBeanContext implements ContextBeanInfo {
	/* Gets the context name of the bean */
	private String beanName;

	/* Getter for the Beanname */
	public String getBeanName() {
		return beanName;
	}

	/* Setter for the Beanname */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

}
