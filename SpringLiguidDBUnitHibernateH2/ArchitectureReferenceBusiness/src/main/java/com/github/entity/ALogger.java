package com.github.entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ALogger {
	protected transient Log LOG = LogFactory.getLog(getClass());
}
