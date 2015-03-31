package com.github.entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class EloLogger {
	protected transient Log logger = LogFactory.getLog(getClass());
}
